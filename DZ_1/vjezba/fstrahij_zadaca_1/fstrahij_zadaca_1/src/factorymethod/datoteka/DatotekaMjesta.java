/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorymethod.datoteka;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import prototype.Mjesto;

/**
 *
 * @author Filip
 */
public class DatotekaMjesta implements Datoteka{

    String naziv;
    List<Mjesto> mjesta;
    BufferedReader reader;

    public DatotekaMjesta(String naziv) {
        this.naziv = naziv;
    }
    
    @Override
    public void otvoriKreirajDatoteku() {
        try {
            reader = new BufferedReader(new FileReader(this.naziv));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DatotekaMjesta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void procitajDatoteku() {
        String line;
        mjesta = new ArrayList<>();
        
        try {
            Mjesto mjesto;
            line = reader.readLine();
            boolean prvaLinija = true;
            while (line != null) {
                
                String[] atributi = line.split(";");                
                line = reader.readLine();
                if (prvaLinija) {
                        prvaLinija = false;
                }else{
                    mjesto = new Mjesto(atributi[0], 
                                        Integer.parseInt(atributi[1]), 
                                        Integer.parseInt(atributi[2]), 
                                        Integer.parseInt(atributi[3]));
                                       
                    mjesta.add(mjesto);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(DatotekaMjesta.class.getName()).log(Level.SEVERE, null, ex);
        }
        mjesta.stream().map((m) -> {
            System.out.println("");
            return m;
        }).map((m) -> {
            System.out.println("Naziv: " + m.getNaziv());
            return m;
        }).map((m) -> {
            System.out.println("Tip: " + m.getTip());
            return m;
        }).map((m) -> {
            System.out.println("Broj aktuatora: " + m.getBr_aktuatora());
            return m;
        }).forEachOrdered((m) -> {
            System.out.println("Broj senzora: " + m.getBr_senzora());            
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
            Logger.getLogger(DatotekaMjesta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Object> vratiPodatke() {
        otvoriKreirajDatoteku();
        procitajDatoteku();
        zatvoriDatoteku();
        List<Object> objects = new ArrayList<>(mjesta);
        return objects;
    }
    
}
