/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package composite;

import java.util.ArrayList;
import java.util.List;
import composite.Aktuator;
import composite.Senzor;

/**
 *
 * @author Filip
 */
public class Mjesto implements Component{
    private String naziv;
    private int tip, br_senzora, br_aktuatora, id;
    private ArrayList uredjaji = new ArrayList();

    public Mjesto(String naziv, int tip, int br_senzora, int br_aktuatora, int id) {
        this.naziv = naziv;
        this.tip = tip;
        this.br_senzora = br_senzora;
        this.br_aktuatora = br_aktuatora;
        this.id = id;
    }
    
    

    public void setId(int id) {
        this.id = id;
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

    public int getId() {
        return id;
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

    public ArrayList getUredjaji() {
        return uredjaji;
    }    
    
    public  void add (Object o){    
        uredjaji.add(o);          
    }
    
    public void remove(Object o){
        uredjaji.remove(o);
    }

    @Override
    public void sastavi() {
        for (Object u : uredjaji) {            
            Component comp = (Component) u;
            comp.sastavi();            
        }        
    }
}
