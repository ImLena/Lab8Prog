package RegistWindow;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import Collections.TicketType;
import MainWindow.MainController;
import Other.Client;
import Other.Login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegWindow {

    private MainController mainController;
    private Client client;
    private String user;
    private String pass;
    String[] command = new String[3];

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML
    private Text str1;
    @FXML
    private Text str2;
    @FXML
    private Text str3;
    @FXML
    private Text loginStr;
    @FXML
    private Text passStr;
    @FXML
    private Button logInButton;
    @FXML
    private Button registerButton;


    @FXML
    private ComboBox<String> lang;

    // This method is called by the FXMLLoader when initialization is complete
           /* VBox vbox = new VBox();
vbox.setPadding(new Insets(10, 10, 10, 10));
vbox.setSpacing(10);
    Button btn1 = new Button("1");
    Button btn2 = new Button("2");
    Button btn3 = new Button("3");
    Button btn4 = new Button("4");
vbox.getChildren().addAll(btn1, btn2, btn3, btn4);
    */
/*    void initialize() throws InterruptedException, IOException, ClassNotFoundException {
        username.textProperty().addListener(
                (observable, oldValue, newValue) -> user = newValue);
        password.textProperty().addListener(
                (observable, oldValue, newValue) -> pass = newValue);
        loginButton.setOnAction(actionEvent -> {
            loginButtonClick(new ActionEvent());
        });
        registerButton.setOnAction(actionEvent -> {
            registerButtonClick(new ActionEvent());
        });
    }*/

public void initialize(){
    ObservableList<String> languages = FXCollections.observableArrayList("en_CA","ru", "is","pl");
    lang.getItems().addAll(languages);
}


    public void loginButtonClick(ActionEvent actionEvent) {
        try {
            String login = username.getText();
            String pass = password.getText();
            command[0] = "login";
            command[1]= login;
            command[2] = pass;
            String answer = Login.registerUser(Client.getChannel(), command);
            if (answer.equals("user logged")) {
                showAlert(Alert.AlertType.INFORMATION, mainController.getCurrentBundle().getString("login"), null, mainController.getCurrentBundle().getString("logged"));
                client.setLogged(true);
                client.setLogin(command[1]);
                client.setPassword(command[2]);
                Stage authorizedStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                authorizedStage.close();/*
                Client client = new Client(command[1]);
                client.client(command[1], command[2]);*/
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", null, "Wrong login or password");
            }
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", null,  "IOException " + e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR,"Error", null, "Unexpected Exception " + e.getMessage());
        }
    }

    public void registerButtonClick(ActionEvent actionEvent) {
        try {
            String login = username.getText();
            String pass = password.getText();
            command[0] = "regist";
            command[1]= login;
            command[2] = pass;
            String answer = Login.registerUser(Client.getChannel(), command);
            if (answer.equals("user registered")) {
                showAlert(Alert.AlertType.INFORMATION, "Log in", null, "You logged in successful");
                Stage authorizedStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                client.setLogged(true);
                client.setLogin(command[1]);
                client.setPassword(command[2]);
                authorizedStage.close();/*
                Client client = new Client(command[1]);
                client.client(command[1], command[2]);*/
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", null, "Wrong login or password");
            }
        } catch (IOException | InterruptedException | ClassNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, "Error", null,  "IOException" + e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR,"Error", null, "Unexpected Exception" + e.getMessage());
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    @FXML
    private void setRussian() {
        mainController.setCurrentBundle(ResourceBundle.getBundle("bundles/bundles", new Locale("ru")));
        changeLanguage();
    }
    @FXML
    private void setEnglish() {
        mainController.setCurrentBundle(ResourceBundle.getBundle("bundles/bundles", new Locale("en", "CA")));
        changeLanguage();
    }
    @FXML
    private void setIrish() {
        mainController.setCurrentBundle(ResourceBundle.getBundle("bundles/bundles", new Locale("is")));
        changeLanguage();
    }
    @FXML
    private void setPolish() {
        mainController.setCurrentBundle(ResourceBundle.getBundle("bundles/bundles", new Locale("pl")));
        changeLanguage();
    }

    @FXML
    private void langChoose() {
        String language = lang.getSelectionModel().getSelectedItem().toString();
        switch (language){
            case "ru":
                setRussian();
                break;
            case "en_CA":
                setEnglish();
                break;
            case "is":
                setIrish();
                break;
            case "pl":
                setPolish();
                break;
            default:
                setEnglish();
                break;
        }
        //mainController.setCurrentBundle(ResourceBundle.getBundle("bundles/bundle", new Locale(language)));
        /*setChanged();
        notifyObservers(mainController.getCurrentBundle());*/
    }


    private void changeLanguage() {
        //lang.setText(mainController.getCurrentBundle().getString("selectLanguage"));
        logInButton.setText(mainController.getCurrentBundle().getString("login"));
        registerButton.setText(mainController.getCurrentBundle().getString("register"));
        loginStr.setText(mainController.getCurrentBundle().getString("login"));
        passStr.setText(mainController.getCurrentBundle().getString("password"));
        str1.setText(mainController.getCurrentBundle().getString("Welcome"));
        str2.setText(mainController.getCurrentBundle().getString("please"));
        str3.setText(mainController.getCurrentBundle().getString("start"));
    }
}
