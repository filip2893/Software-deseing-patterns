/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fstrahij_zadaca_1;

/**
 *
 * @author Filip
 */
public class Aktuator {
    
    private String naziv;
    private int tip, vrsta, min, max;  

    public Aktuator(String naziv, int tip, int vrsta, int min, int max) {
        this.naziv = naziv;
        this.tip = tip;
        this.vrsta = vrsta;
        this.min = min;
        this.max = max;
    }
    
    

    public String getNaziv() {
        return naziv;
    }

    public int getTip() {
        return tip;
    }

    public int getVrsta() {
        return vrsta;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public void setVrsta(int vrsta) {
        this.vrsta = vrsta;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }
    
}
