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
public class GornjiProzor {

    public boolean nastavak = false;
    public static int brojac = 1;

    private static String ispis = "";
    private final String ANSI_ESC = "\033[";
    private int brRedaka;
    private int brStupaca;
    private String boja = "";

    public GornjiProzor(int brRedaka, int brStupaca) {
        this.brRedaka = brRedaka;
        this.brStupaca = brStupaca;
    }

    public void ispisi(String linija) {
        Scanner scanner;
        
        if (linija == null) {
            scanner = new Scanner(ispis);
        }else{
            scanner = new Scanner(linija);
        }
        
        String newLine = "", newIspis = "";
        
        System.out.print(ANSI_ESC + brojac + ";0f");
        
        while (scanner.hasNext() ) {
            newLine = scanner.nextLine() + "\n";

            if (brojac <= brRedaka) {
                String[] atributi = newLine.split(";");
                //System.out.print(newLine);
                System.out.print(ANSI_ESC + brojac + ";0f");
                for (int i = 0; i < atributi.length; i++) {
                    System.out.print(atributi[i]);
                    System.out.print(ANSI_ESC + (i + 1) + "C");
                }
                //System.out.print(ANSI_ESC + "H");
                
            } else {
                newIspis += newLine;
            }
            brojac++;
        }
        if (brojac > brRedaka) {
            ispis = newIspis;  
            nastavak = true;          
        } else {
            ispis = "";
            nastavak = false;
        }
    }

    public void ocistiProzor() {
        System.out.print(ANSI_ESC + (brRedaka + 1) +";0f");
        System.out.print(ANSI_ESC + "1J");
        System.out.print(ANSI_ESC + "0;0f");
    }

    public void inicijaliziraj() {
        boja = "32;1m";

        for (int i = 1; i <= brRedaka; i++) {
            System.out.print(ANSI_ESC + i + ";" + (brStupaca + 1) + "f");
            System.out.print(ANSI_ESC + boja);
            System.out.print("|");
        }
        //System.in.read();
    }

}
