<%-- 
    Document   : monthend-view-consolidations
    Created on : Jan 22, 2015, 6:22:57 AM
    Author     : rnaidoo
--%>

<%@page import="za.co.argility.furnmart.entity.ProdConsViewEntity"%>
<%@page import="za.co.argility.furnmart.entity.GLMapActTyp"%>
<%@page import="za.co.argility.furnmart.entity.GLSubType"%>
<%@page import="za.co.argility.furnmart.servlet.helper.MonthendData"%>
<%@page import="za.co.argility.furnmart.util.WebPages"%>
<%@page import="java.util.*"%>
<%@page import="za.co.argility.furnmart.servlet.*" %>
<%@page import="za.co.argility.furnmart.servlet.helper.*" %>
<%@page import="za.co.argility.furnmart.servlet.SessionAttribute"%>

<%
    // get the data from the session
    MonthendData data = (MonthendData)session.getAttribute(MonthendData.class.getName());
    if (data == null) {
        response.sendRedirect(WebPages.BASE_APP_URL + "/MonthEndConsolidation?tab=consView"); 
        return;
    }
    
    
    List<ProdConsViewEntity> prodConsViewEntities = data.getProdConsViewEntities();
    if (prodConsViewEntities == null)
        prodConsViewEntities = new ArrayList<ProdConsViewEntity>();
    System.out.println("prodConsViewEntites-- > " + prodConsViewEntities.size());
    
   
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
       <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>Furnmart Monthend Consolidations View Page</title>
         <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css" />
           <link type="text/css" rel="stylesheet" href="stylesheets/biExtracts.css" />
         <%@include file="master/global-header.jspf" %>
         <script type="text/javascript" src="scripts/monthendProcesses.js"></script>
         <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
          <script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>         
    </head>
    <body>
         <%@include file="master/loadingPanel.jspf" %>
        
         <div class="content" id="content">
            
            <%@include file="master/monthend-menu.jspf" %>
         </div>
        
        
        
         <div class="header">
                <div class="wrapper" ><p>Monthend Consolidations View Page</p></div>
          </div>       
        
                           
          
        
          <table border="0" cellspacing="2" cellpadding="2" width="50%">
               
                     <tr class="RowToClick"> 
                    
                        
                              <td style="text-align: center">Process Code</td>
                              <td style="text-align: center">Description</td>
                              <td style="text-align: center">Period</td>
                              <td style="text-align: center">Start Date</td>
                              <td style="text-align: center">End Date</td>
                              <td style="text-align: center">Status</td>
                                           
                     </tr>
                                   
                        <% 
                            int count = 0;    
                            for (ProdConsViewEntity entity : prodConsViewEntities){
                                  ++count;
                                boolean isEven = (count % 2 == 0);                            
                                
                               %>
                            
                            <tr class="<%= isEven ? "dataRowEven" : "dataRowOdd" %>">       
                               
                                <td><%= entity.getProdConsId() %></td>
                                <td><%= entity.getProdConsDescr() %></td>
                                <td><%= entity.getFppCde() %></td>
                                <td><%= entity.getProdConsStartDte() %></td>
                                <td><%= entity.getProdConsEndDte() %></td>
                                <% String status = null;
                                    if (entity.getProdConsError().equals("null")) {
                                     status = "images/ok.png"; %>
                                     <td><img src="<%= status %>" style="width:36px;"/></td>
                                <%
                                } else { 
                                     status = "images/error.png"; %>
                                     <td title="<%= ("ERROR: " + entity.getProdConsError().replaceAll("\"", "'")) %>"><img src="<%= status %>" style="width:36px;" /></td>
                                <% } %>     
                              
                            </tr>  
                            
                        <% } %>
                        
                        
                 
          </table>
        

    </body>
</html>



