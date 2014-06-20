/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tmaleka
 */
public final class Log {
    
    private static Logger LOG = null;
    
    public static void setLogger(Class c) {
        LOG = Logger.getLogger(c.getName());
    }
    
    public static void debug(String message) {
        if (LOG == null)
            setLogger(Log.class);
        
        LOG.log(Level.ALL, message);
    }
    
    public static void info(String message) {
        if (LOG == null)
            setLogger(Log.class);
        
        LOG.log(Level.INFO, message);
    }
    
    public static void severe(String message) {
        if (LOG == null)
            setLogger(Log.class);
        
        LOG.log(Level.SEVERE, message);
    }
    
}
