package Actions;

import Collections.CommandsManager;
import Other.Answer;
import Other.ReadCommand;

import java.io.IOException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Класс для реализации команды count_greater_than_price
 */

public class CountGreaterThanPrice extends Command {
    private static final long serialVersionUID = 32L;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public Answer execute(ReadCommand com, CommandsManager mc) throws IOException {
        String price = com.getStrArgs();
        float pr = Float.parseFloat(price);
        try {
            lock.readLock().lock();
            return new Answer(mc.count_greater_than_price(pr), null);
        } catch (NumberFormatException | NullPointerException e) {
            return new Answer("wrongKey", null);
        } finally {
            lock.readLock().unlock();
        }
    }
}
