<%-- 
    Document   : monthend-consolidations
    Created on : Sep 22, 2014, 1:34:07 PM
    Author     : rnaidoo
--%>
<%@page import="za.co.argility.furnmart.util.WebPages"%>
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
        response.sendRedirect(WebPages.BASE_APP_URL + "/MontEndConsolidation?tab=cons"); 
        return;
    }
    
%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Monthend Consolidations</title>
    </head>
    <body>
        <h1>Monthend Consolidations</h1>
    </body>
</html>
