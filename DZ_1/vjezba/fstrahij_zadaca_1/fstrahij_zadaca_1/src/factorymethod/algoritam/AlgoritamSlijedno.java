/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorymethod.algoritam;

import java.util.ArrayList;
import java.util.List;
import prototype.Mjesto;

/**
 *
 * @author Filip
 */
public class AlgoritamSlijedno implements Algoritam{
    List<Mjesto> mjesta;

    @Override
    public Mjesto odrediMjesto(List<Mjesto> mjesta, int element) {
        
        this.mjesta = new ArrayList<>(mjesta);        
        
        return mjesta.get(element);
    }

    @Override
    public List<Mjesto> getMjesta() {
        return this.mjesta;
    }
    
}
