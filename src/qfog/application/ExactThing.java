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
public class ExactThing extends ThingsRequirement{
    private String id;

    public ExactThing(String type, QoSProfile q) {
        this.id = type;
        super.setQ(q);
    }
    
    public String getId(){
        return id;
    }

    public String toString(){
        return "(" + id + ", "+super.getQ()+")";
    }
}
