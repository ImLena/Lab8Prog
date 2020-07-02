package Actions;

import DataBase.TicketsDB;
import Collections.MapCommands;
import Collections.Ticket;
import Other.ReadCommand;
import Other.Answer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Класс для реализации команды remove_greater
 */

public class RemoveGreater extends Command{

    private static final long serialVersionUID = 32L;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public Answer execute(ReadCommand com, MapCommands mc) throws IOException {
        try{
        Ticket tic = com.getTicket();
        String user = com.getLogin();
            if (tic != null) {
                TicketsDB.removeGreater(tic, user);
                try {
                    lock.writeLock().lock();
                    mc.remove_greater(tic, user);
                }finally {
                    lock.writeLock().unlock();
                }
                return new Answer("executed", null);
            }else {
                return new Answer("Ticket expected", null);
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            return new Answer("wrongKey", null);
        }
    }
}
