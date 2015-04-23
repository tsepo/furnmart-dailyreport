/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.argility.furnmart.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
    public static final String MONTHEND_OVERVIEW_PAGE = BASE_APP_URL + "/monthend-overview.jsp";
    public static final String REPLICATION_PAGE = BASE_APP_URL + "/replication.jsp";
    public static final String NETWORK_PAGE = BASE_APP_URL + "/network.jsp";
    public static final String DAILY_BI_EXTRACTS_PAGE = BASE_APP_URL + "/daily-bi-extracts.jsp";
    public static final String ITC_700_EXTRACTS_VERIFIER_PAGE = BASE_APP_URL + "/itc700extractsVerifier.jsp";
    public static final String DISK_SPACE_PAGE = BASE_APP_URL + "/disk-space.jsp";
    public static final String MONTHEND_PROD_PAGE = BASE_APP_URL + "/monthend-production.jsp";
    public static final String MONTHEND_STATEMENTS = BASE_APP_URL + "/monthend-statements.jsp";
    public static final String MONTHEND_PROCESSES_PAGE = BASE_APP_URL + "/monthend-processes.jsp";
    public static final String GL_MAIN_PAGE = BASE_APP_URL + "/gl-main.jsp";
    public static final String GL_DETAIL_DEBTORS_PAGE = BASE_APP_URL + "/gl-detail-debtors.jsp";
    public static final String GL_DETAIL_STOCK_PAGE = BASE_APP_URL + "/gl-detail-stock.jsp";
    public static final String MONTHEND_CONSOLIDATION_PAGE = BASE_APP_URL + "/monthend-consolidations.jsp";
    public static final String MONTHEND_VIEW_CONSOLIDATION_PAGE = BASE_APP_URL + "/monthend-view-consolidations.jsp";
    public static final String ERROR_PAGE = BASE_APP_URL + "/errorpages/error-occured.jsp";
    
    /**
     * Gets the local host name 
     * 
     * @return 
     */
    private static String getHostName() {
        
        final String PROPERTIES_FILE_NAME = "/home/ucsretail/central/envproperties";
        File propertiesFile = new File(PROPERTIES_FILE_NAME);
        
        String hostname = "localhost:8080";
        
        if (!propertiesFile.exists())
            return hostname;
        
        else {
            try {
                
                BufferedReader reader = new BufferedReader(
                        new FileReader(propertiesFile));
                
                while (reader.ready()) {
                    String line = reader.readLine();
                    if (line.startsWith("HOSTNAME")) {
                        hostname = line.substring(line.indexOf("=") + 1);
                        break;
                    }
                }
                
                reader.close();
                return hostname;
            }
            
            catch (IOException e) {
                return hostname;
            }
        }
        
    }
    
}
