package sample;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MainMenu {

    @FXML
    private Button play;

    public void initialize(){

    }

    public void playClicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Game.fxml"));
        Stage stage = (Stage) play.getScene().getWindow();
        stage.setScene(new Scene(root,800,600));

    }

    public void settingsClicked(){

    }

    public void creditsClicked(){

    }

    public void exitClicked(){
        Platform.exit();
    }
}
