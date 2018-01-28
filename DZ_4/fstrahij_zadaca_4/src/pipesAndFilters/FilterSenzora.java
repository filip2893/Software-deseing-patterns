/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipesAndFilters;

import MVC.controller.singleton.ThingsOfFOI;
import MVC.model.composite.Mjesto;
import MVC.model.composite.Senzor;

/**
 *
 * @author Filip
 */
public class FilterSenzora implements Filter {

    private Filter next;
    private ThingsOfFOI tof = ThingsOfFOI.getInstance();

    @Override
    public void setNextFilter(Filter next) {
        this.next = next;
    }

    @Override
    public void zamijeni(Mjesto m, Object u) {
        if (u.getClass().toString().contains("Senzor")) {
            int max = tof.getMax_br_ur() + 1;
            tof.setMax_br_ur(max);

            String ispis = m.getNaziv() + "(" + m.getId() + ");";
            int idSen = 0;
            
            Senzor s = (Senzor) u;
            Object brisi = null;
            
            for (Object re : tof.getRezervniUredjaji()) {
                if (re.getClass().toString().contains("Senzor")) {
                    Senzor se = (Senzor) re;
                    if (s.getIdModel() == se.getIdModel()) {
                       se.setIdUredjaj(max);
                       m.add(se);
                       tof.addSenzor(se);
                       brisi = se;
                       idSen = se.getIdUredjaj();
                       break;
                    }
                }
            }
            
            tof.getRezervniUredjaji().remove(brisi);
            m.remove(u);

            ispis += "(S)" + s.getIdUredjaj() + ";" + idSen + "\n";  
            
            tof.setIzlaz(ispis);
            
        }else{
            this.next.zamijeni(m, u);
        }
    }

}
