package hotelmanager;

import hotel.Booking;
import hotel.Client;
import hotel.Hotel;
import hotel.Room;
import java.time.LocalDate;
import utils.CancelException;
import utils.Input;

public class Manager {
    private static Hotel hotel;

    /**
     * Construye y configura el objeto Hotel con todas sus habitaciones.
     */
    private static void buildHotel() {
        Room[] rooms = new Room[10]; 
        rooms[0] = new Room(101, "Room 101", 1);
        rooms[1] = new Room(102, "Room 102", 1);
        rooms[2] = new Room(103, "Room 103", 1);
        rooms[3] = new Room(104, "Room 104", 1);
        rooms[4] = new Room(201, "Room 201", 2);
        rooms[5] = new Room(202, "Room 202", 2);
        rooms[6] = new Room(203, "Room 203", 2);
        rooms[7] = new Room(204, "Room 204", 2);
        rooms[8] = new Room(301, "Room 301", 3);
        rooms[9] = new Room(302, "Room 302", 3);
        hotel = new Hotel("Hotel Rodeira", rooms);
    }

    /**
     * Gestiona el alta o la consulta de un cliente.
     */
    public static Client fichaCliente(String msg) throws CancelException {
        Client client=null;
        String dni;
        System.out.println(msg);
        dni=Input.getDni("DNI");
        client=hotel.getClient(dni); // Intenta obtener cliente existente
        
        if (client!=null) {
            System.out.println("Cliente existente: "+client);
        } else {
            // Si no existe, pide todos los datos y lo añade
            String firstname=Input.getStr("Nombre");
            String lastname=Input.getStr("Apellidos");
            String email=Input.getStr("E-mail");
            String phone=Input.getStr("Teléfono");
            client=new Client(dni,firstname,lastname,email,phone);
            hotel.addClient(client);
            System.out.println("Cliente añadido correctamente");
        }
        return client;
    }

    /**
     * Permite al usuario realizar una nueva reserva.
     */
    private static void book() throws CancelException {
        System.out.println("\n*** Hacer Reserva ***");
        Client client=fichaCliente("Datos del cliente que hace la reserva:");
        if (client!=null) {
            LocalDate from=Input.getDate("Fecha Inicio Reserva (DD-MM-AAAA)");
            LocalDate to=Input.getDate("Fecha Fin Reserva (DD-MM-AAAA)");
            int guestnumber=Input.getInt("Número de huéspedes",1,10); 
            Object[] rooms=hotel.getAvailableRooms(from, to, guestnumber); // Busca habitaciones disponibles

            if (rooms.length > 0) {
                Integer choosed=Input.chooser("Elija Habitación",rooms);
                if (choosed != null) {
                    Room rchose=(Room) rooms[choosed];
                    if (Input.question("¿Desea proceder a la reserva de "+rchose+" para "+from+" hasta "+to+"?")) {
                        if (rchose.bookRoom(from, to, client)) {
                            System.out.println("La reserva se realizó correctamente");
                        } else {
                            System.out.println("No se hizo la reserva. (Error al reservar)");
                        }
                    }
                }
            } else {
                System.out.println("Sin habitaciones libres para esas fechas");
            }
        }
        Input.pause("\nPara seguir pulse Enter.");
    }

    /**
     * Permite al usuario hacer el Check-In a una reserva.
     */
    private static void checkin() throws CancelException {
        System.out.println("\n*** Hacer Check-In ***");
        String dni=Input.getDni("DNI del Cliente");
        Client client=hotel.getClient(dni);
        if (client!=null) {
            Object[] bookings=hotel.booking(client); 
            if (bookings.length>0) {
                Integer choosed=Input.chooser("Elija Reserva para Check-In",bookings);
                if (choosed!=null) {
                    Booking booking=(Booking) bookings[choosed];
                    if (Input.question("Confirmar check-in para la reserva: "+booking)) {
                        booking.check();
                        System.out.println("Check-in realizado correctamente.");
                    }
                }
            } else {
                System.out.println("No hay reservas pendientes para este cliente.");
            }
        } else {
            System.out.println("Cliente no encontrado.");
        }
        Input.pause("\nPara seguir pulse Enter.");
    }

    /**
     * Permite al usuario cancelar una reserva.
     */
    private static void cancel() throws CancelException {
        System.out.println("\n*** Cancelar Reserva ***");
        String dni=Input.getDni("DNI del Cliente");
        Client cl=hotel.getClient(dni);
        if (cl!=null) {
            Object[] bookings=hotel.booking(cl);
            if (bookings.length > 0) {
                Integer choosed=Input.chooser("Elija Reserva",bookings);
                
                if (choosed != null) {
                    Booking booking=(Booking) bookings[choosed];
                    if (Input.question("¿Desea proceder a la cancelación de "+booking+"?")){
                        if (booking.cancel())  System.out.println("Reserva cancelada correctamente");
                        else                   System.out.println("Ocurrió un error durante la cancelación");
                    } else System.out.println("No se llevó a cabo la cancelación");
                }
            } else System.out.println("El cliente no tiene reservas pendientes");
        } else System.out.println(dni+" no es cliente del Hotel");
        Input.pause("\nPara seguir pulse Enter.");
    }
    
    // NOTA: El check-out no requiere implementación lógica según el examen, solo la pausa.
    private static void checkout() throws CancelException {
        System.out.println("\n*** Hacer Check-Out ***");
        Input.pause("\nPara seguir pulse Enter.");
    }


    /**
     * Muestra el menú principal de la aplicación.
     */
    private static void menu() {
        System.out.println("---------- MENÚ PRINCIPAL ----------");
        System.out.println("1.- Consultar/Alta Cliente");
        System.out.println("2.- Hacer Reserva");
        System.out.println("3.- Hacer Check-In");
        System.out.println("4.- Hacer Check-Out");
        System.out.println("5.- Cancelar Reserva");
        System.out.println("6.- SALIR");
        System.out.println("------------------------------------");
    }

    /**
     * Método principal que inicializa el hotel y ejecuta el bucle del menú.
     */
    public static void main(String[] args) {
        buildHotel();
        int op=0;

        try {
            do {
                menu();
                try {
                    op=Input.getInt("Opción elegida:",1,6);
                    switch(op) {
                        case 1 -> fichaCliente("Datos del cliente: ");
                        case 2 -> book();
                        case 3 -> checkin();
                        case 4 -> checkout();
                        case 5 -> cancel();
                    }
                } catch(CancelException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println();
            } while(op!=6);
        } catch(CancelException e) {
            System.out.println(e.getMessage());
            System.out.println("Abandonando el programa");
        }
    }
}
