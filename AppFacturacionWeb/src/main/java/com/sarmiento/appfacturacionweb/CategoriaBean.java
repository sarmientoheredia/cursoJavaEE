package com.sarmiento.appfacturacionweb;

import com.sarmiento.entidades.Categoria;
import com.sarmiento.sessionBeans.CategoriaFacadeLocal;
import com.sarmiento.utilitarios.Mensaje;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.omnifaces.util.Components;

@Named(value = "categoriaBean")
@ViewScoped
public class CategoriaBean implements Serializable {

    //INICIO DE LA DECLARACION DE LAS VARIABLES
    private List<Categoria> categoriaList;
    private List<Categoria> categoriaActivosList;
    private Categoria categoria;
    //FIN DE LA DECLARACION DE LAS VARIABLES

    //INICIO DE LAS INYECCIONES
    @EJB
    private CategoriaFacadeLocal categoriaFacadeLocal;
    //FIN DE LAS INYECCIONES

    public CategoriaBean() {
    }

    @PostConstruct
    public void init() {
        categoriaList = categoriaFacadeLocal.findAll();
        categoriaActivosList = categoriaFacadeLocal.findActive();
        categoria = null;     
    }

    //INICO DE LOS METODOS GETTERS AND SETTERS
    public List<Categoria> getCategoriaList() {
        return categoriaList;
    }

    public void setCategoriaList(List<Categoria> categoriaList) {
        this.categoriaList = categoriaList;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Categoria> getCategoriaActivosList() {
        return categoriaActivosList;
    }

    public void setCategoriaActivosList(List<Categoria> categoriaActivosList) {
        this.categoriaActivosList = categoriaActivosList;
    }

    //FIN DE LOS METODOS GETTERS AND SETTERS
    //INICO DE LOS METODOS
    //metodos para instanciar y que me muestre el panel para agregar nuevo
    public void nuevo() {
        categoria = new Categoria();
    }

    //metodo para guardar el objeto
    public void grabar() {
      try {
            if (categoria.getId() != null) {
                categoriaFacadeLocal.edit(categoria);
                Mensaje.mostrarExito("Actualizacion con exito");
            } else {
                categoriaFacadeLocal.create(categoria);
                Mensaje.mostrarExito("Registro Exitoso");
            }
            init();
        } catch (Exception e) {
            System.out.println("primer nivel: " + e.getMessage());
            System.out.println("primera clase: " + e.getClass().getName());

            System.out.println("segundo nivel: " + e.getCause().getMessage());
            System.out.println("primera clase: " + e.getCause().getClass().getName());

            System.out.println("tercer nivel: " + e.getCause().getCause().getMessage());
            System.out.println("primera clase: " + e.getCause().getCause().getClass().getName());
            
            if (e.getCause().getCause().getClass().getName().equals("org.hibernate.exception.ConstraintViolationException")) {
                if (e.getCause().getCause().getMessage().contains("could not execute statement")) {
                    Mensaje.mostrarError("La categoria ya esta registrado");
                }
            }
        }
    }
    
    //metodo para seleccionar antes de eliminar
    public void eliminar(Categoria categoria) {
        try {
            categoriaFacadeLocal.remove(categoria);
            init();
            Mensaje.mostrarExito("Eliminacion exitosa");
        } catch (Exception e) {
                System.out.println("mensaje nivel 1: " + e.getMessage());
            System.out.println("clase nivel 1: " + e.getClass().getName());

            System.out.println("mensaje segundo nivel: " + e.getCause().getMessage());
            System.out.println("clase nivel 2: " + e.getCause().getClass().getName());

            System.out.println("mensaje tercer nivel: " + e.getCause().getCause().getMessage());
            System.out.println("clase nivel 3: " + e.getCause().getCause().getClass().getName());
            
  
             if (e.getCause().getCause().getClass().getName().equals("javax.persistence.PersistenceException")) {
                if (e.getCause().getCause().getMessage().contains("could not execute statement")) {
                    Mensaje.mostrarError("Existen productos que tienen esta categoria");
                }
            }
        }

    }

    //metodo que verifica si el id de la categoria ya existe en la base de datos 
    public void validarNombre() {
        Categoria cat = categoriaFacadeLocal.findByNombre(categoria.getNombre());
        if (cat != null) {
            Mensaje.mostrarAdvertencia("La categoria ya existe");
        }
    }

    //METODO PARA FILTRAR POR CUALQUIER CAMPO
    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        int filterInt = getInteger(filterText);

        Categoria categoria = (Categoria) value;
        return categoria.getNombre().toLowerCase().contains(filterText)
                || categoria.getEstado().equals(value)
                || categoria.getId() == filterInt;
    }

    //METODO PARA CONVERTIR EL ID
    private int getInteger(String string) {
        try {
            return Integer.valueOf(string);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    //FIN DE LOS METODOS
}
