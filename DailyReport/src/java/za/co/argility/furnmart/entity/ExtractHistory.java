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
public class ExtractHistory {
    
    private int historyId;
    private String branch;
    private String period;
    private ProcessType processType;
    private ExtractType extractType;
    private Date startTime;
    private Date endTime;
    private ExtractError extractError;
    
    private ExtractHistory() {
        this.branch = null;
        this.period = null;
        this.processType = null;
        this.extractType = null;
        this.startTime = null;
        this.endTime = null;
        this.extractError = null;
        
    }
    
    public ExtractHistory(int historyId) {
        this();
        this.historyId = historyId;
    }

    public int getHistoryId() {
        return historyId;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public ProcessType getProcessType() {
        return processType;
    }

    public void setProcessType(ProcessType processType) {
        this.processType = processType;
    }

    public ExtractType getExtractType() {
        return extractType;
    }

    public void setExtractType(ExtractType extractType) {
        this.extractType = extractType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public ExtractError getExtractError() {
        return extractError;
    }

    public void setExtractError(ExtractError extractError) {
        this.extractError = extractError;
    }
    
    
    
}
