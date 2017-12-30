/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorymethod.algoritam;

import MVC.model.composite.Mjesto;
import MVC.controller.singleton.ThingsOfFOI;
import java.util.List;

/**
 *
 * @author Filip
 */
public class AlgoritamAbecedno implements Algoritam {

    List<Mjesto> mjesta;
    Mjesto mjesto;

    @Override
    public Mjesto getMjesto() {
        return this.mjesto;
    }

    @Override
    public Mjesto odrediMjesto(int element) {
        
        /*ThingsOfFOI tof = ThingsOfFOI.getInstance();
        
        int brojac = 0;        

        Mjesta.Iterator mj_it;
        
        Mjesta dodavac = null;//tof.sortiraj(mjesta);
        
        mj_it = dodavac.create_iterator();

        for (mj_it.first(); !mj_it.is_done(); mj_it.next()) {
            if (brojac == element) {
                this.mjesto = mj_it.current_item();
                break;
            }
            brojac++;
        }
        
        return mjesto;*/
        return null;
    }

    @Override
    public void setMjesta(List<Mjesto> mjesta) {
        this.mjesta = mjesta;
    }

}
