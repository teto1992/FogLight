/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qfog.application;

import qfog.utils.QoSProfile;

/**
 *
 * @author stefano
 */
public abstract class ThingsRequirement {

    private QoSProfile q;
    

    public void setQ(QoSProfile q) {
        this.q = q;
    }

    public QoSProfile getQ() {
        return q;
    }
}
