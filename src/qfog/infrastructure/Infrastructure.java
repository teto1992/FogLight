/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qfog.infrastructure;

import java.util.HashMap;
import java.util.List;
import qfog.utils.Couple;
import qfog.utils.Link;
import qfog.utils.Node;

/**
 *
 * @author stefano
 */
public class Infrastructure {
    public HashMap<String,CloudDatacentre> C;
    public HashMap<String,FogNode> F;
    public HashMap<String,Thing> T;
    public HashMap<Couple, Link> L;
    
    public Infrastructure(){
        C = new HashMap<>();
        F = new HashMap<>();
        T = new HashMap<>();
        L = new HashMap<>();
    }

    public void addCloudDatacentre(String identifier, List<String> software, double x, double y) {
        C.put(identifier,new CloudDatacentre(identifier, software, x, y));
        L.put(new Couple(identifier,identifier), new Link(identifier, identifier, 0, Double.MAX_VALUE));
    }

    public void addFogNode(String identifier, List<String> software, int hardware, double x, double y) {
        F.put(identifier,new FogNode(identifier, hardware, software, x, y));
        L.put(new Couple(identifier,identifier), new Link(identifier, identifier, 0, Double.MAX_VALUE));
    }
    
    public void addThing(String identifier, String type, double x, double y, String fogNode) {
        T.put(identifier, new Thing(identifier, type, x, y));
        L.put(new Couple(identifier, fogNode), new Link(identifier, fogNode, 0, Double.MAX_VALUE));
        L.put(new Couple(fogNode, identifier), new Link(fogNode, identifier, 0, Double.MAX_VALUE));
        F.get(fogNode);
    }
    
    public void addLink(String a, String b, int latency, double bandwidth) {
        L.put(new Couple(a,b), new Link(a,b,latency,bandwidth));
        L.put(new Couple(b,a), new Link(b,a,latency,bandwidth));
    }

    public void addLink(String a, String b, int latency, double bandwidthba, double bandwidthab) {
        L.put(new Couple(a,b), new Link(a,b,latency,bandwidthab));
        L.put(new Couple(b,a), new Link(b,a,latency,bandwidthba));
    }

    public String toString(){
        String result = "C = {\n";
        
        for (Node c: C.values()){
            result+="\t"+c;
            result+="\n";
        }
        
        result+="}\n\nF = {\n";
        
        
        for (Node f : F.values()){
            result+="\t"+f;
            result+="\n";
        }
        
        result+="}\n\nT = {\n";
        
        
        for (Thing t : T.values()){
            result+="\t"+t;
            result+="\n";
        }
        
        result+="}\n\nL = {\n";
        
        
        for (Link l : L.values()){
            result+="\t"+l;
            result+="\n";
        }
        
        result+="}";
        
        return result;
    }
    
}
