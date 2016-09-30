/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fogtorch.deployment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import fogtorch.application.AnyThing;
import fogtorch.application.Application;
import fogtorch.application.SoftwareComponent;
import fogtorch.application.ExactThing;
import fogtorch.application.ThingRequirement;
import fogtorch.infrastructure.CloudDatacentre;
import fogtorch.infrastructure.FogNode;
import fogtorch.infrastructure.Infrastructure;
import fogtorch.infrastructure.Thing;
import fogtorch.utils.Couple;
import fogtorch.infrastructure.ComputationalNode;
import fogtorch.utils.QoSProfile;
import java.util.List;

public class Search {

    private Application A;
    private Infrastructure I;
    private HashMap<String, ArrayList<ComputationalNode>> K;
    private HashMap<String, HashSet<String>> businessPolicies;
    int steps;
    public ArrayList<Deployment> D;

    public Search(Application A, Infrastructure I) {
        this.A = A;
        this.I = I;
        K = new HashMap<>();
        D = new ArrayList<>();
        businessPolicies = new HashMap<String, HashSet<String>>();
    }
    
    private boolean findCompatibleNodes() {
        for (SoftwareComponent s : A.S) {
            
            for (CloudDatacentre n : I.C.values()) {
                if (s.Theta.isEmpty() && n.isCompatible(s) && 
                        ((businessPolicies.containsKey(s.getId()) && businessPolicies.get(s.getId()).contains(n.getId())) 
                        || !businessPolicies.containsKey(s.getId()))) {
                    if (!K.containsKey(s.getId())) {
                        K.put(s.getId(), new ArrayList<>());
                    }
                    K.get(s.getId()).add(n);
                }
            }
        }

        for (SoftwareComponent s : A.S) {
            for (FogNode n : I.F.values()) {
                if (n.isCompatible(s) && checkThings(s, n) && 
                        ((businessPolicies.containsKey(s.getId()) && businessPolicies.get(s.getId()).contains(n.getId())) 
                        || !businessPolicies.containsKey(s.getId()))) {
                    if (!K.containsKey(s.getId())) {
                        K.put(s.getId(), new ArrayList<>());
                    }
                    K.get(s.getId()).add(n);
                }
            }
        }
        return true;
    }

    public Deployment findDeployments() {
        Deployment deployment = new Deployment();
        findCompatibleNodes();
        System.out.println(K);
        exhaustiveSearch(deployment);
        return deployment;
    }

    private Deployment search(Deployment deployment) {
        if (isComplete(deployment)) {
            System.out.println(deployment);
            return deployment;
        }
        SoftwareComponent s = selectUndeployedComponent(deployment);
        for (ComputationalNode n : K.get(s.getId())) { // for all nodes compatible with s
            System.out.println(steps + " Checking " + s.getId() + " onto node " + n.getId());
            if (isValid(deployment, s, n)) {
                System.out.println(steps + " Deploying " + s.getId() + " onto node " + n.getId());
                deploy(deployment, s, n);
                HashMap<SoftwareComponent, ComputationalNode> result = search(deployment);
                if (result != null) {
                    return deployment;
                }
                undeploy(deployment, s, n);
            }
            System.out.println(steps + " Undeploying " + s.getId() + " from node " + n.getId());
        }
        return null;
    }

    private void exhaustiveSearch(Deployment deployment) {
        if (isComplete(deployment)) {
            D.add((Deployment) deployment.clone());
            System.out.println(deployment);
            return;
        }
        SoftwareComponent s = selectUndeployedComponent(deployment);
        if (K.get(s.getId()) != null) {
            for (ComputationalNode n : K.get(s.getId())) { // for all nodes compatible with s 
                steps++;
                System.out.println(steps + " Checking " + s.getId() + " onto " + n.getId());
                if (isValid(deployment, s, n)) {
                    System.out.println(steps + " Deploying " + s.getId() + " onto " + n.getId());
                    deploy(deployment, s, n);
                    exhaustiveSearch(deployment);
                    undeploy(deployment, s, n);
                }
                System.out.println(steps + " Undeploying " + s.getId() + " from " + n.getId());
            }
        }
    }

    private boolean checkLinks(Deployment deployment, SoftwareComponent s, ComputationalNode n) {
        for (SoftwareComponent c : deployment.keySet()) {
            ComputationalNode m = deployment.get(c); // nodo deployment c
            Couple couple1 = new Couple(c.getId(), s.getId());
            Couple couple2 = new Couple(s.getId(), c.getId());
            if (A.L.containsKey(couple1)) {
                QoSProfile al1 = A.L.get(couple1);
                QoSProfile al2 = A.L.get(couple2);
                Couple c1 = new Couple(n.getId(), m.getId());
                Couple c2 = new Couple(m.getId(), n.getId());
                System.out.println("Finding a link for "+couple1 + " between " + c1);
                if (I.L.containsKey(c1)) {
                    QoSProfile pq1 = I.L.get(c1);
                    QoSProfile pq2 = I.L.get(c2);
                    if (!pq1.supports(al1) || !pq2.supports(al2)) {
                        System.out.println("It does not support QoS...");
                        return false;
                    }
                } else {
                    System.out.println("It does not exist");
                    return false;
                }
            }
        }
        return true;
    }

