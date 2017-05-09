<%-- 
    Document   : monthend-processes-run.jsp
    Created on : Jul 12, 2016, 6:28:03 AM
    Author     : rnaidoo
--%>

<%@page import="za.co.argility.furnmart.entity.GLEntity"%>
<%@page import="za.co.argility.furnmart.entity.MonthendSearchEntity"%>
<%@page import="za.co.argility.furnmart.entity.MonthendStatusEntity"%>
<%@page import="java.util.ArrayList"%>
<%@page import="za.co.argility.furnmart.data.DataFactory"%>
<%@page import="java.util.List"%>
<%@page import="za.co.argility.furnmart.servlet.helper.MonthendData"%>
<%@page import="za.co.argility.furnmart.servlet.helper.MeProdRunData"%>
<%@page import="za.co.argility.furnmart.servlet.SessionAttribute"%>

<%@page import="za.co.argility.furnmart.servlet.SessionAttribute"%>
<%@page import="za.co.argility.furnmart.util.WebPages"%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>


<% 
    // get the data from the session
    
    
    System.out.println("cowabunga initial-- > ");
    MonthendData data = (MonthendData)session.getAttribute(SessionAttribute.MONTHEND_DATA_TAG);
    if (data == null) {
        response.sendRedirect(WebPages.BASE_APP_URL + "/MonthEndProduction?tab=prod"); 
        return;
    }
    
      System.out.println("cOwabunga-- > ");
    
    List<MonthendStatusEntity> details = data.getMonthendStatusEntity();
 
    if (details == null)
        details = new ArrayList<MonthendStatusEntity>();
    
    List<String> branches = data.getMonthendBranchList();
    if (branches == null)
        branches = new ArrayList<String>(); 
    branches.add("ALL");
    branches.add("ERROR/S");
    System.out.println("branches-- > " +  branches.size());
    
    // process types
    List<String> fppCdes = data.getFppCdeList();
    if (fppCdes == null)
        fppCdes = new ArrayList<String>();
    
    String fppCode = data.getSelectedFppCde();
    
    
    
 %>   



<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>Gl Balancing page</title>
          <%@include file="master/global-header.jspf" %>
          <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css" />
           <link type="text/css" rel="stylesheet" href="stylesheets/biExtracts.css" />
           <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
          <script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>  
           <script type="text/javascript" src="scripts/monthendProcesses.js"></script>
    </head>
    <body>
        
        <%@include file="master/loadingPanel.jspf" %>
        
        <div class="content" id="content">
             <%@include file="master/monthend-menu.jspf" %>
         </div>
        
        
        
         
       <br><br>
           <form name="processStatusForm" method="get" action="MonthEndProduction">
       
               
               <hr></hr>
         <div style="margin-left: 10px">  
             <br><br>
             <table><tr>
             
             <table border="0" cellspacing="2" cellpadding="2" width="50%">
             <tr>
             <td valign="top">
             <label>Selection: </label><br/>
             <input type="radio" name="processStatusSelect" value="all" checked="checked"/> All</br>                   
             <input type="radio" name="processStatusSelect" value="errors" /> Error/s
             </td>
              <td valign='bottom'>
             <input type="submit" value="search" name="submitForm" class="button"/>
              </td></tr></table>
                 <table border="0" cellspacing="2" cellpadding="2" width="50%">
                     <tr>
                     <td>
                         
                                       <%
                                          MonthendSearchEntity search = data.getSearch();
                                        if (search == null) {
                                            search = new MonthendSearchEntity();
                                        }    
                                          String selectedFppCde = search.getFppCde();
                                           if (selectedFppCde == null)
                                               selectedFppCde = "";

                                           %>
                                       <select name="fppCde">
                                           <% for (String fppCde : fppCdes) {
                                               %>
                                               <option value="<%=fppCde %>"
                                                       <%  
                                                            if (fppCde.equals(selectedFppCde))
                                                                out.print("selected='selected'");
                                                       %> 
                                                       ><%=fppCde %></option>
                                               <% } %>
                                       </select>

                     </td></tr>
                 </table></tr> 
                         
             </table>                
             <br><br>
             
        </div>
       
                                       
          <div class="header">
                <div class="wrapper" ><p>Process Status Summary Page (<%= fppCode %>)</p></div>
           
          </div>                               
        
          <table border="0" cellspacing="2"padding="2" width="50%">
               <tr></tr>
               <tr></tr>
                  
                         <tr class="RowToClick"> 
                              <td style="text-align: center">Branch Code</td>
                              <td style="text-align: center">Branch Name</td> 
                               <td style="text-align: center">All Processes Run</td> 
                              <td>Status</td>                                                  
                        </tr>
                        <% int count = 0; 
                           int count2 = 0; 
                            for (MonthendStatusEntity entity : details){ 
                                ++count;
                                boolean isEven = (count % 2 == 0);  
                                String status = "images/ok.png"; 
                                String missingActions = "images/ok.png"; 
                                List<String> missingActionsList = entity.getMissingProcessList();
                                if(entity.getStatus().equals("f")){
                                    missingActions = "images/error.png"; 
                                }
                                
                                if(!entity.isAllProcessesRun()){
                                    status = "images/error.png"; 
                                }
                                
                                if(!data.isIsAllProcessStatusSelected()){
                                       System.out.println("I is here 1");
                                }else{
                                       System.out.println("I is here 2");        
                                } 
                        %>                                                            
                            <tr class="<%= isEven ? "dataRowEven" : "dataRowOdd" %>">
                               <td span class="bigText"  style="text-align: center" ><a title="Click on link for process details." href="MonthEndProduction?branchNumber=<%= entity.getBrCde() %>" ><%= entity.getBrCde() %></a></td>  
                                <td span class="smallText" style="text-align: center"><%= entity.getBrDesc()%></td>
                                <td style="text-align: center"><img src="<%= missingActions %>" style="width:36px" title="<%= missingActionsList %>"/></td>
                                <td style="text-align: center"><img src="<%= status %>" style="width:36px" /></td> 
                             </tr> 
                              <% } %> 
                        </table>            
                   
                     
                        
         <div style="margin-left: 10px">  
                            <br><br><hr>
            <h3>Returned batch process status results for <strong> <%= count %></strong> stores.</h3>
        </div>
                        
         
             
           
    </body>
</html>