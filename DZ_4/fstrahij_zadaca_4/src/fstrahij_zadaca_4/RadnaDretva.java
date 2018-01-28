/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fstrahij_zadaca_4;

import chainofresponsibility.Obrada;
import chainofresponsibility.Uredjaj;
import MVC.model.composite.Aktuator;
import MVC.model.composite.Mjesto;
import MVC.model.composite.Senzor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import MVC.controller.singleton.ThingsOfFOI;
import pipesAndFilters.Filter;
import pipesAndFilters.FilterAktuatora;
import pipesAndFilters.FilterSenzora;

/**
 *
 * @author Filip
 */
public class RadnaDretva extends Thread {

    private int trajanje_ciklusa = 0, br_ciklusa = 0;
    private String neispravniUredjaji = "", ispravniUredjaji = "", izlaz = "";
    private List<Mjesto> mjesta;
    private Obrada obrada = new Obrada();
    private HashMap<String, Integer> mjestoUredjaji;
    private Mjesto mjesto;
    private boolean ispravan;
    ThingsOfFOI tof;
    private HashMap<Mjesto, Object> uredjajiNaPopravku;

    public RadnaDretva(int trajanje_ciklusa, int br_ciklusa) {
        this.trajanje_ciklusa = trajanje_ciklusa * 1000;
        this.br_ciklusa = br_ciklusa;
        tof = ThingsOfFOI.getInstance();
        mjestoUredjaji = new HashMap<>();
        mjesta = new ArrayList<>(tof.getMjesta());
        uredjajiNaPopravku = new HashMap<>();
    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {

        int i = 0;
        int cp;
        
        inicijalizirajUredjajeMjesta();
        
        izlaz = "PROVJERA STATUSA UREDJAJA\n-------------------------------\n";

        while (i < br_ciklusa) {

            izlaz += "\nCiklus br: " + (i+1) + "\n";
            
            for (int element = 0; element < mjesta.size(); element++) {
                mjesto = tof.getMjesta().get(element);
                provjeriIspravnostUredjaja(mjesto);
            }
            i++;
            
            cp = tof.getCp();
            cp--;
            tof.setCp(cp);
            if (cp == 0) {
                tof.dajPopravljeneUredjaje();
                
                for (Map.Entry<Mjesto, Object> map : uredjajiNaPopravku.entrySet()) {
                    map.getKey().add(map.getValue());
                }
            }            
            
            try {
                sleep(trajanje_ciklusa);
            } catch (InterruptedException ex) {
                Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        dajZamjenu();        
        tof.setIzlaz(izlaz);
        //zapisiUDatoteku();
        //this.interrupt();
    }

    public String statistika() {
        String linija = "NAZIV_MJESTA;NAZIV_UREDJAJA;BROJ_GRESKI\n";
        for (Map.Entry<String, Integer> entry : mjestoUredjaji.entrySet()) {
            linija += entry.getKey() + ";" + entry.getValue() + "\n";
        }
        return linija;
    }
    
    private void inicijalizirajUredjajeMjesta(){
        for (Mjesto mj : mjesta) {
            for(Object u : mj.getUredjaji()){
                if (u.getClass().toString().contains("Aktuator")) {
                    Aktuator a = (Aktuator) u;
                    ispravniUredjaji = "(" + mj.getNaziv() + ");(" + a.getNaziv()+ ")";
                } else {
                    Senzor s = (Senzor) u;
                    ispravniUredjaji = "(" + mj.getNaziv() + ");(" + s.getNaziv()+ ")";
                }
                mjestoUredjaji.put(ispravniUredjaji, 0);
            }
        }
    }
    
    public void dajZamjenu() {  
        
        Filter filterAktuatora = new FilterAktuatora();
        Filter filterSenzora = new FilterSenzora();
        
        filterAktuatora.setNextFilter(filterSenzora);
    
        String zamjenaIspis = "MJESTO;ID_STARI;ID_NOVI;\n";
        zamjenaIspis +="------;--------;-------;\n";
        for (Map.Entry<Mjesto, Object> map : uredjajiNaPopravku.entrySet()) {

            filterAktuatora.zamijeni(map.getKey(), map.getValue());//obradaPodataka.zamijeniUredjaj(map.getKey(), map.getValue());

            zamjenaIspis += tof.getIzlaz();

        }
        
        izlaz += "\n" + zamjenaIspis;
        
    }

    private void provjeriIspravnostUredjaja(Mjesto mjesto) {
        List<Object> uClone = new ArrayList<>();

        for (Object u : mjesto.getUredjaji()) {

            ispravan = obrada.execute((Uredjaj) u, tof.getPi());

            if (!ispravan) {
                if (u.getClass().toString().contains("Aktuator")) {
                    Aktuator a = (Aktuator) u;
                    neispravniUredjaji = "(" + mjesto.getNaziv() + ");(" + a.getNaziv() + ")";
                    uredjajiNaPopravku.put(mjesto, u);
                    izlaz += "\n" + neispravniUredjaji + "[-]";
                } else if(u.getClass().toString().contains("Senzor")){
                    Senzor s = (Senzor) u;
                    neispravniUredjaji = "(" + mjesto.getNaziv() + ");(" + s.getNaziv() + ")";
                    izlaz += "\n" + neispravniUredjaji + "[-]";
                    uredjajiNaPopravku.put(mjesto, u);
                }

                if (mjestoUredjaji.containsKey(neispravniUredjaji)) {
                    int br = mjestoUredjaji.get(neispravniUredjaji);
                    br++;
                    mjestoUredjaji.put(neispravniUredjaji, br);

                } else {
                    mjestoUredjaji.put(neispravniUredjaji, 1);
                }
            } else {
                
                if (u.getClass().toString().contains("Aktuator")) {
                    Aktuator a = (Aktuator) u;
                    ispravniUredjaji = "(" + mjesto.getNaziv() + ");(" + a.getNaziv() + ")";
                    izlaz += "\n" + ispravniUredjaji + "[+]";                    
                } else if(u.getClass().toString().contains("Senzor")){
                    Senzor s = (Senzor) u;
                    ispravniUredjaji = "(" + mjesto.getNaziv() + ");(" + s.getNaziv() + ")";
                    izlaz += "\n" + ispravniUredjaji + "[+]";
                }
                mjestoUredjaji.put(ispravniUredjaji, 0);
            }
        }
        if (!uClone.isEmpty()) {
            for (Object u : uClone) {
                Object c = u;
                if (u.getClass().toString().contains("Aktuator")) {
                    Aktuator a = (Aktuator) u;
                    try {
                        c = a.clone();
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if(u.getClass().toString().contains("Senzor")){
                    Senzor s = (Senzor) u;
                    try {
                        c = s.clone();
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                mjesto.remove(u);
                mjesto.add(u);
            }
        }        
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

}
