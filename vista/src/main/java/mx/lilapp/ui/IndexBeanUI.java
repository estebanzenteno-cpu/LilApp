package mx.lilapp.ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.lilapp.helper.IndexHelper;

import java.io.Serializable;

@Named("indexUI")
@ViewScoped
public class IndexBeanUI implements Serializable {

    private IndexHelper helper;
    private String mensaje;

    @PostConstruct
    /** Inicializa el helper y el mensaje de bienvenida. */
    public void init() {
        helper = new IndexHelper();
        mensaje = helper.mensajeBienvenida();
    }

    public String getMensaje() {
        return mensaje;
    }
}
