package com.all2you.models.operacoes;

import com.all2you.models.artigos.Produto;
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
@Table(name="operacao")
public class Operacao implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_operacao")
    private int id;
    
    /**
     * Tipo de operação, contem uma relação de Many-To-One com a entidade/modelo
     * "TipoOperacao", significando que "Operacao" apenas pode ter 1 tipo de 
     * operação e "TipoOperacao" pode conter várias "Operacao"
     */
    @ManyToOne
    @JoinColumn(name="id_tp_operacao", referencedColumnName="id_tp_operacao")
    private TipoOperacao operationType;
    
    /**
     * Produto associado à operação, contem uma relação de Many-To-One com a entidade/modelo
     * "Produto", significando que "Operacao" apenas pode ter 1 "Produto" 
     * "Produto" pode ter várias "Operacao"
     */
    @ManyToOne
    @JoinColumns({ // Usado para referenciar as chaves compostas
        @JoinColumn(name="ref_produto", referencedColumnName="ref_produto"), 
        @JoinColumn(name="versao_produto", referencedColumnName="versao_produto"),
    })
    private Produto product;
       
    @Column(name="ordem")
    @NotNull
    private int order;
    
    /**
     * Breve descrição sobre a operação.
     */
    @Column(name="descricao")
    @Length(max=200)
    private String description;
    
    /**
     * Número de operadores necessários para realizar a operação.
     */
    @Column(name="mao_de_obra")
    @NotNull
    private int labor;
    
    /**
     * Tempo até a operação ser concluida expresso em segundos. 
     */
    @Column(name="tempo")
    @NotNull
    private int time;
    
    @Column(name="instrucoes_tecnicas")
    @Length(max=200)
    private String technicalInstructions;
    
    /**
     * Construtor vazio.
     */
    public Operacao(){ }
    
    /**
     * Construtor inicializador. 
     * @param operationType
     * @param product
     * @param order
     * @param description
     * @param labor
     * @param time
     * @param technicalInstructions 
     */
    public Operacao(TipoOperacao operationType, Produto product, int order, String description, int labor, int time, String technicalInstructions) {
        initializeProperties(operationType, product, order, description, labor, time, technicalInstructions);
    }
    
    /**
     * Atribui os valores às propriedades de Operacao.
     * @param operationType
     * @param product
     * @param order
     * @param description
     * @param labor
     * @param time
     * @param technicalInstructions 
     */
    private void initializeProperties(TipoOperacao operationType, Produto product, int order, String description, int labor, int time, String technicalInstructions) {
        setOperationType(operationType);
        setProduct(product);
        setOrder(order);
        setDescription(description);
        setLabor(labor);
        setTime(time);
        setTechnicalInstructions(technicalInstructions);
    }
    
    /**
     * Retorna o ID da operação.
     * @return 
     */
    public int getId() {
        return id;
    }
    
    /**
     * Retorna o tipo de operação associado.
     * @return 
     */
    public TipoOperacao getOperationType() {
        return operationType;
    }
    
    /**
     * Retorna o produto associado.
     * @return 
     */
    public Produto getProduct() {
        return product;
    }
    
    public int getOrder() {
        return order;
    }

    /**
     * Retorna a descrição da operação.
     * @return 
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Retorna o número de operadores necessários para realizar a operação.
     * @return 
     */
    public int getLabor() {
        return labor;
    }
    
    /**
     * Retorna o número de segundos necessários para concluir a operação.
     * @return 
     */
    public int getTime() {
        return time;
    }
    
    /**
     * Retornas as instruções técnicas.
     * @return 
     */
    public String getTechnicalInstructions() {
        return technicalInstructions;
    }
    
    /**
     * Define o tipo de operação associado a esta operação.
     * @param operationType
     * @return 
     */
    public Operacao setOperationType(TipoOperacao operationType) {
        this.operationType = operationType;
        return this;
    }
    
    /**
     * Define qual o produto associado a esta operação.
     * @param product
     * @return 
     */
    public Operacao setProduct(Produto product) {
        this.product = product;
        return this;
    }
    
    /**
     * 
     * @param order
     * @return 
     */
    public Operacao setOrder(int order) {
        this.order = order;
        return this;
    }
    
    /**
     * Define a descrição que representa esta operação.
     * @param description
     * @return 
     */
    public Operacao setDescription(String description) {
        this.description = description;
        return this;
    }
    
    /**
     * Define o número de operadores necessários para realizar a operação.
     * @param labor
     * @return 
     */
    public Operacao setLabor(int labor) {
        this.labor = labor;
        return this;
    }
    
    /**
     * Define o tempo em segundos necessário para concluir a operação.
     * @param time
     * @return 
     */
    public Operacao setTime(int time) {
        this.time = time;
        return this;
    }

    /**
     * Indica as instrucções técnicas necessárias para concluir a operação.
     * @param technicalInstructions
     * @return 
     */
    public Operacao setTechnicalInstructions(String technicalInstructions) {
        this.technicalInstructions = technicalInstructions;
        return this;
    }
}
