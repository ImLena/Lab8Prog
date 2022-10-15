package Collections;

import Exceptions.InvalidFieldException;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.Serializable;

/**
 * Класс для создания person
 */

public class Person implements Serializable {
    private double height; //Значение поля должно быть больше 0
    private Location location; //Поле не может быть null
    private static final long serialVersionUID = 32L;

    public Location getLocation() {
        return location;
    }

    public Person() {
    }

    public Person(double height, Location location) {
        this.height = height;
        this.location = location;
    }

    public DoubleProperty getHeightProperty() {
        return new SimpleDoubleProperty(height);
    }

    public Property getLocationProperty() {
        return new SimpleObjectProperty(location);
    }

    @Override
    public String toString() {
        return (" height = " + height + ", location: " + location.toString());
    }

    public void setHeight(double height) throws InvalidFieldException {
        this.height = height;
        checkHeigh();
    }

    public void setLocation(Location location) {
        this.location = location;
        checkLoc();
    }

    public double getHeight() {
        return height;
    }

    private void checkHeigh() {
        if (height <= 0) {
            throw new InvalidFieldException("Error in field height");
        }
    }

    private void checkLoc() {
        if (location == null) {
            throw new InvalidFieldException("Error in field location");
        }
    }
}

