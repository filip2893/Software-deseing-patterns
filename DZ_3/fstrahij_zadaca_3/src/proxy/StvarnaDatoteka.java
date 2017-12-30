/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import factorymethod.datoteka.DatotekaFactory;

/**
 *
 * @author Filip
 */
public class StvarnaDatoteka implements Izlaz{
    
    private String izlaz, izlaz_dat;

    public StvarnaDatoteka(String izlaz, String izlaz_dat) {
        this.izlaz = izlaz;
        this.izlaz_dat = izlaz_dat;
    }

    @Override
    public void zapisi() {
    
        DatotekaFactory df = new DatotekaFactory();
        factorymethod.datoteka.Datoteka datoteka = df.koristiDatoteku("Izlaz", izlaz_dat);
        datoteka.otvoriKreirajDatoteku();
        datoteka.zapisiUDatoteku(izlaz);
        
    }
    
}
