/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fstrahij_zadaca_1;

import prototype.Aktuator;
import prototype.Senzor;
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
import singleton.ThingsOfFOI;

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
       
        String izlaz = "";
        
         if (status) {
            if (m.group(1) != null) {
                sjeme = Long.valueOf(m.group(1));
                izlaz += "Sjeme: " + sjeme + "\n";
            } if (m.group(2) != null) {
                mjesta_dat = m.group(2);
                izlaz += "Mjesta: " + mjesta_dat + "\n";
            } if (m.group(3) != null) {
                senzori_dat = m.group(3);
                izlaz += "Senzori: " + senzori_dat + "\n";
            } if (m.group(4) != null) {
                aktuatori_dat = m.group(4);
                izlaz += "Aktuatori: " + aktuatori_dat + "\n";
            } 
              if (m.group(5) != null) {
                algoritam = m.group(5);
                izlaz += "Algoritam: " + algoritam + "\n";
            }
              if (m.group(6) != null) {
                trajanje_ciklusa = Integer.parseInt(m.group(6));
                izlaz += "Trajanje ciklusa dretve: " + trajanje_ciklusa + "\n";
            }
              if (m.group(7) != null) {
                br_ciklusa = Integer.parseInt(m.group(7));
                izlaz += "Broj ciklusa dretve: " + br_ciklusa + "\n";
            }
              if (m.group(8) != null) {
                izlaz_dat = m.group(8);
                izlaz += "Izlaz: " + izlaz_dat + "\n\n";
            }
        } else {
            System.out.println("[Aplikacija]: Sintaksa ne odgovara!");
            return;
        }
         
        ThingsOfFOI thingsOfFOI = ThingsOfFOI.getInstance(); 
        thingsOfFOI.inicijalizirajPodatke(sjeme, mjesta_dat, senzori_dat, aktuatori_dat, algoritam, izlaz_dat, trajanje_ciklusa, br_ciklusa, izlaz);
        
    }     
}
