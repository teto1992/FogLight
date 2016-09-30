/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fogtorch.deployment;

import java.util.HashMap;
import java.util.HashSet;
import fogtorch.application.SoftwareComponent;
import fogtorch.infrastructure.ComputationalNode;

/**
 *
 * @author stefano
 */
public class Deployment extends HashMap<SoftwareComponent, ComputationalNode> {
    HashMap<String, HashSet<String>> businessPolicies;
    
    public Deployment(){
        super();
        businessPolicies = new HashMap<String, HashSet<String>>();   
    }

    Deployment(Deployment deployment) {
        super(deployment);
    }
    
    @Override
    public String toString(){
        String result ="Delta:\n";
        for (SoftwareComponent s : super.keySet()){
            result+="\t["+s.getId()+", " +super.get(s).getId()+"]\n";
        }
        return result;   
    }

}
