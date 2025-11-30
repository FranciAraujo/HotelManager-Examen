package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator extends Validator {
    private LocalDate date;
    
    @Override
    public boolean isValid(Object data) {
        try {
            String str=(String) data;
            // Define el formato esperado: día(2)-mes(2)-año(4)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            date=LocalDate.parse(str, formatter); // Intenta convertir el texto a fecha
            return true;
        } catch(DateTimeParseException e) {
            return false; // Si falla, no es una fecha válida
        }
    }
    
    @Override
    public String message() {
        return "La fecha no es válida! Debes indicar DD-MM-AAAA";
    }
    
    public LocalDate getDate() {
        return date;
    }
}
