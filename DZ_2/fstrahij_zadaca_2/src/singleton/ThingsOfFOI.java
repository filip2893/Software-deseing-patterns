/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package singleton;

import chainofresponsibility.Obrada;
import chainofresponsibility.Uredjaj;
import fstrahij_zadaca_2.GeneratorSlucajnihBrojeva;
import composite.Mjesto;
import fstrahij_zadaca_2.RadnaDretva;
import iterator.Mjesta;
import java.util.ArrayList;
import java.util.List;
import composite.Aktuator;
import composite.Senzor;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Filip
 */
public class ThingsOfFOI {

    private static volatile ThingsOfFOI thingsOfFOI;
    private String algoritam, izlaz_dat, izlaz = "";
    private long sjeme;
    private int trajanje_ciklusa, br_ciklusa, brl;
    private List<Aktuator> aktuators;
    private List<Senzor> senzors;
    private GeneratorSlucajnihBrojeva gen = new GeneratorSlucajnihBrojeva();
    private Mjesta mjesta_it;

    private ThingsOfFOI() {
    }

    public static ThingsOfFOI getInstance() {
        if (thingsOfFOI == null) {
            synchronized (ThingsOfFOI.class) {
                if (thingsOfFOI == null) {
                    thingsOfFOI = new ThingsOfFOI();
                }
            }
        }
        return thingsOfFOI;
    }

    public void inicijalizirajSustav(String algoritam, String izlaz_dat,
            int trajanje_ciklusa, int br_ciklusa, String izlaz, Mjesta sva_mjesta, int brl) {

       
        this.izlaz_dat = izlaz_dat;
        this.algoritam = algoritam;
        this.trajanje_ciklusa = trajanje_ciklusa;
        this.br_ciklusa = br_ciklusa;
        this.izlaz += izlaz;
        this.aktuators = new ArrayList<>();
        this.senzors = new ArrayList<>();
        this.mjesta_it = sva_mjesta;
        this.brl = brl;

        inicijalizirajMjesta(sva_mjesta);

        RadnaDretva rd = new RadnaDretva(trajanje_ciklusa, br_ciklusa, algoritam);
        rd.start();

    }    

    public String getAlgoritam() {
        return algoritam;
    }

    public String getIzlaz_dat() {
        return izlaz_dat;
    }

    public String getIzlaz() {
        return izlaz;
    }

    public long getSjeme() {
        return sjeme;
    }

    public int getTrajanje_ciklusa() {
        return trajanje_ciklusa;
    }

    public int getBr_ciklusa() {
        return br_ciklusa;
    }

    public List<Aktuator> getAktuators() {
        return aktuators;
    }

    public List<Senzor> getSenzors() {
        return senzors;
    }

    public Mjesta getMjesta_it() {
        return mjesta_it;
    }

    public void setIzlaz(String izlaz) {
        this.izlaz = izlaz;
    }

    public void setGen(GeneratorSlucajnihBrojeva gen) {
        this.gen = gen;
    }

    public GeneratorSlucajnihBrojeva getGen() {
        return gen;
    }

    public int getBrl() {
        return brl;
    }

    public Mjesta sortiraj(Mjesta sva_mjesta) {
        int min = 1000, id = 0, brojacStaro = 0, velicina = sva_mjesta.length(), brojac = 0;

        Mjesto mjestoMin = null;

        Mjesta brisac = new Mjesta(velicina);
        Mjesta dodavac = new Mjesta(velicina);

        Mjesta.Iterator mj_it;

        mj_it = mjesta_it.create_iterator();
        int x = 0;
        for (mj_it.first(); !mj_it.is_done(); mj_it.next()) {
            brisac.add(mj_it.current_item(), x);
            x++;
        }

        for (int i = 0; i < velicina; i++) {
            mj_it = brisac.create_iterator();

            for (mj_it.first(); !mj_it.is_done(); mj_it.next()) {
                int trId = mj_it.current_item().getId();
                if (trId < min) {
                    min = mj_it.current_item().getId();
                    mjestoMin = mj_it.current_item();
                    id = brojac;
                }
                brojac++;
            }
            brisac.remove(id);
            dodavac.add(mjestoMin, i);
            brojac = 0;
            id = 0;
            min = 1000;
        }

        return dodavac;
    }

    public void inicijalizirajMjesta(Mjesta sva_mjesta) {

        Mjesta sva_mjesta_ispravna = sortiraj(sva_mjesta);
        Mjesta.Iterator mj_it = sva_mjesta_ispravna.create_iterator();
        Obrada obrada = new Obrada();
        boolean ispravan;
        int senzoriIspravni = 0;
        ArrayList<Aktuator> aktIspravni = new ArrayList<>();
        ArrayList<Senzor> senIspravni = new ArrayList<>();

        for (mj_it.first(); !mj_it.is_done(); mj_it.next()) {

            ArrayList uredjaji = mj_it.current_item().getUredjaji();

            izlaz += "\n####################################\ninicijalizacija mjesta[" + mj_it.current_item().getNaziv().toUpperCase() + "]\n------------------------------------";

            for (Object u : uredjaji) {
                ispravan = obrada.execute((Uredjaj) u, 0.1);

                if (ispravan) {
                    if (u.getClass().toString().contains("Aktuator")) {
                        Aktuator a = (Aktuator) u;
                        aktIspravni.add(a);
                        izlaz += "\nAKTUATOR: " + a.getNaziv() + "[+]";
                    } else {
                        Senzor s = (Senzor) u;
                        senIspravni.add(s);
                        izlaz += "\nSENZOR: " + s.getNaziv() + "[+]";
                        senzoriIspravni++;
                    }
                } else {
                    if (u.getClass().toString().contains("Aktuator")) {
                        Aktuator a = (Aktuator) u;
                        izlaz += "\nAKTUATOR: " + a.getNaziv() + "[-]";
                    } else {
                        Senzor s = (Senzor) u;
                        izlaz += "\nSENZOR: " + s.getNaziv() + "[-]";
                    }
                }
            }
            if (senzoriIspravni > 0) {
                opremanjeMjesta(aktIspravni, senIspravni, senzoriIspravni + 1);
            }
            aktIspravni.clear();
            senIspravni.clear();
            senzoriIspravni = 0;
        }
    }

    public void opremanjeMjesta(ArrayList<Aktuator> aktIspravni, ArrayList<Senzor> senIspravni, int senzoriIspravni) {

        int broj = gen.dajSlucajniBroj(1, senzoriIspravni) + 1;

        izlaz += "\n...................................\nOPREMANJE MJESTA\n..................................";

        ArrayList<Senzor> senzori;

        for (Aktuator a : aktIspravni) {

            senzori = new ArrayList<>(senIspravni);

            izlaz += "\nAKTUATOR [" + a.getNaziv() + "] == SENZORI:";

            for (int i = 0; i < broj; i++) {

                Senzor s = slucajniSenzor(senzori);

                a.add(s);

                senzori.remove(s);

                izlaz += "\n\t" + "=> " + s.getNaziv();
            }

            a.sastavi();
        }
        izlaz += "\n####################################\n\n";
    }

    public Senzor slucajniSenzor(ArrayList<Senzor> senIspravni) {
        Random generator = new Random();
        int randomIndex = generator.nextInt(senIspravni.size());
        return senIspravni.get(randomIndex);
    }

}
