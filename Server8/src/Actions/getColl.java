package Actions;

import Collections.CommandsManager;
import Other.Answer;
import Other.ReadCommand;

import java.io.IOException;
import java.sql.SQLException;

public class getColl extends Command {
    @Override
    public Answer execute(ReadCommand com, CommandsManager mc) throws IOException, SQLException {
        return new Answer(null, mc.getArr());
    }
}
