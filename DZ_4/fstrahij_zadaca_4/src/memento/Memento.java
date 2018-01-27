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
public class Memento {
    private String statistike;

    public Memento(String statistika) {
        this.statistike = statistika;
    }
    
    public String vratiSpremljenuStatistiku(){
        return statistike;
    }
}
