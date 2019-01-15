package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Main extends Application {


    private Properties properties = new Properties();

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
        primaryStage.setResizable(true);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
