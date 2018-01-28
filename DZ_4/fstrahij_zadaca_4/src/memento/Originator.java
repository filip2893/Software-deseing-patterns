/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memento;

import MVC.model.composite.Aktuator;
import MVC.model.composite.Mjesto;
import MVC.model.composite.Senzor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Filip
 */
public class Originator {
    
    private String statistike;
    private List<Mjesto> mjesta;
    private List<Aktuator> aktuatori;
    private List<Senzor> senzori;
    private List<Object> rezervniUredjaji;
    private HashMap<Mjesto, Object> uredjajiNaPopravku;

    public Originator() {
    }
    
    public void set(String newStatistike, List<Mjesto> newMjesta,
                    List<Aktuator> newAktuatori, List<Senzor> newSenzori,
                    List<Object> newRezervniUredjaji, HashMap<Mjesto, Object> newUredjajiNaPopravku){
        this.statistike = newStatistike;
        this.mjesta = new ArrayList<>(newMjesta);
        this.aktuatori = new ArrayList<>(newAktuatori);
        this.senzori = new ArrayList<>(newSenzori);
        this.rezervniUredjaji = new ArrayList<>(newRezervniUredjaji);
        this.uredjajiNaPopravku = new HashMap<>(newUredjajiNaPopravku);
    }
    
    public Memento spremiUMemento(){
        return new Memento(statistike,
                            mjesta,
                            aktuatori,
                            senzori,
                            rezervniUredjaji,
                            uredjajiNaPopravku);
    }
    
    public String vratiStatistiku(Memento memento){
        statistike = memento.getStatistike();
        return statistike;
    }
    
    public List<Mjesto> vratiMjesta(Memento memento){
        mjesta =  new ArrayList<>(memento.getMjesta());
        return mjesta;
    }
    
    public List<Aktuator> vratiAktuatore(Memento memento){
        aktuatori = new ArrayList<>(memento.getAktuatori());
        return aktuatori;
    }
    
    public List<Senzor> vratiSenzore(Memento memento){
        senzori = new ArrayList<>(memento.getSenzori());
        return senzori;
    }
    
    public List<Object> vratiRezervneUredjaje(Memento memento){
        rezervniUredjaji = new ArrayList<>(memento.getRezervniUredjaji());
        return rezervniUredjaji;
    }
    
    public HashMap<Mjesto, Object> vratiUredjajeNaPopravku(Memento memento){
        uredjajiNaPopravku = new HashMap<>(memento.getUredjajiNaPopravku());
        return uredjajiNaPopravku;
    }
}
