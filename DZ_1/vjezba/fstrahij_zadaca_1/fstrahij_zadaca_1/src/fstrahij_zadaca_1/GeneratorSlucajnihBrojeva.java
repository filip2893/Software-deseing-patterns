/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fstrahij_zadaca_1;

import java.util.Random;

/**
 *
 * @author Filip
 */
public class GeneratorSlucajnihBrojeva {
    
    private static long sjeme;
    private static Random random;

    public GeneratorSlucajnihBrojeva() {
    }
    
    public static void postavi(long sjeme){
        GeneratorSlucajnihBrojeva.setSjeme(sjeme);
        random = new Random(sjeme);
    }

    public static long getSjeme() {
        return sjeme;
    }

    public static int getSlucajniBroj(int max) {
        return random.nextInt(max);
    }

    public static void setSjeme(long sjeme) {
        GeneratorSlucajnihBrojeva.sjeme = sjeme;
    } 
}
