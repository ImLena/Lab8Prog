import Other.Client;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
      //  Parameters parameters = getParameters();
        //List<String> args = parameters.getRaw();
        Parent root = FXMLLoader.load(getClass().getResource("/MainWindow/main.fxml"));
        stage.setTitle("Console App Tickets");
        stage.setScene(new Scene(root, 1326, 700));
        stage.setResizable(false);

 /*       try {
            System.out.println("Enter port:");
            int port = Integer.valueOf(new Scanner(System.in).nextLine());
            try {
                Client.loginClient(port);
            } catch (IllegalArgumentException e) {
                System.out.println("wrong port");
                start(stage);
            }

        } catch (NoSuchElementException e) {
            System.out.println("What a shame! Are you trying to break my code? Enter correct commands next time, please!\nDisappointed client disconnecting, start client again!");
            System.exit(0);
        }*/
        /*Button button1 = new Button("Log in");

        // (B1) Anchor to the Top + Left + Right
        AnchorPane.setTopAnchor(button1, 100.0);
        AnchorPane.setLeftAnchor(button1, 100.0);
        AnchorPane.setRightAnchor(button1, 550.0);*/

        stage.show();
    }
}
