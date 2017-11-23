/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fstrahij_zadaca_1;

import builder.Mjesto;
import prototype.Aktuator;
import prototype.Senzor;
import factorymethod.algoritam.Algoritam;
import factorymethod.algoritam.AlgoritamFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import prototype.Aktuatori;
import prototype.Senzori;
import singleton.ThingsOfFOI;

/**
 *
 * @author Filip
 */
public class RadnaDretva extends Thread {

    private int trajanje_ciklusa = 0, br_ciklusa = 0;
    private String algoritam;
    private List<Mjesto> mjesta;
    private HashMap<String, List<String>> neispravniUredjaji = new HashMap<>();

    public RadnaDretva(int trajanje_ciklusa, int br_ciklusa, String algoritam, List<Mjesto> mjesta) {

        this.trajanje_ciklusa = trajanje_ciklusa * 1000;
        this.br_ciklusa = br_ciklusa;
        this.algoritam = algoritam;
        this.mjesta = new ArrayList<>(mjesta);

    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        AlgoritamFactory af = new AlgoritamFactory();
        Algoritam alg = af.kreirajAlgoritam(algoritam);
        alg.setMjesta(mjesta);

        int i = 0, element = 0;
        long t = 0, kraj = 0;

        List<String> uredjaji = new ArrayList<>();

        while (i < br_ciklusa) {

            if (element >= mjesta.size()) {
                element = 0;
            }

            t = System.currentTimeMillis();
            kraj = t + trajanje_ciklusa;

            while (System.currentTimeMillis() < kraj) {
                uredjaji = alg.odrediIspravnostUredjaja(element);
            }

            if (!uredjaji.isEmpty()) {
                String naziv = mjesta.get(element).getNaziv();
                System.out.println("|NAZIV MJESTA | NAZIV UREDJAJA|");
                System.out.println("===============================================================");

                if (!neispravniUredjaji.containsKey(naziv)) {

                    neispravniUredjaji.put(naziv, uredjaji);
                    continue;
                }

                List<String> stari = neispravniUredjaji.get(naziv);

                for (String novi : uredjaji) {

                    String brisiIzListe = null;

                    int brojNeispravnih = 1;

                    for (String s : stari) {
                        if (novi.equals(s)) {
                            brojNeispravnih++;
                            if (brojNeispravnih == 3) {
                                
                                /*ThingsOfFOI tof = ThingsOfFOI.getInstance();                                
                                
                                Mjesto nM = tof.getMjesta().get(element);
                                System.out.println("\nIMA IMA IMA\n" + s);
                                for (Senzor pr : nM.senzoriMjesta) {
                                    //if (s.toUpperCase().equals(pr.getNaziv())) {
                                        System.out.println("\npizda:\n"+pr.getNaziv());
                                    //}
                                }*/
                                
                                Senzor se = mjesta.get(element).getSenzor(s);

                                if (se == null) {

                                    Aktuator ak = mjesta.get(element).getAktuator(s);

                                    Aktuator klon = null;

                                    try {
                                        klon = ak.clone();
                                        System.out.println("\nKLON AKTUATOR: " + klon.getNaziv() + "\n");

                                    } catch (CloneNotSupportedException ex) {
                                        Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                    
                                    mjesta.get(element).obrisiAktuator(ak);
                                    ak = null;
                                    System.out.println("ZAMIJENJENI AKTUATOR: " + naziv + " | " + s);

                                } else {

                                    Senzor klon = null;

                                    try {
                                        klon = se.clone();
                                        System.out.println("\nKLON: " + klon.getNaziv() + "\n");

                                    } catch (CloneNotSupportedException ex) {
                                        Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                   
                                    mjesta.get(element).obrisiSenzor(se);
                                    se = null;
                                    //klonati aktuator te ga postaviti
                                    System.out.println("ZAMIJENJENI SENZOR: " + naziv + " | " + s);

                                }

                                brisiIzListe = s;
                            }
                        }

                    }

                    System.out.println("|" + naziv + " | " + novi + "|");

                    if (brisiIzListe == null) {
                        stari.add(novi);
                    } else {
                        while (stari.contains(brisiIzListe)) {
                            stari.remove(brisiIzListe);
                        }
                        brisiIzListe = null;
                    }
                }

                neispravniUredjaji.put(naziv, stari);
            }
            System.out.println("===============================================================");
            i++;
            System.out.println("");
            System.out.println("ciklus br: " + i);
            System.out.println("");

            element++;
        }
        /*for (String key : neispravniUredjaji.keySet()) {
            for (String value : neispravniUredjaji.get(key)) {
                System.out.println(key + " " + value);
            }
        }*/
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

}
