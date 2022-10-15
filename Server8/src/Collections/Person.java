package Collections;

import java.io.Serializable;

/**
 * Класс для создания person
 */

public class Person implements Serializable {
    private double height;
    private Location location;
    private static final long serialVersionUID = 32L;


    @Override
    public String toString() {
        return (" height = " + height + ", location: " + location.toString());
    }

    public Person(double height, Location location) {
        this.height = height;
        this.location = location;
    }

    public Location getLoc() {
        return location;
    }

    public double getHeight() {
        return height;
    }

}

