/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.argility.furnmart.data.DataFactory;
import za.co.argility.furnmart.entity.ExtractHistory;
import za.co.argility.furnmart.entity.ProcessType;
import za.co.argility.furnmart.servlet.helper.DailyBIExtractsData;
import za.co.argility.furnmart.util.BucketMap;
import za.co.argility.furnmart.util.WebPages;

/**
 *
 * @author tmaleka
 */
public class DailyBIExtractsServlet extends GenericServlet {
    
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        super.doGet(request, response);
        if (response.isCommitted())
            return;
        
        DailyBIExtractsData data = (DailyBIExtractsData)getSessionData(request, 
                SessionAttribute.DAILY_BI_EXTRACTS_DATA_TAG);
        if (data == null) {
            data = new DailyBIExtractsData();
        }
        
        try {
           
           ProcessType type = ProcessType.DayEnd;
            
           // get the requested tab
           String tab = request.getParameter(DailyBIExtractsData.TAB_PARAM);
           if (tab == null || tab.length() == 0)
               tab = DailyBIExtractsData.TAB_DAILY;
           data.setCurrentTab(tab); 
           
           // get the current date
           String day = request.getParameter(DailyBIExtractsData.DAY_PARAM);
           if (day == null)
               day = DailyBIExtractsData.DAY_TODAY;
           
           Date selectedDate = null;
           
           try {
               SimpleDateFormat format = new SimpleDateFormat(DailyBIExtractsData.DATE_FORMAT);
               selectedDate = format.parse(day);
               data.setCurrentDay(selectedDate); 
           }
           
           catch (ParseException p) {
               selectedDate = new Date();
               data.setCurrentDay(selectedDate); 
           }
          
           
           // get the daily BI extracts history
           if (!tab.equals(DailyBIExtractsData.TAB_DAILY))
               type = ProcessType.MonthEnd;
           
           // get the data
           BucketMap<String, ExtractHistory> history = null;
           
           if (type == ProcessType.MonthEnd) {
               
                BucketMap<String, ExtractHistory> fromCentral = DataFactory.getDailyBIExtractHistoryRun(selectedDate, type, false);
                BucketMap<String, ExtractHistory> fromBatch =  DataFactory.getDailyBIExtractHistoryRun(selectedDate, type, true);
                
                Set<String> keys = fromBatch.keySet();
                if (keys == null || keys.isEmpty())
                    history = fromCentral;
                
                else {
                    for (String branch : keys) {
                        
                        List<ExtractHistory> list = fromBatch.get(branch);
                        
                        for (ExtractHistory item : list) {
                            fromCentral.put(branch, item);
                        }
                    }
                    
                    history = fromCentral;
                }
                
                
           }
           
           else
               history = DataFactory.getDailyBIExtractHistoryRun(selectedDate, type, false);
           
                       
           data.setExtractHistory(history); 
           
           // get the last time the extract files were sent
           data.setLastExtractFilesSentMap(DataFactory.getExtractFilesLastSentDate()); 
           
           // serialise the data to the session
           saveSession(request, data, SessionAttribute.DAILY_BI_EXTRACTS_DATA_TAG); 
            
            // send redirect to the display page
            response.sendRedirect(WebPages.DAILY_BI_EXTRACTS_PAGE); 
            
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
