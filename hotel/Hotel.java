package hotel;

import java.time.LocalDate;
import utils.DinamicArray;

public class Hotel {
    private final String name;  
    private final Room[] rooms;  
    private final DinamicArray guests=new DinamicArray(10); 

    public Hotel(String name, Room[] rooms) {
        this.name = name;
        this.rooms = rooms;
    }

    public Object[] getAvailableRooms(LocalDate datefrom,LocalDate dateto,int guestnumber) {
        DinamicArray availableRooms = new DinamicArray(5);
        
        for (Room room : rooms) {
            // 1. Comprueba si la capacidad de la habitación es suficiente
            if (room.getCapacity() >= guestnumber) {
                // 2. Comprueba si la habitación está disponible en el rango de fechas
                if (room.isAvailable(datefrom, dateto)) { 
                    availableRooms.add(room);
                }
            }
        }
        return availableRooms.toArray(); 
    }
    
    public Client getClient(String dni)  {
        // Crea un objeto temporal solo con el DNI para buscar su posición en DinamicArray
        Integer position=guests.position(new Client(dni,"","")); 
        if (position!=null) return (Client)guests.get(position);
        return null;
    }
    
    public boolean addClient(Client client) {
        Integer position=guests.position(client);
        if (position==null) {
            guests.add(client);
            return true;
        }
        return false;
    }

    public Object[] booking(Client client) {
        DinamicArray clientBookings = new DinamicArray(5);
        // Recorre todas las habitaciones y todas sus reservas
        for (Room room : rooms) { 
            for(int i=0;i<room.bookings.length();i++) {
                Booking booking=(Booking) room.bookings.get(i);
                // Si la reserva pertenece al cliente, la añade a la lista
                if (booking.getGuest().equals(client)) {
                    clientBookings.add(booking);
                }
            }
        }
        return clientBookings.toArray();
    }
}
