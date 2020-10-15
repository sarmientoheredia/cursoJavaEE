package com.sarmiento.sessionBeans;

import com.sarmiento.entidades.Producto;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ProductoFacadeLocal {

    void create(Producto producto);

    void edit(Producto producto);

    void remove(Producto producto);

    Producto find(Object id);
    
    Producto findByNombre(String nombre);

    List<Producto> findAll();

    List<Producto> findRange(int[] range);

    int count();
    
}
