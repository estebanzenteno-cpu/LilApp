package mx.lilapp.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario", nullable = false)
    private Integer idUsuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idRol", nullable = false)
    private Rol rol;

    @Column(name = "nombreUsuario", nullable = false, unique = true, length = 50)
    private String nombreUsuario;

    @Column(name = "`contraseña`", nullable = false, length = 255)
    private String contrasena;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
