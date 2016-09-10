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
public class AnyThing extends ThingsRequirement{
    private Coordinates coord;
    private double maxDist;
    private String type;
    
    public AnyThing(String type, double x, double y, double maxDist){
        this.type = type;
        coord = new Coordinates(x,y);
        this.maxDist = maxDist;
    }
    
    public Coordinates getCoordinates(){
        return this.coord;
    }
    
    public double getDistance(){
        return maxDist;
    }
    
    public String getType(){
        return this.type;
    }
}