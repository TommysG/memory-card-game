package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.Properties;
import java.util.ResourceBundle;

public class SettingsPane {


    @FXML
    private MenuButton language,resolution;
    @FXML
    private MenuItem fullScreen,x86;

    private Properties properties = new Properties();
    private ResourceBundle bundle;
    private OutputStream output = null;
    private InputStream input = null;

    @FXML
    private Button close;


    public void initialize() throws IOException {
        File f = new File("config.properties");

        properties.setProperty("width","800");
        properties.setProperty("height","600");
        properties.setProperty("fullScreen","false");

        if(f.exists()) {
            input = new FileInputStream("config.properties");
            properties.load(input);

            resolution.setText(properties.getProperty("resolution"));
        }

    }

    public void fullScreenSelected() throws IOException{
        resolution.setText("FullScreen");
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

    public void closeSettings(){
        Stage settingsPane = (Stage) close.getScene().getWindow();
        Parent mainMenu = settingsPane.getOwner().getScene().getRoot();
        mainMenu.setEffect(null);

        settingsPane.close();
    }
}
