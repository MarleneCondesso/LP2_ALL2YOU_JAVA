/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.models.clientes.contactos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author mstevz
 */
@Entity
@Table(name="tipo_contacto")
public class TipoContacto {
    
    @Id
    @Length(max=20)
    @Column(name="tipo")
    private String type;
    
    @Length(max=20)
    @NotNull
    @Column(name="descricao")
    private String description;
    
    public TipoContacto() { }
    
    public TipoContacto(String type, String description) {
        this.type = type;
        this.description = description;
    }

    /**
     * Devolve o tipo de contacto.
     * @return 
     */
    public String getType() {
        return type;
    }
    
    /**
     * Devolve um texto que descreva o tipo de contacto.
     * @return 
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Define o tipo de contacto.
     * @param type 
     */
    public TipoContacto setType(String type) {
        this.type = type;
        return this;
    }
    
    /**
     * Descreve o tipo de contacto.
     * @param description 
     */
    public TipoContacto setDescription(String description) {
        this.description = description;
        return this;
    }
    
}
