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
public class CloudDatacentre extends Node {
    
    public CloudDatacentre(String identifier, Collection<String> software, double x, double y){
        super.setId(identifier);
        super.setSoftware(software);
        super.setCoordinates(x,y);
    }

    @Override
    public boolean isCompatible(Component component) {
        ArrayList<String> softwareRequirements = component.getSoftwareRequirements();
        return softwareRequirements.stream().noneMatch((s) 
                -> (!super.getSoftware().contains(s)));
    }
    
}
