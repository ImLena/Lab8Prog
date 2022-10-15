package Actions;

import Collections.Ticket;

import java.io.IOException;
import java.util.Scanner;

/**
 * Класс для реализации команды remove
 */

public class Remove extends Command {

    transient final CommandReceiver commandReceiver;
    private static final long serialVersionUID = 32L;

    public Remove(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    public String help() {
        return "remove_key key - remove element by key";
    }

    @Override
    protected void execute(String[] args, Scanner in, Ticket t) throws IOException {
        if (args.length > 1) {
            commandReceiver.remove(args);
        } else {
            System.out.println("Enter id after command");
        }
    }
}
