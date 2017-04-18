/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.entity;

import java.util.ArrayList;
import java.util.List;

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
    private double  glAdjustedDebtors = 0.0d;   
    private double  glAdjustedStock = 0.0d;   
    private boolean glInBalance = false;
    private double   debtorsCF  = 0.0d;
    private double   debtorsBF = 0.0d;
    private String  strDebtorsBF;
    private String  strDebtorsCF;
    private double  finStockCF  = 0.0d;
    private double  finStockBF = 0.0d;
    private double  physStockCF  = 0.0d;
    private double  physStockBF = 0.0d;
    
    private String  strFinStockCF ;
    private String  strFinStockBF ;
    private String  strPhysStockCF;
    private String  strPhysStockBF;
        
    private double  instoreDebtorsCF  = 0.0d;
    private double  instoreFinStockCF  = 0.0d;
    private String  strInstoreFinStockCF;
    private double  instorePhysStockCF = 0.0d;
    private String  strInstorePhysStockCF; 
    private double  instoreStockPhysical = 0.0d;
    private String  strInstoreDebtorsCF; 
    private double  debtorsFinancialMovement = 0.0d;
    private double  stockFinancialMovement = 0.0d;  
    private String  strDebtorsFinancialMovement;
    private String  strStockFinancialMovement;  
    private List<String> errorMessages = new ArrayList<String>();
    
    
    
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

    /**
     * @return the glAdjustedDebtors
     */
    public double getGlAdjustedDebtors() {
        return glAdjustedDebtors;
    }

    /**
     * @param glAdjustedDebtors the glAdjustedDebtors to set
     */
    public void setGlAdjustedDebtors(double glAdjustedDebtors) {
        this.glAdjustedDebtors = glAdjustedDebtors;
    }

    /**
     * @return the glAdjustedStock
     */
    public double getGlAdjustedStock() {
        return glAdjustedStock;
    }

    /**
     * @param glAdjustedStock the glAdjustedStock to set
     */
    public void setGlAdjustedStock(double glAdjustedStock) {
        this.glAdjustedStock = glAdjustedStock;
    }

    /**
     * @return the glInBalance
     */
    public boolean isGlInBalance() {
        return glInBalance;
    }

    /**
     * @param glInBalance the glInBalance to set
     */
    public void setGlInBalance(boolean glInBalance) {
        this.glInBalance = glInBalance;
    }

    /**
     * @return the debtorsCF
     */
    public double getDebtorsCF() {
        return debtorsCF;
    }

    /**
     * @param debtorsCF the debtorsCF to set
     */
    public void setDebtorsCF(double debtorsCF) {
        this.debtorsCF = debtorsCF;
    }

    /**
     * @return the debtorsBF
     */
    public double getDebtorsBF() {
        return debtorsBF;
    }

    /**
     * @param debtorsBF the debtorsBF to set
     */
    public void setDebtorsBF(double debtorsBF) {
        this.debtorsBF = debtorsBF;
    }

    /**
     * @return the finStockCF
     */
    public double getFinStockCF() {
        return finStockCF;
    }

    /**
     * @param finStockCF the finStockCF to set
     */
    public void setFinStockCF(double finStockCF) {
        this.finStockCF = finStockCF;
    }

    /**
     * @return the finStockBF
     */
    public double getFinStockBF() {
        return finStockBF;
    }

    /**
     * @param finStockBF the finStockBF to set
     */
    public void setFinStockBF(double finStockBF) {
        this.finStockBF = finStockBF;
    }

    /**
     * @return the phyStockCF
     */
    public double getPhysStockCF() {
        return physStockCF;
    }

    /**
     * @param phyStockCF the phyStockCF to set
     */
    public void setPhysStockCF(double physStockCF) {
        this.physStockCF = physStockCF;
    }

    /**
     * @return the phyStockBF
     */
    public double getPhysStockBF() {
        return physStockBF;
    }

    /**
     * @param phyStockBF the phyStockBF to set
     */
    public void setPhysStockBF(double physStockBF) {
        this.physStockBF = physStockBF;
    }

    /**
     * @return the instoreDebtorsCF
     */
    public double getInstoreDebtorsCF() {
        return instoreDebtorsCF;
    }

    /**
     * @param instoreDebtorsCF the instoreDebtorsCF to set
     */
    public void setInstoreDebtorsCF(double instoreDebtorsCF) {
        this.instoreDebtorsCF = instoreDebtorsCF;
    }

    /**
     * @return the instoreFinStockCF
     */
    public double getInstoreFinStockCF() {
        return instoreFinStockCF;
    }

    /**
     * @param instoreFinStockCF the instoreFinStockCF to set
     */
    public void setInstoreFinStockCF(double instoreFinStockCF) {
        this.instoreFinStockCF = instoreFinStockCF;
    }

    /**
     * @return the instorePhyStockCF
     */
    public double getInstorePhysStockCF() {
        return instorePhysStockCF;
    }

    /**
     * @param instorePhyStockCF the instorePhyStockCF to set
     */
    public void setInstorePhysStockCF(double instorePhysStockCF) {
        this.instorePhysStockCF = instorePhysStockCF;
    }

    /**
     * @return the instoreStockPhysical
     */
    public double getInstoreStockPhysical() {
        return instoreStockPhysical;
    }

    /**
     * @param instoreStockPhysical the instoreStockPhysical to set
     */
    public void setInstoreStockPhysical(double instoreStockPhysical) {
        this.instoreStockPhysical = instoreStockPhysical;
    }

    /**
     * @return the strDebtorsBF
     */
    public String getStrDebtorsBF() {
        return strDebtorsBF;
    }

    /**
     * @param strDebtorsBF the strDebtorsBF to set
     */
    public void setStrDebtorsBF(String strDebtorsBF) {
        this.strDebtorsBF = strDebtorsBF;
    }

    /**
     * @return the strDebtorsCF
     */
    public String getStrDebtorsCF() {
        return strDebtorsCF;
    }
    
    public void setStrDebtorsCF(String strDebtorsCF) {
        this.strDebtorsCF = strDebtorsCF;
    }

    /**
     * @return the strInstoreDebtorsCF
     */
    public String getStrInstoreDebtorsCF() {
        return strInstoreDebtorsCF;
    }

    /**
     * @param strInstoreDebtorsCF the strInstoreDebtorsCF to set
     */
    public void setStrInstoreDebtorsCF(String strInstoreDebtorsCF) {
        this.strInstoreDebtorsCF = strInstoreDebtorsCF;
    }

    /**
     * @return the debtorsFinancialMovement
     */
    public double getDebtorsFinancialMovement() {
        return debtorsFinancialMovement;
    }

    /**
     * @param debtorsFinancialMovement the debtorsFinancialMovement to set
     */
    public void setDebtorsFinancialMovement(double debtorsFinancialMovement) {
        this.debtorsFinancialMovement = debtorsFinancialMovement;
    }

    /**
     * @return the stockFinancialMovement
     */
    public double getStockFinancialMovement() {
        return stockFinancialMovement;
    }

    /**
     * @param stockFinancialMovement the stockFinancialMovement to set
     */
    public void setStockFinancialMovement(double stockFinancialMovement) {
        this.stockFinancialMovement = stockFinancialMovement;
    }

    /**
     * @return the strDebtorsFinancialMovement
     */
    public String getStrDebtorsFinancialMovement() {
        return strDebtorsFinancialMovement;
    }

    /**
     * @param strDebtorsFinancialMovement the strDebtorsFinancialMovement to set
     */
    public void setStrDebtorsFinancialMovement(String strDebtorsFinancialMovement) {
        this.strDebtorsFinancialMovement = strDebtorsFinancialMovement;
    }

    /**
     * @return the strStockFinancialMovement
     */
    public String getStrStockFinancialMovement() {
        return strStockFinancialMovement;
    }

    /**
     * @param strStockFinancialMovement the strStockFinancialMovement to set
     */
    public void setStrStockFinancialMovement(String strStockFinancialMovement) {
        this.strStockFinancialMovement = strStockFinancialMovement;
    }

    /**
     * @return the strInstoreFinStockCF
     */
    public String getStrInstoreFinStockCF() {
        return strInstoreFinStockCF;
    }

    /**
     * @param strInstoreFinStockCF the strInstoreFinStockCF to set
     */
    public void setStrInstoreFinStockCF(String strInstoreFinStockCF) {
        this.strInstoreFinStockCF = strInstoreFinStockCF;
    }

    /**
     * @return the strInstorePhysStockCF
     */
    public String getStrInstorePhysStockCF() {
        return strInstorePhysStockCF;
    }

    /**
     * @param strInstorePhysStockCF the strInstorePhysStockCF to set
     */
    public void setStrInstorePhysStockCF(String strInstorePhysStockCF) {
        this.strInstorePhysStockCF = strInstorePhysStockCF;
    }

    /**
     * @return the strFinStockCF
     */
    public String getStrFinStockCF() {
        return strFinStockCF;
    }

    /**
     * @param strFinStockCF the strFinStockCF to set
     */
    public void setStrFinStockCF(String strFinStockCF) {
        this.strFinStockCF = strFinStockCF;
    }

    /**
     * @return the strFinStockBF
     */
    public String getStrFinStockBF() {
        return strFinStockBF;
    }

    /**
     * @param strFinStockBF the strFinStockBF to set
     */
    public void setStrFinStockBF(String strFinStockBF) {
        this.strFinStockBF = strFinStockBF;
    }

    /**
     * @return the strPhysStockCF
     */
    public String getStrPhysStockCF() {
        return strPhysStockCF;
    }

    /**
     * @param strPhysStockCF the strPhysStockCF to set
     */
    public void setStrPhysStockCF(String strPhysStockCF) {
        this.strPhysStockCF = strPhysStockCF;
    }

    /**
     * @return the strPhysStockBF
     */
    public String getStrPhysStockBF() {
        return strPhysStockBF;
    }

    /**
     * @param strPhysStockBF the strPhysStockBF to set
     */
    public void setStrPhysStockBF(String strPhysStockBF) {
        this.strPhysStockBF = strPhysStockBF;
    }

    /**
     * @return the errorMessages
     */
    public List<String> getErrorMessages() {
        return errorMessages;
    }

    /**
     * @param errorMessages the errorMessages to set
     */
    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    /**
     * @return the errorMessages
     */
   
    
    
    /**
     * @return the instoreDebtorsCF
     */
    
}
