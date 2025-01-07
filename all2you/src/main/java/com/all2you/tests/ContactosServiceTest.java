package com.all2you.tests;


import com.all2you.models.clientes.Cliente;
//import com.all2you.models.clientes.Morada;
//import com.all2you.models.clientes.NotaCliente;
import com.all2you.models.clientes.contactos.ContactoCliente;
import com.all2you.models.clientes.contactos.TipoContacto;
import com.all2you.services.ContactosService;
import java.util.List;


/**
 *
 * @author mstevz
 */
public class ContactosServiceTest {
    
    private static ContactosService cs = new ContactosService();
    
    public boolean testCreateContacat(){
        return cs.createContact(new ContactoCliente("1234567", new TipoContacto().setType("teste"), new Cliente().setNif(123)));
    }
    
    public boolean testCreateContactType(){
        return cs.createContactType(new TipoContacto("teste", "teste"));
    }
   
    
    public void testGetAllContactsByClient(){
        cs.getAllContactsByClient(123);
    }
    
    public void testGetAllContactType(){
       List<TipoContacto> l = cs.getAllContactTypes();
       
       for(int i = 0; i < l.size(); i++){
           System.out.println(l.get(i).getType());
       }
    } 
    
    public boolean testUpdateContact(){
        ContactoCliente c = cs.getAllContactsByClient(123).get(0);
        
        c.setContact("123123123");
        
        return cs.updateContact(c);
    }
    
    public boolean testUpdateContactType(){
        TipoContacto c = cs.getContactType("teste");
        
        c.setDescription("fui atualizado");
        
        return cs.updateContactType(c);
    }
   
    public boolean testDeleteContact(){
        return cs.deleteContact(cs.getAllContactsByClient(123).get(0));
    }
    
    public boolean testDeleteContactType(){
        return cs.deleteContactType(cs.getContactType("teste"));
    }
    
    
    public void testAll(){
        
        boolean cct = testCreateContactType(),
                cc = testCreateContacat(),
                uc = testUpdateContact(),
                uct = testUpdateContactType(),
                dc = testDeleteContact(),
                dct = testDeleteContactType();
        
        System.out.println("Teste criar contacto: " + cc);
        System.out.println("Teste criar tipo contacto: " + cct);
        System.out.println("Teste atualizar contacto: " + uc);
        System.out.println("Teste atualizar tipo contacto: " + uct);
        System.out.println("Teste eliminar contacto: " + dc);
        System.out.println("Teste eliminar tipo contacto: " + dct);
    }
    
    
   
    
}
