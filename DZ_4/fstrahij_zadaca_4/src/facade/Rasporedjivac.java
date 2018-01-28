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
public class Rasporedjivac {

    private final UlazniKontrolor opcija = new UlazniKontrolor();

    private ThingsOfFOI tof;
    private String izlaz;
    private List<Aktuator> aktuatorsModel, aktuators;
    private List<Senzor> senzorsModel, senzors;
    private List<Object> rezervniUredjaji =  new ArrayList<>();
    private List<Mjesto> mjesta;
    private List<Raspored> raspored;
    private HashMap<Integer, Aktuator> aktuatoriNovi;
    private HashMap<Integer, Senzor> senzoriNovi;

    public Rasporedjivac() {
        this.aktuatoriNovi = new HashMap<>();
        this.senzoriNovi = new HashMap<>();
        
        this.senzors = new ArrayList<>();
        this.aktuators = new ArrayList<>();
        
        tof = ThingsOfFOI.getInstance();
        izlaz = tof.getIzlaz();
    }

    public boolean provjeriArgumente(String[] args) {
        if (opcija.provjera(args) == -1) {
            System.out.println("Neispravna sintaksa");
            return false;
        }else if (opcija.provjera(args) == 1) {
            return false;
        }
        return true;
    }

    public void ucitajDatoteke() {

        CitacDatoteka cd = new CitacDatoteka();

        aktuatorsModel = new ArrayList<>(cd.aktuatoriDatoteka(opcija.getAktuatori_dat()));
        senzorsModel = new ArrayList<>(cd.senzoriDatoteka(opcija.getSenzori_dat()));
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
        dodavanjePodmjesta();
        tof.inicijalizirajSustav(mjesta, aktuators, senzors, raspored);
        //System.out.println(tof.getIzlaz());        
    }

    private void rasporedjivanjeMjesta() {
        Aktuator aktNovi;
        Senzor senNovi;
        int max = 0;
        
        for (Raspored zapis : this.raspored) {

            int vrsta = zapis.getVrstaZapisa();

            if (vrsta == 0) {
                for (Mjesto mjesto : this.mjesta) {

                    if (zapis.getMjestoId() == mjesto.getId()) {

                        if (zapis.getVrsta() == 0) {
                            for (Senzor s : senzorsModel) {

                                if ((mjesto.getTip() == s.getTip() || s.getTip() > 1) 
                                        && zapis.getModelUredajajId() == s.getIdModel()) {
 
                                    senNovi = new Senzor(s.getIdModel(), 
                                                            s.getNaziv(),
                                                            s.getTip(),
                                                            s.getVrsta(),
                                                            s.getMin(),
                                                            s.getMax());
                                    
                                    senNovi.setIdUredjaj(zapis.getUredjajId());
                                    
                                    mjesto.add(senNovi);
                                    
                                    senzors.add(senNovi);

                                    senzoriNovi.put(zapis.getUredjajId(), senNovi);
                                    
                                    if (zapis.getUredjajId() > max) {                                        
                                        
                                        max = zapis.getUredjajId();
                                        
                                    }
                                }else{
                                    rezervniUredjaji.add(s);
                                }
                            }
                        } else if(zapis.getVrsta() == 1){
                            for (Aktuator a : aktuatorsModel) {

                                if ((mjesto.getTip() == a.getTip() || a.getTip() > 1) 
                                        && zapis.getModelUredajajId() == a.getIdModel()) {
 
                                    aktNovi = new Aktuator(a.getIdModel(), 
                                                            a.getNaziv(),
                                                            a.getTip(),
                                                            a.getVrsta(),
                                                            a.getMin(),
                                                            a.getMax());
                                    
                                    aktNovi.setIdUredjaj(zapis.getUredjajId());
                                    
                                    mjesto.add(aktNovi);
                                    
                                    aktuators.add(aktNovi);

                                    aktuatoriNovi.put(zapis.getUredjajId(), aktNovi);

                                    if (zapis.getUredjajId() > max) {                                        
                                        
                                        max = zapis.getUredjajId();
                                        
                                    }
                                }else{
                                    rezervniUredjaji.add(a);
                                }
                            }
                        }
                    }else{
                        continue;
                    }
                    mjesto.sastavi();
                }
            }
        }
        tof.setRezervniUredjaji(rezervniUredjaji);
        tof.setMax_br_ur(max);
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
    
    private void dodavanjePodmjesta() {
        boolean postoji = false;        
        List<Integer> IdPodmjesta = new ArrayList<>();
        
        for (Raspored zapis : this.raspored) {
            int vrsta = zapis.getVrstaZapisa();
            if (vrsta == 2) {
                if (!IdPodmjesta.isEmpty()) {
                    for (Integer idp : IdPodmjesta) {
                        if (idp == zapis.getPodMjestoId()) {
                            postoji = true;
                            break;
                        }
                    }
                }
                if (!postoji) {
                    for (Mjesto mjesto : this.mjesta) {
                        if (zapis.getMjestoId() == mjesto.getId()) {
                            for (Mjesto pod_mjesto : this.mjesta) {
                                if (zapis.getPodMjestoId() == pod_mjesto.getId()) {
                                    mjesto.add(pod_mjesto);
                                    IdPodmjesta.add(pod_mjesto.getId());
                                }
                            }
                        }
                    }
                }   
                postoji = false;
            }
        }
    }    
}
