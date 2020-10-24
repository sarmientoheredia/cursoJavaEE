/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sarmiento.sessionBeans;

import com.sarmiento.entidades.Cliente;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Cbos- Com. Sarmiento H. Luis A.
 */
@Stateless
public class ClienteFacade extends AbstractFacade<Cliente> implements ClienteFacadeLocal {

    @PersistenceContext(unitName = "FacturacionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(Cliente.class);
    }

    @Override
    public Cliente findByCedula(String cedula) {
        Query q=em.createNamedQuery("Cliente.findByCedula", Cliente.class);
        q.setParameter("cedula", cedula);
        return (Cliente) q.getSingleResult();
    }

    @Override
    public Cliente findByCorreo(String correo) {
        Query q=em.createNamedQuery("Cliente.findByCorreo", Cliente.class);
        q.setParameter("correo", correo);
        return (Cliente) q.getSingleResult();
    }
    
}
