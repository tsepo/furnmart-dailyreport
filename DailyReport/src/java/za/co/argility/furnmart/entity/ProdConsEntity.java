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
public class ProdConsEntity {
    /*prod_cons_id        | integer                     | not null
 prod_cons_desc      | text                        | 
 prod_cons_script    | text                        | 
 prod_cons_error     | text                        | 
 prod_cons_start_dte | timestamp without time zone | 
 prod_cons_end_dte   | timestamp without time zone | 
 prod_cons_active    | boolean                     | 
*/

    
    private int prodConsId;
    private String prodConsDesc;
    private String prodConsScript;
    private String prodConsError;
    private Date prodConsStartDte;
    private Date prodConsEndDte;
    private boolean prodConsActive;

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
     * @return the prodConsDesc
     */
    public String getProdConsDesc() {
        return prodConsDesc;
    }

    /**
     * @param prodConsDesc the prodConsDesc to set
     */
    public void setProdConsDesc(String prodConsDesc) {
        this.prodConsDesc = prodConsDesc;
    }

    /**
     * @return the prodConsScript
     */
    public String getProdConsScript() {
        return prodConsScript;
    }

    /**
     * @param prodConsScript the prodConsScript to set
     */
    public void setProdConsScript(String prodConsScript) {
        this.prodConsScript = prodConsScript;
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

    /**
     * @return the prodConsActive
     */
    public boolean isProdConsActive() {
        return prodConsActive;
    }

    /**
     * @param prodConsActive the prodConsActive to set
     */
    public void setProdConsActive(boolean prodConsActive) {
        this.prodConsActive = prodConsActive;
    }
    
    
    
    
}
