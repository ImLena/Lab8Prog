package Actions;

import Collections.CommandsManager;
import Other.Answer;
import Other.ReadCommand;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class CommandInvoker {

    private static CommandInvoker instance;

    private final LinkedHashMap<String, Command> commands = new LinkedHashMap<>();

    public void addCom() {
        commands.put("clear", new Clear());
        commands.put("count_greater_than_price", new CountGreaterThanPrice());
        commands.put("info", new Info());
        commands.put("insert", new Insert());
        commands.put("min_by_creation_date", new MinByCreationDate());
        commands.put("print_descending", new PrintDescending());
        commands.put("remove_key", new Remove());
        commands.put("remove_greater", new RemoveGreater());
        commands.put("replace_if_greater", new ReplaceIfGreater());
        commands.put("show", new Show());
        commands.put("update", new Update());
        commands.put("login", new Login());
        commands.put("register", new Register());
    }

    public Answer executeCom(CommandsManager mc, ReadCommand com) throws IOException {
        addCom();
        Command cmd = commands.get(com.getComm());
        try {
            return cmd.execute(com, mc);
        } catch (SQLException e) {
            return new Answer("Something strange happened with Data Base: " + e.getMessage(), null);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            return new Answer("Server let out a death rattle", null);
        }
    }

    public static CommandInvoker getInstance() {
        if (instance == null) {
            instance = new CommandInvoker();
        }
        return instance;
    }
}
