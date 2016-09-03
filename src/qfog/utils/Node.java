/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qfog.utils;

import java.util.ArrayList;
import java.util.Collection;
import qfog.application.Component;

/**
 *
 * @author stefano
 */
public abstract class Node {
    private ArrayList<String> software;
    private String identifier;
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
    
    public abstract boolean isCompatible(Component component);

    public abstract void deploy(Component s);
     
    public abstract void undeploy(Component s);

    
}
