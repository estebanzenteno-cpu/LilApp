package mx.lilapp.ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.lilapp.entity.Proveedor;
import mx.lilapp.helper.ProveedorHelper;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named("proveedorUI")
@ViewScoped
public class ProveedorBeanUI implements Serializable{

    private ProveedorHelper helper;
    private Proveedor proveedor;
    private Integer[] productosSeleccionados;
    private List<SelectItem> opcionesProducto;
    private boolean registroExitoso;
    private String productosRegistrados;
    private String busquedaProveedor;
    private List<Proveedor> proveedores;
    private Proveedor proveedorConsulta;

    @PostConstruct
    public void init() {
        helper = new ProveedorHelper();
        limpiar();
    }

    public void registrar() {
        registroExitoso = false;
        List<String> errores =
                helper.validar(proveedor, productosSeleccionados);

        if(!errores.isEmpty()) {
            errores.forEach(this::mostrarError);
            return;
        }

        try{
            helper.guardar(proveedor, productosSeleccionados);

            productosRegistrados = helper.listarTiposProducto().stream()
                    .filter(tipo -> java.util.Arrays.asList(productosSeleccionados)
                            .contains(tipo.getIdTipodeProducto()))
                    .map(tipo -> tipo.getNombreProducto())
                    .collect(java.util.stream.Collectors.joining(", "));

            registroExitoso = true;

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            ProveedorHelper.MSG_REGISTRO_EXITOSO,
                            null));
        }catch (RuntimeException e){
            mostrarError("No se pudo registrar el proveedor: " + e.getMessage());
        }
    }

    public boolean isRegistroExitoso() {
        return registroExitoso;
    }

    public String getProductosRegistrados() {
        return productosRegistrados;
    }

    public void nuevoRegistro() {
        limpiar();
        productosRegistrados = null;
        registroExitoso = false;
    }

    public void limpiar(){
        proveedor = new Proveedor();
        productosSeleccionados = new Integer[0];
    }

    private void mostrarError(String mensaje){
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, null));

        FacesContext.getCurrentInstance().validationFailed();
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public Integer[] getProductosSeleccionados() {
        return productosSeleccionados;
    }

    public void setProductosSeleccionados(Integer[] productosSeleccionados) {
        this.productosSeleccionados = productosSeleccionados;
    }

    public List<SelectItem> getOpcionesProducto() {
        if(opcionesProducto == null) {
            opcionesProducto = helper.listarTiposProducto()
                    .stream()
                    .map(tipo -> new SelectItem(
                            tipo.getIdTipodeProducto(),
                            tipo.getNombreProducto()))
                    .collect(Collectors.toList());
        }
        return opcionesProducto;
    }

    public List<Proveedor> getProveedores() {
        if (proveedores == null) {
            proveedores = helper.listarProveedoresOrdenados();
        }
        return proveedores;
    }

    public String getBusquedaProveedor() {
        return busquedaProveedor;
    }

    public void setBusquedaProveedor(String busquedaProveedor) {
        this.busquedaProveedor = busquedaProveedor;
    }

    public void buscarProveedores() {
        proveedores = helper.buscarProveedoresPorNombre(busquedaProveedor);
    }

    public void limpiarBusqueda() {
        busquedaProveedor = null;
        proveedores = helper.listarProveedoresOrdenados();
    }

    public void verProveedor(Proveedor proveedor) {
        this.proveedorConsulta = proveedor;
    }

    public Proveedor getProveedorConsulta() {
        return proveedorConsulta;
    }
}
