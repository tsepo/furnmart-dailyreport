/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.servlet.helper;

import java.util.List;
import za.co.argility.furnmart.entity.NetworkEntity;

/**
 *
 * @author tmaleka
 */
public class NetworkData  {
    
    private List<NetworkEntity> networkAvailableList;
    private List<NetworkEntity> networkUnavailableList;
    
    public NetworkData() {
    }

    public List<NetworkEntity> getNetworkAvailableList() {
        return networkAvailableList;
    }

    public void setNetworkAvailableList(List<NetworkEntity> networkAvailableList) {
        this.networkAvailableList = networkAvailableList;
    }

    public List<NetworkEntity> getNetworkUnavailableList() {
        return networkUnavailableList;
    }

    public void setNetworkUnavailableList(List<NetworkEntity> networkUnavailableList) {
        this.networkUnavailableList = networkUnavailableList;
    }
    
    
    
}
