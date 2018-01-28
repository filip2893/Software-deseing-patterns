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
import MVC.model.composite.Mjesto;
import MVC.controller.singleton.ThingsOfFOI;

/**
 *
 * @author Filip
 */
public class DatotekaMjesta implements Datoteka {

    String naziv, izlaz = "";    
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
        
        try {
            Mjesto mjesto;
            line = reader.readLine();
            boolean prvaLinija = true;
            izlaz = tof.getIzlaz();
            izlaz += "\n-----------------\nUČITANA MJESTA \n-----------------\n";
            while (line != null) {

                String[] atributi = line.split(";");
                line = reader.readLine();
                
                if (atributi[1].isEmpty()) {
                    prvaLinija = true;
                }
                
                for (int i = 0; i < 4; i++) {
                    if (i == 1) {
                        continue;
                    }
                    try {
                        Integer.parseInt(atributi[i]);
                    } catch (NumberFormatException e) {
                        prvaLinija = true;
                        break;
                    }
                }
                
                if (prvaLinija || atributi.length > 5) {
                    prvaLinija = false;
                } else {                    

                    int id = Integer.parseInt(atributi[0]);

                    mjesto = new Mjesto(atributi[1],
                            Integer.parseInt(atributi[2]),
                            Integer.parseInt(atributi[3]),
                            Integer.parseInt(atributi[4]),
                            id);

                    mjesta.add(mjesto);

                    izlaz += "\n\nNaziv: " + atributi[1] 
                            + "\nTip: " + Integer.parseInt(atributi[2])
                            + "\nBroj senzora: " + Integer.parseInt(atributi[3])
                            + "\nBroj aktuatora: " + Integer.parseInt(atributi[4])
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
