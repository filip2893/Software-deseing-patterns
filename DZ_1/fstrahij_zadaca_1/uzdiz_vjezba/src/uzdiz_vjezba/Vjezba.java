/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_vjezba;

import singleton.pr2.Singleton;

/**
 *
 * @author Filip
 */
public class Vjezba {
    private String naziv;

    public Vjezba() {
    }
    
    public String getNaziv() {
        naziv = Singleton.dajInstancu().getNaziv();
        return naziv;
    }
    
}
