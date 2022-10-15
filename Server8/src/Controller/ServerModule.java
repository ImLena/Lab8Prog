package Controller;

import Collections.CommandsManager;
import Collections.TicketMap;
import DataBase.Base;
import DataBase.RegistBase;
import DataBase.TicketsDB;
import Other.Answer;
import Other.ReadCommand;

import java.io.*;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class ServerModule {

    private static Logger log = Logger.getLogger(ServerModule.class.getName());
    private static String url = "jdbc:postgresql://localhost:5432/postgres";
    private static String user = "";
    private static String password = "";

    private static ExecutorService reading = Executors.newCachedThreadPool();
    private static ExecutorService handling = Executors.newCachedThreadPool();
    private static ForkJoinPool sending = new ForkJoinPool();

    public static void server(ServerHandler serverHandler) throws IOException, SQLException {
        CommandsManager commandsManager = null;
        try (InputStream input = new FileInputStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            try {
                Base dataBase = new Base(prop.getProperty("url"), prop.getProperty("user"), prop.getProperty("password"));
                RegistBase users = dataBase.getUsersDB();
                TicketsDB ticketsDB = dataBase.getTicketsDB();
                TicketMap tm;
                tm = new TicketMap(ticketsDB.loadTicketsDB());
                commandsManager = new CommandsManager(tm);
            } catch (SQLException e) {
                System.out.println("Wrong data connection!");
                ServerModule.server(serverHandler);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Can not read .properties file");
            e.printStackTrace();
            System.exit(1);
        }

        LinkedHashMap<SelectionKey, Future<ReadCommand>> threadCom = new LinkedHashMap<>();
        LinkedHashMap<SelectionKey, Future<Answer>> threadAnsw = new LinkedHashMap<>();

        while (true) {
            serverHandler.getSelector().select(700);
            Iterator iter = serverHandler.getSelector().selectedKeys().iterator();

            if (System.in.available() > 0) {
                BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
                String s = r.readLine();
                readCmd(s);
            }

            while (iter.hasNext()) {
                SelectionKey key = (SelectionKey) iter.next();
                iter.remove();
                if (!key.isValid()) {
                    continue;
                }
                if (key.isAcceptable()) {
                    serverHandler.accept();
                }
                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    threadCom.put(key, reading.submit(new ReadClient(channel)));
                    key.interestOps(SelectionKey.OP_WRITE);
                }
                if (key.isWritable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    try {
                        if (threadCom.containsKey(key) && threadCom.get(key).isDone()) {
                            threadAnsw.put(key, handling.submit(new ExecuteCommand(commandsManager, threadCom.get(key).get())));
                            threadCom.remove(key);
                            log.info("Command executed");
                        }
                        if (threadAnsw.containsKey(key) && threadAnsw.get(key).isDone()) {
                            sending.execute(new AnswerClient(channel, threadAnsw.get(key).get()));
                            threadAnsw.remove(key);
                            key.interestOps(SelectionKey.OP_READ);
                        }
                    } catch (NullPointerException e) {
                        log.info("Client disconnected");
                        key.cancel();
                    } catch (InterruptedException | ExecutionException | CancelledKeyException e) {
                        e.printStackTrace();
                        log.warning("\nDisconnection.");
                        key.cancel();
                    }
                }
            }
        }
    }

    private static void chooseDB() {
        System.out.println("Do you want to connect to jdbc:postgresql://pg:5432/studs with user s261747? Yes/No");
        String s = new Scanner(System.in).nextLine();
        switch (s) {
            case "Yes":
                break;
            case "No":
                insertDB();
                break;
            default:
                System.out.println("Not correct answer");
                chooseDB();
                break;
        }

    }

    private static void insertDB() {
        System.out.println("Enter url");
        url = new Scanner(System.in).nextLine();

        System.out.println("Enter user");
        user = new Scanner(System.in).nextLine();

        System.out.println("Enter password");
        password = new Scanner(System.in).nextLine();
    }

    private static void readCmd(String str) {
        if (str.equals("save")) {
            System.out.println("Collection is already saved");
        } else {
            if (str.equals("exit")) {
                System.exit(0);
            } else {
                log.info("Unknown command");
            }
        }
    }
}
