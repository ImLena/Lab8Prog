package Actions;

import Collections.CommandsManager;
import Other.Answer;
import Other.ReadCommand;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;

public abstract class Command implements Serializable {

    private static final long serialVersionUID = 32L;
    public abstract Answer execute(ReadCommand com, CommandsManager mc) throws IOException, SQLException;

}
