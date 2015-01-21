/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.argility.furnmart.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
import za.co.argility.furnmart.entity.ProdConsRunEntity;
import za.co.argility.furnmart.entity.ProdConsScriptsEntity;
import za.co.argility.furnmart.jdbc.JdbcDeleteConsRunData;
import za.co.argility.furnmart.jdbc.JdbcInsertConsRunData;
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
                
                try {
                    runProcesses(request, response);
                } catch (Exception ex) {
                    Logger.getLogger(MonthEndConsolidationServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                response.sendRedirect(WebPages.MONTHEND_CONSOLIDATION_PAGE);
                //sendInternalRedirect(request, response, 
                        //"/MonthEndConsolidation?tab=cons");
                return;
            }
            
            doGet(request, response);
       
    }

    private void runProcesses(HttpServletRequest request, HttpServletResponse response)
            throws IOException, Exception {

        MonthendData data = (MonthendData)getSessionData(request, MonthendData.class);
        Map<Integer, ProdConsScriptsEntity> map = new TreeMap<Integer, ProdConsScriptsEntity>();
        for (ProdConsScriptsEntity entity : data.getProdConsEntities()) 
            map.put(entity.getProdConsId(), entity);
        
        String[] parameters = null;
        List<ProdConsScriptsEntity> prodConsSelected = new ArrayList<ProdConsScriptsEntity>();
        
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
            ProdConsScriptsEntity entity = map.get(id);
            ProdConsRunEntity prodConsRunEntity = new ProdConsRunEntity(); 
             String fppCde =  DataFactory.getMeconFpp();
            if (entity != null) {
                try {
                    
                    entity.setProdConsStartDte(new Date());
                    prodConsRunEntity.setProdConsStartDte(new Date());
                            
                    List<String> commands = new ArrayList<String>();
                    String[] array = entity.getProdConsScript().split(" ");
                    for (String t : array)
                        commands.add(t);
                                                        
                    
                    for(String c : commands){
                        new JdbcDeleteConsRunData(entity.getProdConsId(),fppCde);
                        ProcessBuilder builder = new ProcessBuilder(c);
                        Process process = builder.start();
                        process.waitFor();
                        prodConsSelected.add(entity);
                    }                    
                    
                    
                    entity.setProdConsError(null);
                    data.setProdConsSelectedEntities(prodConsSelected);
                   
                         
                    
                    //insert into prod_cons_run table 
                 
                   prodConsRunEntity.setProdConsId(entity.getProdConsId());
                   //String fppCde =  DataFactory.getMeconFpp();
                   prodConsRunEntity.setFppCde(fppCde);
                   prodConsRunEntity.setProdConsError(null);
                    
                } catch (Exception ex) {
                     entity.setProdConsError(ex.getMessage().replaceAll("\"", "'"));
                     prodConsRunEntity.setProdConsId(entity.getProdConsId());
                     prodConsRunEntity.setFppCde(fppCde);
                     prodConsRunEntity.setProdConsError(ex.getMessage());
                    // prodConsRunEntity.setProdConsError("tana");
                     prodConsRunEntity.setProdConsStartDte(new Date());
                     prodConsRunEntity.setProdConsEndDte(new Date());
                     //prodConsSelected.add(entity);
                    /*
                     try {
                        new JdbcInsertConsRunData(prodConsRunEntity);
                    } catch (SQLException ex1) {
                        Logger.getLogger(MonthEndConsolidationServlet.class.getName()).log(Level.SEVERE, null, ex1);
                    }*/
                   
                }finally{
                    entity.setProdConsEndDte(new Date());
                    prodConsRunEntity.setProdConsEndDte(new Date());
                    try {
                        new JdbcInsertConsRunData(prodConsRunEntity);
                    } catch (SQLException ex) {
                        Logger.getLogger(MonthEndConsolidationServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }            
            }
            
        }
        
       
        for (ProdConsScriptsEntity prodConsEntity :map.values()) {
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
