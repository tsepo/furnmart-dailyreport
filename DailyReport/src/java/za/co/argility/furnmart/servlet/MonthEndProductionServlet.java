/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.argility.furnmart.data.DataFactory;
import za.co.argility.furnmart.entity.GLSubType;
import za.co.argility.furnmart.entity.MonthEndTableType;
import za.co.argility.furnmart.entity.MonthendEntity;
import za.co.argility.furnmart.servlet.helper.MonthendData;
import za.co.argility.furnmart.servlet.helper.MonthendOverviewData;
import za.co.argility.furnmart.servlet.helper.MonthendProcesses;
import za.co.argility.furnmart.util.BucketMap;
import za.co.argility.furnmart.util.Log;
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
       
        Log.setLogger(MonthEndProductionServlet.class);
        
        if (response.isCommitted()) {
            return;
        }
        
        try {
        
        boolean hasParameters = (request.getParameterMap() == null || 
                 !request.getParameterMap().isEmpty());
            
        if (!hasParameters || (request.getParameter("tab") != null 
                && request.getParameter("tab").equals("overview"))) {
            
            Log.info("... inside overview ...");          
            
            
            
            processMonthEndOverviewData(request, response);
            response.sendRedirect(WebPages.MONTHEND_OVERVIEW_PAGE);
            return;
        }
        
        if (request.getParameter("tab") != null &&
                request.getParameter("tab").equals("production")) {
            
            Log.info("... inside production ...");
            
            processMonthEndProductionData(request, response);
            response.sendRedirect(WebPages.MONTHEND_PROD_PAGE);
            return;
        }
        
        
         if (request.getParameter("tab") != null &&
                request.getParameter("tab").equals("processes")) {
            
            Log.info("... inside processes ...");
            
            checkGLSubType(request, response);
            response.sendRedirect(WebPages.MONTHEND_PROCESSES_PAGE);
            return;
        }
        
        
        }
        
        catch (Exception e) {
            Log.info("Exception: " + e.getMessage());
            this.showErrorPage(request, response);
        }
        
    }
    
    protected void processMonthEndOverviewData(HttpServletRequest request, 
            HttpServletResponse response) throws IOException {
        
        if (request.getParameterMap() == null || request.getParameterMap().size() == 0) {
            
            HttpSession session = request.getSession(true);
            MonthendOverviewData data = (MonthendOverviewData)getSessionData(request, 
                    SessionAttribute.MONTHEND_OVERVIEW_DATA_TAG);
            if (data == null)
                data = new MonthendOverviewData();
            
            
            saveSession(request, data, SessionAttribute.MONTHEND_OVERVIEW_DATA_TAG);

        }
        
    }

    
    
    
    protected void processMonthEndProductionData(HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
        if (request.getParameter("tab") != null &&
                request.getParameter("tab").equals("production")) {
            
        MonthendData data = (MonthendData)getSessionData(request, 
                            SessionAttribute.MONTHEND_DATA_TAG);

        if (data == null) {
            data = new MonthendData();
        }
        
       

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
         
         String server = request.getServerName();
         
         Log.info("Server ---> " + server);
         File f = null;
         for (MonthendEntity det : details){
             String filePath = "/home/ucsretail/" + det.getBranchCode() + "/PWC_SENT_SUCCESSFULLY";
             Log.info("fielPath --->  " + filePath);
             f = new File(filePath);
             if(f.exists()){
                det.setIsPWCExtractsDelivered(true);
             }else{
                det.setIsPWCExtractsDelivered(false); 
             }
         }
         
         data.setMonthendDetails(details);


         // get the replication branch list
         //data.setMonthendBranchList(DataFactory.getMonthendBranchList()); 

         // get the processes
         //data.setProcesses(DataFactory.getReplicationProcesses()); 

         // save the data to the session
         saveSession(request, data, SessionAttribute.MONTHEND_DATA_TAG);

         // this can be changed later - just for now
        // response.sendRedirect(WebPages.MONTHEND_PROD_PAGE);

        }           
                
        
    }
    
     protected void checkGLSubType(HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
            
          
            if (request.getParameter("tab") != null &&  
                request.getParameter("tab").equals("processes")) {
            MonthendData data = (MonthendData)getSessionData(request, 
                            SessionAttribute.MONTHEND_DATA_TAG);

            if (data == null) {
                data = new MonthendData();
            }   
             Log.info("... inside gl subType ...");    
            
            List<GLSubType>  glSubTypeMissingList = DataFactory.getGlSubTypeMissingList();
            data.setGlSubType(glSubTypeMissingList);
            List<MonthendProcesses>  meProcessList = DataFactory.getMEProcessesList();         
            data.setMonthendProcesses(meProcessList);
            
            Log.info("... inside gl subType ..." + glSubTypeMissingList.size());    
            saveSession(request, data, SessionAttribute.MONTHEND_DATA_TAG);
        
            }                    
                    
         
     }
}
