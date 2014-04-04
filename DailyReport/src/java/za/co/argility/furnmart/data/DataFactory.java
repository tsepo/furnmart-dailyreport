/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import za.co.argility.furnmart.entity.ExtractError;
import za.co.argility.furnmart.entity.ExtractHistory;
import za.co.argility.furnmart.entity.ExtractType;
import za.co.argility.furnmart.entity.NetworkEntity;
import za.co.argility.furnmart.entity.ProcessType;
import za.co.argility.furnmart.entity.ReplicationEntity;

/**
 *
 * @author tmaleka
 */
public class DataFactory {
    
    /**
     * Gets the network overview data
     * @return
     * @throws Exception 
     */
    public static Map<String, Integer> getNetworkOverviewData() throws Exception{
        Map<String, Integer> map = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            connection = ConnectionManager.getConnection(ConnectionType.CENTRAL, ConnectionManager.DAILY_REPORTING_DB);
            ps = connection.prepareStatement(SQLFactory.SELECT_NETWORK_OVERVIEW_DATA);
            rs = ps.executeQuery();
            map = new HashMap<String, Integer>();
            
            while (rs.next()) {
                map.put(rs.getString(2), new Integer(rs.getInt(1)));
            }
            
            return map;
        
        }
        
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
        
        finally {
            ConnectionManager.close(connection); 
        }
       
    }
    
    public static Map<String, Integer> getReplicationStatusOverviewData() throws Exception {
        Map<String, Integer> map = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
        
            connection = ConnectionManager.getConnection(ConnectionType.CENTRAL, null);
            ps = connection.prepareStatement(SQLFactory.SELECT_REPLICATION_STATUS_OVERVIEW_DATA);
            
            rs = ps.executeQuery();
            map = new HashMap<String, Integer>();
            
            while (rs.next()) {
                String process = rs.getString(1);
                int count = rs.getInt(2);
                
                map.put(process, count);
            }
            
            return map;
        }
        
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        
        finally {
            ConnectionManager.close(connection); 
        }
    }
    
    public static List<ReplicationEntity> getReplicationDetails() throws Exception {
        
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        ArrayList<ReplicationEntity> list = new ArrayList<ReplicationEntity>();
        
        try {
            
            connection = ConnectionManager.getConnection(ConnectionType.CENTRAL, null);
            ps = connection.prepareStatement(SQLFactory.GET_REPLICATION_DETAILS);
            
            rs = ps.executeQuery();
            ReplicationEntity item = null;
            
            while(rs.next()) {
                
                item = new ReplicationEntity();
                item.setBranchCode(rs.getString("br_cde"));
                item.setBranchName(rs.getString("br_desc")); 
                item.setAudit(rs.getInt("audit"));
                item.setReplicate(rs.getInt("replicate"));
                item.setDifference(rs.getInt("diff"));
                item.setIsLocked(rs.getBoolean("is_locked"));
                item.setLockedDate(rs.getTimestamp("br_lock_date"));
                item.setUnlockedDate(rs.getTimestamp("br_unlock_date"));
                item.setProcess(rs.getString("process"));
                
                List<String> comments = new ArrayList<String>();
               
                if (item.isLocked()) 
                    comments.add("Branch is locked on replication.");
                if (item.getLockedDate() == null)
                    comments.add("Branch never replicated to central.");
                if (item.getProcess().contains("CRASHED"))
                    comments.add("Replication crashed on central.");
                
                if (item.getLockedDate() != null) {
                    
                    java.util.Date now = new java.util.Date();
                    
                    int numberOfDays = Days.daysBetween(new DateTime(item.getLockedDate()), 
                            new DateTime(now)).getDays();
                    if (numberOfDays >= 1) {
                        comments.add("Branch has not replicated for over a day.");
                    }
                    
                    
                    int numberOfHours = Hours.hoursBetween(new DateTime(item.getLockedDate()), 
                            new DateTime(now)).getHours();
                    
                    if (numberOfHours > 1 && 
                            (item.getProcess() != null && item.getProcess().contains("STARTED")))
                        comments.add("Branch has been locked for too long, about " + numberOfHours + " hours ago.");
                    
                }
                
                if (item.getUnlockedDate() != null) {
                    
                    java.util.Date now = new java.util.Date();
                    
                    int hours = Hours.hoursBetween(new DateTime(item.getUnlockedDate()), 
                            new DateTime(now)).getHours();
                    
                    if (hours >= 3)  
                        comments.add("Replication is passive on central for over " + hours + " hours");
                    
                    
                    
                }
                
                item.setComments(comments); 
                
                if (comments.isEmpty()) 
                    item.setIsBranchOk(true);
                else
                    item.setIsBranchOk(false); 
                
                list.add(item);
                
            }
            
            return list;
        
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        
        finally {
            ConnectionManager.close(connection);
        }
        
    }
    
    public static final List<String> getReplicationBranchList() throws Exception {
        
        List<String> list = new ArrayList<String>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            connection = ConnectionManager.getConnection(ConnectionType.CENTRAL, null);
            ps = connection.prepareStatement(SQLFactory.GET_REPLICATION_BRANCH_LIST);
            rs = ps.executeQuery();
            
            list.add("ALL");
            
            while (rs.next()) {
                list.add(rs.getString("br_cde"));
            }
        
            return list;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        
        finally {
            ConnectionManager.close(connection); 
        }
        
    }
    
    public static final List<String> getReplicationProcesses() throws Exception {
        
        List<String> list = new ArrayList<String>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            connection = ConnectionManager.getConnection(ConnectionType.CENTRAL, null);
            ps = connection.prepareStatement(SQLFactory.GET_PROCESS_LIST);
            rs = ps.executeQuery();
            
            list.add("ANY");
            
            while (rs.next()) {
                String process = rs.getString("process");
                if (process == null || process.trim().length() == 0)
                    continue;
                
                list.add(process);
            }
        
            return list;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        
        finally {
            ConnectionManager.close(connection); 
        }
        
    }
    
    public static List<ReplicationEntity> searchReplicationDataByFilter(String branch, String process) throws Exception {
        
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        ArrayList<ReplicationEntity> list = new ArrayList<ReplicationEntity>();
        
        try {
            
            connection = ConnectionManager.getConnection(ConnectionType.CENTRAL, null);
            String query = SQLFactory.SEARCH_REPLICATION_DATA;
            
            if (branch == null && process == null) {
                query = query.replace("{0}", "").replace("{1}", "");
            }
            
            else if (branch == null && process != null) {
                query = query.replace("{0}", "").replace("{1}", process);
            }
            
            else if (branch != null && process == null) {
                query = query.replace("{0}", branch).replace("{1}", "");
            }
            
            else if (branch != null && process != null){
                query = query.replace("{0}", branch).replace("{1}", process);
            }
            
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ReplicationEntity item = null;
            
            System.out.println(query);
            
            while(rs.next()) {
                
                item = new ReplicationEntity();
                item.setBranchCode(rs.getString("br_cde"));
                item.setBranchName(rs.getString("br_desc")); 
                item.setAudit(rs.getInt("audit"));
                item.setReplicate(rs.getInt("replicate"));
                item.setDifference(rs.getInt("diff"));
                item.setIsLocked(rs.getBoolean("is_locked"));
                item.setLockedDate(rs.getTimestamp("br_lock_date"));
                item.setUnlockedDate(rs.getTimestamp("br_unlock_date"));
                item.setProcess(rs.getString("process"));
                
                List<String> comments = new ArrayList<String>();
               
                if (item.isLocked()) 
                    comments.add("Branch is locked on replication.");
                if (item.getLockedDate() == null)
                    comments.add("Branch never replicated to central.");
                if (item.getProcess().contains("CRASHED"))
                    comments.add("Replication crashed on central.");
                
                if (item.getLockedDate() != null) {
                    
                    java.util.Date now = new java.util.Date();
                    
                    int numberOfDays = Days.daysBetween(new DateTime(item.getLockedDate()), 
                            new DateTime(now)).getDays();
                    if (numberOfDays >= 1) {
                        comments.add("Branch has not replicated for over a day.");
                    }
                    
                    
                    int numberOfHours = Hours.hoursBetween(new DateTime(item.getLockedDate()), 
                            new DateTime(now)).getHours();
                    
                    if (numberOfHours > 1 && 
                            (item.getProcess() != null && item.getProcess().contains("STARTED")))
                        comments.add("Branch has been locked for too long, about " + numberOfHours + " hours ago.");
                    
                }
                
                if (item.getUnlockedDate() != null) {
                    
                    java.util.Date now = new java.util.Date();
                    
                    int hours = Hours.hoursBetween(new DateTime(item.getUnlockedDate()), 
                            new DateTime(now)).getHours();
                    
                    if (hours >= 3)  
                        comments.add("Replication is passive on central for over " + hours + " hours");
                    
                    
                    
                }
                
                item.setComments(comments); 
                
                if (comments.isEmpty()) 
                    item.setIsBranchOk(true);
                else
                    item.setIsBranchOk(false); 
                
                list.add(item);
                
            }
            
            return list;
        
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        
        finally {
            ConnectionManager.close(connection);
        }
    }
    
    public static List<NetworkEntity> getNetworkStatistics(boolean withNetwork) throws Exception {
        
        List<NetworkEntity> list = new ArrayList<NetworkEntity>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            connection = ConnectionManager.getConnection(ConnectionType.CENTRAL, ConnectionManager.DAILY_REPORTING_DB);
            ps = connection.prepareStatement(SQLFactory.GET_NETWORK_STATISTICS_DATA);
            ps.setBoolean(1, withNetwork);
            rs = ps.executeQuery();
            
            NetworkEntity entity = null;
            
            while(rs.next()) {
                
                entity = new NetworkEntity();
                entity.setBranchCode(rs.getString("branch_code"));
                entity.setBranchName(rs.getString("branch_desc"));
                entity.setNetworkAvailable(rs.getBoolean("network_available"));
                entity.setLastPrompted(rs.getTimestamp("last_evaluated"));
                
                list.add(entity);
                
            }
            
            return list;
        
        }
        
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        
        finally {
            ConnectionManager.close(connection);
        }
    }
    
    public static final TreeMap<Date, ArrayList<ExtractHistory>> getDailyBIExtractHistoryRun(Date date, ProcessType type) 
            throws Exception {
        
        TreeMap<Date, ArrayList<ExtractHistory>> treeMap = null;
        ArrayList<ExtractHistory> list = null;
        
        ExtractHistory history = null;
        ExtractType extractType = null;
        ExtractError error = null;
        
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            connection = ConnectionManager.getConnection(ConnectionType.CENTRAL, null);
            ps = connection.prepareStatement(SQLFactory.DAILY_BI_EXTRACTS_HISTORY_DATA);
            
            if (type == ProcessType.DayEnd) {
                ps.setString(1, "DE");
                ps.setDate(2, new java.sql.Date(date.getTime()));  
            }
            else {
                ps.setString(1, "ME");
                ps.setDate(1, new java.sql.Date(date.getTime())); 
            }
            
            rs = ps.executeQuery();
            
            treeMap = new TreeMap<Date, ArrayList<ExtractHistory>>();
            list = new ArrayList<ExtractHistory>();
            
            while (rs.next()) {
                
                int histroyId = rs.getInt("daily_bi_extracts_hist_id");
        
                history = new ExtractHistory(histroyId);
                history.setBranch(rs.getString("br_cde"));
                history.setPeriod(rs.getString("fpp_cde"));
                
                int extractCode = rs.getInt("extract_type");
                
                extractType = new ExtractType(extractCode);
                extractType.setExtractDescription(rs.getString("extract_desc"));
                extractType.setActive(rs.getBoolean("is_active")); 
                
                history.setExtractType(extractType);
                
                String processType = rs.getString("process_type");
                if (processType.equalsIgnoreCase("DE"))
                    history.setProcessType(ProcessType.DayEnd);
                else
                    history.setProcessType(ProcessType.MonthEnd);
                
                history.setStartTime(rs.getTimestamp("start_time"));
                history.setEndTime(rs.getTimestamp("end_time"));
                
                int errorId = rs.getInt("daily_bi_extracts_errors_id");
                if (errorId > 0) {
                    
                    error = new ExtractError(errorId);
                    error.setDateOfError(rs.getTimestamp("error_ts"));
                    error.setStackTrace(rs.getString("error_stack_trace"));
                    
                    history.setExtractError(error); 
                    
                }
                
                list.add(history);
            }
            
            treeMap.put(date, list);
            
            return treeMap;
        }
        
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        
        finally {
            ConnectionManager.close(connection); 
        }
        
    }
}
