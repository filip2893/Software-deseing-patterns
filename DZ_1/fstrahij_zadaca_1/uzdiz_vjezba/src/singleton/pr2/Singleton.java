/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package singleton.pr2;

/**
 *
 * @author Filip
 */
public class Singleton {
    
    private static Singleton sin = new Singleton();
    
    private String naziv;

    private Singleton() {
    }
    
    public static Singleton dajInstancu(){
        return sin;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }  
            
}
