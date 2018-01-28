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
import java.util.HashMap;
import java.util.Map;
import memento.Caretaker;
import memento.Originator;
import pipesAndFilters.Filter;
import pipesAndFilters.FilterAktuatora;
import pipesAndFilters.FilterSenzora;

/**
 *
 * @author Filip
 */
public class ThingsOfFOI{

    private static volatile ThingsOfFOI thingsOfFOI;
    
    private String algoritam, izlaz_dat, izlaz = "";
    private long sjeme;
    private int trajanje_ciklusa, br_ciklusa, br, brk, brs, kmin, kmax, kpov, cp;
    private int max_br_ur;
    private double pi;
    private List<Aktuator> aktuators;
    private List<Senzor> senzors;
    private List<Mjesto> mjesta;
    private List<Raspored> raspored;
    private List<Object> rezervniUredjaji = new ArrayList<>();
    private GeneratorSlucajnihBrojeva gen = new GeneratorSlucajnihBrojeva();
    private GornjiProzor gornjiProzor;
    private DonjiProzor donjiProzor;
    private HashMap<Mjesto, Object> uredjajiNaPopravku = new HashMap<>();
    
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

        for (Mjesto mjesto : this.mjesta) {

            ArrayList uredjaji = mjesto.getUredjaji();
            
            if (uredjaji.isEmpty()) {
                continue;
            }

            izlaz += "\n####################################\ninicijalizacija mjesta[" + mjesto.getNaziv().toUpperCase() + "]\n------------------------------------";

            for (Object u : uredjaji) {
                ispravan = obrada.execute((Uredjaj) u, this.pi);

                if (ispravan) {
                    if (u.getClass().toString().contains("Aktuator")) {
                        Aktuator a = (Aktuator) u;
                        izlaz += "\nAKTUATOR: " + a.getNaziv() + "[+]";
                    } else if(u.getClass().toString().contains("Senzor")){
                        Senzor s = (Senzor) u;
                        izlaz += "\nSENZOR: " + s.getNaziv() + "[+]";
                    }
                } else {
                    uredjajiNaPopravku.put(mjesto, u);
                }
            }
        }
        dajZamjenu();
    }

    public void ispisiPodatke(String podaci) {
        gornjiProzor = new GornjiProzor(br - brk, brs);
        donjiProzor = new DonjiProzor(br - brk + 1, brk);
        gornjiProzor.ocistiProzor();
        gornjiProzor.inicijaliziraj();
        gornjiProzor.ispisi(podaci);
    }

    public void prikaziProzore() {
        ObradaPodataka obradaPodataka = new ObradaPodataka();;
        RadnaDretva rd = null;
        Caretaker caretaker = new Caretaker();
        Originator originator = new Originator();

        //gornjiProzor.ocistiProzor();
        //gornjiProzor.inicijaliziraj();
        String linija = "", id = "", vraceniPodaci = "";
        boolean kraj = false, svp = false, dretva = false;

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
                if (naredba[1].isEmpty() && isItInteger(naredba[1]) == false) {
                    break;
                }
                int brc = Integer.parseInt(naredba[1]);
                if (brc > 0 && brc <= 100) {
                    dretva = true;
                    this.br_ciklusa = brc;
                    rd = new RadnaDretva(trajanje_ciklusa, br_ciklusa);
                    rd.start();
                    while (rd.isAlive()) {}
                    gornjiProzor.ispisi(izlaz);
                    svp = false;
                }else{
                    gornjiProzor.ispisi("krivi unos");
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
                        if (rd != null && dretva) {
                            linija = rd.statistika();
                            originator.set(linija, mjesta, aktuators, senzors, rezervniUredjaji, uredjajiNaPopravku);
                            caretaker.dodajMemento(originator.spremiUMemento());                            
                            gornjiProzor.ispisi("Podaci su spremljeni");                            
                        }
                        break;
                    case "VP":
                        if (rd != null && dretva) {
                            svp = true;
                            vraceniPodaci = originator.vratiStatistiku(caretaker.vratiZadnjiMemento());
                            this.mjesta = new ArrayList<>(originator.vratiMjesta(caretaker.vratiZadnjiMemento()));
                            this.aktuators =  new ArrayList<>(originator.vratiAktuatore(caretaker.vratiZadnjiMemento()));
                            this.senzors =  new ArrayList<>(originator.vratiSenzore(caretaker.vratiZadnjiMemento()));
                            this.rezervniUredjaji =  new ArrayList<>(originator.vratiRezervneUredjaje(caretaker.vratiZadnjiMemento()));
                            this.uredjajiNaPopravku =  new HashMap<>(originator.vratiUredjajeNaPopravku(caretaker.vratiZadnjiMemento()));
                            
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
    
    public void dajZamjenu() {  
        
        Filter filterAktuatora = new FilterAktuatora();
        Filter filterSenzora = new FilterSenzora();
        
        filterAktuatora.setNextFilter(filterSenzora);
    
        String zamjenaIspis = "MJESTO;ID_STARI;ID_NOVI;\n";
        zamjenaIspis +="------;--------;-------;\n";
        for (Map.Entry<Mjesto, Object> map : uredjajiNaPopravku.entrySet()) {

            filterAktuatora.zamijeni(map.getKey(), map.getValue());//obradaPodataka.zamijeniUredjaj(map.getKey(), map.getValue());

            zamjenaIspis += izlaz;

        }
        
        //ispisiPodatke(zamjenaIspis);
        
    }
    
    public void dajPopravljeneUredjaje(){
    
        for (Map.Entry<Mjesto, Object> map : uredjajiNaPopravku.entrySet()) {
            map.getKey().add(map.getValue());
        }
        
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

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }    
    
    public int getMax_br_ur() {
        return max_br_ur;
    }

    public void setMax_br_ur(int max_br_ur) {
        this.max_br_ur = max_br_ur;
    }

    public List<Object> getRezervniUredjaji() {
        return rezervniUredjaji;
    }

    public void setRezervniUredjaji(List<Object> rezervniUredjaji) {
        this.rezervniUredjaji = rezervniUredjaji;
    }
    
    public void addAktuator(Aktuator akt){
        aktuators.add(akt);
    }
    
    public void addSenzor(Senzor sen){
        senzors.add(sen);
    }
}
