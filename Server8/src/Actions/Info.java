package Actions;

import Collections.MapCommands;
import Other.ReadCommand;
import Other.Answer;

import java.io.IOException;

/**
 * Класс для реализации команды info
 */

public class Info extends Command {
    private static final long serialVersionUID = 32L;
    @Override
    public Answer execute (ReadCommand com, MapCommands mc) throws IOException {
        return new Answer(mc.info(), null);
    }
}
