/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.all2you.models.*;
import com.all2you.models.artigos.*;
import com.all2you.models.clientes.*;
import com.all2you.models.clientes.contactos.*;
import com.all2you.models.encomendas.*;
import com.all2you.models.operacoes.*;
import com.all2you.models.tarefas.*;
import java.util.AbstractList;
import java.util.List;

/**
 *
 * @author mstevz
 */
public abstract class Service<T> {
    
    private static Configuration dbConfiguration;
    protected SessionFactory sessionFactory;

    protected Service() {
        sessionFactory = dbConfiguration.buildSessionFactory();
    }

    public static void setConfiguration(String config) {
        dbConfiguration = new Configuration().configure(config); // "hibernate.cfg.xml"
        bindModels(dbConfiguration);
    }

    private static void bindModels(Configuration cfg) {
        cfg.addAnnotatedClass(TipoDia.class)
           .addAnnotatedClass(Maquina.class)
           .addAnnotatedClass(MaquinaHorario.class)
           .addAnnotatedClass(Operador.class)
           .addAnnotatedClass(OperadorHorario.class)
           .addAnnotatedClass(Componente.class)
           .addAnnotatedClass(Produto.class)
           .addAnnotatedClass(TipoUnidade.class)
           .addAnnotatedClass(Morada.class)
           .addAnnotatedClass(Cliente.class)
           .addAnnotatedClass(NotaCliente.class)
           .addAnnotatedClass(ContactoCliente.class)
           .addAnnotatedClass(ObservacaoContactoCliente.class)
           .addAnnotatedClass(TipoContacto.class)
           .addAnnotatedClass(Encomenda.class)
           .addAnnotatedClass(EstadoEncomenda.class)
           .addAnnotatedClass(LinhaEncomenda.class)
           .addAnnotatedClass(TipoDesconto.class)
           .addAnnotatedClass(TipoEstado.class)
           .addAnnotatedClass(Operacao.class)
           .addAnnotatedClass(TipoOperacao.class)
           .addAnnotatedClass(TarefaMaquina.class)
           .addAnnotatedClass(TarefaOperador.class);
    }

    /**
     * Cria um novo registo na base de dados com as informações passadas no
     * modelo.
     *
     * @param <T> Tipo genérico que deve receber um dos Models anotados.
     * @param model
     */
    protected <T> boolean create(T model) {
        Session session = openSession();
        boolean wasSuccessful = false;

        try {
            session.beginTransaction();
            session.save(model);
            session.getTransaction().commit();
            
            wasSuccessful = true;
        } catch (Exception ex) {
            System.out.println("SERVICE ERROR: Could not create \"" + model.getClass().getSimpleName() + "\" model record. \r\n" + ex.getMessage());
        } finally {
            session.close();
        }

        return wasSuccessful;
    }

    protected <T> boolean update(T model) {
        Session session = openSession();
        boolean wasSuccessful = false;

        try {
            session.beginTransaction();
            session.update(model);
            session.getTransaction().commit();
            wasSuccessful = true;
        } catch (Exception ex) {
            System.out.println("SERVICE ERROR: Could not update \"" + model.getClass().getSimpleName() + "\" model record. \r\n" + ex.getMessage());
        } finally {
            session.close();
        }

        return wasSuccessful;
    }

    /**
     * Apaga um registo na base de dados que seja igual ao modelo providenciado.
     *
     * @param <T> Tipo genérico que deve receber um dos Modelos anotados.
     * @param model
     */
    protected <T> boolean delete(T model) {
        Session session = openSession();
        boolean wasSuccessful = false;

        try {
            session.beginTransaction();
            session.delete(model);
            session.getTransaction().commit();
            
            wasSuccessful = true;
        } catch (Exception ex) {
            System.out.println("SERVICE ERROR: Could not delete \"" + model.getClass().getSimpleName() + "\" model record. \r\n" + ex.getMessage());
        } finally {
            session.close();
        }

        return wasSuccessful;

    }

    protected Session openSession() {
        return sessionFactory.openSession();
    }
    
    protected List customGetQuery(String query, String errorMessage){
        Session session = openSession();
        
        List<Morada> result = null;
        
        try {
            session.beginTransaction();
            result = session.createQuery(query).getResultList();
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println(errorMessage + ex.getMessage());
        } finally {
            session.close();
        }
        System.out.println(result.size());
        return result;
    }

}

