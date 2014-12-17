/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.argility.furnmart.util.Log;
import za.co.argility.furnmart.util.WebPages;

/**
 *
 * @author rnaidoo
 */
public class MonthEndConsolidationServlet extends GenericServlet{
    
    
     protected PrintWriter out = null;

 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         super.doGet(request, response); 
       
        if (request.getParameter("tab") != null &&
                request.getParameter("tab").equals("cons")) {
              System.out.println("I am in Consolidation page.");
              Log.info("... montendConsolidations ...");       
        if (response.isCommitted())
            return;
        
       
        
        out = new PrintWriter(response.getOutputStream());
        runProcesses(request, response);
        
              response.sendRedirect(WebPages.MONTHEND_CONSOLIDATION_PAGE);
              
          }
         
        
        
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void runProcesses(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        out.println("Running clear PWC extracts ");
        out.println("Started process, please wait...\r\n");
        
        List<String> commands = new ArrayList<String>();
        commands.add("/home/ucsretail/bin/clearPWCNotifications.sh");
       
        
        ProcessBuilder builder = new ProcessBuilder(commands);
        builder.start();
        
        out.println("Process started.");
        out.flush();
        
    }
    
}
