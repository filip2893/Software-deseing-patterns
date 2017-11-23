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
import composite.Mjesto;
import fstrahij_zadaca_2.GeneratorSlucajnihBrojeva;
import composite.Aktuator;
import composite.Senzor;
import java.io.LineNumberReader;
import singleton.ThingsOfFOI;

/**
 *
 * @author Filip
 */
public class DatotekaMjesta implements Datoteka {

    String naziv, izlaz = "";
    ;
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

        ThingsOfFOI tof = ThingsOfFOI.getInstance();

        //List<Aktuator> aktuatori = tof.getAktuators();
        //List<Senzor> senzori = tof.getSenzors();
        try {
            Mjesto mjesto;
            line = reader.readLine();
            boolean prvaLinija = true;
            izlaz = tof.getIzlaz();
            izlaz += "\n-----------------\nUČITANA MJESTA \n-----------------\n";
            while (line != null) {

                String[] atributi = line.split(";");
                line = reader.readLine();
                if (prvaLinija) {
                    prvaLinija = false;
                } else {
                    GeneratorSlucajnihBrojeva gen = tof.getGen();

                    int id = gen.dajSlucajniBroj(1, 1000);
                    
                    mjesto = new Mjesto(atributi[0],
                            Integer.parseInt(atributi[1]),
                            Integer.parseInt(atributi[2]),
                            Integer.parseInt(atributi[3]),
                            id);                    

                    mjesta.add(mjesto);
                    
                    izlaz += "\n\nNaziv: " + atributi[0] + "\nTip: " + Integer.parseInt(atributi[1]) 
                            + "\nBroj senzora: " + Integer.parseInt(atributi[2])
                            + "\nBroj aktuatora: " + Integer.parseInt(atributi[3])
                            + "\nID: " + id + "\n";
                    
                }
            }
            tof.setIzlaz(izlaz);
        } catch (IOException ex) {
            Logger.getLogger(DatotekaMjesta.class.getName()).log(Level.SEVERE, null, ex);
        }

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
