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
            " SELECT (CASE WHEN process IS NULL OR process = '' THEN 'NOT REPLICATING' \n" +
            " ELSE process END), count(*) \n" +
            " FROM branch_is_replicate_locked \n" + 
            " JOIN branch USING (br_cde) " +
            " JOIN checkpoint USING (br_cde) "
            + "WHERE br_is_ceres IS FALSE "
            + "AND br_active IS TRUE "
            + "AND br_is_central IS FALSE "
            + "AND br_is_merch IS FALSE "
            + "AND br_central_brn = (select br_cde from br_prof) "
            + "GROUP BY process ORDER BY process";
    
    public static final String GET_REPLICATION_DETAILS = "select br_cde, \n" +
                                                        "br_desc, \n" +
                                                        "check_audit_up_to as audit, \n" +
                                                        "check_replicate_up_to as replicate, \n" +
                                                        "(check_audit_up_to - check_replicate_up_to) as diff, \n" +
                                                        "is_locked, \n" +
                                                        "br_lock_date, \n" +
                                                        "br_unlock_date, \n" +
                                                        "process\n" +
                                                        "\n" +
                                                        "from checkpoint \n" +
                                                        "join branch_is_replicate_locked using (br_cde) \n" +
                                                        "join branch using (br_cde)  \n" +
                                                        "where br_active = true \n" +
                                                        "and br_is_merch = false \n" +
                                                        "and br_is_central = false \n" +
                                                        "and br_central_brn = (select br_cde from br_prof) \n" +
                                                        "\n" +
                                                        "order by br_cde";
    
    public static final String GET_REPLICATION_BRANCH_LIST = "select br_cde\n" +
                                                                "from checkpoint \n" +
                                                                "join branch_is_replicate_locked using (br_cde) \n" +
                                                                "join branch using (br_cde)  \n" +
                                                                "where br_active = true \n" +
                                                                "and br_is_merch = false \n" +
                                                                "and br_is_central = false \n" +
                                                                "and br_central_brn = (select br_cde from br_prof) \n" +
                                                                "\n" +
                                                                "order by br_cde";
    
    public static final String SEARCH_REPLICATION_DATA = "select br_cde, \n" +
                                                        "br_desc, \n" +
                                                        "check_audit_up_to as audit, \n" +
                                                        "check_replicate_up_to as replicate, \n" +
                                                        "(check_audit_up_to - check_replicate_up_to) as diff, \n" +
                                                        "is_locked, \n" +
                                                        "br_lock_date, \n" +
                                                        "br_unlock_date, \n" +
                                                        "process\n" +
                                                        "\n" +
                                                        "from checkpoint \n" +
                                                        "join branch_is_replicate_locked using (br_cde) \n" +
                                                        "join branch using (br_cde)  \n" +
                                                        "where br_active = true \n" +
                                                        "and br_is_merch = false \n" +
                                                        "and br_is_central = false \n" +
                                                        "and br_central_brn = (select br_cde from br_prof) \n" +
                                                        "and br_cde ilike '%{0}%' \n" +
                                                        "and process ilike '%{1}%' \n" +
                                                        "order by br_cde";
    
    public static final String GET_PROCESS_LIST = "SELECT distinct(process) FROM branch_is_replicate_locked";
    
}
