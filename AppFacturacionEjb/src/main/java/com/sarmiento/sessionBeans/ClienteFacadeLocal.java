/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sarmiento.sessionBeans;

import com.sarmiento.entidades.Cliente;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Cbos- Com. Sarmiento H. Luis A.
 */
@Local
public interface ClienteFacadeLocal {

    void create(Cliente cliente);

    void edit(Cliente cliente);

    void remove(Cliente cliente);

    Cliente find(Object id);
    
    Cliente findByCedula(String cedula);
    
    Cliente findByCorreo(String correo);

    List<Cliente> findAll();

    List<Cliente> findRange(int[] range);

    int count();
    
}
