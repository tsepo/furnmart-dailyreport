
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
    
    if (option == null)
        return;
    
    // if the OVERVIEW menu item is clicked
    if (option == "overview") {
        window.location = BASE_URL + "/overview";
        toggleLoadingTopBar(true);
    }
    
    // if the REPLICATION menu item is clicked
    if (option == "replication") {
        window.location = BASE_URL + "/replication";
        toggleLoadingTopBar(true);
    }
}