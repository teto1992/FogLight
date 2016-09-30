/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fogtorch.infrastructure;

import java.util.Collection;
import java.util.HashSet;
import fogtorch.application.SoftwareComponent;

/**
 *
 * @author stefano
 */
public class FogNode extends ComputationalNode{
    private int hardware;
    private HashSet<String> reachableThings;
    
    public FogNode(String identifier, int hardware, Collection<String> software, double x, double y){
        super.setId(identifier);
        setHardware(hardware);
        super.setSoftware(software);
        super.setCoordinates(x,y);
        reachableThings = new HashSet<>();
    }

    
    public void addReachableThing(String t) {
        reachableThings.add(t);
    }
    
    public boolean isReachable(String t) {
        return this.reachableThings.contains(t);
    }

    public void setHardware(int hardware){
       this.hardware = hardware;
    }
    
    public int getHardware(){
        return this.hardware;
    }
    
    @Override
    public boolean isCompatible(SoftwareComponent component){
        int hardwareRequest = component.getHardwareRequirements();
        Collection<String> softwareRequest = component.getSoftwareRequirements();
        
        return hardwareRequest <= this.hardware && 
                softwareRequest.stream().noneMatch(
                        (s) -> (!super.getSoftware().contains(s))
                );
    }
   

    @Override
    public void deploy(SoftwareComponent s) {
        this.setHardware(this.getHardware()-s.getHardwareRequirements());
    }

    @Override
    public void undeploy(SoftwareComponent s) {
        this.setHardware(this.getHardware()+s.getHardwareRequirements());
    }
    
        @Override
    public String toString(){
        String result = "<";
        result = result + getId() + ", " + super.getSoftware() + ", "+ Integer.toString(this.hardware)+", "+this.getCoordinates();        
        result += ">";
        return result; 
    }

    public Iterable<String> getReachableThings() {
        return this.reachableThings;
    }

    public double distance(Thing t) {
        return t.getCoordinates().distance(super.getCoordinates());
    }


    
}
