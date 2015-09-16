/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.servlet.helper;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author tmaleka
 */
public class OverviewData  {
    
    private Map<String, Integer> networkOverview;
    private Map<String, Integer> replicationStatusOverview;
    
    public OverviewData() {
        this.networkOverview = null;
        this.replicationStatusOverview = null;
    }
    
    public String getReplicationLabels() {
        
        Set<String> labels = replicationStatusOverview.keySet();
        String[] temp = new String[labels.size()];
        int i = 0;
        for (String label : labels) {
            temp[i] = "'" + label + "'";
            i++;
        }
        
        return Arrays.toString(temp);
    }
    
    public String getReplicationDataPoints() {
        
         Set<String> labels = replicationStatusOverview.keySet();
         Integer[] temp = new Integer[labels.size()];
         
         int i = 0;
         for (String label : labels) {
             int value = replicationStatusOverview.get(label);
             temp[i] = value;
             i++;
         }
         
         return Arrays.toString(temp);
    }
    
    public Map<String, Integer> getNetworkOverview() {
        return networkOverview;
    }

    public void setNetworkOverview(Map<String, Integer> networkOverview) {
        this.networkOverview = networkOverview;
    }

    public Map<String, Integer> getReplicationStatusOverview() {
        return replicationStatusOverview;
    }

    public void setReplicationStatusOverview(Map<String, Integer> replicationStatusOverview) {
        this.replicationStatusOverview = replicationStatusOverview;
    }
    
    
    
}
