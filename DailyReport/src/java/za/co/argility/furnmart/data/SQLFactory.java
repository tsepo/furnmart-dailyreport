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
    
    public static final String GET_MONTHEND_DETAILS =      "select branch, {1},  br_desc from ( \n" +
                                                                "SELECT branch.br_cde branch, COUNT(*) as {0}, br_desc \n" + 
                                                                "FROM {1} \n" +
                                                                "join branch using(br_cde) \n" +
                                                                "where fpp_cde = '{2}' \n" +
                                                                "and br_active = true  \n" +
                                                                "and br_is_ceres = false  \n" + 
                                                                "and br_is_central = false  \n" + 
                                                                "and br_is_merch = false  \n" +
                                                                "group by branch.br_cde  \n" +
                                                                "UNION  \n" +
                                                                "select branch.br_cde branch, 0 as {0},  br_desc \n" + 
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
    
    public static final String GET_MONTHEND_BRANCH_LIST = "SELECT br_cde \n" +
                                                           "from branch \n" +                                                           
                                                           "where br_active = true  \n" +
                                                           "and br_is_ceres = false \n" +
                                                           "and br_is_central = false \n" +
                                                           "and br_is_merch = false  \n" +   
                                                           "and br_cde in ('0002','0003') \n" + 
                                                           "group by branch.br_cde order by branch.br_cde";
    
    
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
    
     public static final String GET_MISSING_GL_SUB_TYPES = "select distinct gl_tran_map.act_typ, gl_tran_map.sub_typ, act_desc \n" + 
                                                          "from gl_tran_map \n" +
                                                          "join action_typ using (act_typ) \n" + 
                                                          "join new_gl_tran_ext on new_gl_tran_ext.act_typ = gl_tran_map.act_typ \n" + 
                                                          "and new_gl_tran_ext.sub_typ = gl_tran_map.sub_typ \n" +
                                                          "where not exists (select * from gl_sub_type_map  \n" + 
                                                          "where gl_sub_type_map.act_typ = gl_tran_map.act_typ \n" +
                                                          "and gl_sub_type_map.gl_sub_type = gl_tran_map.sub_typ)";
     
     public static final String GET_ME_PROCESSES = "select prod_cde , prod_class_desc , prod_method , prod_obj_jndi_name \n" +  
                                                            "from prod_class join prod_run using (prod_cde) \n" +
                                                            "where prod_active and prod_type = 'ME'";
     
     public static final String GET_MISSING_GL_MAP_ACT_TYPES = "select distinct new_gl_tran_ext.act_typ, act_desc \n" + 
                                                               "from new_gl_tran_ext \n" +
                                                               "join action_typ using (act_typ) \n" +
                                                               "where not exists (select * from gl_tran_map \n" +   
                                                               "where gl_tran_map.act_typ =  new_gl_tran_ext.act_typ)"; 

     public static final String GET_BRANCH_ACTION_TYPES =       "select distinct(act_typ)  \n" + 
                                                                "from audit join action_typ using (act_typ) \n " +
                                                                "where fpp_cde = '201406' \n " +
                                                                "and act_is_fin";
     
     public static final String GET_GL_TRAN_MAP_ACTION_TYPES = "select distinct(act_typ) from gl_tran_map"; 
     
     
     public static final String GET_ACTION_TYP_DESC = "select act_desc from action_typ where act_typ = ?"; 
     
     public static final String GET_GL_DEBTORS_DATA =  "select sum(value) as debtors_value from new_gl_tran_ext join branch using (br_cde) \n " +
                                                        "where (debit_code = '500400' or debit_code = '500401' or debit_code = '500402' \n " +
                                                        "or debit_code = '800011' or debit_code = '800012' or debit_code = '800013' \n " +
                                                        "or debit_code = '800014' or credit_code = '500400' or credit_code = '500401' \n " +
                                                        "or credit_code = '500402' or credit_code = '500403' or credit_code = '500404' \n " +
                                                        "or credit_code = '800010' or credit_code = '800011' or credit_code = '800012' \n " +
                                                        "or credit_code = '800013' or credit_code = '800014' ) and fpp_cde = ? \n " +
                                                        "and br_cde = ? \n " +
                                                        "group by br_cde"; 
      
       
     
               
     public static final String GET_GL_STOCK_DATA = "select sum(value) as stock_value from ( \n" +
                                                    "select grp_cde,br_cde,act_typ,sub_typ,typ_desc,fpp_cde,debit_code,credit_code,sum(value) as value from ( \n" +
                                                    "SELECT grp_cde,br_cde,act_typ,sub_typ,typ_desc,fpp_cde,debit_code,credit_code,value as value \n" +
                                                    "from new_gl_tran_ext join branch using (br_cde) \n" +
                                                    "where (debit_code = '200040' or credit_code = '200040'  or debit_code = '500330' \n" +
                                                     "or credit_code = '500330') \n" +
                                                    "and fpp_cde = ? \n" +
                                                    "and br_cde = ? \n " +
                                                    "and not (sub_typ IN (11,12) and act_typ in ('77065','75005'))) as try \n" +
                                                    "where not (act_typ = '70045' and sub_typ not in (0,1)) \n" +
                                                    "group by grp_cde,br_cde,act_typ,sub_typ,typ_desc,fpp_cde,debit_code,credit_code \n" +
                                                    "order by grp_cde,br_cde,act_typ,sub_typ,typ_desc,fpp_cde,debit_code,credit_code) as try";
     
     public static final String GET_INSTORE_DEBTORS_DATA = "SELECT \n" +
                                                    "sum(hptran_amt) as debtors_value \n" + 
                                                    "FROM audmth \n" +
                                                    "JOIN hp_doc USING(aud_id) \n" + 
                                                    "JOIN hp_tran USING(hpdoc_id) \n" + 
                                                    "JOIN audit USING(aud_id) \n" +
                                                    "JOIN fin_proc_per USING(fpp_cde) \n" +
                                                    "JOIN action_typ USING(act_typ) \n" +
                                                    "WHERE hpdoc_is_fin IS TRUE \n" +
                                                    "GROUP BY fpp_cde \n" ;
     public static final String GET_INSTORE_STOCK_DATA = "SELECT SUM (stran_qty :: NUMERIC * stran_unit_cos_cost) as stock_value \n" +
                                                    "FROM audmth  \n" +
                                                    "JOIN audit USING(aud_id)  \n" +
                                                    "JOIN fin_proc_per USING(fpp_cde)  \n" +
                                                    "JOIN action_typ USING(act_typ)  \n" +
                                                    "JOIN sku_tran USING(aud_id)  \n" +
                                                    "WHERE stran_is_fin IS TRUE";
     
     public static final String GET_GL_DETAIL_DEBTORS_LIST = "select act_typ, sum(value) as value from new_gl_tran_ext join branch using (br_cde) \n" +
                                                                "where (debit_code = '500400' or debit_code = '500401' or debit_code = '500402' \n" +
                                                                "or debit_code = '800011' or debit_code = '800012' or debit_code = '800013' \n" +
                                                                "or debit_code = '800014' or credit_code = '500400' or credit_code = '500401' \n" +
                                                                "or credit_code = '500402' or credit_code = '500403' or credit_code = '500404' \n" +
                                                                "or credit_code = '800010' or credit_code = '800011' or credit_code = '800012' \n" +
                                                                "or credit_code = '800013' or credit_code = '800014' ) \n" + 
                                                                "and fpp_cde = ?  \n" +
                                                                "and br_cde = ? \n" +
                                                                "group by act_typ \n" +
                                                                "order by act_typ; ";

     
      public static final String GET_INSTORE_DETAIL_DEBTORS_LIST = "SELECT \n" + 
                                                                "act_typ, sum(hptran_amt) as value \n" +  
                                                                "FROM audmth \n" +
                                                                "JOIN hp_doc USING(aud_id) \n" + 
                                                                "JOIN hp_tran USING(hpdoc_id) \n" +
                                                                "JOIN audit USING(aud_id) \n" +
                                                                "JOIN fin_proc_per USING(fpp_cde) \n" + 
                                                                "JOIN action_typ USING(act_typ) \n" +
                                                                "WHERE hpdoc_is_fin IS TRUE \n" +
                                                                "GROUP BY act_typ \n" +
                                                                "order by act_typ;";
      
    public static final String GET_INSTORE_DETAIL_STOCK_LIST = "SELECT act_typ, sum(stran_qty :: NUMERIC * stran_unit_cos_cost) as value \n" +
                                                                   "FROM audmth  \n" +
                                                                   "JOIN audit USING(aud_id) \n" +  
                                                                    "JOIN fin_proc_per USING(fpp_cde) \n" +  
                                                                    "JOIN action_typ USING(act_typ) \n" +
                                                                    "JOIN sku_tran USING(aud_id) \n" +
                                                                    "WHERE stran_is_fin IS TRUE \n" +
                                                                    "group by act_typ \n" +
                                                                    "order by act_typ;";
    
    public static final String GET_GL_DETAIL_STOCK_LIST =  "SELECT act_typ, sum( value ) as value from new_gl_tran_ext join branch using (br_cde) \n" +
                                                            "where (debit_code = '200040' or credit_code = '200040'  or debit_code = '500330' \n" +
                                                            "or credit_code = '500330') \n" +
                                                            "and fpp_cde = ? \n" +
                                                            "and br_cde = ? \n" +
                                                            "and not (sub_typ IN (11,12) and act_typ in ('77065','75005')) \n" + 
                                                            "and not (act_typ = '70045' and sub_typ not in (0,1)) group by act_typ \n" + 
                                                            "order by act_typ ;";
    
    public static final String GET_GL_SUMMARY_DETS = "SELECT br_cde, fpp_cde, gl_debtors, gl_instore_debtors, gl_stock, gl_instore_stock \n" +
                                                     "FROM new_gl_balancing \n" +
                                                     "WHERE fpp_cde = ? \n" +
                                                     "AND br_cde = ? ;" ;
     
}
