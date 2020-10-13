package com.sarmiento.appfacturacionweb;

import com.sarmiento.entidades.Categoria;
import com.sarmiento.sessionBeans.CategoriaFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
    
    //metodos para instanciar y que me muestre el panel para agregar nuevo
    public void nuevo() {
        categoria = new Categoria();
    }
    
    public void cancelar(){
        categoria=null;
    }
    
    
    //metodo para guardar el objeto
    public void guardar(){
        categoriaFacadeLocal.create(categoria);
        //reinicio la variable para que me muestre el listado 
        categoria=null;
        
        //metodo para que me recargue el listado de las categorias
        init();
    }

    //FIN DE LOS METODOS
}
