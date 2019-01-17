package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * <h1>Η κλάση των επιδόσεων.</h1>
 */
public class HighScore {

    @FXML
    private Button back;
    @FXML
    private Label sm1,sm2,sm3,m1,m2,m3,battle,singleModeLabel,multiplayerLabel,battleLabel,battleWinsLabel,highScore;

    private String s1,s2,s3,mu1,mu2,mu3,bat;

    private Properties properties = new Properties();
    private Properties properties2 = new Properties();

    /**
     * Φορτώνει τις επιδόσεις απο το αρχείο που έχει δημιουργηθεί.
     * @throws IOException εάν αποτύχει να φορτώσει το αρχείο.
     */
    public void initialize() throws IOException{

        File f =new File("score.properties");
        File f2 = new File("config.properties");

        if(f2.exists()) {
            InputStream input2 = new FileInputStream("config.properties");
            properties2.load(input2);

            String lang = properties2.getProperty("flag");
            loadLang(lang);
        }


        if(f.exists()){
            InputStream input = new FileInputStream("score.properties");
            properties.load(input);

            if(!properties.getProperty("SingleModeHighScore1").equals("99999")) {
                sm1.setText(s1 + properties.getProperty("SingleModeHighScore1"));
            }
            else{
                sm1.setText(s1 + "-" );
            }
            if(!properties.getProperty("SingleModeHighScore2").equals("99999")) {
                sm2.setText(s2 + properties.getProperty("SingleModeHighScore2"));
            }
            else {
                sm2.setText(s2 + " -" );
            }
            if(!properties.getProperty("SingleModeHighScore3").equals("99999")) {
                sm3.setText(s3 + properties.getProperty("SingleModeHighScore3"));
            }
            else{
                sm3.setText(s3 + " -" );
            }

            m1.setText(mu1+properties.getProperty("MultiplayerWins1"));
            m2.setText(mu2+properties.getProperty("MultiplayerWins2"));
            m3.setText(mu3+properties.getProperty("MultiplayerWins3"));

            battleWinsLabel.setText(bat +properties.getProperty("BattleWins"));
        }

    }

    /**
     * Το event handler του κουμπιού που σε πηγαίνει στην προήγουμενη σκηνή.
     * @throws IOException εάν αποτύχει να φορτώσει το αρχειό FXML.
     */
    @FXML
    private void backClicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Stage stage = (Stage) back.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    /**
     * Φορτώνει τη γλώσσα που εμφανίζονται οι επιδόσεις.
     * @param lang {@code String} παίρνει δύο τιμές "el" και "en" ανάλογα την γλώσσα
     */
    private void loadLang(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("sample.lang", locale);

        singleModeLabel.setText(bundle.getString("singleModeLabel"));
        multiplayerLabel.setText(bundle.getString("multiplayerLabel"));
        battleLabel.setText(bundle.getString("battleLabel"));
        s1 = bundle.getString("normalScore");
        s2 = bundle.getString("doubleScore");
        s3 = bundle.getString("tripleScore");
        mu1 = bundle.getString("normalWinsLabel");
        mu2 = bundle.getString("doubleWinsLabel");
        mu3 = bundle.getString("tripleWinsLabel");
        bat = bundle.getString("battleWinsLabel");
        highScore.setText(bundle.getString("highScore"));

    }
}
