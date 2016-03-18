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

public class DailyITCExtractsServlet extends GenericServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        super.doGet(request, response);
        if (response.isCommitted())
            return;
        
        DailyITCExtractsData data = (DailyITCExtractsData)getSessionData(request, 
                SessionAttribute.DAILY_ITC_EXTRACTS_DATA_TAG);
        if (data == null) {
            data = new DailyITCExtractsData();
        }
        
        try {
           // get the requested tab
           String tab = request.getParameter(DailyITCExtractsData.TAB_PARAM);
           if (tab == null || tab.length() == 0)
               tab = DailyITCExtractsData.TAB_DAILY;
           
           data.setCurrentTab(tab);
           data.setItcExtract(DataFactory.getDailyITCExtractHistoryRun()); 
           
           // serialise the data to the session
           saveSession(request, data, SessionAttribute.DAILY_ITC_EXTRACTS_DATA_TAG); 
            
            // send redirect to the display page
            response.sendRedirect(WebPages.DAILY_ITC_EXTRACTS_PAGE); 
            
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
