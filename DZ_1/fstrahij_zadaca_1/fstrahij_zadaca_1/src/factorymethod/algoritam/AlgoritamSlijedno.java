/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorymethod.algoritam;

import prototype.Aktuator;
import fstrahij_zadaca_1.GeneratorSlucajnihBrojeva;
import java.util.ArrayList;
import java.util.List;
import builder.Mjesto;

/**
 *
 * @author Filip
 */
public class AlgoritamSlijedno implements Algoritam{
    List<Mjesto> mjesta;
    Mjesto mjesto;


    @Override
    public List<Mjesto> getMjesta() {
        return this.mjesta;
    }

    @Override
    public List<String> odrediIspravnostUredjaja(int element) {
        List<String> neispravniUredjaji = new ArrayList<>();
        //generator slučajnih brojeva (1 - u redu, 0 - pogreška)
        
        mjesto =  this.mjesta.get(element);
        
        GeneratorSlucajnihBrojeva gen = new GeneratorSlucajnihBrojeva();              
        Integer ispravan; 
        
        for (Aktuator a : mjesto.getAktuatoriMjesta()) {
            ispravan = gen.getSlucajniBroj(2);
            if (ispravan == 0) { 
                neispravniUredjaji.add(a.getNaziv());
            }
        }
        return neispravniUredjaji;
    }

    @Override
    public void setMjesta(List<Mjesto> mjesta) {
        
        this.mjesta = new ArrayList<>(mjesta);        
        
    }
    
}
