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
import za.co.argility.furnmart.servlet.helper.DailyITCExtractsData;
import za.co.argility.furnmart.util.WebPages;

/**
 *
 * @author mbalenhle ndaba
 */
public class ViewITCBranchesServlet extends GenericServlet {

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        super.doGet(request, response);
        if (response.isCommitted())
            return;
        
        DailyITCExtractsData data = (DailyITCExtractsData)getSessionData(request, 
                SessionAttribute.VIEW_ITC_BRANCHES_DATA_TAG);
        if (data == null) {
            data = new DailyITCExtractsData();
        }
        
        try {
            
           // get the requested tab
           String tab = request.getParameter(DailyITCExtractsData.TAB_PARAM);
           if (tab == null || tab.length() == 0)
               tab = DailyITCExtractsData.TAB_DAILY;
           data.setCurrentTab(tab); 
           
           // get the current date
           String day = request.getParameter(DailyITCExtractsData.DAY_PARAM);
           if (day == null)
               day = DailyITCExtractsData.DAY_TODAY;
         
           data.setItcExtract(DataFactory.getITCBranchDetailsRun()); 
          
           // serialise the data to the session
           saveSession(request, data, SessionAttribute.VIEW_ITC_BRANCHES_DATA_TAG); 
            
            // send redirect to the display page
            response.sendRedirect(WebPages.VIEW_ITC_BRANCHES); 
        }
        
        catch (Exception e) {
            showErrorPage(request, response); 
        }
    }
    
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
