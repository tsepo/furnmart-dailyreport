/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.argility.furnmart.data.DataFactory;
import za.co.argility.furnmart.entity.FlashFiguresEntity;
import za.co.argility.furnmart.entity.FlashFiguresSearchEntity;
import za.co.argility.furnmart.entity.ReplicationEntity;
import za.co.argility.furnmart.entity.ReplicationSearchEntity;
import za.co.argility.furnmart.servlet.helper.FlashFiguresData;
import za.co.argility.furnmart.servlet.helper.ReplicationData;
import za.co.argility.furnmart.util.GeneralUtils;
import za.co.argility.furnmart.util.WebPages;

/**
 *
 * @author rnaidoo
 */
public class FlashFiguresServlet extends GenericServlet {

  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        super.doGet(request, response); 
        
        if (response.isCommitted()) {
            return;
        }
        
        FlashFiguresData data = (FlashFiguresData)getSessionData(request, 
                SessionAttribute.FLASH_FIGURES_DATA_TAG);
        
        try {
            if (data == null) {

                data = new FlashFiguresData();

                // get the details for replication
                data.setReplicationDetails(DataFactory.getFlashFiguresDetails());

                // get the replication branch list
                data.setReplicationBranchList(DataFactory.getReplicationBranchList()); 

                // get the processes
                data.setProcesses(DataFactory.getReplicationProcesses()); 

                
            }
        
             // check for the search data
            if (request.getParameter("search") != null) {
                processSearchFilterRequest(data, request, response);
            }
            
            // check if the client requested an export
            if (request.getParameter("export") != null && 
                request.getParameter("export").equals("csv")) {
                    
                if (request.getParameter("type") != null && 
                    request.getParameter("type").equals("filter")) {
                
                    processExportReplicationSummary(data, FlashFiguresData.EXPORT_TYPE_FILTER, response);
                    return;
                }
                
                if (request.getParameter("type") != null && 
                    request.getParameter("type").equals("unhealthy")) {
                    
                    processExportReplicationSummary(data, FlashFiguresData.EXPORT_TYPE_UNHEALTHY_BRANCHES, response);
                    return;
                    
                }
            }
            
            // save the data to the session
            saveSession(request, data, SessionAttribute.FLASH_FIGURES_DATA_TAG);
            
            // this can be changed later - just for now
            response.sendRedirect(WebPages.FLASH_FIGURES_PAGE);
        
        }
        catch (Exception e) {
             showErrorPage(request, response);
        }
        
        
        
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if (response.isCommitted()) {
            return;
        }
        System.out.println("Yani page ");
        
         FlashFiguresData data = (FlashFiguresData)getSessionData(request, 
                SessionAttribute.FLASH_FIGURES_DATA_TAG);
        
        try {
            // check for the search data
            if (request.getParameter("search") != null) {
                processSearchFilterRequest(data, request, response);
            }
            
            
            // save the data to the session
            saveSession(request, data, SessionAttribute.FLASH_FIGURES_DATA_TAG);
            
            response.sendRedirect(WebPages.FLASH_FIGURES_PAGE); 
        }
        
