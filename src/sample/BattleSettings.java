package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class BattleSettings {


    @FXML
    private Button back,battle;
    @FXML
    private MenuItem goldfish,kangaroo,elephant;
    @FXML
    private MenuButton rivalSelector;
    private Image theme = new Image("Images/Cards/backgroundSmall.png");

    private GameMode gameMode;

    public BattleSettings(){
        gameMode = new GameMode();
    }

    public void goldfishSelected(){
        rivalSelector.setText("Goldfish");
        gameMode.setRival1("Goldfish");
    }

    public void kangarooSelected(){
        rivalSelector.setText("Kangaroo");
        gameMode.setRival1("Kangaroo");
    }

    public void elephantSelected(){
        rivalSelector.setText("Elephant");
        gameMode.setRival1("Elephant");
    }

    public void backClicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Stage stage = (Stage) back.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    public void battleClicked() throws IOException{
        gameMode.setGlobalMode("Battle");

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Battle.fxml"));
        Loader.load();
        Battle b = Loader.getController();
        Stage stage = (Stage) battle.getScene().getWindow();
        b.setMode(gameMode,theme);
        stage.getScene().setRoot(Loader.getRoot());
    }
}
