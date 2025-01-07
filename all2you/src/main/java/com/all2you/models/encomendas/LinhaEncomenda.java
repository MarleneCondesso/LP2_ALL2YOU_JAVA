package com.all2you.models.encomendas;

import com.all2you.models.artigos.Produto;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="linha_encomenda")
public class LinhaEncomenda implements Serializable {
    
    @Id
    @ManyToOne
    @JoinColumn(name="id_encomenda", referencedColumnName="id_encomenda")
    private Encomenda orderRequest;
    
    /**
     * Produto associado à operação, contem uma relação de Many-To-One com a entidade/modelo
     * "Produto", significando que "Operacao" apenas pode ter 1 "Produto" 
     * "Produto" pode ter várias "Operacao"
     */
    @Id
    @ManyToOne
    @JoinColumns({ // Usado para referenciar as chaves compostas
        @JoinColumn(name="ref_produto", referencedColumnName="ref_produto"), 
        @JoinColumn(name="versao_produto", referencedColumnName="versao_produto"),
    })
    private Produto product;
    
    @Column(name="descricao")
    @Length(max=100)
    @NotNull
    private String description ;
    
    @Column(name="quantidade")
    @NotNull
    private int quantity;
    
    @Column(name="preco_unitario")
    @NotNull
    private double unitPrice;
    
    /**
     * Construtor Vazio
     */
    public LinhaEncomenda() { }
    
    public LinhaEncomenda(Encomenda order, Produto product) {
        initializeProperties(order, product);
    }
    
    /**
     * Atribui os valores às propriedades de LinhaEncomenda.
     * @param order
     * @param product 
     */
    private void initializeProperties(Encomenda order, Produto product) {
        setOrder(order);
        setProduct(product);
    }
    
    /**
     * Retorna a encomenda associada.
     * @return 
     */
    public Encomenda getOrder() {
        return orderRequest;
    }
    
    /**
     * Retorna o produto associado à encomenda.
     * @return 
     */
    public Produto getProduct() {
        return product;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
      
    /**
     * Define a encomenda associada.
     * @param order 
     * @return
     */
    public LinhaEncomenda setOrder(Encomenda order) {
        this.orderRequest = order;
        return this;
    }
    
    /**
     * Define o produto associado à encomenda.
     * @param product 
     * @return
     */
    public LinhaEncomenda setProduct(Produto product) {
        this.product = product;
        return this;
    }

    public LinhaEncomenda setDescription(String description) {
        this.description = description;
        return this;
    }

    public LinhaEncomenda setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public LinhaEncomenda setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }
    
    
}
