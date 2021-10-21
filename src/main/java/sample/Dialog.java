package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * <h1>Η κλάση Exit</h1>
 */
public class Dialog  {

    @FXML
    private Button yes,no;

    @FXML
    private Label exit;

    private Properties properties = new Properties();
    private MediaPlayer mediaPlayer;

    /**
     * Κατασκευαστής της κλάσης
     */
    public Dialog(){
        Media buttonSound = new Media(getClass().getClassLoader().getResource("Sounds/buttonSound.wav").toExternalForm());
        mediaPlayer = new MediaPlayer(buttonSound);
    }
    /**
     * Κάνει initialize.
     * @throws IOException Αν αποτύχει να φορτώσει το αρχειο.
     */
    @FXML
    private void initialize() throws IOException{
        File f = new File("config.properties");

        if(f.exists()) {
            InputStream input = new FileInputStream("config.properties");
            properties.load(input);

            String lang = properties.getProperty("flag");
            loadLang(lang);
        }
    }

    /**
     * Το event handler του κουμπιού NAI.
     */
    @FXML
    private void yesClicked(){
        mediaPlayer.seek(Duration.ZERO);
        mediaPlayer.setVolume(0.3f);
        mediaPlayer.play();
        Platform.exit();
    }
    /**
     * Το event handler του κουμπιού ΟΧΙ.
     */
    @FXML
    private void noClicked() {
        mediaPlayer.seek(Duration.ZERO);
        mediaPlayer.setVolume(0.3f);
        mediaPlayer.play();
        Stage dialog = (Stage) no.getScene().getWindow();

        Parent root = dialog.getOwner().getScene().getRoot();
        root.setEffect(null);

        dialog.close();
    }

    /**
     * Φορτώνει τη γλώσσα του παιχνιδιού.
     * @param lang {@code String}
     */
    private void loadLang(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("lang", locale);

        exit.setText(bundle.getString("exitDialog"));
        yes.setText(bundle.getString("yes"));
        no.setText(bundle.getString("no"));
    }

}