package Actions;

import DataBase.RegistBase;
import Collections.MapCommands;
import Other.ReadCommand;
import Other.Answer;

import java.io.IOException;
import java.sql.SQLException;

public class Login extends Command {
    @Override
    public Answer execute(ReadCommand com, MapCommands mc) throws IOException, SQLException {
        String login = com.getLogin();
        String pass = com.getPass();
        return new Answer(RegistBase.login(login, pass), null);
    }
}
