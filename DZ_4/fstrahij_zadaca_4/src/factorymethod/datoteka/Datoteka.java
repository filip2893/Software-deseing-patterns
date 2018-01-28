/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorymethod.datoteka;

import java.util.List;

/**
 *
 * @author Filip
 */
public interface Datoteka {
    
    void otvoriKreirajDatoteku();
    void procitajDatoteku();
    void zapisiUDatoteku(String tekst);
    void zatvoriDatoteku();
    List<Object> vratiPodatke();
    
}
