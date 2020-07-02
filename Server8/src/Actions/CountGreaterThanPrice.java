package Actions;

import Collections.MapCommands;
import Other.ReadCommand;
import Other.Answer;

import java.io.IOException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Класс для реализации команды count_greater_than_price
 */

public class CountGreaterThanPrice extends Command {
    private static final long serialVersionUID = 32L;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public Answer execute(ReadCommand com, MapCommands mc) throws IOException {
        String price = com.getStrArgs();
        float pr = Float.parseFloat(price);
        try {
            lock.readLock().lock();
            return new Answer(mc.count_greater_than_price(pr), null);
        }catch (NumberFormatException e){
            return new Answer("wrongKey", null);
        } catch (NullPointerException e){
            return new Answer("wrongKey",null);
        } finally {
            lock.readLock().unlock();
        }
    }
}
