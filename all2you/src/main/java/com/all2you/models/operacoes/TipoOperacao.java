package com.all2you.models.operacoes;

import java.io.Serializable;
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
@Table(name="tipo_operacao")
public class TipoOperacao implements Serializable {

    /**
     * Identificador do tipo de operação gerado automaticamente.
     */
    @Id
    @Column(name="id_tp_operacao")
    @Length(max=50)
    private String id;
    
    @Column(name="descricao")
    @NotNull
    @Length(max=100)
    private String description;
    
    /**
     * Construtor vazio.
     */
    public TipoOperacao() { }
        
    /**
     * Cosntrutor inicializador.
     * @param descricao 
     */
    public TipoOperacao(String id, String description){
        initializeProperties(id, description);
    }
    
    /**
     * Atribui valores às propriedades.
     * @param id
     * @param description 
     */
    private void initializeProperties(String id, String description){
        setId(id);
        setDescription(description);
    }
    /**
     * Retorna o nome do tipo de operação.
     * @return 
     */
    public String getId(){
        return id;
    }
    
    /**
     * Retorna descrição referente ao tipo de operação.
     * @return 
     */
    public String getDescription(){
        return description;
    }
    
    /**
     * Define o ID do tipo de operação.
     * @param value
     * @return 
     */
    public TipoOperacao setId(String value){
        id = value;
        return this;
    }
    /**
     * Define uma descrição para o tipo de operação.
     * @param value 
     * @return
     */
    public TipoOperacao setDescription(String value){
        description = value;
        return this;
    }
    
}
