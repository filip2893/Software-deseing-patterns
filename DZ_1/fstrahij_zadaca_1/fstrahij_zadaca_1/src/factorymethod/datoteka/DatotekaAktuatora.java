/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorymethod.datoteka;

import prototype.Aktuator;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import singleton.ThingsOfFOI;

/**
 *
 * @author Filip
 */
public class DatotekaAktuatora implements Datoteka{
    
    String naziv, izlaz="\n";
    List<Aktuator> aktuatori;
    BufferedReader reader;

    public DatotekaAktuatora(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public void otvoriKreirajDatoteku() {
        try {
            reader = new BufferedReader(new FileReader(this.naziv));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DatotekaAktuatora.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    @Override
    public void procitajDatoteku() {
        String line;
        aktuatori = new ArrayList<>();
        try {            
            Aktuator aktuator;
            line = reader.readLine();
            boolean prvaLinija = true;
            while (line != null) {
                
                String[] atributi = line.split(";");                
                line = reader.readLine();
                if (prvaLinija) {
                        prvaLinija = false;
                }else{
                    aktuator = new Aktuator(atributi[0], 
                                        Integer.parseInt(atributi[1]), 
                                        Integer.parseInt(atributi[2]), 
                                        Integer.parseInt(atributi[3]),
                                        Integer.parseInt(atributi[4]));
                                       
                    aktuatori.add(aktuator);
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(DatotekaAktuatora.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ThingsOfFOI tof = ThingsOfFOI.getInstance();
        izlaz += tof.getIzlaz();
        izlaz += "-----------------\nUČITANI AKTUATORI \n-----------------\n";
        
        aktuatori.stream().map((a) -> {
            izlaz += "\n";
            return a;
        }).map((a) -> {
            izlaz += "\nNaziv: " + a.getNaziv();
            return a;
        }).map((a) -> {
            izlaz += "\nTip: " + a.getTip();
            return a;
        }).map((a) -> {
            izlaz += "\nVrsta: " + a.getVrsta();
            return a;
        }).map((a) -> {
            izlaz += "\nMin: " + a.getMin();
            return a;
        }).forEachOrdered((a) -> {
            izlaz += "\nMax: " + a.getMax();
        });
        izlaz += "\n\n";
        tof.setIzlaz(izlaz);
    }

    @Override
    public void zapisiUDatoteku(String tekst) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void zatvoriDatoteku() {
        try {
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(DatotekaAktuatora.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Object> vratiPodatke() {
        otvoriKreirajDatoteku();
        procitajDatoteku();
        zatvoriDatoteku();
        List<Object> objects = new ArrayList<>(aktuatori);
        return objects;
    }
    
    
}
