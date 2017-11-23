/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builder;

import prototype.Aktuator;
import prototype.Senzor;

/**
 *
 * @author Filip
 */
public class MjestoBuilderImpl implements MjestoBuilder{
    private Mjesto mjesto;

    public MjestoBuilderImpl() {
        
        mjesto = new Mjesto();
        
    }
    
    

    @Override
    public Mjesto build() {
        return mjesto;
    }

    @Override
    public MjestoBuilder postaviAktuator(Aktuator aktuator) {
    
        mjesto.dodajAktuator(aktuator);
        return this;
    
    }

    @Override
    public MjestoBuilder postaviSenzor(Senzor senzor) {
    
        mjesto.dodajSenzor(senzor);
        return this;
        
    }

    @Override
    public void setNaziv(String naziv) {
    
        mjesto.setNaziv(naziv);
    
    }

    @Override
    public void setTip(int tip) {
    
        mjesto.setTip(tip);
        
    }

    @Override
    public void setBr_senzora(int br_senzora) {
        
        mjesto.setBr_senzora(br_senzora);
    
    }

    @Override
    public void setBr_aktuatora(int br_aktuatora) {
    
        mjesto.setBr_aktuatora(br_aktuatora);
        
    }

    
}
