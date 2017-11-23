/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visitor;

import composite.Aktuator;
import composite.Senzor;
import java.util.List;

/**
 *
 * @author Filip
 */
public class Znakovi implements Visitor{
     
    private int samoglasnici = 0, znakovi = 0;

    public Znakovi() {
    }    

    @Override
    public void visit(Aktuator aktuator) {
       zbroji(aktuator.getNaziv());
    }

    @Override
    public void visit(Senzor senzor) {
        zbroji(senzor.getNaziv());
    }
    
    public int zbroji(String naziv){
        for (int i = 0; i < naziv.length(); i++) {
            if (naziv.charAt(i) == 'a' || naziv.charAt(i) == 'e' || naziv.charAt(i) == 'i' 
                    || naziv.charAt(i) == 'o' || naziv.charAt(i) == 'u') {
                this.samoglasnici ++;
            }
            this.znakovi++;
        }
        return this.samoglasnici;
    }

    public int getSamoglasnici() {
        return samoglasnici;
    }

    public int getZnakovi() {
        return znakovi;
    }
    
}
