/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorymethod.datoteka;

import MVC.model.Raspored;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import MVC.controller.singleton.ThingsOfFOI;

/**
 *
 * @author Filip
 */
public class DatotekaRasporeda implements Datoteka {

    String naziv, izlaz = "";
    List<Raspored> raspored;
    BufferedReader reader;

    public DatotekaRasporeda(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public void otvoriKreirajDatoteku() {
        try {
            reader = new BufferedReader(new FileReader(this.naziv));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DatotekaRasporeda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void procitajDatoteku() {
        String line;
        raspored = new ArrayList<>();
        String greske = "";

        ThingsOfFOI tof = ThingsOfFOI.getInstance();
        try {
            Raspored ra;
            line = reader.readLine();
            int vrstaZapisa = 0, id_mjesta, vrsta, id_modela_uredjaja, id_uredjaja, id_aktuatora, id_senzora, id_podmjesta;
            int brojac = 1;

            izlaz = tof.getIzlaz();
            izlaz += "-----------------\nUČITANI RASPORED\n-----------------";

            while (line != null) {

                String[] atributi = line.split(";"); 
                
                String trenutna = line;
                
                line = reader.readLine();

                if (brojac <= 4) {
                    brojac++;
                    continue;
                }
                try {
                    vrstaZapisa = Integer.parseInt(atributi[0]);
                } catch (NumberFormatException e) {
                    greske += "\n" + trenutna + " => vrsta zapisa mora biti numerička vrijednost";
                    continue;
                }
                if (vrstaZapisa == 0 && atributi.length == 5) {
                    boolean ispravan = true;
                    for (int i = 0; i < 5; i++) {
                        try {
                            Integer.parseInt(atributi[i]);
                        } catch (NumberFormatException e) {
                            greske += "\n" + trenutna + " => sve vrijednost moraju biti numeričke";                            
                            ispravan = false;
                            break;
                        }
                    }
                    if (ispravan) {
                        id_mjesta = Integer.parseInt(atributi[1]);
                        vrsta = Integer.parseInt(atributi[2]);
                        id_modela_uredjaja = Integer.parseInt(atributi[3]);
                        id_uredjaja = Integer.parseInt(atributi[4]);

                        ra = new Raspored(vrstaZapisa, id_mjesta, vrsta, id_modela_uredjaja, id_uredjaja);

                        izlaz += "\n\nVrsta zapisa: " + vrstaZapisa + "\nID mjesta: " + id_mjesta
                                + "\nVrsta: " + vrsta
                                + "\nID modela uredaja: " + id_modela_uredjaja
                                + "\nID uredaja: " + id_uredjaja + "\n";
                        raspored.add(ra);
                    }                  

                } else if (vrstaZapisa == 1 && atributi.length > 2) {                   
                    try {
                        id_aktuatora = Integer.parseInt(atributi[1]);
                    } catch (NumberFormatException e) {
                        greske += "\n" + trenutna + " => id aktuatora nije numerička vrijednost";
                        break;
                    }
                    
                    for (int i = 2; i < atributi.length; i++) {
                        String senzori = atributi[i];
                        
                        try {
                            id_senzora = Integer.parseInt(senzori);
                        } catch (NumberFormatException e) {
                            greske += "VRSTA_ZAPISA;ID_AKTUATOR;ID_SENZOR;\n";
                            greske += "------------;-----------;---------;";
                            greske += "\n \t" + atributi[0] + "\t;" + atributi[1] + "\t\t;" + senzori 
                                    + "\n => id senzora nije numerička vrijednost\n";
                            continue;
                        }

                        ra = new Raspored(vrstaZapisa, id_aktuatora, id_senzora, true);

                        izlaz += "\n\nVrsta zapisa: " + vrstaZapisa + "\nID aktuatora: " + id_aktuatora
                                + "\nID senzora: " + id_senzora + "\n";
                        
                        raspored.add(ra);
                    }

                    //for (int i = 0; i < senzori.length; i++) {

                        
                    //}
                } else if(vrstaZapisa == 2 && atributi.length > 2){
                    //mjestu dodaj mjesto - složena mjesta
                    try {
                        id_mjesta = Integer.parseInt(atributi[1]);
                    } catch (NumberFormatException e) {
                        greske += "\n" + trenutna + " => id složenog mjesta nije numerička vrijednost";
                        break;
                    }
                    
                    for (int i = 2; i < atributi.length; i++) {
                        String podMjesta = atributi[i];
                        
                        try {
                            id_podmjesta = Integer.parseInt(podMjesta);
                        } catch (NumberFormatException e) {
                            greske += "VRSTA_ZAPISA;ID_MJESTO;ID_POD_MJESTO;\n";
                            greske += "------------;---------;-------------;";
                            greske += "\n \t" + atributi[0] + "\t;" + atributi[1] + "\t\t;" + podMjesta 
                                    + "\n => id mjesta nije numerička vrijednost\n";
                            continue;
                        }

                        ra = new Raspored(vrstaZapisa, id_mjesta, id_podmjesta, false);

                        izlaz += "\n\nVrsta zapisa: " + vrstaZapisa + "\nID složenog mjesta: " + id_mjesta
                                + "\nID podmjesta: " + id_podmjesta + "\n";
                        
                        raspored.add(ra);
                    }
                }else {
                    greske += "\n" + trenutna + " => Ne odgovara vrsti zapisa(0,1) "
                            + "i/ili previše ili premalo stupaca\n";
                    continue;
                }                
            }
            izlaz += "\n*NEISPRAVNI ZAPISI RASPOREDA:\n" +greske;
            tof.ispisPodatke(greske);
        } catch (IOException ex) {
            Logger.getLogger(DatotekaRasporeda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void zapisiUDatoteku(String tekst) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void zatvoriDatoteku() {
        try {
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(DatotekaRasporeda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Object> vratiPodatke() {
        otvoriKreirajDatoteku();
        procitajDatoteku();
        zatvoriDatoteku();
        List<Object> objects = new ArrayList<>(raspored);
        return objects;
    }

}
