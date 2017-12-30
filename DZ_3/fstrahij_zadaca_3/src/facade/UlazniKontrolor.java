/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import fstrahij_zadaca_3.GeneratorSlucajnihBrojeva;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import MVC.controller.singleton.ThingsOfFOI;

/**
 *
 * @author Filip
 */
public class UlazniKontrolor {

    private String mjesta_dat = "", senzori_dat = "", aktuatori_dat = "", raspored_dat = "", izlaz = "";
    private long sjeme = 0;
    private int br = 24, bs = 80, brk = 2, pi = 50, tcd = 0;
    private ThingsOfFOI tof = ThingsOfFOI.getInstance();

    public boolean provjera(String[] args) {
        
        if (args.length <= 0) {return false;}
        
        sjeme_default();
        tcd_default();
        

        for (int i = 0; i < args.length; i += 2) {
            String opcija = args[i];
            int vrij = 0;
            if (opcija.equals("--help")) {
                help();
                return true;
            } else if (opcija.equals("-br")) {
                vrij = Integer.parseInt(args[i + 1]);
                if (raspon(24, 40, vrij)) {
                    br = vrij;
                } else {
                    return false;
                }
            } else if (opcija.equals("-bs")) {
                vrij = Integer.parseInt(args[i + 1]);
                if (raspon(80, 160, vrij)) {
                    bs = vrij;
                } else {
                    return false;
                }
            } else if (opcija.equals("-brk")) {
                vrij = Integer.parseInt(args[i + 1]);
                if (raspon(2, 5, vrij)) {
                    brk = vrij;
                } else {
                    return false;
                }
            } else if (opcija.equals("-pi")) {
                vrij = Integer.parseInt(args[i + 1]);
                if (raspon(0, 100, vrij)) {
                    pi = vrij;
                } else {
                    return false;
                }
            } else if (opcija.equals("-g")) {
                vrij = Integer.parseInt(args[i + 1]);
                if (raspon(100, 65535, vrij)) {
                    sjeme = vrij;
                } else {
                    return false;
                }
            } else if (opcija.equals("-m")) {
                if (!args[i + 1].isEmpty()) {
                    mjesta_dat = args[i + 1];
                } else {
                    return false;
                }
            } else if (opcija.equals("-s")) {
                if (!args[i + 1].isEmpty()) {
                    senzori_dat = args[i + 1];
                } else {
                    return false;
                }
            } else if (opcija.equals("-a")) {
                if (!args[i + 1].isEmpty()) {
                    aktuatori_dat = args[i + 1];
                } else {
                    return false;
                }
            } else if (opcija.equals("-r")) {
                if (!args[i + 1].isEmpty()) {
                    raspored_dat = args[i + 1];
                } else {
                    return false;
                }
            } else if (opcija.equals("-tcd")) {
                vrij = Integer.parseInt(args[i + 1]);
                if (raspon(1, 17, vrij)) {
                    tcd = vrij;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        zapisIzlazna();
        return true;
    }

    private void tcd_default() {
        GeneratorSlucajnihBrojeva gen = new GeneratorSlucajnihBrojeva();
        tof.setGen(gen, sjeme);
        do {
            tcd = gen.dajSlucajniBroj(1, 17);
        } while (!raspon(1, 17, tcd));
    }

    private void sjeme_default() {
        int sec, ms;
        DateFormat dateFormat;
        Date date;
        do {
            dateFormat = new SimpleDateFormat("ss");
            date = new Date();
            sec = Integer.parseInt(dateFormat.format(date)) * 1000;

            dateFormat = new SimpleDateFormat("SSS");
            ms = Integer.parseInt(dateFormat.format(date));
            sjeme = sec + ms;

        } while (!raspon(100, 65535, (int) sjeme));
    }

    private boolean raspon(int odBroja, int doBroja, int vrijednost) {
        return (vrijednost >= odBroja && vrijednost <= doBroja) ? true : false;
    }

    private void zapisIzlazna() {
        izlaz += "Broj redataka: " + br
                + "\nBroj stupaca: " + bs
                + "\nBroj redaka na ekranu za unos komandi: " + brk
                + "\nProsjecna ispravnost uredjaja: " + pi + " %"
                + "\nSjeme: " + sjeme
                + "\nNaziv datoteke mjesta: " + mjesta_dat
                + "\nNaziv datoteke senzora: " + senzori_dat
                + "\nNaziv datoteke aktuatora: " + aktuatori_dat
                + "\nNaziv datoteke rasporeda: " + raspored_dat
                + "\nTrajanje ciklusa dretve: " + tcd + "\n";   
        tof.setBr(this.br);
        tof.setBrk(this.brk);
        tof.setBrs(this.bs);
        tof.setTrajanje_ciklusa(this.tcd);
        tof.setPi(this.pi);
        tof.setIzlaz(this.izlaz);
    }

    private void help() {
        izlaz = "-br broj redaka na ekranu (24-40). Ako nije upisana opcija, uzima se 24.\n"
                + "-bs broj stupaca na ekranu (80-160). Ako nije upisana opcija, uzima se 80.\n"
                + "-brk broj redaka na ekranu za unos komandi (2-5). Ako nije upisana opcija, uzima se 2.\n"
                + "-pi prosječni % ispravnosti uređaja (0-100). Ako nije upisana opcija, uzima se 50.\n"
                + "-g sjeme za generator slučajnog broja (u intervalu 100 - 65535). "
                + "Ako nije upisana opcija, uzima se broj milisekundi u "
                + "trenutnom vremenu na bazi njegovog broja sekundi i broja milisekundi.\n"
                + "-m naziv datoteke mjesta\n"
                + "-s naziv datoteke senzora\n"
                + "-a naziv datoteke aktuatora\n"
                + "-r naziv datoteke rasporeda\n"
                + "-tcd trajanje ciklusa dretve u sek. "
                + "Ako nije upisana opcija, uzima se slučajni broj u intervalu 1 - 17.\n"
                + "--help pomoć za korištenje opcija u programu.";
        ThingsOfFOI tof = ThingsOfFOI.getInstance();
        tof.setIzlaz(izlaz);
    }

    public String getMjesta_dat() {
        return mjesta_dat;
    }

    public String getSenzori_dat() {
        return senzori_dat;
    }

    public String getAktuatori_dat() {
        return aktuatori_dat;
    }

    public String getRaspored_dat() {
        return raspored_dat;
    }
}
