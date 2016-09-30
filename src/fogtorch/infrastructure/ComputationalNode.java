/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fogtorch.infrastructure;

import java.util.ArrayList;
import java.util.Collection;
import fogtorch.application.SoftwareComponent;
import fogtorch.utils.Coordinates;

/**
 *
 * @author stefano
 */
public abstract class ComputationalNode {
    private String identifier;
    private ArrayList<String> software;
    private Coordinates coords;
    
    public void setSoftware(Collection<String> software){
        this.software = new ArrayList<>(software);
    }
    
    public ArrayList<String> getSoftware(){
        return this.software;
    }
    
    public String getId(){
        return this.identifier;
    }
    
    public void setId(String identifier){
        this.identifier = identifier;
    }
    
    public void setCoordinates(double x, double y){
        this.coords = new Coordinates(x,y);
    }
    
    public Coordinates getCoordinates(){
        return this.coords;
    }
    
    public double distance(ComputationalNode n){
        return getCoordinates().distance(n.getCoordinates());
    }
    
    public abstract boolean isCompatible(SoftwareComponent s);

    public abstract void deploy(SoftwareComponent s);
     
    public abstract void undeploy(SoftwareComponent s);

        
}
