/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package singleton.pr1;

/**
 *  Lazy initialization za višedretveni rad
 * 
 * @author Filip
 */
public class Singleton {
    
    private String naziv;
    
    public static volatile Singleton sin;

    private Singleton() {
    }
    
    public static Singleton dajInstancu(){
        if (sin == null) {
            synchronized(Singleton.class){
                if (sin == null) {                    
                    sin = new Singleton();
                }
            }
        }
        return sin;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }    
    
}
