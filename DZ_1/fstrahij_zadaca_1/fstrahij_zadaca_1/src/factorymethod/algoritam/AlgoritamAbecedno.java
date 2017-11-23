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
import builder.Mjesto;

/**
 *
 * @author Filip
 */
public class AlgoritamAbecedno implements Algoritam{
    List<Mjesto> mjesta;   
    
    @Override
    public List<Mjesto> getMjesta() {
        return this.mjesta;
    }

    @Override
    public List<String> odrediIspravnostUredjaja(int element) {
        Collections.sort(mjesta, Comparator.comparing(Mjesto::getNaziv));
        
        this.mjesta = new ArrayList<>(mjesta);
        return null;
    }

    @Override
    public void setMjesta(List<Mjesto> mjesta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
