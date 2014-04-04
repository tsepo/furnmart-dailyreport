/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.entity;

/**
 *
 * @author tmaleka
 */
public class ExtractType {
    
    private int extractType;
    private String extractDescription;
    private boolean active;
    
    public ExtractType(int extractType) {
        this();
        this.extractType = extractType;
    }
    
    private ExtractType() {
        this.extractDescription = null;
        this.active = true;
    }

    public int getExtractType() {
        return extractType;
    }
    
    public String getExtractDescription() {
        return extractDescription;
    }

    public void setExtractDescription(String extractDescription) {
        this.extractDescription = extractDescription;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    
}
