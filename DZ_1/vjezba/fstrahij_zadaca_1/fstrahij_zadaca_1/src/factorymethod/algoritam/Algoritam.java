/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorymethod.algoritam;

import java.util.List;
import prototype.Mjesto;

/**
 *
 * @author Filip
 */
public interface Algoritam {
    Mjesto odrediMjesto(List<Mjesto> mjesta, int element);
    List<Mjesto> getMjesta();
}
