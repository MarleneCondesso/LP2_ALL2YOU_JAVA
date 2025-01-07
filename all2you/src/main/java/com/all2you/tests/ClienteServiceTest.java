package com.all2you.tests;


import com.all2you.models.clientes.Cliente;
import com.all2you.models.clientes.Morada;
import com.all2you.models.clientes.NotaCliente;
import com.all2you.services.ClientesService;
import java.util.List;


/**
 *
 * @author mstevz
 */
public class ClienteServiceTest {
    
    private static ClientesService cs = new ClientesService();
    
    public boolean testCreateCliente(){
        Cliente c = new Cliente(999, "John Doe");
        
        return cs.createClient(c);
    }
    
    public boolean testCreateAddress(){
        Morada m = new Morada("teste rua da massa", "1111-222", "linguini", "pizza", new Cliente().setNif(999));
     m.setIsFiscalAddress(true);
        return cs.createAddress(m);
    }
    
    public boolean testCreateNote(){
        NotaCliente n = new NotaCliente(new Cliente().setNif(999), "É um teste");
        
        return cs.createNote(n);
    }
    
    public boolean testGetClient(){
        Cliente c = cs.getClient(999); 
        
        return c != null;
    }
    
    public void testGetAllClients(){
        List<Cliente> l = cs.getAllClients();
        
        for(int i = 0; i < l.size(); i++){
            System.out.println(l.get(i).getNif());
        }
    }
    
    public void testGetAllAddresses(){
        List<Morada> l = cs.getAllAddresses();
        
        for(int i = 0; i < l.size(); i++){
            System.out.println(l.get(i).getAddress());
        }
    }
    
    public void testGetAllAddressesByClient(){
        List<Morada> l = cs.getAllAddressesByClient(999); 
        
        for(int i = 0; i < l.size(); i++){
            System.out.println(l.get(i).getAddress());
        }
    }
    
    public boolean testUpdateCliente(){
        Cliente c = new Cliente(999, "John Dorito");
        
        return cs.updateClient(c);
    }
    
    public boolean testUpdateAddress(){
        Morada m = cs.getAllAddressesByClient(999).get(0);
        m.setLocale("alfaces");
        return cs.updateAddress(m);
    }
    
    public boolean testUpdateNote(){
        NotaCliente n = new NotaCliente(new Cliente().setNif(999), "É um teste atualizado");
        
        return cs.updateNote(n);
    }
    
    public boolean testDeleteNote(){
        return cs.deleteNote(cs.getAllNotesByClient(999).get(0));
    }
    
    public boolean testDeleteAddress(){
        Morada m = cs.getAllAddressesByClient(999).get(0);
        return cs.deleteAddress(m);
    }
    
    public boolean testDeleteClient(){
        return cs.deleteClient(cs.getClient(999));
    }
    
    public void testAll(){
        
        boolean cc = testCreateCliente(),
                ca = testCreateAddress(),
                cn = testCreateNote(),
                gc = testGetClient(),
                ua = testUpdateAddress(),
                uc = testUpdateCliente(),
                un = testUpdateNote(),
                da = testDeleteAddress(),
                dn = testDeleteNote(),
                dc = testDeleteClient();
                
        System.out.println("Teste obter todos endereços: ");
        testGetAllAddresses();
        
        System.out.println("Teste obter todos endereços por cliente:");
        testGetAllAddressesByClient();
               
        System.out.println("Teste obter todos clientes: ");
        testGetAllClients();
        
        System.out.println("Teste obter cliente: " + gc);
        
        System.out.println("Teste criar cliente: " + cc);
        System.out.println("Teste criar morada: " + ca);
        System.out.println("Teste criar nota cliente: " + cn);
       
        System.out.println("Teste atualizar morada: " + ua);
        System.out.println("Teste atualizar cliente: " + uc);
        System.out.println("Teste atualizar nota: " + un);
        
        System.out.println("Teste eliminar morada: " + da);
        System.out.println("teste eliminar nota: " + dn);
        System.out.println("Teste eliminar cliente: " + dc);
    }
    
    
   
    
}
