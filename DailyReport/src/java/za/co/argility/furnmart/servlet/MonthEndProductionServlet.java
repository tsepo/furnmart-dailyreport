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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.argility.furnmart.data.DataFactory;
import za.co.argility.furnmart.entity.GLDetailEntity;
import za.co.argility.furnmart.entity.GLDeteailGLEntity;
import za.co.argility.furnmart.entity.GLEntity;
import za.co.argility.furnmart.entity.GLMapActTyp;
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
    private GLDetailEntity GLDetailInstoreEntity;
    private GLDetailEntity GLDetailGLEntity;

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
            checkGLMapActType(request,response);
            response.sendRedirect(WebPages.MONTHEND_PROCESSES_PAGE);
            return;
        }
        
        
         if (request.getParameter("tab") != null &&
                request.getParameter("tab").equals("gl")) {
            
            Log.info("... inside GL Balancing ...");
            
           
            processGLData(request, response);
            response.sendRedirect(WebPages.GL_MAIN_PAGE);
            return;
        }
        
         if (request.getParameter("branchNo") != null) {
            
            Log.info("... going to GL Detail Balancing ...");
            
            
            processDetailGLData(request, response);
            response.sendRedirect(WebPages.GL_DETAIL_PAGE);
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
             Log.info("filePath --->  " + filePath);
             f = new File(filePath);
             boolean isPWCDelivered = false;
             if(f.exists()){
                isPWCDelivered = true;
             }else{
                Log.info("file does not exist !!! ");
                isPWCDelivered = false; 
             }
             det.setIsPWCExtractsDelivered(isPWCDelivered); 
             
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
     
     protected void checkGLMapActType(HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
            Log.info("...checkGLMapActType 1");   
          
            if (request.getParameter("tab") != null &&  
                request.getParameter("tab").equals("processes")) {
            MonthendData data = (MonthendData)getSessionData(request, 
                            SessionAttribute.MONTHEND_DATA_TAG);
            Log.info("...  ..."); 

            if (data == null) {
                data = new MonthendData();
            }   
             Log.info("...checkGLMapActType 2");    
            
            //List<GLMapActTyp>  glMapAcyTypeMissingList = DataFactory.getMissingGLMapActionTypeList();
            List<GLMapActTyp>  glMapAcyTypeMissingList = DataFactory.getBranchActionTypes();
            data.setGlMapActType(glMapAcyTypeMissingList);
            List<MonthendProcesses>  meProcessList = DataFactory.getMEProcessesList();         
            data.setMonthendProcesses(meProcessList);
            
            Log.info("... checkGLMapActType 3" + glMapAcyTypeMissingList.size());    
            saveSession(request, data, SessionAttribute.MONTHEND_DATA_TAG);
        
            }                    
                    
         
     }
     
     
     protected void processGLData(HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
        if (request.getParameter("tab") != null &&
                request.getParameter("tab").equals("gl")) {
            
        MonthendData data = (MonthendData)getSessionData(request, 
                            SessionAttribute.MONTHEND_DATA_TAG);

        if (data == null) {
            data = new MonthendData();
        }
        
       
        List<GLEntity>  glData = DataFactory.getGLData();                 
        data.setGlDetails(glData);


         // save the data to the session
         saveSession(request, data, SessionAttribute.MONTHEND_DATA_TAG);

         // this can be changed later - just for now
        // response.sendRedirect(WebPages.MONTHEND_PROD_PAGE);

        }           
                
        
    }
     
     
     protected void processDetailGLData(HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
        
        if (request.getParameter("branchNo") != null) {
            String branchNo =  request.getParameter("branchNo");
        
        System.out.println("branch no ----> " + branchNo);    
            
        MonthendData data = (MonthendData)getSessionData(request, 
                            SessionAttribute.MONTHEND_DATA_TAG);

        if (data == null) {
            data = new MonthendData();
        }
        
        HashMap<String, GLDetailEntity> map = new HashMap<String,GLDetailEntity >();
        List<GLDetailEntity>  glDetailDebtorsList = DataFactory.getGlDetailDebtorsList();
        List<GLDetailEntity>  instoreDetailDebtorsList = DataFactory.getInstoreDetailDebtorsList(branchNo);     
        
        System.out.println("in  processDetailGLData ----> ");
        System.out.println("instoreDetailDebtorsList size ----> " +instoreDetailDebtorsList.size());
        System.out.println("glDetailDebtorsList size ----> " + glDetailDebtorsList.size());
       
        
        
        for (Iterator<GLDetailEntity> it = instoreDetailDebtorsList.iterator(); it.hasNext();) {
              GLDetailInstoreEntity = it.next();
              GLDetailInstoreEntity.setGlVal(getGlVal(glDetailDebtorsList,GLDetailInstoreEntity.getActionType()));
        }
        
        
        
        System.out.println("instoreDetailDebtorsList ----> " + instoreDetailDebtorsList.size()); 
        data.setGlDets(instoreDetailDebtorsList);
        
         // save the data to the session
        saveSession(request, data, SessionAttribute.MONTHEND_DATA_TAG);

           
        }         
                      
        
    }
     
    public double getGlVal(List GLDetailEntity, int actTyp){
        List<GLDetailEntity>  glDetailDebtorsList = GLDetailEntity;
        for (Iterator<GLDetailEntity> it = glDetailDebtorsList.iterator(); it.hasNext();) {
              GLDetailInstoreEntity = it.next();
              if(actTyp == GLDetailInstoreEntity.getActionType()){
                  return GLDetailInstoreEntity.getGlVal();
              }
        }
        return 0.0;
    } 
     


}