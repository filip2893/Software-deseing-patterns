/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package singleton;

import factorymethod.datoteka.Datoteka;
import factorymethod.datoteka.DatotekaFactory;
import fstrahij_zadaca_1.GeneratorSlucajnihBrojeva;
import builder.Mjesto;
import fstrahij_zadaca_1.RadnaDretva;
import java.util.ArrayList;
import java.util.List;
import prototype.Aktuator;
import prototype.Senzor;

/**
 *
 * @author Filip
 */
public class ThingsOfFOI {
    
    private static volatile ThingsOfFOI thingsOfFOI;
    private String mjesta_dat, senzori_dat, aktuatori_dat, algoritam, izlaz_dat, izlaz = "";
    private long sjeme;
    private int trajanje_ciklusa, br_ciklusa; 
    private List<Aktuator> aktuators;
    private List<Senzor> senzors; 
    private List<Mjesto> mjesta;
    private GeneratorSlucajnihBrojeva gen;

    private ThingsOfFOI() {
    }
    
    public static ThingsOfFOI getInstance(){
        if (thingsOfFOI == null) {
            synchronized(ThingsOfFOI.class){
                if (thingsOfFOI == null) {                    
                    thingsOfFOI = new ThingsOfFOI();
                }
            }
        }
        return thingsOfFOI;
    }
    
    public void inicijalizirajPodatke(long sjeme, String mjesta_dat, String senzori_dat, String aktuatori_dat, String algoritam, String izlaz_dat, int trajanje_ciklusa, int br_ciklusa, String izlaz){    
        
        this.sjeme = sjeme;
        this.mjesta_dat = mjesta_dat;
        this.senzori_dat = senzori_dat;
        this.aktuatori_dat = aktuatori_dat;
        this.izlaz_dat = izlaz_dat;
        this.algoritam = algoritam;
        this.trajanje_ciklusa = trajanje_ciklusa;
        this.br_ciklusa = br_ciklusa;
        this.izlaz += izlaz; 
        this.aktuators = new ArrayList<>();
        this.senzors = new ArrayList<>();
        this.mjesta = new ArrayList<>();        
        
        postaviSjeme();
        ucitajDatoteke();      
        
        RadnaDretva rd = new RadnaDretva(trajanje_ciklusa, br_ciklusa, algoritam, mjesta);
        rd.start(); 
    }
    
    public void postaviSjeme(){
        gen = new GeneratorSlucajnihBrojeva();
        gen.postavi(sjeme);
    }
    
    public void ucitajDatoteke(){
        
        DatotekaFactory df = new DatotekaFactory();
        
        Datoteka datoteka = df.koristiDatoteku("Aktuatori", aktuatori_dat);
        datoteka.otvoriKreirajDatoteku();
        
        for (Object o : datoteka.vratiPodatke()) {
            aktuators.add((Aktuator)o);
        }
        
        datoteka = df.koristiDatoteku("Senzori", senzori_dat); 
        
        for (Object o : datoteka.vratiPodatke()) {
            senzors.add((Senzor)o);
        }     
        
        datoteka = df.koristiDatoteku("Mjesta", mjesta_dat); 
        
        
        for (Object o : datoteka.vratiPodatke()) {            
            mjesta.add((Mjesto)o);
        }  
        
        int slucajniBroj = 0; 
        
        /*for (Mjesto mj : mjesta) {
            for (Aktuator a : aktuators) {
                
                slucajniBroj = gen.getSlucajniBroj(2);
                
                if ((mj.getTip() == a.getTip() || a.getTip() > 1) && slucajniBroj == 1) {
                    mj.dodajAktuator(a); 
                    izlaz += "Mjestu: "+ mj.getNaziv() + "== je dodan aktuator:"+  a.getNaziv() +"\n";
                }
                
            }
                        
             for (Senzor s : senzors) {
                 slucajniBroj = gen.getSlucajniBroj(2);
                
                if ((mj.getTip() == s.getTip() || s.getTip() > 1) && slucajniBroj == 1) {
                    mj.dodajSenzor(s);
                    izlaz += "Mjestu: "+ mj.getNaziv() + "== je dodan senzor:"+  s.getNaziv() +"\n";
                }
             }
            izlaz += "---------------------------------------------------------------- \n" ;
              
        }*/
        
        kreirajIzlaznuDatoteku(df, datoteka);
    }
    
    public void kreirajIzlaznuDatoteku(DatotekaFactory df, Datoteka datoteka){
        System.out.print(izlaz);
        datoteka = df.koristiDatoteku("Izlaz", izlaz_dat);
        datoteka.otvoriKreirajDatoteku();
        datoteka.zapisiUDatoteku(izlaz);
    }

    public String getMjesta_dat() {
        return mjesta_dat;
    }

    public String getSenzori_dat() {
        return senzori_dat;
    }

    public String getAktuatori_dat() {
        return aktuatori_dat;
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

    public List<Mjesto> getMjesta() {
        return mjesta;
    }

    public void setIzlaz(String izlaz) {
        this.izlaz = izlaz;
    }

    public GeneratorSlucajnihBrojeva getGen() {
        return gen;
    }
    
}
