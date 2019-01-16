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
import java.util.Properties;

public class HighScore {

    @FXML
    private Button back;
    @FXML
    private Label sm1,sm2,sm3,m1,m2,m3,battle;

    private Properties properties = new Properties();
    private InputStream input = null;

    public void initialize() throws IOException{

        File f =new File("score.properties");


        if(f.exists()){
            input = new FileInputStream("score.properties");
            properties.load(input);

            if(!properties.getProperty("SingleModeHighScore1").equals("99999")) {
                sm1.setText("Single Mode Normal: " + properties.getProperty("SingleModeHighScore1"));
            }
            else{
                sm1.setText("Single Mode Normal: -" );
            }
            if(!properties.getProperty("SingleModeHighScore2").equals("99999")) {
                sm2.setText("Single Mode Double: " + properties.getProperty("SingleModeHighScore2"));
            }
            else {
                sm2.setText("Single Mode Double: -" );
            }
            if(!properties.getProperty("SingleModeHighScore3").equals("99999")) {
                sm3.setText("Single Mode Triple: " + properties.getProperty("SingleModeHighScore3"));
            }
            else{
                sm3.setText("Single Mode Triple: -" );
            }

            m1.setText("Mutliplayer Normal: "+properties.getProperty("MultiplayerWins1"));
            m2.setText("Mutliplayer Double: "+properties.getProperty("MultiplayerWins2"));
            m3.setText("Mutliplayer Triple: "+properties.getProperty("MultiplayerWins3"));

            battle.setText("Battle Mode: " +properties.getProperty("BattleWins"));
        }

    }

    public void backClicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Stage stage = (Stage) back.getScene().getWindow();
        stage.getScene().setRoot(root);
    }
}
