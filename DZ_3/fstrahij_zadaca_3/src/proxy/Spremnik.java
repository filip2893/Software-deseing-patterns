/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import java.util.Scanner;

/**
 *
 * @author Filip
 */
public class Spremnik implements Izlaz{    
    
    private StvarnaDatoteka sd;
    private String izlaz = "", izlaz_dat;

    public Spremnik(int brl, String izlaz, String izlaz_dat) {        
        this.izlaz_dat = izlaz_dat;
        
        Scanner scanner = new Scanner(izlaz);
        while (scanner.hasNext() && brl > 1) {
            this.izlaz += scanner.nextLine() + "\n";
            brl--;
        }
    }
    
    
    @Override
    public void zapisi() {
        if (sd == null) {
            sd = new StvarnaDatoteka(izlaz, izlaz_dat);
        }
        sd.zapisi();
        izlaz = "";
    }
    
}
