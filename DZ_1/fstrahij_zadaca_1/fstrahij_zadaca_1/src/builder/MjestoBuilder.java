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
public interface MjestoBuilder {
    
    Mjesto build();
    
    void setNaziv(String naziv);
    
    void setTip(int tip);
    
    void setBr_senzora(int br_senzora);
    
    void setBr_aktuatora(int br_aktuatora);
    
    MjestoBuilder postaviAktuator(Aktuator aktuator);
    
    MjestoBuilder postaviSenzor(Senzor senzor);
    
}
