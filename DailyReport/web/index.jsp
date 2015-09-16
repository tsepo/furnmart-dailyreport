<%-- 
    Document   : startup
    Created on : Mar 24, 2014, 2:19:27 PM
    Author     : tmaleka
--%>
<%@page import="za.co.argility.furnmart.util.WebPages"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome | Furnmart Daily Report</title>
        
        <link href='http://fonts.googleapis.com/css?family=Special+Elite' rel='stylesheet' type='text/css' />
        
        <style type="text/css">
            body {
               margin: 0px;
               font-family: 'Special Elite', cursive;
               background-color: white;
               display: block;
               font-size: 22px;
               padding: 12px;
               width: 100%;
            }
            
            li {
                margin-top: 15px;
                display: block;
                font-size: 26px;
            }
            
            a, a:active {
                text-decoration: none;
                color: #0099FF;
            }
            
            a:hover {
                color: #0099FF;
                text-decoration: underline;
            }
            
            .main {
                width: 30%;
                height: auto;
                margin: 0px;
                padding: 12px;
            }
            
            .description {
                margin: 0px;
                padding: 0px;
                font-size:12px; 
                font-family: 'Arial';
            }
            
            
        </style>
        
    </head>
    <body>
        
       <div class="main"> 
            <h1>Furnmart Daily Report</h1>
       
            <h4>Select from the options below:</h4>
       
            <ul>
                <li>
                    <a href="<%= WebPages.BASE_APP_URL %>/overview" title="Replication, Network and Disk Space Utilization">
                        Replication, Network and Disk Space Utilization</a>
                       
                </li>
                <li>
                    <a href="<%= WebPages.BASE_APP_URL %>/MonthEndProduction?tab=overview" title="Month End Production and Extracts">
                        Month End Production and Extracts</a>
                </li>
                
                <li>
                    <a href="http://c9980.fm.co.za" title="Day & Month Ends, Monitor Jobs and Conversions">
                        Day & Month Ends, Monitor Jobs and Conversions</a>
                </li>
                
                <li>
                    <a href="<%= WebPages.BASE_APP_URL %>/dailyBIExtracts" title="Daily & Monthly BI Extracts">
                        Daily & Monthly BI Extracts</a>
                </li>
                
            </ul>
                        
            <h4>For Developers:</h4>
            <p>Fork the source code from <a href="https://github.com/tsepo/furnmart-dailyreport" 
                                            title="GitHub" target="_blank">GitHub</a>.</p>
       </div>
    </body>
</html>
 