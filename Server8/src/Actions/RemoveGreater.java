package Actions;

import Collections.CommandsManager;
import Collections.Ticket;
import DataBase.TicketsDB;
import Other.Answer;
import Other.ReadCommand;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Класс для реализации команды remove_greater
 */

public class RemoveGreater extends Command {

    private static final long serialVersionUID = 32L;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public Answer execute(ReadCommand com, CommandsManager mc) throws IOException {
        try {
            Ticket tic = com.getTicket();
            String user = com.getLogin();
            if (tic != null) {
                TicketsDB.removeGreater(tic, user);
                try {
                    lock.writeLock().lock();
                    mc.remove_greater(tic, user);
                } finally {
                    lock.writeLock().unlock();
                }
                return new Answer("executed", null);
            } else {
                return new Answer("Ticket expected", null);
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            return new Answer("wrongKey", null);
        }
    }
}
