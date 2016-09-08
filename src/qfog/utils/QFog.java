/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qfog.utils;

import qfog.deployment.Search;
import static java.util.Arrays.asList;
import java.util.HashMap;
import qfog.application.Application;
import qfog.application.Component;
import qfog.deployment.Deployment;
import qfog.infrastructure.Infrastructure;

/**
 *
 * @author stefano
 */
public class QFog {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Application A = new Application();
        //components
        A.addComponent("insights", asList("a","b","c"), 4);
        A.addComponent("mlengine", asList("a","b","c", "d"), 8);
        A.addComponent("irrigation", asList("b","c"), 2);
        A.addComponent("fireflood", asList("a","b","c"), 1);
        A.addComponent("irrigationGW", asList("a","b", "e"), 1);
        A.addComponent("firefloodGW", asList("a","b", "e"), 1);
        //links
        A.addLink("insights", "mlengine", 60 , 2);
        A.addLink("insights", "fireflood", 15 , 1);
        A.addLink("irrigation", "insights", 15 , 1);
        A.addLink("irrigation", "mlengine", 200, 3, 6);
        A.addLink("irrigation", "fireflood", 15, 1);
        A.addLink("irrigationGW", "irrigation", 15, 8, 1);
        A.addLink("firefloodGW", "fireflood", 5, 2);
        
        System.out.println(A);
        
        Infrastructure Phi = new Infrastructure();
        //fog1 43.7464449,10.4615923 fog2 43.7381285,10.4552213
        Phi.addCloudDatacentre("cloud_1", asList("a", "b", "c","d"), 52.195097,3.0364791 );
        Phi.addCloudDatacentre("cloud_2", asList("a", "b", "c"), 44.123896,-122.781555);
        Phi.addFogNode("consortium_1",asList("a", "b", "c"), 10, 43.740186, 10.364619);
        Phi.addFogNode("local_1", asList("a","b", "e"), 2, 43.7464449,10.4615923);
        Phi.addFogNode("local_2", asList("a","b","e"), 4, 43.7381285,10.4552213);
        
        Phi.addThing("water0", "water", 43.7464449, 10.4615923, "local_1");
        Phi.addThing("moisture0", "moisture", 43.7464449,10.4615923, "local_1");
        Phi.addThing("UV0", "UV", 43.7464449,10.4615923, "local_1");
        Phi.addThing("camera0", "video", 43.7464449,10.4615923, "local_1");
        Phi.addThing("salts0", "salts", 43.7464449,10.4615923, "local_1");
        
        Phi.addThing("wind0", "wind", 43.740186, 10.364619, "consortium_1");
        Phi.addThing("pressure0", "pressure", 43.740186, 10.364619, "consortium_1");
        Phi.addThing("temperature0", "temperature", 43.740186, 10.364619, "consortium_1");
        
        Phi.addThing("water1", "water", 43.7381285,10.4552213, "local_2");
        Phi.addThing("fertiliser0", "fertiliser", 43.7381285,10.4552213, "local_2");
        Phi.addThing("extinguisher0", "extinguisher", 43.7381285,10.4552213, "local_2");
        Phi.addThing("flood0", "flood", 43.7381285,10.4552213, "local_2");
        Phi.addThing("fire0", "fire", 43.7381285,10.4552213, "local_2");
        Phi.addThing("floodgates0", "floodgates", 43.7381285,10.4552213, "local_2");
        
        Phi.addLink("local_1", "local_2", 5, 20);
        Phi.addLink("local_1", "consortium_1", 5, 20);
        Phi.addLink("local_2", "consortium_1", 5, 20);
        Phi.addLink("local_1", "cloud_1", 130, 8, 6);
        Phi.addLink("local_1", "cloud_2", 200, 12, 10);
        Phi.addLink("local_2", "cloud_1", 100, 12, 8);
        Phi.addLink("local_2", "cloud_2", 180, 15, 11);
        Phi.addLink("consortium_1", "cloud_1", 35, 60, 18);
        Phi.addLink("consortium_1", "cloud_2", 45, 65, 18);
        
        Deployment d = new Deployment();
        
        Search search = new Search(A, Phi, d);

        HashMap<Component, Node> m = search.findDeployment();
        
        if(m != null){
            System.out.println(m);
        }
        else 
            System.out.println("failure");
        
        System.out.println(search.D);

    }
    
}
