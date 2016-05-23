/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.servlet.helper;

import java.util.List;
import za.co.argility.furnmart.entity.ReplicationEntity;
import za.co.argility.furnmart.entity.ReplicationSearchEntity;

/**
 *
 * @author tmaleka
 */
public class ReplicationData  {
    
   public static final String BRANCK_OK_IMAGE_URL = 
            "http://i879.photobucket.com/albums/ab359/tmaleka/ARGILITY/correct_2_zpsf7b3590b.png";
   public static final String BRANCH_WARNING_IMAGE_URL = 
            "http://i879.photobucket.com/albums/ab359/tmaleka/ARGILITY/warning_2_zps4954eb85.png";
   public static final String BRANCH_ERROR_IMAGE_URL =  
            "\"images/error.png\"";
    
    
    public static final int EXPORT_TYPE_FILTER = 0;
    public static final int EXPORT_TYPE_UNHEALTHY_BRANCHES = 1;
    
    private List<ReplicationEntity> replicationDetails;
    private List<String> replicationBranchList;
    private ReplicationSearchEntity search;
    private List<String> processes;
    
    public ReplicationData() {
    }

    public List<ReplicationEntity> getReplicationDetails() {
        return replicationDetails;
    }

    public void setReplicationDetails(List<ReplicationEntity> replicationDetails) {
        this.replicationDetails = replicationDetails;
    }

    public List<String> getReplicationBranchList() {
        return replicationBranchList;
    }

    public void setReplicationBranchList(List<String> replicationBranchList) {
        this.replicationBranchList = replicationBranchList;
    }

    public ReplicationSearchEntity getSearch() {
        return search;
    }

    public void setSearch(ReplicationSearchEntity search) {
        this.search = search;
    }

    public List<String> getProcesses() {
        return processes;
    }

    public void setProcesses(List<String> processes) {
        this.processes = processes;
    }
    
    
}
