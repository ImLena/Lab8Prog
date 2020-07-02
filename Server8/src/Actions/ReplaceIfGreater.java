package Actions;


import DataBase.TicketsDB;
import Collections.MapCommands;
import Collections.Ticket;
import Other.ReadCommand;
import Other.Answer;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Класс для реализации команды replace_if_greater
 */

public class ReplaceIfGreater extends Command{
    private static final long serialVersionUID = 32L;
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    @Override
    public Answer execute(ReadCommand com, MapCommands mc) throws IOException, SQLException {
        Ticket tic = com.getTicket();
        Long id = Long.valueOf(com.getStrArgs());
        tic.setCreationDate(LocalDateTime.now());
        if (mc.getTickets().containsKey(id)) {
            TicketsDB.replaceIfGreater(tic, id);
            try {
            lock.writeLock().lock();
                mc.replace_if_greater(tic.getId(), tic, tic.getUser());
            }finally {
                lock.writeLock().unlock();
            }
            if (mc.replace_if_greater(id, tic, tic.getUser())) {
                return new Answer("executed", null);
            } else {
                return new Answer("notRemoved", null);
            }
        } else {
            return new Answer("noID", null);
        }
    }
}
