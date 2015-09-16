/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author tmaleka
 */
public final class GeneralUtils {
    
    /**
     * Parses a boolean value to a Yes / No
     * string value.
     * 
     * @param value
     * @return 
     */
    public static String parseBooleanForUI(boolean value) {
        if (value == true) 
            return "Yes";
        else
            return "No";
    }
     
    /**
     * Formats the date output
     * to the given format
     * 
     * @param date
     * @param format
     * @return 
     */
    public static String formatDate(Date date, String format) {
        
        if (date == null || format == null)
            return "";
        
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(date);
    }
    
    /**
     * Gets the name of the operating system
     * @return 
     */
    public static String getOperationSystemName() {
        return System.getProperty("os.name");
    }
    
    /**
     * Gets the context user's username
     * @return 
     */
    public static String getContextUsername() {
        return System.getProperty("user.name");
    }
    
    /**
   * Added by Tsepo
   * Rounds off a decimal number to the specified digits after a comma
   * 
   * @param value - the double value to round off
   * @param decimalPlaces - decimal places after a comma
     * @return 
   */
    public static String roundOff(double value, int decimalPlaces){
             String roundedFormat = String.format("%." + decimalPlaces + "f", value);
             return roundedFormat;
    }

}
