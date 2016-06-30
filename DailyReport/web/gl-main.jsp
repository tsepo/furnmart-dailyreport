<%-- 
    Document   : gl_main
    Created on : Aug 20, 2014, 8:06:10 AM
    Author     : rnaidoo
--%>

<%@page import="za.co.argility.furnmart.entity.MonthendSearchEntity"%>
<%@page import="za.co.argility.furnmart.entity.ReplicationSearchEntity"%>
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
    
    
    List<String> branches = data.getMonthendBranchList();
    if (branches == null)
        branches = new ArrayList<String>(); 
    branches.add("ALL");
    branches.add("OUT OF BALANCE");
    System.out.println("branches-- > " +  branches.size());
    
    // process types
    List<String> fppCdes = data.getFppCdeList();
    if (fppCdes == null)
        fppCdes = new ArrayList<String>();
    
    String fppCode = data.getSelectedFppCde();
    
    
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
        
        <%@include file="master/loadingPanel.jspf" %>
        
        <div class="content" id="content">
             <%@include file="master/monthend-menu.jspf" %>
         </div>
        
        
        
         
         <br><br>
           <form name="glSummaryForm" method="get" action="MonthEndProduction">
       
               
               <hr></hr>
         <div style="margin-left: 10px">  
             <br><br>
             <table><tr>
             
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
                <div class="wrapper" ><p>GL Balancing Summary Page (<%= fppCode %>)</p></div>
           
          </div>                               
        
        
        
                
           <table border="0" cellspacing="2"padding="2" width="50%">
               <tr></tr>
               <tr></tr>
                  
                         <tr class="RowToClick"> 
                              <td style="text-align: center">Branch Code</td>
                              <td style="text-align: center">Branch Name</td>                              
                            <td>Instore Debtors</td>     
                            <td>Gl Debtors</td>
                            <td>Instore Stock</td>
                            <td>GL Stock</td>
                            <td>Opening Debtors</td>
                            <td>Closing Debtors</td>
                            <td>Opening Financial Stock</td>
                            <td>Closing Financial Stock</td>
                            <td>Opening Physical Stock</td>
                            <td>Closing Physical Stock</td>
                            <td>ME Debtors Balance</td>
                            <td>ME Physical Stock</td>
                            <td>ME Financial Stock</td>
                            <td>Debtors Financial Movement</td>
                            <td>Stock Financial Movement</td>
                            <td>Status</td>
                            
                       
                        </tr>
                        <% int count = 0; 
                           int count2 = 0; 
                            for (GLEntity entity : details){ 
                                ++count;
                                boolean isEven = (count % 2 == 0);  
                                String status = "images/ok.png"; 
                                if(!data.isIsAllGLSelected()){
                                    if(entity.isGlInBalance()){                                       
                                            continue; 
                                    }else{
                                        status = "images/error.png";
                                        ++count2;
                                    }
                                }else{
                                     if(entity.isGlInBalance()){
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
                                <td><%= entity.getStrDebtorsBF() %></td>
                                <td><%= entity.getStrDebtorsCF() %></td>
                                <td><%= entity.getStrFinStockBF() %></td>
                                <td><%= entity.getStrFinStockCF() %></td>
                                <td><%= entity.getStrPhysStockBF() %></td>
                                <td><%= entity.getStrPhysStockCF() %></td>
                                <td><%= entity.getStrInstoreDebtorsCF() %></a></td>
                                <td><%= entity.getStrInstorePhysStockCF() %></a></td>
                                <td><%= entity.getStrInstoreFinStockCF() %></a></td>
                                <td><%= entity.getStrDebtorsFinancialMovement() %></a></td>
                                <td><%= entity.getStrStockFinancialMovement() %></a></td>
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
                       

