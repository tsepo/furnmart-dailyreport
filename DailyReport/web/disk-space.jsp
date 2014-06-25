<%-- 
    Document   : disk-space
    Created on : Jun 10, 2014, 10:02:00 AM
    Author     : tmaleka
--%>

<%@page import="java.util.Set"%>
<%@page import="za.co.argility.furnmart.servlet.helper.ReplicationData"%>
<%@page import="za.co.argility.furnmart.entity.DiskUtilisationLevelType"%>
<%@page import="za.co.argility.furnmart.util.GeneralUtils"%>
<%@page import="java.util.TreeSet"%>
<%@page import="za.co.argility.furnmart.entity.DiskSpace"%>
<%@page import="java.util.TreeMap"%>
<%@page import="za.co.argility.furnmart.util.WebPages"%>
<%@page import="za.co.argility.furnmart.servlet.SessionAttribute"%>
<%@page import="za.co.argility.furnmart.servlet.helper.DiskSpaceData"%>

<%
     DiskSpaceData data = (DiskSpaceData)session.getAttribute(
             SessionAttribute.DISK_SPACE_DATA_TAG);
     if (data == null) {
         response.sendRedirect(WebPages.BASE_APP_URL + "/DiskSpace");
         return;
     }
     
     TreeMap<String, DiskSpace> stats = data.getDiskSpaceStats();
     if (stats == null)
         stats = new TreeMap<String, DiskSpace>();
     
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Disk Space Statistics | Furnmart Daily Report</title>
        
        <link type='text/css' rel='stylesheet' href='<%= WebPages.BASE_APP_URL %>/stylesheets/diskSpace.css' />
        
        <%@include file="master/global-header.jspf" %>
        
        <%
        
        int lowCount = 0, normalCount = 0,
                        highCount = 0;
        
            if (!stats.isEmpty()) {
                
                Set<String> keySet = stats.keySet();
                for (String key : keySet) {
                 
                    DiskSpace item = stats.get(key);
                    
                    if (item.getDiskSpaceUtilisationLevel() == DiskUtilisationLevelType.Low)
                        lowCount++;
                    if (item.getDiskSpaceUtilisationLevel() == DiskUtilisationLevelType.Normal)
                        normalCount++; 
                    if (item.getDiskSpaceUtilisationLevel() == DiskUtilisationLevelType.High)
                        highCount++;
                    
                }
                
        %>
        
        <script type='text/javascript'>
            $(document).ready(function (){
                
                 $('#diskSpaceChart').highcharts({
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                        },
                        title: {
                            text: 'Instore Disk Utilisation - Overview'
                        },
                        tooltip: {
                                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                        },
                        plotOptions: {
                            pie: {
                                allowPointSelect: true,
                                cursor: 'pointer',
                                dataLabels: {
                                    enabled: false
                                },
                                showInLegend: true
                            }
                        },
                        series: [{
                            type: 'pie',
                            name: 'Percentage',
                            data: [
                                ['Low Disk Utilisation',   <%= String.valueOf(lowCount) %>],
                                ['Normal Disk Utilisation',   <%= String.valueOf(normalCount) %>],
                                ['High Disk Utilisation',   <%= String.valueOf(highCount) %>]
                             
                            ]
                        }]
                    });
                
            });
        </script>
        
        <%
            }
        %>
        
    </head>
    <body>
        
        <%@include file="master/loadingPanel.jspf" %>
        
        <div class="content" id="content">
            
            <%@include file="master/menu.jspf" %>
            
            <div class="header">
                <div class="wrapper" >
                    <p>DISK SPACE STATISTICS : FURNMART STORES</p>
                </div>
            </div>
            
            
            <div class='subContentArea'>
                
                 <div class='subHeader'>
                    OVERVIEW - DISK UTILIZATION
                </div>
                
                <div id='diskSpaceChart' class='chartArea'></div>
            </div>
            
            <div class="subContentArea">
               
                <div class='subHeader'>
                    DETAILED SUMMARY - DISK UTILIZATION
                </div>
                
                <table border='0' class='diskSpaceTable' width='100%' >
                    <thead>
                        <tr class='headerRow'>
                            <th> </th>
                            <th>Branch</th>
                            <th>Total Disk Space</th>
                            <th>Space Used</th>
                            <th>Free Space</th>
                            <th>Space Utilization</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            TreeSet<String> branches = new TreeSet(stats.keySet());
                            
                            for (String branch : branches) {
                                
                                DiskSpace item = stats.get(branch);
                                String className = null,
                                        comments = null,
                                        imageSrc = null;
                                
                                DiskUtilisationLevelType type = item.getDiskSpaceUtilisationLevel();
                                if (type == DiskUtilisationLevelType.Low) {
                                    className = "utilisationLow";
                                    comments = "LOW utilisation";
                                    imageSrc = ReplicationData.BRANCK_OK_IMAGE_URL;
                                 }
                                
                                if (type == DiskUtilisationLevelType.Normal) {
                                    className = "utilisationNormal";
                                    comments = "NORMAL utilisation";
                                    imageSrc = ReplicationData.BRANCK_OK_IMAGE_URL;
                                }
                                
                                if (type == DiskUtilisationLevelType.High) {
                                    className = "utilisationHigh";
                                    comments = "HIGH utilisation";
                                    imageSrc = ReplicationData.BRANCH_WARNING_IMAGE_URL;
                                }
                                
                                
                                %>
                        
                                <tr class="<%= className %>">
                                    <td title="Utilisation Indicator">
                                        <img alt='utilisation' src='<%= imageSrc %>' style='width:36px' />
                                    </td>
                                    <td title="Store details"><p><span class='bigText'><%= branch %></span><br/>
                                            <span class='smallText'><%= item.getDescription() %></span>
                                        </p></td>
                                    <td title="Total Disk Space (GB)"><p><span><%= item.getTotalDiskSpace() %> GB</span> <br/>
                                            <span class="smallTextV2"><%= GeneralUtils.roundOff(item.getReservedDiskSpace(), 1) %> GB RESERVED</span></p></td>
                                    <td title="Used Disk Space (GB)"><p><%= item.getUsedDiskSpace() %> GB</p></td>
                                    <td title="Available Disk Space (GB)"><p><%= item.getAvailableDiskSpace() %> GB</p></td>
                                    <td title="Disk Space Utilisation (%)"><p><span class='bigText'><%= GeneralUtils.roundOff(item.getDiskUtilisation(), 2) %>%</span> <br/> 
                                            <span><%= comments %></span></p></td>
                                      
                                </tr>
                                
                               <%
                            }
                        %>
                    </tbody>
                </table>
                
            </div>
            
            <div class="subContentArea">
                <p><b>Notes:</b><br/> 
                    Disk space statistics are updated every hour - branches with no network will display the
                       last successful update on their disk space statistics.</p>
            </div>
            
        </div>
        
    </body>
</html>
