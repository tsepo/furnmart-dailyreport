<%-- 
    Document   : monthend-process-status.jsp
    Created on : Feb 16, 2017, 18:32:18 AM
    Author     : rnaidoo
--%>

<%@page import="za.co.argility.furnmart.entity.MonthendDetailStatusEntity"%>
<%@page import="za.co.argility.furnmart.entity.GLDetailEntity"%>
<%@page import="za.co.argility.furnmart.entity.GLEntity"%>
<% 
    // get the data from the session
    
    MonthendData data = (MonthendData)session.getAttribute(SessionAttribute.MONTHEND_DATA_TAG);
    if (data == null) {
        response.sendRedirect(WebPages.BASE_APP_URL + "/MonthEndProduction?tab=prod"); 
        return;
    }
    
    List<MonthendDetailStatusEntity> details = data.getMonthendDetailStatusEntity();
    System.out.println("details in detail Page-- > " + details.size());
    if (details == null)
        details = new ArrayList<MonthendDetailStatusEntity>();
    
 %>   



<%@page import="za.co.argility.furnmart.util.WebPages"%>
<%@page import="za.co.argility.furnmart.servlet.helper.MonthendData"%>
<%@page import="za.co.argility.furnmart.entity.MonthendEntity"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="za.co.argility.furnmart.entity.ReplicationEntity"%>
<%@page import="java.util.List"%>
<%@page import="za.co.argility.furnmart.servlet.helper.ReplicationData"%>
<%@page import="za.co.argility.furnmart.servlet.SessionAttribute"%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>Gl Debtors Detail Page</title>
          <%@include file="master/global-header.jspf" %>
          <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css" />
           <link type="text/css" rel="stylesheet" href="stylesheets/biExtracts.css" />
           <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
          <script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>  
           <script type="text/javascript" src="scripts/monthendProcesses.js"></script>
    </head>
    <body>
        <div class="content" id="content">
             <%@include file="master/monthend-menu.jspf" %>
         </div>
         <div class="header">
                <div class="wrapper" ><p>Monthend Process Detail Detail Page (<%=data.getProcessStatusDetailBranchCode() %>)</p></div>
         </div>
         <br><br>
        
                <table border="0" cellspacing="2"padding="2" width="50%">
                          <tr class="RowToClick"> 
                            <td  span class="bigText" style="text-align: center">Branch</td>
                            <td  span class="bigText" style="text-align: center">Process</td>                              
                            <td  span class="bigText" style="text-align: center">Proc Start Date</td>
                            <td  span class="bigText" style="text-align: center">Proc End Date</td>  
                            <td  span class="bigText" style="text-align: center">Proc Error Code</td>  
                          <tr></tr>
                           <tr></tr>
                            </tr>
                            <br><br>
                            
                              <% int count = 0;    
                            for (MonthendDetailStatusEntity entity : details){ 
                                ++count;
                                boolean isEven = (count % 2 == 0);  
                               
                                  
                              %>
                            
                            
                            <tr class="<%= isEven ? "dataRowEven" : "dataRowOdd" %>">
                                
                                <% if(entity.getErrorCde() > 0){ %>
                                <td  span class="smallText" style="text-align: center"> <font color="red"><%= entity.getBrCde()%></font></td>
                                    <td  span class="smallText" style="text-align: left"> <font color="red"><%= entity.getMonthendProcess()%></font></td>
                                    <td  span class="smallText" style="text-align: center"> <font color="red"><%= entity.getProcessStartDte()%></font></td>
                                    <td  span class="smallText" style="text-align: center"> <font color="red"><%= entity.getProcessEndDte() %></font></td>
                                    <td  span class="smallText" style="text-align: right"> <font color="red"><%= entity.getErrorCde() %></font></td>
                                <% }else{ %> 
                                    <td  span class="smallText" style="text-align: center"><%= entity.getBrCde()%></td>
                                    <td  span class="smallText" style="text-align: left"><%= entity.getMonthendProcess()%></td>
                                    <td  span class="smallText" style="text-align: center"><%= entity.getProcessStartDte()%></td>
                                    <td  span class="smallText" style="text-align: center"><%= entity.getProcessEndDte() %></td>
                                    <td  span class="smallText" style="text-align: right"><%= entity.getErrorCde() %></td>
                                <% } %> 
                            </tr>
                            
                            <%}                       
                           
                        %>
         </table>
    </body>
</html>
