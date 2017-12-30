/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import MVC.model.composite.Aktuator;
import MVC.model.composite.Mjesto;
import MVC.model.composite.Senzor;
import MVC.model.Raspored;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import MVC.controller.singleton.ThingsOfFOI;
import MVC.view.DonjiProzor;
import MVC.view.GornjiProzor;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Filip
 */
public class Ispis {

    private final UlazniKontrolor opcija = new UlazniKontrolor();

    private ThingsOfFOI tof;
    private String izlaz;
    private List<Aktuator> aktuators;
    private List<Senzor> senzors;
    private List<Mjesto> mjesta;
    private List<Raspored> raspored;
    private HashMap<Integer, Aktuator> aktuatoriNovi;
    private HashMap<Integer, Senzor> senzoriNovi;

    public Ispis() {
        this.aktuatoriNovi = new HashMap<>();
        this.senzoriNovi = new HashMap<>();
        tof = ThingsOfFOI.getInstance();
        izlaz = tof.getIzlaz();
    }

    public boolean provjeriArgumente(String[] args) {
        if (!opcija.provjera(args)) {
            System.out.println("Neispravna sintaksa");
            return false;
        }
        return true;
    }

    public void ucitajDatoteke() {

        CitacDatoteka cd = new CitacDatoteka();

        aktuators = new ArrayList<>(cd.aktuatoriDatoteka(opcija.getAktuatori_dat()));
        senzors = new ArrayList<>(cd.senzoriDatoteka(opcija.getSenzori_dat()));
        mjesta = new ArrayList<>(cd.mjestaDatoteka(opcija.getMjesta_dat()));
        raspored = new ArrayList<>(cd.rasporedDatoteka(opcija.getRaspored_dat()));

        /*for (int i = 0; i < 5; i++) {
            System.out.println("\033[" + i + ";"+ i +"f");
        }*/
        //System.out.println(tof.getIzlaz()); 
        /*GornjiProzor gp = new GornjiProzor(br-brk, brs);
        DonjiProzor dp = new DonjiProzor(br-brk+1, brk);
        gp.inicijaliziraj();
        dp.cekajNaredbu();*/
    }

    public void inicijalizirajSustav() {
        rasporedjivanjeMjesta();
        rasporedjivanjeAktuatora();
        tof.inicijalizirajSustav(mjesta, aktuators, senzors, raspored);
        //System.out.println(tof.getIzlaz());
    }

    private void rasporedjivanjeMjesta() {
        for (Raspored zapis : this.raspored) {

            int vrsta = zapis.getVrstaZapisa();

            if (vrsta == 0) {
                for (Mjesto mjesto : this.mjesta) {

                    if (zapis.getMjestoId() == mjesto.getId()) {

                        if (zapis.getVrsta() == 0) {
                            for (Senzor s : senzors) {

                                if ((mjesto.getTip() == s.getTip() || s.getTip() > 1) && zapis.getModelUredajajId() == s.getId()) {

                                    mjesto.add(s);

                                    senzoriNovi.put(zapis.getUredjajId(), s);

                                }
                            }
                        } else {
                            for (Aktuator a : aktuators) {

                                if ((mjesto.getTip() == a.getTip() || a.getTip() > 1) && zapis.getModelUredajajId() == a.getId()) {

                                    mjesto.add(a);

                                    aktuatoriNovi.put(zapis.getUredjajId(), a);

                                }
                            }
                        }
                    }
                    mjesto.sastavi();
                }
            }
        }
        tof.setIzlaz(izlaz);
    }

    private void rasporedjivanjeAktuatora() {

        for (Raspored zapis : this.raspored) {
            
            Aktuator aktuator = null;            

            int vrsta = zapis.getVrstaZapisa();
            if (vrsta == 1) {

                for (Map.Entry<Integer, Aktuator> a : aktuatoriNovi.entrySet()) {
                    if (zapis.getAktuatorId() == a.getKey()) {
                        aktuator = a.getValue();
                        break;
                    }
                }
                for (Map.Entry<Integer, Senzor> s : senzoriNovi.entrySet()) {
                    if (zapis.getSenzorId() == s.getKey()) {
                        aktuator.add(s.getValue());
                        break;
                    }
                }
            }
        }
    }
}
