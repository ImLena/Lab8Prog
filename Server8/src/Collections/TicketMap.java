package Collections;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

/**
 * Класс, определяющий поля коллекции
 */

public class TicketMap implements Comparable<Ticket>, Serializable {
    private static Long id = 0L;
    private String name;
    private Coordinates coordinates;
    private static LocalDateTime creationDate = LocalDateTime.now();
    private float price;
    private TicketType type;
    private Person person;
    private Location location;
    private LinkedHashMap<Long, Ticket> Tickets;

    public TicketMap(LinkedHashMap<Long, Ticket> tickets) {
        Tickets = tickets;
    }

    public static void setId(Long ID) {
        id = ID;
    }

    public LinkedHashMap<Long, Ticket> getTickets() {
        return Tickets;
    }

    public String getCreationDate() {
        return creationDate.toString();
    }

    @Override
    public int compareTo(Ticket t) {
        if (t == null) {
            return -1;
        }
        return (int) (this.price - t.getPrice());
    }

    @Override
    public String toString() {
        return (name + ", " + coordinates.getX() + ", " + coordinates.getY() + ", " + price + ", "
                + type + ", " + person.getHeight() + ", " + location.getX() + ", " + location.getY()
                + ", " + location.getZ() + ", " + location.getName() + "\n");

    }

}
