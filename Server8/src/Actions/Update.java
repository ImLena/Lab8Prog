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
 * Класс для реализации команды update
 */

public class Update extends Command {

    private static final long serialVersionUID = 32L;
    private ReadWriteLock lock = new ReentrantReadWriteLock();


    @Override
    public Answer execute(ReadCommand com, CommandsManager mc) throws IOException {
        try {
            String answ;
            String arg = com.getStrArgs();
            Long id = Long.valueOf(arg);
            Ticket tic = com.getTicket();
            if (mc.getTickets().containsKey(id)) {
                if (mc.getTickets().get(id).getUser().equals(tic.getUser())) {
                    TicketsDB.update(tic, id);
                    try {
                        lock.writeLock().lock();
                        answ = mc.update(id, tic);
                    } finally {
                        lock.writeLock().unlock();
                    }
                    return new Answer(answ, null);
                } else {
                    return new Answer("wrongUser", null);
                }
            } else {
                return new Answer("noID", null);
            }

        } catch (NumberFormatException | SQLException e) {
            return new Answer("wrongKey", null);
        }

    }
}