/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.data;

/**
 *
 * @author tmaleka
 */
public interface SQLFactory {
    
    public static final String SELECT_NETWORK_OVERVIEW_DATA = "SELECT COUNT(*), "
            + "(CASE WHEN network_available IS TRUE THEN 'AVAILABLE' ELSE 'NOT AVAILABLE' END) "
            + "FROM network_statistics "
            + "GROUP BY (CASE WHEN network_available IS TRUE THEN 'AVAILABLE' ELSE 'NOT AVAILABLE' END)";
    
    public static final String SELECT_REPLICATION_STATUS_OVERVIEW_DATA = 
            "SELECT (CASE WHEN process IS NULL OR process = '' THEN 'NOT REPLICATING' \n" +
            "ELSE process END), count(*) \n" +
            "FROM branch_is_replicate_locked \n" + 
            "JOIN (SELECT br_cde FROM branch WHERE br_active "
            + "AND br_is_ceres IS FALSE "
            + "AND br_is_central IS FALSE "
            + "AND br_is_cco IS FALSE "
            + ") AS branch USING (br_cde) \n" +
            "GROUP BY process ORDER BY process";
}
