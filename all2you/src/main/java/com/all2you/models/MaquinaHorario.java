/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.models;

import java.io.Serializable;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author mstevz
 */
@Entity
@Table(name="maquina_horario")
public class MaquinaHorario implements Serializable {
    
    @Id
    @ManyToOne
    @JoinColumns( {
        @JoinColumn(name="id_maquina", referencedColumnName="id_maquina"),
        @JoinColumn(name="id_tp_operacao", referencedColumnName="id_tp_operacao")            
    })
    private Maquina machine;
    
    @Id
    @ManyToOne
    @JoinColumn(name="dia", referencedColumnName="dia")
    private TipoDia day;
    
    @Id
    @Column(name="hora_inicio")
    private Time beginAt;
    
    @Column(name="hora_fim")
    private Time endAt;

    public MaquinaHorario(Maquina machine, TipoDia day, Time beginAt, Time endAt) {
        setMachine(machine);
        setDay(day);
        setBeginAt(beginAt);
        setEndAt(endAt);
    }

    public MaquinaHorario() {
            }

    public Maquina getMachine() {
        return machine;
    }

    public TipoDia getDay() {
        return day;
    }

    public Time getBeginAt() {
        return beginAt;
    }

    public Time getEndAt() {
        return endAt;
    }

    public void setMachine(Maquina machine) {
        this.machine = machine;
    }

    public void setDay(TipoDia day) {
        this.day = day;
    }

    public void setBeginAt(Time beginAt) {
        this.beginAt = beginAt;
    }

    public void setEndAt(Time endAt) {
        this.endAt = endAt;
    }

}
