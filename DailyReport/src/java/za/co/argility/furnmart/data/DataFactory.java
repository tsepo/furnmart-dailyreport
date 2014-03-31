/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.data;

import java.sql.*;
import java.util.*;

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
}
