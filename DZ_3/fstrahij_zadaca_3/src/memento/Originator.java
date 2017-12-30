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
public class Originator {

    private Mjesto mjesto;
    private Object uredjaj;

    public Originator() {
    }

    public void set(Mjesto newMjesto, Object newUredjaj) {
        this.mjesto = newMjesto;
        this.uredjaj = newUredjaj;
    }

    public Memento spremiUMemento() {
        return new Memento(mjesto, uredjaj);
    }

    public Mjesto vratiIzMementaMjesta(Memento memento) {
        mjesto = memento.vratiSpremljenaMjesta();
        return mjesto;
    }
    
    public Object vratiIzMementaUredjaje(Memento memento) {
        uredjaj = memento.vratiSpremljeneUredjaje();
        return uredjaj;
    }

}
