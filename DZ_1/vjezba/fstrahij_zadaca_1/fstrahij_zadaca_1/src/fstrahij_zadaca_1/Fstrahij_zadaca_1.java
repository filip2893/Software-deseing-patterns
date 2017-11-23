/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fstrahij_zadaca_1;

import factorymethod.algoritam.Algoritam;
import factorymethod.algoritam.AlgoritamFactory;
import factorymethod.datoteka.Datoteka;
import factorymethod.datoteka.DatotekaFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import prototype.Mjesto;

/**
 *
 * @author Filip
 */
public class Fstrahij_zadaca_1 {    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String mjesta_dat = "", senzori_dat = "", aktuatori_dat = "", algoritam = "", izlaz_dat="";
        long sjeme = 0;
        int trajanje_ciklusa = 0, br_ciklusa = 0;        

        //dkermek_zadaca_1 717 DZ_1_mjesta.txt DZ_1_senzori.txt DZ_1_aktuatori.txt AlgoritamAbecedno 5 20 izlaz.txt
        String sintaksa = "^([0-9]{3,}) ([\\a-zA-Z0-9_/.]*.txt) ([\\a-zA-Z0-9_/.]*.txt) ([\\a-zA-Z0-9_/.]*.txt) ([a-zA-Z0-9_]*) ([0-9]{0,}) ([0-9]{0,}) ([\\a-zA-Z0-9_/.]*.txt)$";
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }
        String p = sb.toString().trim();
        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(p);
        boolean status = m.matches();
        String text = null;
         if (status) {
            if (m.group(1) != null) {
                sjeme = Long.valueOf(m.group(1));
                System.out.println("Sjeme: " + sjeme);
                text = "Sjeme: " + sjeme + "\n";
            } if (m.group(2) != null) {
                mjesta_dat = m.group(2);
                System.out.println("Mjesta: " + mjesta_dat);
                text += "Mjesta: " + mjesta_dat + "\n";
            } if (m.group(3) != null) {
                senzori_dat = m.group(3);
                System.out.println("Senzori: " + senzori_dat);
                text += "Senzori: " + senzori_dat + "\n";
            } if (m.group(4) != null) {
                aktuatori_dat = m.group(4);
                System.out.println("Aktuatori: " + aktuatori_dat);
                text += "Aktuatori: " + aktuatori_dat + "\n";
            } 
              if (m.group(5) != null) {
                algoritam = m.group(5);
                System.out.println("Algoritam: " + algoritam);
                text += "Algoritam: " + algoritam + "\n";
            }
              if (m.group(6) != null) {
                trajanje_ciklusa = Integer.parseInt(m.group(6));
                System.out.println("Trajanje ciklusa dretve: " + trajanje_ciklusa);
                text += "Trajanje ciklusa dretve: " + trajanje_ciklusa + "\n";
            }
              if (m.group(7) != null) {
                br_ciklusa = Integer.parseInt(m.group(7));
                System.out.println("Broj ciklusa dretve: " + br_ciklusa);
                text += "Broj ciklusa dretve: " + br_ciklusa + "\n";
            }
              if (m.group(8) != null) {
                izlaz_dat = m.group(8);
                System.out.println("Izlaz: " + izlaz_dat);
                text += "Izlaz: " + izlaz_dat + "\n";
            }
        } else {
            System.out.println("[Aplikacija]: Sintaksa ne odgovara!");
            return;
        }
        GeneratorSlucajnihBrojeva gen = new GeneratorSlucajnihBrojeva();
        gen.postavi(sjeme);
         
        List<Aktuator> aktuators = new ArrayList<>(); 
        List<Senzor> senzors = new ArrayList<>(); 
        List<Mjesto> mjesta = new ArrayList<>();
         
        DatotekaFactory df = new DatotekaFactory();
        
        Datoteka datoteka = df.koristiDatoteku("Izlaz", izlaz_dat);
        datoteka.otvoriKreirajDatoteku();
        datoteka.zapisiUDatoteku(text);
        
        datoteka = df.koristiDatoteku("Aktuatori", aktuatori_dat); 
        
        for (Object o : datoteka.vratiPodatke()) {
            aktuators.add((Aktuator)o);
        }
        
        datoteka = df.koristiDatoteku("Senzori", senzori_dat); 
        
        for (Object o : datoteka.vratiPodatke()) {
            senzors.add((Senzor)o);
        }     
        
        datoteka = df.koristiDatoteku("Mjesta", mjesta_dat); 
        
        
        for (Object o : datoteka.vratiPodatke()) {            
            mjesta.add((Mjesto)o);
        }  
        
        int slucajniBroj = 0; 
        
        for (Mjesto mj : mjesta) {
            for (Aktuator a : aktuators) {
                
                slucajniBroj = gen.getSlucajniBroj(2);
                
                if ((mj.getTip() == a.getTip() || a.getTip() > 1) && slucajniBroj == 1) {
                    mj.dodajAktuator(a); 
                    System.out.println("Mjestu: "+ mj.getNaziv() + "== je dodan aktuator:"+  a.getNaziv());
                }
                
            }
                        
             for (Senzor s : senzors) {
                 slucajniBroj = gen.getSlucajniBroj(2);
                
                if ((mj.getTip() == s.getTip() || s.getTip() > 1) && slucajniBroj == 1) {
                    mj.dodajSenzor(s);
                    System.out.println("Mjestu: "+ mj.getNaziv() + "== je dodan senzor:"+  s.getNaziv());
                }
             }
            System.out.println("----------------------------------------------------------------");
              
        }       
        
        datoteka.zatvoriDatoteku();
        
        RadnaDretva rd = new RadnaDretva(trajanje_ciklusa, br_ciklusa, algoritam, mjesta);
        rd.start();              
        
    }     
}
