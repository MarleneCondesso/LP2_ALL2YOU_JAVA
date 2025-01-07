/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.services;

import com.all2you.models.clientes.contactos.ContactoCliente;
import com.all2you.models.clientes.contactos.ObservacaoContactoCliente;
import com.all2you.models.clientes.contactos.TipoContacto;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author mstevz
 */
public class ContactosService extends Service {
    
    // Resta fazer observacaoContactoCliente
    
    public List<ContactoCliente> getAllContacts(){
        String query = "from ContactoCliente";
        String errorMessage = "SERVICE ERROR: Failed to retrieve all record from \"ContactoCliente\". \r\n";
        
        return customGetQuery(query, errorMessage);
    }
    
    public List<ContactoCliente> getAllContactsByClient(int nif){
        
        String query = "from ContactoCliente "
                     + "WHERE nif = " + nif;
                    
        String errorMessage = "SERVICE ERROR: Failed to retrieve all record from \"Cliente\". \r\n";
        
        return customGetQuery(query, errorMessage);
    }
    
    public List<TipoContacto> getAllContactTypes(){
        String query = "from TipoContacto";
        String errorMessage = "SERVICE ERROR: Failed to retrieve all record from \"TipoContacto\". \r\n";
        
        return customGetQuery(query, errorMessage);
    }
    
    public List<ObservacaoContactoCliente> getAllObservations(ContactoCliente contacto){
        String query = "from ObservacaoContactoCliente"
                     + "WHERE contacto = " + contacto.getContact()
                     + "AND tipo_contacto = " + contacto.getContactType().getType();
        
        String errorMessage = "SERVICE ERROR: Failed to retrieve records from \"ObservacaoContactoCliente\" \r\n";
        
        return customGetQuery(query, errorMessage);
    }
    
    public TipoContacto getContactType(String type){
        Session s = openSession();
        
        TipoContacto result = s.find(TipoContacto.class, type);
        
        s.close();
        
        return result;
    }
    
    public boolean createContact(ContactoCliente contact){
        return super.create(contact);
    }
    
    public boolean createContactType(TipoContacto contactType){
        return super.create(contactType);
    }
    
    public boolean updateContact(ContactoCliente contact){
        return super.update(contact);
    }
    
    public boolean updateContactType(TipoContacto contactType){
        return super.update(contactType);
    }
    
    public boolean deleteContact(ContactoCliente contact){
        return super.delete(contact);
    }
    
    public boolean deleteContactType(TipoContacto contactType){
        return super.delete(contactType);
    }
    
}
