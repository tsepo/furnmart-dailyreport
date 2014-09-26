<%-- 
    Document   : gl_main
    Created on : Aug 20, 2014, 8:06:10 AM
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
    
    List<GLEntity> details = data.getGlDetails();
    System.out.println("details-- > " + details.size());
    if (details == null)
        details = new ArrayList<GLEntity>();
    
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
        
        <title>Gl Balancing page</title>
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
                <div class="wrapper" ><p>GL Balancing Summary Page</p></div>
          </div>
         
         <br><br>
           <form name="glSummaryForm" method="get" action="MonthEndProduction">
         
         <div style="margin-left: 10px">  
             <br><br>
             <table border="0" cellspacing="2" cellpadding="2" width="50%">
             <tr>
             <td valign="top">
             <label>Selection: </label><br/>
             <input type="radio" name="glSelect" value="all" checked="checked"/> All</br>                   
             <input type="radio" name="glSelect" value="unbalanced" /> Out Of Balance
             </td>
              <td valign='bottom'>
             <input type="submit" value="search" name="submitForm" class="button"/>
              </td></tr></table>
             <br><br><hr>
             
        </div>
        
        
        <br><br>
      
           <table border="0" cellspacing="2"padding="2" width="50%">
                    
                  
                         <tr class="RowToClick"> 
                              <td style="text-align: center">Branch Code</td>
                              <td style="text-align: center">Branch Name</td>                              
                            <td>Instore Debtors</td>     
                            <td>Gl Debtors</td>
                            <td>Instore Stock</td>
                            <td>GL Stock</td>
                            <td>Status</td>
                            
                       
                        </tr>
                        <% int count = 0; 
                           int count2 = 0; 
                            for (GLEntity entity : details){ 
                                ++count;
                                boolean isEven = (count % 2 == 0);  
                                String status = "images/ok.png"; 
                                if(!data.isIsAllGLSelected()){
                                    if(entity.getInstoreDebtors() == entity.getGlDebtors() && 
                                            entity.getInstoreStock() == entity.getGlStock()){                                       
                                            continue; 
                                    }else{
                                        status = "images/error.png";
                                        ++count2;
                                    }
                                }else{
                                     if(entity.getInstoreDebtors() == entity.getGlDebtors() && 
                                            entity.getInstoreStock() == entity.getGlStock()){
                                            status = "images/ok.png";                                        
                                    }else{
                                        status = "images/error.png";
                                    }
                                    ++count2; 
                                }
                        %>
                            
                            <tr class="<%= isEven ? "dataRowEven" : "dataRowOdd" %>">
                                <td span class="bigText" style="text-align: center"><%= entity.getBranchCode()  %></td>
                                <td span class="smallText" style="text-align: center"><%= entity.getBranchDesc()  %></td>
                                <td><a title="Click on link for detailed detobrs balancing." href="MonthEndProduction?branchNo=<%= entity.getBranchCode()  %>&type=debtors" ><%= entity.getInstoreDebtors() %></a></td>
                                <td><a title="Click on link for detailed detobrs balancing." href="MonthEndProduction?branchNo=<%= entity.getBranchCode()  %>&type=debtors" ><%= entity.getGlDebtors() %></a></td>
                                <td><a title="Click on link for detailed stock balancing." href="MonthEndProduction?branchNo=<%= entity.getBranchCode()  %>&type=stock" ><%= entity.getInstoreStock() %></a></td>
                                <td><a title="Click on link for detailed stock balancing." href="MonthEndProduction?branchNo=<%= entity.getBranchCode()  %>&type=stock" ><%= entity.getGlStock() %></a></td>
                                <td style="text-align: center"><img src="<%= status %>" style="width:36px" /></td> 
                             </tr>
                            
                            <%}                       
                           
                        %>
              
          </table>
                        
                        <div style="margin-left: 10px">  
                            <br><br><hr>
            <h3>Returned GL Balancing results for <strong> <%= count2 %></strong> stores.</h3>
        </div>
                        
           </form>
    </body>
</html>
                       

