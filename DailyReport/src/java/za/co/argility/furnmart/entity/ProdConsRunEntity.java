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
public class ProdConsRunEntity {
    private int prodConsId;
    private String fppCde;
    private String prodConsError;
    private Date prodConsStartDte;
    private Date prodConsEndDte;

    /**
     * @return the prodConsId
     */
    public int getProdConsId() {
        return prodConsId;
    }

    /**
     * @param prodConsId the prodConsId to set
     */
    public void setProdConsId(int prodConsId) {
        this.prodConsId = prodConsId;
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
     * @return the prodConsError
     */
    public String getProdConsError() {
        return prodConsError;
    }

    /**
     * @param prodConsError the prodConsError to set
     */
    public void setProdConsError(String prodConsError) {
        this.prodConsError = prodConsError;
    }

    /**
     * @return the prodConsStartDte
     */
    public Date getProdConsStartDte() {
        return prodConsStartDte;
    }

    /**
     * @param prodConsStartDte the prodConsStartDte to set
     */
    public void setProdConsStartDte(Date prodConsStartDte) {
        this.prodConsStartDte = prodConsStartDte;
    }

    /**
     * @return the prodConsEndDte
     */
    public Date getProdConsEndDte() {
        return prodConsEndDte;
    }

    /**
     * @param prodConsEndDte the prodConsEndDte to set
     */
    public void setProdConsEndDte(Date prodConsEndDte) {
        this.prodConsEndDte = prodConsEndDte;
    }
    
    
}
