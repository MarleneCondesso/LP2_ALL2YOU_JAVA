package com.all2you.models.artigos;

import java.io.Serializable;
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
@Table(name = "Produto")
public class Produto implements Serializable {

    /**
     * Referencia do produto. Permite até 100 caracteres.
     */
    @Id
    @Column(name = "ref_produto")
    @Length(max = 100)
    private String reference;

    /**
     * Versão do produto. Permite até 50 caracteres.
     */
    @Id
    @Column(name = "versao_produto")
    @Length(max = 50)
    private String version;

    /**
     * Permite até 100 caracteres.
     */
    @Column(name = "designacao")
    @NotNull
    @Length(max = 100)
    private String designation;

    /**
     * Permite até 100 caracteres.
     */
    @Column(name = "designacao_comercial")
    @NotNull
    @Length(max = 100)
    private String commercialDesignation;

    /**
     * Quantidade lote do produto.
     */
    @Column(name = "qtd_lote")
    @NotNull
    private int batchQuantity;

    /**
     * Tipo de unidade, contem uma relação de Many-To-One com a entidade/modelo
     * "TipoUnidade", significando que "Produto" apenas pode ter 1 tipo de
     * unidade e "TipoUnidade" pode conter várias "Unidades"
     */
    @ManyToOne
    @JoinColumn(name = "unidade", referencedColumnName = "unidade")
    private TipoUnidade unitType;

    /**
     *
     */
    @Column(name = "estado")
    @NotNull
    private boolean status;

    /**
     * Construtor vazio.
     */
    public Produto() {
    }

    /**
     * Construtor inicializador.
     *
     * @param reference
     * @param version
     * @param designation
     * @param commercialDesignation
     * @param batchQuantity
     * @param unitType
     * @param status
     */
    public Produto(String reference, String version, String designation, String commercialDesignation, int batchQuantity, TipoUnidade unitType, boolean status) {
        initializeProperties(reference, version, designation, commercialDesignation, batchQuantity, unitType, status);
    }

    /**
     * Atribui os valores ás propriedades do produto.
     *
     * @param reference
     * @param version
     * @param designation
     * @param commercialDesignation
     * @param batchQuantity
     * @param unitType
     * @param status
     */
    private void initializeProperties(String reference, String version, String designation, String commercialDesignation, int batchQuantity, TipoUnidade unitType, boolean status) {
        setReference(reference);
        setVersion(version);
        setDesignation(designation);
        setCommercialDesignation(commercialDesignation);
        setBatchQuantity(batchQuantity);
        setUnitType(unitType);
        setStatus(status);
    }

    /**
     * Retorna a referência que identifica o produto.
     *
     * @return
     */
    public String getReference() {
        return reference;
    }

    /**
     * Retorna a versão do produto.
     *
     * @return
     */
    public String getVersion() {
        return version;
    }

    /**
     *
     * @return
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * Retorna a designação comercial.
     *
     * @return
     */
    public String getCommercialDesignation() {
        return commercialDesignation;
    }

    /**
     * Retorna a quantidade lote disponivel para o produto.
     *
     * @return
     */
    public int getBatchQuantity() {
        return batchQuantity;
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
     * Retorna o estado do produto. Ativo => Disponivel Inativo => Indisponivel
     *
     * @return
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * Define o valor de referência do produto. Ter em conta que este é o campo
     * primário que identifica o registo na base de dados.
     *
     * @param reference
     * @return
     */
    public Produto setReference(String reference) {
        this.reference = reference;
        return this;
    }

    /**
     * Define a versão do produto.
     *
     * @param version
     * @return
     */
    public Produto setVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * Define a designação do produto.
     *
     * @param designation
     * @return
     */
    public Produto setDesignation(String designation) {
        this.designation = designation;
        return this;
    }

    /**
     * Define a designação comercial do produto.
     *
     * @param commercialDesignation
     * @return
     */
    public Produto setCommercialDesignation(String commercialDesignation) {
        this.commercialDesignation = commercialDesignation;
        return this;
    }

    /**
     * Define a quantidade de lote para o produto.
     *
     * @param batchQuantity
     * @return
     */
    public Produto setBatchQuantity(int batchQuantity) {
        this.batchQuantity = batchQuantity;
        return this;
    }

    /**
     * Define a unidade de medição que é usada para contar as quantidades.
     *
     * @param unitType
     * @return
     */
    public Produto setUnitType(TipoUnidade unitType) {
        this.unitType = unitType;
        return this;
    }

    /**
     * Define o estado de disponibilidade do produto.
     *
     * @param status
     * @return
     */
    public Produto setStatus(boolean status) {
        this.status = status;
        return this;
    }
}
