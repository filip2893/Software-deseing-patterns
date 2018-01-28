/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipesAndFilters;

import MVC.model.composite.Mjesto;

/**
 *
 * @author Filip
 */
public interface Filter {
    
    public void setNextFilter(Filter next);
    public void zamijeni(Mjesto m, Object u);
    
}
