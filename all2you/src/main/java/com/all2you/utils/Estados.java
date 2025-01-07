package com.all2you.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ruteg
 */
public class Estados {
    
    public static String estadoAtivoInativo(boolean bool) {
        String booleanString = "Inativo";
        if(bool) {
            booleanString = "Ativo";
        }
        return booleanString;
    }
    
    public static String confirmacaoSimNao(boolean bool) {
    String booleanString = "Não";
    if(bool) {
        booleanString = "Sim";
    }
    return booleanString;
    }
    
    public static List listaConfirmacao(){
        List confirmation = new ArrayList();
        
        confirmation.add("Sim");
        confirmation.add("Não");
        
        return confirmation;
    }
    
    public static List listaEstados() {
        List estados = new ArrayList();
        
        estados.add("Ativo");
        estados.add("Inativo");
        
        return estados;
    }
    
}