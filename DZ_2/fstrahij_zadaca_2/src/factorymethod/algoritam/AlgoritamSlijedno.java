/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorymethod.algoritam;

import chainofresponsibility.Obrada;
import chainofresponsibility.Uredjaj;
import composite.Aktuator;
import fstrahij_zadaca_2.GeneratorSlucajnihBrojeva;
import java.util.ArrayList;
import java.util.List;
import composite.Mjesto;
import composite.Senzor;
import iterator.Mjesta;
import java.util.HashMap;

/**
 *
 * @author Filip
 */
public class AlgoritamSlijedno implements Algoritam {

    Mjesta mjesta;
    Mjesto mjesto;    

    @Override
    public Mjesto getMjesto() {
        return this.mjesto;
    }

    @Override
    public Mjesto odrediMjesto(int element) {

        int brojac = 0;

        Mjesta.Iterator mj_it = mjesta.create_iterator();

        for (mj_it.first(); !mj_it.is_done(); mj_it.next()) {
            if (brojac == element) {
                this.mjesto = mj_it.current_item();
                break;
            }
            brojac++;
        }

       return mjesto;
    }

    @Override
    public void setMjesta(Mjesta mjesta) {

        this.mjesta = mjesta;

    }

}
