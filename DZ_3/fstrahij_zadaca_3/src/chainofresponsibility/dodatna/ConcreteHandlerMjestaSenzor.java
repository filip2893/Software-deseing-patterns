/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chainofresponsibility.dodatna;

import MVC.controller.singleton.ThingsOfFOI;
import MVC.model.composite.Aktuator;
import MVC.model.composite.Mjesto;
import MVC.model.composite.Senzor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Filip
 */
public class ConcreteHandlerMjestaSenzor implements Zahtjev {

    private Zahtjev chain;

    @Override
    public void setNextChain(Zahtjev next) {
        this.chain = next;
    }

    @Override
    public void obradi(Handler naredba) {
        //naziv;tip: 0 - vanjski, 1 - unutarnji;broj senzora;broj aktuatora;naziv sen; vrsta(0,1,2,3); min; max;
        if (naredba.getNaredba().startsWith("s")) {
            String command = naredba.getNaredba().substring(2, naredba.getNaredba().length());

            String[] atributi = command.split(";");
            int tip = Integer.parseInt(atributi[1]);
            int vrsta = Integer.parseInt(atributi[5]);

            if (atributi.length == 8 && (tip == 0 || tip == 1) && vrsta >= 0 && vrsta <= 3) {
                ThingsOfFOI tof = ThingsOfFOI.getInstance();

                List<Mjesto> mjesta = new ArrayList<>(tof.getMjesta());
                List<Senzor> senzori = new ArrayList<>(tof.getSenzors());

                Collections.sort(mjesta, Comparator.comparing(Mjesto::getId));
                Collections.sort(senzori, Comparator.comparing(Senzor::getId));

                int id = mjesta.get((mjesta.size() - 1)).getId() + 1;

                Mjesto newMjesto = new Mjesto(atributi[0],
                        tip,
                        Integer.parseInt(atributi[2]),
                        Integer.parseInt(atributi[3]), id);

                mjesta.add(newMjesto);

                id = senzori.get((senzori.size() - 1)).getId() + 1;

                Senzor newSenzor = new Senzor(id, atributi[4],
                        tip,
                        Integer.parseInt(atributi[5]),
                        Float.parseFloat(atributi[6]),
                        Float.parseFloat(atributi[7]));
                
                newMjesto.add(newSenzor);
                newMjesto.sastavi();
                
                mjesta.add(newMjesto);
                senzori.add(newSenzor);

                tof.inicijalizirajSustav(mjesta, tof.getAktuators(), senzori, tof.getRaspored());
            } else {
                this.chain.obradi(naredba);
            }
        }
    }
}
