/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.model;

import MVC.model.composite.Mjesto;
import java.util.ArrayList;
import java.util.List;
import MVC.controller.singleton.ThingsOfFOI;
import MVC.model.composite.Aktuator;
import MVC.model.composite.Senzor;

/**
 *
 * @author Filip
 */
public class BazaPodataka {
    private final ThingsOfFOI tof;

    public BazaPodataka() {
        tof = ThingsOfFOI.getInstance();
    }    
    
    public Mjesto mjestoPodaci(String naziv){        
        for (Mjesto mj : tof.getMjesta()) {
            if (mj.getNaziv().equals(naziv)) {
                return mj;
            }
        }        
        return null;    
    }
    
    public Senzor senzorPodaci(String naziv){        
        for (Senzor se : tof.getSenzors()) {
            if (se.getNaziv().equals(naziv)) {
                return se;
            }
        }        
        return null;    
    }
    
    public Aktuator aktuatorPodaci(String naziv){        
        for (Aktuator ak : tof.getAktuators()) {
            if (ak.getNaziv().equals(naziv)) {
                return ak;
            }
        }        
        return null;    
    }
}
