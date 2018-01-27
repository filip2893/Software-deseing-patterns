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
    
    public Mjesto mjestoPodaci(Integer id){        
        for (Mjesto mj : tof.getMjesta()) {
            if (mj.getId() == id) {
                return mj;
            }
        }        
        return null;    
    }
    
    public Senzor senzorPodaci(Integer id){        
        for (Senzor se : tof.getSenzors()) {
            if (se.getIdUredjaj() == id) {
                return se;
            }
        }        
        return null;    
    }
    
    public Aktuator aktuatorPodaci(Integer id){        
        for (Aktuator ak : tof.getAktuators()) {
            if (ak.getIdUredjaj() == id) {
                return ak;
            }
        }        
        return null;    
    }
    
    public String ModelPodaci(Integer id, boolean odabir){
        String podaci = "";
        if (odabir) {
            for (Aktuator ak : tof.getAktuators()) {
                if (ak.getIdModel() == id) {
                    podaci += ak.getNaziv() + ";" + ak.getIdUredjaj() + "\n";
                }
            }
            return podaci;
        } else {
            for (Senzor se : tof.getSenzors()) {
                if (se.getIdModel() == id) {
                    podaci += se.getNaziv() + ";" + se.getIdUredjaj() + "\n";                    
                }
            }
            return podaci;
        }       
    }
}
