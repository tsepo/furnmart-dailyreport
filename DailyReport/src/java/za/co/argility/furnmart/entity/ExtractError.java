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
public class ExtractError {
    
    private int errorId;
    private Date dateOfError;
    private String stackTrace;
    
    private ExtractError() {
        this.dateOfError = null;
        this.stackTrace = null;
    }
    
    public ExtractError(int errorId) {
        this();
        this.errorId = errorId;
    }

    public int getErrorId() {
        return errorId;
    }
    
    public Date getDateOfError() {
        return dateOfError;
    }

    public void setDateOfError(Date dateOfError) {
        this.dateOfError = dateOfError;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
    
    
}
