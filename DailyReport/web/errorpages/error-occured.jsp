<%@page import="za.co.argility.furnmart.util.WebPages"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Error Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width">
        
        <link rel="stylesheet" type="text/css" href="<%= WebPages.BASE_APP_URL %>/stylesheets/default.css" />
        <script type="text/javascript">
            // Global variables go HERE

            // constants
            var BASE_URL = "<%= WebPages.BASE_APP_URL %>";

            // element IDs
            var LOADING_PANEL_ID = "loadingPanel";

        </script>

        <script type="text/javascript" src="<%= WebPages.BASE_APP_URL %>/scripts/default.js"></script>
        
    </head>
    
    <body>
        <div class="content">
            <div class="subContentArea">
                
                <img src="<%= WebPages.BASE_APP_URL %>/errorpages/error-page-image.png" alt="error message image"
                     style="width:900px" /> <br/>
                
                <a href="<%= WebPages.BASE_APP_URL %>" title="Go to Home Page">Return Home</a>
                
            </div>
        </div>
    </body>
</html>
