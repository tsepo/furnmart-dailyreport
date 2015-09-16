/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.servlet;

/**
 *
 * @author tmaleka
 */
public interface SessionAttribute {
    public static final String OVERVIEW_DATA_TAG = "overview_data";
    public static final String MONTHEND_OVERVIEW_DATA_TAG = "monthend-overview_data";
    public static final String REPLICATION_DATA_TAG = "replication-data";
    public static final String MONTHEND_DATA_TAG = "monthend-data";
    //public static final String MONTHEND_CONS_TAG = "monthend-cons";
    public static final String NETWORK_DATA_TAG = "network-data";
    public static final String GLOBAL_SETTINGS_TAG = "global-settings";
    public static final String DAILY_BI_EXTRACTS_DATA_TAG =  "daily-bi-extracts";
    public static final String ITC_700_EXTRACTS_DATA_TAG = "itc-700-extracts";
    public static final String DISK_SPACE_DATA_TAG = "disk-space";
}
