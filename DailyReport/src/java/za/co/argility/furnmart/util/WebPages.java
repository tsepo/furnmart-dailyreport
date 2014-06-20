/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.argility.furnmart.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tmaleka
 */

public class WebPages {
    
    public static final String BASE_APP_URL 
            = "http://" + getHostName() + "/DailyReport";
    

    public static final String STARTUP_PAGE = BASE_APP_URL + "/index.jsp";
    public static final String OVERVIEW_PAGE = BASE_APP_URL + "/overview.jsp";
    public static final String REPLICATION_PAGE = BASE_APP_URL + "/replication.jsp";
    public static final String NETWORK_PAGE = BASE_APP_URL + "/network.jsp";
    public static final String DAILY_BI_EXTRACTS_PAGE = BASE_APP_URL + "/daily-bi-extracts.jsp";
    public static final String ITC_700_EXTRACTS_VERIFIER_PAGE = BASE_APP_URL + "/itc700extractsVerifier.jsp";
    public static final String DISK_SPACE_PAGE = BASE_APP_URL + "/disk-space.jsp";
    public static final String MONTHEND_PROD_PAGE = BASE_APP_URL + "/monthend-production.jsp";
    public static final String ERROR_PAGE = BASE_APP_URL + "/errorpages/error-occured.jsp";
    
    /**
     * Gets the local host name
     * 
     * @return 
     */
    private static String getHostName() {
        
        InetAddress address = null;
        try {
            
            address = InetAddress.getLocalHost();
        } 
        
        catch (UnknownHostException ex) {
            Logger.getLogger(WebPages.class.getName()).log(Level.SEVERE, null, ex);
            return "localhost:8080"; 
        }
        
        String hostName = address.getCanonicalHostName();
        
        if (hostName == null || hostName.isEmpty() || 
                hostName.contains("Company.net")) 
            return "localhost:8080";
        else
            return hostName + ":8080";
        
    }
    
}
