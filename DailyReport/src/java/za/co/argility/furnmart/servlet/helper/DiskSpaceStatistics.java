/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.servlet.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.TreeMap;
import za.co.argility.furnmart.entity.DiskSpace;

/**
 *
 * @author tmaleka
 */
public class DiskSpaceStatistics {
    
    public static final String DIRECTORY = "/home/ucsretail/bin/disk-space";
    
    public DiskSpaceStatistics(){
    }
    
    /**
     * Gets all the disk space statistics for all branches
     * 
     * @return 
     */
    public TreeMap<String, DiskSpace> getDiskSpaceStatistics() throws IOException {
        
        final String directoryName = DIRECTORY + "/results/collectDiskSpaceStats";
        TreeMap<String, DiskSpace> map = null;
        String[] resultFiles = null;
        
        File directory = new File(directoryName);
        if (directory.isDirectory()) {
            
            resultFiles = directory.list(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return (name.startsWith("collectDiskSpaceStats."));
                }
            });
            
            map = new TreeMap<String, DiskSpace>();
            String key = null;
            
            for (String filename : resultFiles) {
                
                File file = new File(directoryName + "/" + filename);
                
                if (file.exists() && !file.isDirectory()) {
                    
                    key = filename.substring(filename.indexOf(".") + 1);
                    map.put(key, createDiskSpaceInstances(file));
                }
            }
        }
        
        return map;
    }
    
    /**
     * Creates an instance of the disk space entity from a file
     * @param file
     * @return
     * @throws IOException 
     */
    private DiskSpace createDiskSpaceInstances(File file) throws IOException {
        
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        DiskSpace value = new DiskSpace();
        
        while (reader.ready()) {
            line = reader.readLine();
            
            if (line == null || line.isEmpty())
                continue;
            
            if (line.startsWith("TOTAL:")) {
                
                value.setTotalDiskSpace(Double.parseDouble(line.substring(
                        line.indexOf(":") + 1).replace("G", "")));
            }
            
            if (line.startsWith("USED:")) {
                value.setUsedDiskSpace(Double.parseDouble(line.substring(
                        line.indexOf(":") + 1).replace("G", "")));
            }
            
            if (line.startsWith("AVAILABLE:")) {
                value.setAvailableDiskSpace(Double.parseDouble(line.substring(
                        line.indexOf(":") + 1).replace("G", "")));
            }
        }
        
        reader.close();
        
        return value;
    }
    
}
