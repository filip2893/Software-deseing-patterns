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
public class Handler {
    
    private String naredba;

    public Handler(String naredba) {
        this.naredba = naredba;
    }

    public String getNaredba() {
        return naredba;
    }    
    
}
