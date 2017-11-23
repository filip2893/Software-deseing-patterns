/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorymethod.algoritam;

/**
 *
 * @author Filip
 */
public class AlgoritamFactory {
    
    public Algoritam kreirajAlgoritam(String algoritam){
        switch(algoritam){
            case "factorymethod.algoritam.AlgoritamSlijedno":
                System.out.println("");
                System.out.println("AlgoritamSlijedno");              
                System.out.println("===================");
                return new AlgoritamSlijedno();            
            case "factorymethod.algoritam.AlgoritamAbecedno":                
                System.out.println("");
                System.out.println("AlgoritamAbecedno");           
                System.out.println("===================");
                return new AlgoritamAbecedno();
            case "factorymethod.algoritam.AlgoritamSlucajno":
                System.out.println("");
                System.out.println("AlgoritamSlucajno");           
                System.out.println("===================");
                return new AlgoritamSlucajno();
            default:
                return null;
        }
    }
    
}
