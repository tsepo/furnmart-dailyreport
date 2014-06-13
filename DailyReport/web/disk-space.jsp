<%-- 
    Document   : disk-space
    Created on : Jun 10, 2014, 10:02:00 AM
    Author     : tmaleka
--%>

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
        
        <%@include file="master/global-header.jspf" %>
      
        <style type="text/css">
            
            DIV.diskSpaceChartArea {
                overflow : scroll;
            }
            
        </style>
        
        <script type="text/javascript">
            
            $(document).ready(
                function () {
                        $('#stackedBarChart').highcharts({
                            chart: {
                                type: 'column',
                                width: <%= String.valueOf(data.determineDiskSpaceChartHeight()) %>,
                                height : 500
                            }
                           
                            title: {
                                text: 'Available Disk Space vs. Used Disk Space (Live Stores)'
                            },
                            xAxis: {
                                categories: [<%= data.getChartCategories() %>]
                                
                            },
                            yAxis: {
                                min: 0,
                                title: {
                                    text: 'Disk Space Utilisation (GB)'
                                }
                            },
                            tooltip: {
                                pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}GB</b> ({point.percentage:.0f}%)<br/>',
                                shared: true
                            },
                            plotOptions: {
                                series: {
                                    stacking: 'percent'
                                }
                            },
                                series: [{
                                    name: 'Used Space (GB)',
                                    data: [<%= data.getUsedDiskSpaceChartValues() %>]
                                        }, 
                                   {
                                    name: 'Available Space (GB)',
                                    data: [<%= data.getAvailableDiskSpaceChartValues() %>]
                                }]
                        });
                    });
    
            
        </script>
        
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
            
            <div class="subContentArea">
                
                <div id="stackedBarChart" class="chartArea diskSpaceChartArea"></div>
                
            </div>
            
            <div class="subContentArea">
                <p><b>Notes:</b><br/> 
                    Disk space statistics are updated every hour - branches with no network will display the
                       last successful update on their disk space statistics.</p>
            </div>
            
        </div>
        
    </body>
</html>
