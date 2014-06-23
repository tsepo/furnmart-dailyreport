/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.entity;

import java.util.Date;
import java.util.List;

/**
 *
 * @author tmaleka
 */
public class ReplicationEntity {
    
    private boolean isBranchOk;
    private String branchCode;
    private String branchName;
    private int audit;
    private int replicate;
    private int difference;
    private boolean isLocked;
    private Date lockedDate;
    private Date unlockedDate;
    private String process;
    private List<String> comments;
    private String period;
    
    public ReplicationEntity() {
        
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
    
    public int getReplicate() {
        return replicate;
    }

    public void setReplicate(int replicate) {
        this.replicate = replicate;
    }
    
    public boolean isBranchOk() {
        return isBranchOk;
    }

    public void setIsBranchOk(boolean isBranchOk) {
        this.isBranchOk = isBranchOk;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public int getAudit() {
        return audit;
    }

    public void setAudit(int audit) {
        this.audit = audit;
    }

    public int getDifference() {
        return difference;
    }

    public void setDifference(int difference) {
        this.difference = difference;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Date getLockedDate() {
        return lockedDate;
    }

    public void setLockedDate(Date lockedDate) {
        this.lockedDate = lockedDate;
    }

    public Date getUnlockedDate() {
        return unlockedDate;
    }

    public void setUnlockedDate(Date unlockedDate) {
        this.unlockedDate = unlockedDate;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
    
    
}
