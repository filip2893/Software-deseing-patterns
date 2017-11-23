/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visitor;

import composite.Aktuator;
import composite.Senzor;

/**
 *
 * @author Filip
 */
public interface Visitor {
    
    public void visit(Aktuator aktuator);
    public void visit(Senzor senzor);
    
}
