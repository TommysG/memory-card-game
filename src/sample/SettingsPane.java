package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * <Η1>Η κλάση των ρυθμίσεων.</Η1>
 */

public class SettingsPane {


    @FXML
    private MenuButton language,resolution;
    @FXML
    private MenuItem fullScreen,x86,x1280;
    @FXML
    private ToggleButton sounds;
    @FXML
    private Button clearProgress;
    @FXML
    private Label settingsLabel,resolutionLabel,soundsLabel,clearProgressLabel;


    private Properties properties = new Properties();
    private Properties properties2 = new Properties();
    private OutputStream output = null;
    private InputStream input = null;

    @FXML
    private Button close;

    private String fs;

    /**
     * Κάνει initialize.
     * @throws IOException Εάν αποτύχει να φορτώσει το αρχείο.
     */
    public void initialize() throws IOException {
        File f = new File("config.properties");

        properties.setProperty("width","800");
        properties.setProperty("height","600");
        properties.setProperty("fullScreen","false");

        if(f.exists()) {
            input = new FileInputStream("config.properties");
            properties.load(input);

            String lang = properties.getProperty("flag");
            loadLang(lang);

            resolution.setText(properties.getProperty("resolution"));
            if(properties.getProperty("resolution").equals("FullScreen")){
                resolution.setText(fs);
            }

        }


    }

    /**
     * Αλλάζει την ανάλυση σε πλήρης οθόνη.
     * @throws IOException Εάν αποτύχει να φορτώσει το αρχείο FXML.
     */
    public void fullScreenSelected() throws IOException{
        resolution.setText(fs);
        output = new FileOutputStream("config.properties");
        properties.setProperty("fullScreen","true");
        properties.setProperty("width","999");
        properties.setProperty("height","999");
        properties.setProperty("resolution","FullScreen");
        properties.store(output,null);
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Stage settingsPane = (Stage) resolution.getScene().getWindow();

        Stage stage = (Stage) settingsPane.getOwner().getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        double centerXPosition = stage.getX() + stage.getWidth()/2d;
        double centerYPosition = stage.getY() + stage.getHeight()/2d;
        settingsPane.setWidth(stage.getWidth()/2);
        settingsPane.setHeight(stage.getHeight()/2);
        settingsPane.setX(centerXPosition - settingsPane.getWidth()/2d);
        settingsPane.setY(centerYPosition - settingsPane.getHeight()/2d);
        settingsPane.show();

    }

    /**
     * Αλλάζει την ανάλυση σε "1280x720".
     * @throws IOException Εάν αποτύχει να φορτώσει το αρχείο FXML.
     */
    public void x1024Selected() throws IOException{
        resolution.setText("1280x720");
        output = new FileOutputStream("config.properties");
        properties.setProperty("resolution","1280x720");
        properties.setProperty("fullScreen","false");
        properties.setProperty("width","1280");
        properties.setProperty("height","720");
        properties.store(output,null);

        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Stage settingsPane = (Stage) resolution.getScene().getWindow();

        Stage stage = (Stage) settingsPane.getOwner().getScene().getWindow();
        stage.setScene(new Scene(root,1280,720));
        GaussianBlur blur = new GaussianBlur(3);
        Parent settings = settingsPane.getOwner().getScene().getRoot();
        settings.setEffect(blur);
        double centerXPosition = stage.getX() + stage.getWidth()/2d;
        double centerYPosition = stage.getY() + stage.getHeight()/2d;
        settingsPane.setWidth(stage.getWidth()/2);
        settingsPane.setHeight(stage.getHeight()/2);
        settingsPane.setX(centerXPosition - settingsPane.getWidth()/2d);
        settingsPane.setY(centerYPosition - settingsPane.getHeight()/2d);
        settingsPane.show();
    }

