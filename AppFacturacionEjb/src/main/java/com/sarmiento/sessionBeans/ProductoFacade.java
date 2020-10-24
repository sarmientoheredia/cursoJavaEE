package com.sarmiento.sessionBeans;

import com.sarmiento.entidades.Producto;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Cbos- Com. Sarmiento H. Luis A.
 */
@Stateless
public class ProductoFacade extends AbstractFacade<Producto> implements ProductoFacadeLocal {

    @PersistenceContext(unitName = "FacturacionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoFacade() {
        super(Producto.class);
    }

    @Override
    public Producto findByNombre(String nombre) {
        Query query =em.createNamedQuery("Producto.findByNombre", Producto.class);
        query.setParameter("nombre", nombre);
        return (Producto) query.getSingleResult();
    }
    
    
}
