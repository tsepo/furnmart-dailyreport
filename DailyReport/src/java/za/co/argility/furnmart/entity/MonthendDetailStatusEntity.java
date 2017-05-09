/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.entity;

import java.util.Date;

/**
 *
 * @author rnaidoo
 */
public class MonthendDetailStatusEntity {
    
    /* br_cde |   mendstat_process    |       mendstat_start       |       mendstat_start       |        mendstat_end*/
    
    private String brCde = null;
    private String monthendProcess = null;
    private Date processStartDte;
    private Date processEndDte;
    private int errorCde;
    private String status;

    /**
     * @return the brCde
     */
    public String getBrCde() {
        return brCde;
    }

    /**
     * @param brCde the brCde to set
     */
    public void setBrCde(String brCde) {
        this.brCde = brCde;
    }

    /**
     * @return the monthendProcess
     */
    public String getMonthendProcess() {
        return monthendProcess;
    }

    /**
     * @param monthendProcess the monthendProcess to set
     */
    public void setMonthendProcess(String monthendProcess) {
        this.monthendProcess = monthendProcess;
    }

    /**
     * @return the processStartDte
     */
    public Date getProcessStartDte() {
        return processStartDte;
    }

    /**
     * @param processStartDte the processStartDte to set
     */
    public void setProcessStartDte(Date processStartDte) {
        this.processStartDte = processStartDte;
    }

    /**
     * @return the processEndDte
     */
    public Date getProcessEndDte() {
        return processEndDte;
    }

    /**
     * @param processEndDte the processEndDte to set
     */
    public void setProcessEndDte(Date processEndDte) {
        this.processEndDte = processEndDte;
    }

    /**
     * @return the errorCde
     */
    public int getErrorCde() {
        return errorCde;
    }

    /**
     * @param errorCde the errorCde to set
     */
    public void setErrorCde(int errorCde) {
        this.errorCde = errorCde;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