    /**
     * Αλλάζει την ανάλυση σε "800x600".
     * @throws IOException Εάν αποτύχει να φορτώσει το αρχείο FXML.
     */
    public void x86Selected() throws IOException {
        resolution.setText("800x600");
        output = new FileOutputStream("config.properties");
        properties.setProperty("resolution","800x600");
        properties.setProperty("fullScreen","false");
        properties.setProperty("width","800");
        properties.setProperty("height","600");
        properties.store(output,null);

        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Stage settingsPane = (Stage) resolution.getScene().getWindow();

        Stage stage = (Stage) settingsPane.getOwner().getScene().getWindow();
        stage.setScene(new Scene(root,800,600));
        GaussianBlur blur = new GaussianBlur(3);
        Parent settings = settingsPane.getOwner().getScene().getRoot();
        settings.setEffect(blur);
        double centerXPosition = stage.getX() + stage.getWidth()/2d;
        double centerYPosition = stage.getY() + stage.getHeight()/2d;
        settingsPane.setWidth(stage.getWidth()/2);
        settingsPane.setHeight(stage.getHeight()/2);
        settingsPane.setX(centerXPosition - settingsPane.getWidth()/2d);
        settingsPane.setY(centerYPosition - settingsPane.getHeight()/2d);
        settingsPane.show();
    }

    /**
     * Αλλάζει την ανάλυση σε "600x600".
     * @throws IOException Εάν αποτύχει να φορτώσει το αρχείο FXML.
     */
    public void x64Selected() throws IOException{
        resolution.setText("600x600");
        output = new FileOutputStream("config.properties");
        properties.setProperty("resolution","600x600");
        properties.setProperty("fullScreen","false");
        properties.setProperty("width","600");
        properties.setProperty("height","600");
        properties.store(output,null);

        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Stage settingsPane = (Stage) resolution.getScene().getWindow();

        Stage stage = (Stage) settingsPane.getOwner().getScene().getWindow();
        stage.setScene(new Scene(root,600,600));
        GaussianBlur blur = new GaussianBlur(3);
        Parent settings = settingsPane.getOwner().getScene().getRoot();
        settings.setEffect(blur);
        double centerXPosition = stage.getX() + stage.getWidth()/2d;
        double centerYPosition = stage.getY() + stage.getHeight()/2d;
        settingsPane.setWidth(stage.getWidth()/2);
        settingsPane.setHeight(stage.getHeight()/2);
        settingsPane.setX(centerXPosition - settingsPane.getWidth()/2d);
        settingsPane.setY(centerYPosition - settingsPane.getHeight()/2d);
        settingsPane.show();

    }

    /**
     * Το event handler του κουμιού εξόδου από τις ρυθμίσεις.
     */
    public void closeSettings(){
        Stage settingsPane = (Stage) close.getScene().getWindow();
        Parent mainMenu = settingsPane.getOwner().getScene().getRoot();
        mainMenu.setEffect(null);

        settingsPane.close();
    }

    /**
     * Κάνει πλήρης εκκαθάριση των επιδόσεων του παίχτη.
     * @throws IOException Εάν αποτύχει να φορτώσει το αρχείο.
     */
    public void clearProgressClicked() throws IOException{
        File f2 =new File("score.properties");

        if(f2.exists()){
            input = new FileInputStream("score.properties");
            properties.load(input);

            OutputStream output2 = new FileOutputStream("score.properties");
            properties2.setProperty("MultiplayerWins1","0");
            properties2.setProperty("MultiplayerWins2","0");
            properties2.setProperty("MultiplayerWins3","0");
            properties2.setProperty("SingleModeHighScore1","99999");
            properties2.setProperty("SingleModeHighScore2","99999");
            properties2.setProperty("SingleModeHighScore3","99999");
            properties2.setProperty("BattleWins","0");
            properties2.store(output2,null);
        }

    }

    /**
     * Φορτώνει τη γλώσσα που εμφανίζονται οι επιλογές των ρυθμίσεων.
     * @param lang {@code String}
     */
    private void loadLang(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("sample.lang", locale);

        settingsLabel.setText(bundle.getString("settings"));
        soundsLabel.setText(bundle.getString("sounds"));
        clearProgressLabel.setText(bundle.getString("clearProgressLabel"));
        resolutionLabel.setText(bundle.getString("resolutionLabel"));
        sounds.setText(bundle.getString("sounds"));
        resolution.setText(bundle.getString("resolutionLabel"));
        clearProgress.setText(bundle.getString("clearProgressLabel"));
        x1280.setText(bundle.getString("x1280"));
        fullScreen.setText(bundle.getString("fullScreen"));
        fs = bundle.getString("fullScreen");

    }
}
