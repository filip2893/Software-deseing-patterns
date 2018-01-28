/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import MVC.controller.singleton.ThingsOfFOI;

/**
 *
 * @author Filip
 */
public class Program {
    private Rasporedjivac ispis;
    private ThingsOfFOI tof;

    public Program() {
        this.ispis = new Rasporedjivac();
        tof = ThingsOfFOI.getInstance();
    }
    
    public void pokreni (String[] args){
        if (ispis.provjeriArgumente(args)) {
            ispis.ucitajDatoteke();
            ispis.inicijalizirajSustav();
            tof.prikaziProzore();
            
        }      
    }
}
