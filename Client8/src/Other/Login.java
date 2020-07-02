package Other;

import RegistWindow.RegWindow;

import java.io.IOException;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Scanner;

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
            }else{
                s = "Error on server";
            }
        } else{
            s = "Error on server";
        }
        return s;
    }

 /*   public static String registration(){
        Scanner scanner = new Scanner(System.in);
        String s = null;
        System.out.println("Do you want to login or to register? Please, choose one:");
        switch (scanner.nextLine()){
            case "login":
                s = "login";
                break;
            case "register":
                s = "regist";
                break;
            default:
                registration();
        }
        return s;
    }

    public static String[] insertlogPas(SocketChannel channel) throws InterruptedException, IOException, ClassNotFoundException {

        Scanner in = new Scanner(System.in);
        String[] logPas = new String[2];
        System.out.println("Enter your login");
        logPas[0] = in.next();
        checkExit(logPas[0]);
        checkNull(logPas[0], channel);
        System.out.println("Enter your password");
        logPas[1] = in.next();
        checkExit(logPas[1]);
        checkNull(logPas[1], channel);
        return logPas;
    }

    public static void checkExit(String s){
        if (s.equals("exit")){
            System.out.println("Good bye!");
            System.exit(0);
        }
    }

    public static void checkNull(String s, SocketChannel channel) throws InterruptedException, IOException, ClassNotFoundException {
        if (s.equals("")){
            System.out.println("Can't be empty");
            registerUser(channel);
        }
    }

    public static String getLogin() {
        return logPas[0];
    }

    public static String getPas() {
        return logPas[1];
    }*/
}
