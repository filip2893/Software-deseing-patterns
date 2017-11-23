/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builder;

import java.util.ArrayList;
import java.util.List;
import prototype.Aktuator;
import prototype.Senzor;

/**
 *
 * @author Filip
 */
public class Mjesto {
    String naziv;
    int tip, br_senzora, br_aktuatora;
    public List<Aktuator> aktuatoriMjesta;
    public List<Senzor> senzoriMjesta;    
    //String naziv, int tip, int br_senzora, int br_aktuatora
    public Mjesto() {
        /*this.naziv = naziv;
        this.tip = tip;
        this.br_senzora = br_senzora;
        this.br_aktuatora = br_aktuatora;*/
        this.aktuatoriMjesta = new ArrayList<>();
        this.senzoriMjesta = new ArrayList<>();
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public void setBr_senzora(int br_senzora) {
        this.br_senzora = br_senzora;
    }

    public void setBr_aktuatora(int br_aktuatora) {
        this.br_aktuatora = br_aktuatora;
    }
    
    public String getNaziv() {
        return naziv;
    }

    public int getTip() {
        return tip;
    }

    public int getBr_senzora() {
        return br_senzora;
    }

    public int getBr_aktuatora() {
        return br_aktuatora;
    }

    public List<Aktuator> getAktuatoriMjesta() {
        return aktuatoriMjesta;
    }

    public List<Senzor> getSenzoriMjesta() {
        return senzoriMjesta;
    }

    public void dodajAktuator(Aktuator aktuator){
        this.aktuatoriMjesta.add(aktuator);
    }    
    
    public void obrisiAktuator(Aktuator aktuator){
        this.aktuatoriMjesta.remove(aktuator);
    } 
    
    public void dodajSenzor(Senzor senzor){
        this.senzoriMjesta.add(senzor);
    }
    
    public void obrisiSenzor(Senzor senzor){
        this.senzoriMjesta.remove(senzor);
    }
    
    public Senzor getSenzor(String naziv){
        for (Senzor se : senzoriMjesta) {
            if (se.getNaziv().equals(naziv)) {
                return se;
            }
        }
        return null;
    }
    
    public Aktuator getAktuator(String naziv){
    
        for (Aktuator ak : aktuatoriMjesta) {
            if (ak.getNaziv().equals(naziv)) {
                return ak;
            }
        }       
        
        return null;
    }
    
}
