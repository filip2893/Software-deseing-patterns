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
public class Memento {
    private String statistike;
    private List<Mjesto> mjesta;
    private List<Aktuator> aktuatori;
    private List<Senzor> senzori;
    private List<Object> rezervniUredjaji;
    private HashMap<Mjesto, Object> uredjajiNaPopravku;

    public Memento(String statistike, List<Mjesto> mjesta, List<Aktuator> aktuatori, List<Senzor> senzori, List<Object> rezervniUredjaji, HashMap<Mjesto, Object> uredjajiNaPopravku) {
        this.statistike = statistike;
        this.mjesta = new ArrayList<>(mjesta) ;
        this.aktuatori = new ArrayList<>(aktuatori);
        this.senzori = new ArrayList<>(senzori);
        this.rezervniUredjaji = new ArrayList<>(rezervniUredjaji);
        this.uredjajiNaPopravku = new HashMap<>(uredjajiNaPopravku);
    }   

    public String getStatistike() {
        return statistike;
    }

    public List<Mjesto> getMjesta() {
        return mjesta;
    }

    public List<Aktuator> getAktuatori() {
        return aktuatori;
    }

    public List<Senzor> getSenzori() {
        return senzori;
    }

    public List<Object> getRezervniUredjaji() {
        return rezervniUredjaji;
    }

    public HashMap<Mjesto, Object> getUredjajiNaPopravku() {
        return uredjajiNaPopravku;
    }
    
}
