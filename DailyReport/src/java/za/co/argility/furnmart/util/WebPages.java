/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.argility.furnmart.util;

/**
 *
 * @author tmaleka
 */
public interface WebPages {

    public static final String BASE_APP_URL = "http://c9901.fm.co.za/DailyReport";
    //public static final String BASE_APP_URL = "http://localhost:8080/DailyReport";
    //public static final String MONTHEND_URL = "http://localhost:8080/DailyReport";

    public static final String STARTUP_PAGE = BASE_APP_URL + "/index.jsp";
    public static final String OVERVIEW_PAGE = BASE_APP_URL + "/overview.jsp";
    public static final String REPLICATION_PAGE = BASE_APP_URL + "/replication.jsp";
    public static final String NETWORK_PAGE = BASE_APP_URL + "/network.jsp";
    public static final String DAILY_BI_EXTRACTS_PAGE = BASE_APP_URL + "/daily-bi-extracts.jsp";
    public static final String ITC_700_EXTRACTS_VERIFIER_PAGE = BASE_APP_URL + "/itc700extractsVerifier.jsp";
    public static final String DISK_SPACE_PAGE = BASE_APP_URL + "/disk-space.jsp";
    public static final String MONTHEND_PROD_PAGE = BASE_APP_URL + "/monthend-production.jsp";
    public static final String ERROR_PAGE = BASE_APP_URL + "/errorpages/404.html";
    //public static final String MONTHEND_PAGE = BASE_APP_URL + "/errorpages/404.html";
}
