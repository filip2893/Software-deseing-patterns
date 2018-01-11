/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fstrahij_zadaca_3;

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

    public RadnaDretva(int trajanje_ciklusa, int br_ciklusa) {
        this.trajanje_ciklusa = trajanje_ciklusa * 1000;
        this.br_ciklusa = br_ciklusa;
        tof = ThingsOfFOI.getInstance();
        mjestoUredjaji = new HashMap<String, Integer>();
        mjesta = new ArrayList<>(tof.getMjesta());
    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {

        int i = 0, element = 0;
        
        inicijalizirajUredjajeMjesta();
        
        izlaz = "PROVJERA STATUSA UREDJAJA\n-------------------------------\n";

        while (i < br_ciklusa) {

            izlaz += "\nCiklus br: " + (i+1) + "\n";

            if (element == mjesta.size()) {
                element = 0;
            }

            mjesto = odrediMjesto(element);

            provjeriIspravnostUredjaja(mjesto);

            i++;
            element++;
            try {
                sleep(trajanje_ciklusa);
            } catch (InterruptedException ex) {
                Logger.getLogger(RadnaDretva.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
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

    private void provjeriIspravnostUredjaja(Mjesto mjesto) {
        List<Object> uClone = new ArrayList<>();

        for (Object u : mjesto.getUredjaji()) {

            ispravan = obrada.execute((Uredjaj) u, 0.5);

            if (!ispravan) {
                if (u.getClass().toString().contains("Aktuator")) {
                    Aktuator a = (Aktuator) u;
                    neispravniUredjaji = "(" + mjesto.getNaziv() + ");(" + a.getNaziv() + ")";
                    izlaz += "\n" + neispravniUredjaji + "[-]";
                } else {
                    Senzor s = (Senzor) u;
                    neispravniUredjaji = "(" + mjesto.getNaziv() + ");(" + s.getNaziv() + ")";
                    izlaz += "\n" + neispravniUredjaji + "[-]";
                }

                if (mjestoUredjaji.containsKey(neispravniUredjaji)) {
                    int br = mjestoUredjaji.get(neispravniUredjaji);
                    br++;
                    if ((br % 3) == 0) {
                        uClone.add(u);
                        //mjesto.replace(u, o);
                        //mjesto.remove(u);
                        //mjesto.add(o);
                        //mjestoUredjaji.remove(neispravniUredjaji, u); 
                        //mjestoUredjaji.put(neispravniUredjaji, br);
                        izlaz += "\n##################\n ZAMIJENJENO:\n" + neispravniUredjaji + "\n##################";
                    } //else {
                    //br++;
                    mjestoUredjaji.put(neispravniUredjaji, br);
                    //}

                } else {
                    mjestoUredjaji.put(neispravniUredjaji, 1);
                }
            } else {
                
                if (u.getClass().toString().contains("Aktuator")) {
                    Aktuator a = (Aktuator) u;
                    ispravniUredjaji = "(" + mjesto.getNaziv() + ");(" + a.getNaziv() + ")";
                    izlaz += "\n" + ispravniUredjaji + "[+]";                    
                } else {
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
                } else {
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
    
    private Mjesto odrediMjesto(int element){
        int brojac = 0;
        Mjesto mje = null;
        
        for (Mjesto mj : tof.getMjesta()) {
            if (brojac == element) {
                mje = mj;
                break;
            }
            brojac++;
        }
        return mje;
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

}
