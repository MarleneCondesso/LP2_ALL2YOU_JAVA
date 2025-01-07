/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.models.clientes;

import com.all2you.models.clientes.contactos.ContactoCliente;
import com.all2you.services.ClientesService;
import com.all2you.services.GenericService;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author mstevz
 */
@Entity
@Table(name="Cliente")
public class Cliente implements Serializable {
    
    @Id
    @Column(name="nif")
    private int nif;
    
    @Length(min=3, max=50)
    @NotNull
    @Column(name="nome")
    private String name;
        
    @OneToMany(mappedBy="client")
    //@JoinColumn(name="nota", referencedColumnName="id")
    private List<NotaCliente> notes;

    public Cliente(){}
    
    public Cliente(int nif, String name) {
        this.nif = nif;
        this.name = name;
    }

    public int getNif() {
        return nif;
    }

    public String getName() {
        return name;
    }
     
    public List<NotaCliente> getNotes() {
        return notes;
    }
  
    public Cliente setNif(int nif) {
        this.nif = nif;
        return this;
    }

    public Cliente setName(String name) {
        this.name = name;
        return this;
    }

    public Cliente setNotes(NotaCliente note) {
        this.notes.add(note);
        return this;
    }
    
}
