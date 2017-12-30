/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorymethod.algoritam;

import MVC.controller.singleton.ThingsOfFOI;
import java.util.ArrayList;
import java.util.List;
import MVC.model.composite.Mjesto;

/**
 *
 * @author Filip
 */
public class AlgoritamSlijedno implements Algoritam {

    List<Mjesto> mjesta;
    Mjesto mjesto;    

    @Override
    public Mjesto getMjesto() {
        return this.mjesto;
    }

    @Override
    public Mjesto odrediMjesto(int element) {
        
        ThingsOfFOI tof = ThingsOfFOI.getInstance();

        int brojac = 0;

        for (Mjesto mj : tof.getMjesta()) {
            if (brojac == element) {
                this.mjesto = mj;
                break;
            }
            brojac++;
        }

       return mjesto;
    }

    @Override
    public void setMjesta(List<Mjesto> mjesta) {

        this.mjesta = new ArrayList<>(mjesta);

    }

}
