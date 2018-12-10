package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenu {

    @FXML
    private Button play;

    public void playClicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Game.fxml"));
        Stage stage = (Stage) play.getScene().getWindow();
        stage.setScene(new Scene(root,800,600));

    }
}
