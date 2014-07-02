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
public class GLSubType {
    private int glActType ;
    private int glSubType ;
    private String glActDesc; 

    /**
     * @return the glActType
     */
    public int getGlActType() {
        return glActType;
    }

    /**
     * @param glActType the glActType to set
     */
    public void setGlActType(int glActType) {
        this.glActType = glActType;
    }

    /**
     * @return the glSubType
     */
    public int getGlSubType() {
        return glSubType;
    }

    /**
     * @param glSubType the glSubType to set
     */
    public void setGlSubType(int glSubType) {
        this.glSubType = glSubType;
    }

    /**
     * @return the glActDesc
     */
    public String getGlActDesc() {
        return glActDesc;
    }

    /**
     * @param glActDesc the glActDesc to set
     */
    public void setGlActDesc(String glActDesc) {
        this.glActDesc = glActDesc;
    }
    
    
}
