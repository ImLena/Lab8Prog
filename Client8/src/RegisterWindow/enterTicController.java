package RegisterWindow;

import Collections.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class enterTicController {
    public static Stage stage;
    private ResourceBundle currentBundle;
    public static String command;
    public static String id;
    @FXML
    TextField name;
    @FXML
    TextField x;
    @FXML
    TextField y;
    @FXML
    ComboBox<TicketType> type;
    @FXML
    TextField height;
    @FXML
    TextField price;
    @FXML
    TextField xPl;
    @FXML
    TextField yPl;
    @FXML
    TextField zPl;
    @FXML
    TextField place;

    private String user;
    private Long Id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String Name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float Price; //Значение поля должно быть больше 0
    private TicketType Type; //Поле может быть null
    private Person person; //Поле не может быть null

    private Float X; //Значение поля должно быть больше -502
    private Integer Y; //Поле не может быть null
    private Long XPl; //Поле не может быть null
    private Float YPl; //Поле не может быть null
    private Float ZPl; //Поле не может быть null
    private String Place; //Поле не может быть null
    private double Height; //Значение поля должно быть больше 0
    private Location location; //Поле не может быть null
    Ticket tic = new Ticket();

    public void initialize() {
        ObservableList<TicketType> types = FXCollections.observableArrayList(TicketType.CHEAP, TicketType.BUDGETARY, TicketType.USUAL, TicketType.VIP);
        type.getItems().addAll(types);
    }

    @FXML
    public void OKClick(ActionEvent actionEvent) {
        Name = name.getText();
        try {
            X = Float.parseFloat(x.getText());
        } catch (NumberFormatException e) {
            return;
        }
        try {
            Y = Integer.parseInt(y.getText());
        } catch (NumberFormatException e) {
            return;
        }
        try {
            Price = Float.parseFloat(price.getText());
        } catch (NumberFormatException e) {
            return;
        }
        try {
            Height = Double.parseDouble(height.getText());
        } catch (NumberFormatException e) {
            return;
        }
        try {
            XPl = Long.parseLong(xPl.getText());
        } catch (NumberFormatException e) {
            return;
        }
        try {
            YPl = Float.parseFloat(yPl.getText());
        } catch (NumberFormatException e) {
            return;
        }
        try {
            ZPl = Float.parseFloat(zPl.getText());
        } catch (NumberFormatException e) {
            return;
        }
        Type = type.getValue();
        Place = place.getText();
        if (!checker()) return;

        try {
            coordinates = new Coordinates(X, Y);
            location = new Location(XPl, YPl, ZPl, Place);
            person = new Person(Height, location);
            tic.setName(Name);
            tic.setCoordinates(coordinates);
            tic.setPrice(Price);
            tic.setPerson(person);
            tic.setType(Type);
            tic.setCreationDate(LocalDateTime.now());
            Stage thisStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            thisStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TextField getName() {
        return name;
    }

    public TextField getX() {
        return x;
    }

    public TextField getY() {
        return y;
    }

    public ComboBox<TicketType> getType() {
        return type;
    }

    public TextField getHeight() {
        return height;
    }

    public TextField getPrice() {
        return price;
    }

    public TextField getxPl() {
        return xPl;
    }

    public TextField getyPl() {
        return yPl;
    }

    public TextField getzPl() {
        return zPl;
    }

    public Ticket getTic() {
        return tic;
    }

    public TextField getPlace() {
        return place;
    }

    private boolean checker() {
        return !(Price <= 0) && xPl != null && Y != null && X >= -502
                && !Place.trim().isEmpty() && !Name.trim().isEmpty();
    }

    private void errorWindow(String fieldName, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setCurrentBundle(ResourceBundle currentBundle) {
        this.currentBundle = currentBundle;
    }
}
