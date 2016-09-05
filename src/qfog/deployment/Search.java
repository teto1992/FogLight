/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qfog.deployment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import qfog.application.Application;
import qfog.application.Component;
import qfog.infrastructure.Infrastructure;
import qfog.utils.Couple;
import qfog.utils.Link;
import qfog.utils.Node;

public class Search {

    Application A;
    Infrastructure Phi;
    HashMap<String, ArrayList<Node>> K;
    HashMap<Component, Node> deployment;

    public Search(Application A, Infrastructure Phi) {
        this.A = A;
        this.Phi = Phi;
        K = new HashMap<>();
        deployment = new HashMap<>();
    }

    public void findCompatibleNodes() {

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

    public boolean search() {
        Component s = A.S.get(deployment.size());
        
        System.out.println(s.getId() + " " + deployment.size() +  " " +deployment);

        if (deployment.size() == A.S.size()) {
            return true;
        }

        for (Node n : K.get(s.getId())) { // for all nodes compatible with s
            if (n.isCompatible(s) && checkLinks(s, n)) {
                deployment.put(s, n);
                n.deploy(s);
                deployLinks(s, n);
                if (!search()){
                    n.undeploy(s);
                    undeployLinks(s, n);
                    deployment.remove(s);
                }
            }
            
        }

        return false;
    }

    private boolean checkLinks(Component s, Node n) {
        if (deployment.isEmpty()) {
            return true;
        }
        for (Component c : deployment.keySet()) {
            Node m = deployment.get(c); // nodo deployment c
            Couple couple1 = new Couple(c.getId(), s.getId());
            Couple couple2 = new Couple(s.getId(), c.getId());
            //System.out.println(couple1 + "---" + couple2);
            if (A.L.containsKey(couple1) && A.L.containsKey(couple2)) {
                Link al1 = A.L.get(couple1);
                Link al2 = A.L.get(couple2);
                Couple c1 = new Couple(n.getId(), m.getId());
                Couple c2 = new Couple(m.getId(), n.getId());
                if (Phi.L.containsKey(c1)) {
                    Link pl1 = Phi.L.get(c1);
                    Link pl2 = Phi.L.get(c2);
                    //System.out.println(al1 + " " + pl1);
                    if (al1.getQ().getLatency() >= pl1.getQ().getLatency()) {
                        if (al1.getQ().getBandwidth() <= pl1.getQ().getBandwidth()) {
                            if (al2.getQ().getBandwidth() <= pl2.getQ().getBandwidth()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private void deployLinks(Component s, Node n) {
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

                    pl1.getQ().setBandwidth(pl1.getQ().getBandwidth() - al1.getQ().getBandwidth());
                    pl2.getQ().setBandwidth(pl2.getQ().getBandwidth() - al2.getQ().getBandwidth());
                }
            }

        }
    }

    private void undeployLinks(Component s, Node n) {
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

}
