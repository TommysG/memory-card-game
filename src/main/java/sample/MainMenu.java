package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.*;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * <h1>Η κλάση του Main Menu</h1>
 */
public class MainMenu {

    @FXML
    private Button exit,highScore;
    @FXML
    private MenuButton language,playMenu;
    @FXML
    private ImageView flag,cards;
    @FXML
    private AnchorPane mainMenu;
    @FXML
    private MenuItem singleMode,multiplayerItem,englishMenu,greekMenu,battle;

    private Properties properties = new Properties();
    private Properties properties2 = new Properties();
    private OutputStream output = null;

    private Image greece = new Image("Images/el.png");
    private Image uk = new Image("Images/en.png");

    private Locale locale;
    private MediaPlayer mediaPlayer;

    /**
     * Ο κατασκευαστής της κλάσης.
     */
    public MainMenu(){
        Media buttonSound = new Media(getClass().getClassLoader().getResource("Sounds/buttonSound.wav").toExternalForm());
        mediaPlayer = new MediaPlayer(buttonSound);
    }

    /**
     * Δημιουργεί τα αρχεία αν δεν υπάρχουν, βάζοντας μέσα τις κατάλληλες τιμές, αν το αρχείο υπάρχει τότε φορτώνει αυτές τις τιμές.
     * @throws IOException εάν αποτύχει να φορτώσει το αρχείο.
     */
    @FXML
    private void initialize() throws IOException{
        File f = new File("config.properties");
        File f2 =new File("score.properties");

        if(f2.exists()){
            InputStream input2 = new FileInputStream("score.properties");
            properties2.load(input2);
        }
        else{
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

        if(f.exists()) {
            InputStream input = new FileInputStream("config.properties");
            properties.load(input);

            String string1 = properties.getProperty("flag");
            String string2 = properties.getProperty("language");
            System.out.println(string1);
            System.out.println(string2);

            language.setText(string2);
            flag.setImage(new Image("Images/" + string1 + ".png"));
            loadLang(string1);

            if(properties.getProperty("sound").equals("enabled")){
                Main.mediaPlayer.play();
            }
            else if(properties.getProperty("sound").equals("disabled")){
                Main.mediaPlayer.pause();
            }

            int width = Integer.parseInt(properties.getProperty("width"));
            if(width == 800){
                cards.setFitWidth(583);
                cards.setFitHeight(173);
            }
            else if(width == 999 ){
                cards.setFitWidth(596);
                cards.setFitHeight(372);
                VBox.setMargin(cards,new Insets(0,0,100,0));
            }
            else if(width == 600){
                cards.setFitWidth(446);
                cards.setFitHeight(149);
            }
            else if(width == 1280){
                cards.setFitWidth(531);
                cards.setFitHeight(205);

            }
        }
        else{
            output = new FileOutputStream("config.properties");
            properties.setProperty("width","1280");
            properties.setProperty("height","720");
            properties.setProperty("resolution", "1280x720");
            properties.setProperty("fullScreen","false");
            properties.setProperty("sound","enabled");
            Locale currentLocale = Locale.getDefault();
            loadLang(currentLocale.getLanguage());
            language.setText(currentLocale.getDisplayLanguage());
            flag.setImage(new Image("Images/" + locale.getLanguage() +".png"));
            properties.setProperty("language",currentLocale.getDisplayLanguage());
            properties.setProperty("flag",locale.getLanguage());

            properties.store(output,null);


            cards.setFitWidth(531);
            cards.setFitHeight(205);

        }
    }

    /**
     * Ο Event Handler του κουμπιού ΜΟΝΟ.
     * @throws IOException εάν αποτύχει να φορτώσει το αρχείο FXML.
     */
    @FXML
    private void playClicked() throws IOException {
        mediaPlayer.seek(Duration.ZERO);
        mediaPlayer.setVolume(0.3f);
        mediaPlayer.play();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SingleModeSettings.fxml"));
        Stage stage = (Stage) playMenu.getScene().getWindow();
        stage.getScene().setRoot(root);

    }

    /**
     * Ο Event Handler του κουμπιού ΠΟΛΛΑΠΛΟ.
     * @throws IOException εάν αποτύχει να φορτώσει το αρχείο FXML.
     */
    @FXML
    private void multiplayerClicked() throws IOException{
        mediaPlayer.seek(Duration.ZERO);
        mediaPlayer.setVolume(0.3f);
        mediaPlayer.play();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MultiplayerSettings.fxml"));
        Stage stage = (Stage) playMenu.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    /**
     * Ο Event Handler του κουμπιού ΜΟΝΟΜΑΧΙΑ.
     * @throws IOException εάν αποτύχει να φορτώσει το αρχείο FXML.
     */
    @FXML
    private void battleClicked() throws IOException{
        mediaPlayer.seek(Duration.ZERO);
        mediaPlayer.setVolume(0.3f);
        mediaPlayer.play();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/BattleSettings.fxml"));
        Stage stage = (Stage) playMenu.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    /**
     * Ο Event Handler του κουμπιού ρυθμίσεων.
     * @throws IOException εάν αποτύχει να φορτώσει το αρχείο FXML.
     */
    @FXML
    private void settingsClicked() throws IOException{
        mediaPlayer.seek(Duration.ZERO);
        mediaPlayer.setVolume(0.3f);
        mediaPlayer.play();
        Stage primaryStage = (Stage) exit.getScene().getWindow();
        GaussianBlur blur = new GaussianBlur(3);
        mainMenu.setEffect(blur);

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SettingsPane.fxml"));

        Stage dialog = new Stage();
        dialog.setTitle("Settings");

        Scene scene = new Scene(root,primaryStage.getWidth()/2,primaryStage.getHeight()/2);
        scene.setFill(Color.TRANSPARENT);
        dialog.setScene(scene);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(primaryStage);
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setResizable(false);

        double centerXPosition = primaryStage.getX() + primaryStage.getWidth()/2d;
        double centerYPosition = primaryStage.getY() + primaryStage.getHeight()/2d;

        dialog.setOnShowing(event -> dialog.hide());

        dialog.setOnShown(event -> {
            dialog.setX(centerXPosition - dialog.getWidth()/2d);
            dialog.setY(centerYPosition - dialog.getHeight()/2d);
            dialog.show();
        });

        dialog.show();
    }

    /**
     * Ο Event Handler του κουμπιού επιδόσεων.
     * @throws IOException Εάν αποτύχει να φορτώσει το αρχείο FXML.
     */
    @FXML
    private void highScoreClicked() throws IOException{
        mediaPlayer.seek(Duration.ZERO);
        mediaPlayer.setVolume(0.3f);
        mediaPlayer.play();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/HighScore.fxml"));
        Stage stage = (Stage) highScore.getScene().getWindow();
        stage.getScene().setRoot(root);

    }

    /**
     * Ο Event Handler του κουμπιού εξόδου.
     * @throws IOException Εάν αποτύχει να φορτώσει το αρχείο FXML.
     */
    @FXML
    private void exitClicked() throws Exception{
        mediaPlayer.seek(Duration.ZERO);
        mediaPlayer.setVolume(0.3f);
        mediaPlayer.play();
        Stage primaryStage = (Stage) exit.getScene().getWindow();
        GaussianBlur blur = new GaussianBlur(3);
        mainMenu.setEffect(blur);

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/Dialog.fxml"));

        Stage dialog = new Stage();
        dialog.setTitle("Exit");

        Scene scene = new Scene(root,425,200);
        scene.setFill(Color.TRANSPARENT);
        dialog.setScene(scene);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(primaryStage);
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setResizable(false);

        double centerXPosition = primaryStage.getX() + primaryStage.getWidth()/2d;
        double centerYPosition = primaryStage.getY() + primaryStage.getHeight()/2d;

        dialog.setOnShowing(event -> dialog.hide());

        dialog.setOnShown(event -> {
            dialog.setX(centerXPosition - dialog.getWidth()/2d);
            dialog.setY(centerYPosition - dialog.getHeight()/2d);
            dialog.show();
        });

        dialog.show();

    }

    /**
     * Ο Event Handler της επιλογής γλώσσας για Ελληνικά.
     * @throws IOException Εάν αποτύχει να φορτώσει το αρχείο.
     */
    @FXML
    private void greek() throws IOException{
        mediaPlayer.seek(Duration.ZERO);
        mediaPlayer.setVolume(0.3f);
        mediaPlayer.play();
        loadLang("el");
        flag.setImage(greece);
        language.setText("Ελληνικά");

        output = new FileOutputStream("config.properties");
        properties.setProperty("language","Ελληνικά");
        properties.setProperty("flag","el");
        properties.store(output,null);
    }

    /**
     * Ο Event Handler της επιλογής γλώσσας για Αγγλικά.
     * @throws IOException Εάν αποτύχει να φορτώσει το αρχείο.
     */
    @FXML
    private void english() throws IOException{
        mediaPlayer.seek(Duration.ZERO);
        mediaPlayer.setVolume(0.3f);
        mediaPlayer.play();
        loadLang("en");
        flag.setImage(uk);
        language.setText("English");

        output = new FileOutputStream("config.properties");
        properties.setProperty("language","English");
        properties.setProperty("flag","en");
        properties.store(output,null);
    }

    /**
     * Φορτώνει τη γλώσσα που εμφανίζεται το FXML.
     * @param lang {@code String}
     */
    private void loadLang(String lang) {
        locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("lang", locale);
        playMenu.setText(bundle.getString("play"));
        highScore.setText(bundle.getString("highScore"));
        exit.setText(bundle.getString("exit"));
        singleMode.setText(bundle.getString("singleModeMenu"));
        multiplayerItem.setText(bundle.getString("multiplayerMenu"));
        battle.setText(bundle.getString("battleMenu"));

        greekMenu.setText(bundle.getString("menuGreek"));
        englishMenu.setText(bundle.getString("menuEnglish"));

    }

}
