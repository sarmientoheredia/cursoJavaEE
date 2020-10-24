/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sarmiento.sessionBeans;

import com.sarmiento.entidades.Categoria;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Cbos- Com. Sarmiento H. Luis A.
 */
@Stateless
public class CategoriaFacade extends AbstractFacade<Categoria> implements CategoriaFacadeLocal {

    @PersistenceContext(unitName = "FacturacionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoriaFacade() {
        super(Categoria.class);
    }

    @Override
    public List<Categoria> findAll() {
        Query q = em.createNamedQuery("Categoria.findAll", Categoria.class);
        return q.getResultList();
    }

    @Override
    public List<Categoria> findActive() {
        char estado = 'A';
        Query query = em.createQuery("SELECT c FROM Categoria c WHERE UPPER(c.estado)=:estado ");
        query.setParameter("estado", estado);
        return query.getResultList();

    }

    @Override
    public Categoria findByNombre(String nombre) {
        Query query = em.createNamedQuery("Categoria.findByNombre", Categoria.class);
        query.setParameter("nombre", nombre);
        return (Categoria) query.getSingleResult();
    }

}
