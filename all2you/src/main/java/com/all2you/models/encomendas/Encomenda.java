package com.all2you.models.encomendas;

import com.all2you.models.clientes.Cliente;
import com.all2you.models.clientes.Morada;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="encomenda")
public class Encomenda implements Serializable {
    
    @Id
    @Column(name="id_encomenda")
    @Length(max=15)
    private String id;
    
    @ManyToOne
    @JoinColumn(name="nif", referencedColumnName="nif")
    @NotNull
    private Cliente client;
    
    @ManyToOne
    @JoinColumn(name="id_morada_entrega", referencedColumnName="id")
    @NotNull
    private Morada address;
    
    @ManyToOne
    @JoinColumn(name="tipo_desconto", referencedColumnName="id")
    @NotNull
    private TipoDesconto discountType;
    
    @Column(name="valor_desconto")
    @NotNull
    private double discount;
    
    @Column(name="data_documento")
    @NotNull
    private LocalDate documentDate;
    
    @Column(name="data_criacao")
    @NotNull
    private LocalDate creationDate;
    
    @Column(name="data_modificaçao")
    @NotNull
    private LocalDate lastModification;
    
    /**
     * Construtor vazio.
     */
    public Encomenda(){ }
    
    /**
     * Construtor para inicializador.
     * @param id
     * @param date
     * @throws Exception 
     */
    public Encomenda(String id, LocalDateTime date){
        initializeProperties(id, date);
    }
        
    /**
     * Atribui os valores às propriedades de Encomenda.
     * @param id
     * @param date 
     */
    private void initializeProperties(String id, LocalDateTime date){
        setId(id);
    }
        
    /**
     * Retorna o identificador associado à encomenda.
     * @return
     */
    public String getId(){
        return id;
    }
    
    public TipoDesconto getDiscountType() {
        return discountType;
    }

    public double getDiscount() {
        return discount;
    }

    public LocalDate getDocumentDate() {
        return documentDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getLastModification() {
        return lastModification;
    }

    public Cliente getClient() {
        return client;
    }
    
    public Morada getMorada(){
        return address;
    }
    /**
     * Define o ID da encomenda.
     * @param id
     * @throws Exception 
     * @return
     */
    public Encomenda setId(String id){
        this.id = id;
        return this;
    }

    public Encomenda setDiscountType(TipoDesconto discountType) {
        this.discountType = discountType;
        return this;
    }

    public Encomenda setDiscount(double discount) {
        this.discount = discount;
        return this;
    }

    public Encomenda setDocumentDate(LocalDate documentDate) {
        this.documentDate = documentDate;
        return this;
    }

    public Encomenda setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public Encomenda setLastModification(LocalDate lastModification) {
        this.lastModification = lastModification;
        return this;
    }

    public Encomenda setClient(Cliente client) {
        this.client = client;
        return this;
    }
   
    public Encomenda setMorada(Morada address){
        this.address = address;
        return this;
    }
    
}
