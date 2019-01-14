package sample;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;
import java.util.Properties;
import java.util.ResourceBundle;

public class MultiplayerSettings {

    @FXML
    private Button start,backButton,multiplayer;
    @FXML
    private CheckBox normal,doublesize,trio,red,black;
    private GameMode mode;
    private double width,height;
    private Image theme;
    @FXML
    private ImageView redImage,blackImage,normalMode,doubleMode,trioMode;
    @FXML
    private MenuButton player1,player2,player3,number;
    @FXML
    private MenuItem Human1,Goldfish1,Kangaroo1,Elephant1;


    private Properties properties = new Properties();
    private OutputStream output = null;
    private InputStream input = null;

    private boolean fullScreen;

    private ResourceBundle bundle;
    private Glow glow = new Glow(0.6);


    public MultiplayerSettings(){
        mode = new GameMode();
    }
    @FXML
    public void initialize() throws IOException{
        File f = new File("config.properties");

        if(f.exists()) {
            input = new FileInputStream("config.properties");
            properties.load(input);

            width = Double.parseDouble(properties.getProperty("width"));
            height = Double.parseDouble(properties.getProperty("height"));
            fullScreen = Boolean.parseBoolean(properties.getProperty("fullScreen"));

            if(width == 800 || fullScreen || width == 1280){
                redImage.setScaleX(1.5);
                redImage.setScaleY(1.5);
                blackImage.setScaleX(1.5);
                blackImage.setScaleY(1.5);
                normalMode.setScaleX(2);
                normalMode.setScaleY(2);
                doubleMode.setScaleX(2);
                doubleMode.setScaleY(2);
                trioMode.setScaleX(2);
                trioMode.setScaleY(2);
            }
            if(fullScreen){
                redImage.setScaleX(2);
                redImage.setScaleY(2);
                blackImage.setScaleX(2);
                blackImage.setScaleY(2);
            }
        }

        normal.setSelected(true);
        red.setSelected(true);
        normalMode.setEffect(glow);
        redImage.setEffect(glow);
    }

    public void multiplayerClicked() throws IOException{
        mode.setGlobalMode("Multiplayer");

        if(normal.isSelected()) {
            mode.setMode(1);
        }
        if(doublesize.isSelected()) {
            mode.setMode(2);
        }
        if(trio.isSelected()) {
            mode.setMode(3);
        }
        if(red.isSelected())
            theme = new Image("Images/Cards/backgroundSmall.png");
        if(black.isSelected())
            theme = new Image("Images/Cards/background1Small.png");


        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Multiplayer.fxml"));
        Loader.load();
        Multiplayer multi = Loader.getController();
        Stage stage = (Stage) multiplayer.getScene().getWindow();
        multi.setMode(mode,theme);
        multi.multiplayerStart();
        stage.getScene().setRoot(Loader.getRoot());
    }
    public void number1Clicked(){
        mode.setRivalsNumber(1);
        number.setText("1");
        mode.setRival2("");
        mode.setRival3("");
        player1.setDisable(false);
        player2.setDisable(true);
        player3.setDisable(true);
    }

    public void number2Clicked(){
        mode.setRivalsNumber(2);
        number.setText("2");
        mode.setRival3("");
        player1.setDisable(false);
        player2.setDisable(false);
        player3.setDisable(true);
    }

    public void number3Clicked(){
        mode.setRivalsNumber(3);
        number.setText("3");
        player1.setDisable(false);
        player2.setDisable(false);
        player3.setDisable(false);
    }

    @FXML
    public void backButtonClicked() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    @FXML
    public void normalModeClicked(){
        normalMode.setEffect(glow);
        doubleMode.setEffect(null);
        trioMode.setEffect(null);
        normal.setSelected(true);
        doublesize.setSelected(false);
        trio.setSelected(false);
    }
    @FXML
    public void doubleModeClicked(){
        normalMode.setEffect(null);
        trioMode.setEffect(null);
        doubleMode.setEffect(glow);
        normal.setSelected(false);
        doublesize.setSelected(true);
        trio.setSelected(false);
    }
    @FXML
    public void trioModeClicked(){
        trioMode.setEffect(glow);
        normalMode.setEffect(null);
        doubleMode.setEffect(null);
        normal.setSelected(false);
        doublesize.setSelected(false);
        trio.setSelected(true);
    }
    @FXML
    public void redClicked(){
        redImage.setEffect(glow);
        blackImage.setEffect(null);
        red.setSelected(true);
        black.setSelected(false);
    }
    @FXML
    public void blackClicked(){
        redImage.setEffect(null);
        blackImage.setEffect(glow);
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

    public void H1()
    {
        mode.setRival1("Human");
        player1.setText("Human");
    }
    public void E1()
    {
        mode.setRival1("Elephant");
        player1.setText("Elephant");
    }
    public void G1()
    {
        mode.setRival1("Goldfish");
        player1.setText("Goldfish");
    }
    public void K1()
    {
        mode.setRival1("Kangaroo");
        player1.setText("Kangaroo");
    }
    public void h2(){
        mode.setRival2("Human");
        player2.setText("Human");

    }
    public void g2(){
        mode.setRival2("Goldfish");
        player2.setText("Goldfish");

    }
    public void k2(){
        mode.setRival2("Kangaroo");
        player2.setText("Kangaroo");

    }
    public void e2(){
        mode.setRival2("Elephant");
        player2.setText("Elephant");

    }
    public void h3(){
        mode.setRival3("Human");
        player3.setText("Human");

    }
    public void g3(){
        mode.setRival3("Goldfish");
        player3.setText("Goldfish");

    }
    public void k3(){
        mode.setRival3("Kangaroo");
        player3.setText("Kangaroo");
    }
    public void e3(){
        mode.setRival3("Elephant");
        player3.setText("Elephant");
    }

}
