/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fstrahij_zadaca_4;

import MVC.model.composite.Aktuator;
import MVC.model.composite.Mjesto;
import MVC.model.composite.Senzor;
import facade.Program;
import facade.UlazniKontrolor;
import factorymethod.datoteka.Datoteka;
import factorymethod.datoteka.DatotekaFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import MVC.controller.singleton.ThingsOfFOI;

/**
 *
 * @author Filip
 */
public class Fstrahij_zadaca_4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {      
        
        Program program = new Program();
        program.pokreni(args);

    }

}
