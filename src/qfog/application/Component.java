/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qfog.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import qfog.utils.Link;

/**
 *
 * @author stefano
 */
public class Component {
    String identifier;
    List<String> softwareReqs;
    int hardwareReqs;
    
    public Component(String identifier, List<String> softwareReqs, int hardwareReqs){
        this.identifier = identifier;
        this.softwareReqs = softwareReqs;
        this.hardwareReqs = hardwareReqs;
    }

    public List<String> getSoftwareRequirements() {
        return softwareReqs;
    }

    public int getHardwareRequirements() {
        return hardwareReqs;
    }

    public String getId() {
        return this.identifier;
    }
    
    @Override
    public String toString(){
        String result = "<";
        result = result + this.identifier + " - Sigma: " + this.softwareReqs + " H: "+ Integer.toString(this.hardwareReqs);        
        result += ">";
        return result; 
    }
    
}
