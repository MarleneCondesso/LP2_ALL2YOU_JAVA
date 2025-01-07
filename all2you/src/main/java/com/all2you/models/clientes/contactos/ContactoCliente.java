/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.models.clientes.contactos;

import com.all2you.models.clientes.Cliente;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author mstevz
 */
@Entity
@Table(name="contacto_cliente")
public class ContactoCliente implements Serializable {
    
    @Id
    @Length(max=20)
    @Column(name="contacto")
    private String contact;
    
    @Id
    @ManyToOne
    @JoinColumn(name="tipo_contacto", referencedColumnName="tipo")
    private TipoContacto contactType;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name="nif", referencedColumnName="nif")
    private Cliente client;
    
    @OneToMany
    @JoinColumns({
        @JoinColumn(name="contacto"),
        @JoinColumn(name="tipo_contacto")
    })
    public List<ObservacaoContactoCliente> observations; 

    public ContactoCliente() {
    }

    public ContactoCliente(String contact, TipoContacto contactType, Cliente client) {
        this.contact = contact;
        this.contactType = contactType;
        this.client = client;
    }

    public String getContact() {
        return contact;
    }

    public TipoContacto getContactType() {
        return contactType;
    }

    public Cliente getClient() {
        return client;
    }

    public List<ObservacaoContactoCliente> getObservations() {
        return observations;
    }
    
    public ContactoCliente setContact(String contact) {
        this.contact = contact;
        return this;
    }

    public ContactoCliente setContactType(TipoContacto contactType) {
        this.contactType = contactType;
        return this;
    }

    public ContactoCliente setClient(Cliente client) {
        this.client = client;
        return this;
    }
    
    public ContactoCliente setObservations(ObservacaoContactoCliente observation) {
        this.observations.add(observation);
        return this;
    }
    
    
    
    
}
