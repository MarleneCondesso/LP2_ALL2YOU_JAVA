package com.all2you.models.tarefas;

import com.all2you.models.Maquina;
import com.all2you.models.operacoes.Operacao;
import com.all2you.utils.LocalDateTimeAttributeConverter;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author mstevz
 */
@Entity
@Table(name="tarefa_maquina")
public class TarefaMaquina implements Serializable {
    
    @Id
    @ManyToOne
    @JoinColumn(name="id_operacao", referencedColumnName="id_operacao")
    private Operacao operation;
    
    @Id
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name="id_maquina", referencedColumnName="id_maquina"),
        @JoinColumn(name="id_tp_operacao", referencedColumnName="id_tp_operacao"),
     })
    private Maquina machine;
    
    @Id
    @Column(name="data_inicio", columnDefinition="DATETIME")
    @NotNull
    private String startAt;
    
    /**
     * Contem o valor relacionado à base dados "data_inicio" no formato LocalDateTime.
     * Este campo está relacionado com a propriedade na enteidade "startAt".
     * 
     * Campo que não permite serialização e não é usado pela Framework do Hibernate.
     * 
     * Existe apenas para simular o campo DateTime na entidade, dado que o JPA e 
     * Hibernate  não permitem o uso de Datetime ou LocalDateTime.
     */
    @Transient
    private transient LocalDateTime transientStartAt;
    
    @Column(name="data_fim")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @NotNull
    private LocalDateTime finishAt;
    
    /**
     * Construtor vazio.
     */
    public TarefaMaquina(){ }
    
    /**
     * Construtor inicializador.
     * @param operation
     * @param machine
     * @param startTime
     * @param finishTime 
     */
    public TarefaMaquina(Operacao operation, Maquina machine, LocalDateTime startTime, LocalDateTime finishTime) {
        initializeProperites(operation, machine, startTime, finishTime);
    }
    
    /**
     * Atritui os valores às propriedades de TarefaMaquina.
     * @param operation
     * @param machine
     * @param startTime
     * @param finishTime 
     */
    private void initializeProperites(Operacao operation, Maquina machine, LocalDateTime startTime, LocalDateTime finishTime) {
        setOperation(operation);
        setMachine(machine);
        setStartTime(startTime);
        setFinishTime(finishTime);
    }
    
    /**
     * Retorna a operação associada a esta maquina.
     * @return 
     */
    public Operacao getOperation() {
        return operation;
    }
    
    /**
     * Retorna a máquina associada à tarefa.
     * @return 
     */
    public Maquina getMachine() {
        return machine;
    }

    /**
     * Retorna a data e hora em que a tarefa começou.
     * @return 
     */
    public LocalDateTime getStartTime() {
        transientStartAt = LocalDateTime.parse(startAt, LocalDateTimeAttributeConverter.patternFormater);
        return transientStartAt;
    }
    
    /**
     * Retorna a data e hora em que a tarefa terminou.
     * @return 
     */
    public LocalDateTime getFinishTime() {
        return finishAt;
    }
    
    
    /**
     * Associa uma operação à tarefa.
     * @param operation
     * @return 
     */
    public TarefaMaquina setOperation(Operacao operation) {
        this.operation = operation;
        return this;
    }
    
    /**
     * Associa uma máquina à tarefa.
     * @param machine
     * @return 
     */
    public TarefaMaquina setMachine(Maquina machine) {
        this.machine = machine; 
        return this;
    }
    
    /**
     * Define a hora de inicio de trabalho da máquina.
     * @param startTime
     * @return 
     */
    public TarefaMaquina setStartTime(LocalDateTime startTime) {
        transientStartAt = startTime;
        this.startAt = transientStartAt.format(LocalDateTimeAttributeConverter.patternFormater);
        return this;
    }
    
    /**
     * Define a hora de fim de trabalho da máquina.
     * @param finishTime
     * @return 
     */
    public TarefaMaquina setFinishTime(LocalDateTime finishTime) {
        this.finishAt = finishTime;
        return this;
    }

}