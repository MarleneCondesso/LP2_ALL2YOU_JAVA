/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.models.clientes.contactos;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author mstevz
 */
@Entity
@Table(name="observacao_contacto_cliente")
public class ObservacaoContactoCliente implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name="contacto", referencedColumnName="contacto"),
        @JoinColumn(name="tipo_contacto", referencedColumnName="tipo_contacto")
    })
    private ContactoCliente contact;
    
    @Length(max=500)
    @NotNull
    @Column(name="observacao")
    private String observation;
    
    public ObservacaoContactoCliente(){ }
    
    public ObservacaoContactoCliente(int id, ContactoCliente contact, String observation) {
        this.id = id;
        this.contact = contact;
        this.observation = observation;
    }

    public int getId() {
        return id;
    }

    public ContactoCliente getContact() {
        return contact;
    }

    public String getObservation() {
        return observation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContact(ContactoCliente contact) {
        this.contact = contact;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
    
    
    
}
