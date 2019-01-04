package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.util.Properties;
import java.util.ResourceBundle;

public class MainMenu {

    @FXML
    private Button exit,credits;
    @FXML
    private MenuButton language,resolution,playMenu;
    @FXML
    private ImageView flag,settings;
    @FXML
    private AnchorPane mainMenu;
    @FXML
    private VBox vbox;
    @FXML
    private MenuItem fullScreen,x86,singleMode,multiplayerMode;

    private Properties properties = new Properties();
    private ResourceBundle bundle;
    private OutputStream output = null;
    private InputStream input = null;

    private Image greece = new Image("Images/el.png");
    private Image uk = new Image("Images/en.png");

    private Label label;


    public void initialize() throws IOException{
        File f = new File("config.properties");

        if(f.exists()) {
            input = new FileInputStream("config.properties");
            properties.load(input);

            resolution.setText(properties.getProperty("resolution"));
        }
        else{
            output = new FileOutputStream("config.properties");
            properties.setProperty("width","800");
            properties.setProperty("height","600");
            properties.setProperty("resolution", "800x600");
            properties.setProperty("fullScreen","false");
            properties.store(output,null);

            resolution.setText(properties.getProperty("resolution"));
        }

    }

    public void playClicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SingleModeSettings.fxml"));
        Stage stage = (Stage) playMenu.getScene().getWindow();
        //double width = Double.parseDouble(properties.getProperty("width"));
        // double height = Double.parseDouble(properties.getProperty("height"));
        // boolean fullScreen = Boolean.parseBoolean(properties.getProperty("fullScreen"));
        stage.getScene().setRoot(root);

    }


    public void multiplayerClicked() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("MultiplayerSettings.fxml"));
        Stage stage = (Stage) playMenu.getScene().getWindow();
        //double width = Double.parseDouble(properties.getProperty("width"));
        // double height = Double.parseDouble(properties.getProperty("height"));
        // boolean fullScreen = Boolean.parseBoolean(properties.getProperty("fullScreen"));
        stage.getScene().setRoot(root);
    }

    public void settingsClicked() throws IOException{
        Stage primaryStage = (Stage) exit.getScene().getWindow();
        GaussianBlur blur = new GaussianBlur(3);
        mainMenu.setEffect(blur);

        Parent root = FXMLLoader.load(getClass().getResource("SettingsPane.fxml"));

        Stage dialog = new Stage();
        dialog.setTitle("QuickStart");

        Scene scene = new Scene(root,450,350);
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

    public void english(){
        flag.setImage(uk);
        language.setText("English");
    }

    public void greek(){
        flag.setImage(greece);
        language.setText("Greek");
    }

    public void creditsClicked(){

    }

    public void exitClicked() throws Exception{
        Stage primaryStage = (Stage) exit.getScene().getWindow();
        GaussianBlur blur = new GaussianBlur(3);
        mainMenu.setEffect(blur);

        Parent root = FXMLLoader.load(getClass().getResource("Dialog.fxml"));

        Stage dialog = new Stage();
        dialog.setTitle("Exit");

        Scene scene = new Scene(root,400,200);
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

    public void fullScreenSelected() throws IOException{
        output = new FileOutputStream("config.properties");
        properties.setProperty("fullScreen","true");
        properties.setProperty("width","999");
        properties.setProperty("height","999");
        properties.setProperty("resolution","FullScreen");
        properties.store(output,null);
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Stage stage = (Stage) resolution.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.show();
    }

    public void x1024Selected() throws IOException{
        output = new FileOutputStream("config.properties");
        properties.setProperty("resolution","1280x720");
        properties.setProperty("fullScreen","false");
        properties.setProperty("width","1280");
        properties.setProperty("height","720");
        properties.store(output,null);

        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Stage stage = (Stage) resolution.getScene().getWindow();
        stage.setScene(new Scene(root,1280,720));
    }

    public void x86Selected() throws IOException {
        output = new FileOutputStream("config.properties");
        properties.setProperty("resolution","800x600");
        properties.setProperty("fullScreen","false");
        properties.setProperty("width","800");
        properties.setProperty("height","600");
        properties.store(output,null);

        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Stage stage = (Stage) resolution.getScene().getWindow();
        stage.setScene(new Scene(root,800,600));
    }

    public void x64Selected() throws IOException{
        output = new FileOutputStream("config.properties");
        properties.setProperty("resolution","600x400");
        properties.setProperty("fullScreen","false");
        properties.setProperty("width","600");
        properties.setProperty("height","400");
        properties.store(output,null);

        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Stage stage = (Stage) resolution.getScene().getWindow();
        stage.setScene(new Scene(root,600,400));
    }
}
