package utils;

public class DniValidator extends Validator {
    private static final String LETTERS = "TRWAGMYFPDXBNJZSQVHLCKE";

    @Override
    public boolean isValid(Object data) {
        try {
            if (!(data instanceof String)) return false;
            String dni = (String) data;
            // 8 dígitos seguidos de 1 letra
            if (!dni.matches("\\d{8}[A-Z]")) return false; 
            
            String numberPart = dni.substring(0, 8);
            char letterPart = dni.charAt(8);
            int number = Integer.parseInt(numberPart);
            
            // Fórmula: resto de la división entre 23
            char expectedLetter = LETTERS.charAt(number % 23);
            
            return letterPart == expectedLetter;
        } catch(NumberFormatException e) {}
        return false;
    }

    @Override
    public String message() {
        return "El DNI no es válido (formato incorrecto o letra no coincide).";
    }
}
