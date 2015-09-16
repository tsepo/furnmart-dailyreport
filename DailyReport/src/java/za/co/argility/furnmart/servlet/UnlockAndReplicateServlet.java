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

/**
 *
 * @author tmaleka
 */
public class UnlockAndReplicateServlet extends GenericServlet {

    
    protected PrintWriter out = null;

 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        super.doGet(request, response); 
        
        if (response.isCommitted())
            return;
        
        String branchCode = request.getParameter("branch");
        if (branchCode == null || branchCode.length() != 4)
            return;
        
        out = new PrintWriter(response.getOutputStream());
        processUnlockAndReplicateRequest(branchCode, request, response);
        
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void processUnlockAndReplicateRequest(String branchCode, HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        out.println("Running unlockAndReplicate on c9901 for branch " + branchCode + "...");
        out.println("Started process, please wait...\r\n");
        
        List<String> commands = new ArrayList<String>();
        commands.add("unlockAndReplicate");
        commands.add(branchCode);
        
        ProcessBuilder builder = new ProcessBuilder(commands);
        builder.start();
        
        out.println("Process started.");
        out.flush();
        
    }

    

}
