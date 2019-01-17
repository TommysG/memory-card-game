package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

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

    public BattleSettings(){
        gameMode = new GameMode();
    }

    public void initialize() throws IOException{
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
            else if(lang.equals("em")){
                gold = "Goldfish";
                kang = "Kangaroo";
                ele = "Elephant";
            }
        }
    }

    public void goldfishSelected(){
        rivalSelector.setText(gold);
        gameMode.setRival1("Goldfish");
    }

    public void kangarooSelected(){
        rivalSelector.setText(kang);
        gameMode.setRival1("Kangaroo");
    }

    public void elephantSelected(){
        rivalSelector.setText(ele);
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
        b.battleStart();
        stage.getScene().setRoot(Loader.getRoot());
    }

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
