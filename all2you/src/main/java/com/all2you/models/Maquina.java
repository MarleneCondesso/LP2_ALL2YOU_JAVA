package com.all2you.models;

import com.all2you.models.operacoes.TipoOperacao;
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
@Table(name="maquina")
public class Maquina implements Serializable {
    
    @Id
    @Column(name="id_maquina")
    @Length(max=20)
    private String id;
    
    @Id
    @ManyToOne
    @JoinColumn(name="id_tp_operacao", referencedColumnName="id_tp_operacao")
    private TipoOperacao operationType;
    
    @Column(name="estado")
    @NotNull
    private boolean status;
    
    public Maquina(){ }
    
    /**
     * Construtor inicializador.
     * @param id
     * @param operationType
     * @param status 
     */
    public Maquina(String id, TipoOperacao operationType, boolean status) {
        initializeProperties(id, operationType, status);
    }
    
    /**
     * Atribui valores às propriedades da Maquina.
     * @param id
     * @param operationType
     * @param status 
     */
    private void initializeProperties(String id, TipoOperacao operationType, boolean status){
        setId(id);
        setOperationType(operationType);
        setStatus(status);
    }
    
    /**
     * Retorna o ID da máquina.
     * @return 
     */
    public String getId() {
        return id;
    }
    
    /**
     * Retorna o tipo de operação que a máquina desempenha.
     * @return 
     */
    public TipoOperacao getOperationType() {
        return operationType;
    }
    
    /**
     * Retorna o estado de disponibilidade da máquina.
     * @return 
     */
    public boolean getStatus() {
        return status;
    }
    
    /**
     * Define o ID da máquina.
     * @param id 
     * @return
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Define qual o tipo de operação que a máquina desempenha.
     * @param operationType 
     * @return
     */
    public void setOperationType(TipoOperacao operationType) {
        this.operationType = operationType;
    }
    
    /**
     * Define o estado de disponibilidade da máquina.
     * @param status 
     * @return
     */
    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
