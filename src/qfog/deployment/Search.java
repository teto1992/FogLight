/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qfog.deployment;

import java.util.ArrayList;
import java.util.HashMap;
import qfog.application.Application;
import qfog.application.Component;
import qfog.infrastructure.Infrastructure;
import qfog.utils.Couple;
import qfog.utils.Link;
import qfog.utils.Node;
import qfog.utils.QoSProfile;

public class Search {

    private Application A;
    private Infrastructure Phi;
    private HashMap<String, ArrayList<Node>> K;
    int steps;

    public Search(Application A, Infrastructure Phi, Deployment d) {
        this.A = A;
        this.Phi = Phi;
        K = new HashMap<>();
    }

    private void findCompatibleNodes() {
        for (Node n : Phi.C.values()) {
            A.S.stream().filter((s) -> (n.isCompatible(s))).map((s) -> {
                if (!K.containsKey(s.getId())) {
                    K.put(s.getId(), new ArrayList<>());
                }
                return s;
            }).forEach((s) -> {
                K.get(s.getId()).add(n);
            });
        }
        for (Node n : Phi.F.values()) {
            A.S.stream().filter((s) -> (n.isCompatible(s))).map((s) -> {
                if (!K.containsKey(s.getId())) {
                    K.put(s.getId(), new ArrayList<>());
                }
                return s;
            }).forEach((s) -> {
                K.get(s.getId()).add(n);
            });
        }
    }

    public Deployment findDeployment(){
        Deployment deployment = new Deployment();
        findCompatibleNodes();
        deployment = search(deployment);
        return deployment;
    }
    private  Deployment  search(Deployment deployment) {
        steps++;
        if (isComplete(deployment)){
            return deployment;
        }
        Component s = selectUndeployedComponent(deployment);
        for (Node n : K.get(s.getId())) { // for all nodes compatible with s
            if (isValid(deployment, s, n)) {
                System.out.println(steps+" Deploying "+ s + " onto node " + n);
                deploy(deployment, s, n);
                HashMap<Component, Node>  result = search(deployment);
                if (result != null) {
                    return deployment;
                }
            }
            if (deployment.containsKey(s)) {
                System.out.println(steps+" Undeploying "+ s + " from node " + n);
                undeploy(deployment, s, n);
            }
        }
        return null;
    }

    private boolean checkLinks(Deployment deployment, Component s, Node n) {
        
        for (Component c : deployment.keySet()) {
            Node m = deployment.get(c); // nodo deployment c
            Couple couple1 = new Couple(c.getId(), s.getId());
            Couple couple2 = new Couple(s.getId(), c.getId());
            //System.out.println(couple1 + "---" + couple2);
            if (A.L.containsKey(couple1)) {
                QoSProfile al1 = A.L.get(couple1).getQ();
                QoSProfile al2 = A.L.get(couple2).getQ();
                Couple c1 = new Couple(n.getId(), m.getId());
                Couple c2 = new Couple(m.getId(), n.getId());
                if (Phi.L.containsKey(c1)) {
                    QoSProfile pq1 = Phi.L.get(c1).getQ();
                    QoSProfile pq2 = Phi.L.get(c2).getQ();
                    if (!pq1.supports(al1) || !pq2.supports(al2)) {
                        return false;
                    }
                } else {
                    return false;
                } 
            }
        }
        return true;
    }

    private void deployLinks(Deployment deployment, Component s, Node n) {
        for (Component c : deployment.keySet()) {
            Node m = deployment.get(c);
            Couple couple1 = new Couple(c.getId(), s.getId());
            Couple couple2 = new Couple(s.getId(), c.getId());

            if (A.L.containsKey(couple1) && A.L.containsKey(couple2)) {
                Link al1 = A.L.get(couple1); //c,s
                Link al2 = A.L.get(couple2); //s,c
                Couple c1 = new Couple(m.getId(), n.getId()); // m,n
                Couple c2 = new Couple(n.getId(), m.getId()); // n,m
                if (Phi.L.containsKey(c1)) {
                    Link pl1 = Phi.L.get(c1);
                    Link pl2 = Phi.L.get(c2);

                    pl1.getQ().setBandwidth(pl1.getQ().getBandwidth() - al1.getQ().getBandwidth());
                    pl2.getQ().setBandwidth(pl2.getQ().getBandwidth() - al2.getQ().getBandwidth());
                }
            }
        }
    }

    private void undeployLinks(Deployment deployment, Component s, Node n) {
        for (Component c : deployment.keySet()) {
            Node m = deployment.get(c);
            Couple couple1 = new Couple(c.getId(), s.getId());
            Couple couple2 = new Couple(s.getId(), c.getId());

            if (A.L.containsKey(couple1) && A.L.containsKey(couple2)) {
                Link al1 = A.L.get(couple1);
                Link al2 = A.L.get(couple2);
                Couple c1 = new Couple(n.getId(), m.getId());
                Couple c2 = new Couple(m.getId(), n.getId());
                if (Phi.L.containsKey(c1)) {
                    Link pl1 = Phi.L.get(c1);
                    Link pl2 = Phi.L.get(c2);

                    pl1.getQ().setBandwidth(pl1.getQ().getBandwidth() + al1.getQ().getBandwidth());
                    pl2.getQ().setBandwidth(pl2.getQ().getBandwidth() + al2.getQ().getBandwidth());
                }
            }

        }
    }

    private boolean isValid(Deployment deployment, Component s, Node n) {
        return n.isCompatible(s) && checkLinks(deployment, s, n);
    }

    private void deploy(Deployment deployment, Component s, Node n) {
                deployment.put(s, n);
                n.deploy(s);
                deployLinks(deployment, s, n);
    }
    
    private void undeploy(Deployment deployment, Component s, Node n) {
                deployment.remove(s);
                n.undeploy(s);
                undeployLinks(deployment, s, n);
    }

    private Component selectUndeployedComponent(Deployment deployment) {
        return A.S.get(deployment.size());    
    }

    private boolean isComplete(Deployment deployment) {
        return deployment.size() == A.S.size();
    }


}
