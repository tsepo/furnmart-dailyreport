/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.servlet.helper;
import java.util.HashMap;
import java.util.List;
import za.co.argility.furnmart.entity.GLDetailEntity;
import za.co.argility.furnmart.entity.GLEntity;
import za.co.argility.furnmart.entity.GLMapActTyp;
import za.co.argility.furnmart.entity.GLSubType;
import za.co.argility.furnmart.entity.MonthendDetailStatusEntity;
import za.co.argility.furnmart.entity.MonthendEntity;
import za.co.argility.furnmart.entity.MonthendSearchEntity;
import za.co.argility.furnmart.entity.MonthendStatusEntity;
import za.co.argility.furnmart.entity.ProdConsScriptsEntity;
import za.co.argility.furnmart.entity.ProdConsViewEntity;
import za.co.argility.furnmart.util.BucketMap;



/**
 *
 * @author rnaidoo
 */
public class MonthendData {
    
   
    private List<MonthendEntity> monthendDetails;
    private List<String> monthendBranchList;
    private MonthendSearchEntity search;
    private List<String> processes;
    private List<GLSubType> glSubType;
    private List<GLMapActTyp> glMapActType;
    private List<MonthendProcesses> monthendProcesses;
    private List<GLEntity> glDetails;
    private List<GLDetailEntity> glDets;
    private String glDetailBranchCode;
    private String processStatusDetailBranchCode;
    private String glDeatilType; 
    private boolean isAllGLSelected = true;
    private boolean isAllProcessStatusSelected = true;
    private boolean isAllStatementsSelected = true;
    private List<ProdConsScriptsEntity> prodConsSelectedEntities;
    private List<ProdConsScriptsEntity> prodConsEntities;
    private List<ProdConsViewEntity> prodConsViewEntities;
    private List<String> fppCdeList;
    private List<MonthendStatusEntity> monthendStatusEntity;
    private List<MonthendDetailStatusEntity> monthendDetailStatusEntity;
    private boolean consRun;
    private String selectedFppCde;
    
    public MonthendData() {
    }

    /**
     * @return the replicationDetails
     */
    public List<MonthendEntity> getMonthendDetails() {
        return monthendDetails;
    }

    /**
     * @param replicationDetails the replicationDetails to set
     */
    public void setMonthendDetails(List<MonthendEntity> replicationDetails) {
        this.monthendDetails = replicationDetails;
    }

    /**
     * @return the monthendBranchList
     */
    public List<String> getMonthendBranchList() {
        return monthendBranchList;
    }

    /**
     * @param monthendBranchList the monthendBranchList to set
     */
    public void setMonthendBranchList(List<String> monthendBranchList) {
        this.monthendBranchList = monthendBranchList;
    }

    /**
     * @return the search
     */
    public MonthendSearchEntity getSearch() {
        return search;
    }

    /**
     * @param search the search to set
     */
    public void setSearch(MonthendSearchEntity search) {
        this.search = search;
    }

    /**
     * @return the processes
     */
    public List<String> getProcesses() {
        return processes;
    }

    /**
     * @param processes the processes to set
     */
    public void setProcesses(List<String> processes) {
        this.processes = processes;
    }

    /**
     * @return the glSubType
     */
    public List<GLSubType> getGlSubType() {
        return glSubType;
    }

    /**
     * @param glSubType the glSubType to set
     */
    public void setGlSubType(List<GLSubType> glSubType) {
        this.glSubType = glSubType;
    }

    /**
     * @return the monthendProcesses
     */
    public List<MonthendProcesses> getMonthendProcesses() {
        return monthendProcesses;
    }

    /**
     * @param monthendProcesses the monthendProcesses to set
     */
    public void setMonthendProcesses(List<MonthendProcesses> monthendProcesses) {
        this.monthendProcesses = monthendProcesses;
    }

    /**
     * @return the glMapActType
     */
    public List<GLMapActTyp> getGlMapActType() {
        return glMapActType;
    }

    /**
     * @param glMapActType the glMapActType to set
     */
    public void setGlMapActType(List<GLMapActTyp> glMapActType) {
        this.glMapActType = glMapActType;
    }

    /**
     * @return the glDetails
     */
    public List<GLEntity> getGlDetails() {
        return glDetails;
    }

    /**
     * @param glDetails the glDetails to set
     */
    public void setGlDetails(List<GLEntity> glDetails) {
        this.glDetails = glDetails;
    }

    /**
     * @return the glDets
     */
    public List<GLDetailEntity> getGlDets() {
        return glDets;
    }

    /**
     * @param glDets the glDets to set
     */
    public void setGlDets(List<GLDetailEntity> glDets) {
        this.glDets = glDets;
    }

    /**
     * @return the glDetailBranchCode
     */
    public String getGlDetailBranchCode() {
        return glDetailBranchCode;
        
    }

    /**
     * @param glDetailBranchCode the glDetailBranchCode to set
     */
    public void setGlDetailBranchCode(String glDetailBranchCode) {
        this.glDetailBranchCode = glDetailBranchCode;
    }

