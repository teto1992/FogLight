/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qfog.infrastructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import qfog.application.Component;
import qfog.utils.Node;
import qfog.utils.QoSProfile;

/**
 *
 * @author stefano
 */
public class FogNode extends Node{
    private int hardware;
    private HashMap<Thing, QoSProfile> reachableThings;
    private HashSet<Thing> connectedThings;
    
    public FogNode(String identifier, int hardware, Collection<String> software, double x, double y){
        super.setId(identifier);
        setHardware(hardware);
        super.setSoftware(software);
        super.setCoordinates(x,y);
        reachableThings = new HashMap<>();
        connectedThings = new HashSet<>();
    }
    
    public boolean addThing(Thing t){
        return connectedThings.add(t);
    }
    
    public void addReachableThing(Thing t, QoSProfile q) {
        reachableThings.put(t, q);
    }
    
    public boolean removeThing(Thing t){
        return connectedThings.remove(t);
    }

    public void setHardware(int hardware){
       this.hardware = hardware;
    }
    
    public int getHardware(){
        return this.hardware;
    }
    
    @Override
    public boolean isCompatible(Component component){
        int hardwareRequest = component.getHardwareRequirements();
        Collection<String> softwareRequest = component.getSoftwareRequirements();
        
        return hardwareRequest < this.hardware && 
                softwareRequest.stream().noneMatch(
                        (s) -> (!super.getSoftware().contains(s))
                );
    }
   

    @Override
    public void deploy(Component s) {
        this.setHardware(this.getHardware()-s.getHardwareRequirements());
    }

    @Override
    public void undeploy(Component s) {
        this.setHardware(this.getHardware()+s.getHardwareRequirements());
    }
    
        @Override
    public String toString(){
        String result = "<";
        result = result + getId() + ", " + super.getSoftware() + ", "+ Integer.toString(this.hardware) +", "+this.getCoordinates();        
        result += ">";
        return result; 
    }


    
}
