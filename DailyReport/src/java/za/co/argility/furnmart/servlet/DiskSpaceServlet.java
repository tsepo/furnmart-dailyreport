/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.servlet;

import java.io.IOException;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.argility.furnmart.entity.DiskSpace;
import za.co.argility.furnmart.servlet.helper.DiskSpaceData;
import za.co.argility.furnmart.servlet.helper.DiskSpaceStatistics;
import za.co.argility.furnmart.util.WebPages;

/**
 *
 * @author tmaleka
 */
public class DiskSpaceServlet extends GenericServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response); 
        
        if (response.isCommitted()) {
            return;
        }
        
        DiskSpaceData data = (DiskSpaceData)getSessionData(request, 
                SessionAttribute.DISK_SPACE_DATA_TAG);
        
        if (data == null) {
            data = new DiskSpaceData();
        }
        
        try {
            
            // Get the latest data for disk space
            DiskSpaceStatistics stats = new DiskSpaceStatistics();
            TreeMap<String, DiskSpace> map = stats.getDiskSpaceStatistics();
            data.setDiskSpaceStats(map);
            
            // serialise to the session
            saveSession(request, data, SessionAttribute.DISK_SPACE_DATA_TAG);
            
            // redirect to the JSP page
            response.sendRedirect(WebPages.DISK_SPACE_PAGE);
        }
        
        catch (Exception e) {
            showErrorPage(request, response);
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

   

}
