<%-- 
    Document   : daily-bi-extracts
    Created on : Apr 4, 2014, 8:56:58 AM
    Author     : tmaleka
--%>

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
<%@page import="java.util.TreeMap"%>
<%@page import="za.co.argility.furnmart.servlet.helper.DailyBIExtractsData"%>

<%
    DailyBIExtractsData data = (DailyBIExtractsData)session.getAttribute(SessionAttribute.DAILY_BI_EXTRACTS_DATA_TAG);
    if (data == null)
        response.sendRedirect(WebPages.BASE_APP_URL + "/dailybiextracts"); 
    
    BucketMap bucket = data.getExtractHistory();
    if (bucket == null)
        return;
    
    HashMap<String, Date> extractsLastSent = data.getLastExtractFilesSentMap();
    
    Date date = data.getCurrentDay();
    SimpleDateFormat headerDateFormat = null;
    
    SimpleDateFormat datePickerFormat = new SimpleDateFormat("dd/MM/yyyy");
     
    
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>BI Extracts | Furnmart Daily Report</title>
        
        <%@include file="master/global-header.jspf" %>
        
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css" />
        <link type="text/css" rel="stylesheet" href="stylesheets/biExtracts.css" />
        <script type="text/javascript" src="scripts/biExtracts.js"></script>
        
    </head>
    
    <body onload="initialise()">
       
        <%@include file="master/loadingPanel.jspf" %>
        
        <div class="content" id="content">
            
            <%@include file="master/menu.jspf" %>
            
            <div class="header">
                <div class="wrapper">DAILY BI EXTRACTS</div>
            </div>
            
            <div class="subContentArea" style="border-bottom: 1px solid gray; font-size:14px">
                
                <form name="pickDayBIExtractsForm" id="pickDayBIExtractsForm" method="get" 
                      action="<%= WebPages.BASE_APP_URL %>/dailyBIExtracts"
                      onsubmit="return validatePickDayForExtracts(this)">
                    
                    <table border="0" cellspacing="2" cellpadding="2" width="50%">
                        <tr>
                            <td valign="top">
                                <label>PICK A DAY:</label> <br/>
                                <input type="text" name="day" id="datepicker"
                                       value="<%= datePickerFormat.format(date) %>"
                                       class="textBox"/>
                            </td>
                            <td valign="top">
                                <label>PROCESS TYPE: </label><br/>
                                <%
                                    if (data.getCurrentTab().equalsIgnoreCase(DailyBIExtractsData.TAB_DAILY)) {
                                %>
                                    <input type="radio" name="tab" value="daily" checked="checked"/> Day End <br/>
                                    <input type="radio" name="tab" value="monthly" /> Month End
                                <%
                                    }
                                    
                                    else {
                                %>
                                    <input type="radio" name="tab" value="daily"/> Day End <br/>
                                    <input type="radio" name="tab" value="monthly" checked="checked" /> Month End
                                <%
                                    }
                                %>
                                
                            </td>
                            <td valign='bottom'>
                                <input type="submit" value="Lookup" name="submitForm" class="button"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <p class="alertText">NB: If you checked <b>Month End</b>, pick any date WITHIN that financial period.</p>
                            </td>
                        </tr>
                    </table>
                     
                                
                                
                </form>
                       
            </div>
            
         
            <div class="subContentArea">
                
                <%
                    
                  
                  String tab = data.getCurrentTab();
                  if (tab.equals(DailyBIExtractsData.TAB_DAILY))
                      headerDateFormat = new SimpleDateFormat("dd MMMM yyyy");
                  else
                       headerDateFormat = new SimpleDateFormat("yyyyMM");
                  
                  String displayDate = headerDateFormat.format(date); 
                  
                %>
                
                    <div class="subHeader">
                        DISPLAYING <%= tab.toUpperCase() %> BI EXTRACTS FOR: <%= displayDate %>
                    </div>

                    <div>
                        
                        <%
                            int results = 0;
                            if (bucket.keySet() != null)
                                results = bucket.keySet().size();
                            
                            boolean singleResult = (results == 1);
                        %>
                        
                        <p>Returned <b><%= singleResult ? (results + "</b> result ") : (results + "</b> results ") %>
                            for the selected date and process type.</p>
                        
                        <table border="0" class="biExtractsTable">
                            <thead>
                            <tr class="headerRow">
                                <th></th>
                                <th>COMMON EXTRACTS</th>
                                <th>SALES EXTRACTS</th>
                                <th>STOCK EXTRACTS</th>
                                <th>PROMOTIONS EXTRACTS</th>
                                <th>DEBTORS EXTRACTS</th>
                                <th>LAST SUCCESSFUL COPY</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                Set<String> branches = bucket.keySet();
                                int count = 0;
                                
                                if (results > 0) {
                                     
                                    for (String branchCode : branches.toArray(new String[results])) { 
                                        
                                        count++;
                                        boolean isEven = (count % 2 == 0);

                                        TreeMap<Integer, ExtractHistory> temp = new TreeMap<Integer, ExtractHistory>();

                                        List<ExtractHistory> list = bucket.get(branchCode);
                                        for (ExtractHistory item : list) {
                                            temp.put(new Integer(item.getExtractType().getExtractType()), item);  
                                        }

                                        ExtractHistory history = null;
                                %>

                                <tr class="<%= isEven ? "dataRowEven" : "dataRowOdd" %>">
                                    <td style="width:200px">
                                        <p><span class="bigText"><%= branchCode %></span></p>
                                    </td>
                                    <td>
                                        <% 
                                            history = temp.get(new Integer(ExtractType.COMMON_EXTRACT));
                                            if (history != null) {
                                               out.print(history.toHtml());
                                            }

                                            else {
                                                %>
                                                <img src="images/error.png" style="width:36px" alt="error" />
                                                <p>Extracts did not run.</p>
                                              <%
                                            }
                                        %>
                                    </td>
                                    <td>
                                        <% 
                                            history = temp.get(new Integer(ExtractType.SALES_EXTRACT));
                                            if (history != null) {
                                               out.print(history.toHtml());
                                            }

                                            else {
                                                %>
                                                <img src="images/error.png" style="width:36px" alt="error" />
                                                <p>Extracts did not run.</p>
                                              <%
                                            }
                                        %>
                                    </td>
                                    <td>
                                        <% 
                                            history = temp.get(new Integer(ExtractType.STOCK_EXTRACT));
                                            if (history != null) {
                                               out.print(history.toHtml());
                                            }

                                            else {
                                                %>
                                                <img src="images/error.png" style="width:36px" alt="error" />
                                                <p>Extracts did not run.</p>
                                              <%
                                            }

                                        %>
                                    </td>
                                    <td>
                                        <% 
                                            history = temp.get(new Integer(ExtractType.PROMO_EXTRACT));
                                            if (history != null) {
                                               out.print(history.toHtml());
                                            }

                                            else {
                                                %>
                                                <img src="images/error.png" style="width:36px" alt="error" />
                                                <p>Extracts did not run.</p>
                                              <%
                                            }
                                        %>
                                    </td>
                                    <td>
                                        <% 
                                            history = temp.get(new Integer(ExtractType.DEBTORS_EXTRACT));
                                            if (history != null) {
                                               out.print(history.toHtml());
                                            }
                                            else {
                                                %>
                                                <img src="images/error.png" style="width:36px" alt="error" />
                                                <p>Extracts did not run.</p>
                                              <%
                                            }
                                        %>
                                    </td>
                                    <td>
                                         <%
                                                Date lastSent = extractsLastSent.get(branchCode);
                                                if (lastSent != null) {
                                                    
                                                    %>
                                                        <img src="images/files_sent.png" alt="files sent" style="width: 36px" />
                                                    <%
                                                    
                                                    if (Hours.hoursBetween(new DateTime(lastSent), new DateTime(new Date())).getHours() > 24) {                                   
                                                        %>
                                                        <p class="alertText">
                                                            <%= new SimpleDateFormat("dd MMMM yyyy HH:mm").format(lastSent) %> 
                                                        </p>
                                                    <%
                                                    }
                                                    
                                                    else {
                                                    %>
                                                         <p>
                                                            <%= new SimpleDateFormat("dd MMMM yyyy HH:mm").format(lastSent) %> 
                                                        </p>
                                                        <% 
                                                    }
                                                    
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
            
        </div>
        
    </body>
</html>
