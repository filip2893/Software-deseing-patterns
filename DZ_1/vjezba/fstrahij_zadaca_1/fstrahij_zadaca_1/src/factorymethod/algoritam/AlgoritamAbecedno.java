/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorymethod.algoritam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import prototype.Mjesto;

/**
 *
 * @author Filip
 */
public class AlgoritamAbecedno implements Algoritam{
    List<Mjesto> mjesta;

    @Override
    public Mjesto odrediMjesto(List<Mjesto> mjesta, int element) {
        
        Collections.sort(mjesta, Comparator.comparing(Mjesto::getNaziv));
        
        this.mjesta = new ArrayList<>(mjesta);
        
        return null;
        
    }
    
    @Override
    public List<Mjesto> getMjesta() {
        return this.mjesta;
    }
    
}
