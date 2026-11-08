package mx.lilapp.exception;

public class NegocioException extends RuntimeException {

    /** Crea el error con el mensaje que se mostrara al usuario. */
    public NegocioException(String mensaje) {
        super(mensaje);
    }
}
