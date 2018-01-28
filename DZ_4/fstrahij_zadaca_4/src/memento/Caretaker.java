/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memento;

import java.util.ArrayList;

/**
 *
 * @author Filip
 */
public class Caretaker {
    
    ArrayList<Memento> spremljeno = new ArrayList<Memento>();

    public Caretaker() {
    }
    
    public void dodajMemento(Memento m){
        spremljeno.add(m);
    }
    
    public Memento vratiMemento(int index){
        return spremljeno.get(index);
    }
    
    public Memento vratiZadnjiMemento(){
        return spremljeno.get(spremljeno.size() -1);
    }
    
}