        catch (Exception e) {
            e.printStackTrace();
            showErrorPage(request, response);
        }
        
    }

    
    private void processSearchFilterRequest(FlashFiguresData data, 
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        if (data == null) {
            return;
        }
        
        FlashFiguresSearchEntity search = null;
        
        if (data.getSearch() == null) {
            search = new FlashFiguresSearchEntity();
        }
        else {
            search = data.getSearch();
        }
        
         System.out.println("yani is  chatting ");
        //set the branch code
        String branchCode = request.getParameter("searchByBranchCode");
        search.setBranchCode(branchCode);
        
        // set branch code to null if ALL
        if (branchCode != null && search.getBranchCode().equals("ALL"))
            search.setBranchCode(null); 
        
        System.out.println("branch yani ---> "  + branchCode);
        
        // set the process type
        String processType = request.getParameter("processType");
        search.setProcess(processType);
        
        if (processType != null && processType.equals("ANY"))
            search.setProcess(null);
        
        // search the data
        data.setReplicationDetails(DataFactory.searchFlashFiguresDataByFilter
                    (search.getBranchCode(), search.getProcess()));
        
        data.setSearch(search);
        
        
    }

   
    private void processExportReplicationSummary(FlashFiguresData data, int exportType, 
            HttpServletResponse response) throws Exception {
        
        if (data == null)
            return;
        
        final String DELIMITER = ",";
        final String LINE_FEED = "\r\n";
        final String DATE_FORMAT = "yyyy MMMM dd HH:mm:ss";
        
        List<FlashFiguresEntity> details = null;
        
        // read up all the replication details
        if (exportType == FlashFiguresData.EXPORT_TYPE_FILTER) {
            details = data.getReplicationDetails();
            if (details == null)
                details = DataFactory.getFlashFiguresDetails();
        }
        
        else if (exportType == ReplicationData.EXPORT_TYPE_UNHEALTHY_BRANCHES) {
            
            List<FlashFiguresEntity> temp = DataFactory.getFlashFiguresDetails();
            details = new ArrayList<FlashFiguresEntity>();
            
            for (FlashFiguresEntity item : temp){
                if (!item.isBranchOk()) {
                    details.add(item);
                }
            }
            
            temp = null;
                
        }
            
        StringBuilder builder2 = new StringBuilder();
        
        // prepare the headings of the CSV file
       
        builder2.append("Branch").append(DELIMITER)
               .append("Period").append(DELIMITER)
               .append("Replicate Up To").append(DELIMITER)
               .append("Flash Up To").append(DELIMITER)
               .append("Difference").append(DELIMITER)
               .append("Locked").append(DELIMITER)
               .append("Last Flash").append(DELIMITER)
               .append("Comment").append(LINE_FEED);
        
        
        // append each detail to the CSV content
        
        if (details != null && !details.isEmpty()) {
            
            for (FlashFiguresEntity item : details) {
                
                builder2.append(item.getBranchCode()).append(DELIMITER);
                builder2.append(item.getPeriod()).append(DELIMITER);
                builder2.append(item.getReplicate()).append(DELIMITER);
                builder2.append(item.getFlashAudUpTo()).append(DELIMITER);
                builder2.append(item.getDifference()).append(DELIMITER);
                builder2.append(GeneralUtils.parseBooleanForUI(item.isLocked())).append(DELIMITER);
                builder2.append(GeneralUtils.formatDate(item.getFlashAudDate(), DATE_FORMAT)).append(DELIMITER);
                        
                if (item.getComments() != null && !item.getComments().isEmpty()) {
                    StringBuilder comments = new StringBuilder();
      
                    for (String comment : item.getComments()) {
                        comments.append(comment).append(" ");
                    }
                    
                    builder2.append(comments.toString()).append(LINE_FEED);
                }
                else
                    builder2.append("").append(LINE_FEED);
            }
            
            // write the CSV content to the file to be downloaded
            
            long tag = System.currentTimeMillis();
            String FILE_PATH = null;
            
            if (GeneralUtils.getOperationSystemName().contains("Windows"))
                FILE_PATH = "C:\\Users\\" + GeneralUtils.getContextUsername() 
                        + "\\AppData\\Local\\Temp\\" + tag + ".csv";
            else
                FILE_PATH = "/tmp/" + tag + ".csv";
            
            File downloadFile = new File(FILE_PATH);
            if (downloadFile.exists())
                downloadFile.delete();
            
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(downloadFile, true));
            writer.write(builder2.toString());
            writer.flush();
            writer.close();
            
            // obtain the servlet context
            ServletContext context = getServletContext();
            
            // get the MIME type for the file
            String mimeType = context.getMimeType(FILE_PATH);
            if (mimeType == null)
                mimeType = "application/octet-stream";
            
            // modify the response header
            response.setContentType(mimeType);
            response.setContentLength((int)downloadFile.length());
            
            // force download action
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);
            
            // obtains response's output stream
            OutputStream output = response.getOutputStream();
            FileInputStream input = new FileInputStream(downloadFile);

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }

            input.close();
            output.close();
            
            downloadFile.delete();
            
        }
        
    }
    
}
