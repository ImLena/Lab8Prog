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

public class CommandReceiver {
    private final CommandInvoker commandInvoker;
    private Enter e = new Enter();
    private SocketChannel channel;
    int delay = 1500;
    private String login;
    private String pas;
    String answer;
    MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    public CommandReceiver(CommandInvoker commandInvoker, SocketChannel socket, String login, String pas) {
        this.commandInvoker = commandInvoker;
        this.channel = socket;
        this.login = login;
        this.pas = pas;
    }

    public void help() {
        mainController.setAnswer("helpStr");
    }

    public void history() {
        mainController.setNubmerAnswer(CommandInvoker.getHistory().toString());
    }


    public void clear() throws IOException, ClassNotFoundException, InterruptedException {
        ReadCommand rc = new ReadCommand("clear", null, null, login, pas);
        Client.writeCommand(rc);
        Thread.sleep(delay);
        mainController.setAnswer(Client.getMessage().getAnswer());

    }

    public void info() throws IOException, ClassNotFoundException, InterruptedException {
        ReadCommand rc = new ReadCommand("info", null, null, login, pas);
        Client.writeCommand(rc);
        Thread.sleep(delay);
        mainController.setNubmerAnswer(Client.getMessage().getAnswer());
    }

    public void insert(String[] command, Ticket tic, Scanner in) throws IOException, InterruptedException, ClassNotFoundException {
        if (tic == null) {
            tic = e.enter(command, in);
        }
        try {
            if (command.length > 1) {
                ReadCommand rc = new ReadCommand("insert", tic.getId().toString(), tic, login, pas);
                Client.writeCommand(rc);
                Thread.sleep(delay);
                mainController.setAnswer(Client.getMessage().getAnswer());
            } else {
                mainController.setAnswer("EnterArg'");
            }
        } catch (InputMismatchException | NumberFormatException | ClassNotFoundException | InterruptedException e) {
            mainController.setAnswer("Wrongtype");
        }
    }

    public void min_by_creation_date() throws IOException, InterruptedException, ClassNotFoundException {
        ReadCommand rc = new ReadCommand("min_by_creation_date", null, null, login, pas);
        Client.writeCommand(rc);
        Thread.sleep(delay);
        mainController.setNubmerAnswer(Client.getMessage().getAnswer());
    }

    public void print_descending() throws IOException, InterruptedException, ClassNotFoundException {
        ReadCommand rc = new ReadCommand("print_descending", null, null, login, pas);
        Client.writeCommand(rc);
        Thread.sleep(delay);
        mainController.setAnswer(Client.getMessage().getAnswer());
    }

    public void remove(String[] args) throws IOException {
        try {
            if (args.length > 1) {
                Long id = Long.valueOf(args[1]);
                ReadCommand rc = new ReadCommand("remove_key", String.valueOf(id), null, login, pas);
                Client.writeCommand(rc);
                Thread.sleep(delay);
                mainController.setAnswer(Client.getMessage().getAnswer());
            } else {
                mainController.setAnswer("EnterArg");
            }
        } catch (InputMismatchException | NumberFormatException | ClassNotFoundException | InterruptedException e) {
            mainController.setAnswer("Wrongtype");
        }
    }

    public void remove_greater(String[] command, Ticket tic, Scanner in) throws IOException, InterruptedException, ClassNotFoundException {
        if (tic == null) {
            tic = e.enter(command, in);
        }
        ReadCommand rc = new ReadCommand("remove_greater", null, tic, login, pas);
        Client.writeCommand(rc);
        Thread.sleep(1500);
        // setAnswer(Client.getMessage().getAnswer());
        mainController.setAnswer(Client.getMessage().getAnswer());
    }

    public void replace_if_greater(String[] command, Ticket tic, Scanner in) throws IOException, ClassNotFoundException, InterruptedException {
        if (command.length > 1) {
            if (tic == null) {
                tic = e.enter(command, in);
            }
            ReadCommand rc = new ReadCommand("replace_if_greater", command[1], tic, login, pas);
            Client.writeCommand(rc);
            Thread.sleep(delay);
            mainController.setAnswer(Client.getMessage().getAnswer());
        } else {
            mainController.setAnswer("EnterArg");
        }
    }

    public void update(String[] command, Ticket tic, Scanner in) throws IOException, InterruptedException, ClassNotFoundException {
        if (command.length > 1) {
            ReadCommand rc = new ReadCommand("update", command[1], tic, login, pas);
            Client.writeCommand(rc);
            Thread.sleep(1500);
            mainController.setAnswer(Client.getMessage().getAnswer());
        } else {
            mainController.setAnswer("EnterArg");
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
                ReadCommand rc = new ReadCommand("count_greater_than_price", com[1], null, login, pas);
                Client.writeCommand(rc);
                Thread.sleep(delay);
                mainController.setNubmerAnswer(Client.getMessage().getAnswer());
            } else {
                mainController.setAnswer("EnterArg");
            }
        } catch (InvalidFieldException e) {
            mainController.setNubmerAnswer(e.getMessage());
        } catch (InputMismatchException | NumberFormatException e) {
            mainController.setAnswer("Wrongtype");
        } catch (ClassNotFoundException | InterruptedException ex) {
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
                            commandInvoker.execute(com, in2, null);
                        }

                    }
                    q.pollLast();

                }

            } else {
                mainController.setNubmerAnswer("You can't read file");
            }

        } catch (FileNotFoundException e) {
            mainController.setNubmerAnswer("This file doesn't exist");
        } catch (NullPointerException e) {
            mainController.setNubmerAnswer("Unknown command in the script");
        } catch (ExecuteScriptException e) {
            mainController.setNubmerAnswer("Recursion in the script!");
        } catch (InvalidFieldException e) {
            mainController.setNubmerAnswer("Invalid field entered in the script ");
        } catch (StackOverflowError e) {
            mainController.setNubmerAnswer("Recursion happened");
        } catch (Exception e) {
            mainController.setNubmerAnswer("Unknown error, aliens have taken over the program");
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
