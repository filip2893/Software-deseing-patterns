/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fstrahij_zadaca_2;

import chainofresponsibility.Obrada;
import chainofresponsibility.Uredjaj;
import composite.Aktuator;
import composite.Mjesto;
import composite.Senzor;
import factorymethod.algoritam.Algoritam;
import factorymethod.algoritam.AlgoritamFactory;
import factorymethod.datoteka.Datoteka;
import factorymethod.datoteka.DatotekaFactory;
import iterator.Mjesta;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import proxy.Izlaz;
import proxy.Spremnik;
import singleton.ThingsOfFOI;

/**
 *
 * @author Filip
 */
public class RadnaDretva extends Thread {

    private int trajanje_ciklusa = 0, br_ciklusa = 0;
    private String algoritam, neispravniUredjaji = "", izlaz = "";
    private Mjesta mjesta;
    private Obrada obrada = new Obrada();
    private HashMap<String, Integer> mjestoUredjaji = new HashMap<String, Integer>();
    private Mjesto mjesto;
    private boolean ispravan;
    ThingsOfFOI tof = ThingsOfFOI.getInstance();

    public RadnaDretva(int trajanje_ciklusa, int br_ciklusa, String algoritam) {
        this.trajanje_ciklusa = trajanje_ciklusa * 1000;
        this.br_ciklusa = br_ciklusa;
        this.algoritam = algoritam;
    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        mjesta = tof.getMjesta_it();

        AlgoritamFactory af = new AlgoritamFactory();
        Algoritam alg = af.kreirajAlgoritam(algoritam);
        alg.setMjesta(mjesta);

        int i = 0, element = 0;

        while (i < br_ciklusa) {

            System.out.println("\nCiklus br: " + i + "\n");

            if (element == mjesta.length()) {
                element = 0;
            }

            mjesto = alg.odrediMjesto(element);

            izlaz = "\nPROVJERA STATUSA UREDJAJA\n-------------------------------";

            provjeriIspravnostUredjaja(mjesto);

            i++;
            element++;
            System.out.println(izlaz);
            String dodajIzlazu = tof.getIzlaz();
            dodajIzlazu += izlaz;
            tof.setIzlaz(dodajIzlazu);
            try {
                sleep(trajanje_ciklusa);
            } catch (InterruptedException ex) {
                Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        zapisiUDatoteku();
        this.interrupt();
    }

    private void provjeriIspravnostUredjaja(Mjesto mjesto) {
        for (Object u : mjesto.getUredjaji()) {
            ispravan = obrada.execute((Uredjaj) u, 0.5);
            if (!ispravan) {
                if (u.getClass().toString().contains("Aktuator")) {
                    Aktuator a = (Aktuator) u;
                    neispravniUredjaji = mjesto.getNaziv() + " " + a.getNaziv();
                    izlaz += "\n" + neispravniUredjaji + "[-]";
                } else {
                    Senzor s = (Senzor) u;
                    neispravniUredjaji = mjesto.getNaziv() + " " + s.getNaziv();
                    izlaz += "\n" + neispravniUredjaji + "[-]";
                }

                if (mjestoUredjaji.containsKey(neispravniUredjaji)) {
                    int br = mjestoUredjaji.get(neispravniUredjaji);
                    if (br == 3) {
                        Object o = u;
                        if (u.getClass().toString().contains("Aktuator")) {
                            Aktuator a = (Aktuator) u;
                            try {
                                o = a.clone();
                            } catch (CloneNotSupportedException ex) {
                                Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            Senzor s = (Senzor) u;
                            try {
                                o = s.clone();
                            } catch (CloneNotSupportedException ex) {
                                Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        mjesto.remove(u);
                        mjesto.add(o);
                        mjestoUredjaji.remove(neispravniUredjaji, u);
                        izlaz += "\n##################\n ZAMIJENJENO:\n" + neispravniUredjaji + "\n##################";
                        break;
                    } else {
                        br++;
                        mjestoUredjaji.put(neispravniUredjaji, br);
                    }
                } else {
                    mjestoUredjaji.put(neispravniUredjaji, 1);
                }
            } else {
                String ispravniUredjaji;
                if (u.getClass().toString().contains("Aktuator")) {
                    Aktuator a = (Aktuator) u;
                    ispravniUredjaji = mjesto.getNaziv() + " " + a.getNaziv();
                    izlaz += "\n" + ispravniUredjaji + "[+]";
                } else {
                    Senzor s = (Senzor) u;
                    ispravniUredjaji = mjesto.getNaziv() + " " + s.getNaziv();
                    izlaz += "\n" + ispravniUredjaji + "[+]";
                }
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    public void zapisiUDatoteku() {
        izlaz = tof.getIzlaz();
        int brl = tof.getBrl();
        String izlaz_dat = tof.getIzlaz_dat();

        Izlaz iz = new Spremnik(brl, izlaz, izlaz_dat);
        iz.zapisi();
    }

}
