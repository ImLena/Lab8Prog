package RegistWindow;

import Collections.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.beans.IntrospectionException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        //    showAlert(currentBundle.getString("coordX"), currentBundle.getString("IncorrectDataField"));
            return;
        }
        try {
            Y = Integer.parseInt(y.getText());
        } catch (NumberFormatException e) {
          //  showAlert(currentBundle.getString("coordY"), currentBundle.getString("IncorrectDataField"));
            return;
        }
        try {
            Price = Float.parseFloat(price.getText());
        } catch (NumberFormatException e) {
           // showAlert(currentBundle.getString("health"), currentBundle.getString("IncorrectDataField"));
            return;
        }
        try {
            Height = Double.parseDouble(height.getText());
        } catch (NumberFormatException e) {
            //    showAlert(currentBundle.getString("coordX"), currentBundle.getString("IncorrectDataField"));
            return;
        }
        try {
            XPl = Long.parseLong(xPl.getText());
        } catch (NumberFormatException e) {
         //   showAlert(currentBundle.getString("heartCount"), currentBundle.getString("IncorrectDataField"));
            return;
        }
        try {
            YPl = Float.parseFloat(yPl.getText());
        } catch (NumberFormatException e) {
            //    showAlert(currentBundle.getString("coordX"), currentBundle.getString("IncorrectDataField"));
            return;
        }
        try {
            ZPl = Float.parseFloat(zPl.getText());
        } catch (NumberFormatException e) {
            //    showAlert(currentBundle.getString("coordX"), currentBundle.getString("IncorrectDataField"));
            return;
        }
        Type = type.getValue();
        Place = place.getText();
        checker();

        try {
            //Coordinates coords = new Coordinates();
            coordinates = new Coordinates(X,Y);
            //Location loc = new Location();
            location= new Location(XPl, YPl, ZPl, Place);
           // Person person = new Person();
            person=new Person(Height, location);
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

    private void checker(){
        if (Price <= 0) {
        //  showAlert(currentBundle.getString("health"), currentBundle.getString("IncorrectNumber"));
        return;
    }
        if (xPl == null) {
        //  showAlert(currentBundle.getString("heartCount"), currentBundle.getString("IncorrectNumber"));
        return;
    }
        if (Place.trim().isEmpty()) {
            //showAlert(currentBundle.getString("chapterName"), currentBundle.getString("IncorrectDataField"));
            return;
        }
        if (Name.trim().isEmpty()) {
            //   showAlert(currentBundle.getString("name"), currentBundle.getString("IncorrectDataField"));
            return;
        }
        if (Y == null) {
            //  showAlert(currentBundle.getString("coordY"), currentBundle.getString("IncorrectNumber"));
            return;
        }
        if (X < -502) {
            //  showAlert(currentBundle.getString("coordX"), currentBundle.getString("IncorrectNumber"));
            return;
        }
        if (Name.trim().isEmpty()) {
            //   errorWindow(currentBundle.getString("name"), currentBundle.getString("IncorrectDataField"));
            return;
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {/*
        ArrayList<String> col = Stream.of(Color.values()).map(Color::name).collect(Collectors.toCollection(ArrayList::new));
        col.add("");
        String[] colors = col.toArray(new String[0]);
        String[] country = Stream.of(Country.values()).map(Country::name).toArray(String[]::new);*/
     /*   type.getItems().addAll(colors);
        type.getSelectionModel().select("");
      //  this.resourceBundle = resourceBundle;
        if (command.equals("update")) {
            Ticket tic = LocalCollection.getTickets().stream().filter(i -> i.getId() == Long.parseLong(id)).findFirst().orElse(null);
            fillFields(tic);
        }*/
    }

    private void errorWindow(String fieldName, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        /*alert.setTitle(currentBundle.getString("Error"));
        alert.setHeaderText(currentBundle.getString("IncorrectField") + "\"" + fieldName + "\"");
        */alert.setContentText(content);
        alert.showAndWait();
    }
    public void setCurrentBundle(ResourceBundle currentBundle) {
        this.currentBundle = currentBundle;
    }
}
