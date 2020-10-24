/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sarmiento.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Cbos- Com. Sarmiento H. Luis A.
 */
@Entity
@Table(name = "factura")
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f"),
    @NamedQuery(name = "Factura.findById", query = "SELECT f FROM Factura f WHERE f.id = :id"),
    @NamedQuery(name = "Factura.findByFecha", query = "SELECT f FROM Factura f WHERE f.fecha = :fecha"),
    @NamedQuery(name = "Factura.findByNumero", query = "SELECT f FROM Factura f WHERE f.numero = :numero"),
    @NamedQuery(name = "Factura.findByEstado", query = "SELECT f FROM Factura f WHERE f.estado = :estado")})
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "numero")
    private String numero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private Character estado='A';
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cliente cliente;
    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST}, mappedBy = "factura" , fetch = FetchType.EAGER,orphanRemoval = true)
    private List<DetalleFactura> detalleFacturaList;

    public Factura() {
    }

    public Factura(Integer id) {
        this.id = id;
    }

    public Factura(Integer id, Date fecha, String numero, Character estado) {
        this.id = id;
        this.fecha = fecha;
        this.numero = numero;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<DetalleFactura> getDetalleFacturaList() {
        return detalleFacturaList;
    }

    public void setDetalleFacturaList(List<DetalleFactura> detalleFacturaList) {
        this.detalleFacturaList = detalleFacturaList;
    }

 
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sarmiento.entidades.Factura[ id=" + id + " ]";
    }

    //metoto para calcular el subtota
    public BigDecimal getSubtotal() {
        return BigDecimal.valueOf(detalleFacturaList.stream().mapToDouble(d -> d.getSubtotal().doubleValue()).sum());

    }

    public BigDecimal getTotal() {
        return getSubtotal().multiply(BigDecimal.valueOf(1.12));
    }
    
    
    //metodo que permite agregar los detalles si el detalle factura esta en nulo 
    public void agregarDetalle (DetalleFactura detalleFactura){
         if(detalleFacturaList==null){
            detalleFacturaList=(new ArrayList<>());
        }
         //con esto agregamos las claves foraneas o hacemos la insercion en cascada
         detalleFactura.setFactura(this);
         detalleFacturaList.add(detalleFactura);
    }
        //metdo para eliminar el detalle 
    public void eliminarDetalle(DetalleFactura detalleFactura){
        detalleFacturaList.remove(detalleFactura);
    }
    
    
    
    //metodo para controlar los prodcutos repetidos
    public boolean  esProductoDuplicado(Producto producto){
        
        //enymatch verifica si alguien coincide con el valor a ingresosa
        if(detalleFacturaList==null){
            return false;
        }
        return detalleFacturaList.stream().anyMatch(d->d.getProducto().equals(producto));
    }
}
