/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fstrahij_zadaca_1;

import factorymethod.algoritam.Algoritam;
import factorymethod.algoritam.AlgoritamFactory;
import java.util.ArrayList;
import java.util.List;
import prototype.Mjesto;

/**
 *
 * @author Filip
 */
public class RadnaDretva extends Thread{

    private int trajanje_ciklusa = 0, br_ciklusa = 0; 
    private String algoritam;
    private List<Mjesto> mjesta;

    public RadnaDretva(int trajanje_ciklusa, int br_ciklusa, String algoritam, List<Mjesto> mjesta) {
        this.trajanje_ciklusa = trajanje_ciklusa * 1000;
        this.br_ciklusa = br_ciklusa;
        this.algoritam = algoritam;
        this.mjesta = new ArrayList<>(mjesta);
    }    

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {  
        
        AlgoritamFactory af = new AlgoritamFactory();
        Algoritam alg = af.kreirajAlgoritam(algoritam);
        
        int i = 0, element = 0;
        long t = 0, kraj = 0;
        
        Mjesto mj = null;        
        
        while(i < br_ciklusa){
            
            if (element >= mjesta.size()) {
                element = 0;
            }
            
            t = System.currentTimeMillis();
            kraj = t+trajanje_ciklusa;
            
            while (System.currentTimeMillis() < kraj) {
               mj  =  alg.odrediMjesto(mjesta, element);
                //provjeri statuse uredjaja mjesta
            }
            
            i++;
            System.out.println("ciklus br: "+ i);
            
            element++;
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
