/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memento;

import MVC.model.composite.Mjesto;

/**
 *
 * @author Filip
 */
public class Memento {
    
    private Mjesto mjesto;
    private Object uredjaj;

    public Memento(Mjesto mjesto, Object uredjaj) {
        this.mjesto = mjesto;
        this.uredjaj = uredjaj;
    }
    
    public Mjesto vratiSpremljenaMjesta(){
        return mjesto;
    }
    
    public Object vratiSpremljeneUredjaje(){
        return uredjaj;
    }
}
