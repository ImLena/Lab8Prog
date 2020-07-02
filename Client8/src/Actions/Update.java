package Actions;

import java.io.IOException;
import java.util.Scanner;

/**
 * Класс для реализации команды update
 */

public class Update extends Command {

    transient final CommandReceiver commandReceiver;
    private static final long serialVersionUID = 32L;

    public Update(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    public String help(){
        return "update key {element} - update element by key";
    }

    @Override
    protected void execute(String[] args, Scanner in) throws IOException {
           // commandReceiver.update(args, in);
    }
}