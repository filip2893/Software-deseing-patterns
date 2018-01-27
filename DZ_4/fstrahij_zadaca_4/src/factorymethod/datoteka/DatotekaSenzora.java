/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorymethod.datoteka;

import MVC.model.composite.Senzor;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import MVC.controller.singleton.ThingsOfFOI;

/**
 *
 * @author Filip
 */
public class DatotekaSenzora implements Datoteka {

    String naziv, izlaz = "";
    List<Senzor> senzori;
    BufferedReader reader;

    public DatotekaSenzora(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public void otvoriKreirajDatoteku() {
        try {
            reader = new BufferedReader(new FileReader(this.naziv));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DatotekaSenzora.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void procitajDatoteku() {
        String line;
        senzori = new ArrayList<>();
        try {
            Senzor senzor;
            line = reader.readLine();
            boolean prvaLinija = true;
            while (line != null) {

                String[] atributi = line.split(";");
                line = reader.readLine();

                for (int i = 0; i < 6; i++) {
                    if (atributi[1].isEmpty()) {
                        prvaLinija = true;
                        break;
                    } else if (i == 0 || i == 2 || i == 3) {
                        try {
                            Integer.parseInt(atributi[i]);
                        } catch (NumberFormatException e) {
                            prvaLinija = true;
                            break;
                        }
                    }else if (i == 4 || i == 5) {
                        try {
                            Float.parseFloat(atributi[i]);
                        } catch (NumberFormatException e) {
                            prvaLinija = true;
                            break;
                        }
                    }
                }

                if (prvaLinija) {
                    prvaLinija = false;
                } else {
                    senzor = new Senzor(Integer.parseInt(atributi[0]),
                            atributi[1],
                            Integer.parseInt(atributi[2]),
                            Integer.parseInt(atributi[3]),
                            Float.parseFloat(atributi[4]),
                            Float.parseFloat(atributi[5]));

                    senzori.add(senzor);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(DatotekaSenzora.class.getName()).log(Level.SEVERE, null, ex);
        }

        ThingsOfFOI tof = ThingsOfFOI.getInstance();
        izlaz = tof.getIzlaz();
        izlaz += "\n-----------------\nUČITANI SENZORI \n-----------------\n";

        senzori.stream().map((s) -> {
            izlaz += "\n\nID: " + s.getIdModel();
            return s;
        }).map((s) -> {
            izlaz += "\nNaziv: " + s.getNaziv();
            return s;
        }).map((s) -> {
            izlaz += "\nTip: " + s.getTip();
            return s;
        }).map((s) -> {
            izlaz += "\nVrsta: " + s.getVrsta();
            return s;
        }).map((s) -> {
            izlaz += "\nMin: " + s.getMin();
            return s;
        }).forEachOrdered((s) -> {
            izlaz += "\nMax: " + s.getMax();
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
            Logger.getLogger(DatotekaSenzora.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Object> vratiPodatke() {
        otvoriKreirajDatoteku();
        procitajDatoteku();
        zatvoriDatoteku();
        List<Object> objects = new ArrayList<>(senzori);
        return objects;
    }

}
