package utils;

public class YesNoValidator extends Validator {
    @Override
    public boolean isValid(Object data) {
        String str=(String) data;
        // Comprueba si el texto es S, Y o N (sin importar mayúsculas/minúsculas)
        return str.toUpperCase().equals("S") || str.toUpperCase().equals("Y") || str.toUpperCase().equals("N");
    }
    
    @Override
    public String message() {
        return "Debes indicar S/Y (Sí) o N (No)";
    }
}
