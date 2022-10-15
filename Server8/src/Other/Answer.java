package Other;

import Collections.Ticket;

import java.io.Serializable;
import java.util.ArrayList;

public class Answer implements Serializable {
    private static final long serialVersionUID = 32L;
    private String user;
    private String answer;
    private ArrayList<Ticket> tickets;

    public Answer(String answer, ArrayList<Ticket> tickets) {
        this.answer = answer;
        this.tickets = tickets;
    }


    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }


    public String getAnswer() {
        return answer;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }
}
