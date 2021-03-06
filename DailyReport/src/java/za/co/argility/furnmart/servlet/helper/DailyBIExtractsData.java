/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.servlet.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import za.co.argility.furnmart.entity.ExtractHistory;
import za.co.argility.furnmart.util.BucketMap;

/**
 *
 * @author tmaleka
 */
public class DailyBIExtractsData {
    
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    
    public static final String TAB_PARAM = "tab";
    public static final String DAY_PARAM = "day";
    public static final String PERIOD_PARAM = "period";
    public static final String SEARCH_PARAM = "search";
    
    public static final String TAB_DAILY = "daily";
    public static final String TAB_MONTHLY = "monthly";
    
    public static final String DAY_TODAY = "today";
    
    private BucketMap<String, ExtractHistory> extractHistory;
    private HashMap<String, Date> lastExtractFilesSentMap;
    private String currentTab;
    private Date currentDay;
    
    public DailyBIExtractsData() {
        this.extractHistory = null;
        this.lastExtractFilesSentMap = null;
    }
    
    public static String formatDate(Date date) {
        if (date == null)
            return null;
        
        Date today = new Date();
        if (date.compareTo(today) == 0)
            return DailyBIExtractsData.DAY_TODAY;
        
        return new SimpleDateFormat("dd MMMM yyyy").format(date);        
        
    }

    public BucketMap<String, ExtractHistory> getExtractHistory() {
        return extractHistory;
    }

    public void setExtractHistory(BucketMap<String, ExtractHistory> extractHistory) {
        this.extractHistory = extractHistory;
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

    public HashMap<String, Date> getLastExtractFilesSentMap() {
        return lastExtractFilesSentMap;
    }

    public void setLastExtractFilesSentMap(HashMap<String, Date> lastExtractFilesSentMap) {
        this.lastExtractFilesSentMap = lastExtractFilesSentMap;
    }
    
    
    
}
