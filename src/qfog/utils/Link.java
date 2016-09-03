/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qfog.utils;

/**
 *
 * @author stefano
 */
public class Link {
    Couple link;
    QoSProfile q;
    
    /**
     * 
     * @param a first node
     * @param b second node
     * @param latency
     * @param bandwidth 
     */
    public Link (String a, String b, int latency, double bandwidth){
        link = new Couple(a,b);
        this.q = new QoSProfile(latency, bandwidth);
    }
    
    public String toString(){
        return "("+ this.link.getA() +
                ", " + this.link.getB() +
                ", " + this.q + ")";
    }
   
        
}
