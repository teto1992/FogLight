/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fogtorch.utils;

import java.util.ArrayList;
import fogtorch.deployment.Search;
import static java.util.Arrays.asList;
import fogtorch.application.Application;
import fogtorch.application.ExactThing;
import fogtorch.application.ThingRequirement;
import fogtorch.infrastructure.Infrastructure;

/**
 *
 * @author stefano
 */
public class FogTorchExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Application A = new Application();
        ArrayList<ThingRequirement> irrigationThings = new ArrayList<>();
        ArrayList<ThingRequirement> firefloodThings = new ArrayList<>();
        QoSProfile q = new QoSProfile(30, 1.0);
        QoSProfile q2 = new QoSProfile(0, 1.0);

        irrigationThings.add(new ExactThing("videocamera0", q));
        irrigationThings.add(new ExactThing("water0", q));
        irrigationThings.add(new ExactThing("water1", q));
        irrigationThings.add(new ExactThing("fertiliser0", q));
        irrigationThings.add(new ExactThing("temperature0", q));
        irrigationThings.add(new ExactThing("UV0", q));
        irrigationThings.add(new ExactThing("salts0", q));
        irrigationThings.add(new ExactThing("moisture0", q));

        firefloodThings.add(new ExactThing("fire0", q2));
        firefloodThings.add(new ExactThing("flood0", q2));
        firefloodThings.add(new ExactThing("extinguisher0", q2));
        firefloodThings.add(new ExactThing("floodgates0", q2));

        //components
        A.addComponent("insights", asList(".NETcore", "mySQL"), 4);
        A.addComponent("mlengine", asList("spark", "mySQL"), 8);
        A.addComponent("irrigation", asList("python", "c++", "mySQL"), 2);
        A.addComponent("fireflood", asList("python", "c++"), 1);
        A.addComponent("irrigationDC", asList("c++", "linux"), 1, irrigationThings);
        A.addComponent("firefloodDC", asList("c++", "linux"), 1, firefloodThings);
        //links
        A.addLink("insights", "mlengine", 60, 2);
        A.addLink("insights", "fireflood", 15, 1);
        A.addLink("irrigation", "insights", 15, 1);
        A.addLink("irrigation", "mlengine", 200, 3, 6);
        A.addLink("irrigation", "fireflood", 15, 1);
        A.addLink("irrigationDC", "irrigation", 15, 8, 1);
        A.addLink("firefloodDC", "fireflood", 5, 2);
        
        Infrastructure I = new Infrastructure();
        //fog1 43.7464449,10.4615923 fog2 43.7381285,10.4552213
        I.addCloudDatacentre("cloud_1", asList("java", ".NETcore", "ruby", "mySQL"), 52.195097, 3.0364791);
        I.addCloudDatacentre("cloud_2", asList("spark", "mySQL", "linux", "windows", "python", "c++"), 44.123896, -122.781555);
        //Fog nodes
        I.addFogNode("consortium_1", asList("python", "c++", "mySQL", ".NETcore"), 10, 43.740186, 10.364619);
        I.addFogNode("local_1", asList("c++", "linux", "python"), 4, 43.7464449, 10.4615923);
        I.addFogNode("local_2", asList("c++", "linux", "python"), 4, 43.7381285, 10.4552213);
        //Links
        I.addLink("local_1", "local_2", 1, 100);
        I.addLink("local_1", "consortium_1", 5, 20);
        I.addLink("local_2", "consortium_1", 5, 20);
        I.addLink("local_1", "cloud_1", 130, 8, 6);
        I.addLink("local_1", "cloud_2", 200, 12, 10);
        I.addLink("local_2", "cloud_1", 100, 12, 8);
        I.addLink("local_2", "cloud_2", 180, 15, 11);
        I.addLink("consortium_1", "cloud_1", 35, 60, 18);
        I.addLink("consortium_1", "cloud_2", 45, 65, 18);
        //Things for local
        I.addThing("water0", "water", 43.7464449, 10.4615923, "local_1");
        I.addThing("moisture0", "moisture", 43.7464449, 10.4615923, "local_1");
        I.addThing("UV0", "UV", 43.7464449, 10.4615923, "local_1");
        I.addThing("videocamera0", "videocamera", 43.7464449, 10.4615923, "local_1");
        I.addThing("salts0", "salts", 43.7464449, 10.4615923, "local_1");

        I.addThing("wind0", "wind", 43.740186, 10.364619, "consortium_1");
        I.addThing("pressure0", "pressure", 43.740186, 10.364619, "consortium_1");
        I.addThing("temperature0", "temperature", 43.740186, 10.364619, "consortium_1");

        I.addThing("water1", "water", 43.7381285, 10.4552213, "local_2");
        I.addThing("fertiliser0", "fertiliser", 43.7381285, 10.4552213, "local_2");
        I.addThing("extinguisher0", "extinguisher", 43.7381285, 10.4552213, "local_2");
        I.addThing("flood0", "flood", 43.7381285, 10.4552213, "local_2");
        I.addThing("fire0", "fire", 43.7381285, 10.4552213, "local_2");
        I.addThing("floodgates0", "floodgates", 43.7381285, 10.4552213, "local_2");

        Search search = new Search(A, I);
        search.addBusinessPolicies("mlengine", asList("cloud_2"));
        search.findDeployments();

        System.out.println(search.D);
        System.out.println(search.D.size());

    }

}
