/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.argility.furnmart.data.DataFactory;
import za.co.argility.furnmart.entity.ReplicationEntity;
import za.co.argility.furnmart.entity.ReplicationSearchEntity;
import za.co.argility.furnmart.servlet.helper.ReplicationData;
import za.co.argility.furnmart.util.WebPages;

/**
 *
 * @author tmaleka
 */
public class ReplicationServlet extends GenericServlet {

  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        super.doGet(request, response); 
        
        if (response.isCommitted()) {
            return;
        }
        
        ReplicationData data = (ReplicationData)getSessionData(request, SessionAttribute.REPLICATION_DATA_TAG);
        if (data == null) {
            data = new ReplicationData();
        }
        
        try {
            
            // get the details for replication
            data.setReplicationDetails(DataFactory.getReplicationDetails());
            
            // get the replication branch list
            data.setReplicationBranchList(DataFactory.getReplicationBranchList()); 
            
            // get the processes
            data.setProcesses(DataFactory.getReplicationProcesses()); 
            
            // save the data to the session
            saveSession(request, data, SessionAttribute.REPLICATION_DATA_TAG);
            
            // this can be changed later - just for now
            response.sendRedirect(WebPages.REPLICATION_PAGE);
        
        }
        catch (Exception e) {
             showErrorPage(request, response);
        }
        
        
        
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if (response.isCommitted()) {
            return;
        }
        
        try {
            // check for the search data
            if (request.getParameter("search") != null) {
                processSearchFilterRequest(request, response);
            }
            
            response.sendRedirect(WebPages.REPLICATION_PAGE); 
        }
        
        catch (Exception e) {
            e.printStackTrace();
            showErrorPage(request, response);
        }
        
    }

    
    private void processSearchFilterRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // get the data from the session
        ReplicationData data = (ReplicationData)getSessionData(request, SessionAttribute.REPLICATION_DATA_TAG);
        if (data == null) {
            return;
        }
        
        ReplicationSearchEntity search = null;
        
        if (data.getSearch() == null) {
            search = new ReplicationSearchEntity();
        }
        else {
            search = data.getSearch();
        }
        
        //set the branch code
        String branchCode = request.getParameter("searchByBranchCode");
        search.setBranchCode(branchCode);
        
        // set branch code to null if ALL
        if (branchCode != null && search.getBranchCode().equals("ALL"))
            search.setBranchCode(null); 
        
        // set the process type
        String processType = request.getParameter("processType");
        search.setProcess(processType);
        
        if (processType != null && processType.equals("ANY"))
            search.setProcess(null);
        
        // search the data
        data.setReplicationDetails(DataFactory.searchReplicationDataByFilter
                    (search.getBranchCode(), search.getProcess()));
        
        data.setSearch(search);
        
        saveSession(request, data, SessionAttribute.REPLICATION_DATA_TAG); 
        
        
    }

   
}
