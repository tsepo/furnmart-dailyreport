<%-- 
    Document   : monthendOverview.jsp
    Created on : Jun 21, 2014, 10:17:03 AM
    Author     : rnaidoo
--%>

<%@page import="za.co.argility.furnmart.util.WebPages"%>
<%@page import="java.util.*"%>
<%@page import="za.co.argility.furnmart.servlet.*" %>
<%@page import="za.co.argility.furnmart.servlet.helper.*" %>

<%
    // get the data from the session
    MonthendOverviewData data = (MonthendOverviewData)session.getAttribute(SessionAttribute.MONTHEND_OVERVIEW_DATA_TAG);
    if (data == null) {
        response.sendRedirect(WebPages.BASE_APP_URL + "/MonthEndProduction?tab=overview"); 
        return;
    }

    
%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Furnmart Monthend Report</title>
         <%@include file="master/global-header.jspf" %>
    </head>
    <body>
        
         <div class="content" id="content">
            
            <%@include file="master/monthend-menu.jspf" %>
         </div>
    </body>
</html>

