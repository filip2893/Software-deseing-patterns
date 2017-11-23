/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package builder;

import fstrahij_zadaca_1.GeneratorSlucajnihBrojeva;
import java.util.List;
import prototype.Aktuator;
import prototype.Senzor;
import singleton.ThingsOfFOI;

/**
 *
 * @author Filip
 */
public class MjestoBuildDirector {

    private MjestoBuilder builder;

    public MjestoBuildDirector(final MjestoBuilder builder) {
        this.builder = builder;
    }

    public Mjesto construct(String naziv, int tip, int br_senzora, int br_aktuatora, List<Aktuator> aktuators, List<Senzor> senzors) {

        builder.setNaziv(naziv);
        builder.setTip(tip);
        builder.setBr_senzora(br_senzora);
        builder.setBr_aktuatora(br_aktuatora);

        int slucajniBroj = 0;

        ThingsOfFOI tof = ThingsOfFOI.getInstance();

        GeneratorSlucajnihBrojeva gen = tof.getGen();

        String izlaz = tof.getIzlaz() + "\n";
        izlaz += "---------------------------------------------------------------- \nUREDJAJI DODANI MJESTU ["
                + naziv + "] \n---------------------------------------------------------------- \n";

        for (Aktuator a : aktuators) {

            slucajniBroj = gen.getSlucajniBroj(2);

            if ((tip == a.getTip() || a.getTip() > 1) && slucajniBroj == 1) {

                builder.postaviAktuator(a);
                izlaz += "dodan je aktuator : " + a.getNaziv() + "\n";
            }

        }
        
        izlaz += "..............................................................\n";
        
        for (Senzor s : senzors) {
            slucajniBroj = gen.getSlucajniBroj(2);

            if ((tip == s.getTip() || s.getTip() > 1) && slucajniBroj == 1) {
                builder.postaviSenzor(s);
                izlaz += "dodan je senzor : " + s.getNaziv() + "\n";
            }
        }
        izlaz += "\n";
        tof.setIzlaz(izlaz);

        return builder.build();
    }

}
