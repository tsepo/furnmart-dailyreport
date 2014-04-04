/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.argility.furnmart.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tmaleka
 */
public class LinuxCommands {
    
    private static ProcessBuilder builder;
    private static List<String> commands;
    
    public static void unlockAndReplicate(String branchCode) throws IOException {
        
        String command = "ssh root@c9901 unlockAndReplicate " + branchCode;
        builder = new ProcessBuilder(command);
        builder.start();
        
    }
    
}
