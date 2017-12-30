/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chainofresponsibility.dodatna;

/**
 *
 * @author Filip
 */
public interface Zahtjev {
    void setNextChain(Zahtjev next);
    void obradi(Handler naredba);    
}
