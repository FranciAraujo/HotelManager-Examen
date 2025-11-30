package hotel;

import java.time.LocalDate;
import utils.DinamicArray;

public class Room {
    private final int number;
    private final String name;
    private final int capacity;
    public final DinamicArray bookings;

    public Room(int number, String name, int capacity) {
        this.number = number;
        this.name = name;
        this.capacity = capacity;
        this.bookings = new DinamicArray(5);
    }

    public boolean isAvailable(LocalDate startDate, LocalDate endDate) {
        for (int i = 0; i < bookings.length(); i++) {
            Booking existingBooking = (Booking) bookings.get(i);
            // Si la reserva existente se solapa con el nuevo rango de fechas, no está disponible.
            if (existingBooking.overlapsWith(startDate, endDate)) {
                return false; 
            }
        }
        return true; 
    }

    public boolean bookRoom(LocalDate startDate, LocalDate endDate, Client guest) {
        if (isAvailable(startDate, endDate)) {
            Booking newBooking = new Booking(startDate, endDate, guest, this);
            bookings.add(newBooking);
            return true;
        }
        return false;
    }    
    
    public boolean cancelBooking(Booking booking) {
        Integer pos=bookings.position(booking);
        if (pos!=null) {
            bookings.remove(pos.intValue());
            return true;
        }
        return false;
    }
    
    public String getName() { return name; }
    public int getCapacity() { return capacity; }

    @Override
    public String toString() {
        return String.format("[%03d] %s (%d plazas)", number, name, capacity);
    }
}
