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
public class MonthendSearchEntity {
      private String branchCode;
    private String process;
    private String fppCde;
    
    public MonthendSearchEntity() {
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }
    
    public String getFppCde() {
        return fppCde;
    }

    public void setFppCde(String fppCde) {
        this.fppCde = fppCde;
    }
    
}
