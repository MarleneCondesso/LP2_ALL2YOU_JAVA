/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.models.encomendas;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author mstevz
 */
@Entity
@Table(name="estado_encomenda")
public class EstadoEncomenda implements Serializable {
    
    @Id
    @JoinColumn(name="id_encomenda", referencedColumnName="id_encomenda")
    private Encomenda order;
    
    @Id
    @JoinColumn(name="id_estado", referencedColumnName="id")
    private TipoEstado state;
    
    @Column(name="fechado")
    @NotNull
    private boolean doesClose;
    
    @Column(name="data_ultima_modificacao")
    @NotNull
    private Date lastModification;

    public EstadoEncomenda() {
    }
    
    public EstadoEncomenda(Encomenda order, TipoEstado state) {
        this.order = order;
        this.state = state;
    }

    public Encomenda getOrder() {
        return order;
    }

    public TipoEstado getState() {
        return state;
    }

    public boolean isDoesClose() {
        return doesClose;
    }

    public Date getLastModification() {
        return lastModification;
    }

    public EstadoEncomenda setOrder(Encomenda order) {
        this.order = order;
        return this;
    }

    public EstadoEncomenda setState(TipoEstado state) {
        this.state = state;
        return this;
    }

    public EstadoEncomenda setDoesClose(boolean doesClose) {
        this.doesClose = doesClose;
        return this;
    }

    public EstadoEncomenda setLastModification(Date lastModification) {
        this.lastModification = lastModification;
        return this;
    }
   
    
}
