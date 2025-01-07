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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author mstevz
 */
@Entity
@Table(name = "operador_horario")
public class OperadorHorario implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_operador", referencedColumnName = "id_operador")
    private Operador id;

    @Id
    @ManyToOne
    @JoinColumn(name = "dia", referencedColumnName = "dia")
    private TipoDia day;

    @Id
    @Column(name = "hora_inicio")
    private Time beginAt;

    @Column(name = "hora_fim")
    private Time endAt;

    public OperadorHorario(Operador id, TipoDia day, Time beginAt, Time endAt) {
        setOperator(id);
        setDay(day);
        setBeginAt(beginAt);
        setEndAt(endAt);
    }

    public OperadorHorario() {
    }

    public Operador getId() {
        return id;
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

    public void setOperator(Operador id) {
        this.id = id;
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
