/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.entity;

import java.util.Date;

/**
 *
 * @author tmaleka
 */
public class NetworkEntity {
    
    private String branchCode;
    private String branchName;
    private boolean networkAvailable;
    private Date lastPrompted;
    
    public NetworkEntity() {
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public boolean isNetworkAvailable() {
        return networkAvailable;
    }

    public void setNetworkAvailable(boolean networkAvailable) {
        this.networkAvailable = networkAvailable;
    }

    public Date getLastPrompted() {
        return lastPrompted;
    }

    public void setLastPrompted(Date lastPrompted) {
        this.lastPrompted = lastPrompted;
    }
    
    
    
}
