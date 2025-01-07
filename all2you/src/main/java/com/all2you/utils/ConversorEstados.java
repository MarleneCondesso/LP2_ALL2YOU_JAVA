/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.utils;

/**
 *
 * @author ruteg
 */
public class ConversorEstados {
    
    public static String estadoAtivoInativo(boolean bool) {
        String booleanString = "Inativo";
        if(bool) {
            booleanString = "Ativo";
        }
        return booleanString;
    }
}
