/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC.view;

import java.util.Scanner;

/**
 *
 * @author Filip
 */
public class DonjiProzor {

    private final String ANSI_ESC = "\033[";
    private static String ispis = "";
    private static int trenutniRedak, brojac = 1;
    private int brRedaka;
    private int pocRedak;
    private Scanner scanner;
    private String naredba;
    private String boja;

    public DonjiProzor(int pocRedak, int brRedaka) {
        this.pocRedak = pocRedak;
        this.brRedaka = brRedaka;
        this.trenutniRedak = pocRedak;
        scanner = new Scanner(System.in);
    }

    public void ocistiProzor() {
        System.out.print(ANSI_ESC + pocRedak +";0f");
        System.out.print(ANSI_ESC + "J");
    }

    public String cekajNaredbu(boolean nastavak) {
        String tekst;
        boja = "1;32m";
        if (nastavak) {
            tekst = "[Izbornik] Unesi n/N za nastavak: ";            
        } else {
            tekst = "[Izbornik] Unesi naredbu: ";
        }
        ispis += tekst;
        System.out.print(ANSI_ESC + trenutniRedak + ";0f");
        
        System.out.print(ANSI_ESC + boja + tekst);

        naredba = scanner.nextLine(); 
        
        if (brRedaka == brojac) {
            ocistiProzor();            
            brojac = 1;
            trenutniRedak = pocRedak;
            //System.out.print(ANSI_ESC + brojac + "A");
        }else{
            brojac++;
            trenutniRedak++;
            //System.out.print(ANSI_ESC + "1B");
        }
        
        //ocistiProzor();
        
        return naredba;
    }

}
