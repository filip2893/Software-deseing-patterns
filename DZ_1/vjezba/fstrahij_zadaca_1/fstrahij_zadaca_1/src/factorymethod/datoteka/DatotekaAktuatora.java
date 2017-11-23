/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorymethod.datoteka;

import fstrahij_zadaca_1.Aktuator;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Filip
 */
public class DatotekaAktuatora implements Datoteka{
    
    String naziv;
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
        
        aktuatori.stream().map((a) -> {
            System.out.println("");
            return a;
        }).map((a) -> {
            System.out.println("Naziv: " + a.getNaziv());
            return a;
        }).map((a) -> {
            System.out.println("Tip: " + a.getTip());
            return a;
        }).map((a) -> {
            System.out.println("Vrsta: " + a.getVrsta());
            return a;
        }).map((a) -> {
            System.out.println("Min: " + a.getMin());
            return a;
        }).forEachOrdered((a) -> {
            System.out.println("Max: " + a.getMax());
        });
        
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
