/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipesAndFilters;

import MVC.controller.singleton.ThingsOfFOI;
import MVC.model.composite.Aktuator;
import MVC.model.composite.Mjesto;

/**
 *
 * @author Filip
 */
public class FilterAktuatora implements Filter {

    private Filter next;
    private ThingsOfFOI tof = ThingsOfFOI.getInstance();

    @Override
    public void setNextFilter(Filter next) {
        this.next = next;
    }

    @Override
    public void zamijeni(Mjesto m, Object u) {
        if (u.getClass().toString().contains("Aktuator")) {
            int max = tof.getMax_br_ur() + 1;
            tof.setMax_br_ur(max);

            String ispis = m.getNaziv() + "(" + m.getId() + ");";
            int idAkt = 0;

            Aktuator a = (Aktuator) u;
            Object brisi = null;
            
            for (Object re : tof.getRezervniUredjaji()) {
                if (re.getClass().toString().contains("Aktuator")) {
                    Aktuator ak = (Aktuator) re;
                    if (a.getIdModel() == ak.getIdModel()) {
                       ak.setIdUredjaj(max);
                       m.add(ak);
                       tof.addAktuator(ak);
                       brisi = ak;
                       idAkt = ak.getIdUredjaj();
                       break;
                    }
                }
            }
            
            tof.getRezervniUredjaji().remove(brisi);
            m.remove(u);

            ispis += "(A)" + a.getIdUredjaj() + ";" + idAkt + "\n";
            
            tof.setIzlaz(ispis);            
        }else{
            this.next.zamijeni(m, u);
        }
    }

}
