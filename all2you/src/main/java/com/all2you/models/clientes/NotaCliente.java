/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.models.clientes;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author mstevz
 */
@Entity
@Table(name="nota_cliente")
public class NotaCliente implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name="nif", referencedColumnName="nif")
    @NotNull
    private Cliente client;
    
    @Length(max=200)
    @Column(name="nota")
    @NotNull
    private String note;

    public NotaCliente() {
    }

    public NotaCliente(int id) {
        this.id = id;
    }

    public NotaCliente(Cliente client, String note) {
        this.client = client;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public Cliente getClient() {
        return client;
    }

    public String getNote() {
        return note;
    }

    public NotaCliente setId(int id) {
        this.id = id;
        return this;
    }

    public NotaCliente setClient(Cliente client) {
        this.client = client;
        return this;
    }

    public NotaCliente setNote(String note) {
        this.note = note;
        return this;
    }
    
    
}
