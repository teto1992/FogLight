/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qfog.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author stefano
 */
public class Component {
    String identifier;
    ArrayList<String> softwareReqs;
    int hardwareReqs;
    
    public Component(String identifier, ArrayList<String> softwareReqs, int hardwareReqs){
        this.identifier = identifier;
        this.softwareReqs = softwareReqs;
    }

    public ArrayList<String> getSoftwareRequirements() {
        return softwareReqs;
    }

    public int getHardwareRequirements() {
        return hardwareReqs;
    }

    public String getId() {
        return this.identifier;
    }
    
}
