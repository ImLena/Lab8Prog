package Actions;

import Collections.CommandsManager;
import DataBase.TicketsDB;
import Other.Answer;
import Other.ReadCommand;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Класс для реализации команды clear
 */

public class Clear extends Command {
    private static final long serialVersionUID = 32L;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public Answer execute(ReadCommand com, CommandsManager mc) throws IOException, SQLException {
        TicketsDB.clear(com.getLogin());
        String s;
        try {
            lock.writeLock().lock();
            s = mc.clear(com.getLogin());
        }finally {
            lock.writeLock().unlock();
        }

        return new Answer(s, null);
    }
}
