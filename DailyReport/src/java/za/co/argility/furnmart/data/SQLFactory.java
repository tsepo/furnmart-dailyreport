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
    
    public static final String GET_MONTHEND_DETAILS =      "select branch, {1},  br_is_whs from ( \n" +
                                                                "SELECT branch.br_cde branch, COUNT(*) as {0}, br_is_whs \n" + 
                                                                "FROM {1} \n" +
                                                                "join branch using(br_cde) \n" +
                                                                "where fpp_cde = '{2}' \n" +
                                                                "and br_active = true  \n" +
                                                                "and br_is_ceres = false  \n" + 
                                                                "and br_is_central = false  \n" + 
                                                                "and br_is_merch = false  \n" +
                                                                "group by branch.br_cde  \n" +
                                                                "UNION  \n" +
                                                                "select branch.br_cde branch, 0 as {0},  br_is_whs \n" + 
                                                                "from branch  \n" +
                                                                "WHERE br_active = true \n" +
                                                                "and br_is_ceres = false  \n" + 
                                                                "and br_is_central = false  \n" + 
                                                                "and br_is_merch = false  \n" +
                                                                "and not exists(select * from {1} \n" + 
                                                                "where {1}.br_cde = branch.br_cde \n" +
                                                                "and fpp_cde = '{2}') \n" +
                                                                "group by branch.br_cde) as xyz \n" +
                                                                "order by branch";
    
    public static final String GET_REPLICATION_BRANCH_LIST = "select br_cde \n" +
                                                                "from checkpoint \n" +
                                                                "join branch_is_replicate_locked using (br_cde) \n" +
                                                                "join branch using (br_cde)  \n" +
                                                                "where br_active = true \n" +
                                                                "and br_is_merch = false \n" +
                                                                "and br_is_central = false \n" +
                                                                "and br_central_brn = (select br_cde from br_prof) \n" +
                                                                "\n" +
                                                                "order by br_cde";
    
    public static final String GET_MONTHEND_BRANCH_LIST = "SELECT branch.br_cde, COUNT(*) as debtors FROM central_account \n" +
                                                           "join branch using(br_cde) \n" +
                                                           " where fpp_cde = '${0}' \n" +
                                                           "and br_active = true  \n" +
                                                           "and br_is_ceres = false \n" +
                                                           "and br_is_central = false \n" +
                                                           "and br_is_merch = false  \n" +
                                                           "group by branch.br_cde";
    
    
    public static final String GET_MECONS_FPP_CDE = "select fpp_cde  \n" +
                                                                "from br_prof";
                                                              
    
    
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
    
    public static final String GET_NETWORK_STATISTICS_DATA = "SELECT branch_code, branch_desc, network_available, last_evaluated " +
                                                             "FROM network_statistics WHERE network_available = ? " +
                                                             "ORDER BY branch_code";
    
    public static final String DAILY_BI_EXTRACTS_HISTORY_DATA = " SELECT daily_bi_extracts_hist_id, \n" +
                                                                " br_cde, \n" +
                                                                " fpp_cde, \n" +
                                                                " process_type, \n" +
                                                                " daily_bi_extracts_type.extract_type, \n" +
                                                                " extract_desc, \n" +
                                                                " is_active, \n" +
                                                                " start_time, \n" +
                                                                " end_time, \n" +
                                                                " COALESCE(daily_bi_extracts_errors_id, 0) AS daily_bi_extracts_errors_id, \n" +
                                                                " error_ts, \n" +
                                                                " error_stack_trace\n" +
                                                                "\n" +
                                                                " FROM daily_bi_extracts_hist \n" +
                                                                "\n" +
                                                                " JOIN daily_bi_extracts_type USING (extract_type) \n" +
                                                                " LEFT OUTER JOIN daily_bi_extracts_errors ON (daily_bi_extracts_errors_id = error_id) \n" +
                                                                "\n" +
                                                                " WHERE process_type = ? \n" +
                                                                " AND {0} \n" +
                                                                " ORDER BY br_cde, fpp_cde, daily_bi_extracts_hist_id, extract_type, start_time";
    
    public static final String GET_DATE_EXTRACT_FILES_LAST_SENT = "SELECT br_cde, last_sent_time "
            + "FROM daily_bi_extracts_sent_files "
            //+ "WHERE AGE(last_sent_time) < '1 hour' "
            + "ORDER BY br_cde";
    
    public static final String GET_BRANCH_ROLLED_FPP_CODE = "SELECT fpp_cde FROM br_prof";
    
    public static final String GET_BRANCH_DESCRIPTION = "SELECT br_desc FROM branch WHERE br_cde = ?";
    
}
