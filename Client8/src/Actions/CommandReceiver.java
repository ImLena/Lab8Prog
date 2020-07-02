package Actions;
import Collections.Ticket;
import Exceptions.ExecuteScriptException;
import Exceptions.InvalidFieldException;
import MainWindow.MainController;
import Other.Client;
import Other.ReadCommand;

import java.io.*;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class CommandReceiver{
    private final CommandInvoker commandInvoker;
   // private Enter e = new Enter();
    private SocketChannel channel;
    int delay = 1500;
    private String login;
    private String pas;
    String answer;


    public CommandReceiver(CommandInvoker commandInvoker, SocketChannel socket, String login, String pas) {
        this.commandInvoker = commandInvoker;
        this.channel = socket;
        this.login = login;
        this.pas = pas;
    }

    public void help() {
        StringBuilder sb = new StringBuilder();
        commandInvoker.getCommands().forEach((name, command) -> sb.append(command.help()+"\n"));
        setAnswer(sb.toString());
    }

    public void history(){
        setAnswer(CommandInvoker.getHistory().toString());
    }


    public void clear() throws IOException, ClassNotFoundException, InterruptedException {
        ReadCommand rc = new ReadCommand("clear", null,null, login, pas);
        Client.writeCommand(rc);
        Thread.sleep(delay);
        setAnswer(Client.getMessage().getAnswer());

    }

    public void info() throws IOException, ClassNotFoundException, InterruptedException {
        ReadCommand rc = new ReadCommand("info", null,null, login, pas);
        Client.writeCommand(rc);
        Thread.sleep(delay);
        setAnswer(Client.getMessage().getAnswer());
    }

    public void insert(String[] command, Ticket tic) throws IOException, InterruptedException, ClassNotFoundException {
       // Ticket tic = e.enter(command, in);
        try {
            if (command.length > 1) {
                ReadCommand rc = new ReadCommand("insert", tic.getId().toString(), tic, login, pas);
                Client.writeCommand(rc);
                Thread.sleep(delay);
                setAnswer(Client.getMessage().getAnswer());
            } else {
                setAnswer("EnterArg'");
            }
        }catch (InputMismatchException | NumberFormatException | ClassNotFoundException | InterruptedException e){
        setAnswer("Wrongtype");
    }
    }

    public void min_by_creation_date() throws IOException, InterruptedException, ClassNotFoundException {
        ReadCommand rc = new ReadCommand("min_by_creation_date", null,null,login, pas);
        Client.writeCommand(rc);
        Thread.sleep(delay);
        setAnswer(Client.getMessage().getAnswer());
    }

    public void print_descending() throws IOException, InterruptedException, ClassNotFoundException {
        ReadCommand rc = new ReadCommand("print_descending", null,null, login, pas);
        Client.writeCommand(rc);
        Thread.sleep(delay);
        setAnswer(Client.getMessage().getAnswer());
    }

    public void remove(String[] args) throws IOException {
        try{
            if (args.length > 1) {
                Long id = Long.valueOf(args[1]);
                ReadCommand rc = new ReadCommand("remove_key", String.valueOf(id), null, login, pas);
                Client.writeCommand(rc);
                Thread.sleep(delay);
                setAnswer(Client.getMessage().getAnswer());
            } else{
                setAnswer("EnterArg");
            }
        }catch (InputMismatchException | NumberFormatException | ClassNotFoundException | InterruptedException e){
            setAnswer("Wrongtype");
        }
    }

    public void remove_greater(String[] command, Ticket tic, Scanner in) throws IOException, InterruptedException, ClassNotFoundException {
     //   Ticket tic = e.enter(command, in);
        ReadCommand rc = new ReadCommand("remove_greater", null, tic, login, pas);
        Client.writeCommand(rc);
        Thread.sleep(1500);
        setAnswer(Client.getMessage().getAnswer());
    }

    public void replace_if_greater(String[] command, Ticket tic, Scanner in) throws IOException, ClassNotFoundException, InterruptedException {
        if (command.length > 1) {
           // Ticket tic = e.enter(command, in);
            ReadCommand rc = new ReadCommand("replace_if_greater", command[1], tic, login, pas);
            Client.writeCommand(rc);
            Thread.sleep(delay);
            setAnswer(Client.getMessage().getAnswer());
        }else{
            setAnswer("EnterArg");
        }
    }

    public void update(String[] command, Ticket tic, Scanner in) throws IOException, InterruptedException, ClassNotFoundException {
        if (command.length>1){
       // Ticket tic = e.enter(command, in);
        ReadCommand rc = new ReadCommand("update", command[1], tic, login, pas);
        Client.writeCommand(rc);
        Thread.sleep(1500);
        setAnswer(Client.getMessage().getAnswer());
    }else {
        setAnswer("EnterArg");
    }
    }

    public ArrayList<Ticket> show() throws IOException, ClassNotFoundException, InterruptedException {
        ReadCommand rc = new ReadCommand("show", null, null, login, pas);
        Client.writeCommand(rc);
        Thread.sleep(delay);
        return Client.getMessage().getTickets();
    }

    public void count_greater_than_price(String[] com) throws IOException {
        try {
            if (com.length > 1) {
                float price = Float.parseFloat(com[1]);
                ReadCommand rc = new ReadCommand("count_greater_than_price", com[1], null, login, pas);
                Client.writeCommand(rc);
                Thread.sleep(delay);
                setAnswer(Client.getMessage().getAnswer());
            } else {
                setAnswer("EnterArg");
            }
        }catch (InvalidFieldException e) {
            setAnswer(e.getMessage());
        } catch (InputMismatchException e) {
            setAnswer("Wrongtype");
        } catch (NumberFormatException e) {
            setAnswer("Wrongtype");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void execute_script(String path) {
        Path p;
        Reader r;
        Scanner in;
        Scanner in2;
        LinkedList<Scanner> q = new LinkedList<>();
        String file = path;
        String line = "";
        String[] com;
        p = Paths.get(file);
        try {
            if (Files.isReadable(p)) {
                FileInputStream fstream = new FileInputStream(file);
                r = new BufferedReader(new InputStreamReader(fstream));
                in = new Scanner(r);
                q.addLast(in);

                while (!q.isEmpty()) {
                    in2 = q.getLast();
                    while ((in2.hasNextLine()) & (!line.equals("exit"))) {
                        if (Paths.get(path).equals(p)) {
                            line = in2.nextLine();
                            com = line.split(" ");
                            System.out.println(Arrays.toString(com));
                            commandInvoker.execute(com, in2);
                        }

                    }
                    q.pollLast();

                }

            } else {
                setAnswer("You can't read file");
            }

        } catch (FileNotFoundException e) {
            setAnswer("This file doesn't exist");
        } catch (NullPointerException e) {
            setAnswer("Unknown command in the script");
        } catch (ExecuteScriptException e) {
            setAnswer("Recursion in the script!");
        } catch (InvalidFieldException e) {
            setAnswer("Invalid field entered in the script ");
        } catch (StackOverflowError e) {
            setAnswer("Recursion happened");
        } catch (Exception e) {
            setAnswer("Unknown error, aliens have taken over the program");
        }
    }

    public void exit() throws IOException {
        setAnswer("Channel is closed. good bye!");
        Client.closeChannel();
        System.exit(0);
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }
}
