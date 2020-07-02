package Collections;

import Exceptions.InvalidFieldException;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.Serializable;

/**
 * Класс для установки координат
 */


public class Coordinates implements Serializable {
    private Float x; //Значение поля должно быть больше -502
    private Integer y; //Поле не может быть null
    private static final long serialVersionUID = 32L;


    public Coordinates() {
    }

    public Coordinates(Float x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public FloatProperty getXProperty() {
        return new SimpleFloatProperty(x);
    }
    public IntegerProperty getYProperty() {
        return new SimpleIntegerProperty(y);
    }


    public void setX(Float x) throws InvalidFieldException {
        this.x = x;
        checkCoordsx();
    }


    public void setY(Integer y) throws InvalidFieldException {
        this.y = y;
        checkCoordsy();
    }

    public Float getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
    private void checkCoordsx() {
        if (x <= -502) {
            throw new InvalidFieldException("Error in field x");
        }
    }
    private void checkCoordsy(){
        if (y == null) {
            throw new InvalidFieldException("Error in field y");
        }
    }

    @Override
    public String toString() {
        return ("x = " + x + ", y = " + y);
    }

}
