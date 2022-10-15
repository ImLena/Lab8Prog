package Actions;

import Collections.Ticket;

import java.io.IOException;
import java.util.Scanner;

/**
 * Класс для реализации команды remove_greater
 */

public class RemoveGreater extends Command {

    transient final CommandReceiver commandReceiver;
    private static final long serialVersionUID = 32L;

    public RemoveGreater(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    public String help() {
        return "remove_greater {element} - remove all elements, which price is greater";
    }

    @Override
    protected void execute(String[] args, Scanner in, Ticket t) throws IOException, ClassNotFoundException, InterruptedException {
        commandReceiver.remove_greater(args, t, in);
    }
}
