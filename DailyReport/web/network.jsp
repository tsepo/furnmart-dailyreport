<%-- 
    Document   : network
    Created on : Apr 2, 2014, 8:30:23 AM
    Author     : tmaleka
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="za.co.argility.furnmart.entity.NetworkEntity"%>
<%@page import="java.util.List"%>
<%@page import="za.co.argility.furnmart.servlet.helper.NetworkData"%>
<%@page import="za.co.argility.furnmart.servlet.SessionAttribute"%>

<%
    NetworkData data = (NetworkData)session.getAttribute(SessionAttribute.NETWORK_DATA_TAG);
    if (data == null)
        response.sendRedirect(WebPages.BASE_APP_URL + "/network"); 
    
    // Get the list of stores with network and those without
    List<NetworkEntity> withNetwork = data.getNetworkAvailableList();
    List<NetworkEntity> noNetwork = data.getNetworkUnavailableList();
    
    SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
    
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Network Details | Furnmart Daily Report</title>
        
        <%@include file="master/global-header.jspf" %>
        
        <link rel="stylesheet" type="text/css" href="stylesheets/network.css" />
        
    </head>
   
    <body>
        
        <%@include file="master/loadingPanel.jspf" %>
        
        <div class="content">
            
            <%@include file="master/menu.jspf" %>
            
            <div class="header">
                <div class="wrapper">NETWORK</div>
            </div>
            
            <!-- show stores with network connection -->
            <div class="subContentArea">
            
                
                <div id="networkAvailable" class="subHeader">STORES WITH NETWORK CONNECTION</div> 
                
                <% String display = null;
                
                   if (withNetwork.size() == 1)
                       display = withNetwork.size() + " store has network connection available.";
                   else
                       display = withNetwork.size() + " stores have network connection available.";
                %>
                
                <p><%= display %></p>
                <br/>
                
                <div class="networkDetailsWrapper">
                    
                    <% for (NetworkEntity entity : withNetwork) {
                        
                    %>
                    
                    <div class="networkItem withNetwork">
                        <p><span class="bigText"><%= entity.getBranchCode() %></span><br/>
                            <span class="smallText"><%= entity.getBranchName() %></span>
                        </p>
                    </div>
                    
                    <% 
                        } 
                    %>
                </div>
            </div>
                        
            <!-- show stores without network -->
             <div id="networkUnavailable" class="subContentArea">
                <div class="subHeader">STORES WITHOUT NETWORK CONNECTION</div> 
                
                 <% if (noNetwork.size() == 1)
                                display = noNetwork.size() + " store has no network connection available.";
                            else
                                display = noNetwork.size() + " stores have no network connection available.";
                         %>
                
                <p><%= display %></p>
                 
                 <br/>
                <div class="networkDetailsWrapper">
                    
                    <% for (NetworkEntity entity : noNetwork) {
                        
                    %>
                    
                    <div class="networkItem withoutNetwork">
                        <p><span class="bigText"><%= entity.getBranchCode() %></span><br/>
                            <span class="smallText"><%= entity.getBranchName() %></span>
                        </p>
                    </div>
                    
                    <% 
                        } 
                    %>
                </div>
            </div>
            
        </div>
        
    </body>
</html>
