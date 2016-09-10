/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qfog.infrastructure;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        Thing t = new Thing(identifier, type, x, y);
        T.put(identifier, t);
        FogNode f = F.get(fogNode);
        Set<Couple> s = new HashSet(L.keySet());
        for (Couple l: s){
            if (l.getA().equals(fogNode)){
                String fogNode2 = l.getB();
                if(F.containsKey(fogNode2) && !fogNode2.equals(fogNode)){    
                    Link r = L.get(l);
                    int lat = (int) r.getQ().getLatency();
                    double bw = r.getQ().getBandwidth();
                    L.put(new Couple(identifier, fogNode2), new Link(identifier, fogNode2, lat, bw ));
                    r = L.get(new Couple(fogNode2, fogNode));
                    lat = r.getQ().getLatency();
                    bw = r.getQ().getBandwidth();
                    L.put(new Couple(fogNode2,identifier), new Link(fogNode2, identifier, lat, bw ));
                    
                }
            }
        } 
        addLink(identifier, fogNode, 0, Double.MAX_VALUE);
        addLink(fogNode, identifier, 0, Double.MAX_VALUE);
        f.addReachableThing(t.getId());
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
