package Actions;

import Collections.MapCommands;
import Other.ReadCommand;
import Other.Answer;

import java.io.IOException;
import java.sql.SQLException;

public class getColl extends Command {
    @Override
    public Answer execute(ReadCommand com, MapCommands mc) throws IOException, SQLException {
        return new Answer(null, mc.getArr());
    }
}