    /**
     * @return the glDeatilType
     */
    public String getGlDeatilType() {
        return glDeatilType;
    }

    /**
     * @param glDeatilType the glDeatilType to set
     */
    public void setGlDeatilType(String glDeatilType) {
        this.glDeatilType = glDeatilType;
    }

    /**
     * @return the isAllGLSelected
     */
    public boolean isIsAllGLSelected() {
        return isAllGLSelected;
    }

    /**
     * @param isAllGLSelected the isAllGLSelected to set
     */
    public void setIsAllGLSelected(boolean isAllGLSelected) {
        this.isAllGLSelected = isAllGLSelected;
    }

    /**
     * @return the prodConsEntities
     */
    public List<ProdConsScriptsEntity> getProdConsEntities() {
        return prodConsEntities;
    }

    /**
     * @param prodConsEntities the prodConsEntities to set
     */
    public void setProdConsEntities(List<ProdConsScriptsEntity> prodConsEntities) {
        this.prodConsEntities = prodConsEntities;
    }

    /**
     * @return the consRun
     */
    public boolean isConsRun() {
        return consRun;
    }

    /**
     * @param consRun the consRun to set
     */
    public void setConsRun(boolean consRun) {
        this.consRun = consRun;
    }

    /**
     * @return the prodConsSelectedEntities
     */
    public List<ProdConsScriptsEntity> getProdConsSelectedEntities() {
        return prodConsSelectedEntities;
    }

    /**
     * @param prodConsSelectedEntities the prodConsSelectedEntities to set
     */
    public void setProdConsSelectedEntities(List<ProdConsScriptsEntity> prodConsSelectedEntities) {
        this.prodConsSelectedEntities = prodConsSelectedEntities;
    }

    /**
     * @return the prodConsViewEntities
     */
    public List<ProdConsViewEntity> getProdConsViewEntities() {
        return prodConsViewEntities;
    }

    /**
     * @param prodConsViewEntities the prodConsViewEntities to set
     */
    public void setProdConsViewEntities(List<ProdConsViewEntity> prodConsViewEntities) {
        this.prodConsViewEntities = prodConsViewEntities;
    }

    /**
     * @return the isAllStatementsSelected
     */
    public boolean isIsAllStatementsSelected() {
        return isAllStatementsSelected;
    }

    /**
     * @param isAllStatementsSelected the isAllStatementsSelected to set
     */
    public void setIsAllStatementsSelected(boolean isAllStatementsSelected) {
        this.isAllStatementsSelected = isAllStatementsSelected;
    }
    
    
     public List<String> getFppCdeList() {
        return fppCdeList;
    }

    /**
     * @param glDeatilType the glDeatilType to set
     */
    public void setFppCdeList(List<String> fppCdeList) {
        this.fppCdeList = fppCdeList;
    }
    
    
    public String getSelectedFppCde() {
        return selectedFppCde;
    }

    /**
     * @param glDeatilType the glDeatilType to set
     */
    public void setSelectedFppCde(String selectedFppCde) {
        this.selectedFppCde = selectedFppCde;
    }

    /**
     * @return the monthendStatusEntity
     */
    public List<MonthendStatusEntity> getMonthendStatusEntity() {
        return monthendStatusEntity;
    }

    /**
     * @param monthendStatusEntity the monthendStatusEntity to set
     */
    public void setMonthendStatusEntity(List<MonthendStatusEntity> monthendStatusEntity) {
        this.monthendStatusEntity = monthendStatusEntity;
    }

    /**
     * @return the isAllProcessStatusSelected
     */
    public boolean isIsAllProcessStatusSelected() {
        return isAllProcessStatusSelected;
    }

    /**
     * @param isAllProcessStatusSelected the isAllProcessStatusSelected to set
     */
    public void setIsAllProcessStatusSelected(boolean isAllProcessStatusSelected) {
        this.isAllProcessStatusSelected = isAllProcessStatusSelected;
    }

    /**
     * @return the monthendDetailStatusEntity
     */
    public List<MonthendDetailStatusEntity> getMonthendDetailStatusEntity() {
        return monthendDetailStatusEntity;
    }

    /**
     * @param monthendDetailStatusEntity the monthendDetailStatusEntity to set
     */
    public void setMonthendDetailStatusEntity(List<MonthendDetailStatusEntity> monthendDetailStatusEntity) {
        this.monthendDetailStatusEntity = monthendDetailStatusEntity;
    }

    /**
     * @return the processStatusDetailBranchCode
     */
    public String getProcessStatusDetailBranchCode() {
        return processStatusDetailBranchCode;
    }

    /**
     * @param processStatusDetailBranchCode the processStatusDetailBranchCode to set
     */
    public void setProcessStatusDetailBranchCode(String processStatusDetailBranchCode) {
        this.processStatusDetailBranchCode = processStatusDetailBranchCode;
    }
    
}
