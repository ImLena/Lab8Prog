package Actions;

import Collections.CommandsManager;
import Other.Answer;
import Other.ReadCommand;

import java.io.IOException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Класс для реализации команды min_by_creation_date
 */

public class MinByCreationDate extends Command {

    private static final long serialVersionUID = 32L;
    private ReadWriteLock lock = new ReentrantReadWriteLock();


    @Override
    public Answer execute(ReadCommand com, CommandsManager mc) throws IOException {
        String answ;
        try {
            lock.readLock().lock();
            answ = mc.min_by_creation_date();
        } finally {
            lock.readLock().unlock();
        }
        return new Answer(answ, null);
    }
}
