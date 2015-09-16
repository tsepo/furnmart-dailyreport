/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


 $(document).ready(function()
{
    $('.RowToClick').click(function()
    {
        $(this).nextAll('tr').each(function()
        {
            if ($(this).is('.RowToClick'))
            {
                return false;
            }
            $(this).toggle(350);
        });
    });
});