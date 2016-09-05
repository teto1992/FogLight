/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qfog.deployment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import qfog.application.Component;
import qfog.utils.Node;

/**
 *
 * @author stefano
 */
public class Deployment extends HashMap<Component, Node> {
    HashMap<String, HashSet<String>> businessPolicies;
    HashMap<String, ArrayList<ThingRequirement>> thingRequirements;
    
    public Deployment(){
        super();
        businessPolicies = new HashMap<String, HashSet<String>>();
        thingRequirements = new HashMap<String, ArrayList<ThingRequirement>>();     
    }
}
