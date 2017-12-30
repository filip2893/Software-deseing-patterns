/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorymethod.algoritam;

import chainofresponsibility.Obrada;
import chainofresponsibility.Uredjaj;
import MVC.model.composite.Aktuator;
import java.util.List;
import MVC.model.composite.Mjesto;
import MVC.model.composite.Senzor;
import java.util.ArrayList;
import java.util.HashMap;
import MVC.controller.singleton.ThingsOfFOI;

/**
 *
 * @author Filip
 */
public class AlgoritamSlucajno implements Algoritam {

    List<Mjesto> mjesta;
    Mjesto mjesto;
    List<Integer> brojevi = new ArrayList<>();    

    @Override
    public Mjesto odrediMjesto(int element) {
/*
        ThingsOfFOI tof = ThingsOfFOI.getInstance();

        Mjesta.Iterator mj_it = mjesta.create_iterator();

        int brojac = 0;
        int slucajniBroj = 0;
        boolean postoji = true;

        if (brojevi.isEmpty()) {
            slucajniBroj = tof.getGen().dajSlucajniBroj(0, mjesta.length());
            brojevi.add(slucajniBroj);
        } else {
            while (postoji) {
                slucajniBroj = tof.getGen().dajSlucajniBroj(0, mjesta.length());
                postoji = brojevi.contains(slucajniBroj);
            }
        }

        for (mj_it.first(); !mj_it.is_done(); mj_it.next()) {
            if (brojac == slucajniBroj) {
                this.mjesto = mj_it.current_item();
                break;
            }
            brojac++;
        }
        
        if (brojevi.size() == mjesta.length()) {
            brojevi.clear();
        }
        
        return mjesto;*/
        return null;
    }

    @Override
    public Mjesto getMjesto() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setMjesta(List<Mjesto> mjesta) {
        this.mjesta = mjesta;
    }

}
