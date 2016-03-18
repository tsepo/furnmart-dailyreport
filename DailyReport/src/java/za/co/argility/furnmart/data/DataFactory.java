/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.argility.furnmart.data;

import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import za.co.argility.furnmart.entity.ExtractError;
import za.co.argility.furnmart.entity.ExtractHistory;
import za.co.argility.furnmart.entity.ExtractType;
import za.co.argility.furnmart.entity.GLDetailEntity;
import za.co.argility.furnmart.entity.GLEntity;
import za.co.argility.furnmart.entity.GLMapActTyp;
import za.co.argility.furnmart.entity.GLSubType;
import za.co.argility.furnmart.entity.MonthEndTableType;
import za.co.argility.furnmart.entity.MonthendEntity;
import za.co.argility.furnmart.entity.NetworkEntity;
import za.co.argility.furnmart.entity.ProcessType;
import za.co.argility.furnmart.entity.ProdConsScriptsEntity;
import za.co.argility.furnmart.entity.ProdConsViewEntity;
import za.co.argility.furnmart.entity.ReplicationEntity;
import za.co.argility.furnmart.servlet.helper.MeProdRunData;
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            ConnectionManager.close(connection);
        }
    }

    /**
     * Gets the network overview data
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Integer> getNetworkOverviewData() throws Exception {
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

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        } finally {
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
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

            while (rs.next()) {

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

                if (item.isLocked()) {
                    comments.add("Branch is locked on replication.");
                }
                if (item.getLockedDate() == null) {
                    comments.add("Branch never replicated to central.");
                }
                if (item.getProcess().contains("CRASHED")) {
                    comments.add("Replication crashed on central.");
                }

                if (item.getLockedDate() != null) {

                    java.util.Date now = new java.util.Date();

                    int numberOfDays = Days.daysBetween(new DateTime(item.getLockedDate()),
                            new DateTime(now)).getDays();
                    if (numberOfDays >= 1) {
                        comments.add("Branch has not replicated for over a day.");
                    }

                    int numberOfHours = Hours.hoursBetween(new DateTime(item.getLockedDate()),
                            new DateTime(now)).getHours();

                    if (numberOfHours > 1
                            && (item.getProcess() != null && item.getProcess().contains("STARTED"))) {
                        comments.add("Branch has been locked for too long, about " + numberOfHours + " hours ago.");
                    }

                }

                if (item.getUnlockedDate() != null) {

                    java.util.Date now = new java.util.Date();

                    int hours = Hours.hoursBetween(new DateTime(item.getUnlockedDate()),
                            new DateTime(now)).getHours();

                    if (hours >= 3) {
                        comments.add("Replication is passive on central for over " + hours + " hours.");
                    }

                }
                
                
                item.setComments(comments);

                if (comments.isEmpty()) {
                    item.setIsBranchOk(true);
                } else {
                    item.setIsBranchOk(false);
                }

                // set the item's current fpp code
                item.setPeriod(getCurrentFppCode(item.getBranchCode()));

                list.add(item);

            }

            return list;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            ConnectionManager.close(connection);
        }

    }

    public static void getMonthendDetails(MonthEndTableType type, HashMap<String, MonthendEntity> map, boolean statements) throws Exception {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String fpp = null;
        int count = 0;
        boolean flag = false;

        // ArrayList<MonthendEntity>  list = new ArrayList<MonthendEntity>();
        if (map == null) {
            map = new HashMap<String, MonthendEntity>();
        }

        try {
            if(!statements){
                connection = ConnectionManager.getConnection(ConnectionType.BATCH, null);
                ps = connection.prepareStatement(SQLFactory.GET_MECONS_FPP_CDE);
                rs = ps.executeQuery();
                
                while (rs.next()) {
                    fpp = (rs.getString("fpp_cde"));
                }
            }else{           
                connection = ConnectionManager.getConnection(ConnectionType.CENTRAL, null);
                Calendar cal = new GregorianCalendar();
                String month = null;
                int intMonth = cal.get(Calendar.MONTH) +1;
                if(cal.get(Calendar.MONTH) < 9){
                    month = "0" + intMonth;
                }else{
                    month = "" + intMonth;
                }
                fpp = "" + cal.get(Calendar.YEAR) + month;
                //SimpleDateFormat date_format = new SimpleDateFormat("yyyyMMdd");
                System.out.println("BLikkies Year ---> " + cal.get(Calendar.YEAR));
                System.out.println("BLikkies Month ---> " + month);
                System.out.println("Blikkies fpp ----> " + fpp);
                
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
                     break;
                    
                case Buckets:
                    tableName = "gl_stock_bucket_in";
                    break;
                    
                case Statements:
                    tableName = "statement_mth_extract";
                    break;    

            }

            String query = SQLFactory.GET_MONTHEND_DETAILS;

            if (fpp == null) {
                query = query.replace("{0}", "");
            } else {

                query = query.replace("{0}", tableName)
                        .replace("{1}", tableName)
                        .replace("{2}", fpp);
            }

            ps = connection.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {

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
                } else {
                    item = new MonthendEntity();
                }

                if(statements){
                    item.setFppCde(fpp);
                }
                
                item.setBranchCode(rs.getString("branch"));
                //item.setFppCde(rs.getString("fpp_cde"));
                item.setBranchDesc(rs.getString("br_desc"));
                count = rs.getInt(tableName);
                /*
                System.out.println("me.getKey() ---> " + tableName);
                System.out.println("count ---> " + count);*/
                System.out.println("branch  : " + item.getBranchCode() + "  me.getKey() ---> " + tableName +"count ---> " + count );

                if (count > 0) {
                    flag = true;
                } else {
                    flag = false;
                }

                switch (type) {
                    case CentralAccount:
                        item.setIsDebtorsRun(flag);
                        item.setBranchWarehouse(getWarehouseValues());
                        System.out.println("Hey dude 1 : " + count);
                        System.out.println("item.setIsBranchWarehouse(getWarehouseValues()) : " + item.getBranchWarehouse());
                        break;
                    case Creditors:
                        item.setIsCreditorsRun(flag);
                        System.out.println("Hey dude 2 : " + count);
                        break;
                    case CashBookExtract:
                        item.setIsCashBookExtractRun(flag);
                        System.out.println("Hey dude 3 : " + count);
                        break;
                    case NewGLTranExt:
                        item.setIsNewGLTranExtRun(flag);
                        System.out.println("Hey dude 4 : " + count);
                        break;
                    case Buckets:
                        item.setIsBucketsRun(flag);
                        System.out.println("Hey dude 5 : " + count);
                        break;    
                    case Statements:
                        item.setIsStatementsRun(flag);
                        System.out.println("Hey dude 5 : " + count);
                        break;        

                }

                map.put(key, item);

            }

            System.out.println("test 2");

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            ConnectionManager.close(connection);
        }

    }

    public static final List<String> getMonthendBranchList() throws Exception {

        List<String> list = new ArrayList<String>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            connection = ConnectionManager.getConnection(ConnectionType.BATCH, null);
            ps = connection.prepareStatement(SQLFactory.GET_MONTHEND_BRANCH_LIST);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("br_cde"));
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
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
                if (process == null || process.trim().length() == 0) {
                    continue;
                }

                list.add(process);
            }

            return list;    
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
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
            } else if (branch == null && process != null) {
                query = query.replace("{0}", "").replace("{1}", process);
            } else if (branch != null && process == null) {
                query = query.replace("{0}", branch).replace("{1}", "");
            } else if (branch != null && process != null) {
                query = query.replace("{0}", branch).replace("{1}", process);
            }

            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ReplicationEntity item = null;

            System.out.println(query);

            while (rs.next()) {

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

                if (item.isLocked()) {
                    comments.add("Branch is locked on replication.");
                }
                if (item.getLockedDate() == null) {
                    comments.add("Branch never replicated to central.");
                }
                if (item.getProcess().contains("CRASHED")) {
                    comments.add("Replication crashed on central.");
                }

                if (item.getLockedDate() != null) {

                    java.util.Date now = new java.util.Date();

                    int numberOfDays = Days.daysBetween(new DateTime(item.getLockedDate()),
                            new DateTime(now)).getDays();
                    if (numberOfDays >= 1) {
                        comments.add("Branch has not replicated for over a day.");
                    }

                    int numberOfHours = Hours.hoursBetween(new DateTime(item.getLockedDate()),
                            new DateTime(now)).getHours();

                    if (numberOfHours > 1
                            && (item.getProcess() != null && item.getProcess().contains("STARTED"))) {
                        comments.add("Branch has been locked for too long, about " + numberOfHours + " hours ago.");
                    }

                }

                if (item.getUnlockedDate() != null) {

                    java.util.Date now = new java.util.Date();

                    int hours = Hours.hoursBetween(new DateTime(item.getUnlockedDate()),
                            new DateTime(now)).getHours();

                    if (hours >= 3) {
                        comments.add("Replication is passive on central for over " + hours + " hours");
                    }

                }

                item.setComments(comments);

                if (comments.isEmpty()) {
                    item.setIsBranchOk(true);
                } else {
                    item.setIsBranchOk(false);
                }

                // set the item's current fpp code
                item.setPeriod(getCurrentFppCode(item.getBranchCode()));

                list.add(item);

            }

            return list;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
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

            while (rs.next()) {

                entity = new NetworkEntity();
                entity.setBranchCode(rs.getString("branch_code"));
                entity.setBranchName(rs.getString("branch_desc"));
                entity.setNetworkAvailable(rs.getBoolean("network_available"));
                entity.setLastPrompted(rs.getTimestamp("last_evaluated"));

                list.add(entity);

            }

            return list;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
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

            if (type == ProcessType.MonthEnd && checkOnBatch) {
                connection = ConnectionManager.getConnection(ConnectionType.BATCH, null);
            } else {
                connection = ConnectionManager.getConnection(ConnectionType.CENTRAL, null);
            }

            String query = null;

            if (type == ProcessType.DayEnd) {
                query = SQLFactory.DAILY_BI_EXTRACTS_HISTORY_DATA.replace("{0}", "start_time::DATE = ?");
            } else {
                query = SQLFactory.DAILY_BI_EXTRACTS_HISTORY_DATA.replace("{0}", "fpp_cde = ?");
            }

            ps = connection.prepareStatement(query);

            String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);

            if (type == ProcessType.DayEnd) {
                ps.setString(1, "DE");
                ps.setDate(2, java.sql.Date.valueOf(dateString));
            } else {
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
                if (processType.equalsIgnoreCase("DE")) {
                    history.setProcessType(ProcessType.DayEnd);
                } else {
                    history.setProcessType(ProcessType.MonthEnd);
                }

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
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            ConnectionManager.close(connection);
        }

    }
  
    /**
     * Get ITC Extract data from daily_itc_status table
     * 
     * @return
     * @throws Exception 
     */
    public static List<ExtractHistory> getDailyITCExtractHistoryRun()
            throws Exception {

        List<ExtractHistory> bucket = null;
        ExtractHistory history = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = null;
            connection = ConnectionManager.getConnection(ConnectionType.CENTRAL, null);
         
            query = SQLFactory.GET_DAILY_ITC_STATUS;
            ps = connection.prepareStatement(query);
   
            rs = ps.executeQuery();

            bucket = new ArrayList<ExtractHistory>();

            while (rs.next()) {
                history = new ExtractHistory();
                
                history.setStartTime(rs.getTimestamp("run_date"));
                history.setStatus(rs.getBoolean("is_successful"));
                history.setReason(rs.getString("reason"));
                
                bucket.add(history);
            }
            return bucket;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            ConnectionManager.close(connection);
        }

    }
    
    /**
     * Gets ITC branch details from daily_debtors_status table
     * 
     * @return
     * @throws Exception 
     */
     public static List<ExtractHistory> getITCBranchDetailsRun() 
        throws Exception {
        
        List<ExtractHistory> list = null;
        ExtractHistory history = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = null;
        int daysBehind = 0;
        Date today = null;
        SimpleDateFormat sdf = null;
        
        
        try {
            connection = ConnectionManager.getConnection(ConnectionType.CENTRAL, null);
         
            query = SQLFactory.GET_DAILY_DEBTORS_STATUS;
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            today =  Calendar.getInstance().getTime();
            list = new ArrayList<ExtractHistory>();
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            while (rs.next()) {
                history = new ExtractHistory();
                
                history.setBranch(rs.getString("br_cde"));
                history.setcheckpointAudit(rs.getString("checkpoint_audit"));
                history.setCheckpointDate(rs.getDate("checkpoint_date"));
                
                daysBehind = dateDiffInDays(today, history.getCheckpointDate());
   
                history.setDaysBehind(daysBehind);
               
                Log.info("#############Today's date : " + sdf.format(today));
                Log.info("#############Checkpoint date : " + history.getCheckpointDate());
                Log.info("#############Days behind : " + daysBehind);
                
                list.add(history);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            ConnectionManager.close(connection);
        }
    }
   
     
     /**
      * Calculates difference between 2 dates
      * 
      * @param date1
      * @param date2
      * @return difference in days
      */
    public static int dateDiffInDays(Date date1 , Date date2){
    
        int diffInDays = 0;
        long diff ;
        
        diff = date1.getTime() - date2.getTime();
        diffInDays = (int)(diff / (24 * 60 * 60 * 1000));
        
        return diffInDays;
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
            //connection = ConnectionManager.getConnection(ConnectionType.BATCH, null);
            ps = connection.prepareStatement(SQLFactory.GET_BRANCH_ROLLED_FPP_CODE);
            rs = ps.executeQuery();

            while (rs.next()) {
                period = rs.getString(1);
            }

            return period;

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e);
        } finally {
            ConnectionManager.close(connection);
        }

    }
    
    
    public static String getMeconsFppCode(String branch) throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String period = null;

        try {

            //connection = ConnectionManager.getConnection(ConnectionType.INSTORE, "c" + branch);
            connection = ConnectionManager.getConnection(ConnectionType.BATCH, null);
            ps = connection.prepareStatement(SQLFactory.GET_BRANCH_ROLLED_FPP_CODE);
            rs = ps.executeQuery();

            while (rs.next()) {
                period = rs.getString(1);
            }

            return period;

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e);
        } finally {
            ConnectionManager.close(connection);
        }

    }
    
     public static String getMeconsFppCode() throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String period = null;

        try {

            //connection = ConnectionManager.getConnection(ConnectionType.INSTORE, "c" + branch);
            connection = ConnectionManager.getConnection(ConnectionType.BATCH, null);
            ps = connection.prepareStatement(SQLFactory.GET_BRANCH_ROLLED_FPP_CODE);
            rs = ps.executeQuery();

            while (rs.next()) {
                period = rs.getString(1);
            }

            return period;

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e);
        } finally {
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

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e);
        } finally {
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

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e);
        } finally {
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

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e);
        } finally {
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
                if (className.equals("central.ExtractUpdateSessionHome")) {
                    className = "ExtractUpdateSessionBean";
                }

                entity.setProdClass(className);
                list.add(entity);
            }

            Log.info("... meProcess list size ---> " + list.size());
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e);
        } finally {
            ConnectionManager.close(connection);
        }

    }

    public static List<GLMapActTyp> getBranchActionTypes()
            throws Exception {

        List<GLMapActTyp> list = new ArrayList<GLMapActTyp>();
        List<Integer> actionTypes = new ArrayList<Integer>();
        String fppCde = getMeconFpp();
        List<String> meBranchList = getMonthendBranchList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Set<Integer> actTypes = new HashSet<Integer>();
        System.out.println("meBranchList ---> " + meBranchList.size());
        boolean validBranch;
        for (String branch : meBranchList) {
            try {

                try {
                    connection = ConnectionManager.getConnection(ConnectionType.BATCH, "c" + branch);
                    validBranch = true;
                } catch (SQLException sqle) {
                    validBranch = false;
                }
                if (validBranch) {
                    ps = connection.prepareStatement(SQLFactory.GET_BRANCH_ACTION_TYPES);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        System.out.println("Blikkies --- > " + branch);
                        actTypes.add(rs.getInt("act_typ"));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new SQLException(e);
            } finally {
                ConnectionManager.close(connection);
            }
            actionTypes = new ArrayList<Integer>(actTypes);
        }
        System.out.println("actionTypes size --->" + actionTypes.size());

        List<Integer> glTranMapActionTypes = new ArrayList<Integer>();
        //for (String branch : meBranchList) {
        try {

            connection = ConnectionManager.getConnection(ConnectionType.BATCH, null);
            ps = connection.prepareStatement(SQLFactory.GET_GL_TRAN_MAP_ACTION_TYPES);
            rs = ps.executeQuery();
            while (rs.next()) {
                glTranMapActionTypes.add(rs.getInt("act_typ"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e);
        } finally {
            ConnectionManager.close(connection);
        }
        //}                  
        System.out.println("glTranMapActionTypes.size() ---> " + glTranMapActionTypes.size());

        GLMapActTyp entity = null;
        for (int counter = 0; counter < actionTypes.size(); counter++) {
            if (glTranMapActionTypes.contains(actionTypes.get(counter))) {
                System.out.println("I am in gl tran map ---> " + actionTypes.get(counter));
            } else {
                entity = new GLMapActTyp();
                System.out.println("I am not in gl tran map ---> " + actionTypes.get(counter));
                entity.setActType(actionTypes.get(counter));
                entity.setActDesc(getActDesc(actionTypes.get(counter).intValue()));
                list.add(entity);
            }
        }

        System.out.println("list.size() ---> " + list.size());

        return list;
    }

    public static List<GLEntity> getGLData()
            throws Exception {

        List<GLEntity> list = new ArrayList<GLEntity>();
        String fppCde = getMeconFpp();
        List<String> meBranchList = getMonthendBranchList();
               
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        System.out.println("meBranchList ---> " + meBranchList.size());
        boolean validBranch;
        GLEntity entity = null;
        for (String branch : meBranchList) {
            try {
                 
                try {
                    connection = ConnectionManager.getConnection(ConnectionType.BATCH, "c" + branch);
                    validBranch = true;
                } catch (SQLException sqle) {
                    validBranch = false;
                }
                
                if(validBranch) {
                    entity = new  GLEntity();
                    entity.setBranchCode(branch);
                    entity.setBranchDesc(getBranchDescription(branch));
                    connection = ConnectionManager.getConnection(ConnectionType.BATCH, null);
                    ps = connection.prepareStatement(SQLFactory.GET_GL_DEBTORS_DATA);
                    //System.out.println("fpp Code : " + getCurrentFppCode(branch) + " : Branch : "  + branch);
                    
                    ps.setString(1,fppCde);
                    ps.setString(2, branch);
                    rs = ps.executeQuery();
                     entity.setGlDebtors(0.0);
                    
                    while (rs.next()) {                        
                        entity.setGlDebtors(rs.getDouble("debtors_value"));
                        System.out.println("GL Debtors Data --- > " + branch);
                        //ConnectionManager.close(connection);   
                        
                    }
                    //ConnectionManager.close(connection);  
                    
                    ps = connection.prepareStatement(SQLFactory.GET_GL_STOCK_DATA);
                    ps.setString(1,fppCde);
                    ps.setString(2, branch);
                    rs = ps.executeQuery();
                    
                    while (rs.next()) {
                        entity.setGlStock(rs.getDouble("stock_value"));
                        System.out.println("GL Stock Data --- > " + branch);
                        ConnectionManager.close(connection);

                    }
                    
                    connection = ConnectionManager.getConnection(ConnectionType.BATCH, "c" + branch);
                    ps = connection.prepareStatement(SQLFactory.GET_INSTORE_DEBTORS_DATA);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        entity.setInstoreDebtors(rs.getDouble("debtors_value"));
                        System.out.println("Instore Debtors Data --- > " + branch);
                        ConnectionManager.close(connection);
                    }
                    
                    connection = ConnectionManager.getConnection(ConnectionType.BATCH, "c" + branch);
                    ps = connection.prepareStatement(SQLFactory.GET_INSTORE_STOCK_DATA);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        entity.setInstoreStock(rs.getDouble("stock_value"));
                        System.out.println("Instore Stock Data --- > " + branch);
                        ConnectionManager.close(connection);  
                    }
                    list.add(entity);
                    
                }                
                

            } catch (Exception e) {
                e.printStackTrace();
                throw new SQLException(e);
            } finally {
                ConnectionManager.close(connection);
            }
        }

        System.out.println("list.size() ---> " + list.size());

        return list;
    }
    
    
    
    public static List<GLEntity> getNewGLData(String fppCde)
            throws Exception {

        List<GLEntity> list = new ArrayList<GLEntity>();
        //String fppCde = getMeconFpp();
        List<String> meBranchList = getMonthendBranchList();
        //List<String> meBranchList = new ArrayList<String>();
        //meBranchList.add("0002");
        //meBranchList.add("0003");
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        System.out.println("meBranchList ---> " + meBranchList.size());
        boolean validBranch;
        GLEntity entity = null;
        for (String branch : meBranchList) {
            try {
                 
                try {
                    connection = ConnectionManager.getConnection(ConnectionType.BATCH,null);
                    validBranch = true;
                } catch (SQLException sqle) {
                    Log.info("Yens ---->" );
                    sqle.printStackTrace();
                    validBranch = false;
                }
                
                if(validBranch) {
                     Log.info("branch ----> " + branch);
                    entity = new  GLEntity();
                    entity.setBranchCode(branch);
                    entity.setBranchDesc(getBranchDescription(branch));
                    connection = ConnectionManager.getConnection(ConnectionType.BATCH, null);
                    ps = connection.prepareStatement(SQLFactory.GET_GL_SUMMARY_DETS);
                    ps.setString(1,fppCde);
                    ps.setString(2, branch);                                    
                    rs = ps.executeQuery();
                    entity.setGlDebtors(0.0);
                    entity.setInstoreDebtors(0.0);
                    entity.setGlStock(0.0);
                    entity.setInstoreStock(0.0);
                    
                    while (rs.next()) {                        
                        entity.setGlDebtors(rs.getDouble("gl_debtors"));
                        entity.setInstoreDebtors(rs.getDouble("gl_instore_debtors"));
                        entity.setGlStock(rs.getDouble("gl_stock"));
                        entity.setInstoreStock(rs.getDouble("gl_instore_stock"));
                        
                        //GET_GL_STOCK_DATA
                        double latestGLStockAmt = 0.0d;
                        double latestGLDebtorsAmt = 0.0d;
                        ps = connection.prepareStatement(SQLFactory.GET_GL_STOCK_DATA);
                        ps.setString(1,fppCde);
                        ps.setString(2, branch);                                    
                        rs = ps.executeQuery();
                               
                                               
                        while(rs.next()){
                            latestGLStockAmt = rs.getDouble("stock_value");
                        }
                        
                        if(latestGLStockAmt != entity.getGlStock()){
                            entity.setGlAdjustedStock(latestGLStockAmt);
                        }
                        
                        ps = connection.prepareStatement(SQLFactory.GET_GL_DEBTORS_DATA);
                        ps.setString(1,fppCde);
                        ps.setString(2, branch);                                    
                        rs = ps.executeQuery();
                        
                         while(rs.next()){
                            latestGLDebtorsAmt = rs.getDouble("debtors_value");
                        }
                                              
                        
                         if(latestGLDebtorsAmt != entity.getGlDebtors()){
                            entity.setGlAdjustedDebtors(latestGLDebtorsAmt);
                        }
                        
                    }
                   
                 
                    list.add(entity);
                    //connection.close();
                    
                }                
                

            } catch (Exception e) {
                e.printStackTrace();
                throw new SQLException(e);
            } finally {
                ConnectionManager.close(connection);
            }
        }

        System.out.println("list.size() ---> " + list.size());

        return list;
    }

    public static String getActDesc(int actType)
            throws Exception {

        String actDesc = null;
        //query = query.replace("{0}", "");
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = ConnectionManager.getConnection(ConnectionType.BATCH, null);
            ps = connection.prepareStatement(SQLFactory.GET_ACTION_TYP_DESC);
            ps.setInt(1, actType);
            rs = ps.executeQuery();
            while (rs.next()) {
                actDesc = rs.getString("act_desc");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e);
        } finally {
            ConnectionManager.close(connection);
        }
        return actDesc;
    }
    
    /*
    //get hp_acc values on warehouse branches
        public static String getHpAccCount(int actType)
            throws Exception {

        String actDesc = null;
        //query = query.replace("{0}", "");
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = ConnectionManager.getConnection(ConnectionType.BATCH, null);
            ps = connection.prepareStatement(SQLFactory.GET_ACTION_TYP_DESC);
            ps.setInt(1, actType);
            rs = ps.executeQuery();
            while (rs.next()) {
                actDesc = rs.getString("act_desc");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e);
        } finally {
            ConnectionManager.close(connection);
        }
        return actDesc;
    }
    */
    
    
    //Get warehouse value
    public static int getWarehouseValues()
            throws Exception {

        int countWarehouse = 0;
        //List<MonthendEntity> items = new ArrayList<MonthendEntity>();
        //MonthendEntity items = null;
        
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = ConnectionManager.getConnection(ConnectionType.BATCH, null);
            ps = connection.prepareStatement(SQLFactory.GET_WAREHOUSE_BRANCHES);
            rs = ps.executeQuery();
            while (rs.next()) {
                //items = new MonthendEntity();
                countWarehouse = rs.getInt("count");
                
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e);
        } finally {
            ConnectionManager.close(connection);
        }
        return countWarehouse;
    }
    
    
    
    public static final List<GLDetailEntity> getGlDetailDebtorsList(String branch, String type) throws Exception {

        List<GLDetailEntity> list = new ArrayList<GLDetailEntity>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String fppCde = getMeconFpp();
        System.out.println("fpp cde ----> " + fppCde);
        System.out.println("branch cde ---> " + branch);
        try {

            connection = ConnectionManager.getConnection(ConnectionType.BATCH, null);
            if(type.equals("debtors")){
                 System.out.println("Tana 1 ----> " + fppCde);
                ps = connection.prepareStatement(SQLFactory.GET_GL_DETAIL_DEBTORS_LIST);
            }else{
                System.out.println("Tana 2 ----> " + fppCde);
                ps = connection.prepareStatement(SQLFactory.GET_GL_DETAIL_STOCK_LIST);
            }
            
            ps.setString(1,fppCde);
            ps.setString(2, branch);
            rs = ps.executeQuery();
            

            GLDetailEntity entity = null;

            while (rs.next()) {
               entity = new GLDetailEntity();
               entity.setActionType(rs.getInt("act_typ"));
               String actDesc = "test";
               entity.setDescription(actDesc);
               entity.setGlVal(rs.getDouble("value")); 
               list.add(entity);
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            ConnectionManager.close(connection);
        }

    }
    
    public static final List<GLDetailEntity> getInstoreDetailDebtorsList(String branch, String type) throws Exception {

        List<GLDetailEntity> list = new ArrayList<GLDetailEntity>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        GLDetailEntity entity = null;
            
                
        try {

            connection = ConnectionManager.getConnection(ConnectionType.BATCH, "c" + branch);
            if(type.equals("debtors")){
                ps = connection.prepareStatement(SQLFactory.GET_INSTORE_DETAIL_DEBTORS_LIST);
            }else{
                ps = connection.prepareStatement(SQLFactory.GET_INSTORE_DETAIL_STOCK_LIST);
            }
            rs = ps.executeQuery();

            while (rs.next()) {
               entity = new GLDetailEntity();
               entity.setActionType(rs.getInt("act_typ"));
               entity.setDescription(getActDesc(entity.getActionType()));
               entity.setInstoreVal(rs.getDouble("value"));
               list.add(entity);
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            ConnectionManager.close(connection);
        }

    }
    
     public static List<ProdConsScriptsEntity> getProdConsEntities() throws Exception {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<ProdConsScriptsEntity> list = new ArrayList<ProdConsScriptsEntity>();
     
         try {

            connection = ConnectionManager.getConnection(ConnectionType.BATCH, null);
            ps = connection.prepareStatement(SQLFactory.GET_PROD_CONS_ENTITIES);

            rs = ps.executeQuery();
            ProdConsScriptsEntity item = null;
            
            while (rs.next()) {
                item = new ProdConsScriptsEntity();
                item.setProdConsId(rs.getInt("prod_cons_id"));
                item.setProdConsDesc(rs.getString("prod_cons_desc"));
                 item.setProdConsScript(rs.getString("prod_cons_script"));
                /*         
                item.setProdConsError(rs.getString("prod_cons_error"));
                item.setProdConsStartDte(rs.getTimestamp("prod_cons_start_dte"));
                item.setProdConsEndDte(rs.getTimestamp("prod_cons_end_dte"));
                item.setProdConsActive(rs.getBoolean("prod_cons_active"));*/
                list.add(item);
            }
             return list;
         }catch(SQLException sqle){
             sqle.printStackTrace();
            throw new Exception(sqle);
             
         } finally {
            ConnectionManager.close(connection);
        }
       
     }
     
     public List viewMeProdRun(int option, int proc) throws ClassNotFoundException, Exception{
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<String> ret = null;
        
        List<String> fpp = new ArrayList<String>();
        
        List<String> debtors = new ArrayList<String>();
        List<String> creditors = new ArrayList<String>();
        List<String>  cashBook = new ArrayList<String>();
        List<String>  newGl = new ArrayList<String>();
        List<String>  bucketsReport = new ArrayList<String>();
        
        List<String> error = new ArrayList<String>();
        
        List<String> branch = new ArrayList<String>();
        List timeRun = new ArrayList();
        List start = new ArrayList();
        List end = new ArrayList();
        List<String> process = new ArrayList<String>();
        
        MeProdRunData values = new MeProdRunData();
        
        int test = 0;
        
        try{
            connection = ConnectionManager.getConnection(ConnectionType.BATCH, null);
           ps = connection.prepareStatement(SQLFactory.GET_POST_ME_PROCESSES);
           rs = ps.executeQuery();
             while (rs.next()) {
                 
                 fpp.add(rs.getString("fpp_cde"));
                 
                // branch.add(rs.getString("br_cde"));
                if(!branch.contains(rs.getString("br_cde"))){
                    if(rs.getString("br_cde").length() ==1 ){
                          branch.add("000"+rs.getString("br_cde"));
                    }else if(rs.getString("br_cde").length() ==2){
                         branch.add("00"+rs.getString("br_cde"));
                    }else if(rs.getString("br_cde").length() ==3){
                         branch.add("0"+rs.getString("br_cde"));
                    }else{
                          branch.add(rs.getString("br_cde"));
                    }
                } 
                
                
                start.add(rs.getDate("mendstat_start") + " - " + rs.getDate("mendstat_end"));
                //System.out.println("Error number : " + rs.getInt("mendstat_error_id"));
                
                if(rs.getInt("mendstat_error_id") > 0){
                    System.out.println("....Error number is greater than 0.....");
                    error.add("error");
                }else{
                    error.add("ok");
                }
                
                
//                 //end.add(rs.getDate("mendstat_end"));
//                 if(rs.getString("mendstat_process").equalsIgnoreCase("Creditors")){
                     creditors.add(rs.getString("mendstat_process")); 
//                     //test = rs.getInt("mendstat_error_id");
//                     start.add(rs.getDate("mendstat_start") + " - " + rs.getDate("mendstat_end"));
//                 }else if (rs.getString("mendstat_process").equalsIgnoreCase("Debtors")){
//                     debtors.add(rs.getString("mendstat_process"));
//                      start.add(rs.getDate("mendstat_start") + " - " + rs.getDate("mendstat_end"));
//                 }else if(rs.getString("mendstat_process").equalsIgnoreCase("Cashbook")){
//                     cashBook.add(rs.getString("mendstat_process"));
//                      start.add(rs.getDate("mendstat_start") + " - " + rs.getDate("mendstat_end"));
//                 }else if(rs.getString("mendstat_process").equalsIgnoreCase("New GL Extract")){
//                     newGl.add(rs.getString("mendstat_process"));
//                      start.add(rs.getDate("mendstat_start") + " - " + rs.getDate("mendstat_end"));
//                 }else if(rs.getString("mendstat_process").equalsIgnoreCase("Bucket Report Extract")){
//                     bucketsReport.add(rs.getString("mendstat_process"));
//                      
//                 }
                 
                 
             }
             
             
             //values.setDebtors(debtors);
             values.setBr_cde(branch);
             values.setFpp_cde(fpp);
             values.setMendstat_start(start);
             values.setMendstat_end(end);
             for(int i = 0; i <branch.size(); i++ ){
                 
                 if(branch.get(i).length() ==1 ){
                     System.out.print("BRANCH : " + "000"+ branch.get(i) +" ");
                 }else if(branch.get(i).length() ==2){
                     System.out.print("BRANCH : " + "00"+ branch.get(i) +" ");
                 }else if(branch.get(i).length() ==3){
                     System.out.print("BRANCH : " + "0"+ branch.get(i) +" ");
                 }else{
                      System.out.print("BRANCH : " + branch.get(i) +" ");
                 }
                 
             }
        } catch(SQLException ex){
          ex.getStackTrace();
          throw new Exception(ex);
        }
        if(option == 1 && proc == 0){
            ret = branch;
        }else if(option == 0 && proc == 0){
           ret = start;
        }else if(option == 0 && proc == 1){
            ret = end;
        }else if(option == 10 && proc == 10){
            ret = error;
        }
        
        return ret;
     }
     
     
     public static List<ProdConsViewEntity> getProdConsViewList() throws Exception {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<ProdConsViewEntity> list = new ArrayList<ProdConsViewEntity>();
     
         try {

            connection = ConnectionManager.getConnection(ConnectionType.BATCH, null);
            ps = connection.prepareStatement(SQLFactory.GET_PROD_CONS_VIEW_ENTITIES);

            rs = ps.executeQuery();
            ProdConsViewEntity item = null;
            
            while (rs.next()) {
                item = new ProdConsViewEntity();
                item.setProdConsId(rs.getInt("prod_cons_id"));
                item.setProdConsDescr(rs.getString("prod_cons_desc"));
                item.setFppCde(rs.getString("fpp_cde"));
                item.setProdConsError(rs.getString("prod_cons_error"));
                
                if(item.getFppCde() == null){
                    item.setProdConsError("consolidation not run for this period");
                    //System.out.println("test");
                }
                    
                
                item.setProdConsStartDte(rs.getTimestamp("prod_cons_start_dte"));
                item.setProdConsEndDte(rs.getTimestamp("prod_cons_end_dte"));
                list.add(item);
            }
             return list;
         }catch(SQLException sqle){
             sqle.printStackTrace();
            throw new Exception(sqle);
             
         } finally {
            ConnectionManager.close(connection);
        }
       
     }
     
     public static List<String> getGLFppList() throws Exception {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<String> list = new ArrayList<String>();
     
         try {

            connection = ConnectionManager.getConnection(ConnectionType.BATCH, null);
            ps = connection.prepareStatement(SQLFactory.GET_GL_FPP_LIST);

            rs = ps.executeQuery();
            String fppCde = null;
            
            while (rs.next()) {                
                fppCde = rs.getString("fpp_cde");
                list.add(fppCde);
            }
             return list;
         }catch(SQLException sqle){
             sqle.printStackTrace();
            throw new Exception(sqle);
             
         } finally {
            ConnectionManager.close(connection);
        }
       
     }
     
     
     
       public static void saveProdConEntity(ProdConsScriptsEntity prodConsEntity)throws Exception {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {

            connection = ConnectionManager.getConnection(ConnectionType.BATCH, null);
            connection.setAutoCommit(false);
            
            ps = connection.prepareStatement(SQLFactory.UPDATE_PROD_CONS_ENTITIES);
            ps.setString(1, prodConsEntity.getProdConsError());
            
            if (prodConsEntity.getProdConsStartDte() == null)
                ps.setNull(2, Types.TIMESTAMP);
            else
                ps.setTimestamp(2, new Timestamp(prodConsEntity.getProdConsStartDte().getTime()));
            
            if (prodConsEntity.getProdConsEndDte() == null)
                ps.setNull(3, Types.TIMESTAMP);
            else
                ps.setTimestamp(3, new Timestamp(prodConsEntity.getProdConsEndDte().getTime()));
           
            ps.setInt(4, prodConsEntity.getProdConsId());
            ps.executeUpdate();
            
            connection.commit();
            
        }catch(SQLException sqle){
            ConnectionManager.rollback(connection);
            sqle.printStackTrace();
            throw new Exception(sqle);
            
        }finally{
              ConnectionManager.close(connection);
        }
       }
        
        
 

}
