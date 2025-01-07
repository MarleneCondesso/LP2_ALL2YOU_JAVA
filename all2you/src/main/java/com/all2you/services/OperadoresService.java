package com.all2you.services;

import com.all2you.models.Operador;
import com.all2you.models.tarefas.TarefaOperador;
import com.all2you.models.OperadorHorario;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author mstevz
 */
public class OperadoresService extends Service {

    public OperadoresService() {
        super();
    }

    /**
     * Cria um novo registo de Operador na base de dados.Retorna se operação foi
     * realizada com sucesso ou não.
     *
     * @param operador
     * @return
     */
    public boolean createOperator(Operador operador) {
        return super.create(operador);
    }

    /**
     * Cria um novo registo de Tarefa de operador na base de dados.Retorna se
     * operação foi realizada com sucesso ou não.
     *
     * @param tarefa
     * @return
     */
    public boolean createTask(TarefaOperador tarefa) {
        return super.create(tarefa);
    }

    /**
     * Cria um novo registo de Horário de operador na base de dados.Retorna se
     * operação foi realizada com sucesso ou não.
     *
     * @param schedule
     * @return
     */
    public boolean createSchedule(OperadorHorario schedule) {
        return super.create(schedule);
    }

    /**
     * Retorna o registo de Operador na base de dados com chave igual ao
     * parâmetro introduzido.
     *
     * @param id
     * @return
     */
    public Operador getOperator(int id) {
        Session session = openSession();

        Operador result = session.find(Operador.class, id);

        session.close();

        return result;
    }

    /**
     * Retorna o registo de Tarefa de Operador na base de dados com chave igual
     * ao parâmetro introduzido.
     *
     * @param task
     * @return
     */
    public TarefaOperador getTask(TarefaOperador task) {
        Session session = openSession();
        TarefaOperador result = null;

        try {
            session.beginTransaction();
            result = session.find(TarefaOperador.class, task);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("SERVICE ERROR: Failed to retrieve the record from \"OperadorServices:Tasks\". \r\n" + ex.getMessage());
        } finally {
            session.close();
        }

        return result;
    }

    /**
     * Retorna todos os registos de Operador na base de dados.
     *
     * @return
     */
    public List<Operador> getAllOperators() {

        List<Operador> result = null;
        Session session = openSession();

        try {
            session.beginTransaction();
            result = session.createQuery("from Operador").getResultList();
            session.getTransaction().commit();

        } catch (Exception ex) {
            System.out.println("SERVICE ERROR: Failed to retrieve all record from \"Operador\". \r\n" + ex.getMessage());
        } finally {
            session.close();
        }

        return result;
    }

    /**
     * Retorna todos os registos de Tarefas de Operador na base de dados.
     *
     * @return
     */
    public List<TarefaOperador> getAllTasks() {
        List<TarefaOperador> result = null;
        Session session = openSession();

        try {
            session.beginTransaction();
            result = session.createQuery("from TarefaOperador", TarefaOperador.class).getResultList();
            session.getTransaction().commit();

        } catch (Exception ex) {
            System.out.println("SERVICE ERROR: Failed to retrieve all record from \"OperadorServices:Tasks\". \r\n" + ex.getMessage());
        } finally {
            session.close();
        }

        return result;
    }

    /**
     * Retorna todos os registos de Horário de Operador na base de dados com
     * chave igual ao parâmetro introduzido.
     *
     * @param op
     * @return
     */
    public List<OperadorHorario> getAllSchedules(Operador op) {
        Session session = openSession();
        List<OperadorHorario> result = null;

        try {
            session.beginTransaction();
            result = session.createQuery("from OperadorHorario", OperadorHorario.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("SERVICE ERROR: Failed to retrieve the record from \"OperadorHorario\". \r\n" + ex.getMessage());
        } finally {
            session.close();
        }

        return result;
    }

    /**
     * Edita um registo de Operador na base de dados. Retorna se operação foi
     * realizada com sucesso ou não.
     *
     * @param operador
     * @return
     */
    public boolean updateOperator(Operador operador) {
        return super.update(operador);
    }

    /**
     * Edita um registo de Tarefa de Operador na base de dados. Retorna se
     * operação foi realizada com sucesso ou não.
     *
     * @param task
     * @return
     */
    public boolean updateTask(TarefaOperador task) {
        return super.update(task);
    }

    /**
     * Edita um registo de Horárip na base de dados. Retorna se operação foi
     * realizada com sucesso ou não.
     *
     * @param schedule
     * @return
     */
    public boolean updateSchedule(OperadorHorario schedule) throws Exception {

        boolean result = false;

        if (schedule.getEndAt().toLocalTime().isAfter(schedule.getBeginAt().toLocalTime())) {
            result = super.update(schedule);
        } else {
            throw new Exception("SERVICE ERROR: Cannot define \"OperadorHorario@endAt\" as smaller than \"OperadorHorario@beginAt\"");
        }

        return result;
    }

    /**
     * Elimina um registo de Operador na base de dados. Retorna se operação foi
     * realizada com sucesso ou não.
     *
     * @param operador
     * @return
     */
    public boolean deleteOperator(Operador operador) {
        return super.delete(operador);
    }

    /**
     * Elimina um registo de tarefa de Operador na base de dados. Retorna se
     * operação foi realizada com sucesso ou não.
     *
     * @param task
     * @return
     */
    public boolean deleteTask(TarefaOperador task) {
        return super.delete(task);
    }

    /**
     * Elimina um registo de horário de Operador na base de dados. Retorna se
     * operação foi realizada com sucesso ou não.
     *
     * @param schedule
     * @return
     */
    public boolean deleteSchedule(OperadorHorario schedule) {
        return super.delete(schedule);
    }
}
