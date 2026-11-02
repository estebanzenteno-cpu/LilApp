package mx.lilapp.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "proveedor")
public class Proveedor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProveedor", nullable = false)
    private Integer idProveedor;

    @Column(name = "nombreProveedor", nullable = false, length = 150)
    private String nombreProveedor;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "direccion", length = 255)
    private String direccion;

    /** Tabla intermedia "ofrece": tipos de producto que ofrece este proveedor. */
    @ManyToMany
    @JoinTable(name = "ofrece",
            joinColumns = @JoinColumn(name = "idProveedor"),
            inverseJoinColumns = @JoinColumn(name = "idTipodeProducto"))
    private Set<TipoProducto> tiposProducto = new HashSet<>();

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Set<TipoProducto> getTiposProducto() {
        return tiposProducto;
    }

    public void setTiposProducto(Set<TipoProducto> tiposProducto) {
        this.tiposProducto = tiposProducto;
    }
}
