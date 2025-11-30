package utils;

public class CancelException extends Exception {
    
    // Constructor por defecto, usa el mensaje estándar
    public CancelException() {
        super("Operación Cancelada.");
    }
    
    // Constructor que permite especificar un mensaje
    public CancelException(String msg) {
        super(msg);
    }
}
