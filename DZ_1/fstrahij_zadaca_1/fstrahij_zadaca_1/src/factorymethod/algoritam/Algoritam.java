/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorymethod.algoritam;

import java.util.List;
import builder.Mjesto;

/**
 *
 * @author Filip
 */
public interface Algoritam {
    
    List<String> odrediIspravnostUredjaja(int element);
    List<Mjesto> getMjesta();
    void setMjesta(List<Mjesto> mjesta);
    
}
