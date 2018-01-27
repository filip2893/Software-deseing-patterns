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
    
    ArrayList<Memento> spremljeneStatistike = new ArrayList<Memento>();

    public Caretaker() {
    }
    
    public void dodajMemento(Memento m){
        spremljeneStatistike.add(m);
    }
    
    public Memento vratiMemento(int index){
        return spremljeneStatistike.get(index);
    }
    
    public Memento vratiZadnjMemento(){
        return spremljeneStatistike.get(spremljeneStatistike.size() -1);
    }
    
}
