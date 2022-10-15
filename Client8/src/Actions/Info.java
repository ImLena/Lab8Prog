package Actions;

import Collections.Ticket;

import java.io.IOException;
import java.util.Scanner;

/**
 * Класс для реализации команды info
 */

public class Info extends Command {
    transient final CommandReceiver commandReceiver;
    private static final long serialVersionUID = 32L;

    public Info(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    protected String help() {
        return "help - show information about collection";
    }

    @Override
    protected void execute(String[] args, Scanner in, Ticket t) throws IOException, InterruptedException, ClassNotFoundException {
        commandReceiver.info();
    }
}
