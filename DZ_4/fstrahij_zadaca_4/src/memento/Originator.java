/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memento;

/**
 *
 * @author Filip
 */
public class Originator {
    
    private String statistike;

    public Originator() {
    }
    
    public void set(String newStatistike){
        this.statistike = newStatistike;
    }
    
    public Memento spremiUMemento(){
        return new Memento(statistike);
    }
    
    public String vratiIzMementa(Memento memento){
        statistike = memento.vratiSpremljenuStatistiku();
        return statistike;
    }
    
    
}
