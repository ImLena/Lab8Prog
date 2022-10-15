package Other;

import MainWindow.MainController;

import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    private static final String localhost = "127.0.0.1";
    private static SocketChannel channel;
    private static String login;
    private static String password;
    MainController mainController;
    private boolean logged = false;

    public static void setPassword(String password) {
        Client.password = password;
    }

    public static String getPassword() {
        return password;
    }

    public Client(MainController mainController) {
        this.mainController = mainController;
    }

    public static SocketChannel getChannel() {
        return channel;
    }

    public static void setLogin(String login) {
        Client.login = login;
    }

    public static String getLogin() {
        return login;
    }

    public static void loginClient(int serverPort) throws IOException, ClassNotFoundException, InterruptedException {
        try {
            InetSocketAddress ip = new InetSocketAddress(localhost, serverPort);
            channel = SocketChannel.open(ip);
            channel.configureBlocking(false);
        } catch (ConnectException ex) {
            System.out.println("No connection");
        }
    }

    public static Answer getMessage() throws ClassNotFoundException {
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);
            channel.read(byteBuffer);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object o = objectInputStream.readObject();

            return (Answer) o;
        } catch (StreamCorruptedException e) {
            System.exit(0);
            return new Answer("Congrats! You broke server!", null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void writeCommand(ReadCommand com) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(com);
        byte[] data = baos.toByteArray();
        channel.write(ByteBuffer.wrap(data));
    }

    public static void closeChannel() throws IOException {
        channel.close();
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public boolean isLogged() {
        return logged;
    }
}
