/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.tests;

import com.all2you.models.Operador;
import com.all2you.models.OperadorHorario;
import com.all2you.models.tarefas.TarefaOperador;
import com.all2you.models.TipoDia;
import com.all2you.services.OperacoesService;
import com.all2you.services.OperadoresService;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author mstevz
 */
public abstract class OperadoresServiceTest {

    private static OperadoresService service = new OperadoresService();
    private static OperacoesService ops = new OperacoesService();

    private static Operador operador = service.getOperator(1);
    
    /*
    public static TarefaOperador tarefa = service.getTask(new TarefaOperador(operador, ops.getOperation(1), 
                                                              Date.valueOf(LocalDate.of(2021, Month.MARCH, 2).atTime(0,0, 0, 0).toLocalDate()), 
                                                              Date.valueOf(LocalDate.of(2021, Month.MARCH, 2).atTime(0,0, 0, 0).toLocalDate())));
    */
    
    // new TarefaOperador(operador, ops.getOperation(1), Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()));
    public static void testGetAndListAllOperators() {
        List<Operador> records = service.getAllOperators();

        for (Operador o : records) {
            System.out.println(o.getName());
        }
    }

    public static void testGetAndListAllTasks() {
        List<TarefaOperador> records = service.getAllTasks();

        for (TarefaOperador to : records) {
            System.out.println(to.getStartAt());
        }

    }

    public static Operador testCreateOperator() {
        
        Operador op = new Operador("John Doe 3", false);
        
        service.createOperator(op);        
        return op;
    }
    
    public static TarefaOperador testCreateTask(Operador op) {
        
        TarefaOperador task = new TarefaOperador(op, ops.getOperation(1), LocalDateTime.now(), LocalDateTime.now());

        service.createTask(task);
        return task;
    }

    public static OperadorHorario testCreateSchedule(Operador op){
        
        OperadorHorario schedule = new OperadorHorario(op,  TipoDia.getInstance(TipoDia.Day.Segunda), Time.valueOf(LocalTime.now()), Time.valueOf(LocalTime.now()));
        service.createSchedule(schedule);
        return schedule;
    }
    
    public static void testUpdateOperator(Operador op) {
        op.setName("Teste Alterado Com Update");
        service.updateOperator(op);
    }

    public static void testUpdateTask(TarefaOperador task) {
        
        task = service.getTask(task);
                
        task.setFinishAt(LocalDateTime.now());
        
        service.updateTask(task);
    }

    public static void testUpdateSchedule(OperadorHorario schedule){
        schedule.setEndAt(Time.valueOf(LocalTime.now()));
        
        try{
            service.updateSchedule(schedule);
        }
        catch(Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }
    
    public static void testDeleteOperator(Operador op) {
        service.deleteOperator(op);
    }
    
    public static void testDeleteTask(TarefaOperador task){        
        service.deleteTask(task);
    }
    
    public static void testDeleteSchedule(OperadorHorario schedule){
        service.deleteSchedule(schedule);
    }
    
    public static void testAll() {
        testGetAndListAllOperators();
        testGetAndListAllTasks();

        Operador op = testCreateOperator();
        System.out.println("Operador criado com sucesso.");
        testUpdateOperator(op);
        System.out.println("Atualização do operador com sucesso.");
        
        OperadorHorario schedule = testCreateSchedule(operador);
        System.out.println("Horário criado com sucesso.");
        testUpdateSchedule(schedule); 
        System.out.println("Horário atualizado com sucesso.");
        
        TarefaOperador task = testCreateTask(op);
        System.out.println("Tarefa criada com sucesso.");
        testUpdateTask(task);
        System.out.println("Tarefa atualizada com sucesso.");
        
        testDeleteTask(task);
        System.out.println("Tarefa eliminada com sucesso.");
        testDeleteSchedule(schedule);
        System.out.println("Horário eliminado com sucesso.");
        testDeleteOperator(op);
        System.out.println("Operador eliminado com sucesso.");
    }
}
