package mx.lilapp.ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.lilapp.helper.IndexHelper;

import java.io.Serializable;

/**
 * BeanUI: respalda una pagina .xhtml (lo que la vista lee y las acciones de botones).
 * Delega el trabajo al Helper correspondiente. Usa este como plantilla para los demas.
 */
@Named("indexUI")
@ViewScoped
public class IndexBeanUI implements Serializable {

    private IndexHelper helper;
    private String mensaje;

    @PostConstruct
    public void init() {
        helper = new IndexHelper();
        mensaje = helper.mensajeBienvenida();
    }

    public String getMensaje() {
        return mensaje;
    }
}
