/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.model.composite;

import chainofresponsibility.Uredjaj;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author Filip
 */
public class Aktuator implements Cloneable, Component, Uredjaj{

    private String naziv;
    private int tip, vrsta, min, max, idModel,idUredjaj, vrCj, ispravan, neispravan;
    private float vrDec;
    private boolean vrBool;

    private Aktuator aktuator;
    private List<Senzor> senzori = new ArrayList();

    public Aktuator(Aktuator aktuator) {
        this.aktuator = aktuator;
    }

    public Aktuator(int idModel, String naziv, int tip, int vrsta, int min, int max) {
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

    public int getMin() {
        return min;
    }

    public int getMax() {
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

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public Aktuator getAktuator() {
        return aktuator;
    }

    public void add(Senzor s) {
        senzori.add(s);
    }

    public List<Senzor> getSenzori() {
        return senzori;
    }

    public int getVrCj() {
        return vrCj;
    }

    public float getVrDec() {
        return vrDec;
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

    public boolean isVrBool() {
        return vrBool;
    }

    public void setVrDec(float vrDec) {
        this.vrDec = vrDec;
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
    public Aktuator clone() throws CloneNotSupportedException {
        Aktuator temp = this.getAktuator();

        return new Aktuator(temp);

    }

    @Override
    public void sastavi() {
        for (Object s : senzori) {
            Component comp = (Component) s;
            comp.sastavi();
        }
    }

    @Override
    public String obradi() {
        return "AKTUATOR";
    }

}
