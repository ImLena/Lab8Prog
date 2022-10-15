package Actions;

import Collections.CommandsManager;
import Collections.Ticket;
import DataBase.TicketsDB;
import Other.Answer;
import Other.ReadCommand;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Класс для реализации команды insert
 */


public class Insert extends Command {
    private static final long serialVersionUID = 32L;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public Answer execute(ReadCommand com, CommandsManager mc) throws IOException {
        try {
            Ticket tic = com.getTicket();
            tic.setCreationDate(LocalDateTime.now());
            tic.setUser(com.getLogin());
            if (!mc.getTickets().containsKey(tic.getId())) {
                TicketsDB.insert(tic);
                try {
                    lock.writeLock().lock();
                    mc.insert(Long.valueOf(com.getStrArgs()), tic);
                } finally {
                    lock.writeLock().unlock();
                }
                return new Answer("executed", null);
            } else {
                return new Answer("elementExist", null);
            }


        } catch (NumberFormatException | IOException | SQLException e) {
            return new Answer("Not correct key", null);
        }
    }
}
