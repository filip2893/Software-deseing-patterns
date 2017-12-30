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

    public static final String ANSI_ESC = "\033[";

    private int brRedaka;
    private int pocRedak;
    private Scanner scanner;
    private String naredba;
    private String boja;

    public DonjiProzor(int pocRedak, int brRedaka) {
        this.pocRedak = pocRedak;
        this.brRedaka = brRedaka;
        scanner = new Scanner(System.in);
    }

    public void ocistiProzor() {
        System.out.print(ANSI_ESC + "2J");
    }

    public String cekajNaredbu() {
        String tekst;
        boja = "1;37m";
        tekst = "[Izbornik] Unesi naredbu: ";

        System.out.print(ANSI_ESC + pocRedak + ";1f");

        System.out.print(ANSI_ESC + boja + tekst);

        naredba = scanner.nextLine();
        ocistiProzor();

        return naredba;
    }

}
