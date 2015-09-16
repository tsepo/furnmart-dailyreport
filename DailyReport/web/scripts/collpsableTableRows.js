/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function toggle_visibility() {
var soft = document.getElementById("test");
var newSoft = document.getElementById("test4");
 if(soft.style.display == 'block'){
    soft.style.display = 'none';
    newSoft.style.display = 'block';
    }
    else{
      soft.style.display = 'block';
  }
}
