package com.sarmiento.appfacturacionweb;

import com.sarmiento.entidades.Producto;
import com.sarmiento.sessionBeans.ProductoFacadeLocal;
import com.sarmiento.utilitarios.Mensaje;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "productoBean")
@ViewScoped
public class ProductoBean implements Serializable {

//    INICIO VARIABLES
    private List<Producto> productoList;
    private Producto producto;
//    FIN VARIABLES

//INICIO INYECCION 
    @EJB
    private ProductoFacadeLocal productoFacadeLocal;
//FIN INYECCION    

    public ProductoBean() {
    }

    @PostConstruct
    public void init() {
        productoList = productoFacadeLocal.findAll();
        producto = null;
    }

    //INICIO METODOS GETTERS Y SETTERS
    public List<Producto> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<Producto> productoList) {
        this.productoList = productoList;
    }

    public Producto getProducto() {
        return producto;
    }

    //usamos el metodo de los setters para poder seleccionar el producto antes de eliminar
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    //FIN METODOS GETTERS Y SETTERS


///    INICO METODOS
    public void nuevo() {
        producto = new Producto();
    }

    //metodo para guardar y para actualizar
    public void grabar() {
        try {
            if (producto.getId() != null) {
                productoFacadeLocal.edit(producto);
                Mensaje.mostrarExito("Actualizacion con exito");
            } else {
                productoFacadeLocal.create(producto);
                Mensaje.mostrarExito("Registro Exitoso");
            }
            init();
        } catch (Exception e) {
            if (e.getCause().getCause().getClass().getName().equals("org.hibernate.exception.ConstraintViolationException")) {
                if (e.getCause().getCause().getMessage().contains("could not execute statement")) {
                    Mensaje.mostrarError("El producto ya esta registrado");
                }
            }
        }
    }

    public void eliminar(Producto producto) {
        try {
            productoFacadeLocal.remove(producto);
            init();
            Mensaje.mostrarExito("El producto fue eliminado con exito");
        } catch (Exception e) {
            Mensaje.mostrarError("El producto no se elimino");
        }
    }

    //metodo para validar si el nombre ya extiste
    public void verificarNombre() {
        Producto pro = productoFacadeLocal.findByNombre(producto.getNombre());
        if (pro != null) {
            Mensaje.mostrarAdvertencia("El producto con este nombre ya existe");
        }
    }

    
    
    
    public List<Producto> getProductosActivos(){
        return productoList.stream().filter(p->p.getEstado()=='A').collect(Collectors.toList());
    }
//    FIN METODOS
}
