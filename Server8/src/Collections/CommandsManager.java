package Collections;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommandsManager implements Comparable<Ticket> {
    private TicketMap tm;

    private static Long id = 0L;
    private String name;
    private Coordinates coordinates;
    private static LocalDateTime creationDate;
    private float price;
    private TicketType type;
    private Person person;
    private Location location;
    private static ArrayList<Ticket> arrTic = new ArrayList<Ticket>();

    public CommandsManager(TicketMap tm) {
        this.tm = tm;
    }


    public String info() throws IOException {
        return "Collection type: " + tm.getClass().toString() + "\n" +
                "Date and time of initialization: " + tm.getCreationDate() + "\n" +
                "Number of items: " + tm.getTickets().size() + "\n";
    }

    public String count_greater_than_price(Float price) throws IOException {
        int count = 0;
        for (Map.Entry<Long, Ticket> e : tm.getTickets().entrySet()) {
            Ticket tic = e.getValue();
            if (tic.getPrice() > price) {
                count++;
            }
        }
        return String.valueOf(count);
    }

    public String min_by_creation_date() throws IOException {
        StringBuilder sb = new StringBuilder();
        if (!tm.getTickets().isEmpty()) {
            Map.Entry<Long, Ticket> tick = tm.getTickets().entrySet().stream().min(Comparator.comparing(x -> x.getValue().getCreationDate())).get();
            sb.append(tick.toString());
        } else {
            sb.append("empty");
        }
        return sb.toString();
    }

    public String print_descending() {
        StringBuilder sb = new StringBuilder();
        if (!tm.getTickets().isEmpty()) {
            tm.getTickets().entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                    .forEach(x -> sb.append(x.toString() + "\n"));
        } else {
            sb.append("empty");
        }
        return sb.toString();
    }

    public String remove(Long id, String user) throws IOException {
        if (tm.getTickets().get(id).getUser().equals(user)) {
            tm.getTickets().remove(id);
            return "removed";
        } else {
            return "wrongUser";
        }
    }

    public void remove_greater(Ticket tic, String user) {
        tm.getTickets().entrySet().removeIf(x -> (x.getValue().compareTo(tic) < 0) && (x.getValue().getUser().equals(user)));
    }

    public String show() throws IOException {
        StringBuilder sb = new StringBuilder();
        if (!(tm.getTickets().isEmpty())) {
            for (Map.Entry<Long, Ticket> entry : tm.getTickets().entrySet()) {
                sb.append("Key: ").append(entry.getKey()).append("\n").append("Value: ").append(entry.getValue().toString()).append("\n");
            }
            return sb.toString();
        } else return "empty";
    }

    public String insert(Long elemId, Ticket tic) throws IOException {
        if (!tm.getTickets().containsKey(elemId)) {
            tm.getTickets().put(elemId, tic);
            return "executed";
        } else {
            return "elementExist";
        }
    }

    public String update(Long id, Ticket tic) {
        tm.getTickets().put(id, tic);
        return "executed";
    }

    public String clear(String user) {
        StringBuilder sb = new StringBuilder();
        tm.getTickets().entrySet().removeIf(x -> x.getValue().getUser().equals(user));
        return "executed";
    }

    public boolean replace_if_greater(Long id, Ticket tic, String user) throws IOException {
        if ((tm.getTickets().get(id).compareTo(tic) > 0) && (tm.getTickets().get(id).getUser().equals(user))) {
            tm.getTickets().put(id, tic);
            return true;
        } else {
            return false;
        }
    }

    public LinkedHashMap<Long, Ticket> getTickets() {
        return tm.getTickets();
    }

    @Override
    public int compareTo(Ticket t) {
        if (t == null) {
            return -1;
        }
        return (int) (this.price - t.getPrice());
    }

    @Override
    public String toString() {
        return (name + ", " + coordinates.getX() + ", " + coordinates.getY() + ", " + price + ", "
                + type + ", " + person.getHeight() + ", " + location.getX() + ", " + location.getY()
                + ", " + location.getZ() + ", " + location.getName() + "\n");

    }

    public String sortID() {
        StringBuilder sb = new StringBuilder();
        if (!tm.getTickets().isEmpty()) {
            tm.getTickets().entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey(Comparator.naturalOrder()))
                    .forEach(x -> sb.append(x.toString() + "\n"));
        } else {
            sb.append("empty");
        }
        return sb.toString();
    }

    public ArrayList<Ticket> getArr() {
        arrTic.clear();
        tm.getTickets().forEach((key, value) -> arrTic.add(value));
        System.out.println(arrTic.toString());
        return arrTic;
    }

}
