
package com.all2you.services;

import com.all2you.models.encomendas.Encomenda;
import com.all2you.models.encomendas.LinhaEncomenda;
import com.all2you.models.encomendas.TipoDesconto;
import com.all2you.models.encomendas.TipoEstado;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author diasi
 */
public class EncomendasService extends Service {

    public EncomendasService() {
        super();
    }

    /**
     * Retorna o registo de Encomenda na base de dados com chave igual ao
     * parâmetro introduzido.
     *
     * @param id
     * @return
     */
    public Encomenda getOrder(String id) {
        Session session = openSession();

        Encomenda result = session.find(Encomenda.class, id);

        session.close();

        return result;
    }

    /**
     * Retorna o registo de Linha de Encomenda na base de dados com chave igual
     * ao parâmetro introduzido.
     *
     * @param id
     * @return
     */
    public LinhaEncomenda getOrderLine(String id) {
        Session session = openSession();

        LinhaEncomenda result = session.find(LinhaEncomenda.class, id);

        session.close();

        return result;
    }

    /**
     * Retorna o registo de Tipo de Desconto na base de dados com chave igual ao
     * parâmetro introduzido.
     *
     * @param id
     * @return
     */
    public TipoDesconto getDiscountType(Integer id) {
        Session session = openSession();

        TipoDesconto result = session.find(TipoDesconto.class, id);

        session.close();

        return result;
    }

    /**
     * Retorna o registo de Tipo de Estado na base de dados com chave igual ao
     * parâmetro introduzido.
     *
     * @param id
     * @return
     */
    public TipoEstado getStateType(Integer id) {
        Session session = openSession();

        TipoEstado result = session.find(TipoEstado.class, id);

        session.close();

        return result;
    }

    /**
     * Retorna todos os registos de Encomenda na base de dados.
     *
     * @return
     */
    public List<Encomenda> getAllOrders() {

        List<Encomenda> result = null;
        Session session = openSession();

        try {
            session.beginTransaction();
            result = session.createQuery("from Encomenda").getResultList();
            session.getTransaction().commit();

        } catch (Exception ex) {
            System.out.println("SERVICE ERROR: Failed to retrieve all record from \"Encomenda\". \r\n" + ex.getMessage());
        } finally {
            session.close();
        }

        return result;
    }
        /**
     * Retorna todos os registos de Linha de Encomenda referente ao id_encomenda na base de dados.
     *
     * @return
     */
//    public List<LinhaEncomenda> getAllOrdersLineByEncomenda(String id) {
//        String query = "from LinhaEncomenda "
//                     + "WHERE id_encomenda = " + id;
//                    
//        String errorMessage = "SERVICE ERROR: Failed to retrieve all record from \"LinhaEncomenda\". \r\n";
//        
//        return customGetQuery(query, errorMessage);
//    
//    }    
    /**
     * Retorna todos os registos de Tipos de Desconto na base de dados.
     *
     * @return
     */
    public List<TipoDesconto> getAllDiscountTypes() {

        List<TipoDesconto> result = null;
        Session session = openSession();

        try {
            session.beginTransaction();
            result = session.createQuery("from TipoDesconto").getResultList();
            session.getTransaction().commit();

        } catch (Exception ex) {
            System.out.println("SERVICE ERROR: Failed to retrieve all record from \"TipoDesconto\". \r\n" + ex.getMessage());
        } finally {
            session.close();
        }

        return result;
    }

    /**
     * Retorna todos os registos de Tipos de Estados na base de dados.
     *
     * @return
     */
    public List<TipoEstado> getAllStatesTypes() {

        List<TipoEstado> result = null;
        Session session = openSession();

        try {
            session.beginTransaction();
            result = session.createQuery("from TipoEstado").getResultList();
            session.getTransaction().commit();

        } catch (Exception ex) {
            System.out.println("SERVICE ERROR: Failed to retrieve all record from \"TipoEstado\". \r\n" + ex.getMessage());
        } finally {
            session.close();
        }

        return result;
    }

    /**
     * Cria um novo registo de Encomenda na base de dados.Retorna se operação
     * foi realizada com sucesso ou não.
     *
     * @param encomenda
     * @return
     */
    public boolean createOrder(Encomenda encomenda) {
        return super.create(encomenda);
    }

    /**
     * Cria um novo registo de Linha de Encomenda na base de dados.Retorna se
     * operação foi realizada com sucesso ou não.
     *
     * @param encomendaProduto
     * @return
     */
    public boolean createOrderLine(LinhaEncomenda encomendaProduto) {
        return super.create(encomendaProduto);
    }

    /**
     * Cria um novo registo de Tipo de Desconto na base de dados.Retorna se
     * operação foi realizada com sucesso ou não.
     *
     * @param tipoDesconto
     * @return
     */
    public boolean createDiscountType(TipoDesconto tipoDesconto) {
        return super.create(tipoDesconto);
    }

    /**
     * Cria um novo registo de Tipo de Estado na base de dados. Retorna se
     * operação foi realizada com sucesso ou não.
     *
     * @param tipoEstado
     * @return
     */
    public boolean createStateType(TipoEstado tipoEstado) {
        return super.create(tipoEstado);
    }

    /**
     * Edita um registo de Encomenda na base de dados. Retorna se operação foi
     * realizada com sucesso ou não.
     *
     * @param encomenda
     * @return
     */
    public boolean updateOrder(Encomenda encomenda) {
        return super.update(encomenda);
    }

    /**
     * Edita um registo de Linha de Encomenda na base de dados. Retorna se
     * operação foi realizada com sucesso ou não.
     *
     * @param encomendaProduto
     * @return
     */
    public boolean updateOrderLine(LinhaEncomenda encomendaProduto) {
        return super.update(encomendaProduto);
    }

    /**
     * Edita um registo de Tipo de Desconto na base de dados.Retorna se operação
     * foi realizada com sucesso ou não.
     *
     * @param tipoDesconto
     * @return
     */
    public boolean updateDiscountType(TipoDesconto tipoDesconto) {
        return super.update(tipoDesconto);
    }

    /**
     * Edita um registo de Tipo de Estado na base de dados.Retorna se operação
     * foi realizada com sucesso ou não.
     *
     * @param tipoEstado
     * @return
     */
    public boolean updateStateType(TipoEstado tipoEstado) {
        return super.update(tipoEstado);
    }

    /**
     * Elimina um registo de Encomenda na base de dados.Retorna se operação foi
     * realizada com sucesso ou não.
     *
     * @param encomenda
     * @return
     */
    public boolean deleteOrder(Encomenda encomenda) {
        return super.delete(encomenda);
    }

    /**
     * Elimina um registo de Linha de Encomenda na base de dados.Retorna se
     * operação foi realizada com sucesso ou não.
     *
     * @param encomendaProduto
     * @return
     */
    public boolean deleteOrderLine(LinhaEncomenda encomendaProduto) {
        return super.delete(encomendaProduto);
    }

    /**
     * Elimina um registo de Tipo de Desconto na base de dados.Retorna se
     * operação foi realizada com sucesso ou não.
     *
     * @param tipoDesconto
     * @return
     */
    public boolean deleteDiscountType(TipoDesconto tipoDesconto) {
        return super.delete(tipoDesconto);
    }

    /**
     * Elimina um registo de Tipo de Estado na base de dados.Retorna se operação
     * foi realizada com sucesso ou não.
     *
     * @param tipoEstado
     * @return
     */
    public boolean deleteStateType(TipoEstado tipoEstado) {
        return super.delete(tipoEstado);
    }

}
