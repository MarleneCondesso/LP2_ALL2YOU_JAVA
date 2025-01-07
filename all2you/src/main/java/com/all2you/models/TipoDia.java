/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author mstevz
 */
@Entity
@Table(name="tipo_dia")
public class TipoDia {
    
    @Id
    @Length(max=8)
    private String dia;
    
    public static enum Day{
        Domingo,
        Segunda,
        Terca,
        Quarta,
        Quinta,
        Sexta,
        Sabado
    };
    
    public TipoDia() {
    }

    
    public static TipoDia getInstance(Day day){
        return new TipoDia(day.name());
    }

    private TipoDia(String dia) {
        setDia(dia);
    }
   
    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
    
}
