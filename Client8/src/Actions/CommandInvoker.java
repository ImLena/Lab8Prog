package Actions;

import MainWindow.MainController;
import Collections.Ticket;

import java.io.IOException;
import java.util.*;

public class CommandInvoker {
    public static Queue<String> history = new LinkedList<>();
    public static final LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
    private int count = 0;
    public void addCom(String key, Command com){
        commands.put(key,com);
    }
        public void execute(String[] commandName, Scanner in, Ticket t) {
            try {
                Command command = commands.get(commandName[0]);
                command.execute(commandName, in, t);
                if (count < 11) {
                    history.offer(commandName[0]);
                    count++;
                } else {
                    history.poll();
                    history.offer(commandName[0]);
                }
            }catch (NullPointerException e){
                System.out.println("Command " + Arrays.toString(commandName) + " doesn't exist");
            } catch (IllegalStateException | IOException | ClassNotFoundException ex) {
                System.out.println("Something strange happened, server is dead. Come back later");
                System.exit(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        public static Queue<String> getHistory(){
            return history;
        }

    public static LinkedHashMap<String, Command> getCommands(){
            return commands;
        }

        public static Command getCommand(String com){
            return commands.get(com);
        }
}
