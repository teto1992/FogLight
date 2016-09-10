/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qfog.application;

import qfog.utils.QoSProfile;

/**
 *
 * @author stefano
 */
public class ThingsRequirement {
    private String type;
    private int number;
    private QoSProfile q;

    public ThingsRequirement(String type, QoSProfile q, int number) {
        this.type = type;
        this.number = number;
        this.q = q;
    }
    
    public ThingsRequirement(String type, QoSProfile q) {
        this.type = type;
        this.number = 1;
        this.q = q;
    }
    
    public String getType(){
        return type;
    }
    
    public int getNumber(){
        return number;
    }
    
    public QoSProfile getQ(){
        return q;
    }
    
    public String toString(){
        return "(" + type + ", "+q+", "+number +")";
    }
}
