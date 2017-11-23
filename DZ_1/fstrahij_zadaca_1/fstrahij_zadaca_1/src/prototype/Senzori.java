/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Filip
 */
public class Senzori implements Cloneable{
    
    private Senzor senzor;   

    public Senzori(Senzor senzor) {
        this.senzor = senzor;
    }

    public Senzor getSenzori() {
        return senzor;
    }
    
    @Override
    public Senzor clone() throws CloneNotSupportedException {
        Senzor temp = this.getSenzori();        
        
        return new Senzor (temp);
    }
    
    
    
}
