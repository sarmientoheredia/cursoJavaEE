package com.sarmiento.appfacturacionweb;

import com.sarmiento.entidades.Cliente;
import com.sarmiento.sessionBeans.ClienteFacadeLocal;
import com.sarmiento.utilitarios.Mensaje;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Cbos- Com. Sarmiento H. Luis A.
 */
@Named(value = "clienteBean")
@ViewScoped
public class ClienteBean implements Serializable {

// INICIO VARIABLES
    private List<Cliente> clienteList;
    private Cliente cliente;
    private boolean  bandera;
// FIN VARIABLES

//    INICO INYECCION
    @EJB
    private ClienteFacadeLocal clienteFacadeLocal;
//    FIN INYECCION

    public ClienteBean() {
    }

    @PostConstruct
    public void init() {
        clienteList = clienteFacadeLocal.findAll();
        cliente = null;
    }

//    INICIO GETTERS Y SETTERS
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    //    FIN GETTERS Y SETTERS

//    INICION METODOS
    public void nuevo() {
        cliente = new Cliente();
    }

    public void grabar() {
        try {
            if (cliente.getId() == null && bandera==true) {
                clienteFacadeLocal.create(cliente);
                Mensaje.mostrarExito("Registro exitoso");
            } else if(cliente.getId() != null){
                clienteFacadeLocal.edit(cliente);
                Mensaje.mostrarExito("Actualizacion exitosa");
            }else if(bandera==false){
               Mensaje.mostrarError("El numero de cedula es invalido se le advirtio");
            }
            init();
        } catch (Exception e) {
            Mensaje.mostrarError("Error de la operacion");
        }

    }

    public void eliminar(Cliente cliente) {
        try {
            clienteFacadeLocal.remove(cliente);
            init();
            Mensaje.mostrarExito("Eliminacion Exitosa");
        } catch (Exception e) {
            Mensaje.mostrarError("error de eliminacion");
        }
    }

    //metodo para verificar si la cedula ya esta registrada
    public void verificarCedula() {
            Cliente cli = clienteFacadeLocal.findByCedula(cliente.getCedula());
            if (cli != null) {
                Mensaje.mostrarAdvertencia("El numero de cedula ya existe");
            }
    }

    //metodo para verificar si el email existe
    public void verificarCorreo() {
        Cliente cli = clienteFacadeLocal.findByCorreo(cliente.getCorreo());
        if (cli != null) {
            Mensaje.mostrarAdvertencia("Este correo ya exixte");
        }
    }

    //METODO PARA FILTRAR POR CUALQUIER CAMPO
    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        int filterInt = getInteger(filterText);

        Cliente cliente = (Cliente) value;
        return cliente.getCedula().toLowerCase().contains(filterText)
                || cliente.getApellido().toLowerCase().contains(filterText)
                || cliente.getNombre().toLowerCase().contains(filterText)
                || cliente.getCorreo().toLowerCase().contains(filterText)
                || cliente.getDireccion().toLowerCase().contains(filterText)
                || cliente.getId() == filterInt;
    }

    //METODO PARA CONVERTIR EL ID
    private int getInteger(String string) {
        try {
            return Integer.valueOf(string);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    //metodo para validar el numero de la cedula del cliente
    public  void operacionCedula() {
        int c, suma = 0, acum, resta = 0;

        for (int i = 0; i < cliente.getCedula().length() - 1; i++) {
            c = Integer.parseInt(cliente.getCedula().charAt(i) + "");
            if (i % 2 == 0) {
                c = c * 2;
                if (c > 9) {
                    c = c - 9;
                }
            }
            suma = suma + c;
        }
        if (suma % 10 != 0) {
            acum = ((suma / 10) + 1) * 10;
            resta = acum - suma;
        }
        int ultimo = Integer.parseInt(cliente.getCedula().charAt(9) + "");
        if (ultimo == resta) {
            verificarCedula();
            bandera=true;

        } else {
            bandera=false;
            Mensaje.mostrarError("El numero de cedula invalido");
        }
    }

//    FIN METODOS
}
