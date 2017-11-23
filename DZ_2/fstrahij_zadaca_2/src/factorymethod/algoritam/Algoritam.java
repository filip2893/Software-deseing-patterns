/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorymethod.algoritam;

import java.util.List;
import composite.Mjesto;
import iterator.Mjesta;

/**
 *
 * @author Filip
 */
public interface Algoritam {
    
    Mjesto odrediMjesto(int element);
    Mjesto getMjesto();
    void setMjesta(Mjesta mjesta);
    
}
