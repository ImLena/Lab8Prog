package Actions;

import Collections.MapCommands;
import DataBase.RegistBase;
import Other.ReadCommand;
import Other.Answer;

import java.io.IOException;
import java.sql.SQLException;

public class Regist extends Command {
    @Override
    public Answer execute(ReadCommand com, MapCommands mc) throws IOException, SQLException {
        String login = com.getLogin();
        String pass = com.getPass();
        return new Answer(RegistBase.addNewUser(login, pass), null);
    }
}
