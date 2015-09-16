/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.argility.furnmart.data.DataFactory;
import za.co.argility.furnmart.servlet.helper.OverviewData;
import za.co.argility.furnmart.util.WebPages;

/**
 *
 * @author tmaleka
 */
public class OverviewServlet extends GenericServlet {

   
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        super.doGet(request, response);
        
        if (response.isCommitted()) {
            return;
        }
        
        // check if there is any data in the session
        OverviewData data = (OverviewData)getSessionData(request, SessionAttribute.OVERVIEW_DATA_TAG);
        if (data == null) {
            data = new OverviewData();
        }
        
        try {
            // get the network statistics
            data.setNetworkOverview(DataFactory.getNetworkOverviewData()); 
            //get the replication status overview
            data.setReplicationStatusOverview(DataFactory.getReplicationStatusOverviewData());
            
            // serialise the session data
            saveSession(request, data, SessionAttribute.OVERVIEW_DATA_TAG); 
            
            // send a redirect to page 
            response.sendRedirect(WebPages.OVERVIEW_PAGE); 
        }
        
        catch (Exception ex) { 
            showErrorPage(request, response);
        }
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }


}
