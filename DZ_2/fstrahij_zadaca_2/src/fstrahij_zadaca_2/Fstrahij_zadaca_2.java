/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fstrahij_zadaca_2;

import composite.Aktuator;
import composite.Mjesto;
import composite.Senzor;
import factorymethod.datoteka.Datoteka;
import factorymethod.datoteka.DatotekaFactory;
import iterator.Mjesta;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import singleton.ThingsOfFOI;
import visitor.Znakovi;

/**
 *
 * @author Filip
 */
public class Fstrahij_zadaca_2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String mjesta_dat = "", senzori_dat = "", aktuatori_dat = "", algoritam = "", izlaz_dat = "", izlaz = "";;
        long sjeme = 0;
        int trajanje_ciklusa = 0, br_ciklusa = 0, brl = 0;
        List<Aktuator> aktuators = new ArrayList<>();
        List<Senzor> senzors = new ArrayList<>();
        List<Mjesto> mjesta = new ArrayList<>();
        Mjesta mjesta_it;

        String help = "fstrahij_zadaca_2 --help";

        String sintaksa = "^-g( [0-9]{3,})? -m ([\\a-zA-Z0-9_/.]*.txt) -s ([\\a-zA-Z0-9_/.]*.txt) -a ([\\a-zA-Z0-9_/.]*.txt) -alg ([a-zA-Z0-9_/.]*) -tcd( [0-9]{0,})? -bcd( [0-9]{0,})? -i( [\\a-zA-Z0-9_/.]*.txt)? -brl( [0-9]{0,})?$";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }
        String p = sb.toString().trim();

        Pattern pattern = Pattern.compile(sintaksa);
        Pattern p2 = Pattern.compile(help);

        Matcher m = pattern.matcher(p);
        Matcher m2 = p2.matcher(p);

        boolean status = m.matches();
        boolean status2 = m2.matches();

        ThingsOfFOI tof = ThingsOfFOI.getInstance();

        GeneratorSlucajnihBrojeva gen = new GeneratorSlucajnihBrojeva();
        gen.postavi(sjeme);

        tof.setGen(gen);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        if (status) {
            if (m.group(1) != null) {
                sjeme = Long.valueOf(m.group(1).trim());
            } else {
                sjeme = gen.dajSlucajniBroj(100, 65535);
            }
            if (m.group(2) != null) {
                mjesta_dat = m.group(2);
            }
            if (m.group(3) != null) {
                senzori_dat = m.group(3);
            }
            if (m.group(4) != null) {
                aktuatori_dat = m.group(4);
            }
            if (m.group(5) != null) {
                algoritam = m.group(5);
            }
            if (m.group(6) != null) {
                trajanje_ciklusa = Integer.parseInt(m.group(6).trim());
            } else {
                trajanje_ciklusa = gen.dajSlucajniBroj(1, 17);
            }
            if (m.group(7) != null) {
                br_ciklusa = Integer.parseInt(m.group(7).trim());
            } else {
                br_ciklusa = gen.dajSlucajniBroj(1, 23);
            }
            if (m.group(8) != null) {
                izlaz_dat = m.group(8).trim();
            } else {
                izlaz_dat = "fstrahij" + timeStamp + ".txt";
            }
            if (m.group(9) != null) {
                brl = Integer.parseInt(m.group(9).trim());
            } else {
                brl = gen.dajSlucajniBroj(100, 999);
            }
        } else if (status2) {
            izlaz += "-g sjeme za generator slučajnog broja (u intervalu 100 - 65535). Ako nije upisana opcija, uzima se broj milisekundi u trenutnom vremenu na bazi njegovog broja sekundi i broja milisekundi.\n"
                    + "\n"
                    + "-m naziv datoteke mjesta\n"
                    + "\n"
                    + "-s naziv datoteke senzora\n"
                    + "\n"
                    + "-a naziv datoteke aktuatora\n"
                    + "\n"
                    + "-alg puni naziv klase algoritma provjere koja se dinamički učitava\n"
                    + "\n"
                    + "-tcd trajanje ciklusa dretve u sek. Ako nije upisana opcija, uzima se slučajni broj u intervalu 1 - 17.\n"
                    + "\n"
                    + "-bcd broj ciklusa dretve. Ako nije upisana opcija, uzima se slučajni broj u intervalu 1 - 23.\n"
                    + "\n"
                    + "-i naziv datoteke u koju se sprema izlaz programa. Ako nije upisana opcija, uzima se vlastito korisničko ime kojem se dodaje trenutni podaci vremena po formatu _ggggmmdd_hhmmss.txt npr. dkermek_20171105_203128.txt\n"
                    + "\n"
                    + "-brl broj linija u spremniku za upis u datoteku za izlaz. Ako nije upisana opcija, uzima se slučajni broj u intervalu 100 - 999.\n"
                    + "\n"
                    + "--help pomoć za korištenje opcija u programu.";
            System.out.println(izlaz);
            return;

        } else {
            System.out.println("[Aplikacija]: Sintaksa ne odgovara!");
            return;
        }
        izlaz += "Sjeme: " + sjeme + "\n";
        izlaz += "Mjesta: " + mjesta_dat + "\n";
        izlaz += "Senzori: " + senzori_dat + "\n";
        izlaz += "Aktuatori: " + aktuatori_dat + "\n";
        izlaz += "Algoritam: " + algoritam + "\n";
        izlaz += "Trajanje ciklusa dretve: " + trajanje_ciklusa + "\n";
        izlaz += "Broj ciklusa dretve: " + br_ciklusa + "\n";
        izlaz += "Izlaz: " + izlaz_dat + "\n";
        izlaz += "Broj linija: " + brl + "\n\n";

        tof.setIzlaz(izlaz);

        DatotekaFactory df = new DatotekaFactory();

        Datoteka datoteka = df.koristiDatoteku("Aktuatori", aktuatori_dat);
        datoteka.otvoriKreirajDatoteku();

        for (Object o : datoteka.vratiPodatke()) {
            aktuators.add((Aktuator) o);
        }

        datoteka = df.koristiDatoteku("Senzori", senzori_dat);

        for (Object o : datoteka.vratiPodatke()) {
            senzors.add((Senzor) o);
        }

        datoteka = df.koristiDatoteku("Mjesta", mjesta_dat);

        for (Object o : datoteka.vratiPodatke()) {
            mjesta.add((Mjesto) o);
        }

        izlaz = tof.getIzlaz();

        int slucajniBroj = 0;

        mjesta_it = new Mjesta(mjesta.size());

        for (int i = 0; i < mjesta.size(); i++) {
            mjesta_it.add(mjesta.get(i), i);
        }

        Znakovi sam = new Znakovi();

        for (Aktuator a : aktuators) {
            a.prihvati(sam);
        }

        izlaz += "\nBROJ ZNAKOVA SVIH AKTUATORA: " + sam.getZnakovi();
        izlaz += "\nBROJ SAMOGLASNIKA SVIH AKTUATORA: " + sam.getSamoglasnici();
        sam = new Znakovi();

        for (Senzor s : senzors) {
            s.prihvati(sam);
        }
        izlaz += "\nBROJ ZNAKOVA SVIH SENZORA: " + sam.getZnakovi();
        izlaz += "\nBROJ SAMOGLASNIKA SVIH SENZORA: " + sam.getSamoglasnici() + "\n";

        Mjesta.Iterator mj_it = mjesta_it.create_iterator();

        for (mj_it.first(); !mj_it.is_done(); mj_it.next()) {

            izlaz += "\n";
            String naziv = mj_it.current_item().getNaziv();
            int tip = mj_it.current_item().getTip();
            izlaz += "---------------------------------------------------------------- \nUREDJAJI DODANI MJESTU ["
                    + naziv + "] \n---------------------------------------------------------------- \n";

            for (Aktuator a : aktuators) {

                slucajniBroj = gen.dajSlucajniBroj(0, 2);

                if ((tip == a.getTip() || a.getTip() > 1) && slucajniBroj == 1) {

                    mj_it.current_item().add(a);

                    slucajniBroj = gen.dajSlucajniBroj(1, 1000);

                    a.setId(slucajniBroj);

                    izlaz += "dodan je aktuator[" + slucajniBroj + "]: " + a.getNaziv() + "\n";
                }

            }

            for (Senzor s : senzors) {

                slucajniBroj = gen.dajSlucajniBroj(0, 2);

                if ((tip == s.getTip() || s.getTip() > 1) && slucajniBroj == 1) {

                    mj_it.current_item().add(s);

                    slucajniBroj = gen.dajSlucajniBroj(1, 1000);

                    s.setId(slucajniBroj);

                    izlaz += "dodan je senzor[" + slucajniBroj + "]: " + s.getNaziv() + "\n";
                }
            }
            mj_it.current_item().sastavi();
            izlaz += "\n";
            tof.setIzlaz(izlaz);

        }

        tof.inicijalizirajSustav(
                algoritam,
                izlaz_dat,
                trajanje_ciklusa,
                br_ciklusa,
                izlaz,
                mjesta_it,
                brl);
        System.out.println(tof.getIzlaz());

    }

}
