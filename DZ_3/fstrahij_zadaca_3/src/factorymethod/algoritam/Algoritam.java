/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorymethod.algoritam;

import java.util.List;
import MVC.model.composite.Mjesto;

/**
 *
 * @author Filip
 */
public interface Algoritam {
    
    Mjesto odrediMjesto(int element);
    Mjesto getMjesto();
    void setMjesta(List<Mjesto> mjesta);
    
}
