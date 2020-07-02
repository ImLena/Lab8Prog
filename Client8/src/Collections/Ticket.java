package Collections;

import Exceptions.InvalidFieldException;
import javafx.beans.property.*;
import javafx.util.Builder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.zip.InflaterInputStream;

/**
 * Класс для заполнения полей ticket
 */

public class Ticket implements Comparable, Serializable {
    private String user;
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float price; //Значение поля должно быть больше 0
    private TicketType type; //Поле может быть null
    private Person person; //Поле не может быть null
    private Float x; //Значение поля должно быть больше -502
    private Integer y; //Поле не может быть null
    private Long xPl; //Поле не может быть null
    private Float yPl; //Поле не может быть null
    private Float zPl; //Поле не может быть null
    private String place; //Поле не может быть null
    private double height; //Значение поля должно быть больше 0
    private Location location; //Поле не может быть null
    public Ticket(){
    }
    private static final long serialVersionUID = 32L;

    public void setUser(String user) {
        this.user = user;
    }

    public float getPrice() {
        return price;
    }

    public Ticket(CreateTicket createTicket) {
        this.user = createTicket.user;
        this.id = createTicket.id;
        this.name = createTicket.name;
        this.coordinates = createTicket.coordinates;
        this.creationDate = createTicket.creationDate;
        this.price = createTicket.price;
        this.type = createTicket.type;
        this.person = createTicket.person;
        this.location = createTicket.location;
    }

    public void setId(Long id) {
        this.id = id;
        checkId();
    }
    public LongProperty getIdProperty() {
        return new SimpleLongProperty(id);
    }
    public StringProperty getNameProperty() {
        return new SimpleStringProperty(name);
    }
    public StringProperty getUserProperty() {
        return new SimpleStringProperty(user);
    }


    public Float getX() {
        return getCoords().getX();
    }

    public Integer getY() {
        return getCoords().getY();
    }

    public Long getxPl() {
        return getPerson().getLocation().getX();
    }

    public Float getyPl() {
        return getPerson().getLocation().getY();
    }

    public Float getzPl() {
        return getPerson().getLocation().getZ();
    }

    public String getPlace() {
        return getPerson().getLocation().getName();
    }

    public double getHeight() {
        return getPerson().getHeight();
    }

    public Location getLocation() {
        return location;
    }

    public String getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoords() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public TicketType getType() {
        return type;
    }

    public Person getPerson() {
        return person;
    }

    public Long getId(){
        return id;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
        checkDate();
    }

    public void setName(String name) {
        this.name = name;
        checkName();
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        checkCoords();
    }

    public void setPrice(float price) {
        this.price = price;
        checkPrice();
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public void setPerson(Person person) {
        this.person = person;
        checkPers();
    }

    @Override
    public String toString() {
        return ( "id = " + id + ", name = " + name + ", coordinates: " + coordinates + "; creationDate = " + creationDate + ", price = " + price + ", Ticket type = " + type + ", person: " + person);
    }


    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return -1;
        }
        Ticket t = (Ticket) o;
        return (int)  (t.getPrice() - this.getPrice());
    }

    private void checkName() {
        if (name == null || name.isEmpty()) {
            throw new InvalidFieldException("Error in field name");
        }
    }
    private void checkCoords() {
        if (coordinates == null) {
            throw new InvalidFieldException("Error in field coordinates");
        }
    }
    private void checkId() {
        if (id < 0) {
            throw new InvalidFieldException("Error in field id");
        }
    }
    private void checkPrice() {
        if (price <= 0) {
            throw new InvalidFieldException("Error in field price");
        }
    }
    private void checkDate() {
        if (creationDate == null) {
            throw new InvalidFieldException("Error in field creationDate");
        }
    }
    private void checkPers(){
        if (person == null) {
            throw new InvalidFieldException("Error in field person");
        }
    }

    public static class CreateTicket {
        private String user;
        private Long id;
        private String name;
        private Coordinates coordinates;
        private LocalDateTime creationDate;
        private float price;
        private TicketType type;
        private Person person;
        private Float x;
        private Integer y;
        private Long xPl;
        private Float yPl;
        private Float zPl;
        private String place;
        private double height;
        private Location location;

        public CreateTicket() {
        }

        public CreateTicket id(long id) {
            this.id = id;
            return this;
        }

        public CreateTicket user(String user) {
            this.user = user;
            return this;
        }

        public CreateTicket creationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public CreateTicket name(String name) {
            this.name = name;
            return this;
        }

        public CreateTicket x(Float x) {
            this.x = x;
            return this;
        }

        public CreateTicket y(Integer y) {
            this.y = y;
            return this;
        }

        public CreateTicket height(double height) {
            this.height = height;
            return this;
        }

        public CreateTicket type(TicketType type) {
            this.type = type;
            return this;
        }

        public CreateTicket price(Float price) {
            this.price = price;
            return this;
        }

        public CreateTicket xPl(Long xPl) {
            this.xPl = xPl;
            return this;
        }
        public StringProperty getNameProperty() {
            return new SimpleStringProperty(name);
        }


        public CreateTicket yPl(Float yPl) {
            this.yPl = yPl;
            return this;
        }

        public CreateTicket zPl(Float zPl) {
            this.zPl = zPl;
            return this;
        }

        public CreateTicket place(String place) {
            this.place = place;
            return this;
        }
        public CreateTicket coordinates(Float x, Integer y){
            this.coordinates=new Coordinates(x,y);
            return this;
        }
        public CreateTicket person(double height, Location location){
            this.person=new Person(height,location);
            return this;
        }
        public CreateTicket location(Long xPl, Float yPl, Float zPl, String place){
            this.location = new Location(xPl, yPl, zPl, place);
            return this;
        }
        public Ticket createTicket() {
            return new Ticket(this);
        }
    }

}