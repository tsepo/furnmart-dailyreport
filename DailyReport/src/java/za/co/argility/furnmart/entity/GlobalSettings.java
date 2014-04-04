/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.entity;

import za.co.argility.furnmart.servlet.helper.ServletHelper;

/**
 *
 * @author tmaleka
 */
public class GlobalSettings extends ServletHelper {
    
    private boolean autoRefresh;
    private long refreshInterval;
    private boolean seenWhatsChanged;
    
    public GlobalSettings() {
        this.autoRefresh = false;
        this.refreshInterval = 0;
    }

    public boolean isAutoRefresh() {
        return autoRefresh;
    }

    public void setAutoRefresh(boolean autoRefresh) {
        this.autoRefresh = autoRefresh;
    }

    public long getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(long refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    public boolean hasSeenWhatsChanged() {
        return seenWhatsChanged;
    }

    public void setSeenWhatsChanged(boolean seenWhatsChanged) {
        this.seenWhatsChanged = seenWhatsChanged;
    }
    
    
}
