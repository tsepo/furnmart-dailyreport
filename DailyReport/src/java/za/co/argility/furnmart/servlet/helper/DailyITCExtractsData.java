/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.servlet.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import za.co.argility.furnmart.entity.ExtractHistory;

/**
 *
 * @author mbalenhle ndaba
 */
public class DailyITCExtractsData {
    
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    
    public static final String TAB_PARAM = "tab";
    public static final String DAY_PARAM = "day";
    public static final String PERIOD_PARAM = "period";
    public static final String SEARCH_PARAM = "search";
    
    public static final String TAB_DAILY = "daily";
    public static final String TAB_MONTHLY = "monthly";
    
    public static final String DAY_TODAY = "today";
  
    private List<ExtractHistory> itcExtract;
    private String currentTab;
    private Date currentDay;

    
    
    public DailyITCExtractsData(){
        this.itcExtract = null;
    }
    
    public static String formatDate(Date date) {
    if (date == null)
        return null;
    
    Date today = new Date();
    if (date.compareTo(today) == 0)
        return DailyBIExtractsData.DAY_TODAY;
    
    return new SimpleDateFormat("dd MMMM yyyy").format(date);
    
}
    public String getCurrentTab() {
        return currentTab;
    }

    public void setCurrentTab(String currentTab) {
        this.currentTab = currentTab;
    }

    public Date getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(Date currentDay) {
        this.currentDay = currentDay;
    }
    
    public List<ExtractHistory> getItcExtract() {
        return itcExtract;
    }
    
     public void setItcExtract(List<ExtractHistory> itcExtract) {
        this.itcExtract = itcExtract;
    }
    
}
