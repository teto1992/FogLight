/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qfog.infrastructure;

import java.util.ArrayList;
import java.util.Collection;
import qfog.application.Component;
import qfog.utils.Coordinates;
import qfog.utils.Node;

/**
 *
 * @author stefano
 */
public class FogNode extends Node{

    private int hardware;

    public FogNode(String identifier, int hardware, Collection<String> software, double x, double y){
        super.setId(identifier);
        setHardware(hardware);
        super.setSoftware(software);
        super.setCoordinates(x,y);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void undeploy(Component s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
