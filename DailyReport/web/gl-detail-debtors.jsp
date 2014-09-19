<%-- 
    Document   : gl-detail
    Created on : Sep 15, 2014, 10:57:18 AM
    Author     : rnaidoo
--%>

<%@page import="za.co.argility.furnmart.entity.GLDetailEntity"%>
<%@page import="za.co.argility.furnmart.entity.GLEntity"%>
<% 
    // get the data from the session
    
    MonthendData data = (MonthendData)session.getAttribute(SessionAttribute.MONTHEND_DATA_TAG);
    if (data == null) {
        response.sendRedirect(WebPages.BASE_APP_URL + "/MonthEndProduction?tab=gl"); 
        return;
    }
    
    List<GLDetailEntity> details = data.getGlDets();
    System.out.println("details-- > " + details.size());
    if (details == null)
        details = new ArrayList<GLDetailEntity>();
    
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
                <div class="wrapper" ><p>GL Detail Debtors Page (<%=data.getGlDetailBranchCode() %>)</p></div>
         </div>
         <br><br>
        
                <table border="0" cellspacing="2"padding="2" width="50%">
                          <tr class="RowToClick"> 
                            <td style="text-align: center">Action Type</td>
                            <td style="text-align: center">Description</td>                              
                            <td>Instore</td>
                            <td>GL</td>
                            <td>Status</td>
                            </tr>
                            
                              <% int count = 0;    
                            for (GLDetailEntity entity : details){ 
                                ++count;
                                boolean isEven = (count % 2 == 0);  
                                String status = "images/ok.png"; 
                                if(entity.getInstoreVal() == entity.getGlVal()){                                       
                                    status = "images/ok.png";
                                }else{
                                    status = "images/error.png";
                                }   
                        %>
                            
                            <tr class="<%= isEven ? "dataRowEven" : "dataRowOdd" %>">
                                <td><%= entity.getActionType() %></td>
                                <td><%= entity.getDescription() %></td>
                                <td title= "Instore"><%= entity.getInstoreVal() %></td>
                                <td title = "GL"><%= entity.getGlVal() %></td>
                                <td style="text-align: center"><img src="<%= status %>" style="width:36px" /></td> 
                             </tr>
                            
                            <%}                       
                           
                        %>
         </table>
    </body>
</html>
