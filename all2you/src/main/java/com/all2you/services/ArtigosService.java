package com.all2you.services;

import com.all2you.models.artigos.*;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author diasi
 */
public class ArtigosService extends Service {

    public ArtigosService() {
        super();
    }

    /**
     * Cria um novo registo de Produto na base de dados. Retorna se operação foi
     * realizada com sucesso ou não.
     *
     * @param produto
     * @return
     */
    public boolean createProduct(Produto produto) {
        return super.create(produto);
    }

    /**
     * Cria um novo registo de Componente na base de dados.Retorna se operação
     * foi realizada com sucesso ou não.
     *
     * @param componente
     * @return
     */
    public boolean createComponent(Componente componente) {
        return super.create(componente);
    }

    /**
     * Cria um novo registo de Tipo de Unidade na base de dados.Retorna se
     * operação foi realizada com sucesso ou não.
     *
     * @param tipoUnidade
     * @return
     */
    public boolean createUnitType(TipoUnidade tipoUnidade) {
        return super.create(tipoUnidade);
    }

    /**
     * Edita um registo de Produto na base de dados. Retorna se operação foi
     * realizada com sucesso ou não.
     *
     * @param produto
     * @return
     */
    public boolean updateProduct(Produto produto) {
        return super.update(produto);
    }

    /**
     * Edita um registo de Componente na base de dados. Retorna se operação foi
     * realizada com sucesso ou não.
     *
     * @param componente
     * @return
     */
    public boolean updateComponent(Componente componente) {
        return super.update(componente);
    }

    /**
     * Edita um registo de Tipo de Unidade na base de dados. Retorna se operação
     * foi realizada com sucesso ou não.
     *
     * @param tipoUnidade
     * @return
     */
    public boolean updateUnitType(TipoUnidade tipoUnidade) {
        return super.update(tipoUnidade);
    }

    /**
     * Elimina um registo de Produto na base de dados. Retorna se operação foi
     * realizada com sucesso ou não.
     *
     * @param produto
     * @return
     */
    public boolean deleteProduct(Produto produto) {
        return super.delete(produto);
    }

    /**
     * Elimina um registo de Componente na base de dados. Retorna se operação
     * foi realizada com sucesso ou não.
     *
     * @param componente
     * @return
     */
    public boolean deleteComponent(Componente componente) {
        return super.delete(componente);
    }

    /**
     * Elimina um registo de Tipo de Unidade na base de dados. Retorna se
     * operação foi realizada com sucesso ou não.
     *
     * @param tipoUnidade
     * @return
     */
    public boolean deleteUnitType(TipoUnidade tipoUnidade) {
        return super.delete(tipoUnidade);
    }

    /**
     * Retorna o registo de Produto na base de dados com chave igual ao
     * parâmetro introduzido.
     *
     * @param ref
     * @param version
     * @return
     */
    public Produto getProduct(String ref, String version) {
        Session session = openSession();

        Produto product = new Produto();
        product.setReference(ref)
                .setVersion(version);

        Produto result = session.find(Produto.class, product);

        session.close();

        return result;
    }

    /**
     * Retorna o registo de Componente na base de dados com chave igual ao
     * parâmetro introduzido.
     *
     * @param ref
     * @param version
     * @return
     */
    public Componente getComponent(String ref, String version) {

        Session session = openSession();
        Componente componente = new Componente();
        componente.setReference(ref)
                .setVersion(version);

        Componente result = session.find(Componente.class, componente);

        session.close();

        return result;
    }

    /**
     * Retorna o registo de Tipo de Unidade na base de dados com chave igual ao
     * parâmetro introduzido.
     *
     * @param id
     * @return
     */
    public TipoUnidade getUnitType(String id) {

        Session session = openSession();

        TipoUnidade result = session.find(TipoUnidade.class, id);

        session.close();

        return result;
    }

    /**
     * Retorna todos os registos de Produto na base de dados.
     *
     * @return
     */
    public List<Produto> getAllProducts() {

        List<Produto> result = null;
        Session session = openSession();

        try {
            session.beginTransaction();
            result = session.createQuery("from Produto").getResultList();
            session.getTransaction().commit();

        } catch (Exception ex) {
            System.out.println("SERVICE ERROR: Failed to retrieve all record from \"Produto\". \r\n" + ex.getMessage());
        } finally {
            session.close();
        }

        return result;
    }

    /**
     * Retorna todos os registos de Componente na base de dados.
     *
     * @return
     */
    public List<Componente> getAllComponents() {

        List<Componente> result = null;
        Session session = openSession();

        try {
            session.beginTransaction();
            result = session.createQuery("from Componente").getResultList();
            session.getTransaction().commit();

        } catch (Exception ex) {
            System.out.println("SERVICE ERROR: Failed to retrieve all record from \"Componente\". \r\n" + ex.getMessage());
        } finally {
            session.close();
        }

        return result;
    }

    /**
     * Retorna todos os registos de Tipo de Unidade na base de dados.
     *
     * @return
     */
    public List<TipoUnidade> getAllUnitTypes() {

        List<TipoUnidade> result = null;
        Session session = openSession();

        try {
            session.beginTransaction();
            result = session.createQuery("from TipoUnidade").getResultList();
            session.getTransaction().commit();

        } catch (Exception ex) {
            System.out.println("SERVICE ERROR: Failed to retrieve all record from \"TipoUnidade\". \r\n" + ex.getMessage());
        } finally {
            session.close();
        }

        return result;
    }
}
