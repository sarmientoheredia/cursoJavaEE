package com.sarmiento.appfacturacionweb;

import com.sarmiento.entidades.Categoria;
import com.sarmiento.sessionBeans.CategoriaFacadeLocal;
import java.io.Serializable;
import java.util.List;
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
    FacesContext context = FacesContext.getCurrentInstance();
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
        categoria = new Categoria();
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
    //INICO DE LOS METODOS GETTERS AND SETTERS

    //INICO DE LOS METODOS
    public void agregarCategoria(Categoria categoria) {
        try {
            categoriaFacadeLocal.create(categoria);
            context.addMessage(null, new FacesMessage("Exito", "Categoria: " + "Registrada con exito"));
        } catch (Exception e) {
            System.out.println("mensaje 1" + e.getMessage());
            System.out.println("clse 1" + e.getClass().getName());

            System.out.println("mensaje 2 " + e.getCause().getMessage());
            System.out.println("clase 2 " + e.getCause().getClass().getName());

            System.out.println("mensaje 3 " + e.getCause().getCause().getMessage());
            System.out.println("clase 3 " + e.getCause().getCause().getClass().getName());
        }
    }
    
    //FIN DE LOS METODOS
}
