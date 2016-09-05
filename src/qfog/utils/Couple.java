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
public class Couple {
    private String a, b;
    
    public Couple(String a, String b){
        this.a = a;
        this.b = b;
    }
    
    public void setA(String a){
        this.a = a;
    }
    
    public void setB(String b){
        this.b = b;
    }
    
    public String getA(){
        return a;
    }
    
    public String getB(){
        return b;
    }
    
    public boolean equals(String x, String y){
        return x.equals(a) && y.equals(b);
    }
}
