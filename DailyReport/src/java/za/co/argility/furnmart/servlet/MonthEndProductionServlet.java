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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import za.co.argility.furnmart.entity.MonthendDetailStatusEntity;
import za.co.argility.furnmart.entity.MonthendEntity;
import za.co.argility.furnmart.entity.MonthendSearchEntity;
import za.co.argility.furnmart.entity.MonthendStatusEntity;
import za.co.argility.furnmart.servlet.helper.MeProdRunData;
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
        Log.info("... in doGet ME Prod Servlet ...");
       
        Log.setLogger(MonthEndProductionServlet.class);
        
        if (response.isCommitted()) {
            return;
        }
        
        try {
            
        if(request.getParameter("fppCde") != null){
            Log.info("Found fpp doGet ---> " + request.getParameter("fppCde"));
            Log.info("Tab = " + request.getParameter("tab"));
        }else{
            Log.info("Yeneetha ----> fpp not found.");
        }    
        
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
                request.getParameter("tab").equals("cons")) {
              System.out.println("I am in Consolidation page.");
              Log.info("... montendConsolidations ...");       
       
        response.sendRedirect(WebPages.MONTHEND_CONSOLIDATION_PAGE); 
        
        /*
         if (response.isCommitted())
            return;
        
       
        out = new PrintWriter(response.getOutputStream());
        runProcesses(request, response);
        */
          
              
          }
         
        
        
        if (request.getParameter("tab") != null &&
                request.getParameter("tab").equals("production")) {
            
            Log.info("... inside production ...");
            
            processMonthEndProductionData(request, response);
            response.sendRedirect(WebPages.MONTHEND_PROD_PAGE);
            return;
        }
        
        // BEGIN Nyeleti===============================================================
        
          if (request.getParameter("tab") != null &&
                request.getParameter("tab").equals("prod")) {
            
            Log.info("... Testing new change ...");
              String fppCde = null;
        
        if(request.getParameter("fppCde") != null){
            Log.info("Found fpp Bliksim---> " + request.getParameter("fppCde"));
            fppCde = request.getParameter("fppCde");
        }else{
            fppCde = DataFactory.getMeconsFppCode();
            Log.info("Initial fpp --->.");
        }     
                      
             
            Log.info("Rajen ---> ");
            buildProcessRunsHistory(request, response, fppCde);
            Log.info("Mbale ---> ");
            response.sendRedirect(WebPages.PROCESS_STATUS_PAGE);
            return; 
          }
          
          
          
        boolean allStatusSelected = statusSelection(request, response); 
       
         if (request.getParameter("branchNumber") != null) {
            
            Log.info("... going to process status details ...");            
            
            processDetailStatusData(request, response,allStatusSelected);
            
            response.sendRedirect(WebPages.PROCESS_STATUS_DETAIL_PAGE);
            return;
        } 
         
          
        
          // END ==================================================================
        
          if (request.getParameter("tab") != null &&
                request.getParameter("tab").equals("processes")) {
            
            Log.info("... inside processes ...");
            
            checkGLSubType(request, response);
            checkGLMapActType(request,response);
            response.sendRedirect(WebPages.MONTHEND_PROCESSES_PAGE);
            return;
        }
          
          
          
          if (request.getParameter("tab") != null &&
                request.getParameter("tab").equals("statements")) {
            
            Log.info("... inside Statements ...");
            
           
            processStatementData(request, response);
            response.sendRedirect(WebPages.MONTHEND_STATEMENTS);
            return;
          }  
        
          
        
       
        if(request.getParameter("statementSelect") != null){
            if (request.getParameter("statementSelect").equals("all") ||  request.getParameter("statementSelect").equals("nostatements")) {  

                MonthendData data = (MonthendData)getSessionData(request, 
                            SessionAttribute.MONTHEND_DATA_TAG);

                    if (data == null) {
                        data = new MonthendData();
                    }
                    
                if(request.getParameter("statementSelect").equals("nostatements")){                   
                    data.setIsAllStatementsSelected(false);
                    Log.info("... statementSelect ...");
                    Log.info("... inside Statements ...");
                 }else{
                    data.setIsAllStatementsSelected(true);
                }
                  response.sendRedirect(WebPages.MONTHEND_STATEMENTS);
            }     
            
            
        } 
          
          
          
        
         if ((request.getParameter("tab") != null &&
                request.getParameter("tab").equals("gl")) || request.getParameter("fppCde") != null) {
            
            Log.info("... inside GL Balancing ...");
         
        String fppCde = null;
        
        if(request.getParameter("fppCde") != null){
            Log.info("Found fpp ---> " + request.getParameter("fppCde"));
            fppCde = request.getParameter("fppCde");
        }else{
            fppCde = DataFactory.getMeconsFppCode();
            Log.info("Initial fpp --->.");
        }     
           
            
             
            Log.info("Rajen ---> ");
            processGLData(request, response,fppCde);
            Log.info("Mbale ---> ");
            response.sendRedirect(WebPages.GL_MAIN_PAGE);
            return;
        }
        
        boolean allGlSelected = glSelection(request, response); 
       
         if (request.getParameter("branchNo") != null) {
            
            Log.info("... going to GL Detail Balancing ...");
            
            
            processDetailGLData(request, response,allGlSelected);
            if(request.getParameter("type").equals("debtors")){
                response.sendRedirect(WebPages.GL_DETAIL_DEBTORS_PAGE);
            }else{
                response.sendRedirect(WebPages.GL_DETAIL_STOCK_PAGE);
                }
            return;
        } 
       
         
         
          
        
        }
        
        
        
        
        catch (Exception e) {
            Log.info("Exception: " + e.getMessage());
            this.showErrorPage(request, response);
        }
        
        }

    private boolean glSelection(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean allGlSelected = true;
        if(request.getParameter("glSelect") != null){
            if (request.getParameter("glSelect").equals("all") ||  request.getParameter("glSelect").equals("unbalanced")) {
                
                MonthendData data = (MonthendData)getSessionData(request,
                        SessionAttribute.MONTHEND_DATA_TAG);
                
                if (data == null) {
                    data = new MonthendData();
                }
                
                if(request.getParameter("glSelect").equals("unbalanced")){
                    Log.info("Yaneetha 1--->");
                    data.setIsAllGLSelected(false);
                    
                }else{
                    Log.info("Yaneetha 2--->");
                    data.setIsAllGLSelected(true);
                }
                response.sendRedirect(WebPages.GL_MAIN_PAGE);
            }            
            
        }
        return allGlSelected;
    }
    
    
    private boolean statusSelection(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean allStatusSelected = true;
        if(request.getParameter("processStatusSelect") != null){
            if (request.getParameter("processStatusSelect").equals("all") ||  request.getParameter("processStatusSelect").equals("errors")) {
                
                MonthendStatusEntity data = (MonthendStatusEntity)getSessionData(request,
                        SessionAttribute.MONTHEND_DATA_TAG);
                
                if (data == null) {
                    data = new MonthendStatusEntity();
                }
                
                if(request.getParameter("processStatusSelect").equals("errors")){
                    Log.info("Yaneetha 1--->");
                    data.setAllDataSelected(false);
                    
                }else{
                    Log.info("Yaneetha 2--->");
                    data.setAllDataSelected(true);
                }
                response.sendRedirect(WebPages.PROCESS_STATUS_PAGE);
            }
            
            
        }
        return allStatusSelected;
    }
    
    
    
     private void buildProcessRunsHistory(HttpServletRequest request, HttpServletResponse response, String fppCde) throws Exception {
        
         
         
         if (request.getParameter("tab") != null &&
                request.getParameter("tab").equals("prod")) { 
             
         
         MonthendData data = (MonthendData)getSessionData(request, 
                            SessionAttribute.MONTHEND_DATA_TAG);

        if (data == null) {
            data = new MonthendData();
        }
        
        //String fppCde  = DataFactory.getMeconsFppCode();
     
        
        Log.info("fppCde  buildProcessRunsHistory---> " + fppCde);
        List<MonthendStatusEntity> finalProcessStatusList = new ArrayList<MonthendStatusEntity>();
        List<MonthendStatusEntity>  getMonthendStatusList = DataFactory.getMonthendStatusList(); 
        String tmpBranchCode = getMonthendStatusList.get(0).getBrCde();
       
        
        
        
        for(int r=0;r<getMonthendStatusList.size();r++){
           if(r==0){
                 System.out.println("Nyl 1");
               finalProcessStatusList.add(getMonthendStatusList.get(r));
           } 
           if(r>=1){
               if(!tmpBranchCode.equals(getMonthendStatusList.get(r).getBrCde())){
                   finalProcessStatusList.add(getMonthendStatusList.get(r));
                   tmpBranchCode = getMonthendStatusList.get(r).getBrCde();
                   System.out.println("Nyl 2");
               }
           }
            
        }
        
        System.out.println("Tana 9 ----> " + getMonthendStatusList.size());
        data.setMonthendStatusEntity(finalProcessStatusList);
         
         
          saveSession(request, data);
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

         DataFactory.getMonthendDetails(MonthEndTableType.NewGLTranExt, map,false);
         DataFactory.getMonthendDetails(MonthEndTableType.CashBookExtract, map,false);
         DataFactory.getMonthendDetails(MonthEndTableType.CentralAccount, map,false);
         DataFactory.getMonthendDetails(MonthEndTableType.Creditors,map,false);
         DataFactory.getMonthendDetails(MonthEndTableType.Buckets,map,false);
         DataFactory.getMonthendDetails(MonthEndTableType.PolicyExtract,map,false);
         DataFactory.getMonthendDetails(MonthEndTableType.ClaimsExtract,map,false);
         
         

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
    
    
    
    protected void processStatementData(HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
        if (request.getParameter("tab") != null &&
                request.getParameter("tab").equals("statements")) {
            
        MonthendData data = (MonthendData)getSessionData(request, 
                            SessionAttribute.MONTHEND_DATA_TAG);

        if (data == null) {
            data = new MonthendData();
        }
        
       

        // get the details for replication
         HashMap<String, MonthendEntity> map = new HashMap<String, MonthendEntity>();

         DataFactory.getMonthendDetails(MonthEndTableType.Statements, map,true);
         

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
            HttpServletResponse response, String fppCde) throws Exception {
         
         Log.info("In processGLData ---> Yaneetha");   
        if ((request.getParameter("tab") != null &&
                request.getParameter("tab").equals("gl") || fppCde != null)) {
            
            
            
           // boolean allGlSelected = glSelection(request, response); 
            
         
        MonthendData data = (MonthendData)getSessionData(request, 
                            SessionAttribute.MONTHEND_DATA_TAG);

        if (data == null) {
            data = new MonthendData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
        }
        
        //Coenraad
        
       // boolean allGlSelected = true;
        if(request.getParameter("glSelect") != null){
            if(request.getParameter("glSelect").equals("unbalanced")){
                Log.info("Yaneetha 1--->");
                data.setIsAllGLSelected(false);
                    
            }else{
                Log.info("Yaneetha 2--->");
                data.setIsAllGLSelected(true);
            }
        }
        data.setSelectedFppCde(fppCde);
        data.setMonthendBranchList(DataFactory.getMonthendBranchList());
        data.setFppCdeList(DataFactory.getGLFppList());
        
    
        
        
       
        
        
        Log.info("fppCde ---> " + fppCde);
        
        List<GLEntity>  glData = DataFactory.getNewGLData(fppCde);                 
        data.setGlDetails(glData);
        
        

         // save the data to the session
         saveSession(request, data, SessionAttribute.MONTHEND_DATA_TAG);

         // this can be changed later - just for now
       

        }           
                
        
    }
     
     
     protected void processDetailGLData(HttpServletRequest request, 
            HttpServletResponse response, Boolean allGlSelected) throws Exception {
        
        System.out.println("allGlSelected ----> " + allGlSelected);
        if (request.getParameter("branchNo") != null) {
            String branchNo =  request.getParameter("branchNo");
        
         String type = null;   
         if (request.getParameter("type") != null) {
            type =  request.getParameter("type"); 
         }
            
            
        System.out.println("branch no ----> " + branchNo);    
        System.out.println("type ----> " + type); 
            
        MonthendData data = (MonthendData)getSessionData(request, 
                            SessionAttribute.MONTHEND_DATA_TAG);

        if (data == null) {
            data = new MonthendData();
        }     
        
            data.setIsAllGLSelected(allGlSelected);
            List<GLDetailEntity>  glDetailDebtorsList = DataFactory.getGlDetailDebtorsList(branchNo,type);
            List<GLDetailEntity>  instoreDetailDebtorsList = DataFactory.getInstoreDetailDebtorsList(branchNo,type);     

            System.out.println("in  processDetailGLData ----> ");
            System.out.println("instoreDetailDebtorsList size ----> " +instoreDetailDebtorsList.size());
            System.out.println("glDetailDebtorsList size ----> " + glDetailDebtorsList.size());
            
            double ibtVarianceVal = 0.0d; 
            for (Iterator<GLDetailEntity> it = glDetailDebtorsList.iterator(); it.hasNext();) {
                  GLDetailInstoreEntity = it.next();
                  if(GLDetailInstoreEntity.getActionType() == 71008){
                      ibtVarianceVal = GLDetailInstoreEntity.getGlVal();
                  }
            }
            
            System.out.println("ibtVarianceVal ---> " +ibtVarianceVal);
            
            for (Iterator<GLDetailEntity> it = glDetailDebtorsList.iterator(); it.hasNext();) {
                  GLDetailInstoreEntity = it.next();
                  if(GLDetailInstoreEntity.getActionType() == 71007){
                     GLDetailInstoreEntity.setGlVal(GLDetailInstoreEntity.getGlVal() + ibtVarianceVal);
                     double amt = GLDetailInstoreEntity.getGlVal();
                     double roundedAmt = Math.round(amt * 100);
		     GLDetailInstoreEntity.setGlVal(roundedAmt/100);
                     System.out.println("act type  71007 ---> " + GLDetailInstoreEntity.getGlVal());		

                  }
            }
            
            


            for (Iterator<GLDetailEntity> it = instoreDetailDebtorsList.iterator(); it.hasNext();) {
                  GLDetailInstoreEntity = it.next();
                  GLDetailInstoreEntity.setGlVal(getGlVal(glDetailDebtorsList,GLDetailInstoreEntity.getActionType()));
            }

            System.out.println("instoreDetailDebtorsList ----> " + instoreDetailDebtorsList.size()); 
            data.setGlDets(instoreDetailDebtorsList);
            data.setGlDetailBranchCode(branchNo);
            data.setGlDeatilType(type);
        
         // save the data to the session
        saveSession(request, data, SessionAttribute.MONTHEND_DATA_TAG);

           
        }         
                      
        
    }
     
     protected void processDetailStatusData(HttpServletRequest request, 
            HttpServletResponse response, Boolean allGlSelected) throws Exception {
        
        System.out.println("allGlSelected ----> " + allGlSelected);
        if (request.getParameter("branchNumber") != null) {
            String branchNo =  request.getParameter("branchNumber");
        
                  
            
        System.out.println("branch no ----> " + branchNo);    
       
            
        MonthendData data = (MonthendData)getSessionData(request, 
                            SessionAttribute.MONTHEND_DATA_TAG);

        if (data == null) {
            data = new MonthendData();
        }     
        
            data.setIsAllGLSelected(allGlSelected);
            data.setProcessStatusDetailBranchCode(branchNo);
           
        List<MonthendDetailStatusEntity>  getMonthendDetailStatusList = DataFactory.getMonthendDetailStatusList(branchNo);  
        
        data.setMonthendDetailStatusEntity(getMonthendDetailStatusList);
        
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