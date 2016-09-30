/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fogtorch.infrastructure;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import fogtorch.utils.Couple;
import fogtorch.utils.QoSProfile;

/**
 *
 * @author stefano
 */
public class Infrastructure {
    public HashMap<String,CloudDatacentre> C;
    public HashMap<String,FogNode> F;
    public HashMap<String,Thing> T;
    public HashMap<Couple, QoSProfile> L;
    
    public Infrastructure(){
        C = new HashMap<>();
        F = new HashMap<>();
        T = new HashMap<>();
        L = new HashMap<>();
    }

    public void addCloudDatacentre(String identifier, List<String> software, double x, double y) {
        C.put(identifier,new CloudDatacentre(identifier, software, x, y));
        L.put(new Couple(identifier,identifier), new QoSProfile(0, Double.MAX_VALUE));
    }

    public void addFogNode(String identifier, List<String> software, int hardware, double x, double y) {
        F.put(identifier,new FogNode(identifier, hardware, software, x, y));
        L.put(new Couple(identifier,identifier), new QoSProfile(0, Double.MAX_VALUE));
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
                    QoSProfile r = L.get(l);
                    L.put(new Couple(identifier, fogNode2), r);
                    r = L.get(new Couple(fogNode2, fogNode));
                    L.put(new Couple(fogNode2,identifier), r);
                    F.get(fogNode2).addReachableThing(identifier);
                }
            }
        } 
        addLink(identifier, fogNode, 0, Double.MAX_VALUE);
        addLink(fogNode, identifier, 0, Double.MAX_VALUE);
        f.addReachableThing(t.getId());
    }
    
    public void addLink(String a, String b, int latency, double bandwidth) {
        L.put(new Couple(a,b), new QoSProfile(latency,bandwidth));
        L.put(new Couple(b,a), new QoSProfile(latency,bandwidth));
    }

    public void addLink(String a, String b, int latency, double bandwidthba, double bandwidthab) {
        L.put(new Couple(a,b), new QoSProfile(latency,bandwidthab));
        L.put(new Couple(b,a), new QoSProfile(latency,bandwidthba));
    }

    public String toString(){
        String result = "C = {\n";
        
        for (ComputationalNode c: C.values()){
            result+="\t"+c;
            result+="\n";
        }
        
        result+="}\n\nF = {\n";
        
        
        for (ComputationalNode f : F.values()){
            result+="\t"+f;
            result+="\n";
        }
        
        result+="}\n\nT = {\n";
        
        
        for (Thing t : T.values()){
            result+="\t"+t;
            result+="\n";
        }
        
        result+="}\n\nL = {\n";
        
        
        for (Entry l : L.entrySet()){
            result+="\t"+l;
            result+="\n";
        }
        
        result+="}";
        
        return result;
    }
    
}
