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
public class Uzdiz_vjezba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Singleton sin = Singleton.dajInstancu();
        Singleton sin2 = Singleton.dajInstancu();
        sin.setNaziv("ovo je singleton");
        sin2.setNaziv("ovo je singleton 2");
        Vjezba vj = new Vjezba();
        System.out.println("naziv: "+ vj.getNaziv());
        // TODO code application logic here
    }
    
}
