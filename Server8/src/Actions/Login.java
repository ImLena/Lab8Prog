package Actions;

import Collections.CommandsManager;
import DataBase.RegistBase;
import Other.Answer;
import Other.ReadCommand;

import java.io.IOException;
import java.sql.SQLException;

public class Login extends Command {
    @Override
    public Answer execute(ReadCommand com, CommandsManager mc) throws IOException, SQLException {
        String login = com.getLogin();
        String pass = com.getPass();
        return new Answer(RegistBase.login(login, pass), null);
    }
}
