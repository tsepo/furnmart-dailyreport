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
public class MonthendStatusEntity {
   
    private String brCde = null;
    private String brDesc = null;
    private String process;
    private Date   procStartDte;
    private Date   prorcEndDte;
    private int    errorId; 
    private boolean allDataSelected;
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
     * @return the process
     */
    public String getProcess() {
        return process;
    }

    /**
     * @param process the process to set
     */
    public void setProcess(String process) {
        this.process = process;
    }

    /**
     * @return the procStartDte
     */
    public Date getProcStartDte() {
        return procStartDte;
    }

    /**
     * @param procStartDte the procStartDte to set
     */
    public void setProcStartDte(Date procStartDte) {
        this.procStartDte = procStartDte;
    }

    /**
     * @return the prorcEndDte
     */
    public Date getProrcEndDte() {
        return prorcEndDte;
    }

    /**
     * @param prorcEndDte the prorcEndDte to set
     */
    public void setProrcEndDte(Date prorcEndDte) {
        this.prorcEndDte = prorcEndDte;
    }

    /**
     * @return the errorId
     */
    public int getErrorId() {
        return errorId;
    }

    /**
     * @param errorId the errorId to set
     */
    public void setErrorId(int errorId) {
        this.errorId = errorId;
    }

    /**
     * @return the allDataSelected
     */
    public boolean isAllDataSelected() {
        return allDataSelected;
    }

    /**
     * @param allDataSelected the allDataSelected to set
     */
    public void setAllDataSelected(boolean allDataSelected) {
        this.allDataSelected = allDataSelected;
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

    /**
     * @return the brDesc
     */
    public String getBrDesc() {
        return brDesc;
    }

    /**
     * @param brDesc the brDesc to set
     */
    public void setBrDesc(String brDesc) {
        this.brDesc = brDesc;
    }

    /**
     * @return the brCde
     */
    
}