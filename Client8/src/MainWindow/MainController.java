package MainWindow;

import Actions.*;
import Collections.LocalCollection;
import Collections.Ticket;
import Collections.TicketType;
import Other.Client;
import Other.ReadCommand;
import RegisterWindow.RegWindow;
import RegisterWindow.enterTicController;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MainController implements Initializable {
    private static Logger log = Logger.getLogger(MainController.class.getName());
    private Client client;
    private ResourceBundle currentBundle;
    private CommandInvoker ci;
    private CommandReceiver cr;
    private ArrayList<Ticket> arrTic;
    private static LocalCollection lc;
    private String[] nowCom = new String[2];
    Reader r = new BufferedReader(new InputStreamReader(System.in));
    Scanner in = new Scanner(r);

    @FXML
    TextField idFilter;
    @FXML
    TextField nameFilter;
    @FXML
    TextField xFilter;
    @FXML
    TextField yFilter;
    @FXML
    TextField heightFilter;
    @FXML
    TextField crDateFilter;
    @FXML
    TextField typeFilter;
    @FXML
    TextField priceFilter;
    @FXML
    TextField xPlFilter;
    @FXML
    TextField yPlFilter;
    @FXML
    TextField zPlFilter;
    @FXML
    private
    TextField placeFilter;
    @FXML
    private
    TextField userFilter;
    @FXML
    private ComboBox<String> lang;

    @FXML
    FlowPane drawing;

    @FXML
    Button count_greater_than_price;
    @FXML
    TableView<Ticket> tickets;
    @FXML
    TableColumn<Ticket, Long> id;
    @FXML
    TableColumn<Ticket, String> name;
    @FXML
    TableColumn<Ticket, Float> x;
    @FXML
    TableColumn<Ticket, Integer> y;
    @FXML
    TableColumn<Ticket, LocalDateTime> creationDate;
    @FXML
    TableColumn<Ticket, Float> price;
    @FXML
    TableColumn<Ticket, TicketType> type;
    @FXML
    TableColumn<Ticket, Double> height;
    @FXML
    TableColumn<Ticket, Long> xPl;
    @FXML
    TableColumn<Ticket, Float> yPl;
    @FXML
    TableColumn<Ticket, Float> zPl;
    @FXML
    TableColumn<Ticket, String> place;
    @FXML
    TableColumn<Ticket, String> user;
    @FXML
    private TextArea textRes;
    @FXML
    Button help;
    @FXML
    TextField arg;
    @FXML
    private Button insert;
    @FXML
    private Button update;
    @FXML
    private Button min_by_creation_date;
    @FXML
    private Button info;
    @FXML
    private Button remove_key;
    @FXML
    private Button replace_if_greater;
    @FXML
    private Button remove_greater;
    @FXML
    private Button exit;
    @FXML
    private Button clear;
    @FXML
    private Button execute_script;
    @FXML
    private Button history;
    @FXML
    private Label argument;
    @FXML
    private Label usernameLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            currentBundle = ResourceBundle.getBundle("bundles/bundles", new Locale("en"));
            ObservableList<String> languages = FXCollections.observableArrayList("en_CA", "ru", "is", "pl");
            lang.getItems().addAll(languages);
            client = new Client(this);
            client.loginClient(666);
            Client newClient = new Client(this);
            String fxmlFile = "/RegisterWindow/regWindow.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            //   fxmlLoader.setResources(ResourceBundle.getBundle("bundles/bundles", new Locale("ru")));
            Parent root = fxmlLoader.load();
            RegWindow reg = fxmlLoader.getController();
            reg.setClient(client);
            reg.setMainController(this);
            Stage authorizationStage = new Stage();
            authorizationStage.setTitle("Authorisation");
            authorizationStage.setResizable(false);
            authorizationStage.setScene(new Scene(root));
            authorizationStage.requestFocus();
            authorizationStage.initModality(Modality.WINDOW_MODAL);
            authorizationStage.showAndWait();
            if (!client.isLogged()) {
                log.info("User didn't logged, disconnecting.");
                System.exit(0);
            } else {
                log.info("User logged in");
                usernameLabel.setText(/*currentBundle.getString("username") + */client.getLogin());
                setCommands();
                getCollection();
                fillTable();
                visual();
                getTableDoubleClick();
                setUpTimer();

            }
        } catch (IOException e) {
//                showAlert(Alert.AlertType.ERROR, currentBundle.getString("Error"), currentBundle.getString("IOException" + e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAnswer(String s) {
        textRes.setText(currentBundle.getString(s));
    }

    public void setNubmerAnswer(String s) {
        textRes.setText(s);
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                log.info("alert closed");
            }
        });
    }

    public void setCurrentBundle(ResourceBundle currentBundle) {
        this.currentBundle = currentBundle;
    }

    public ResourceBundle getCurrentBundle() {
        return currentBundle;
    }

    private void fillTable() {
        tickets.setEditable(true);

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.cellFactoryProperty().addListener((observable, oldValue, newValue) -> updateByCell());

        x.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        x.setCellValueFactory(tickets -> tickets.getValue().getCoords().getXProperty().asObject());
        y.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        y.setCellValueFactory(tickets -> tickets.getValue().getCoords().getYProperty().asObject());
        creationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));

        price.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        price.setCellValueFactory(tickets -> tickets.getValue().getPriceProperty().asObject());

        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        height.setCellValueFactory(tickets -> tickets.getValue().getPerson().getHeightProperty().asObject());
        xPl.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        yPl.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        zPl.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        xPl.setCellValueFactory(tickets -> tickets.getValue().getPerson().getLocation().getXProperty().asObject());
        yPl.setCellValueFactory(tickets -> tickets.getValue().getPerson().getLocation().getYProperty().asObject());
        zPl.setCellValueFactory(tickets -> tickets.getValue().getPerson().getLocation().getZProperty().asObject());
        place.setCellFactory(TextFieldTableCell.forTableColumn());
        place.setCellValueFactory(tickets -> tickets.getValue().getPerson().getLocation().getPlaceProperty());
        user.setCellValueFactory(new PropertyValueFactory<>("user"));
        updateTable();
    }

    public void updateTable() {
        FilteredList<Ticket> filtered = new FilteredList<>(LocalCollection.getTickets(), t -> true);
        userFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(tic -> tic.getUser().toLowerCase().contains(newValue.toLowerCase())));
        idFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(tic -> Long.toString(tic.getId()).contains(newValue)));
        nameFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(tic -> tic.getName().toLowerCase().contains(newValue.toLowerCase())));
        xFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(tic -> tic.getCoords().getX().toString().toLowerCase().contains(newValue.toLowerCase())));
        yFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(tic -> tic.getCoords().getY().toString().toLowerCase().contains(newValue.toLowerCase())));
        crDateFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(tic -> tic.getCreationDate().toString().toLowerCase().contains(newValue.toLowerCase())));
        priceFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(tic -> Float.toString(tic.getPrice()).toLowerCase().contains(newValue.toLowerCase())));
        typeFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(tic -> {
            String type = tic.getType() == null ? "" : String.valueOf(tic.getType());
            return type.toLowerCase().contains(newValue.toLowerCase());
        }));
        heightFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(tic -> Double.toString(tic.getPerson().getHeight()).toLowerCase().contains(newValue.toLowerCase())));
        xPlFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(tic -> tic.getxPl().toString().toLowerCase().contains(newValue.toLowerCase())));
        yPlFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(tic -> tic.getyPl().toString().toLowerCase().contains(newValue.toLowerCase())));
        zPlFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(tic -> tic.getzPl().toString().toLowerCase().contains(newValue.toLowerCase())));
        placeFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(tic -> tic.getPerson().getLocation().getName().toLowerCase().contains(newValue.toLowerCase())));
        SortedList<Ticket> sorted = new SortedList<>(filtered);
        sorted.comparatorProperty().bind(tickets.comparatorProperty());
        tickets.setItems(sorted);
    }

    private void setUpTimer() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(8), ev -> {
            try {

                getCollection();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            updateTable();
            visual();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void updateByCell() {

        tickets.setRowFactory(tv -> {
            TableRow<Ticket> row = new TableRow<>();
            if ((!row.isEmpty())) {
                Ticket selected = row.getItem();
                arg.setText(String.valueOf(selected.getId()));
                String com[] = new String[2];
                com[0] = "update";
                com[1] = String.valueOf(selected.getId());
                ci.execute(com, in, selected);
            }
            return row;
        });
    }

    private void getTableDoubleClick() {
        tickets.setRowFactory(tv -> {
            TableRow<Ticket> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    if ((!row.isEmpty())) {
                        Ticket selected = row.getItem();
                        arg.setText(String.valueOf(selected.getId()));/*
                        String com[] = new String[2];
                        com[0] = "update";
                        com[1] = String.valueOf(selected.getId());
                        ci.execute(com, in, selected);*/
                    }
                }
            });
            return row;
        });
    }


    public void getCollection() throws IOException, ClassNotFoundException, InterruptedException {
        client.writeCommand(new ReadCommand("show", null, null, client.getLogin(), client.getPassword()));
        Thread.sleep(1500);
        arrTic = client.getMessage().getTickets();
        LocalCollection.setTickets(arrTic);
    }


    public void help() {
        nowCom[0] = "help";
        ci.execute(nowCom, in, null);
    }

    public void count_greater_than_price() throws IOException {
        nowCom[0] = "count_greater_than_price";
        nowCom[1] = getArg();
        checkArg(nowCom[1]);
        ci.execute(nowCom, in, null);
    }

    public void remove_greater() throws InterruptedException, IOException, ClassNotFoundException {
        nowCom[0] = "remove_greater";
        nowCom[1] = getArg();
        checkArg(nowCom[1]);
        checkArg(nowCom[1]);
        Ticket t;
        t = enterTic();
        try {
            t.setId(Long.parseLong(nowCom[1]));
            getCollection();
        } catch (NullPointerException | IOException | ClassNotFoundException | InterruptedException e) {
            showAlert(Alert.AlertType.INFORMATION, "Error", "Enter arg");
        }
        t.setUser(client.getLogin());
        ci.execute(nowCom, in, t);
    }

    public void execute_script() {
        String[] s = new String[2];
        s[0] = "execute_script";
        s[1] = "script.txt";
        ci.execute(s, in, null);
    }

    public void history() {
        nowCom[0] = "history";
        ci.execute(nowCom, in, null);
    }

    public void info() {
        nowCom[0] = "info";
        ci.execute(nowCom, in, null);
    }

    public void insert() throws InterruptedException, IOException, ClassNotFoundException {
        nowCom[0] = "insert";
        nowCom[1] = getArg();
        checkArg(nowCom[1]);
        Ticket t;
        t = enterTic();
        try {
            t.setId(Long.parseLong(nowCom[1]));
            getCollection();
        } catch (NullPointerException e) {
            showAlert(Alert.AlertType.INFORMATION, "Error", "Enter arg");
        }
        t.setCreationDate(LocalDateTime.now());
        t.setUser(client.getLogin());
        ci.execute(nowCom, in, t);
        getCollection();
        fillTable();
        visual();
    }

    public void min_by_creation_date() throws InterruptedException, IOException, ClassNotFoundException {
        nowCom[0] = "min_by_creation_date";
        ci.execute(nowCom, in, null);
    }

    public void remove_key() throws IOException, InterruptedException, ClassNotFoundException {
        nowCom[0] = "remove_key";
        nowCom[1] = getArg();
        checkArg(nowCom[1]);
        ci.execute(nowCom, in, null);
        getCollection();
        fillTable();
        visual();
    }

    public void replace_if_greater() throws InterruptedException, IOException, ClassNotFoundException {
        nowCom[0] = "replace_if_greater";
        nowCom[1] = getArg();
        checkArg(nowCom[1]);
        Ticket t;
        t = enterTic();
        t.setId(Long.parseLong(nowCom[1]));
        t.setUser(client.getLogin());
        ci.execute(nowCom, in, t);
        getCollection();
        fillTable();
    }

    public void update() throws IOException, ClassNotFoundException, InterruptedException {
        nowCom[0] = "update";
        nowCom[1] = getArg();
        checkArg(nowCom[1]);
        Ticket t;
        t = enterTic();
        t.setCreationDate(toLocalDateTime());
        t.setId(Long.parseLong(nowCom[1]));
        t.setUser(client.getLogin());
        ci.execute(nowCom, in, t);
    }

    public void clear() throws InterruptedException, IOException, ClassNotFoundException {
        nowCom[0] = "clear";
        ci.execute(nowCom, in, null);
        getCollection();
        fillTable();
    }

    public void setCommands() {
        ci = new CommandInvoker();

        cr = new CommandReceiver(ci, client.getChannel(), client.getLogin(), client.getPassword());
        cr.setMainController(this);
        Command clear = new Clear(cr);
        Command cgtp = new CountGreaterThanPrice(cr);
        Command es = new ExecuteScript(cr);
        Command help = new Help(cr);
        Command history = new History(cr);
        Command info = new Info(cr);
        Command insert = new Insert(cr);
        Command mbcd = new MinByCreationDate(cr);
        Command remove = new Remove(cr);
        Command rg = new RemoveGreater(cr);
        Command rig = new ReplaceIfGreater(cr);
        Command update = new Update(cr);
        Command exit = new Exit(cr);

        ci.addCom("clear", clear);
        ci.addCom("count_greater_than_price", cgtp);
        ci.addCom("execute_script", es);
        ci.addCom("help", help);
        ci.addCom("history", history);
        ci.addCom("info", info);
        ci.addCom("insert", insert);
        ci.addCom("min_by_creation_date", mbcd);
        ci.addCom("remove_key", remove);
        ci.addCom("remove_greater", rg);
        ci.addCom("replace_if_greater", rig);
        ci.addCom("update", update);
        ci.addCom("exit", exit);
    }

    public String getArg() {
        return arg.getText();
    }

    private void checkArg(String arg) {
        if (arg == "") {
            showAlert(Alert.AlertType.INFORMATION, currentBundle.getString("Error"), currentBundle.getString("EnterArg"));
        }
    }

    public Ticket enterTic() {
        try {
            Ticket tic = new Ticket();
            String fxmlFile = "/RegisterWindow/enterTic.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            enterTicController enterTicController = fxmlLoader.getController();
            enterTicController.setCurrentBundle(currentBundle);
            Stage dialogStage = new Stage();
            dialogStage.setResizable(false);
            dialogStage.setScene(new Scene(root));
            dialogStage.requestFocus();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();

            tic = enterTicController.getTic();
            return tic;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    private void exit() {
        System.exit(0);
    }

    public void visual() {
        new Random();
        ArrayList<Ellipse> ellipses = new ArrayList<>();
        ArrayList<Ticket> appear = LocalCollection.getTickets().stream().filter(i -> !LocalCollection.getArrList().contains(i)).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Ticket> disappear = LocalCollection.getArrList().stream().filter(i -> !LocalCollection.getTickets().contains(i)).collect(Collectors.toCollection(ArrayList::new));
        HashSet<Ticket> all = new HashSet<>(LocalCollection.getTickets());
        all.addAll(LocalCollection.getArrList());

        for (Ticket tic : all) {
            //  newTic(tic);
            if (!LocalCollection.getType().containsKey(tic.getUser()))
                LocalCollection.getType().put(tic.getUser(), javafx.scene.paint.Color.color(Math.random(), Math.random(), Math.random()));
            double size = tic.getPrice() / 3;
            if (size > 100) {
                size = 100;
            }
            Ellipse ellipse = new Ellipse();
            ellipse.setRadiusX(size);
            ellipse.setRadiusY(size / 2);
            ellipse.centerXProperty().bind(drawing.widthProperty().multiply(tic.getCoords().getX()).divide(100));
            ellipse.centerYProperty().bind(drawing.heightProperty().multiply(tic.getCoords().getY()).divide(100));

            ellipse.setFill(LocalCollection.getType().get(tic.getUser()));

            TranslateTransition tt = new TranslateTransition(Duration.millis(3100), ellipse);
            tt.setAutoReverse(true);
            tt.setFromX(drawing.getLayoutX());
            tt.setFromY(drawing.getLayoutY());
            tt.setToY(ellipse.getCenterX());
            tt.setToY(ellipse.getCenterY());
            tt.setCycleCount(Animation.INDEFINITE);
            tt.setInterpolator(Interpolator.LINEAR);
            tt.play();
            if (appear.contains(tic)) {
                FadeTransition st = new FadeTransition(Duration.millis(3100), ellipse);
                st.setFromValue(0);
                st.setToValue(1);
                st.play();


            }
            if (disappear.contains(tic)) {
                FadeTransition st = new FadeTransition(Duration.millis(3100), ellipse);
                st.setFromValue(1);
                st.setToValue(0);
                st.setAutoReverse(true);
                st.play();
            } else {
                ellipses.add(ellipse);
                ellipse.setOnMouseClicked(mouseEvent -> {
                    textRes.setText(tic.toString());
                    tickets.getSelectionModel().select(tic);
                });
            }
        }
        drawing.getChildren().setAll(ellipses);
        LocalCollection.getArrList().clear();
        LocalCollection.getArrList().addAll(LocalCollection.getTickets());
    }

    @FXML
    private void setRussian() {
        setCurrentBundle(ResourceBundle.getBundle("bundles/bundles", new Locale("ru")));
        changeLanguage();
    }

    @FXML
    private void setEnglish() {
        setCurrentBundle(ResourceBundle.getBundle("bundles/bundles", new Locale("en", "CA")));
        changeLanguage();
    }

    @FXML
    private void setIrish() {
        setCurrentBundle(ResourceBundle.getBundle("bundles/bundles", new Locale("is")));
        changeLanguage();
    }

    @FXML
    private void setPolish() {
        setCurrentBundle(ResourceBundle.getBundle("bundles/bundles", new Locale("pl")));
        changeLanguage();
    }

    @FXML
    private void langChoose() {
        String language = lang.getSelectionModel().getSelectedItem().toString();
        switch (language) {
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
    }


    private void changeLanguage() {
        name.setText(getCurrentBundle().getString("name"));
        creationDate.setText(getCurrentBundle().getString("creationDate"));
        type.setText(getCurrentBundle().getString("type"));
        price.setText(getCurrentBundle().getString("price"));
        place.setText(getCurrentBundle().getString("place"));
        xPl.setText(getCurrentBundle().getString("xPl"));
        yPl.setText(getCurrentBundle().getString("yPl"));
        zPl.setText(getCurrentBundle().getString("zPl"));
        insert.setText(getCurrentBundle().getString("insert"));
        clear.setText(getCurrentBundle().getString("clear"));
        execute_script.setText(getCurrentBundle().getString("execute_script"));
        exit.setText(getCurrentBundle().getString("exit"));
        help.setText(getCurrentBundle().getString("help"));
        history.setText(getCurrentBundle().getString("history"));
        info.setText(getCurrentBundle().getString("info"));
        min_by_creation_date.setText(getCurrentBundle().getString("min_by_creation_date"));
        remove_greater.setText(getCurrentBundle().getString("remove_greater"));
        replace_if_greater.setText(getCurrentBundle().getString("replace_if_greater"));
        update.setText(getCurrentBundle().getString("update"));
        height.setText(getCurrentBundle().getString("height"));
        argument.setText(getCurrentBundle().getString("arg"));
        remove_key.setText(getCurrentBundle().getString("remove_key"));
        priceFilter.setPromptText((getCurrentBundle().getString("price")));
        placeFilter.setPromptText(getCurrentBundle().getString("place"));
        xPlFilter.setPromptText(getCurrentBundle().getString("xPl"));
        yPlFilter.setPromptText(getCurrentBundle().getString("yPl"));
        zPlFilter.setPromptText(getCurrentBundle().getString("zPl"));
        nameFilter.setPromptText((getCurrentBundle().getString("name")));
        crDateFilter.setPromptText(getCurrentBundle().getString("creationDate"));
        typeFilter.setPromptText(getCurrentBundle().getString("type"));
        heightFilter.setPromptText(getCurrentBundle().getString("height"));
        count_greater_than_price.setText(getCurrentBundle().getString("cgtp"));
        userFilter.setPromptText(getCurrentBundle().getString("user"));
    }

    public LocalDateTime toLocalDateTime() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withLocale(getCurrentBundle().getLocale());
        String ld = LocalDateTime.now().format(formatter);
        LocalDateTime dt = LocalDateTime.ofInstant(Instant.now(), setZone());
        return dt;
    }

    private ZoneId setZone() {
        System.out.println(currentBundle.getLocale().getDisplayLanguage());
        switch (currentBundle.getLocale().getDisplayLanguage()) {
            case "английский":
                return ZoneId.of("Canada/Yukon");
            case "русский":
                return ZoneId.of("Europe/Moscow");
            case "исландский":
                return ZoneId.of("Atlantic/Reykjavik");
            case "польский":
                return ZoneId.of("Europe/Warsaw");
        }
        return null;

    }

}
