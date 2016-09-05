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

    Application A;
    Infrastructure Phi;
    HashMap<String, ArrayList<Node>> K;
    public HashMap<Component, Node> deployment;

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
        //if assignement is complete
        if (deployment.size() == A.S.size()) {
            return true;
        }
        //select unassigned component s
        Component s = A.S.get(deployment.size());

        //for each node in the domain of s
        for (Node n : K.get(s.getId())) { // for all nodes compatible with s
            // if node is consistent with assignment
            if (n.isCompatible(s) && checkLinks(s, n)) {
                // add {s = n} to deployment
                deployment.put(s, n);
                //inferences
                n.deploy(s);
                deployLinks(s, n);
                //recursive call
                boolean result = search();
                if (result) {
                    return true;
                }
            }
            if (deployment.containsKey(s)) {
                deployment.remove(s);
                n.undeploy(s);
                undeployLinks(s, n);
            }
        }
        return false;
    }

    private boolean checkLinks(Component s, Node n) {
        
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
