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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.argility.furnmart.entity.GlobalSettings;
import za.co.argility.furnmart.util.Log;
import za.co.argility.furnmart.util.WebPages;

/**
 *
 * @author tmaleka
 */
public class GenericServlet extends HttpServlet {

    protected GlobalSettings settings = null;
    static {
        Log.setLogger(GenericServlet.class);
    }
    
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
         // log the client's header details i.e. IP ADDRESs, AGENT etc.
        logClientDetails(request, response); 
        
        // process global session settings
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
     * Serializes to the session scope
     * @param request
     * @param data 
     */
    protected void saveSession(HttpServletRequest request, Object data) {
        saveSession(request, data, data.getClass().getName());
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
    
    protected Object getSessionData(HttpServletRequest request, Class c) {
        return getSessionData(request, c.getName());
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
        
        Log.info("... inside logClientDetails()..."); 
        Log.info("HOSTNAME: " + WebPages.BASE_APP_URL);
        
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
     * Processes the current session
     * settings
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    protected void processGlobalSettings(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        settings = (GlobalSettings)getSessionData(request, SessionAttribute.GLOBAL_SETTINGS_TAG);
        if (settings == null)
            settings = new GlobalSettings();
        
        String requestedUrl = request.getRequestURL().toString();
        if (requestedUrl == null)
            requestedUrl = WebPages.BASE_APP_URL + "/overview";
        
        settings.setServletName(requestedUrl);
        
        saveSession(request, settings, SessionAttribute.GLOBAL_SETTINGS_TAG);
        
    }
    
    protected boolean sendInternalRedirect(HttpServletRequest request, HttpServletResponse response, 
            String urlPattern) throws IOException {
        
        String contextPath = request.getContextPath();
        response.sendRedirect(contextPath + urlPattern);
        return true;
    }
    
}
