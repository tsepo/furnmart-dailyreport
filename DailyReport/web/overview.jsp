<%-- 
    Document   : index
    Created on : Mar 24, 2014, 12:07:46 PM
    Author     : tmaleka
--%>

<%@page import="za.co.argility.furnmart.util.WebPages"%>
<%@page import="java.util.*"%>
<%@page import="za.co.argility.furnmart.servlet.*" %>
<%@page import="za.co.argility.furnmart.servlet.helper.*" %>

<%
    // get the data from the session
    OverviewData data = (OverviewData)session.getAttribute(SessionAttribute.OVERVIEW_DATA_TAG);
    if (data == null) {
        response.sendRedirect(WebPages.BASE_APP_URL + "/overview"); 
        return;
    }

    // the network overview data
    Map<String, Integer> networkOverview = data.getNetworkOverview();
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <title>Furnmart Daily Report</title>
        
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width">
        
        <%@include file="master/global-header.jspf" %>
        
        <script>
             $(document).ready(function () {
    	
            <% if (networkOverview != null) { %>
                // populate the network overview chart
                $('#networkOverViewChart').highcharts({
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                        },
                        title: {
                            text: 'Stores with network vs. stores without network'
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
                                ['Network Available',   <%= String.valueOf(networkOverview.get("AVAILABLE")) %>],
                                ['No Network',   <%= String.valueOf(networkOverview.get("NOT AVAILABLE")) %>]
                             
                            ]
                        }]
                    });
              <% } %>
                    
                     //populate the replication status chart
                    $("#replicationStatusOverviewChart").highcharts({
                        chart: {
                            type: 'bar'
                        },
                        title: {
                            text: 'Replication Status'
                        },
                        subtitle: {
                            text: 'Displaying the current replication status overview'
                        },
                        xAxis: {
                            categories: <%= data.getReplicationLabels() %>,
                            title: {
                                text: null
                            }
                        },
                        yAxis: {
                            min: 0,
                            title: {
                                text: 'Number of stores',
                                align: 'high'
                            },
                            labels: {
                                overflow: 'justify'
                            }
                        },
                        tooltip: {
                            valueSuffix: ' branch(es)'
                        },
                        plotOptions: {
                            bar: {
                                dataLabels: {
                                    enabled: true
                                }
                            }
                        },
                        legend: {
                            layout: 'vertical',
                            align: 'right',
                            verticalAlign: 'top',
                            x: -40,
                            y: 100,
                            floating: true,
                            borderWidth: 1,
                            backgroundColor: '#FFFFFF',
                            shadow: true
                        },
                        credits: {
                            enabled: false
                        },
                        series: [{
                            name: 'Number of branches',
                            data: <%= data.getReplicationDataPoints() %>
                        }]
                    });
                    
                    
                    //outstanding ceres to java transactions
                   /* $('#outstandingSwitchingOverviewChart').highcharts({
                            chart: {
                                type: 'areaspline'
                            },
                            title: {
                                text: 'Outstanding switching transactions'
                            },
                            legend: {
                                layout: 'vertical',
                                align: 'left',
                                verticalAlign: 'top',
                                x: 150,
                                y: 100,
                                floating: true,
                                borderWidth: 1,
                                backgroundColor: '#FFFFFF'
                            },
                            xAxis: {
                                categories: [
                                    'Monday',
                                    'Tuesday',
                                    'Wednesday',
                                    'Thursday',
                                    'Friday',
                                    'Saturday',
                                    'Sunday'
                                ]
                               
                            },
                            yAxis: {
                                title: {
                                    text: 'Fruit units'
                                }
                            },
                            tooltip: {
                                shared: true,
                                valueSuffix: ''
                            },
                            credits: {
                                enabled: false
                            },
                            plotOptions: {
                                areaspline: {
                                    fillOpacity: 0.5
                                }
                            },
                            series: [{
                                name: 'Other Branch Receipts',
                                data: [3, 4, 3, 5, 4, 10, 12]
                            }, {
                                name: 'MERCH Inter-branch transfer',
                                data: [1, 3, 4, 3, 3, 5, 4]
                            },
                            {
                                name: 'MERCH Allocation Transfer Out - Create',
                                data: [6, 2, 0, 5, 3, 1, 4]
                            }
                            ]
                        }); */

                    
                });
                
               
                
            
        </script>
        
    </head>
    <body>
        
        <%@include file="master/loadingPanel.jspf" %>
        
        <div class="content" id="content">
            
            <%@include file="master/menu.jspf" %>
            
            <!-- Overview Section -->
            
            <div class="header">
                <div class="wrapper" >
                    <p>OVERVIEW DETAILS</p>
                </div>
            </div>
            
            <div class="subContentArea">
                
                <div class="subHeader" >NETWORK STATISTICS</div>
                
                <div id="networkOverViewChart" class="chartArea">
                </div>
                
            </div>
            
            <div class="subContentArea">
                
                <div class="subHeader" >REPLICATION STATUS</div>
                
                <div id="replicationStatusOverviewChart" class="chartArea">
                </div>
                
            </div>
            
           <!-- <div class="subContentArea">
                <div class="subHeader">OUTSTANDING SWITCHING TRANSACTIONS</div>
                <div id="outstandingSwitchingOverviewChart" class="chartArea"></div>
            </div> -->
            
        </div>
        
    </body>
</html>
