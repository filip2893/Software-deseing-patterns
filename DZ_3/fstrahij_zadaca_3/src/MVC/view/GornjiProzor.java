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
    
    public static final String ANSI_ESC = "\033[";
    
    private int brRedaka;
    private int brStupaca; 
    private String boja = "";

    public GornjiProzor(int brRedaka, int brStupaca) {
        this.brRedaka = brRedaka;
        this.brStupaca = brStupaca;
    }
    
    public void ispisi(String linija){
        Scanner scanner = new Scanner(linija);
        String newLine ="";
        int brojac = 1;        
        
        while (scanner.hasNext() && (brojac < brRedaka)) {
            System.out.print(ANSI_ESC + "H");
            System.out.print(ANSI_ESC + brojac + "B");
            newLine = scanner.nextLine() + "\n";
            String[] atributi = newLine.split(";");
            for (int i = 0; i < atributi.length; i++) {
                System.out.print(atributi[i]);
                System.out.print(ANSI_ESC + (i+1) +"C" );
            }            
            brojac++;
        }
        
    }
    
    public void ocistiProzor() {
        System.out.print(ANSI_ESC + "2J");
    }
    
    public void inicijaliziraj(){
        boja = "32;1m";  
        
        for (int i = 1; i <= brRedaka; i++) {
            System.out.print(ANSI_ESC + i + ";" + (brStupaca + 1) + "f");
            System.out.print(ANSI_ESC + boja);
            System.out.print("|");
        }
        //System.in.read();
    } 
    
    
    
}
