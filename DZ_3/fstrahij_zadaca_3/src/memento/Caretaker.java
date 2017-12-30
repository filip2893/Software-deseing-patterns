/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memento;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Filip
 */
public class Caretaker {
    
    ArrayList<Memento> spremljeniPodaci;

    public Caretaker() {
        this.spremljeniPodaci = new ArrayList<>();
    }
    
    public void dodajMemento(Memento m){
        spremljeniPodaci.add(m);
    }
    
    public List<Memento> vratiMemento(){
        return spremljeniPodaci;
    }
    
}
