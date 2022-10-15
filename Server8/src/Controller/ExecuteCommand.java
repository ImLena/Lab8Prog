package Controller;

import Actions.CommandInvoker;
import Collections.CommandsManager;
import Other.Answer;
import Other.ReadCommand;

import java.util.concurrent.Callable;

public class ExecuteCommand implements Callable<Answer> {
    private CommandsManager mc;
    private ReadCommand com;

    public ExecuteCommand(CommandsManager mc, ReadCommand com) {
        this.mc = mc;
        this.com = com;
    }

    @Override
    public Answer call() throws Exception {
        return CommandInvoker.getInstance().executeCom(mc, com);
    }
}

