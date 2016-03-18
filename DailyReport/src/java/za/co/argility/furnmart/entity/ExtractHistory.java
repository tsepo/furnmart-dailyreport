/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.Seconds;

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
    private Date filesLastSent;
    private boolean status;
    private String reason;
    private String checkpointAudit;
    private Date checkpointDate;
    private int daysBehind;
    
    public ExtractHistory() {
        this.branch = null;
        this.period = null;
        this.processType = null;
        this.extractType = null;
        this.startTime = null;
        this.endTime = null;
        this.extractError = null;
        this.filesLastSent = null;
        this.status = false;
        this.reason = null;   
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

    public Date getFilesLastSent() {
        return filesLastSent;
    }
    
    public void setFilesLastSent(Date filesLastSent) {
        this.filesLastSent = filesLastSent;
    }
    
    public boolean getStatus() {
        return this.status;
    }
               
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public String getReason() {
        return this.reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public String getCheckpointAudit() {
        return this.checkpointAudit;
    }
    
    public void setcheckpointAudit(String checkpointAudit) {
        this.checkpointAudit = checkpointAudit;
    }
    
    public Date getCheckpointDate() {
        return this.checkpointDate;
    }
    
    public void setCheckpointDate(Date checkpointDate) {
        this.checkpointDate = checkpointDate;
    }
    
    public int getDaysBehind() {
        return daysBehind;
    }
    
    public void setDaysBehind(int daysBehind) {
        this.daysBehind = daysBehind;
    }
    
    public String toHtml() {
         
        final String DATE_FORMAT = "dd MMMM yyyy HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        String html = "";
        ExtractProgress progress = null;
        
        
        // Determine the current progress of the extract
        if (extractError == null && endTime != null) 
            progress = ExtractProgress.Finished;
        else if (extractError != null && endTime == null)
            progress = ExtractProgress.ErrorOccured;
        else
            progress = ExtractProgress.InProgress;
        
       
        if (progress == ExtractProgress.Finished)
            html += "<img src=\"images/ok.png\" style=\"width:36px\" alt=\"ok\" />";
        
        else if (progress == ExtractProgress.ErrorOccured)
            html += "<img src=\"images/error.png\" style=\"width:36px\" alt=\"error\" />";
        else
            html += "<img src=\"images/in_progress.png\" style=\"width:36px\" alt=\"in progress\" />";
        
        
        html += "<p>";
     
        if (progress == ExtractProgress.InProgress)
            html += "<span>" +
                    "<b>STARTED:</b> " +
                     format.format(startTime)
                     + "</span><br/>";
        
        else if (progress == ExtractProgress.ErrorOccured)
            html += "<span>" +
                    "<b>ABORTED:</b> " +
                     format.format(startTime)
                     + "</span><br/>";
            
        else
            html += "<span>" + 
                    "<b>FINISHED:</b> " +
                     format.format(endTime)
                     + "</span><br/>";
        
        if (endTime != null) {
            
            int total = Seconds.secondsBetween(new DateTime(startTime), new DateTime(endTime)).getSeconds();
            int seconds = total % 60;
            int minutes = (int)(total / 60);
            
            String duration = "";
            
            if (minutes == 0 && seconds > 0) {
                duration = seconds + " sec";
            }
            
            else if (minutes > 0 && seconds == 0) {
                duration = minutes + " min";
            }
            
            else if (minutes == 0 && seconds == 0) {
                duration = "under a second";
            }
            
            else {
                duration = minutes + " min, " +  seconds + " sec";
            }
            
            html += "<span><b>DURATION: </b>" + 
                     duration +
                    "</span>";
        }
        
        return html + "</p>";
        
    }
    
}
