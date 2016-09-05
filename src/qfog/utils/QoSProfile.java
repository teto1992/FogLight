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
public class QoSProfile {
    private double latency, bandwidth;
    
    public QoSProfile (double latency, double bandwidth){
        this.latency = latency;
        this.bandwidth = bandwidth;
    }
    
    public void setLatency (double latency){
        this.latency = latency;
    }
    
    public void setBandwidth(double bandwidth){
        this.bandwidth = bandwidth;
    }
    
    public double getLatency(){
        return this.latency;
    }
    
    public double getBandwidth(){
        return this.bandwidth;
    }
    
    public String toString(){
        return "<" + latency + ", " + bandwidth + ">";
    }
    
    public boolean supports(QoSProfile q){
        boolean result = false;
        if (this.latency <= q.getLatency() && this.bandwidth >= q.getBandwidth())
            result = true;
        return result;
    }
}
