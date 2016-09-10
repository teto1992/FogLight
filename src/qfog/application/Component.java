/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qfog.application;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author stefano
 */
public class Component {
    String identifier;
    List<String> softwareReqs;
    int hardwareReqs;
    public ArrayList<ThingsRequirement> Theta;

    public Component(String identifier, List<String> softwareReqs, int hardwareReqs, ArrayList<ThingsRequirement> Theta){
        this.identifier = identifier;
        this.softwareReqs = softwareReqs;
        this.hardwareReqs = hardwareReqs;
        this.Theta = new ArrayList<>(Theta);
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
        result = result + this.identifier + ", " + this.softwareReqs + ", "+ Integer.toString(this.hardwareReqs) + ", "+  Theta;        
        result += ">";
        return result; 
    }

    public Iterable<ThingsRequirement> getThingsRequirements() {
        return this.Theta;
    }
    
}
