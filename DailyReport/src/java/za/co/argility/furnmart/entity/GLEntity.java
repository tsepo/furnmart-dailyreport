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
public class GLEntity {
    private String branchCode;    
    private String branchDesc;
    private double  instoreDebtors = 0.0d;  
    private double  glDebtors = 0.0d;
    private double  instoreStock = 0.0d;
    private double  glStock = 0.0d;      

    /**
     * @return the branchCode
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * @param branchCode the branchCode to set
     */
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    /**
     * @return the branchDesc
     */
    public String getBranchDesc() {
        return branchDesc;
    }

    /**
     * @param branchDesc the branchDesc to set
     */
    public void setBranchDesc(String branchDesc) {
        this.branchDesc = branchDesc;
    }

    /**
     * @return the instoreDebtors
     */
    public double getInstoreDebtors() {
        return instoreDebtors;
    }

    /**
     * @param instoreDebtors the instoreDebtors to set
     */
    public void setInstoreDebtors(double instoreDebtors) {
        this.instoreDebtors = instoreDebtors;
    }

    /**
     * @return the glDebtors
     */
    public double getGlDebtors() {
        return glDebtors;
    }

    /**
     * @param glDebtors the glDebtors to set
     */
    public void setGlDebtors(double glDebtors) {
        this.glDebtors = glDebtors;
    }

    /**
     * @return the istoreStock
     */
    public double getInstoreStock() {
        return instoreStock;
    }

    /**
     * @param istoreStock the istoreStock to set
     */
    public void setInstoreStock(double instoreStock) {
        this.instoreStock = instoreStock;
    }

    /**
     * @return the glStock
     */
    public double getGlStock() {
        return glStock;
    }

    /**
     * @param glStock the glStock to set
     */
    public void setGlStock(double glStock) {
        this.glStock = glStock;
    }
}
