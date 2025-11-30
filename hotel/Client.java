package hotel;

import java.util.Objects;
import utils.DniValidator;

public class Client {
    private final String dni;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;

    public Client(String dni, String firstname, String lastname, String email, String phone) {
        if (!new DniValidator().isValid(dni)) throw new IllegalArgumentException("El DNI no es correcto");
        this.dni = dni;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
    }
    
    public Client(String dni,String firstname,String lastname) {
        this(dni,firstname,lastname,"","");
    }
    
    @Override
    public boolean equals(Object obj) { 
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final Client other = (Client) obj;
        return Objects.equals(this.dni, other.dni);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.dni);
    }

    public String getDni() { return dni; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return String.format("[%s] %s, %s",dni,lastname,firstname);
    }
}
