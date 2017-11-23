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
public class Aktuatori implements Cloneable{
    
    private List<Aktuator> aktuatori;

    public Aktuatori() {
        this.aktuatori = new ArrayList<>();
    }  

    public Aktuatori(List<Aktuator> aktuatori) {
        this.aktuatori = aktuatori;
    }

    public List<Aktuator> getAktuatori() {
        return aktuatori;
    }

    public void setAktuatori(Aktuator aktuator) {
        this.aktuatori.add(aktuator);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        List<Aktuator> temp = new ArrayList<>();
        
        for (Aktuator a : this.getAktuatori()) {
            temp.add(a);
        }
        
        return new Aktuatori (temp);
    }
    
}
