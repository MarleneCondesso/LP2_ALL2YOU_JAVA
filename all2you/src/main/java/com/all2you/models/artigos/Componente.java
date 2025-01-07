package com.all2you.models.artigos;

import com.all2you.models.operacoes.Operacao;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name="componente")
public class Componente implements Serializable {
    
    @Id
    @Column(name="ref")
    @Length(max=100)
    private String reference;
    
    @Id
    @Column(name="versao")
    @Length(max=50)
    private String version;
    
    @ManyToOne
    @JoinColumn(name="id_operacao", referencedColumnName="id_operacao")
    private Operacao operation;
    
    @Column(name="designacao_comercial")
    @NotNull
    @Length(max=100)
    private String comercialDesignation;
    
    @Column(name="quantidade")
    @NotNull
    private BigDecimal quantity;
       
    @ManyToOne
    @JoinColumn(name="unidade", referencedColumnName="unidade")
    private TipoUnidade unitType;    
    
    @Column(name="alternativa")
    @Length(max=25)
    private String alternative;
    
    /**
     * Construtor vazio.
     */
    public Componente() { }
    
    /**
     * Construtor inicializador.
     * @param reference
     * @param version
     * @param operation
     * @param comercialDesignation
     * @param quantity
     * @param unit
     * @param alternative 
     */
    public Componente(String reference, String version, Operacao operation, String comercialDesignation, BigDecimal quantity, TipoUnidade unit, String alternative) {     
        initializeProperties(reference, version, operation, comercialDesignation, quantity, unit, alternative);
    }
    
    /**
     * Atribui os valores campos respetivos do componente.
     * @param reference
     * @param operation
     * @param comercialDesignation
     * @param quantity
     * @param unit
     * @param alternative
     */
    private void initializeProperties(String reference, String version, Operacao operation, String comercialDesignation, BigDecimal quantity, TipoUnidade unit, String alternative){
        setReference(reference);
        setVersion(version);
        setOperation(operation);
        setComercialDesignation(comercialDesignation);
        setQuantity(quantity);
        setUnitType(unitType);
        setAlternative(alternative);
    }
    
    /**
     * Retorna a referência do componente.
     * Campo primário.
     * @return 
     */
    public String getReference() {
        return reference;
    }
    
    /**
     * Retorna a versão do componente.
     * Campo primário.
     * @return 
     */
    public String getVersion() {
        return version;
    }
    
    /**
     * Retorna a operação de produção associada ao componente.
     * @return 
     */
    public Operacao getOperation() {
        return operation;
    }
    
    /**
     * Retorna a designação comercial do componente.
     * @return 
     */
    public String getComercialDesignation() {
        return comercialDesignation;
    }

    /**
     * Retorna a quantidade disponivel em stock.
     * @return 
     */
    public BigDecimal getQuantity() {
        return quantity;
    }
    
    /**
     * Retorna o nome da unidade de medição usada para calcular a quantidade.
     *
     * @return
     */
    public TipoUnidade getUnitType() {
        return unitType;
    }
    
    /**
     * Retorna o nome do componente alternativo.
     * @return 
     */
    public String getAlternative() {
        return alternative;
    }
    
    /**
     * Define a referência do componente. 
     * Campo primário.
     * @param reference 
     * @return
     */
    public Componente setReference(String reference) {
        this.reference = reference;
        return this;
    }
    
    /**
     * Define a versão do componente.
     * Campo primário.
     * @param version 
     */
    public void setVersion(String version) {
        this.version = version;
    }
    
    /**
     * Define a operação de produção associada ao componete;
     * @param operation 
     * @return
     */
    public Componente setOperation(Operacao operation) {
        this.operation = operation;
        return this;
    }
    
    /**
     * Define a designação comercial do componente.
     * @param comercialDesignation 
     * @return
     */
    public Componente setComercialDesignation(String comercialDesignation) {
        this.comercialDesignation = comercialDesignation;
        return this;
    }
    
    /**
     * Define a quantiade disponivel em stock.
     * @param quantity 
     * @return
     */
    public Componente setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }
    
    /**
     * Define a unidade de medição que é usada para contar as quantidades.
     *
     * @param unitType
     * @return
     */
    public Componente setUnitType(TipoUnidade unitType) {
        this.unitType = unitType;
        return this;
    }
    
    /**
     * Define a alternativa a este componente, caso o mesmo esteja disponivel.
     * @param alternative 
     * @return
     */
    public Componente setAlternative(String alternative) {
        this.alternative = alternative;
        return this;
    }

}
