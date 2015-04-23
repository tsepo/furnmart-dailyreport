<%-- 
    Document   : monthend-statements
    Created on : Jun014, 9:12:12 AM
    Author     : rnaidoo
--%>

<%@page import="za.co.argility.furnmart.servlet.helper.MonthendData"%>
<%@page import="za.co.argility.furnmart.entity.MonthendEntity"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="za.co.argility.furnmart.entity.ReplicationEntity"%>
<%@page import="java.util.List"%>
<%@page import="za.co.argility.furnmart.servlet.helper.ReplicationData"%>
<%@page import="za.co.argility.furnmart.servlet.SessionAttribute"%>
<% 
    // get the data from the session
    
    MonthendData data = (MonthendData)session.getAttribute(SessionAttribute.MONTHEND_DATA_TAG);
    if (data == null) {
        response.sendRedirect(WebPages.BASE_APP_URL + "/MonthEndProduction?tab=statements"); 
        return;
    }
    
    // replication details
    List<MonthendEntity> details = data.getMonthendDetails();
    System.out.println("details-- > " + details.size());
    if (details == null)
        details = new ArrayList<MonthendEntity>();
    
    // the branch list
    List<String> branches = data.getMonthendBranchList();
    if (branches == null)
        branches = new ArrayList<String>(); 
    
    
    SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
    
%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <titlend Production Stats Page</title>
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
        <form name="statementForm" method="get" action="MonthEndProduction">
          <% 
                            String fpp = null;    
                            for (MonthendEntity entity : details){ 
                                fpp = entity.getFppCde();
                                break;
                            } %>
         
         
        
          <div class="header">
              <div class="wrapper" ><p>Furnmart Statements (<%=fpp %>)</p></div>
          </div>
          
           <div class="subContentArea">
            
             <div style="margin-left: 10px">  
             <br><br>
             <table border="0" cellspacing="2" cellpadding="2" width="50%">
             <tr>
             <td valign="top">
             <label>Selection: </label><br/>
             <input type="radio" name="statementSelect" value="all" checked="checked"/> All</br>                   
             <input type="radio" name="statementSelect" value="nostatements" /> Not Processed
             </td>
              <td valign='bottom'>
             <input type="submit" value="search" name="submitForm" class="button"/>
              </td></tr></table>
             <br><br><hr>
             
        </div> 
               
              
        
          <table border="0" cellspacing="2"padding="2" width="50%">
                    
                  
                         <tr class="RowToClick"> 
                             <td style="text-align: center">Branch Code</td>
                              <td style="text-align: center">Branch Name</td>                              
                            <td>Statements</td>     
                                                  
                        </tr>
                       
                 
                    
                        
                        
                        
                        <% 
                            int count = 0;    
                            int count2 = 0; 
                            for (MonthendEntity entity : details){ 
                                ++count;
                                boolean isEven = (count % 2 == 0);
                                String statementsFlag = "images/ok.png";
                                if(!data.isIsAllStatementsSelected()){
                                    if(entity.isIsStatementsRun()){                                       
                                            continue; 
                                    }else{
                                         statementsFlag= "images/error.png";
                                        ++count2;
                                    }
                                }else{
                                     if(entity.isIsStatementsRun()){
                                            statementsFlag = "images/ok.png";                                        
                                    }else{
                                        statementsFlag = "images/error.png";
                                    }
                                    ++count2; 
                                }                              
                         
                                       %>                          
                            
                            
                            <tr class="<%= isEven ? "dataRowEven" : "dataRowOdd" %>">
                                <td span class="bigText" style="text-align: center"><%= entity.getBranchCode()  %></td>
                                <td span class="smallText" style="text-align: center"><%= entity.getBranchDesc()  %></td>
                                <td style="text-align: center"><img src="<%= statementsFlag %>" style="width:36px;" title="statements"/></td>                                
                            </tr>
                            
                                    
                           <% }
                               
                        %>
                   
          </table>
                   <div style="margin-left: 10px">  
                            <br><br><hr>
            <h3>Returned statement results for <strong> <%= count2 %></strong> stores.</h3>
        </div>           
        </div>
        </form>                
    </body>
</html>
                       
