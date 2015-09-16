
/**
 * Validates the input from the search
 * options
 * 
 * @param {type} form
 * @returns {Boolean}
 */
function validateInput(form) {
    
    if (form == null)
        return false;
    else {
        toggleLoadingTopBar(true);
        return true;
    }
}

function navigate(action, branch) {
    
    if (action == "unlockAndReplicate") {
        
        var difference = document.getElementById("diff-" + branch).value;
        if (difference > 0) {
            window.location = BASE_URL + "/unlockAndReplicate?branch=" + branch;
        }
        
        else {
            alert("You cannot unlock and replicate this branch.");
        }
    }
    
    else if (action == "connectToStore")
        window.location = "http://f" + branch + "00.fm.co.za/ucsretail/Login";
    
    var menu = document.getElementById("dropDownMenu-" + branch);
    $(menu).slideToggle("fast");
    
}

function toggleDropDownMenu(source) {
    var image = source;
    var branch = image.id.split("-")[1];
    var menu = document.getElementById("dropDownMenu-" + branch);
    
    $(menu).slideToggle("fast");
}



