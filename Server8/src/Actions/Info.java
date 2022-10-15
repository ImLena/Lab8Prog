package Actions;

import Collections.CommandsManager;
import Other.Answer;
import Other.ReadCommand;

import java.io.IOException;

/**
 * Класс для реализации команды info
 */

public class Info extends Command {
    private static final long serialVersionUID = 32L;

    @Override
    public Answer execute(ReadCommand com, CommandsManager mc) throws IOException {
        return new Answer(mc.info(), null);
    }
}
