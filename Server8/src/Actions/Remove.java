package Actions;

import DataBase.TicketsDB;
import Collections.MapCommands;
import Other.ReadCommand;
import Other.Answer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Класс для реализации команды remove
 */

public class Remove extends Command {
    private static final long serialVersionUID = 32L;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public Answer execute(ReadCommand com, MapCommands mc) throws IOException {
        try{
        String arg = com.getStrArgs();
        Long id = Long.valueOf(arg);
        String user = com.getLogin();
        String answ;
        if (mc.getTickets().containsKey(id)) {
            TicketsDB.remove(id, user);
            try {
                lock.writeLock().lock();
                answ = mc.remove(id, user);
            } finally {
                lock.writeLock().unlock();
            }
            return new Answer(answ, null);
        } else {
            return new Answer("noID", null);
        }
        } catch (NumberFormatException | SQLException e) {
            return new Answer("wrongKey", null);
        }
    }
}
