/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function initialise() {
    
    $(document).ready(function() {
        
         // set the date picker
        $("#datepicker").datepicker({

          numberOfMonths: 3,
          showButtonPanel: true,
          maxDate: new Date(),
          minDate : new Date("02/01/2014"),
          dateFormat : "dd/mm/yy"
        
        });
        
    });

}

function validatePickDayForExtracts(form){
    if (form === null)
        return false;
    
    var datePicker = document.getElementById("datepicker");
    
    if (datePicker.value === null) {
        return false;
    }
            
    if (datePicker.value.length < 8 && datePicker.value.indexOf("/") < 0) {
        alert("You must pick a date or check the format of the date.");
        return false;
     }
     
     toggleLoadingTopBar(true);
    
     return true;
    
}

