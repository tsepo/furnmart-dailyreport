<%-- 
    Document   : test.jsp
    Created on : 11 Jun 2015, 1:49:16 PM
    Author     : luthando
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="za.co.argility.furnmart.data.DataFactory"%>
<%@page import="java.util.List"%>
<%@page import="za.co.argility.furnmart.servlet.helper.MonthendData"%>
<%@page import="za.co.argility.furnmart.servlet.helper.MeProdRunData"%>
<%@page import="za.co.argility.furnmart.servlet.SessionAttribute"%>

<%@page import="za.co.argility.furnmart.servlet.SessionAttribute"%>
<%@page import="za.co.argility.furnmart.util.WebPages"%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        
        
        <title> ME Prod run</title>
          <%@include file="master/global-header.jspf" %>
          <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css" />
           <link type="text/css" rel="stylesheet" href="stylesheets/biExtracts.css" />
           <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
          <script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>  
           <script type="text/javascript" src="scripts/monthendProcesses.js"></script>
           <script type="text/javascript" src="scripts/collpsableTableRows.js"></script>
    <body>
        
            <%@include file="master/loadingPanel.jspf" %>
        
         <div class="content" id="content">
            
             <center><%@include file="master/monthend-menu.jspf" %></center>
         </div>
  
<%
  MeProdRunData meProcesses = new MeProdRunData();
  DataFactory test = new DataFactory();
  
  //test.viewMeProdRun();
  List<String> branch = test.viewMeProdRun(1,0);
  
  List<String> error;
  
  
  
 
  List start = test.viewMeProdRun(0,0);
 String errorFlag = null;
  
  
  
      error = test.viewMeProdRun(10,10);
      for(int i = 0; i < error.size(); i++){
        if(error.get(i).equals("error")){
            errorFlag = "images/error.png";
         }else if(error.get(i).equals("ok")){
             errorFlag = "images/ok.png";
        }
    }
  
  
%>
 <table border="0" cellspacing="2"padding="2" width="100%">
   <tr> 
     <th style="text-align: center">Branch Code</th>
     <th style="text-align: center">Status</th>                              
     <th style="text-align: center">Debtors</th>
     <th style="text-align: center">Creditors</th> 
     <th style="text-align: center">New GL</th> 
     <th style="text-align: center">Cash Book</th> 
     <th style="text-align: center">Buckets</th>                                                  
</tr>
   
  <%
            int count = 0; 
          for(int i = 0; i < branch.size(); i++){ 
              ++count;
                boolean isEven = (count % 2 == 0);  
     %>
    <tr class="<%= isEven ? "dataRowEven" : "dataRowOdd" %>">
       
        <td id="test" style="text-align: center;font-size: 150%; animation: cubic-bezier"> <%= branch.get(i) %> </td>
            <td  style="text-align: center;font-size: 150%"><img src="<%= errorFlag %>" style="width:36px"</td>
            <td  style="text-align: center;font-size: 150%"> <%= start.get(i) %></td>
            <td  style="text-align: center;font-size: 150%"> <%= start.get(i) %></td>
            <td  style="text-align: center;font-size: 150%"> <%= start.get(i) %></td>
            <td  style="text-align: center;font-size: 150%"> <%= start.get(i) %></td>
            <td  style="text-align: center;font-size: 150%"> <%= start.get(i)%></td>
            
          
        <%
       }
      %>
   </tr>
   
   
</table>
   
    </body>
</html>
