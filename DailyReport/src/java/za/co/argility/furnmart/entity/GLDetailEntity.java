/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.entity;

/**
 *
 * @author rnaidoo
 */
public class GLDetailEntity {
    
    private int actionType ; 
    private String description;
    private double instoreVal = 0.0d;
    private double  glVal = 0.0d;
    private String status ;

    /**
     * @return the actionType
     */
    public int getActionType() {
        return actionType;
    }

    /**
     * @param actionType the actionType to set
     */
    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

   
    /**
     * @return the instoreVal
     */
    public double getInstoreVal() {
        return instoreVal;
    }

    /**
     * @param instoreVal the instoreVal to set
     */
    public void setInstoreVal(double instoreVal) {
        this.instoreVal = instoreVal;
    }

    /**
     * @return the glVal
     */
    public double getGlVal() {
        return glVal;
    }

    /**
     * @param glVal the glVal to set
     */
    public void setGlVal(double glVal) {
        this.glVal = glVal;
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
