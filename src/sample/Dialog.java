package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Dialog  {

    @FXML
    private Button yes,no;

    public void yesClicked(){
        Platform.exit();
    }

    public void noClicked() throws IOException {
        Stage dialog = (Stage) no.getScene().getWindow();

        Parent root = dialog.getOwner().getScene().getRoot();
        root.setEffect(null);

        dialog.close();
    }

}
