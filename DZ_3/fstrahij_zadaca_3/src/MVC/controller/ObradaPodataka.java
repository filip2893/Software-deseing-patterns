/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.controller;

import MVC.model.BazaPodataka;
import MVC.model.composite.Aktuator;
import MVC.model.composite.Mjesto;
import MVC.model.composite.Senzor;

/**
 *
 * @author Filip
 */
public class ObradaPodataka {

    private BazaPodataka bp;
    private String linija = "";

    public ObradaPodataka() {
        bp = new BazaPodataka();
    }

    public String mjestaObrada(String naziv) {
        Mjesto mjesto = bp.mjestoPodaci(naziv);
        if (mjesto != null) {
            linija = "NAZIV;ID;TIP;BROJ_AKTUATORA;BROJ_SENZORA;\n";
            linija += mjesto.getNaziv() + ";" + mjesto.getId() + ";" + mjesto.getTip()
                    + ";" + mjesto.getBr_aktuatora() + ";" + mjesto.getBr_senzora() + "\n";
            linija += "UREDJAJI;ID\n";
            for (Object u : mjesto.getUredjaji()) {
                if (u.getClass().toString().contains("Aktuator")) {
                    Aktuator a = (Aktuator) u;
                    linija += a.getNaziv() + ";" + a.getId() + "\n";
                } else {
                    Senzor s = (Senzor) u;
                    linija += s.getNaziv() + ";" + s.getId() + "\n";
                }
            }
            return linija;
        }
        return null;
    }

    public String senzoriObrada(String naziv) {
        Senzor senzor = bp.senzorPodaci(naziv);
        if (senzor != null) {
            linija = "NAZIV;ID;TIP;VRSTA;MIN;MAX;\n";
            linija += senzor.getNaziv() + ";" + senzor.getId() + ";" + senzor.getTip()
                    + ";" + senzor.getVrsta() + ";" + senzor.getMin() + ";" + senzor.getMax() + "\n";
            return linija;
        }
        return null;
    }

    public String aktuatoriObrada(String naziv) {
        Aktuator aktuator = bp.aktuatorPodaci(naziv);
        if (aktuator != null) {
            linija = "NAZIV;ID;TIP;VRSTA;MIN;MAX;\n";
            linija += aktuator.getNaziv() + ";" + aktuator.getId() + ";" + aktuator.getTip()
                    + ";" + aktuator.getVrsta() + ";" + aktuator.getMin() + ";" + aktuator.getMax() + "\n";
            linija += "SENZORI;ID\n";
            for (Object u : aktuator.getSenzori()) {
                Senzor s = (Senzor) u;
                linija += s.getNaziv() + ";" + s.getId() + "\n";
            }
            return linija;
        }
        return null;
    }

    public String help() {
        return linija = "M x - ispis podataka mjesta x\n"
                + "S x - ispis podataka senzora x\n"
                + "A x - ispis podataka aktuatora x\n"
                + "S - ispis statistike\n"
                + "SP - spremi podatke (mjesta, uređaja)\n"
                + "VP - vrati spremljene podatke (mjesta, uređaja)\n"
                + "C n - izvršavanje n ciklusa dretve (1-100)\n"
                + "VF s-senzor_ili_a-aktuator naziv_mjesta;tip(0,1);broj_senzora;broj_aktuatora;naziv_senzora_ili_aktuatora;vrsta(0,1,2,3);min;max;\n"
                + "PI n - prosječni % ispravnosti uređaja (0-100)\n"
                + "H - pomoć, ispis dopuštenih komandi i njihov opis\n"
                + "I - izlaz.\n";
    }
}
