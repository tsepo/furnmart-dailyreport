/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.argility.furnmart.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import za.co.argility.furnmart.data.ConnectionManager;
import za.co.argility.furnmart.data.ConnectionType;
import za.co.argility.furnmart.entity.ProdConsRunEntity;

/**
 *
 * @author rnaidoo
 */
public class JdbcInsertConsRunData {

    public JdbcInsertConsRunData(ProdConsRunEntity prodConsRunEntity) throws SQLException {

        Connection conn = null;
       
        try {
            conn = ConnectionManager.getConnection(ConnectionType.BATCH, null);
            Statement statement = conn.createStatement();
            statement.executeUpdate("insert into prod_cons_run values ('"+prodConsRunEntity.getProdConsId()+"','"+prodConsRunEntity.getFppCde()+"', '"+prodConsRunEntity.getProdConsError()+
                    "','"+prodConsRunEntity.getProdConsStartDte()+"','"+prodConsRunEntity.getProdConsEndDte()+"')");                                     
            
        } catch (Exception e) {
            e.printStackTrace();

        } finally {            
            ConnectionManager.close(conn);
        }

        

    }

}
