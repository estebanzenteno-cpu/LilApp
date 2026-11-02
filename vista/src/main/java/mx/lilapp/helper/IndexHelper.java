package mx.lilapp.helper;

import java.io.Serializable;

/**
 * Helper: puente entre el BeanUI y la capa negocio.
 * Aqui se llama a ServiceFacadeLocator.getInstanceFacadeXxx().
 */
public class IndexHelper implements Serializable {

    public String mensajeBienvenida() {
        return "Bienvenido a LilApp";
    }
}
