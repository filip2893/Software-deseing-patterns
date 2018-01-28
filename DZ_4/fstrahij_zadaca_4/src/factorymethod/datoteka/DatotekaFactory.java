/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorymethod.datoteka;

/**
 *
 * @author Filip
 */
public class DatotekaFactory {
    
    public Datoteka koristiDatoteku(String datoteka, String naziv) {
        switch (datoteka) {
            case "Izlaz":
                return new DatotekaIzlaz(naziv);
            case "Aktuatori":
                return new DatotekaAktuatora(naziv);
            case "Senzori":
                return new DatotekaSenzora(naziv);
            case "Mjesta":
                return new DatotekaMjesta(naziv);
            case "Raspored":
                return new DatotekaRasporeda(naziv);
            default:
                return null;
        }
    }
    
}
