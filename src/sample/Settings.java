package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Settings{

    @FXML
    private Button start,backButton,multiplayer;
    @FXML
    private CheckBox normal,doublesize,trio,red,black;
    private int mode;
    private double width,height;
    private Image theme;
    @FXML
    private AnchorPane settingsPane;

    public Settings(){

    }
    @FXML
    public void initialize(){
        normal.setSelected(true);
        red.setSelected(true);
    }
    @FXML
    public void startClicked() throws IOException {
        if(normal.isSelected()) {
            mode = 1;
            width = 800;
            height = 600;
        }
        if(doublesize.isSelected()) {
            mode = 2;
            width = 1000;
            height = 880;
        }
        if(trio.isSelected()) {
            mode = 3;
            width = 800;
            height = 880;
        }
        if(red.isSelected())
            theme = new Image("Images/Cards/backgroundSmall.png");
        if(black.isSelected())
            theme = new Image("Images/Cards/background1Small.png");

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Game.fxml"));
        Loader.load();
        Game game = Loader.getController();
        Parent root = Loader.getRoot();
        Stage stage = (Stage) start.getScene().getWindow();
        Scene scene = new Scene(root,width,height);
        scene.setFill(Color.TRANSPARENT);
        game.setMode(mode,theme);
        stage.setScene(scene);
    }
    public void multiplayerClicked() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Multiplayer.fxml"));
        Stage stage = (Stage) multiplayer.getScene().getWindow();
        stage.setScene(new Scene(root,800,600));
    }

    @FXML
    public void backButtonClicked() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(root,800,600);
        stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
    }

    @FXML
    public void normalModeClicked(){
        normal.setSelected(true);
        doublesize.setSelected(false);
        trio.setSelected(false);
    }
    @FXML
    public void doubleModeClicked(){
        normal.setSelected(false);
        doublesize.setSelected(true);
        trio.setSelected(false);
    }
    @FXML
    public void trioModeClicked(){
        normal.setSelected(false);
        doublesize.setSelected(false);
        trio.setSelected(true);
    }
    @FXML
    public void redClicked(){
        red.setSelected(true);
        black.setSelected(false);
    }
    @FXML
    public void blackClicked(){
        red.setSelected(false);
        black.setSelected(true);
    }
    @FXML
    public void redCheckClicked(){
        red.setSelected(true);
        black.setSelected(false);
    }
    @FXML
    public void blackCheckClicked(){
        red.setSelected(false);
        black.setSelected(true);
    }
    @FXML
    public void normalClicked(){
        normal.setSelected(true);
        doublesize.setSelected(false);
        trio.setSelected(false);
    }
    @FXML
    public void doubleClicked(){
        normal.setSelected(false);
        doublesize.setSelected(true);
        trio.setSelected(false);
    }
    @FXML
    public void trioClicked(){
        normal.setSelected(false);
        doublesize.setSelected(false);
        trio.setSelected(true);
    }

}
