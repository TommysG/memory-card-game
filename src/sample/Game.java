package sample;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Game {

    @FXML
    private Button back;
    private GameMode gameMode;
    @FXML
    private AnchorPane Game;
    private ArrayList<ImageView> imageViews,foundCards;
    private ArrayList<Card> cards;

    private Image theme;
    public int number,clicks = 0;
    private int id1,id2;

    private ImageView imageView1,imageView2;
    private Card card1,card2;

    @FXML
    private Label winLabel;



    public Game(){
        gameMode = new GameMode();
        imageViews = new ArrayList<>();
        cards = new ArrayList<>();
        foundCards = new ArrayList<>();
    }

    public void initialize(){
        winLabel.setVisible(false);

    }

    public void setMode(int number,Image theme){
        this.number = number;
        this.theme = theme;

        gameMode.setMode(number);
        gameMode.CreateMode();

        createImageViews();
        createImages();
        //shuffleCards();
        setImages();

        player();
    }

    public void player(){
        for(int i = 0; i<imageViews.size();i++){
            final ImageView imageView = imageViews.get(i);
            final Card card = cards.get(i);
            imageViews.get(i).setOnMouseClicked(event -> clickEvent(imageView,card));
        }
    }

    public void clickEvent(ImageView imageView,Card card){
        clicks++;
        imageView.setDisable(true);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.4),imageView);
        scaleTransition.setFromX(1);
        scaleTransition.setToX(-1);
        scaleTransition.play();
        scaleTransition.setOnFinished(event -> {imageView.setScaleX(1);imageView.setImage(card.getValue());});


        if(clicks == 1){
            id1 = card.getId();
            imageView1 = imageView;
            card1 = card;
        }
        if(clicks == 2){
            id2 = card.getId();
            imageView2 = imageView;
            card2 = card;

            disableAll();
            if(id1 == id2){
                foundCards.add(imageView1);
                foundCards.add(imageView2);
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.4)));
                timeline.play();
                timeline.setOnFinished(event -> {
                    imageView1.setDisable(true);
                    imageView2.setDisable(true);
                    imageView1.setOpacity(0.6);
                    imageView2.setOpacity(0.6);
                    enableAll();
                });
            }
            else{
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.2)));
                timeline.play();
                timeline.setOnFinished(event -> {
                    imageView1.setImage(card1.getBackground());
                    imageView2.setImage(card2.getBackground());
                    imageView1.setDisable(false);
                    imageView2.setDisable(false);
                    enableAll();
                });

            }
            if(foundCards.size() == gameMode.getSize()){
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.2)));
                timeline.play();
                timeline.setOnFinished(event -> {
                    eraseCards();
                    winLabel.setVisible(true);
                });
            }
            clicks = 0;
        }


    }

    public void eraseCards(){
        for(int i = 0;i<imageViews.size();i++){
            Game.getChildren().remove(imageViews.get(i));
        }
    }

    public void enableAll(){
        for(int i = 0;i<imageViews.size();i++){
            imageViews.get(i).setDisable(false);
        }

        for(int i = 0;i<foundCards.size();i++){
            foundCards.get(i).setDisable(true);
        }
    }

    public void disableAll(){
        for(int i = 0;i<imageViews.size();i++){
            imageViews.get(i).setDisable(true);
        }
    }

    public void backClicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Settings.fxml"));
        Stage stage = (Stage) back.getScene().getWindow();
        stage.setScene(new Scene(root,800,600));
    }

    public void createImageViews(){
        int x = 105,y = 35,count = 0;
        for(int i = 0;i<gameMode.getSize();i++){
            ImageView imageView = new ImageView();
            imageView.setFitWidth(90);
            imageView.setFitHeight(130);
            if(i%gameMode.getRows() == 0 && i != 0) {
                y = y + 135;
                imageView.setLayoutX(x);
                imageView.setLayoutX(y);
                count = 0;
            }
            imageView.setLayoutX(x + count);
            imageView.setLayoutY(y);
            Game.getChildren().add(imageView);
            imageViews.add(imageView);
            count = count + 100;

        }
    }

    public void createImages() {
        int times = 0;
        int j = 0;
        for(int i =1; i<=gameMode.getSize();i++) {
            if(i%gameMode.getSelectCards() == 1){
                times++;
                j++;
            }
            Image value = new Image("Images/Cards/"+ j + ".png");
            Card image2 = new Card(value,theme,times);
            cards.add(image2);
        }
    }

    public void setImages(){
        for(int i = 0;i<imageViews.size();i++){
            imageViews.get(i).setImage(cards.get(i).getBackground());
        }
    }

    public void shuffleCards(){
        Collections.shuffle(imageViews);
    }

}
