<%-- 
    Document   : monthend-consolidations
    Created on : Dec 23, 2014, 11:23:15 AM
    Author     : rnaidoo
--%>



<%@page import="za.co.argility.furnmart.entity.ProdConsEntity"%>
<%@page import="za.co.argility.furnmart.util.WebPages"%>
<%@page import="java.util.ArrayList"%>
<%@page import="za.co.argility.furnmart.servlet.helper.MonthendProcesses"%>
<%@page import="java.util.List"%>
<%@page import="za.co.argility.furnmart.servlet.helper.MonthendData"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    // get the data from the session
    MonthendData data = (MonthendData)session.getAttribute(MonthendData.class.getName());
    if (data == null) {
        response.sendRedirect(WebPages.BASE_APP_URL + "/MonthEndConsolidation?tab=cons"); 
        return;
    }
    
    
    List<ProdConsEntity> prodConsEntities = data.getProdConsEntities();
    if (prodConsEntities == null)
        prodConsEntities = new ArrayList<ProdConsEntity>();
    System.out.println("prodConsEntites-- > " + prodConsEntities.size());
    
    
%>
<html>
    <head>
       <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>Furnmart Monthend Processes Page</title>
         <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css" />
           <link type="text/css" rel="stylesheet" href="stylesheets/biExtracts.css" />
         <%@include file="master/global-header.jspf" %>
         <script type="text/javascript" src="scripts/monthendProcesses.js"></script>
         <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
          <script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>         
    </head>
      
    <%@include file="master/loadingPanel.jspf" %>
        
         <div class="content" id="content">
            
            <%@include file="master/monthend-menu.jspf" %>
         </div>
        
        
        
         <div class="header">
                <div class="wrapper" ><p>Monthend Consolidation Page</p></div>
                 <br>
          </div>
         
        
         
        <% if(data.isConsRun() ==  false){ %>
            <div class="subHeader"><center>Select Monthend Consolidations</center></div>   
        <% }else { %>
            <div class="subHeader"><center>Monthend Consolidation Results</center></div>
         <% } %>    
        <form method="post" action="<%= request.getContextPath() %>/MonthEndConsolidation" >
        <table border="0" cellspacing="2" cellpadding="2" width="50%">
               
                     <tr class="RowToClick"> 
                    
                        
                             <td style="text-align: center">Extract Code</td>
                              <td style="text-align: center">Description</td>
                              
                              <% if(data.isConsRun() ==  false){ %>
                              <td style="text-align: center">Script</td>
                                <td style="text-align: center">Run</td>
                                <% }else { %>
                                 <td style="text-align: center">Start Date</td>
                                 <td style="text-align: center">End Date</td>
                                 <td style="text-align: center">Error</td>
                                
                                <% } %>
                                           
                     </tr>
                                   
                        <% 
                            int count = 0;    
                            for (ProdConsEntity entity : prodConsEntities){
                                  ++count;
                                boolean isEven = (count % 2 == 0);                            
                                
                               %>
                            
                            <tr class="<%= isEven ? "dataRowEven" : "dataRowOdd" %>">       
                               
                                <td><%= entity.getProdConsId() %></td>
                                <td><%= entity.getProdConsDesc() %></td>
                                
                                
                                 <% if(data.isConsRun() ==  false){ %>
                                  <td><%= entity.getProdConsScript() %></td> 
                                  <td><input type="checkbox" name="run" value="<%= entity.getProdConsId() %>"/></td>
                                <% }else { %>
                                  <td><%= entity.getProdConsStartDte() %></td>
                                  <td><%= entity.getProdConsEndDte() %></td>
                                  
                                     <% if (entity.getProdConsError() == null) {
                                      %>
                                      <td>No</td>
                                  <%
                                  } else { %>
                                  
                                      <td title="<%= ("ERROR: " + entity.getProdConsError()) %>">Yes</td>
                                  <% } %>    
                                         
                                     
                                                                 
                                <% } %>
                                
                               
                            </tr>  
                            
                        <% } %>
                        
                      
                 
          </table>
                        <br><br> 
                         <% if(data.isConsRun() ==  false){ %>
                        <hr>
                        
                        <center><input type="submit" name="submitCons" value="submit"></center>
                       
                        <hr>
                         <% } %>
         
</form>
        
    </body>
</html>
