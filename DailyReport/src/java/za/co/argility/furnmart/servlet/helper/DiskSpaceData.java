/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.servlet.helper;

import java.util.TreeMap;
import java.util.TreeSet;
import za.co.argility.furnmart.entity.DiskSpace;

/**
 *
 * @author tmaleka
 */
public class DiskSpaceData {
    
    private TreeMap<String, DiskSpace> diskSpaceStats;
    
    public DiskSpaceData() {
        this.diskSpaceStats = null;
    }

    public TreeMap<String, DiskSpace> getDiskSpaceStats() {
        return diskSpaceStats;
    }

    public void setDiskSpaceStats(TreeMap<String, DiskSpace> diskSpaceStats) {
        this.diskSpaceStats = diskSpaceStats;
    }
    
    public String getChartCategories() {
        StringBuilder builder = new StringBuilder();
        
        if (diskSpaceStats == null)
            return builder.toString();
        
        TreeSet<String> branches = new TreeSet(diskSpaceStats.keySet());
        int count = 0;
        
        for (String branch : branches) {
            count++;
            if (count == branches.size()) 
                builder.append("'").append(branch).append("'");
            
            else
                 builder.append("'").append(branch).append("',");
            
        }
        
        return builder.toString();
    }
    
    public String getUsedDiskSpaceChartValues() {
        
        StringBuilder builder = new StringBuilder();
        
        if (diskSpaceStats == null)
            return builder.toString();
        
        TreeSet<String> branches = new TreeSet(diskSpaceStats.keySet());
        int count = 0;
        
        for (String branch : branches) {
            count++;
            
            DiskSpace data = diskSpaceStats.get(branch);
            double used = data.getUsedDiskSpace();
            
            if (count == branches.size()) 
                builder.append(String.valueOf(used));
            
            else
                 builder.append(String.valueOf(used)).append(",");
            
        }
        
        return builder.toString();
        
    }
    
     public String getAvailableDiskSpaceChartValues() {
        
        StringBuilder builder = new StringBuilder();
        
        if (diskSpaceStats == null)
            return builder.toString();
        
        TreeSet<String> branches = new TreeSet(diskSpaceStats.keySet());
        int count = 0;
        
        for (String branch : branches) {
            count++;
            
            DiskSpace data = diskSpaceStats.get(branch);
            double available = data.getAvailableDiskSpace();
            
            if (count == branches.size()) 
                builder.append(String.valueOf(available));
            
            else
                 builder.append(String.valueOf(available)).append(",");
            
        }
        
        return builder.toString();
        
    }
     
     public int determineDiskSpaceChartHeight() {
         
         if (diskSpaceStats == null || 
                 diskSpaceStats.keySet() == null)
             return 400;
         
         return (int)(diskSpaceStats.keySet().size() * 100);
     }
    
}
