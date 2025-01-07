package com.all2you.services;

import com.all2you.models.operacoes.Operacao;
import com.all2you.models.operacoes.TipoOperacao;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author diasi
 */
public class OperacoesService extends Service {

    public OperacoesService() {
        super();
    }

    /**
     * Cria um novo registo de Operação na base de dados.Retorna se operação foi
     * realizada com sucesso ou não.
     *
     * @param operacao
     * @return
     */
    public boolean createOperation(Operacao operacao) {
        return super.create(operacao);
    }

    /**
     * Cria um novo registo de Tipo de Operação na base de dados.Retorna se
     * operação foi realizada com sucesso ou não.
     *
     * @param tipoOperacao
     * @return
     */
    public boolean createTypeOperation(TipoOperacao tipoOperacao) {
        return super.create(tipoOperacao);
    }

    /**
     * Edita um registo de Operação na base de dados.Retorna se operação foi
     * realizada com sucesso ou não.
     *
     * @param operacao
     * @return
     */
    public boolean updateOperation(Operacao operacao) {
        return super.update(operacao);
    }

    /**
     * Edita um registo de Tipo de Operação na base de dados.Retorna se operação
     * foi realizada com sucesso ou não.
     *
     * @param tipoOperacao
     * @return
     */
    public boolean updateTypeOperation(TipoOperacao tipoOperacao) {
        return super.update(tipoOperacao);
    }

    /**
     * Elimina um registo de Operação na base de dados.Retorna se operação foi
     * realizada com sucesso ou não.
     *
     * @param operacao
     * @return
     */
    public boolean deleteOperation(Operacao operacao) {
        return super.delete(operacao);
    }

    /**
     * Elimina um registo de Tipo de Operação na base de dados.Retorna se
     * operação foi realizada com sucesso ou não.
     *
     * @param tipoOperacao
     * @return
     */
    public boolean deleteOperationType(TipoOperacao tipoOperacao) {
        return super.delete(tipoOperacao);
    }

    /**
     * Retorna o registo de Operação na base de dados com chave igual ao
     * parâmetro introduzido.
     *
     * @param id
     * @return
     */
    public Operacao getOperation(int id) {
        Session session = openSession();

        Operacao result = session.find(Operacao.class, id);

        session.close();

        return result;
    }

    /**
     * Retorna o registo de Tipo de Operação na base de dados com chave igual ao
     * parâmetro introduzido.
     *
     * @param idTpOperacao
     * @return
     */
    public TipoOperacao getOperationType(String idTpOperacao) {
        Session session = openSession();

        TipoOperacao result = session.find(TipoOperacao.class, idTpOperacao);

        session.close();

        return result;
    }

    /**
     * Retorna todos os registos de Operacao na base de dados.
     *
     * @return
     */
    public List<Operacao> getAllOperations() {

        List<Operacao> result = null;
        Session session = openSession();

        try {
            session.beginTransaction();
            result = session.createQuery("from Operacao").getResultList();
            session.getTransaction().commit();

        } catch (Exception ex) {
            System.out.println("SERVICE ERROR: Failed to retrieve all record from \"Operacao\". \r\n" + ex.getMessage());
        } finally {
            session.close();
        }

        return result;
    }

    /**
     * Retorna todos os registos de TipoOperacao na base de dados.
     *
     * @return
     */
    public List<TipoOperacao> getAllOperationTypes() {

        List<TipoOperacao> result = null;
        Session session = openSession();

        try {
            session.beginTransaction();
            result = session.createQuery("from TipoOperacao").getResultList();
            session.getTransaction().commit();

        } catch (Exception ex) {
            System.out.println("SERVICE ERROR: Failed to retrieve all record from \"TipoOperacao\". \r\n" + ex.getMessage());
        } finally {
            session.close();
        }

        return result;
    }

}
