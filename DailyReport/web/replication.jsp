<%-- 
    Document   : replication
    Created on : Mar 31, 2014, 12:38:18 PM
    Author     : tmaleka
--%>

<%@page import="za.co.argility.furnmart.entity.ReplicationSearchEntity"%>
<%@page import="java.util.ArrayList"%>
<%@page import="za.co.argility.furnmart.entity.ReplicationEntity"%>
<%@page import="java.util.List"%>
<%@page import="za.co.argility.furnmart.servlet.helper.ReplicationData"%>
<%@page import="za.co.argility.furnmart.servlet.SessionAttribute"%>

<% 
    // get the data from the session
    ReplicationData data = (ReplicationData)session.getAttribute(SessionAttribute.REPLICATION_DATA_TAG);
    if (data == null)
        response.sendRedirect(WebPages.STARTUP_PAGE); 
    
    // replication details
    List<ReplicationEntity> details = data.getReplicationDetails();
    if (details == null)
        details = new ArrayList<ReplicationEntity>();
    
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
        <title>Replication Details | Furnmart Daily Report</title>
        
        <%@include file="master/global-header.jspf" %>
        
        <link type="text/css" rel="stylesheet" href="stylesheets/replication.css" />
        
    </head>
    <body>
        
        <%@include file="master/loadingPanel.jspf" %>
        
        <div class="content" id="content">
            <%@include file="master/menu.jspf" %>
            
            <!-- place logic here -->
            
            <div class="header">
                <div class="wrapper" ><p>REPLICATION DETAILS</p></div>
            </div>
            
            <div class="subContentArea">
            
                <div class="subHeader">SEARCH FILTERS:</div>

                <div style="margin-left: 10px; margin-bottom:30px">


                        <form method="post" name="filterReplicationForm" action="<%= WebPages.BASE_APP_URL %>/replication" onsubmit="return validateInput(this)">
                            <table border="0" width="60%">
                                <tr>
                                    <td><label>Branch:</label></td>
                                       <% 
                                        ReplicationSearchEntity search = data.getSearch();
                                        if (search == null) {
                                            search = new ReplicationSearchEntity();
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
                                    <td><label>Process Type:</label></td>
                                   <td>
                                       <%
                                           String selectedProcess = search.getProcess();
                                           if (selectedProcess == null)
                                               selectedProcess = "";

                                           %>
                                       <select name="processType">
                                           <% for (String process : processes) {
                                               %>
                                               <option value="<%=process %>"
                                                       <%  
                                                            if (process.equals(selectedProcess))
                                                                out.print("selected='selected'");
                                                       %> 
                                                       ><%=process %></option>
                                               <% } %>
                                       </select>

                                   </td>
                                   <td><input type="submit" value="Search" name="search" class="button" /></td>

                                </tr>
                            </table>
                        </form>
                </div>
            </div>
           
            <div class="subContentArea">
                
            <div class="subHeader">REPLICATION DETAILED SUMMARY</div>
                
            <div style="margin-left: 10px">
                
                <%
                       String resultsDisplay = null;
                       if (details.size() == 1)
                           resultsDisplay = "Returned replication results for " + details.size() + " store.";
                       else
                           resultsDisplay = "Returned replication results for " + details.size() + " stores.";
                %>
                
                <p><%= resultsDisplay %></p>
                <p class="alertText">NB: If the branch is locked or replication crashed on central, please investigate.</p>
                
                <table class="replicationDetails" border="0" width="100%">
                    
                    <thead>
                        <tr style="border-top: 1px solid gray">
                            <th></th>
                            <th>Branch</th>
                            <th>Audit Up To</th>
                            <th>Replicate Up To</th>
                            <th>Difference</th>
                            <th>Locked</th>
                            <th>Locked Date</th>
                            <th>Unlock Date</th>
                            <th>Process</th>
                            <th>Comments</th>
                        </tr>
                    </thead>
                 
                    <tbody>
                        <% 
                            for (ReplicationEntity entity : details) {
                                String className = "branchOk";
                                if (entity.isBranchOk() == false)
                                    className = "branchWarning";
                        %>
                        <tr class="<%= className %>">
                            <td>   
                                <% 
                                    if (entity.isBranchOk()) {
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
                            <td><%= entity.getAudit() %></td>
                            <td><%= entity.getReplicate() %></td>
                            <td><%= entity.getDifference() %></td>
                            <td><% if (entity.isLocked()) 
                                            out.print("Yes");
                                    else out.print("No"); 
                                %>
                            </td>
                            <td><% if (entity.getLockedDate() != null) 
                                        out.println(formatter.format(entity.getLockedDate()));
                            %></td>
                            <td><% if (entity.getUnlockedDate() != null) 
                                        out.println(formatter.format(entity.getUnlockedDate()));
                            %></td>
                            <td><%= entity.getProcess() %></td>
                            <td><p class="comments"><% for (String comment : entity.getComments())  {
                                        out.println("<span> ● " + comment + "</span><br/>");
                                }%>
                                </p>
                            </td>
                        </tr>
                        
                        <% } %>
                        
                    </tbody>
                </table>
                        
             </div>
                        
            </div>
       
        </div>
            
    </body>
</html>
