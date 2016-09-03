/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qfog.infrastructure;

import java.util.HashSet;
import qfog.utils.Link;

/**
 *
 * @author stefano
 */
public class Infrastructure {
    public HashSet<CloudDatacentre> C;
    public HashSet<FogNode> F;
    public HashSet<Thing> T;
    public HashMap<Couple, Link> L;
}
