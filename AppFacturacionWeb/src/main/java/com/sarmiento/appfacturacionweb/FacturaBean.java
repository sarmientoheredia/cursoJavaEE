package com.sarmiento.appfacturacionweb;

import com.sarmiento.entidades.Cliente;
import com.sarmiento.entidades.DetalleFactura;
import com.sarmiento.entidades.Factura;
import com.sarmiento.sessionBeans.FacturaFacadeLocal;
import com.sarmiento.utilitarios.JasperReportUtil;
import com.sarmiento.utilitarios.Mensaje;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Cbos- Com. Sarmiento H. Luis A.
 */
@Named(value = "facturaBean")
@ViewScoped
public class FacturaBean implements Serializable {

//INICIO DE LAS VARIABLES
    private List<Factura> facturaList;
    
    private Factura factura;
    private DetalleFactura detalleFactura;
//FIN DE LAS VARIABLES

//    INICIO INYECCION
    @EJB
    private FacturaFacadeLocal facturaFacadeLocal;
    @Inject
    private JasperReportUtil jasperReportUtil;
    //    FIN INYECCION  

    public FacturaBean() {
    }

    @PostConstruct
    public void init() {
        facturaList = facturaFacadeLocal.findAll();
        factura = null;
    }

//    INICIO GETTERS Y SETERS
    public List<Factura> getFacturaList() {
        return facturaList;
    }

    public void setFacturaList(List<Factura> facturaList) {
        this.facturaList = facturaList;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        
        this.factura = factura;
    }

    public DetalleFactura getDetalleFactura() {
        return detalleFactura;
    }

    public void setDetalleFactura(DetalleFactura detalleFactura) {
        this.detalleFactura = detalleFactura;
    }
//    FIN GETTERS Y SETERS

//    INICIO METODOS
    public void nuevo() {
        factura = new Factura();
        detalleFactura=new DetalleFactura();
    }

    //metodo para seleccionar e instanciar 
    public void seleccionarFactura( Factura factura){
        this.factura=factura;
        detalleFactura=new DetalleFactura();
    }
    
    
    public void grabar() {
        try {
            if (factura.getId() == null) {
                facturaFacadeLocal.create(factura);
                Mensaje.mostrarExito("factura guardada");
            } else {
                facturaFacadeLocal.edit(factura);
                Mensaje.mostrarExito("La operacion se efectuo con exito");
            }
            init();
        } catch (Exception e) {
            Mensaje.mostrarError("Error al realizar la accion");
        }
    }

    
//    public void eliminar(Factura factura){
//        try {
//            facturaFacadeLocal.remove(factura);
//            init();
//            Mensaje.mostrarExito("Eliminacion exitosa");
//        } catch (Exception e) {
//            Mensaje.mostrarError("No se puedo eliminar");
//        }
//    }
    
        //metodo para seleccionar antes de eliminar
    public void eliminar(Factura factura) {
        try {
            facturaFacadeLocal.remove(factura);
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
    
    
    
    public void abrirSeleccionCliente(){
        Map<String, Object> opciones= new HashMap<>();
        opciones.put("contentHeigth", 600);
        opciones.put("contentWidth", 900);
        opciones.put("modal", true);
        
        PrimeFaces.current().dialog().openDynamic("/vistas/plantilla/SeleccionCliente", opciones,null);
    }
    
    
    //metodo que recibe el objeto seleccionado desde el popap
    public void recibirCliente(SelectEvent selectEvent){
       factura.setCliente((Cliente)selectEvent.getObject());
    }
    
    
    //metodo para agregar a la lista del detalle y validar si no hay otro producto
    public void agregarDetalle(){
        if(factura.esProductoDuplicado(detalleFactura.getProducto())){
          Mensaje.mostrarAdvertencia("El producto ya existe");
          return;
        }
      factura.agregarDetalle(detalleFactura);
      detalleFactura=new DetalleFactura();
    }
    

    
    
    //metodo para que setee el precio
    public void seleccionarProducto(){
        detalleFactura.setPrecio(detalleFactura.getProducto().getPrecio());
    }
    
    //como llamar a un metodo que esta en otro bean
    public void exportarPDF(){
        try {
            jasperReportUtil.exportToPdf("FacturaCab", null);
        } catch (Exception ex) {
            Mensaje.mostrarError("Ocurrio un error al generar el reporte de pdf");
            ex.printStackTrace(System.out);
        }
    }
//    FIN METODOS
}
