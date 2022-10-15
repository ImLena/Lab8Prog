package Actions;

import Collections.CommandsManager;
import Other.Answer;
import Other.ReadCommand;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Класс для реализации команды show
 */

public class Show extends Command implements Serializable {
    private static final long serialVersionUID = 32L;

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public Answer execute(ReadCommand com, CommandsManager mc) throws IOException {
        return new Answer(null, mc.getArr());

    }
}
