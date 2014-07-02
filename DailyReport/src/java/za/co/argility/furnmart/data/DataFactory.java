/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import za.co.argility.furnmart.entity.ExtractError;
import za.co.argility.furnmart.entity.ExtractHistory;
import za.co.argility.furnmart.entity.ExtractType;
import za.co.argility.furnmart.entity.GLMapActTyp;
import za.co.argility.furnmart.entity.GLSubType;
import za.co.argility.furnmart.entity.MonthEndTableType;
import za.co.argility.furnmart.entity.MonthendEntity;
import za.co.argility.furnmart.entity.NetworkEntity;
import za.co.argility.furnmart.entity.ProcessType;
import za.co.argility.furnmart.entity.ReplicationEntity;
import za.co.argility.furnmart.servlet.helper.MonthendProcesses;
import za.co.argility.furnmart.util.BucketMap;
import za.co.argility.furnmart.util.Log;

/**
 *
 * @author tmaleka
 */
public class DataFactory {
    
    public static HashMap<String, Date> getExtractFilesLastSentDate() throws Exception {
        HashMap<String, Date> dates = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            connection = ConnectionManager.getConnection(ConnectionType.CENTRAL, null);
            ps = connection.prepareStatement(SQLFactory.GET_DATE_EXTRACT_FILES_LAST_SENT);
            
            rs = ps.executeQuery();
            dates = new HashMap<String, Date>();
            
            while (rs.next()) {
                
                String branch = rs.getString(1);
                Date lastSent = rs.getTimestamp(2);
                
                dates.put(branch, lastSent);
            }
            
            return dates;
        }
        
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        
        finally {
            ConnectionManager.close(connection);
        }
    }
    
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
                        comments.add("Replication is passive on central for over " + hours + " hours.");
                    
                    
                    
                }
                
                item.setComments(comments); 
                
                if (comments.isEmpty()) 
                    item.setIsBranchOk(true);
                else
                    item.setIsBranchOk(false); 
                
                // set the item's current fpp code
                item.setPeriod(getCurrentFppCode(item.getBranchCode()));
                
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
    
    
    public static void getMonthendDetails(MonthEndTableType type, HashMap<String, MonthendEntity> map) throws Exception {
        
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String fpp = null;
        int count = 0;
        boolean flag = false;
        
       // ArrayList<MonthendEntity>  list = new ArrayList<MonthendEntity>();
        
        if (map == null)
            map = new HashMap<String, MonthendEntity>();
        
       
        try {
            connection = ConnectionManager.getConnection(ConnectionType.BATCH, null);
            ps = connection.prepareStatement(SQLFactory.GET_MECONS_FPP_CDE);
            rs = ps.executeQuery();
                  
            
            while (rs.next()) {
                fpp = (rs.getString("fpp_cde"));
            }
        
            MonthendEntity item = null;
            
            String tableName = null;
            
            switch (type) {
                case CentralAccount:
                    tableName = "central_account";
                    break;
                    
                case Creditors:
                    tableName = "creditors";
                    break;
                    
                case CashBookExtract:
                    tableName = "cashbook_extract";
                    break;
                    
                case NewGLTranExt:
                    tableName = "new_gl_tran_ext";
                    
            }
          

            
                String query = SQLFactory.GET_MONTHEND_DETAILS;

                if (fpp == null) {
                    query = query.replace("{0}", "");
                }

                else {
                    
                        query = query.replace("{0}", tableName)
                                .replace("{1}", tableName)
                             .replace("{2}", fpp);
                }


                
                ps = connection.prepareStatement(query);

                rs = ps.executeQuery();
                
                while(rs.next()) {                    
                   
                    String key = rs.getString("branch");
                    
                    /*
                    String branchType ="Instore";
                    if( rs.getBoolean("br_is_whs") == true){
                        branchType ="Warehouse";
                    }
                    
                    
                    if( rs.getBoolean("br_is_headoffice") == true){
                        branchType ="H/O";
                    }*/
                     
                    if (map.get(key) != null) {
                        item = map.get(key);
                    }
                    
                    else
                        
                        item = new MonthendEntity();
                    
                    item.setBranchCode(rs.getString("branch"));
                    //item.setFppCde(rs.getString("fpp_cde"));
                    item.setBranchDesc(rs.getString("br_desc"));
                    count = rs.getInt(tableName); 
                    System.out.println("me.getKey() ---> " + tableName);
                    System.out.println("count ---> " + count);
                    
                    if(count > 0){
                        flag = true;
                    }else{
                        flag = false;
                    }    
                    
                    
                    
               
                switch (type) {
                    case CentralAccount:  item.setIsDebtorsRun(flag);
                             System.out.println("Hey dude 1 : " + count);  
                     break;
                    case Creditors :  item.setIsCreditorsRun(flag);
                             System.out.println("Hey dude 2 : " + count); 
                     break;
                    case CashBookExtract:  item.setIsCashBookExtractRun(flag);                    
                             System.out.println("Hey dude 3 : "  + count);  
                     break;
                    case NewGLTranExt: item.setIsNewGLTranExtRun(flag);
                            System.out.println("Hey dude 4 : " + count); 
                     break;
                                              
                }
                
                
             
                
                    
                 map.put(key, item);
                      

                }
                
                System.out.println("test 2");
        
            
        
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
    
    
    public static final List<String> getMonthendBranchList() throws Exception {
        
        List<String> list = new ArrayList<String>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            connection = ConnectionManager.getConnection(ConnectionType.CENTRAL, null);
            ps = connection.prepareStatement(SQLFactory.GET_MONTHEND_BRANCH_LIST);
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
    
    public static String getMeconFpp() throws Exception {
        String fpp = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            connection = ConnectionManager.getConnection(ConnectionType.BATCH, null);
            ps = connection.prepareStatement(SQLFactory.GET_MECONS_FPP_CDE);
            rs = ps.executeQuery();
            
           
            
            while (rs.next()) {
                fpp = (rs.getString("fpp_cde"));
            }
        
            return fpp;
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
                
                // set the item's current fpp code
                item.setPeriod(getCurrentFppCode(item.getBranchCode()));
                
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
    
    public static BucketMap<String, ExtractHistory> getDailyBIExtractHistoryRun(Date date, ProcessType type, boolean checkOnBatch) 
            throws Exception {
        
        BucketMap<String, ExtractHistory> bucket = null;
        
        ExtractHistory history = null;
        ExtractType extractType = null;
        ExtractError error = null;
        
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            if (type == ProcessType.MonthEnd && checkOnBatch)
                connection = ConnectionManager.getConnection(ConnectionType.BATCH, null);
            else
                connection = ConnectionManager.getConnection(ConnectionType.CENTRAL, null);
                
            String query = null;
            
            if (type == ProcessType.DayEnd)
                query = SQLFactory.DAILY_BI_EXTRACTS_HISTORY_DATA.replace("{0}", "start_time::DATE = ?");
            else
                query = SQLFactory.DAILY_BI_EXTRACTS_HISTORY_DATA.replace("{0}", "fpp_cde = ?");
            
            ps = connection.prepareStatement(query);
            
            String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
            
            if (type == ProcessType.DayEnd) {
                ps.setString(1, "DE");
                ps.setDate(2, java.sql.Date.valueOf(dateString));  
            }
            else {
                ps.setString(1, "ME");
                String fppCode = new SimpleDateFormat("yyyyMM").format(date);
                ps.setString(2, fppCode); 
            }
            
            rs = ps.executeQuery();
            
            bucket = new BucketMap<String, ExtractHistory>();
            
            while (rs.next()) {
                
                int histroyId = rs.getInt("daily_bi_extracts_hist_id");
                String branchCode = rs.getString("br_cde");
        
                history = new ExtractHistory(histroyId);
                history.setBranch(branchCode);
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
                
                bucket.put(branchCode, history);
                
            }
            
            return bucket;
        }
        
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        
        finally {
            ConnectionManager.close(connection); 
        }
        
    }
    
    /**
     * Gets the current fpp code
     * 
     * @param branch
     * @return
     * @throws SQLException 
     */
    public static String getCurrentFppCode(String branch) throws SQLException {
        
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String period = null;
        
        try {
            
            connection = ConnectionManager.getConnection(ConnectionType.INSTORE, "c" + branch);
            ps = connection.prepareStatement(SQLFactory.GET_BRANCH_ROLLED_FPP_CODE);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                period = rs.getString(1);
            }
            
            return period;
        
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
        
        finally {
            ConnectionManager.close(connection);
        }
        
    }
    
    public static String getBranchDescription(String branch) throws SQLException {
        
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String description = null;
        
        try {
            
            connection = ConnectionManager.getConnection(ConnectionType.CENTRAL, null);
            ps = connection.prepareStatement(SQLFactory.GET_BRANCH_DESCRIPTION);
            ps.setString(1, branch);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                description = rs.getString(1);
            }
            
            return description;
        
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
        
        finally {
            ConnectionManager.close(connection);
        }
        
    }
    
    public static List<GLSubType> getGlSubTypeMissingList() 
            throws Exception {
        
        List<GLSubType> list = new ArrayList<GLSubType>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null; 
        
        try {
            
            connection = ConnectionManager.getConnection(ConnectionType.BATCH, null);
            ps = connection.prepareStatement(SQLFactory.GET_MISSING_GL_SUB_TYPES);
            rs = ps.executeQuery();
            
             GLSubType entity = null;            
             Log.info("... inside gl data subtype ..."); 
            while (rs.next()) {
                entity = new GLSubType();
                Log.info("... inside gl data subtype result  ...");   
                entity.setGlActType(rs.getInt("act_typ"));
                entity.setGlSubType(rs.getInt("sub_typ"));
                entity.setGlActDesc(rs.getString("act_desc"));
                list.add(entity);
            }
            
            Log.info("... inside gl data subtype list size ---> " + list.size()); 
            return list;
        
            
        }
        catch (Exception e) {
             e.printStackTrace();
            throw new SQLException(e);
        }
        
        finally {
            ConnectionManager.close(connection);
        }
        
    }
    
    public static List<GLMapActTyp> getMissingGLMapActionTypeList() 
            throws Exception {
        
        List<GLMapActTyp> list = new ArrayList<GLMapActTyp>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null; 
        
        try {
            
            connection = ConnectionManager.getConnection(ConnectionType.BATCH, null);
            ps = connection.prepareStatement(SQLFactory.GET_MISSING_GL_MAP_ACT_TYPES);
            rs = ps.executeQuery();
            
             GLMapActTyp entity = null;            
             Log.info("... inside gl data subtype ..."); 
            while (rs.next()) {
                entity = new GLMapActTyp();                 
                entity.setActType(rs.getInt("act_typ"));
                entity.setActDesc(rs.getString("act_desc"));                
                list.add(entity);
            }
            
            Log.info("... inside getMissingGLMapActionTypeList ---> " + list.size()); 
            return list;
        
            
        }
        catch (Exception e) {
             e.printStackTrace();
            throw new SQLException(e);
        }
        
        finally {
            ConnectionManager.close(connection);
        }
        
    }
    
    
       
       public static List<MonthendProcesses> getMEProcessesList() 
            throws Exception {
        
        List<MonthendProcesses> list = new ArrayList<MonthendProcesses>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null; 
        
        try {
            
            connection = ConnectionManager.getConnection(ConnectionType.BATCH, null);
            ps = connection.prepareStatement(SQLFactory.GET_ME_PROCESSES);
            rs = ps.executeQuery();
            
             MonthendProcesses entity = null;            
             Log.info("... inside gl data subtype ..."); 
            while (rs.next()) {
                entity = new MonthendProcesses();
                Log.info("... inside gl data subtype result  ...");   
                entity.setProdCde(rs.getInt("prod_cde"));
                entity.setProdClassDesc(rs.getString("prod_class_desc"));
                entity.setProdMethod(rs.getString("prod_method"));
                String className = rs.getString("prod_obj_jndi_name");
                if (className.equals("central.ExtractUpdateSessionHome"))
                    className = "ExtractUpdateSessionBean";
                
                entity.setProdClass(className);
                list.add(entity);
            }
            
            Log.info("... meProcess list size ---> " + list.size()); 
            return list;
        
            
        }
        catch (Exception e) {
             e.printStackTrace();
            throw new SQLException(e);
        }
        
        finally {
            ConnectionManager.close(connection);
        }
        
    }
    
}
