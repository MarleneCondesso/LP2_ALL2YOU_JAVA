/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.services;

import org.hibernate.Session;

/**
 *
 * @author mstevz
 */
public class GenericService extends Service {
    
    private Session currentSession;
    
    public GenericService(){
        super();
    }
    
    @Override
    public Session openSession(){
        
        currentSession = super.openSession();
        
        return currentSession;
    }
    
    public void closeSession(){
        currentSession.close();
    }
    
    
}
