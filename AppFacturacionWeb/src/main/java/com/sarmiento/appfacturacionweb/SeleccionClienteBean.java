package com.sarmiento.appfacturacionweb;

import com.sarmiento.entidades.Cliente;
import com.sarmiento.sessionBeans.ClienteFacadeLocal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.primefaces.PrimeFaces;

@Named(value = "seleccionClienteBean")
@RequestScoped
public class SeleccionClienteBean {

    private List<Cliente> clienteList;
    
    @EJB
    private ClienteFacadeLocal clienteFacadeLocal;
    
    public SeleccionClienteBean() {
    }

@PostConstruct
public void init(){
    clienteList=clienteFacadeLocal.findAll();
}


//INICION DE LOS GETTERS Y SETTERS

    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    
    
    //metodo que selecciona el cliente de la lista para pasarlo al metodo en el facturaBean y ciera el dialogo 
    public void retornarCliente(Cliente cliente){
        PrimeFaces.current().dialog().closeDynamic(cliente);
    }
    
    
    //FIN DE LOS GETTERS Y SETTERS
    
}
