package Actions;

import Collections.Ticket;

import java.io.IOException;
import java.util.Scanner;

/**
 * Класс для реализации команды min_by_creation_date
 */

public class MinByCreationDate extends Command {

    transient final CommandReceiver commandReceiver;
    private static final long serialVersionUID = 32L;

    public MinByCreationDate(CommandReceiver commandReceiver) {

        this.commandReceiver = commandReceiver;
    }
    @Override
    protected  String help(){
        return "min_by_creation_date - show element with minimal creation date";
    }
    @Override
    protected  void execute(String[] args, Scanner in, Ticket t) throws IOException, ClassNotFoundException, InterruptedException {
        commandReceiver.min_by_creation_date();
    }
}
