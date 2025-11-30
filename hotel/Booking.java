package hotel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Booking {
    private static int booknumber = 0;
    private final int number;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Client guest;
    private final Room room;
    private boolean checkin;

    public Booking(LocalDate startDate, LocalDate endDate, Client guest, Room room) {
        this.number = ++booknumber; 
        this.startDate = startDate.atTime(12,0);
        this.endDate = endDate.atTime(12,0);
        // Validamos que la fecha de inicio sea estrictamente anterior a la de fin
        if (!this.startDate.isBefore(this.endDate)) 
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin.");
        this.guest = guest;
        this.room = room;
        this.checkin = false;
    }

    /**
     * Comprueba si esta reserva se solapa con otro rango de fechas.
     * La lógica de solapamiento es: (A.inicio < B.fin) && (A.fin > B.inicio)
     * * @param otherStart Fecha de inicio del otro rango.
     * @param otherEnd Fecha de fin del otro rango.
     * @return true si hay solapamiento, false en caso contrario.
     */
    public boolean overlapsWith(LocalDate otherStart, LocalDate otherEnd) { 
        LocalDateTime otherStartDT = otherStart.atTime(12, 0);
        LocalDateTime otherEndDT = otherEnd.atTime(12, 0);
        
        return this.startDate.isBefore(otherEndDT) && this.endDate.isAfter(otherStartDT);
    }
    
    public void check() { this.checkin = true; }
    public boolean cancel() { return room.cancelBooking(this); }

    public Client getGuest() { return guest; }
    public Room getRoom() { return room; }
    
    @Override
    public boolean equals(Object obj) { 
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final Booking other = (Booking) obj;
        return Objects.equals(this.number, other.number);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.number);
    }

    @Override
    public String toString() {
        return String.format("Reserva #%d: %s (%s a %s) Check-in: %s", 
                number, room.getName(), startDate.toLocalDate(), endDate.toLocalDate(), checkin ? "SI" : "NO");
    }
}
