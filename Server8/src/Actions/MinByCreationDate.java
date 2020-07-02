package Actions;

import Collections.MapCommands;
import Other.ReadCommand;
import Other.Answer;

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
    public Answer execute(ReadCommand com, MapCommands mc) throws IOException {
        String answ;
        try {
            lock.readLock().lock();
            answ = mc.min_by_creation_date();
        }finally {
            lock.readLock().unlock();
        }
        return new Answer(answ, null);
    }
}
