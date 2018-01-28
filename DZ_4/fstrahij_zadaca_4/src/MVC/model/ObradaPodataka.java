/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.model;

import MVC.controller.singleton.ThingsOfFOI;
import MVC.model.composite.Aktuator;
import MVC.model.composite.Mjesto;
import MVC.model.composite.Senzor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Filip
 */
public class ObradaPodataka {
    
    private BazaPodataka bp;
    private String linija = "";
    private Boolean ispravan;
    private Integer ID;
    private ThingsOfFOI tof = ThingsOfFOI.getInstance();

    public ObradaPodataka() { 
        bp = new BazaPodataka();
    }

    public String mjestaObrada(String id) {
        validatorBroja(id);
        if (ispravan) {
            Mjesto mjesto = bp.mjestoPodaci(ID);
            if (mjesto != null) {
                linija = "NAZIV;ID;TIP;BROJ_AKTUATORA;BROJ_SENZORA;\n";
                linija += "-----;--;---;--------------;------------;\n";
                linija += mjesto.getNaziv() + ";" + mjesto.getId() + ";" + mjesto.getTip()
                        + ";" + mjesto.getBr_aktuatora() + ";" + mjesto.getBr_senzora() + "\n";
                
                return linija;
            }
        }
        return null;
    }
    
    public String strukturaMjesta(String id) {
        validatorBroja(id);
        if (ispravan) {
            Mjesto mjesto = bp.mjestoPodaci(ID);
            if (mjesto != null) {
                
                linija = "POD_MJESTO;ID\n";
                linija += "----------;--\n";
                for (Mjesto pod_mjesto : mjesto.getPod_mjesta()) {
                    linija += pod_mjesto.getNaziv() + ";" +pod_mjesto.getId() + "\n";
                }
                
                linija += "UREDJAJI;ID\n";
                linija += "--------;--\n";
                for (Object u : mjesto.getUredjaji()) {
                    if (u.getClass().toString().contains("Aktuator")) {
                        Aktuator a = (Aktuator) u;
                        linija += " (A)" + a.getNaziv() + ";" + a.getIdUredjaj() + "\n";
                    } else {
                        Senzor s = (Senzor) u;
                        linija += " (S)" + s.getNaziv() + ";" + s.getIdUredjaj() + "\n";
                    }
                }
                
                return linija;
            }
        }
        return null;
    }

    public String senzoriObrada(String id) {
        validatorBroja(id);
        if (ispravan) {
            Senzor senzor = bp.senzorPodaci(ID);
            if (senzor != null) {
                linija = "NAZIV;ID_MODELA;TIP;VRSTA;MIN;MAX;\n";
                linija +="-----;---------;---;-----;---;---;\n";
                linija += senzor.getNaziv() + ";" + senzor.getIdModel() + ";" + senzor.getTip()
                        + ";" + senzor.getVrsta() + ";" + senzor.getMin() + ";" + senzor.getMax() + "\n";
                return linija;
            }
        }
        return null;
    }
    
    public String kolekcijaSenzor(String id) {
        validatorBroja(id);
        if (ispravan) {
            String zaglavlje = "NAZIV;ID;\n";
            linija = bp.ModelPodaci(ID, false);
            if (linija != "") {
                zaglavlje += linija;
                return linija;
            }
        }
        return null;
    }
    
    public String kolekcijaAktuator(String id) {
        validatorBroja(id);
        if (ispravan) {
            String zaglavlje = "NAZIV;ID;\n";
            linija = bp.ModelPodaci(ID, true);
             if (linija != "") {
                zaglavlje += linija;
                return zaglavlje;
            }
        }
        return null;
    }

    public String aktuatoriObrada(String id) {
        validatorBroja(id);
        if (ispravan) {
            Aktuator aktuator = bp.aktuatorPodaci(ID);
            if (aktuator != null) {
                linija = "NAZIV;ID_MODELA;TIP;VRSTA;MIN;MAX;\n";
                linija +="-----;---------;---;-----;---;---;\n";
                linija += aktuator.getNaziv() + ";" + aktuator.getIdModel() + ";" + aktuator.getTip()
                        + ";" + aktuator.getVrsta() + ";" + aktuator.getMin() + ";" + aktuator.getMax() + "\n";
                linija += "SENZORI;ID\n";
                linija += "--------;--\n";
                for (Object u : aktuator.getSenzori()) {
                    Senzor s = (Senzor) u;
                    linija += " " + s.getNaziv() + ";" + s.getIdUredjaj() + "\n";
                }
                return linija;
            }
        }
        return null;
    }

    public String help() {
        return linija = "M x - ispis podataka mjesta x\n"
                + "S x - ispis podataka senzora x\n"
                + "A x - ispis podataka aktuatora x\n"
                + "SM x - ispis strukture mjesta x\n"
                + "TS x  - ispis podataka o kolekciji modela senzora x (npr. max broj, trenutni broj, broj zamjena, broj nabavljana itd.)"
                + "TA x  - ispis podataka o kolekciji modela aktuatora x (npr. max broj, trenutni broj, broj zamjena, broj nabavljana itd.)"
                + "S - ispis statistike\n"
                + "SP - spremi podatke (mjesta, uređaja)\n"
                + "VP - vrati spremljene podatke (mjesta, uređaja)\n"
                + "C n - izvršavanje n ciklusa dretve (1-100)\n"
                + "CP n - broj ciklusa dretve nakon kojih je uređaj popravljen i vraća se na raspolaganje (1-99)"
                + "PI n - prosječni % ispravnosti uređaja (0-100)\n"
                + "H - pomoć, ispis dopuštenih komandi i njihov opis\n"
                + "I - izlaz.\n";
    }

    private void validatorBroja(String id) {
        try {
            ID = Integer.parseInt(id);
            ispravan = true;
        } catch (NumberFormatException e) {
            ispravan = false;
        }
    }
}