    private void deployLinks(Deployment deployment, SoftwareComponent s, ComputationalNode n) {
        for (SoftwareComponent c : deployment.keySet()) {
            ComputationalNode m = deployment.get(c);
            Couple couple1 = new Couple(c.getId(), s.getId());
            Couple couple2 = new Couple(s.getId(), c.getId());

            if (A.L.containsKey(couple1) && A.L.containsKey(couple2)) {
                QoSProfile al1 = A.L.get(couple1); //c,s
                QoSProfile al2 = A.L.get(couple2); //s,c
                Couple c1 = new Couple(m.getId(), n.getId()); // m,n
                Couple c2 = new Couple(n.getId(), m.getId()); // n,m
                if (I.L.containsKey(c1)) {
                    QoSProfile pl1 = I.L.get(c1);
                    QoSProfile pl2 = I.L.get(c2);
                    pl1.setBandwidth(pl1.getBandwidth() - al1.getBandwidth());
                    pl2.setBandwidth(pl2.getBandwidth() - al2.getBandwidth());
                }
            }
        }
    }

    private void undeployLinks(Deployment deployment, SoftwareComponent s, ComputationalNode n) {
        for (SoftwareComponent c : deployment.keySet()) {
            ComputationalNode m = deployment.get(c);
            Couple couple1 = new Couple(c.getId(), s.getId());
            Couple couple2 = new Couple(s.getId(), c.getId());

            if (A.L.containsKey(couple1) && A.L.containsKey(couple2)) {
                QoSProfile al1 = A.L.get(couple1);
                QoSProfile al2 = A.L.get(couple2);
                Couple c1 = new Couple(m.getId(), n.getId());
                Couple c2 = new Couple(n.getId(), m.getId());
                if (I.L.containsKey(c1)) {
                    QoSProfile pl1 = I.L.get(c1);
                    QoSProfile pl2 = I.L.get(c2);

                    pl1.setBandwidth(pl1.getBandwidth() + al1.getBandwidth());
                    pl2.setBandwidth(pl2.getBandwidth() + al2.getBandwidth());
                }
            }

        }
    }

    private boolean isValid(Deployment deployment, SoftwareComponent s, ComputationalNode n) {
        return n.isCompatible(s) && checkLinks(deployment, s, n);
    }

    private void deploy(Deployment deployment, SoftwareComponent s, ComputationalNode n) {
        deployment.put(s, n);
        n.deploy(s);
        deployLinks(deployment, s, n);
    }

    private void undeploy(Deployment deployment, SoftwareComponent s, ComputationalNode n) {
        if (deployment.containsKey(s)) {
            deployment.remove(s);
            n.undeploy(s);
            undeployLinks(deployment, s, n);
        }
    }

    private SoftwareComponent selectUndeployedComponent(Deployment deployment) {
        return A.S.get(deployment.size());
    }

    private boolean isComplete(Deployment deployment) {
        return deployment.size() == A.S.size();
    }

    private boolean checkThings(SoftwareComponent s, FogNode n) {
        System.out.println("Checking things for "+ s.getId() + " -- " + n);
        for (ThingRequirement r : s.getThingsRequirements()) {
            System.out.println(s.getId() + " " + r.toString());
            if (r.getClass() == ExactThing.class) {
                ExactThing e = (ExactThing) r;
                if (!(n.isReachable(e.getId()))) {
                    return false;
                } else {
                    QoSProfile get = I.L.get(new Couple(n.getId(), e.getId()));
                    if (get.getLatency() > e.getQ().getLatency()) {
                        System.out.println(get);
                        return false;
                    }
                }
            } else {
                AnyThing a = (AnyThing) r;
                boolean found = false;
                for (String tmp : n.getReachableThings()) {
                    Thing t = I.T.get(tmp);
                    QoSProfile get = I.L.get(new Couple(tmp, n.getId()));
                    if (n.distance(t) <= a.getDistance()
                            && a.getType().equals(t.getType())
                            && get.getLatency() <= a.getQ().getLatency()) {
                        found = true;
                        break;
                    }
                }
                if (found == false) {
                    return false;
                }

            }
        }

        return true;

    }

    public void addBusinessPolicies(String component, List<String> allowedNodes) {
        HashSet<String> policies = new HashSet<>();
        policies.addAll(allowedNodes);
        this.businessPolicies.put(component, policies);
    }

}
