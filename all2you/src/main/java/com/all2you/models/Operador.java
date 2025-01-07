package com.all2you.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author mstevz
 */
@Entity
@Table(name="operador")
public class Operador implements Serializable {
 
    @Id
    @Column(name="id_operador")
    @GeneratedValue(strategy=GenerationType.IDENTITY) // tells its auto-incremented value
    private int id;
    
    @Column(name="nome")
    @NotNull
    @Length(max=50)
    private String name;
    
    @Column(name="estado")
    @NotNull
    private boolean status;
    
    /**
     * Construtor vazio.
     */
    public Operador(){ }
    
    /**
     * Construtor inicializador.
     * @param name
     * @param status 
     */
    public Operador(String name, boolean status) {
        initializeProperties(name, status);
    }
    
    /**
     * Atribui os valores às propriedades do Operador.
     * @param name
     * @param status 
     */
    private void initializeProperties(String name, boolean status) {
        setName(name);
        setStatus(status);
    }
    
    /**
     * Retorna o ID do operador.
     * @return 
     */
    public int getId() {
        return id;
    }
    
    /**
     * Retorna o nome do operador.
     * @return 
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * Retorna o estado de disponibilidade do operador.
     * @return 
     */
    public boolean getStatus() {
        return status;
    }
    
    /**
     * Define o ID do operador.
     * Exemplo: Número de trabalhor.
     * @param id 
     * @return 
     */
    public Operador setId(int id) {
        this.id = id;
        return this;
    }
    
    /**
     * Define o nome do operador.
     * @param value
     * @return 
     */
    public Operador setName(String value){
        this.name = value;
        return this;
    }
    
    /**
     * Define o estado de disponibilidade do operador.
     * @param status
     * @return 
     */
    public Operador setStatus(boolean status) {
        this.status = status;
        return this;
    }
       
    
}
