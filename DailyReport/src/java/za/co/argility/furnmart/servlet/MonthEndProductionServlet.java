/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.argility.furnmart.data.DataFactory;
import za.co.argility.furnmart.entity.MonthEndTableType;
import za.co.argility.furnmart.entity.MonthendEntity;
import za.co.argility.furnmart.servlet.helper.MonthendData;
import za.co.argility.furnmart.util.BucketMap;
import za.co.argility.furnmart.util.WebPages;

/**
 *
 * @author rnaidoo
 */
public class MonthEndProductionServlet  extends GenericServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        super.doGet(request, response); 
        
        if (response.isCommitted()) {
            return;
        }
        
        MonthendData data = (MonthendData)getSessionData(request, SessionAttribute.MONTHEND_DATA_TAG);
        if (data == null) {
            data = new MonthendData();
        }
        
        try {
            
           // get the details for replication
            HashMap<String, MonthendEntity> map = new HashMap<String, MonthendEntity>();
            
            DataFactory.getMonthendDetails(MonthEndTableType.NewGLTranExt, map);
            DataFactory.getMonthendDetails(MonthEndTableType.CashBookExtract, map);
            DataFactory.getMonthendDetails(MonthEndTableType.CentralAccount, map);
            DataFactory.getMonthendDetails(MonthEndTableType.Creditors,map);
            
            ArrayList<MonthendEntity> details = new ArrayList<MonthendEntity>();
            TreeSet<String> branches = new TreeSet<String>(map.keySet());
            for (String branch : branches) {
                details.add(map.get(branch));
            }
            data.setMonthendDetails(details);
            
           
            // get the replication branch list
            //data.setMonthendBranchList(DataFactory.getMonthendBranchList()); 
            
            // get the processes
            //data.setProcesses(DataFactory.getReplicationProcesses()); 
            
            // save the data to the session
            saveSession(request, data, SessionAttribute.MONTHEND_DATA_TAG);
            
            // this can be changed later - just for now
            response.sendRedirect(WebPages.MONTHEND_PROD_PAGE);
        
        }
        catch (Exception e) {
             showErrorPage(request, response);
        }
        
        
        
    }
}