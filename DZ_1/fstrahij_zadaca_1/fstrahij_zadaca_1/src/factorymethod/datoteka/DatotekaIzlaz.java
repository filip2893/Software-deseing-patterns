/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorymethod.datoteka;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Filip
 */
public class DatotekaIzlaz implements Datoteka{

    String naziv;
    File file;
    BufferedWriter writer;

    public DatotekaIzlaz(String naziv) {
        this.naziv = naziv;
    }    
    
    @Override
    public void otvoriKreirajDatoteku() {
        file = new File(naziv);       
        try {
            writer = new BufferedWriter(new FileWriter(file));
        } catch (IOException ex) {
            Logger.getLogger(DatotekaIzlaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void procitajDatoteku() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void zapisiUDatoteku(String tekst) {
        otvoriKreirajDatoteku();
        try {
            writer.write(tekst);
        } catch (IOException ex) {
            Logger.getLogger(DatotekaIzlaz.class.getName()).log(Level.SEVERE, null, ex);
        }
        zatvoriDatoteku();
    }

    @Override
    public void zatvoriDatoteku() {
        try {
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(DatotekaIzlaz.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }

    @Override
    public List<Object> vratiPodatke() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
