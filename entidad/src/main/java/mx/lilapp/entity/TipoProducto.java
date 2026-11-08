package mx.lilapp.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tipoproducto")
public class TipoProducto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTipodeProducto", nullable = false)
    private Integer idTipodeProducto;

    @Column(name = "nombreProducto", nullable = false, unique = true, length = 100)
    private String nombreProducto;

    @ManyToMany(mappedBy = "tiposProducto")
    private Set<Proveedor> proveedores = new HashSet<>();

    public Integer getIdTipodeProducto() {
        return idTipodeProducto;
    }

    public void setIdTipodeProducto(Integer idTipodeProducto) {
        this.idTipodeProducto = idTipodeProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Set<Proveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(Set<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }
}
