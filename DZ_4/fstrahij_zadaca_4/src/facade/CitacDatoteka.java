/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import MVC.model.composite.Aktuator;
import MVC.model.composite.Mjesto;
import MVC.model.composite.Senzor;
import factorymethod.datoteka.Datoteka;
import factorymethod.datoteka.DatotekaFactory;
import MVC.model.Raspored;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Filip
 */
public class CitacDatoteka {
    
    private DatotekaFactory df = new DatotekaFactory();
    private Datoteka datoteka;    

    public List<Aktuator> aktuatoriDatoteka(String aktuatori_dat) {
        datoteka = df.koristiDatoteku("Aktuatori", aktuatori_dat);
        datoteka.otvoriKreirajDatoteku();

        List<Aktuator> aktuators = new ArrayList<>();
        
        for (Object o : datoteka.vratiPodatke()) {
            aktuators.add((Aktuator) o);
        }
        return aktuators;
    }
    
    public List<Senzor> senzoriDatoteka(String senzori_dat) {
        datoteka = df.koristiDatoteku("Senzori", senzori_dat);
        datoteka.otvoriKreirajDatoteku();

        List<Senzor> senzors = new ArrayList<>();
        
        for (Object o : datoteka.vratiPodatke()) {
            senzors.add((Senzor) o);
        }
        return senzors;
    }
    
    public List<Mjesto> mjestaDatoteka(String mjesta_dat) {
        datoteka = df.koristiDatoteku("Mjesta", mjesta_dat);
        datoteka.otvoriKreirajDatoteku();

        List<Mjesto> mjesta = new ArrayList<>();
        
        for (Object o : datoteka.vratiPodatke()) {
            mjesta.add((Mjesto) o);
        }
        return mjesta;
    }

    public List<Raspored> rasporedDatoteka(String raspored_dat) {
        datoteka = df.koristiDatoteku("Raspored", raspored_dat);
        datoteka.otvoriKreirajDatoteku();

        List<Raspored> raspored = new ArrayList<>();
        
        for (Object o : datoteka.vratiPodatke()) {
            raspored.add((Raspored) o);
        }
        return raspored;
    }
}
