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
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
    private ImageView flag,settings,cards;
    @FXML
    private AnchorPane mainMenu;
    @FXML
    private VBox vbox;
    @FXML
    private MenuItem fullScreen,x86,singleMode,multiplayerMode;

    private Properties properties = new Properties();
    private Properties properties2 = new Properties();
    private ResourceBundle bundle;
    private OutputStream output = null,output2 = null;
    private InputStream input = null,input2 = null;

    private Image greece = new Image("Images/el.png");
    private Image uk = new Image("Images/en.png");

    private Label label;


    public void initialize() throws IOException{
        File f = new File("config.properties");
        File f2 =new File("score.properties");

        if(f2.exists()){
            input2 = new FileInputStream("score.properties");
            properties2.load(input2);
        }
        else{
            output2 = new FileOutputStream("score.properties");
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
            input = new FileInputStream("config.properties");
            properties.load(input);

            resolution.setText(properties.getProperty("resolution"));
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
            properties.store(output,null);

            cards.setFitWidth(531);
            cards.setFitHeight(205);

            resolution.setText(properties.getProperty("resolution"));
        }
    }

    public void playClicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SingleModeSettings.fxml"));
        Stage stage = (Stage) playMenu.getScene().getWindow();
        stage.getScene().setRoot(root);

    }


    public void multiplayerClicked() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("MultiplayerSettings.fxml"));
        Stage stage = (Stage) playMenu.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    public void battleClicked() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("BattleSettings.fxml"));
        Stage stage = (Stage) playMenu.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    public void settingsClicked() throws IOException{
        Stage primaryStage = (Stage) exit.getScene().getWindow();
        GaussianBlur blur = new GaussianBlur(3);
        mainMenu.setEffect(blur);

        Parent root = FXMLLoader.load(getClass().getResource("SettingsPane.fxml"));

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

    public void x1280Selected() throws IOException{
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
        properties.setProperty("resolution","600x600");
        properties.setProperty("fullScreen","false");
        properties.setProperty("width","600");
        properties.setProperty("height","600");
        properties.store(output,null);

        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Stage stage = (Stage) resolution.getScene().getWindow();
        stage.setScene(new Scene(root,600,600));
    }
}
