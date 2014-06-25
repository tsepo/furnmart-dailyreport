/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.entity;

/**
 *
 * @author tmaleka
 */
public class DiskSpace {
    
    private String branchCode;
    private String description;
    private double totalDiskSpace;
    private double usedDiskSpace;
    private double availableDiskSpace;
    
    public DiskSpace() {
        this.branchCode = null;
        this.description = null;
        this.totalDiskSpace = 0;
        this.usedDiskSpace = 0;
        this.availableDiskSpace = 0;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotalDiskSpace() {
        return totalDiskSpace;
    }

    public void setTotalDiskSpace(double totalDiskSpace) {
        this.totalDiskSpace = totalDiskSpace;
    }

    public double getUsedDiskSpace() {
        return usedDiskSpace;
    }

    public void setUsedDiskSpace(double usedDiskSpace) {
        this.usedDiskSpace = usedDiskSpace;
    }

    public double getAvailableDiskSpace() {
        return availableDiskSpace;
    }

    public void setAvailableDiskSpace(double availableDiskSpace) {
        this.availableDiskSpace = availableDiskSpace;
    }
    
    public DiskUtilisationLevelType getDiskSpaceUtilisationLevel() {
        
        if (totalDiskSpace == 0)
            return DiskUtilisationLevelType.Low;
        
        double utilisation = (usedDiskSpace / totalDiskSpace) * 100;
        if (utilisation <= 30)
            return DiskUtilisationLevelType.Low;
        else if (utilisation > 30 && utilisation < 80)
            return DiskUtilisationLevelType.Normal;
        else
            return DiskUtilisationLevelType.High;
    }
    
    public double getDiskUtilisation() {
        if (totalDiskSpace == 0)
            return 0;
        else
            return (usedDiskSpace / totalDiskSpace) * 100;
    }
    
    public double getReservedDiskSpace() {
        return (totalDiskSpace - usedDiskSpace - availableDiskSpace);
    }
    
}
