
function processMenuItem(option) {
    
    if (option == null)
        return;
    
    var baseUrl = "http://localhost:8080/DailyReport"
    
    if (option == "overview") {
        window.location = baseUrl + "/overview";
    }
    
}