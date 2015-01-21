/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import za.co.argility.furnmart.data.ConnectionManager;
import za.co.argility.furnmart.data.ConnectionType;
import za.co.argility.furnmart.entity.ProdConsRunEntity;

/**
 *
 * @author rnaidoo
 */
public class JdbcDeleteConsRunData {
    
      Connection conn = null;
     public JdbcDeleteConsRunData(int prodConsId, String fppCde) throws SQLException {
        try {
            conn = ConnectionManager.getConnection(ConnectionType.BATCH, null);
            Statement statement = conn.createStatement();
            statement.executeUpdate("delete from prod_cons_run where prod_cons_id = '"+prodConsId+"' and fpp_cde = '"+fppCde+"' ");                                      
            
        } catch (Exception e) {
            e.printStackTrace();

        } finally {            
            ConnectionManager.close(conn);
        }
     }

    
}
