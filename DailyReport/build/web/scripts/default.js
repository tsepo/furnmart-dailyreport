
// constants
var BASE_URL = "http://c9980.fm.co.za:8080/DailyReport";
//var BASE_URL = "http://localhost:8080/DailyReport";

// element IDs
var LOADING_PANEL_ID = "loadingPanel";

/**
 * Toggles the visibility of the top bar
 * for indicating progress.
 * 
 * @param {type} isShow
 * @returns {undefined}T
 */
function toggleLoadingTopBar(isShow) {
    
    var topBar = document.getElementById(LOADING_PANEL_ID);
    if (topBar == null)
        return;
    
    if (isShow == true) {
        $(topBar).css({ "display" : "block" });
    }
    
    else {
         $(topBar).css({ "display" : "none" });
    }
    
    
}

/**
 * Processes a click event for the
 * menu items
 * 
 * @param {type} option
 * @returns {undefined}
 */
function processMenuItem(option) {
   
    // if the OVERVIEW menu item is clicked
    if (option == "overview") {
        toggleLoadingTopBar(true);
        window.location.replace(BASE_URL + "/overview");
        
    }
    
    // if the REPLICATION menu item is clicked
    else if (option == "replication") {
        toggleLoadingTopBar(true);
        window.location.replace(BASE_URL + "/replication");
        
    }
    
    // if the NETWORK menu is clicked
    else if (option == "network") {
        toggleLoadingTopBar(true);
        window.location.replace(BASE_URL + "/network");
        
    }
    
    // if the DAILY BO EXTRACTS menu is clicked
    else if (option == "dailybiextracts") {
        toggleLoadingTopBar(true);
        window.location.replace(BASE_URL + "/dailyBIExtracts");
    }
    
    else {
        
        alert("Sorry, that page is still under development.\n" + 
                "Please try later.");
        
    }
}

/**
 * Sets the auto refresh settings
 * 
 * @param {type} checkBox
 * @returns {undefined}
 */
function setAutoRefresh(checkBox) {
    
    toggleLoadingTopBar(true);
    
    var form = document.getElementById("refreshForm");
    if (form == null)
        return;
    
    if (checkBox.checked == true) 
        form.autoRefresh.value = "true";
    
    else 
        form.autoRefresh.value = "false";
    
    form.submit();
    
}