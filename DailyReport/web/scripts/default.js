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
    
    // if the ITC 700 EXTRACTS VERIFIER menu is clicked
    else if (option == "itc700extractsverifier") {
        toggleLoadingTopBar(true);
        window.location.replace(BASE_URL + "/Itc700ExtractsVerifier");
    }
    
    else if (option == "diskspace") {
        toggleLoadingTopBar(true);
        window.location.replace(BASE_URL + "/DiskSpace");
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
    
    var currentValue = form.autoRefresh.value;
    
    if (currentValue == "true")
        form.autoRefresh.value = "false";
    
    else if (currentValue == "false")
        form.autoRefresh.value = "true";
    
    form.submit();
    
}