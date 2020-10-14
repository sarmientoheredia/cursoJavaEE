package com.sarmiento.appfacturacionweb;

import com.sarmiento.entidades.Categoria;
import com.sarmiento.sessionBeans.CategoriaFacadeLocal;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "categoriaBean")
@ViewScoped
public class CategoriaBean implements Serializable {

    //INICIO DE LA DECLARACION DE LAS VARIABLES
    private List<Categoria> categoriaList;
    private Categoria categoria;
    private boolean modoEdicion;
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

    public boolean isModoEdicion() {
        return modoEdicion;
    }

    public void setModoEdicion(boolean modoEdicion) {
        this.modoEdicion = modoEdicion;
    }
    //FIN DE LOS METODOS GETTERS AND SETTERS

    //INICO DE LOS METODOS
    //metodos para instanciar y que me muestre el panel para agregar nuevo
    public void nuevo() {
        categoria = new Categoria();
        modoEdicion = false;
    }

    //metodo para guardar el objeto
    public void guardar() {
        if (modoEdicion) {
            categoriaFacadeLocal.edit(categoria);
        } else {
            categoriaFacadeLocal.create(categoria);
        }
        //reinicio la variable para que me muestre el listado 
        categoria = null;
        //metodo para que me recargue el listado de las categorias
        init();

    }

    //metodo para ver los detalles en el mismo formulario de la creacion  
    public void seleccionar(Categoria categoria) {
        this.categoria = categoria;
        modoEdicion = true;
    }

    //metodo para seleccionar antes de eliminar
    public void eliminar(Categoria categoria) {
        categoriaFacadeLocal.remove(categoria);
        init();
    }

    //metodo para cancelar 
    public void cancelar() {
        init();
        categoria = null;
    }

    //metodo que verifica si el id de la categoria ya existe en la base de datos 
    public void validarId() {
        Categoria cat = categoriaFacadeLocal.find(categoria.getId());
        if (cat != null) {
            FacesContext.getCurrentInstance().addMessage("form1:txtId", new FacesMessage(FacesMessage.SEVERITY_WARN, "La categoria ya existe", "Clave duplicada"));
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
                ||categoria.getEstado().toString().contains(filterText)
                || categoria.getId()== filterInt;
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
