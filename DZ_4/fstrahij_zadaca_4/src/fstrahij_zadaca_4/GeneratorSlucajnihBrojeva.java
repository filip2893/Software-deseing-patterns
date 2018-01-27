/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fstrahij_zadaca_4;

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

    public static void setSjeme(long sjeme) {
        GeneratorSlucajnihBrojeva.sjeme = sjeme;
    } 
    
    public int dajSlucajniBroj(int odBroja, int doBroja){         
         return random.nextInt((doBroja - odBroja)+1) +odBroja;
    }
    
    public float dajSlucajniBroj(float odBroja, float doBroja){         
        return random.nextFloat() * (doBroja - odBroja) + odBroja;
    }
    
    public int exp(double vjerojatnost) {
        boolean myBool = Math.random() >= 1.0 - vjerojatnost;                 
        return myBool ? 1 : 0;
    }
}
