<%-- 
    Document   : daily-itc-extracts
    Created on : 15-Feb-2016, 11:52:16
    Author     : mbalenhle ndaba
--%>

<%@page import="za.co.argility.furnmart.servlet.helper.DailyITCExtractsData"%>
<%@page import="org.joda.time.Hours"%>
<%@page import="org.joda.time.DateTime"%>
<%@page import="org.joda.time.Days"%>
<%@page import="java.util.TreeSet"%>
<%@page import="java.util.HashMap"%>
<%@page import="za.co.argility.furnmart.util.BucketMap"%>
<%@page import="za.co.argility.furnmart.entity.ExtractType"%>
<%@page import="java.util.ArrayList"%>
<%@page import="za.co.argility.furnmart.entity.ExtractHistory"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Set"%>


<%
    DailyITCExtractsData data = (DailyITCExtractsData)session.getAttribute(SessionAttribute.DAILY_ITC_EXTRACTS_DATA_TAG);
    if (data == null)
        response.sendRedirect(WebPages.BASE_APP_URL + "/dailyitcextracts"); 
    
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
        <title>ITC Extracts | Furnmart Daily Report</title>
        
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
                      headerDateFormat = new SimpleDateFormat(DailyITCExtractsData.DATE_FORMAT);
                
                %>
                
                     <div class="subContentArea" style="border-bottom: 1px solid gray; font-size:14px; text-align:center ">
                        <table border="0" cellspacing="2" cellpadding="2" width="50%">
                            <tr>
                                <input type="submit" value="VIEW BRANCHES" name="viewBranches"  class="button"/>
                            </tr>
                        </table> 
                    </div>

                    <div>
                        
                        <%
                            int results = 0;
                            if (bucket != null)
                                results = bucket.size();
                            
                            boolean singleResult = (results == 1);
                        %>
                        
                        <p>Returned <b><%= singleResult ? (results + "</b> result ") : (results + "</b> results ") %></p>
                        
                        <table border="0" class="biExtractsTable">
                            <thead>
                            <tr class="headerRow"> 
                                <th style="text-align:center">DATE</th>
                                <th style="text-align:center">STATUS</th>
                                <th style="text-align:center">STATUS DETAIL</th>
                            </tr>
                            </thead>
                            <tbody>
                            
                                <%
                                  int count = 0;
                                    if (results > 0) {
                                        for (ExtractHistory itcHist : bucket) {
                                           
                                            count++;
                                            boolean isEven = (count % 2 == 0);
                                %>

                                <tr class="<%= isEven ? "dataRowEven" : "dataRowOdd" %>" >
                                    <td style="text-align:center">
                                        <p><span ><%= headerDateFormat.format(itcHist.getStartTime()) %></span></p>
                                    </td>
                                    <td style="text-align:center">
                                        <% 
                                            if (itcHist.getStatus()) {
                                        %>
                                            <img src="images/ok.png" style="width:36px" alt="ok" />
                                            <p>SUCCESSFUL</p>
                                        <%
                                             }
                                            else {
                                        %>
                                            <img src="images/error.png" style="width:36px" alt="error" />
                                            <p class="alertText">FAILED</p>
                                        <%
                                            }
                                        %>                                          
                                    </td>
                                    <td style="text-align:center">
                                        <% 
                                            if (itcHist.getReason() != null) {
                                        %>
                                            <p class="alertText"><%= itcHist.getReason() %></p>
                                        <%
                                             }
                                            else {
                                        %>         
                                            <p></p>
                                        <%
                                            }
                                        %>  
                                       
                                    </td>
                               
                                </tr>

                                <%
                                        }
                                    }
                                
                            %>
                            </tbody>
                        </table>
                    </div>
                            
            </div>
                            
           </form>
      
        </div>
        
    </body>
</html>

