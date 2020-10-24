package com.sarmiento.sessionBeans;

import com.sarmiento.entidades.Categoria;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Cbos- Com. Sarmiento H. Luis A.
 */
@Local
public interface CategoriaFacadeLocal {

    void create(Categoria categoria);

    void edit(Categoria categoria);

    void remove(Categoria categoria);

    Categoria find(Object id);

    Categoria findByNombre(String nombre);

    List<Categoria> findAll();

    List<Categoria> findRange(int[] range

    );

    int count();

    List<Categoria> findActive();
}
