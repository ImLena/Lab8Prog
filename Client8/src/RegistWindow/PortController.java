package RegistWindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PortController {
    String commandArg = null;
    @FXML
    private TextField inputArg;
    @FXML
    private Label port;

    @FXML
    private void OKClick(ActionEvent actionEvent) {
        commandArg = inputArg.getText();
        Stage thisStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        thisStage.close();
    }

    @FXML
    private void CancelClick(ActionEvent actionEvent) {
        Stage thisStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        thisStage.close();
    }

    public Label getPort() {
        return port;
    }

    public String getCommandArg() {
        return commandArg;
    }
}
