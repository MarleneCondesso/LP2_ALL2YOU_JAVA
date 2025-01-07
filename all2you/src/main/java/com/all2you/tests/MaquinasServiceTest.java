/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.tests;

import com.all2you.models.Maquina;
import com.all2you.models.MaquinaHorario;
import com.all2you.models.TipoDia;
import com.all2you.models.tarefas.TarefaMaquina;
import com.all2you.services.MaquinasService;
import com.all2you.services.OperacoesService;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author mstevz
 */
public abstract class MaquinasServiceTest {

    private static MaquinasService service = new MaquinasService();
    
    private static OperacoesService ops = new OperacoesService();
    
    private static Maquina machine = service.getMachine("MMPRBE019988", "Embalagem Acessorios");
    
    private static TarefaMaquina task = new TarefaMaquina(ops.getOperation(7), machine, LocalDateTime.now(), LocalDateTime.now());
    
    private static MaquinaHorario schedule = new MaquinaHorario(machine, TipoDia.getInstance(TipoDia.Day.Segunda), Time.valueOf("10:00:00"), Time.valueOf("13:00:00"));
    

    public static void testGetAndListAllMachines() {
        List<Maquina> records = service.getAllMachines();

        for (Maquina m : records) {
            System.out.println(m.getId());
        }
    }
    
    public static void testCreateMachine(){
        service.createMachine(machine);
    }
    
    public static void testUpdateMachine(){
        machine.setStatus(true);
        service.updateMachine(machine);
    }
    
    public static void testDeleteMachine(){
        service.deleteMachine(machine);
    }
    
    public static void testCreateSchedule(){
        service.createSchedule(schedule);
    }
    
    public static void testUpdateSchedule(){
        schedule.setEndAt(Time.valueOf("23:50:00"));
        
        try{
            service.updateSchedule(schedule);
        }
        catch(Exception e){
            System.out.println(e.getLocalizedMessage());
        }
        
    }
    
    public static void testeDeleteSchedule(){
        service.deleteSchedule(schedule);
    }
    
    public static void testCreateTask(){
        service.createTask(task);
    }
    
    public static void testUpdateTask(){
        service.updateTask(task);
    }
    
    public static void testDeleteTask(){
        service.deleteTask(task);
    }
    
    public static void testAll() {
        testGetAndListAllMachines();
       // testCreateMachine();
       //testCreateTask();
        testCreateSchedule();
        
      //testUpdateMachine();
        testUpdateSchedule();
      //testUpdateTask();
        
        //testDeleteTask();
        //testDeleteMachine();
    }
}
