/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.controller.singleton;

import MVC.model.ObradaPodataka;
import chainofresponsibility.Obrada;
import chainofresponsibility.Uredjaj;
import fstrahij_zadaca_4.GeneratorSlucajnihBrojeva;
import MVC.model.composite.Mjesto;
import fstrahij_zadaca_4.RadnaDretva;
import java.util.ArrayList;
import java.util.List;
import MVC.model.composite.Aktuator;
import MVC.model.composite.Senzor;
import MVC.model.Raspored;
import MVC.view.DonjiProzor;
import MVC.view.GornjiProzor;
import memento.Caretaker;
import memento.Originator;

/**
 *
 * @author Filip
 */
public class ThingsOfFOI {

    private static volatile ThingsOfFOI thingsOfFOI;
    private String algoritam, izlaz_dat, izlaz = "";
    private long sjeme;
    private int trajanje_ciklusa, br_ciklusa, br, brk, brs, kmin, kmax, kpov, cp;
    private double pi;
    private List<Aktuator> aktuators;
    private List<Senzor> senzors;
    private List<Mjesto> mjesta;
    private List<Raspored> raspored;
    private GeneratorSlucajnihBrojeva gen = new GeneratorSlucajnihBrojeva();
    private GornjiProzor gornjiProzor;
    private DonjiProzor donjiProzor;

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
        //gornjiProzor = new GornjiProzor(br - brk, brs);
        //donjiProzor = new DonjiProzor(br - brk + 1, brk);
        //gornjiProzor.ocistiProzor();
        //gornjiProzor.inicijaliziraj();
        //RadnaDretva rd = new RadnaDretva(trajanje_ciklusa, br_ciklusa, algoritam);
        //rd.start();
    }

    public void ispisPodatke(String podaci) {
        gornjiProzor = new GornjiProzor(br - brk, brs);
        donjiProzor = new DonjiProzor(br - brk + 1, brk);
        gornjiProzor.ocistiProzor();
        gornjiProzor.inicijaliziraj();
        gornjiProzor.ispisi(podaci);
    }

    public void prikaziProzore() {

        ObradaPodataka obradaPodataka = new ObradaPodataka();
        RadnaDretva rd = null;
        Caretaker caretaker = new Caretaker();
        Originator originator = new Originator();

        //gornjiProzor.ocistiProzor();
        //gornjiProzor.inicijaliziraj();
        String linija = "", id = "", vraceniPodaci = "";
        boolean kraj = false, svp = false;

        do {
            String opcija = donjiProzor.cekajNaredbu(gornjiProzor.nastavak);
            String[] naredba = opcija.split(" ");

            if (naredba[0].toUpperCase().equals("N") && gornjiProzor.nastavak == true) {
                GornjiProzor.brojac = 1;
                gornjiProzor.ocistiProzor();
                gornjiProzor.ispisi(null);
            } else if (naredba[0].equals("M") && naredba.length > 1) {
                if (naredba[1].isEmpty() && !isItInteger(naredba[1])) {
                    break;
                }
                id = naredba[1];
                linija = obradaPodataka.mjestaObrada(id);
                if (linija != null) {
                    gornjiProzor.ispisi(linija);
                } else {
                    //gornjiProzor.ocistiProzor();
                    gornjiProzor.ispisi("Mjesto ne postoji\n");
                }
            } else if (naredba[0].equals("S") && naredba.length > 1) {
                if (naredba[1].isEmpty() && !isItInteger(naredba[1])) {
                    break;
                }
                id = naredba[1];
                linija = obradaPodataka.senzoriObrada(id);
                if (linija != null) {
                    gornjiProzor.ispisi(linija);
                } else {
                    gornjiProzor.ispisi("Senzor ne postoji\n");
                }
            } else if (naredba[0].equals("A") && naredba.length > 1) {
                if (naredba[1].isEmpty() && !isItInteger(naredba[1])) {
                    break;
                }
                id = naredba[1];
                linija = obradaPodataka.aktuatoriObrada(id);
                if (linija != null) {
                    gornjiProzor.ispisi(linija);
                } else {
                    gornjiProzor.ispisi("Aktuator ne postoji\n");
                }
            } else if (naredba[0].equals("C") && naredba.length > 1) {
                if (naredba[1].isEmpty() && !isItInteger(naredba[1])) {
                    break;
                }
                int brc = Integer.parseInt(naredba[1]);
                if (brc > 0 && brc <= 100) {
                    this.br_ciklusa = brc;
                    rd = new RadnaDretva(trajanje_ciklusa, br_ciklusa);
                    rd.start();
                    while (rd.isAlive()) {}
                    gornjiProzor.ispisi(izlaz);
                    svp = false;
                }
            } else if (naredba[0].equals("PI") && naredba.length > 1) {
                if (naredba[1].isEmpty() && !isItInteger(naredba[1])) {
                    break;
                }
                int noviPI = Integer.parseInt(naredba[1]);
                if (noviPI >= 0 && noviPI <= 100) {
                    setPi(noviPI);
                    inicijalizirajSustav(mjesta, aktuators, senzors, raspored);
                    linija = "Sustavu je dodijeljen PI: " + noviPI + "\n Sustav je ponovno inicijaliziran";
                    gornjiProzor.ispisi(linija);
                }
            } else if (naredba[0].equals("SM") && naredba.length > 1) {
                if (naredba[1].isEmpty() && !isItInteger(naredba[1])) {
                    break;
                }
                id = naredba[1];
                linija = obradaPodataka.strukturaMjesta(id);
                if (linija != null) {
                    gornjiProzor.ispisi(linija);
                } else {
                    //gornjiProzor.ocistiProzor();
                    gornjiProzor.ispisi("Mjesto ne postoji\n");
                }
            } else if (naredba[0].equals("TS") && naredba.length > 1) {
                if (naredba[1].isEmpty() && !isItInteger(naredba[1])) {
                    break;
                }
                id = naredba[1];
                linija = obradaPodataka.kolekcijaSenzor(id);
                if (linija != null) {
                    gornjiProzor.ispisi(linija);
                } else {
                    gornjiProzor.ispisi("Senzor ne postoji\n");
                }
            } else if (naredba[0].equals("TA") && naredba.length >= 1) {
                if (naredba[1].isEmpty() && !isItInteger(naredba[1])) {
                    break;
                }
                id = naredba[1];
                linija = obradaPodataka.kolekcijaAktuator(id);
                if (linija != null) {
                    gornjiProzor.ispisi(linija);
                } else {
                    gornjiProzor.ispisi("Aktuator ne postoji\n");
                }
            } else if (naredba[0].equals("CP") && naredba.length > 1) {
                if (naredba[1].isEmpty() && !isItInteger(naredba[1])) {
                    break;
                }
                cp = Integer.parseInt(naredba[1]);

                if (cp >= 1 && cp <= 99) {
                    gornjiProzor.ispisi("uredjaj se vraca na raspolaganje nakon: " + cp);
                } else {
                    gornjiProzor.ispisi("CP mora biti u rasponu 1 - 99");
                }
            } else {
                switch (naredba[0]) {
                    case "S":
                        if (rd != null) {
                            if (!svp) {
                                linija = rd.statistika();
                            } else {
                                linija = vraceniPodaci;
                            }
                            gornjiProzor.ispisi(linija);
                        }
                        break;
                    case "SP":
                        if (rd != null) {
                            linija = rd.statistika();
                            originator.set(linija);
                            caretaker.dodajMemento(originator.spremiUMemento());
                            gornjiProzor.ispisi("Podaci su spremljeni");
                        }
                        break;
                    case "VP":
                        if (rd != null) {
                            svp = true;
                            vraceniPodaci = originator.vratiIzMementa(caretaker.vratiZadnjMemento());
                            gornjiProzor.ispisi("Podaci su vraceni");
                        }
                        break;
                    case "H":
                        gornjiProzor.ispisi(obradaPodataka.help());
                        break;
                    case "I":
                        donjiProzor.ocistiProzor();
                        gornjiProzor.ocistiProzor();
                        kraj = true;
                        break;
                    default:
                        gornjiProzor.ispisi("krivi unos");
                }
            }
        } while (!kraj);
    }

    private boolean isItInteger(String number) {
        try {
            int parsedNumber = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
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

    public int getKmin() {
        return kmin;
    }

    public int getKmax() {
        return kmax;
    }

    public int getKpov() {
        return kpov;
    }

    public void setKmin(int kmin) {
        this.kmin = kmin;
    }

    public void setKmax(int kmax) {
        this.kmax = kmax;
    }

    public void setKpov(int kpov) {
        this.kpov = kpov;
    }
}
