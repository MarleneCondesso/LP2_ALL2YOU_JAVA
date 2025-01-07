package com.all2you.models.tarefas;

import com.all2you.models.operacoes.Operacao;
import com.all2you.models.Operador;
import com.all2you.utils.LocalDateTimeAttributeConverter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author mstevz
 */
@Entity
@Table(name="tarefa_operador")
public class TarefaOperador implements Serializable {
    
    @Id
    @ManyToOne
    @JoinColumn(name="id_operador", referencedColumnName="id_operador")
    private Operador operator;
    
    @Id
    @ManyToOne
    @JoinColumn(name="id_operacao", referencedColumnName="id_operacao")
    private Operacao operation;
    
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
    public TarefaOperador() { }
    
    /**
     * Construtor inicializador.
     * @param operator
     * @param operation
     * @param startAt
     * @param finishAt 
     */
    public TarefaOperador(Operador operator, Operacao operation, LocalDateTime startAt, LocalDateTime finishAt) {
        initializeProperties(operator, operation, startAt, finishAt);
    }
    
    /**
     * Atribui os valores ás propriedades de TarefaOperador.
     * @param operator
     * @param operation
     * @param startAt
     * @param finishAt 
     */
    private void initializeProperties(Operador operator, Operacao operation, LocalDateTime startAt, LocalDateTime finishAt){
        setOperator(operator);
        setOperation(operation);
        setStartAt(startAt);
        setFinishAt(finishAt);
    }
    
    /**
     * Retorna o operador associado à tarefa.
     * @return 
     */
    public Operador getOperator() {
        return operator;
    }
    
    /**
     * Retorna a operação associada à tarefa.
     * @return 
     */
    public Operacao getOperation() {
        return operation;
    }

    /**
     * Retorna a data e hora de inicio da tarefa.
     * @return 
     */
    public LocalDateTime getStartAt() {
        transientStartAt = LocalDateTime.parse(startAt, LocalDateTimeAttributeConverter.patternFormater);
        return transientStartAt;
    }
    
    /**
     * Retorna a data e hora do fim da tarefa.
     * @return 
     */
    public LocalDateTime getFinishAt() {
        return finishAt;
    }
    
    /**
     * Associa o operador à tarefa.
     * @param operator
     * @return 
     */
    public TarefaOperador setOperator(Operador operator) {
        this.operator = operator;
        return this;
    }   
    
    /**
     * Associa uma operação à tarefa.
     * @param operation
     * @return 
     */
    public TarefaOperador setOperation(Operacao operation) {
        this.operation = operation;
        return this;
    }
    
    /**
     * Define a data e hora de inicio da tarefa.
     * @param startAt
     * @return 
     */
    public TarefaOperador setStartAt(LocalDateTime startAt) {
        transientStartAt = startAt;
        this.startAt = transientStartAt.format(LocalDateTimeAttributeConverter.patternFormater);
        return this;
    }
    
    /**
     * Definie a data e hora de fim da tarefa.
     * @param finishAt
     * @return 
     */
    public TarefaOperador setFinishAt(LocalDateTime finishAt) {
        this.finishAt = finishAt;
        return this;
    }
    
}
