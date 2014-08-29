/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.servlet.helper;
import java.util.HashMap;
import java.util.List;
import za.co.argility.furnmart.entity.GLEntity;
import za.co.argility.furnmart.entity.GLMapActTyp;
import za.co.argility.furnmart.entity.GLSubType;
import za.co.argility.furnmart.entity.MonthendEntity;
import za.co.argility.furnmart.entity.MonthendSearchEntity;
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
    
}
