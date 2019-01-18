package sample;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * <h1>Η κλάση από την οποία αρχίζει το παιχνίδι</h1>
 */
public class Main extends Application {


    private Properties properties = new Properties();


    /**
     * Δημιουργεί το βασικό Stage
     * @param primaryStage είναι το βασικό stage του παιχνιδιού
     * @throws Exception εάν αποτύχει να φορτώσει το αρχείο FXML
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));

        double width = 1280,height =720;
        boolean fullScreen;

        File f = new File("config.properties");
        if(f.exists()) {
            InputStream input = new FileInputStream("config.properties");
            properties.load(input);

            width = Double.parseDouble(properties.getProperty("width"));
            height = Double.parseDouble(properties.getProperty("height"));
            fullScreen = Boolean.parseBoolean(properties.getProperty("fullScreen"));
            primaryStage.setFullScreen(fullScreen);
            primaryStage.setFullScreenExitHint("");
            primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        }

        primaryStage.setTitle("Memory Game");
        Scene scene = new Scene(root,width,height);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
