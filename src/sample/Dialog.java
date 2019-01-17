package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * <h1>Η κλάση της εξόδου από το παιχνίδι.</h1>
 */
public class Dialog  {

    @FXML
    private Button yes,no;

    @FXML
    private Label exit;

    private Properties properties = new Properties();
    private OutputStream output = null;
    private InputStream input = null;

    /**
     * Κάνει initialize.
     * @throws IOException Αν αποτύχει να φορτώσει το αρχειο.
     */
    public void initialize() throws IOException{
        File f = new File("config.properties");

        if(f.exists()) {
            input = new FileInputStream("config.properties");
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
        Platform.exit();
    }
    /**
     * Το event handler του κουμπιού ΟΧΙ.
     */
    @FXML
    private void noClicked() {
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
        ResourceBundle bundle = ResourceBundle.getBundle("sample.lang", locale);

        exit.setText(bundle.getString("exitDialog"));
        yes.setText(bundle.getString("yes"));
        no.setText(bundle.getString("no"));
    }

}