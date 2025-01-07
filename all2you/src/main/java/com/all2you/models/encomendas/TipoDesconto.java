/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.models.encomendas;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author mstevz
 */
@Entity
@Table(name="tipo_desconto")
public class TipoDesconto implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    
    @Column(name="descricao")
    @Length(max=50)
    @NotNull
    private String description;
    
    /**
     * Construtor vazio.
     */
    public TipoDesconto() { }
    
    public TipoDesconto(String description){
        setDescription(description);
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public TipoDesconto setDescription(String description) {
        this.description = description;
        return this;
    }
   
}
