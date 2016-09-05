/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qfog.infrastructure;

import java.util.ArrayList;
import java.util.Collection;
import qfog.application.Component;
import qfog.utils.Node;

/**
 *
 * @author stefano
 */
public class FogNode extends Node{

    private int hardware;
    private ArrayList<Thing> reachableThings;
    
    public FogNode(String identifier, int hardware, Collection<String> software, double x, double y){
        super.setId(identifier);
        setHardware(hardware);
        super.setSoftware(software);
        super.setCoordinates(x,y);
        reachableThings = new ArrayList<>();
    }
    
    public boolean addThing(Thing t){
        return reachableThings.add(t);
    }
    
    public boolean removeThing(Thing t){
        return reachableThings.remove(t);
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
