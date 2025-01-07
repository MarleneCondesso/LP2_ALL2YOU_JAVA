/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.models.clientes;

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
@Table(name="morada_cliente")
public class Morada {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    
    @Length(max=100)
    @Column(name="morada")
    @NotNull
    private String address;
    
    @Length(max=8)
    @NotNull
    @Column(name="codigo_postal")
    private String zipCode;
    
    @Length(max=100)
    @NotNull
    @Column(name="localidade")
    private String locale;
    
    @Length(max=100)
    @NotNull
    @Column(name="pais")
    private String country;
   
    @Column(name="morada_fiscal")
    private boolean isFiscalAddress;
    
    @ManyToOne
    @JoinColumn(name="nif", referencedColumnName="nif")
    private Cliente client;

    public Morada(){ }
    
    public Morada(String address, String zipCode, String locale, String country, Cliente client) {
        setAddress(address);
        setZipCode(zipCode);
        setLocale(locale);
        setCountry(country);
        setClient(client);
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getLocale() {
        return locale;
    }

    public String getCountry() {
        return country;
    }

    public boolean isIsFiscalAddress() {
        return isFiscalAddress;
    }
    
    public Cliente getClient() {
        return client;
    }
    
    public Morada setAddress(String address) {
        this.address = address;
        return this;
    }

    public Morada setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public Morada setLocale(String locale) {
        this.locale = locale;
        return this;
    }

    public Morada setCountry(String country) {
        this.country = country;
        return this;
    }

    public void setIsFiscalAddress(boolean isFiscalAddress) {
        this.isFiscalAddress = isFiscalAddress;
    }

    public Morada setClient(Cliente client) {
        this.client = client;
        return this;
    }
      
    
}
