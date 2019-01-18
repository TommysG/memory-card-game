package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;


/**
 * Η κλάση ρυθμίσεων για το Single Mode
 */
public class SingleModeSettings {

    @FXML
    private Button start,backButton;
    @FXML
    private CheckBox normal,doublesize,trio,red,black;
    private GameMode mode;
    private Image theme;
    @FXML
    private ImageView redImage,blackImage,normalMode,doubleMode,trioMode;

    private Properties properties = new Properties();
    @FXML
    private Label gamemodeLabel,themesLabel;
    private MediaPlayer mediaPlayer;

    private Glow glow = new Glow(0.6);

    /**
     * Κατασκευαστής της κλάσης
     */
    public SingleModeSettings(){
        mode = new GameMode();
        Media buttonSound = new Media(new File("src/Sounds/buttonSound.wav").toURI().toString());
        mediaPlayer = new MediaPlayer(buttonSound);
    }

    /**
     * Φορτώνει τις τιμές απο τα αρχείο και θέτει τις τιμές.
     * @throws IOException εαν αποτύχει να φορτώσει κάποιο αρχείο.
     */
    @FXML
    private void initialize() throws IOException{
        File f = new File("config.properties");

        if(f.exists()) {
            InputStream input = new FileInputStream("config.properties");
            properties.load(input);

            String lang = properties.getProperty("flag");
            loadLang(lang);

            double width = Double.parseDouble(properties.getProperty("width"));
            double height = Double.parseDouble(properties.getProperty("height"));
            boolean fullScreen = Boolean.parseBoolean(properties.getProperty("fullScreen"));

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

    /**
     * Ο Event Handler που αρχίζει το παιχνίδι.
     * @throws IOException εαν αποτύχει να φορτώσει το αρχείο FXML.
     */
    @FXML
    public void startClicked() throws IOException {
        mediaPlayer.seek(Duration.ZERO);
        mediaPlayer.setVolume(0.3f);
        mediaPlayer.play();
        mode.setGlobalMode("SingleMode");
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
        Loader.setLocation(getClass().getResource("Game.fxml"));
        Loader.load();
        Stage stage = (Stage) start.getScene().getWindow();
        Game game = Loader.getController();
        game.setMode(mode,theme);
        game.gameStart();
        stage.getScene().setRoot(Loader.getRoot());


    }

    /**
     * Ο Event Handler που σε πηγαίνει στην προηγούμενη σκηνή
     * @throws IOException εαν αποτύχει να φορτώσει το αρχείο FXML.
     */
    @FXML
    private void backButtonClicked() throws IOException{
        mediaPlayer.seek(Duration.ZERO);
        mediaPlayer.setVolume(0.3f);
        mediaPlayer.play();
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    /**
     * Ο Event Handler αν επιλέξεις το κανονικό
     */
    @FXML
    private void normalModeClicked(){
        normalMode.setEffect(glow);
        doubleMode.setEffect(null);
        trioMode.setEffect(null);
        normal.setSelected(true);
        doublesize.setSelected(false);
        trio.setSelected(false);
    }
    /**
     * Ο Event Handler αν επιλέξεις το διπλό
     */
    @FXML
    private void doubleModeClicked(){
        normalMode.setEffect(null);
        trioMode.setEffect(null);
        doubleMode.setEffect(glow);
        normal.setSelected(false);
        doublesize.setSelected(true);
        trio.setSelected(false);
    }
    /**
     * Ο Event Handler αν επιλέξεις το τριπλό
     */
    @FXML
    private void trioModeClicked(){
        trioMode.setEffect(glow);
        normalMode.setEffect(null);
        doubleMode.setEffect(null);
        normal.setSelected(false);
        doublesize.setSelected(false);
        trio.setSelected(true);
    }
    /**
     * Ο Event Handler αν επιλέξεις το κόκκινο θέμα
     */
    @FXML
    private void redClicked(){
        redImage.setEffect(glow);
        blackImage.setEffect(null);
        red.setSelected(true);
        black.setSelected(false);
    }
    /**
     * Ο Event Handler αν επιλέξεις το μαύρο θέμα
     */
    @FXML
    private void blackClicked(){
        redImage.setEffect(null);
        blackImage.setEffect(glow);
        red.setSelected(false);
        black.setSelected(true);
    }
    /**
     * Ο Event Handler αν επιλέξεις το κόκκινο checkbox
     */
    @FXML
    private void redCheckClicked(){
        red.setSelected(true);
        black.setSelected(false);
    }
    /**
     * Ο Event Handler αν επιλέξεις το μαύρο checkbox
     */
    @FXML
    private void blackCheckClicked(){
        red.setSelected(false);
        black.setSelected(true);
    }
    /**
     * Ο Event Handler αν επιλέξεις το κανονικό checkbox
     */
    @FXML
    private void normalClicked(){
        normal.setSelected(true);
        doublesize.setSelected(false);
        trio.setSelected(false);
    }
    /**
     * Ο Event Handler αν επιλέξεις το διπλό checkbox
     */
    @FXML
    private void doubleClicked(){
        normal.setSelected(false);
        doublesize.setSelected(true);
        trio.setSelected(false);
    }
    /**
     * Ο Event Handler αν επιλέξεις το τριπλό checkbox
     */
    @FXML
    private void trioClicked(){
        normal.setSelected(false);
        doublesize.setSelected(false);
        trio.setSelected(true);
    }

    /**
     * Φορτώνει την γλώσσα του FXML.
     */
    private void loadLang(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("sample.lang", locale);

        gamemodeLabel.setText(bundle.getString("gameMode"));
        themesLabel.setText(bundle.getString("themes"));
        normal.setText(bundle.getString("normal"));
        doublesize.setText(bundle.getString("doubleSize"));
        trio.setText(bundle.getString("trio"));
        red.setText(bundle.getString("red"));
        black.setText(bundle.getString("black"));
        start.setText(bundle.getString("singleMode"));


    }

}
