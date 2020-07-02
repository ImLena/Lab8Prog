package Actions;

import Collections.Ticket;

import java.io.IOException;
import java.util.Scanner;

/**
 * Класс для реализации команды insert
 */


public class Insert extends Command {
    transient final CommandReceiver commandReceiver;
    private static final long serialVersionUID = 32L;

    public Insert(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }
    public String help(){
        return "insert key {element} - add element by key";
    }
    public void execute(String[] args, Scanner in, Ticket t) throws InterruptedException, IOException, ClassNotFoundException {
                    commandReceiver.insert(args, t, in);
    }
}
