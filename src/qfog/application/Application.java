/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qfog.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import qfog.utils.Couple;
import qfog.utils.Link;
import qfog.utils.Node;

/**
 *
 * @author stefano
 */
public class Application {
    public ArrayList<Component> S;
    public HashMap<Couple, Link> L;
    
    public Application(){
        S = new ArrayList<>();
        L = new HashMap<>();
    }

    public void addLink(String a, String b, int latency, double bandwidth) {
        L.put(new Couple(a,b), new Link(a,b,latency,bandwidth));
        L.put(new Couple(b,a), new Link(b,a,latency,bandwidth));
    }

    public void addLink(String a, String b, int latency, double bandwidthba, double bandwidthab) {
        L.put(new Couple(a,b), new Link(a,b,latency,bandwidthab));
        L.put(new Couple(b,a), new Link(b,a,latency,bandwidthba));
    }

    public void addComponent(String id, List<String> softwareReqs, int hardwareReqs) {
        S.add(new Component(id, softwareReqs, hardwareReqs));   
    }
    
    @Override
    public String toString(){
        String result = "S = {\n";
        
        for (Component s: S){
            result+="\t"+s;
            result+="\n";
        }
        
        result+="}\n\nLambda = {\n";
        
        
        for (Link l : L.values()){
            result+="\t"+l;
            result+="\n";
        }
        
        result+="}";
        
        return result;
    }
}
