/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qfog.infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import qfog.utils.Couple;
import qfog.utils.Link;

/**
 *
 * @author stefano
 */
public class Infrastructure {
    public ArrayList<CloudDatacentre> C;
    public ArrayList<FogNode> F;
    public ArrayList<Thing> T;
    public HashMap<Couple, Link> L;
    
    public Infrastructure(){
        C = new ArrayList<>();
        F = new ArrayList<>();
        T = new ArrayList<>();
        L = new HashMap<>();
    }

    public void addCloudDatacentre(String identifier, List<String> software, double x, double y) {
        C.add(new CloudDatacentre(identifier, software, x, y));
    }

    public void addFogNode(String identifier, List<String> software, int hardware, double x, double y) {
        F.add(new FogNode(identifier, hardware, software, x, y));
    }
    
    public void addThing(String identifier, String type, double x, double y) {
        T.add(new Thing(identifier, type, x, y));
    }
    
    public void addLink(String a, String b, int latency, double bandwidth) {
        L.put(new Couple(a,b), new Link(a,b,latency,bandwidth));
        L.put(new Couple(b,a), new Link(b,a,latency,bandwidth));
    }

    public void addLink(String a, String b, int latency, double bandwidthba, double bandwidthab) {
        L.put(new Couple(a,b), new Link(a,b,latency,bandwidthab));
        L.put(new Couple(b,a), new Link(b,a,latency,bandwidthba));
    }
    
}
