package mx.lilapp.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "rol")
public class Rol implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRol", nullable = false)
    private Integer idRol;

    @Column(name = "nombreRol", nullable = false, unique = true, length = 50)
    private String nombreRol;

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
}
