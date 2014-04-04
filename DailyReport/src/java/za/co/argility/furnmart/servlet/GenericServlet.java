/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.servlet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.argility.furnmart.entity.GlobalSettings;
import za.co.argility.furnmart.util.WebPages;

/**
 *
 * @author tmaleka
 */
public class GenericServlet extends HttpServlet {

    protected GlobalSettings settings = null;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
         // log the client's header details i.e. IP ADDRESs, AGENT etc.
        logClientDetails(request, response); 
        
        // check if the user settings are serialised
        processGlobalSettings(request, response);
        
    }
    
    /**
     * Serialises the data to the current 
     * session
     * @param request
     * @param data
     * @param attribute 
     */
    protected void saveSession(HttpServletRequest request, Object data, String attribute) {
        HttpSession session = request.getSession(true);
        session.setAttribute(attribute, data);
    }
    
    /**
     * Obtains any serialised data from this
     * current session
     * 
     * @param request
     * @param attribute
     * @return 
     */
    protected Object getSessionData(HttpServletRequest request, String attribute) {
        HttpSession session = request.getSession(true);
        return session.getAttribute(attribute);
    }
    
    /**
     * Redirects to an error page
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    protected void showErrorPage(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect(WebPages.ERROR_PAGE); 
    }
    
    /**
     * Logs the client details to a file
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    protected void logClientDetails(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        if (WebPages.BASE_APP_URL.contains("localhost"))
            return;
        
        String visitorsLog = "/home/ucsretail/tsepo/daily_report_logging/visitors.log";
        File log = new File(visitorsLog);
        
        if (!log.exists()) {
            log.createNewFile();
        }
        
        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS").format(new Date());
        
        String line = "Request from IP: [" + ipAddress + "] at time [" + date + "] using [" + userAgent + "].";
        FileWriter writer = new FileWriter(log, true);
        writer.append(line + "\r\n");
        writer.close();
        
    }

    /**
     * Processes the global settings
     * 
     * @param request
     * @param response 
     */
    private void processGlobalSettings(HttpServletRequest request, HttpServletResponse response) {
        
        settings = (GlobalSettings)getSessionData(request, SessionAttribute.GLOBAL_SETTINGS_TAG);
        if (settings == null) {
            settings = new GlobalSettings();
        }
        
        // the auto refresh option
        String autoRefresh = request.getParameter("autoRefresh");
        if (autoRefresh != null) {
            autoRefresh = autoRefresh.toLowerCase();
            settings.setAutoRefresh(Boolean.parseBoolean(autoRefresh)); 
        }
        
        // set the refresh interval 
        String refreshInterval = request.getParameter("refreshInterval");
        if (refreshInterval != null) {
            settings.setRefreshInterval(Long.parseLong(refreshInterval.trim())); 
        }
        
        // set the servlet requested
        String requestingUrl = request.getRequestURL().toString();
        if (requestingUrl != null && 
                !requestingUrl.contains(".jsp") && !requestingUrl.contains(".html")) {
            settings.setServletName(requestingUrl); 
        }
        
        else 
            settings.setServletName(WebPages.BASE_APP_URL); 
        
        // serialise the settings to the current session
        saveSession(request, settings, SessionAttribute.GLOBAL_SETTINGS_TAG); 
        
    }
    
    protected void displayWhatsChangedInfo(HttpServletRequest request, HttpServletResponse response) {
        
    }

}
