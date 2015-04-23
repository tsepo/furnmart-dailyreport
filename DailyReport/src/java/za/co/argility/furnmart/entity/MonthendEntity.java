/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.entity;

import java.util.Date;
import java.util.List;


public class MonthendEntity {
    private boolean isBranchOk;
    private String branchCode;    
    private List<String> comments;
    private String branchDesc;
    private String fppCde;
    private boolean isDebtorsRun;
    private boolean isCreditorsRun;
    private boolean isCashBookExtractRun;
    private boolean isNewGLTranExtRun;
    private boolean isPWCExtractsDelivered;
    private boolean isBucketsRun;
    private boolean isStatementsRun;

    
    /**
     * @return the isBranchOk
     */
    public boolean isIsBranchOk() {
        return isBranchOk;
    }

    /**
     * @param isBranchOk the isBranchOk to set
     */
    public void setIsBranchOk(boolean isBranchOk) {
        this.isBranchOk = isBranchOk;
    }

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
     * @return the comments
     */
    public List<String> getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    /**
     * @return the isDebtorsRun
     */
    public boolean isIsDebtorsRun() {
        return isDebtorsRun;
    }

    /**
     * @param isDebtorsRun the isDebtorsRun to set
     */
    public void setIsDebtorsRun(boolean isDebtorsRun) {
        this.isDebtorsRun = isDebtorsRun;
    }

    /**
     * @return the isCreditorsRun
     */
    public boolean isIsCreditorsRun() {
        return isCreditorsRun;
    }

    /**
     * @param isCreditorsRun the isCreditorsRun to set
     */
    public void setIsCreditorsRun(boolean isCreditorsRun) {
        this.isCreditorsRun = isCreditorsRun;
    }

    /**
     * @return the isCashBookExtractRun
     */
    public boolean isIsCashBookExtractRun() {
        return isCashBookExtractRun;
    }

    /**
     * @param isCashBookExtractRun the isCashBookExtractRun to set
     */
    public void setIsCashBookExtractRun(boolean isCashBookExtractRun) {
        this.isCashBookExtractRun = isCashBookExtractRun;
    }

    /**
     * @return the isNewGLTranExtRun
     */
    public boolean isIsNewGLTranExtRun() {
        return isNewGLTranExtRun;
    }

    /**
     * @param isNewGLTranExtRun the isNewGLTranExtRun to set
     */
    public void setIsNewGLTranExtRun(boolean isNewGLTranExtRun) {
        this.isNewGLTranExtRun = isNewGLTranExtRun;
    }

    /**
     * @return the branchType
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
     * @return the fppCde
     */
    public String getFppCde() {
        return fppCde;
    }

    /**
     * @param fppCde the fppCde to set
     */
    public void setFppCde(String fppCde) {
        this.fppCde = fppCde;
    }

    /**
     * @return the isPWCExtractsDelivered
     */
    public boolean isIsPWCExtractsDelivered() {
        return isPWCExtractsDelivered;
    }

    /**
     * @param isPWCExtractsDelivered the isPWCExtractsDelivered to set
     */
    public void setIsPWCExtractsDelivered(boolean isPWCExtractsDelivered) {
        this.isPWCExtractsDelivered = isPWCExtractsDelivered;
    }

    /**
     * @return the isBucketsRun
     */
    public boolean isIsBucketsRun() {
        return isBucketsRun;
    }

    /**
     * @param isBucketsRun the isBucketsRun to set
     */
    public void setIsBucketsRun(boolean isBucketsRun) {
        this.isBucketsRun = isBucketsRun;
    }

    /**
     * @return the isStatementsRun
     */
    public boolean isIsStatementsRun() {
        return isStatementsRun;
    }

    /**
     * @param isStatementsRun the isStatementsRun to set
     */
    public void setIsStatementsRun(boolean isStatementsRun) {
        this.isStatementsRun = isStatementsRun;
    }
    
    
}
