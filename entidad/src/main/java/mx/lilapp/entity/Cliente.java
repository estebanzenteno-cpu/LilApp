package mx.lilapp.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCliente", nullable = false)
    private Integer idCliente;

    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "apellidoPaterno", nullable = false, length = 100)
    private String apellidoPaterno;

    @Column(name = "apellidoMaterno", length = 100)
    private String apellidoMaterno;

    @Column(name = "correo", nullable = false, unique = true, length = 150)
    private String correo;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "constanciaFiscal", length = 255)
    private String constanciaFiscal;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus", nullable = false)
    private EstatusCliente estatus = EstatusCliente.activo;

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getConstanciaFiscal() {
        return constanciaFiscal;
    }

    public void setConstanciaFiscal(String constanciaFiscal) {
        this.constanciaFiscal = constanciaFiscal;
    }

    public EstatusCliente getEstatus() {
        return estatus;
    }

    public void setEstatus(EstatusCliente estatus) {
        this.estatus = estatus;
    }
}
