/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.services;

import com.all2you.models.clientes.Cliente;
import com.all2you.models.clientes.Morada;
import com.all2you.models.clientes.NotaCliente;
import com.all2you.models.clientes.contactos.ContactoCliente;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author mstevz
 */
public class ClientesService extends Service {

    public ClientesService() {
    }

    public Cliente getClient(int nif) {
        Session session = openSession();

        Cliente result = session.find(Cliente.class, nif);

        session.close();

        return result;
    }
    
    public List<Cliente> getAllClients(){

        String query = "from Cliente";
        String errorMessage = "SERVICE ERROR: Failed to retrieve all record from \"Cliente\". \r\n";

        return customGetQuery(query, errorMessage);
    }

    public List<Morada> getAllAddresses() {
        String query = "from Morada";
        String errorMessage = "SERVICE ERROR: Failed to retrieve all record from \"Cliente\". \r\n";

        return customGetQuery(query, errorMessage);
    }

    public List<Morada> getAllAddressesByClient(int nif) {
        String query = "from Morada "
                + "WHERE nif = " + nif;

        String errorMessage = "SERVICE ERROR: Failed to retrieve all addresses record from \"Cliente\". \r\n";

        return customGetQuery(query, errorMessage);
    }

    public List<NotaCliente> getAllNotesByClient(int nif) {
        String query = "from NotaCliente "
                + "WHERE nif = " + nif;
        String errorMessage = "SERVICE ERROR: Failed to retrieve \"NoteCliente\". records from client with following id: " + nif + " \r\n";

        return customGetQuery(query, errorMessage);
    }

    public boolean createClient(Cliente client) {
        return super.create(client);
    }

    public boolean createAddress(Morada address) {
        boolean result = false;

        if (hasFiscalAddress(address)) {
            System.out.println("SERVICE ERROR: Failed to create record on \"Address\". A fiscal_address is already assigned to cliente with NIF: " + address.getClient().getNif() + "\r\n");
        } else {
            result = super.create(address);
        }

        return result;
    }

    public boolean createNote(NotaCliente note) {
        return super.create(note);
    }

    public boolean updateClient(Cliente client) {
        return super.update(client);
    }

    public boolean updateAddress(Morada address) {
        boolean result = false;

        if (hasFiscalAddress(address)) {
            System.out.println("SERVICE ERROR: Failed to create record on \"Address\". A fiscal_address is already assigned to cliente with NIF: " + address.getClient().getNif() + "\r\n");
        } else {
            result = super.update(address);
        }

        return result;
    }

    public boolean updateNote(NotaCliente note) {
        return super.update(note);
    }

    public boolean deleteClient(Cliente client) {
        return super.delete(client);
    }

    public boolean deleteAddress(Morada address) {
        return super.delete(address);
    }

    public boolean deleteNote(NotaCliente note) {
        return super.delete(note);
    }

    private boolean hasFiscalAddress(Morada address) {
        Session s = openSession();

        boolean result = false;

        if (address.isIsFiscalAddress()) {

            String query = "from Morada WHERE "
                    + "nif = " + address.getClient().getNif()
                    + "AND morada_fiscal = 1";

            result = (s.createQuery(query).getResultList().size() > 0);
        }

        return result;
    }

}
