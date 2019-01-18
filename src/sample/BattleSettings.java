package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Η κλάση των Battle Settings
 */
public class BattleSettings {


    @FXML
    private Button back,battle;
    @FXML
    private MenuItem goldfish,kangaroo,elephant;
    @FXML
    private MenuButton rivalSelector;
    private Image theme = new Image("Images/Cards/backgroundSmall.png");

    private GameMode gameMode;

    private Properties properties = new Properties();
    @FXML
    private Label rivalLabel;

    private String gold,kang,ele;
    private MediaPlayer mediaPlayer;

    /**
     * Ο κατασκευαστής της κλάσης
     */
    public BattleSettings(){
        gameMode = new GameMode();
        Media buttonSound = new Media(new File("src/Sounds/buttonSound.wav").toURI().toString());
        mediaPlayer = new MediaPlayer(buttonSound);
    }

    /**
     * Φορτώνει τις τιμές των αρχείων και θέτει ανάλογα τις τιμές.
     * @throws IOException εαν αποτύχει να φορτώσει το αρχείο
     */
    @FXML
    private void initialize() throws IOException{
       gameMode.setRival1("Goldfish");

        File f = new File("config.properties");

        if(f.exists()) {
            InputStream input = new FileInputStream("config.properties");
            properties.load(input);

            String lang = properties.getProperty("flag");
            loadLang(lang);

            if(lang.equals("el")){
                gold = "Χρυσόψαρο";
                kang = "Καγκουρό";
                ele = "Ελέφαντας";
            }
            else if(lang.equals("en")){
                gold = "Goldfish";
                kang = "Kangaroo";
                ele = "Elephant";
            }
        }
    }

    /**
     * Επιλογή του Goldfish
     */
    @FXML
    private void goldfishSelected(){
        rivalSelector.setText(gold);
        gameMode.setRival1("Goldfish");
    }
    /**
     * Επιλογή του Kangaroo
     */
    @FXML
    private void kangarooSelected(){
        rivalSelector.setText(kang);
        gameMode.setRival1("Kangaroo");
    }

    /**
     * Επιλογή του Elephant
     */
    @FXML
    private void elephantSelected(){
        rivalSelector.setText(ele);
        gameMode.setRival1("Elephant");
    }

    /**
     * Ο Event Handler που σε πηγαίνει στην προηγούμενη σκηνή
     * @throws IOException εαν αποτύχει να φορτώσει το FXML.
     */
    @FXML
    private void backClicked() throws IOException {
        mediaPlayer.seek(Duration.ZERO);
        mediaPlayer.setVolume(0.3f);
        mediaPlayer.play();
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Stage stage = (Stage) back.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    /**
     * Ο Event Handler που φορτώνει την μονομαχία
     * @throws IOException εάν αποτύχει να φορτώσει το αρχείο FXML
     */
    @FXML
    private void battleClicked() throws IOException{
        mediaPlayer.seek(Duration.ZERO);
        mediaPlayer.setVolume(0.3f);
        mediaPlayer.play();
        gameMode.setGlobalMode("Battle");

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Battle.fxml"));
        Loader.load();
        Battle b = Loader.getController();
        Stage stage = (Stage) battle.getScene().getWindow();
        b.setMode(gameMode,theme);
        b.battleStart();
        stage.getScene().setRoot(Loader.getRoot());
    }

    /**
     * Φορτώνει την γλώσσα
     * @param lang {@code String}
     */
    private void loadLang(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("sample.lang", locale);

        battle.setText(bundle.getString("battle"));
        rivalSelector.setText(bundle.getString("rivalSelector"));
        rivalLabel.setText(bundle.getString("rivals"));
        goldfish.setText(bundle.getString("goldfish"));
        kangaroo.setText(bundle.getString("kangaroo"));
        elephant.setText(bundle.getString("elephant"));


    }
}
