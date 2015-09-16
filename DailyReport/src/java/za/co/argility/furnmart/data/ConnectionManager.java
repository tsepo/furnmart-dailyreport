/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author tmaleka
 */
public class ConnectionManager {
    
    private static final String DRIVER_CLASS = "org.postgresql.Driver";
    private static final String CONNECTION_URL = "jdbc:postgresql://{0}/{1}";
    public static final String DAILY_REPORTING_DB = "daily_reporting";
    
    private static final String USERNAME = "ucsretail";
    
    public static Connection getConnection(int connectionType, String dbName) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Class.forName(DRIVER_CLASS);
        String connectionUrl = null;
        
        if (connectionType == ConnectionType.CENTRAL && dbName == null)
            connectionUrl = CONNECTION_URL.replace("{0}", "c9901.fm.co.za").replace("{1}", "c9901");
        
        else if (connectionType == ConnectionType.CENTRAL && dbName != null)
                connectionUrl = CONNECTION_URL.replace("{0}", "c9901.fm.co.za").replace("{1}", dbName);
                
        else if (connectionType == ConnectionType.INSTORE)
            connectionUrl = CONNECTION_URL.replace("{0}", "c9901.fm.co.za").replace("{1}", dbName);
        
        else if (connectionType == ConnectionType.BATCH &&  dbName == null)
            connectionUrl = CONNECTION_URL.replace("{0}", "c9910.fm.co.za").replace("{1}", "mecons");
        else if (connectionType == ConnectionType.BATCH &&  dbName != null)
            connectionUrl = CONNECTION_URL.replace("{0}", "c9910.fm.co.za").replace("{1}", dbName);
        
        
        connection = DriverManager.getConnection(connectionUrl, USERNAME, USERNAME);
        
        return connection;
    }
    
    
    public static void close(Connection connection) {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void rollback(Connection connection) {
        try {
            if (connection != null && connection.isClosed() == false &&
                    connection.getAutoCommit() == false)
                connection.rollback();
        }
        catch (Exception e) {}
    }
    
}
