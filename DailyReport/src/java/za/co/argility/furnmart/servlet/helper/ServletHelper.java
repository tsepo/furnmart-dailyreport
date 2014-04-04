/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.servlet.helper;

/**
 *
 * @author tmaleka
 */
public abstract class ServletHelper {
    
    protected String servletName;
    
    protected ServletHelper() {
        this.servletName = null;
    }

    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }
    
    
    
}
