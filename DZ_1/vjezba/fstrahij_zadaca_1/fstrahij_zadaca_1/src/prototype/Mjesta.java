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
public class Mjesta implements Cloneable{
    
    private List<Mjesto> mjesta;

    public Mjesta() {
        this.mjesta = new ArrayList<>();
    }    
    
    public Mjesta(List<Mjesto> mjesta) {
        this.mjesta = mjesta;
    }

    public List<Mjesto> getMjesta() {
        return mjesta;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        List<Mjesto> temp = new ArrayList<>();
        for (Mjesto m : this.getMjesta()) {
            temp.add(m);
        }
        return new Mjesta (temp);
    }
    
    
    
}
