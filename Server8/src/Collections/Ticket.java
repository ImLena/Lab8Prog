package Collections;

import Exceptions.InvalidFieldException;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Класс для заполнения полей ticket
 */

public class Ticket implements Comparable, Serializable {
    private String user;
    private Long id;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private float price;
    private TicketType type;
    private Person person;

    public Ticket(String name, Coordinates coordinates, float price, TicketType type, Person person, String user) {
        this.name = name;
        this.coordinates = coordinates;
        this.price = price;
        this.type = type;
        this.person = person;
        this.user = user;
    }

    private static final long serialVersionUID = 32L;


    public float getPrice() {
        return price;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }


    public void setId(Long id) {
        this.id = id;
        checkId();
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
        checkDate();
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public TicketType getType() {
        return type;
    }

    public Person getPerson() {
        return person;
    }

    @Override
    public String toString() {
        return ("id = " + id + ", name = " + name + ", coordinates: " + coordinates + "; creationDate = "
                + creationDate + ", price = " + price + ", Ticket type = " + type + ", person: " + person
                + ". Element belongs to " + user);
    }

    public String toParse() {
        return name + ", " + coordinates.getX() + ", " + coordinates.getY() + ", " + price + ", "
                + type + ", " + person.getHeight() + ", " + person.getLoc().getName() + ", "
                + person.getLoc().getX() + ", " + person.getLoc().getY() + ", " + person.getLoc().getZ() + "\n";
    }

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return -1;
        }
        Ticket t = (Ticket) o;
        return (int) (t.getPrice() - this.getPrice());
    }

    private void checkId() {
        if (id < 0) {
            throw new InvalidFieldException("Error in field id");
        }
    }

    private void checkDate() {
        if (creationDate == null) {
            throw new InvalidFieldException("Error in field creationDate");
        }
    }
}