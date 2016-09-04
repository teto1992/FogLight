/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qfog.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import qfog.application.Application;
import qfog.application.Component;
import qfog.infrastructure.Infrastructure;

public class ExhaustiveSearch {

    Application A;
    Infrastructure Phi;
    HashSet<HashMap<String, String>> deployments;
    HashMap<String, ArrayList<Node>> K;
    HashMap<Component, Node> open;

    public ExhaustiveSearch(Application A, Infrastructure Phi) {
        this.A = A;
        this.Phi = Phi;
        deployments = new HashSet<>();
        K = new HashMap<>();
        open = new HashMap<>();
    }

    ExhaustiveSearch() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public HashMap<Component, Node> search() {  
        if (open.size() == A.S.size()) {
            return open;
        }

        Component s = A.S.get(open.size());

        for (Node n : K.get(s.getId())) {
            if (n.isCompatible(s)) {
                open.put(s, n);
                n.deploy(s);
                HashMap<Component, Node> result = search();
                if (result != null){
                    return result;
                }
            }
            n.undeploy(s);
            open.remove(s);
        }
        return null;
    }
}
