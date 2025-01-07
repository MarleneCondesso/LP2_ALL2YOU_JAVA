package com.all2you.models.artigos;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author isabel
 */
@Entity
@Table(name="tipo_unidade")
public class TipoUnidade implements Serializable {

    /**
     * Identificador do tipo de operação gerado automaticamente.
     */
    @Id
    @Column(name="unidade")
    @Length(max=2)
    private String id;
    
    @Column(name="descricao")
    @NotNull
    @Length(max=30)
    private String description;
    
    /**
     * Construtor vazio.
     */
    public TipoUnidade() { }
        
    /**
     * Cosntrutor inicializador.
     * @param id
     * @param descricao 
     */
    public TipoUnidade(String id, String description){
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id= id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
  
}
