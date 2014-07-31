<%-- 
    Document   : monthendOverview.jsp
    Created on : Jun 21, 2014, 10:17:03 AM
    Author     : rnaidoo
--%>

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
    MonthendData data = (MonthendData)session.getAttribute(SessionAttribute.MONTHEND_DATA_TAG);
    if (data == null) {
        response.sendRedirect(WebPages.BASE_APP_URL + "/MonthEndProduction?tab=processes"); 
        return;
    }
    
    List<GLSubType> details = data.getGlSubType();
    System.out.println("details-- > " + details.size());
    if (details == null)
        details = new ArrayList<GLSubType>();
    
    List<GLMapActTyp> glMapActTyp = data.getGlMapActType();
    System.out.println("glMapActTyp-- > " + glMapActTyp.size());
    if (glMapActTyp == null)
        glMapActTyp = new ArrayList<GLMapActTyp>();
  
    List<MonthendProcesses> processes = data.getMonthendProcesses();
    System.out.println("processes-- > " + processes.size());
    if (processes == null)
        processes = new ArrayList<MonthendProcesses>();
    
%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
    <body>
        
         <div class="content" id="content">
            
            <%@include file="master/monthend-menu.jspf" %>
         </div>
        
        
        
         <div class="header">
                <div class="wrapper" ><p>Monthend Process Page</p></div>
          </div>
         
        
        
        <div class="subHeader"><center>Active Monthend Processes</center></div>   
                    
              
        
          <table border="0" cellspacing="2" cellpadding="2" width="50%">
               
                     <tr class="RowToClick"> 
                    
                        
                             <td style="text-align: center">Process Code</td>
                              <td style="text-align: center">Process Name</td>
                              <td style="text-align: center">Process Method</td>
                              <td style="text-align: center">Process Class</td>
                            
                        
                   
                     </tr>
                 
                   <!-- <tbody> -->
                        <% 
                            int count = 0;    
                            for (MonthendProcesses entity : processes){
                                  ++count;
                                boolean isEven = (count % 2 == 0);                            
                                
                               %>
                            
                            <tr class="<%= isEven ? "dataRowEven" : "dataRowOdd" %>">       
                               
                                <td><%= entity.getProdCde() %></td>
                                <td><%= entity.getProdClassDesc() %></td>
                                <td><%= entity.getProdMethod() %></td>
                                <td><%= entity.getProdClass() %></td>
                            </tr>  
                            
                        <% } %>
                        
                        
                 <!--  </tbody> -->
          </table>
        
                        <br><br>
        
                        <div class="subHeader"><center>How to run processes from command line</center></div>
                        <p></p>
                        <ul class="bigText">Log onto c9910.fm.co.za</ul>
                        <ul class="bigText">To run GL (process 100) for branch 0017</ul>
                        <ul class="bigText">x.step.pprocess -p 100 -b 0017</ul>
                        
                        <p></p>
                        
                        
        
        
        <div class="subHeader"><center>Missing GL Sub Type Map Action Types</center></div>   
               
              
        
          <table border="0" cellspacing="2" cellpadding="2" width="50%">
                    
                    
                        <% 
                            int count2 = 0;    
                            for (GLSubType entity : details){
                                  ++count2;
                                boolean isEven = (count2 % 2 == 0);                            
                                
                               %>
                            
                            <thead>
                         <tr class="headerRow"> 
                             <th style="text-align: center">Action Type</th>
                             <th style="text-align: center">Sub Type</th>
                             
                            
                        </tr>
                    </thead>                    
                 
                    <tbody>   
                            <tr class="<%= isEven ? "dataRowEven" : "dataRowOdd" %>">
                                <td><%= entity.getGlActType() %></td>
                                <td><%= entity.getGlActDesc()%></td>
                                <td><%= entity.getGlSubType() %></td>
                            </tr>
                             </tbody>
                            
                        <% } %>
                        
                        
                  
          </table>
                        <br><br>
                        
                        
          <div class="subHeader"><center>Missing GL Map Action Types</center></div>                 
                        
          <table border="0" cellspacing="2" cellpadding="2" width="50%">
                    
                    <thead>
                         <tr class="headerRow"> 
                             <th style="text-align: center">Action Type</th>
                              <th style="text-align: center">Action Description</th>                              
                            
                        </tr>
                    </thead>                    
                 
                    <tbody>
                        <% 
                            int count3 = 0;    
                            for (GLMapActTyp entity : glMapActTyp ){
                                  ++count3;
                                boolean isEven = (count3 % 2 == 0);                            
                                
                               %>
                            
                            <tr class="<%= isEven ? "dataRowEven" : "dataRowOdd" %>">
                                <td style="text-align: left"><%= entity.getActType() %></td>
                                <td style="text-align: left"><%= entity.getActDesc() %></td>
                            </tr>  
                            
                        <% } %>
                        
                        
                   </tbody>
          </table>
                        <br><br>              
                        

    </body>
</html>

