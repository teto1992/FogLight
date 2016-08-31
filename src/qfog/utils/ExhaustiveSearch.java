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
import qfog.infrastructure.Infrastructure;

public class ExhaustiveSearch {
    
    Application A;
    Infrastructure Phi;
    HashSet<HashMap<String,String>> deployments;
    HashMap<String,ArrayList<String>> K;

    public ExhaustiveSearch(Application A, Infrastructure Phi){
        this.A = A;
        this.Phi = Phi;
        deployments = new HashSet<>();
        K = new HashMap<>();
    }
    
    public void findCompatibleNodes(){
        for (Node n : Phi.C){
            A.S.stream().filter((s) -> (n.isCompatible(s))).map((s) -> {
                if (!K.containsKey(s.getId()))
                    K.put(s.getId(), new ArrayList<>());
                return s;
            }).forEach((s) -> {
                K.get(s.getId()).add(n.getId());
            });
        } 
        for (Node n : Phi.F){
            A.S.stream().filter((s) -> (n.isCompatible(s))).map((s) -> {
                if (!K.containsKey(s.getId()))
                    K.put(s.getId(), new ArrayList<>());
                return s;
            }).forEach((s) -> {
                K.get(s.getId()).add(n.getId());
            });
        } 
    }
    
    public void search(){
         
    }
}
