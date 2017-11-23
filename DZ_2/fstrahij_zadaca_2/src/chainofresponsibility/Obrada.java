/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chainofresponsibility;

import java.util.Random;
import singleton.ThingsOfFOI;

/**
 *
 * @author Filip
 */
public class Obrada{
    private static int slucajniBroj;
    private static int nextID = 1;
    private int id = nextID++;
    
    
    public boolean execute(Uredjaj u, double vjerojatnost) {
        ThingsOfFOI tof = ThingsOfFOI.getInstance();
        slucajniBroj = tof.getGen().exp(vjerojatnost);        
        
        if (slucajniBroj != 0) {
            return false;
        }
        return true;
    }
}
