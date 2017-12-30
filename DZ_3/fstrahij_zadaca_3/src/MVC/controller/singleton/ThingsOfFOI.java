/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.controller.singleton;

import MVC.controller.ObradaPodataka;
import MVC.model.BazaPodataka;
import chainofresponsibility.Obrada;
import chainofresponsibility.Uredjaj;
import fstrahij_zadaca_3.GeneratorSlucajnihBrojeva;
import MVC.model.composite.Mjesto;
import MVC.model.RadnaDretva;
import java.util.ArrayList;
import java.util.List;
import MVC.model.composite.Aktuator;
import MVC.model.composite.Senzor;
import MVC.model.Raspored;
import MVC.view.DonjiProzor;
import MVC.view.GornjiProzor;
import chainofresponsibility.dodatna.ConcreteHandlerMjestaAktuator;
import chainofresponsibility.dodatna.ConcreteHandlerMjestaSenzor;
import chainofresponsibility.dodatna.Handler;
import chainofresponsibility.dodatna.Zahtjev;
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
    private int trajanje_ciklusa, br_ciklusa, br, brk, brs;
    private double pi;
    private List<Aktuator> aktuators;
    private List<Senzor> senzors;
    private List<Mjesto> mjesta;
    private List<Raspored> raspored;
    private GeneratorSlucajnihBrojeva gen = new GeneratorSlucajnihBrojeva();

    public ThingsOfFOI() {
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

    public void inicijalizirajSustav(List<Mjesto> mjesta, List<Aktuator> aktuatori, List<Senzor> senzori, List<Raspored> raspored) {

        this.aktuators = new ArrayList<>(aktuatori);
        this.senzors = new ArrayList<>(senzori);
        this.mjesta = new ArrayList<>(mjesta);
        this.raspored = new ArrayList<>(raspored);
        //inicijalizirajMjesta();

        Obrada obrada = new Obrada();
        boolean ispravan;
        ArrayList<Aktuator> aktIspravni = new ArrayList<>();
        ArrayList<Senzor> senIspravni = new ArrayList<>();

        for (Mjesto mjesto : this.mjesta) {

            ArrayList uredjaji = mjesto.getUredjaji();

            izlaz += "\n####################################\ninicijalizacija mjesta[" + mjesto.getNaziv().toUpperCase() + "]\n------------------------------------";

            for (Object u : uredjaji) {
                ispravan = obrada.execute((Uredjaj) u, this.pi);

                if (ispravan) {
                    if (u.getClass().toString().contains("Aktuator")) {
                        Aktuator a = (Aktuator) u;
                        aktIspravni.add(a);
                        izlaz += "\nAKTUATOR: " + a.getNaziv() + "[+]";
                    } else {
                        Senzor s = (Senzor) u;
                        senIspravni.add(s);
                        izlaz += "\nSENZOR: " + s.getNaziv() + "[+]";
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
            aktIspravni.clear();
            senIspravni.clear();
        }

        //RadnaDretva rd = new RadnaDretva(trajanje_ciklusa, br_ciklusa, algoritam);
        //rd.start();
    }

    public void prikaziProzore() {
        GornjiProzor gornjiProzor = new GornjiProzor(br - brk, brs);
        DonjiProzor donjiProzor = new DonjiProzor(br - brk + 1, brk);
        ObradaPodataka obradaPodataka = new ObradaPodataka();
        RadnaDretva rd = null;

        gornjiProzor.ocistiProzor();
        gornjiProzor.inicijaliziraj();
        String linija = "", naziv = "";
        boolean kraj = false;

        do {
            String naredba = donjiProzor.cekajNaredbu();
            if (naredba.startsWith("M") && (naredba.length() > 2)) {
                naziv = naredba.substring(2, naredba.length());
                linija = obradaPodataka.mjestaObrada(naziv);
                if (!linija.isEmpty()) {
                    gornjiProzor.ispisi(linija);
                }
            } else if (naredba.startsWith("S") && (naredba.length() > 2)) {
                naziv = naredba.substring(2, naredba.length());
                linija = obradaPodataka.senzoriObrada(naziv);
                if (!linija.isEmpty()) {
                    gornjiProzor.ispisi(linija);
                }
            } else if (naredba.startsWith("A") && (naredba.length() > 2)) {
                naziv = naredba.substring(2, naredba.length());
                linija = obradaPodataka.aktuatoriObrada(naziv);
                if (!linija.isEmpty()) {
                    gornjiProzor.ispisi(linija);
                }
            } else if (naredba.startsWith("C") && (naredba.length() > 2)) {
                int brc = Integer.parseInt(naredba.substring(2, naredba.length()));
                if (brc > 0 && brc <= 100) {                    
                    this.br_ciklusa = brc;
                    rd = new RadnaDretva(trajanje_ciklusa, br_ciklusa);
                    rd.start();
                    while(rd.isAlive()){}
                    gornjiProzor.ispisi(izlaz);
                }
            } else if (naredba.startsWith("PI") && (naredba.length() > 2)) {
                int noviPI = Integer.parseInt(naredba.substring(3, naredba.length()));
                if (noviPI >= 0 && noviPI <= 100) {
                    setPi(noviPI);
                    inicijalizirajSustav(mjesta, aktuators, senzors, raspored);
                }
            } else if (naredba.startsWith("VF") && (naredba.length() > 2)) {
                linija = naredba.substring(3, naredba.length());
                if (!linija.isEmpty()) {
                    Zahtjev z1 = new ConcreteHandlerMjestaAktuator();
                    Zahtjev z2 = new ConcreteHandlerMjestaSenzor();
                    
                    z1.setNextChain(z2);
                    
                    z1.obradi(new Handler(linija));
                }
            }else {
                switch (naredba) {
                    case "S":
                        if (rd != null) {
                            linija = rd.statistika();
                            gornjiProzor.ispisi(linija);
                        }
                        break;
                    case "SP":
                        break;
                    case "VP":
                        break;                    
                    case "H":
                        gornjiProzor.ispisi(obradaPodataka.help());
                        break;    
                    case "I":
                        kraj = true;
                        break;

                }
            }
        } while (!kraj);
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

    public void setIzlaz(String izlaz) {
        this.izlaz = izlaz;
    }

    public void setGen(GeneratorSlucajnihBrojeva gen, long sjeme) {
        this.gen = gen;
        this.sjeme = sjeme;
        gen.postavi(sjeme);
    }

    public GeneratorSlucajnihBrojeva getGen() {
        return gen;
    }

    public List<Raspored> getRaspored() {
        return raspored;
    }    

    public void setBr(int br) {
        this.br = br;
    }

    public void setBrk(int brk) {
        this.brk = brk;
    }

    public void setBrs(int brs) {
        this.brs = brs;
    }

    public int getBr() {
        return br;
    }

    public int getBrk() {
        return brk;
    }

    public int getBrs() {
        return brs;
    }

    public List<Mjesto> getMjesta() {
        return mjesta;
    }

    public double getPi() {
        return pi;
    }

    public void setPi(int pi) {
        this.pi = pi;
        this.pi = this.pi / 100;
    }

    public void setTrajanje_ciklusa(int trajanje_ciklusa) {
        this.trajanje_ciklusa = trajanje_ciklusa;
    }

}
