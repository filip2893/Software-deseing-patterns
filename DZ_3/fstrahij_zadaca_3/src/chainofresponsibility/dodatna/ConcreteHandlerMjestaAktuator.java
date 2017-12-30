/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chainofresponsibility.dodatna;

import MVC.controller.singleton.ThingsOfFOI;
import MVC.model.composite.Aktuator;
import MVC.model.composite.Mjesto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Filip
 */
public class ConcreteHandlerMjestaAktuator implements Zahtjev{
    private Zahtjev chain;

    @Override
    public void setNextChain(Zahtjev next) {
        this.chain = next;
    }

    @Override
    public void obradi(Handler naredba) {
        //naziv;tip: 0 - vanjski, 1 - unutarnji;broj senzora;broj aktuatora;naziv akt; vrsta(0,1,2,3); min; max;
        if (naredba.getNaredba().startsWith("a")) {
            String command = naredba.getNaredba().substring(2, naredba.getNaredba().length());
            
            String[] atributi = command.split(";");
            int tip = Integer.parseInt(atributi[1]); 
            int vrsta = Integer.parseInt(atributi[5]);
            
            if (atributi.length == 8 && (tip == 0 || tip == 1) && vrsta>=0 && vrsta<=3) {
                ThingsOfFOI tof = ThingsOfFOI.getInstance();
                
                List<Mjesto> mjesta = new ArrayList<>(tof.getMjesta());
                List<Aktuator> aktuatori = new ArrayList<>(tof.getAktuators());
                
                Collections.sort(mjesta, Comparator.comparing(Mjesto::getId));
                Collections.sort(aktuatori, Comparator.comparing(Aktuator::getId));
                
                int id = mjesta.get((mjesta.size()-1)).getId() + 1;
                
                Mjesto newMjesto = new Mjesto(atributi[0], 
                                    tip, 
                                    Integer.parseInt(atributi[2]), 
                                    Integer.parseInt(atributi[3]), id);
                
                
                
                id = aktuatori.get((aktuatori.size()-1)).getId() + 1;
                
                Aktuator newAktuator =  new Aktuator(id, atributi[4], 
                                    tip, 
                                    Integer.parseInt(atributi[5]), 
                                    Integer.parseInt(atributi[6]), 
                                    Integer.parseInt(atributi[7]));
                
                newMjesto.add(newAktuator);
                newMjesto.sastavi();
                
                mjesta.add(newMjesto);
                aktuatori.add(newAktuator);
                
                
                
                
                tof.inicijalizirajSustav(mjesta, aktuatori, tof.getSenzors(), tof.getRaspored());
                
            }
        }else{
            this.chain.obradi(naredba);
        }
    }

   
    
}
