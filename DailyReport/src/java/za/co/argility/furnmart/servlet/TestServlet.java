/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.argility.furnmart.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.argility.furnmart.data.DataFactory;
import za.co.argility.furnmart.servlet.helper.MeProdRunData;
import za.co.argility.furnmart.util.Log;

/**
 *
 * @author luthando
 */
public class TestServlet extends GenericServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
             Log.info("...  newest development.......Luthando");
            if(request.getParameter("tab") != null && request.getParameter("tabs").equals("prod")){
                try {
                    buildProcessRunsHistory(request, response);
                } catch (Exception ex) {
                    Logger.getLogger(TestServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
       
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            buildProcessRunsHistory(request, response);
        } catch (Exception ex) {
            Logger.getLogger(TestServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
         private void buildProcessRunsHistory(HttpServletRequest request, HttpServletResponse response) throws Exception {
         DataFactory test = new DataFactory();
         
         Log.info("...  newest development.......Luthando");
         test.viewMeProdRun(2,0);
         test.viewMeProdRun(2,1);
         test.viewMeProdRun(2,2);
         test.viewMeProdRun(2,3);
         test.viewMeProdRun(2,4);
         test.viewMeProdRun(2,5);
          MeProdRunData data = (MeProdRunData)getSessionData(request, SessionAttribute.MONTHEND_DATA_TAG);

            if (data == null) {
                data = new MeProdRunData();
            }   
            
             
            Log.info( "One branch....."+data.getBr_cde().get(1));
                           
             
            saveSession(request, data);
    }

}
