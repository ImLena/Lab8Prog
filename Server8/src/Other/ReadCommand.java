package Other;

import Collections.Ticket;

import java.io.Serializable;

/**
 * Класс, служащий для передачи команд с клиента на сервер
 */
public class ReadCommand implements Serializable {
    private String comm;
    private String strArgs;
    private Ticket tic;
    private String login;
    private String pass;
    private static final long serialVersionUID = 32L;

    public ReadCommand(String comm, String strArgs, Ticket tic, String login, String pass) {
        this.comm=comm;
        this.strArgs=strArgs;
        this.tic = tic;
        this.login = login;
        this.pass = pass;
    }

    public String getComm() {
        return comm;
    }

    public Ticket getTicket() {
        return tic;
    }

    public String getStrArgs() {
        return strArgs;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }
}
