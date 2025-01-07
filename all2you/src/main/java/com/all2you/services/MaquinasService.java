package com.all2you.services;

import com.all2you.models.Maquina;
import com.all2you.models.MaquinaHorario;
import com.all2you.models.tarefas.TarefaMaquina;
import com.all2you.models.operacoes.TipoOperacao;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author mstevz
 */
public class MaquinasService extends Service {

    /**
     * Retorna o registo de Máquina na base de dados com chave igual ao
     * parâmetro introduzido.
     *
     * @param id
     * @param operationId
     * @return
     */
    public Maquina getMachine(String id, String operationId) {
        Session session = openSession();

        Maquina result = new Maquina();

        result.setId(id);
        result.setOperationType(new TipoOperacao().setId(operationId));

        result = session.find(Maquina.class, result);

        session.close();

        return result;
    }

    /**
     * Retorna o registo de tarefa de Máquina na base de dados com chave igual
     * ao parâmetro introduzido.
     *
     * @param task
     * @return
     */
    public TarefaMaquina getTask(TarefaMaquina task) {
        Session session = openSession();
        TarefaMaquina result = null;

        try {
            result = session.find(TarefaMaquina.class, task);
        } catch (Exception ex) {
            System.out.println("SERVICE ERROR: Failed to retrieve record from \"TarefaMaquina\". \r\n" + ex.getMessage());
        } finally {
            session.close();
        }

        session.close();

        return result;
    }

    /**
     * Retorna todos os registos de Horário de Máquina na base de dados com
     * chave igual ao parâmetro introduzido.
     *
     * @param maq
     * @return
     */
    public List<MaquinaHorario> getAllSchedules(Maquina maq) {
        Session session = openSession();
        List<MaquinaHorario> result = null;

        try {
            session.beginTransaction();
            result = session.createQuery("from MaquinaHorario", MaquinaHorario.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("SERVICE ERROR: Failed to retrieve all record from \"HorarioMaquina\". \r\n" + ex.getMessage());
        } finally {
            session.close();
        }

        return result;
    }

    /**
     * Retorna todos os registos de Máquinhas na base de dados.
     *
     * @return
     */
    public List<Maquina> getAllMachines() {

        List<Maquina> result = null;
        Session session = openSession();

        try {
            session.beginTransaction();
            result = session.createQuery("from Maquina").getResultList();
            session.getTransaction().commit();

        } catch (Exception ex) {
            System.out.println("SERVICE ERROR: Failed to retrieve all record from \"Maquina\". \r\n" + ex.getMessage());
        } finally {
            session.close();
        }

        return result;
    }

    /**
     * Retorna todos os registos de Tarefas de Máquinas na base de dados.
     *
     * @return
     */
    public List<TarefaMaquina> getAllTasks() {

        List<TarefaMaquina> result = null;
        Session session = openSession();

        try {
            session.beginTransaction();
            result = session.createQuery("from TarefaMaquina").getResultList();
            session.getTransaction().commit();

        } catch (Exception ex) {
            System.out.println("SERVICE ERROR: Failed to retrieve all record from \"TerafaMaquina\". \r\n" + ex.getMessage());
        } finally {
            session.close();
        }

        return result;
    }

    /**
     * Cria um novo registo de Máquina na base de dados. Retorna se operação foi
     * realizada com sucesso ou não.
     *
     * @param machine
     * @return
     */
    public boolean createMachine(Maquina machine) {
        return super.create(machine);
    }

    /**
     * Cria um novo registo de Horário na base de dados. Retorna se operação foi
     * realizada com sucesso ou não.
     *
     * @param schedule
     * @return
     */
    public boolean createSchedule(MaquinaHorario schedule) {
        return super.create(schedule);
    }

    /**
     * Cria um novo registo de Tarefa de Máruina na base de dados. Retorna se
     * operação foi realizada com sucesso ou não.
     *
     * @param task
     * @return
     */
    public boolean createTask(TarefaMaquina task) {
        return super.create(task);
    }

    /**
     * Edita um registo de Máquina na base de dados.Retorna se operação foi
     * realizada com sucesso ou não.
     *
     * @param machine
     * @return
     */
    public boolean updateMachine(Maquina machine) {
        return super.update(machine);
    }

    /**
     * Edita um registo de Horário na base de dados.Retorna se operação foi
     * realizada com sucesso ou não.
     *
     * @param schedule
     * @return
     */
    public boolean updateSchedule(MaquinaHorario schedule) throws Exception {
        boolean result = false;

        if (schedule.getEndAt().toLocalTime().isAfter(schedule.getBeginAt().toLocalTime())) {
            result = super.update(schedule);
        } else {
            throw new Exception("SERVICE ERROR: Cannot define \"MaquinaHorario@endAt\" as smaller than \"MaquinaHorario@beginAt\"");
        }

        return result;
    }

    /**
     * Edita um registo de Tarefa de Máquina na base de dados.Retorna se
     * operação foi realizada com sucesso ou não.
     *
     * @param task
     * @return
     */
    public boolean updateTask(TarefaMaquina task) {

        boolean wasSucessful;

        if (task.getMachine().getOperationType() != task.getOperation().getOperationType()) {
            System.out.println("SERVICE ERROR: Could not create Task due to Machine.OperationType is not equal to Operation.OperationType.");
            wasSucessful = false;
        } else {
            wasSucessful = super.update(task);
        }

        return wasSucessful;
    }

    /**
     * Elimina um registo de Máquina na base de dados.Retorna se operação foi
     * realizada com sucesso ou não.
     *
     * @param machine
     * @return
     */
    public boolean deleteMachine(Maquina machine) {
        return super.delete(machine);
    }

    /**
     * Elimina um registo de Horário na base de dados.Retorna se operação foi
     * realizada com sucesso ou não.
     *
     * @param schedule
     * @return
     */
    public boolean deleteSchedule(MaquinaHorario schedule) {
        return super.delete(schedule);
    }

    /**
     * Elimina um registo de Tarefa de Máquina na base de dados.Retorna se
     * operação foi realizada com sucesso ou não.
     *
     * @param task
     * @return
     */
    public boolean deleteTask(TarefaMaquina task) {
        return super.delete(task);
    }

}
