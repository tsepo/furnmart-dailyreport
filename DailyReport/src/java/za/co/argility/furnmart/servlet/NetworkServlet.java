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
import za.co.argility.furnmart.servlet.helper.NetworkData;
import za.co.argility.furnmart.util.WebPages;

/**
 *
 * @author tmaleka
 */
public class NetworkServlet extends GenericServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        super.doGet(request, response); 
        
        if (response.isCommitted()){
            return;
        }
         
        // get the data from the session
        NetworkData data = (NetworkData)getSessionData(request, SessionAttribute.NETWORK_DATA_TAG);
        if (data == null) {
            data = new NetworkData();
        }
        
        try {
            
            // get the stores with network
            data.setNetworkAvailableList(DataFactory.getNetworkStatistics(true));
            // get the stores without network
            data.setNetworkUnavailableList(DataFactory.getNetworkStatistics(false));
            
            // serialise data to the session
            saveSession(request, data, SessionAttribute.NETWORK_DATA_TAG);
            
            //redirect to the web page
            response.sendRedirect(WebPages.NETWORK_PAGE);
            
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
