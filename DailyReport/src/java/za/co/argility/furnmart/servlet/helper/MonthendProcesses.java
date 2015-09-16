/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.servlet.helper;

/**
 *
 * @author rnaidoo
 */
public class MonthendProcesses {
     //prod_cde |         prod_class_desc          |         prod_method          |        prod_obj_jndi_name
    private int prodCde;
    private String prodClassDesc;
    private String prodMethod;
    private String prodClass; 

    /**
     * @return the prodCde
     */
    public int getProdCde() {
        return prodCde;
    }

    /**
     * @param prodCde the prodCde to set
     */
    public void setProdCde(int prodCde) {
        this.prodCde = prodCde;
    }

    /**
     * @return the prodClassDesc
     */
    public String getProdClassDesc() {
        return prodClassDesc;
    }

    /**
     * @param prodClassDesc the prodClassDesc to set
     */
    public void setProdClassDesc(String prodClassDesc) {
        this.prodClassDesc = prodClassDesc;
    }

    /**
     * @return the prodMethod
     */
    public String getProdMethod() {
        return prodMethod;
    }

    /**
     * @param prodMethod the prodMethod to set
     */
    public void setProdMethod(String prodMethod) {
        this.prodMethod = prodMethod;
    }

    /**
     * @return the prodClass
     */
    public String getProdClass() {
        return prodClass;
    }

    /**
     * @param prodClass the prodClass to set
     */
    public void setProdClass(String prodClass) {
        this.prodClass = prodClass;
    }
    
    
    
}
