/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qfog.application;

import qfog.utils.Coordinates;

/**
 *
 * @author stefano
 */
public class ThingRequest {
    private String type;
    private Coordinates coords;
    private double maxDistance;
    
    public ThingRequest(String type, Coordinates coords, double maxDistance){
        this.coords = coords;
        this.type = type;
        this.maxDistance = maxDistance;
    }
    
    public String getType(){
        return this.type;
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    public void setMaxDistance(double d){
        this.maxDistance = d;
    }

    public void setCoordinates(double x, double y){
        this.coords = new Coordinates(x,y);
    }
    
    public Coordinates getCoordinates(){
        return this.coords;
    }

    public double getMaxDistance() {
        return this.maxDistance;
    }

}
