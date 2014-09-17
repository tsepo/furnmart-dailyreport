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
public class GLDeteailGLEntity {
    private int actType;
    private double value = 0.0; 

    /**
     * @return the actType
     */
    public int getActType() {
        return actType;
    }

    /**
     * @param actType the actType to set
     */
    public void setActType(int actType) {
        this.actType = actType;
    }

    /**
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }
    
}
