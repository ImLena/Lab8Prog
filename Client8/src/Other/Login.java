package Other;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class Login {
    public static ReadCommand rc;

    public static String registerUser(SocketChannel channel, String[] command) throws InterruptedException, IOException, ClassNotFoundException {
        String s = null;
        rc = new ReadCommand(command[0], null, null, command[1], command[2]);
        Client.writeCommand(rc);
        Thread.sleep(1500);
        Answer a = Client.getMessage();
        String answer = a.getAnswer();
        String[] answ = answer.split(" ");
        if (answ.length == 2) {
            if (answ[0].equals("user")) {
                if (answ[1].equals("logged") | answ[1].equals("registered")) {
                    Client.setLogin(command[1]);
                    s = answer;
                }
            } else {
                s = "Error on server";
            }
        } else {
            s = "Error on server";
        }
        return s;
    }
}
