<%-- 
    Document   : view-itc-branches
    Created on : 11-Mar-2016, 09:55:05
    Author     : mbalenhle ndaba
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="za.co.argility.furnmart.entity.ExtractHistory"%>
<%@page import="java.util.List"%>
<%@page import="za.co.argility.furnmart.servlet.helper.DailyITCExtractsData"%>

<%
    DailyITCExtractsData data = (DailyITCExtractsData)session.getAttribute(SessionAttribute.VIEW_ITC_BRANCHES_DATA_TAG);
    if (data == null)
        response.sendRedirect(WebPages.BASE_APP_URL + "/viewitcbranches"); 
    
    List<ExtractHistory> bucket = data.getItcExtract();
    if (bucket == null)
        bucket = new ArrayList<ExtractHistory>();
    
    
    Date date = data.getCurrentDay();
    SimpleDateFormat headerDateFormat = null; 
    
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View ITC Branches | Furnmart Daily Report</title>
        
        <%@include file="master/global-header.jspf" %>
        
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css" />
        <link type="text/css" rel="stylesheet" href="stylesheets/biExtracts.css" />
        <script type="text/javascript" src="scripts/biExtracts.js"></script>
    </head>
    <body>
        <%@include file="master/loadingPanel.jspf" %>
        
        <div class="content" id="content">
            
            <%@include file="master/menu.jspf" %>
            
            <div class="header">
                <div class="wrapper">DAILY FILE SUBMISSION</div>
            </div>
            
            <form name="pickDayBIExtractsForm" id="pickDayBIExtractsForm" method="get" 
                      action="<%= WebPages.BASE_APP_URL %>/viewITCBranches"
                      onsubmit="return validatePickDayForExtracts(this)">
                
                <div class="subContentArea">
                    <%
          
                 
                %>
                    
                    <div class="subContentArea" style="border-bottom: 1px solid gray; font-size:14px; text-align:center ">
                        <%
                            int results = 0;
                            if (bucket != null)
                                
                                results = bucket.size();
                            
                            boolean singleResult = (results == 1);
                                
                        %>
                        <p>Only displaying branches with days behind > 1</p>
                    
                    </div>
                
                    <div>
                       
                        <table border="0" class="biExtractsTable">
                            <thead>
                            <tr class="headerRow"> 
                                <th style="text-align:center">BRANCH</th>
                                <th style="text-align:center">CHECKPOINT AUDIT</th>
                                <th style="text-align:center">CHECKPOINT DATE</th>
                                <th style="text-align:center">DAYS BEHIND > 1</th>
                            </tr>
                            </thead>
                            <tbody>
                                <%
                                    int count = 0;
                                      if (results > 0) {
                                        for (ExtractHistory itcHist : bucket) {
                                            if (itcHist.getDaysBehind() > 0) {
                                            count++;
                                            boolean isEven = (count % 2 == 0);
                                %>

                                <tr class="<%= isEven ? "dataRowEven" : "dataRowOdd" %>" >
                                
                                    <td style="text-align:center">
                                        <p><span class="bigText"><%= itcHist.getBranch() %></span></p>
                                    </td>
                                    <td style="text-align:center">
                                         <p><span ><%= itcHist.getCheckpointAudit() %></span></p>
                                    </td>
                                    <td style="text-align:center">
                                         <p><span ><%= itcHist.getCheckpointDate() %></span></p>
                                    </td>
                                    <td style="text-align:center">
                                        <% 
                                               if(itcHist.getDaysBehind() > 0) {
                                        %>    
                                                <img src="images/error.png" style="width:36px" alt="error" />
                                                <p class="alertText"><span ><%= itcHist.getDaysBehind() %></span> </p>
                                        <%
                                               }
                                               else {
                                        %>
                                                <img src="images/ok.png" style="width:36px" alt="ok" />
                                                <p><%= itcHist.getDaysBehind() %> </p>
                                        <%
                                               }
                                        %>
                                    </td>
                                </tr>

                                <%
                                            }
                                         }
                                      }
                                %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </form>
        
    </body>
</html>
