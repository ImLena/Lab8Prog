package Actions;


import Collections.Ticket;

import java.util.Scanner;

/**
 * Класс для реализации команды history
 */

public class History extends Command {
    transient final CommandReceiver commandReceiver;
    private static final long serialVersionUID = 32L;

    public History(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    protected String help() {
        return "history - show last 11 commands";
    }

    @Override
    protected void execute(String[] args, Scanner in, Ticket t) {
        commandReceiver.history();
    }
}
