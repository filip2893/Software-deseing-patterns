/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.model.composite;

import chainofresponsibility.Uredjaj;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author Filip
 */
public class Senzor implements Cloneable, Component, Uredjaj{

    private String naziv;
    private int tip, vrsta, idModel,idUredjaj, vrCj, i, ispravan, neispravan;
    private float vrDec, min, max;
    private boolean vrBool;
    private Senzor senzor; 

    public Senzor(Senzor senzor) {
        this.senzor = senzor;
    }    

    public Senzor(int idModel, String naziv, int tip, int vrsta, float min, float max) {
        this.idModel = idModel;
        this.naziv = naziv;
        this.tip = tip;
        this.vrsta = vrsta;
        this.min = min;
        this.max = max;
        this.ispravan = 0;
        this.neispravan = 0;
    }

    public int getIdUredjaj() {
        return idUredjaj;
    }

    public void setIdUredjaj(int idUredjaj) {
        this.idUredjaj = idUredjaj;
    }    

    public int getIdModel() {
        return idModel;
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

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

    public void setIdModel(int idModel) {
        this.idModel = idModel;
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

    public void setMin(float min) {
        this.min = min;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public Senzor getSenzor() {
        return senzor;
    }  

    public int getVrCj() {
        return vrCj;
    }

    public float getVrDec() {
        return vrDec;
    }

    public boolean isVrBool() {
        return vrBool;
    }

    public void setVrCj(int vrCj) {
        this.vrCj = vrCj;
    }

    public void setVrDec(float vrDec, int brDecimala) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        formatter.setMaximumFractionDigits(brDecimala);
        formatter.setMinimumFractionDigits(brDecimala);
        formatter.setRoundingMode(RoundingMode.HALF_UP);
        Float formatedFloat = new Float(formatter.format(vrDec));
        this.vrDec = formatedFloat;
    }

    public void setVrBool(boolean vrBool) {
        this.vrBool = vrBool;
    }
    
    public void odrediIspravnostUredjaja(boolean status) {
        if (status) {
            ispravan++;
        } else {
            neispravan++;
        }
    }
    

    @Override
    public Senzor clone() throws CloneNotSupportedException {
    
        Senzor temp = this.getSenzor();        
        
        return new Senzor (temp);
    
    }

    @Override
    public void sastavi() {
    }

    @Override
    public String obradi() {
        return "SENZOR";
    }    
    
}
