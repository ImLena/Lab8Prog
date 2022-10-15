package Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class LocalCollection {
    private static ObservableList<Ticket> tickets = FXCollections.observableArrayList();

    private static LinkedHashMap<String, Color> types = new LinkedHashMap<>();

    public static ObservableList<Ticket> getTickets() {
        return tickets;
    }

    public static void setTickets(ArrayList<Ticket> tickets) {
        LocalCollection.tickets = FXCollections.observableArrayList(tickets);
    }

    private static ArrayList<Ticket> arrList = new ArrayList<>();

    public static LinkedHashMap<String, Color> getType() {
        return types;
    }

    public static ArrayList<Ticket> getArrList() {
        return arrList;
    }

}
