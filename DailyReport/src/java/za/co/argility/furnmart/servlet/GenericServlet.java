/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.argility.furnmart.util.WebPages;

/**
 *
 * @author tmaleka
 */
public class GenericServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
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
}
