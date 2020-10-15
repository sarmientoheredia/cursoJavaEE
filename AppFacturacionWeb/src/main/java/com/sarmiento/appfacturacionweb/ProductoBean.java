package com.sarmiento.appfacturacionweb;

import com.sarmiento.entidades.Producto;
import com.sarmiento.sessionBeans.ProductoFacadeLocal;
import com.sarmiento.utilitarios.Mensaje;
import java.io.Serializable;
import java.util.List;
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
    private boolean modoEdicion;
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

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public boolean isModoEdicion() {
        return modoEdicion;
    }

    public void setModoEdicion(boolean modoEdicion) {
        this.modoEdicion = modoEdicion;
    }
    //FIN METODOS GETTERS Y SETTERS

///    INICO METODOS
    public void nuevo() {
        producto = new Producto();
    }

    //metodo para guardar y para actualizar
    public void grabar() {
        try {
            //si el id del produto es nulo se activa el nuevo caso contrario el id ya tiene un valor es edicion
            if (producto.getId() == null) {
                productoFacadeLocal.create(producto);
            } else {
                productoFacadeLocal.edit(producto);
            }
            init();
            Mensaje.mostrarExito("Producto Grabado Exitosamente");
        } catch (Exception e) {
            Mensaje.mostrarError("Ourrio un error al grabar un producto"+e);
        }
    }
    
    
    public void eliminar(Producto producto){
        try {
            productoFacadeLocal.remove(producto);
            init();
            Mensaje.mostrarExito("El producto fue eliminado con exito");
        } catch (Exception e) {
            Mensaje.mostrarError("El producto no se elimino");
        }
    }
       
    //metodo para validar si el nombre ya extiste
    public void verificarNombre(){
        Producto pro=productoFacadeLocal.findByNombre(producto.getNombre());
        if(pro!=null){
            Mensaje.mostrarAdvertencia("El producto con este nombre ya existe");
        }
    }
    
//    FIN METODOS
}
