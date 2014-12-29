/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.argility.furnmart.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.argility.furnmart.data.DataFactory;
import za.co.argility.furnmart.entity.ProdConsEntity;
import za.co.argility.furnmart.servlet.helper.MonthendData;
import za.co.argility.furnmart.servlet.helper.MonthendOverviewData;
import za.co.argility.furnmart.util.Log;
import za.co.argility.furnmart.util.WebPages;

/**
 *
 * @author rnaidoo
 */
public class MonthEndConsolidationServlet extends GenericServlet {

    protected PrintWriter out = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        super.doGet(request, response);

        //response.sendRedirect(WebPages.MONTHEND_CONSOLIDATION_PAGE); 
        /* if ((request.getParameter("tab") != null
                && request.getParameter("tab").equals("cons")) ||
                request.getParameter("tab") ==null) { */
            
            System.out.println("I am in Consolidation page 2.");
            Log.info("... montendConsolidations ...");

            try {
                processProdConsData(request, response);
                response.sendRedirect(WebPages.MONTHEND_CONSOLIDATION_PAGE);
            } catch (Exception ex) {
                Logger.getLogger(MonthEndConsolidationServlet.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
           

            /*
             if (response.isCommitted())
             return;
        
       
             out = new PrintWriter(response.getOutputStream());
             runProcesses(request, response);
             */
        //}

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            if (request.getParameter("submitCons") != null) {
                
                runProcesses(request, response);
                response.sendRedirect(WebPages.MONTHEND_CONSOLIDATION_PAGE);
                //sendInternalRedirect(request, response, 
                        //"/MonthEndConsolidation?tab=cons");
                return;
            }
            
            doGet(request, response);
       
    }

    private void runProcesses(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        MonthendData data = (MonthendData)getSessionData(request, MonthendData.class);
        Map<Integer, ProdConsEntity> map = new TreeMap<Integer, ProdConsEntity>();
        for (ProdConsEntity entity : data.getProdConsEntities()) 
            map.put(entity.getProdConsId(), entity);
        
        String[] parameters = null;
        
        if (request.getParameterValues("run") != null)
            parameters = request.getParameterValues("run");
        else if (request.getParameter("run") != null) {
            parameters = new String[1];
            parameters[0] = request.getParameter("run");
        }
        else
            parameters = new String[0];
            
        for (String parameter : parameters) {
            Integer id = Integer.parseInt(parameter);
            ProdConsEntity entity = map.get(id);
            
            if (entity != null) {
                try {
                    
                    entity.setProdConsStartDte(new Date());
                    
                    List<String> commands = new ArrayList<String>();
                    commands.add(entity.getProdConsScript());
                    ProcessBuilder builder = new ProcessBuilder(commands);
                    builder.start();
                    
                    entity.setProdConsError(null);
                    
                } catch (Exception ex) {
                     entity.setProdConsError(ex.getMessage().replaceAll("\"", "'")); 
                    //Write to error field in table
                }finally{
                    entity.setProdConsEndDte(new Date());

                }            
            }
            
        }
        
       
        for (ProdConsEntity prodConsEntity :map.values()) {
            try {
                DataFactory.saveProdConEntity(prodConsEntity);
            } catch (Exception ex) {
                Logger.getLogger(MonthEndConsolidationServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        data.setConsRun(true);
        saveSession(request, data);
    }

    private void processProdConsData(HttpServletRequest request, HttpServletResponse response) throws Exception {
         
            HttpSession session = request.getSession(true);
             MonthendData data = (MonthendData)getSessionData(request, MonthendData.class);

        if (data == null) {
            data = new MonthendData();
        }
           
            data.setProdConsEntities(DataFactory.getProdConsEntities());
            data.setConsRun(false);
            saveSession(request, data);      
    
    }

}
