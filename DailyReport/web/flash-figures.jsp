<%-- 
    Document   : falsh-figures.jsp
    Created on : May 16, 2016, 1:32:20 PM
    Author     : rnaidoo
--%>


<%@page import="za.co.argility.furnmart.entity.FlashFiguresSearchEntity"%>
<%@page import="za.co.argility.furnmart.entity.FlashFiguresEntity"%>
<%@page import="za.co.argility.furnmart.servlet.helper.FlashFiguresData"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="za.co.argility.furnmart.util.WebPages"%>
<%@page import="za.co.argility.furnmart.entity.ReplicationSearchEntity"%>
<%@page import="java.util.ArrayList"%>
<%@page import="za.co.argility.furnmart.entity.ReplicationEntity"%>
<%@page import="java.util.List"%>
<%@page import="za.co.argility.furnmart.servlet.helper.ReplicationData"%>
<%@page import="za.co.argility.furnmart.servlet.SessionAttribute"%>

<% 
    // get the data from the session
    FlashFiguresData data = (FlashFiguresData)session.getAttribute(SessionAttribute.FLASH_FIGURES_DATA_TAG);
    if (data == null)
        response.sendRedirect(WebPages.BASE_APP_URL + "/flash-figures"); 
    
    // replication details
    List<FlashFiguresEntity> details = data.getReplicationDetails();
    if (details == null)
        details = new ArrayList<FlashFiguresEntity>();
    
    // the branch list
    List<String> branches = data.getReplicationBranchList();
    if (branches == null)
        branches = new ArrayList<String>(); 
    
    // process types
    List<String> processes = data.getProcesses();
    if (processes == null)
        processes = new ArrayList<String>();
    
    SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
    
%>




<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Flash Figure Details | Furnmart Daily Report</title>
        
        <%@include file="master/global-header.jspf" %>
        
        <link type="text/css" rel="stylesheet" href="stylesheets/replication.css" />
        <script type="text/javascript" src="scripts/replication.js"></script>
        
        
    </head>
    <body>
        
        <%@include file="master/loadingPanel.jspf" %>
        
        <div class="content" id="content">
            <%@include file="master/menu.jspf" %>
            
            <form method="post" name="filterReplicationForm" action="<%= WebPages.BASE_APP_URL %>/flashFigures" onsubmit="return validateInput(this)">
            
            <!-- place logic here -->
            
            <div class="header">
                <div class="wrapper" ><p>FLASH FIGURE DETAILS</p></div>
            </div>
            
            <div class="subContentArea">
            
                <div class="subHeader">SEARCH FILTERS:</div>

                <div style="margin-left: 10px; margin-bottom:30px">

                            <table border="0" width="60%">
                                <tr>
                                    <td><label>Branch:</label></td>
                                       <% 
                                        FlashFiguresSearchEntity search = data.getSearch();
                                        if (search == null) {
                                            search = new FlashFiguresSearchEntity();
                                        }

                                        String selectedBranch = search.getBranchCode();
                                        if (selectedBranch == null)
                                            selectedBranch = "ALL";

                                            %>
                                   <td><select name="searchByBranchCode">
                                           <%  for (String branch : branches) 
                                                {
                                            %>
                                            <option value="<%= branch %>" 
                                                    <% if (selectedBranch.equals(branch))
                                                        out.print("selected = 'selected'"); %>>
                                                <%= branch %></option> 
                                           <%
                                                }
                                            %>
                                        </select>
                                   </td>
                                 
                               
                                   <td><input type="submit" value="Apply" name="search" class="button" /></td>

                                </tr>
                            </table>
                        
                </div>
            </div>
           
            <div class="subContentArea">
                
            <div class="subHeader">FLASH FIGURES DETAILED SUMMARY</div>
                
            <div style="margin-left: 10px">
                
                <%
                       String resultsDisplay = null;
                       if (details.size() == 1)
                           resultsDisplay = "Returned replication results for <strong>" + details.size() + "</strong> store.";
                       else
                           resultsDisplay = "Returned replication results for <strong>" + details.size() + "</strong> stores.";
                %>
                
                <p><%= resultsDisplay %></p>
                
                <p>
                    <strong>Export to CSV options: </strong> <a href="<%= WebPages.BASE_APP_URL + "/flashFigures?export=csv&type=filter" %>" target="_blank" 
                       title="Export replication summary as per filter to CSV file">current results</a> | 
                    <a href="<%= WebPages.BASE_APP_URL + "/flashFigures?export=csv&type=unhealthy" %>" target="_blank" 
                       title="Export yellow stores on replication to a CSV file">unhealthy stores</a>
                </p>
                
                <table class="replicationDetails" border="0" width="100%">
                    
                    <thead>
                        <tr style="border-top: 1px solid gray">
                            <th></th>
                            <th>Branch</th>
                            <th>Period</th>
                            <th>Replicate Up To</th>
                            <th>Flash Up To</th>
                            <th>Last Flash Sent</th>
                            <th>Difference</th>
                            <th>Locked</th>
                            <th>Last Flash</th>
                            <th>Comment</th>
                            <th></th>
                        </tr>
                    </thead>
                 
                    <tbody>
                        <% 
                            for (FlashFiguresEntity entity : details) {
                                String className = "branchOk";
                                if (entity.isBranchOk() == false)
                                    className = "branchWarning";
                                if (entity.isError() == true)
                                    className = "branchError";
                        %>
                        <tr class="<%= className %>">
                            <td>   
                                <% 
                                    if (entity.isBranchOk() && !entity.isError()) {
                                        %>
                                            <img alt="branch ok" src="<%= ReplicationData.BRANCK_OK_IMAGE_URL %>" style="width:36px" />
                                        <%
                                    }
                                    else {
                                        %>
                                            <img alt="branch ok" src="<%= ReplicationData.BRANCH_WARNING_IMAGE_URL %>" style="width:36px" />
                                        <%
                                    }
                                   
                                %>
                                
                            </td>
                            <td><span class="bigText"><%= entity.getBranchCode() %></span><br/>
                                <span class="smallText"><%= entity.getBranchName() %></span></td>
                            <td><%= entity.getPeriod() %></td>
                            <td><%= entity.getReplicate() %></td>
                            <td><%= entity.getFlashAudUpTo() %></td>
                            <td><%= entity.getFlashLastAudSent() %></td>
                            <td>
                                <%= entity.getDifference() %>                               
                            </td>
                            <td><% if (entity.isLocked()) 
                                            out.print("Yes");
                                    else out.print("No"); 
                                %>
                            </td>
                             <td><% if (entity.getFlashAudDate() != null) 
                                        out.println(formatter.format(entity.getFlashAudDate()));
                            %></td>
                            <td><p class="comments"><% for (String comment : entity.getComments())  {
                                        out.println("<span> ● " + comment + "</span><br/>");
                                }%>
                                </p>
                            </td>
                            <td></td>
                        </tr>
                        
                        <% } %>
                        
                    </tbody>
                </table>
                        
             </div>
                        
            </div>
       
            </form>
        </div>
            
    </body>
</html>
