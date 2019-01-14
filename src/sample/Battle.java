package sample;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Battle extends Multiplayer {

    @FXML
    private Button back,nextButton;
    @FXML
    private GridPane gridTable1,gridTable2;
    private Image theme;

    private ArrayList<ImageView> imageViews2;
    private ArrayList<Card> cards2;

    private int clicks,random1,round;
    private ImageView playerImageview,botImageView;
    private Card playerCard,botCard;

    private ArrayList<ImageView> foundCards;

    private boolean flag;


    @Override
    public void initialize() {
    }

    public Battle(){
        imageViews2 = new ArrayList<>();
        foundCards = new ArrayList<>();
        cards2 = new ArrayList<>();
        clicks = 0;
        round = 0;
    }

    @Override
    public void setMode(GameMode gameMode, Image theme) throws IOException {
        super.setMode(gameMode, theme);
        this.theme = theme;

        createImageViews(gridTable1,imageViews);
        createImages(cards);
        // shuffleCards();
        setImages(imageViews,cards);
        player();

        createImageViews(gridTable2,imageViews2);
        createImages(cards2);
        // shuffleCards();
        setImages(imageViews2,cards2);


    }

    @Override
    public void clickEvent(ImageView imageView, Card card) {
        System.out.println("ONE CARD CLICKED");
        clicks++;

        flipAnimation(imageView, card);

        nextButton.setOnAction(event -> {
        });

        playerImageview = imageView;
        playerCard = card;

        disableAll();
    }

    private void flipAnimation(ImageView imageView,Card card){
        imageView.setDisable(true);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.4),imageView);
        scaleTransition.setFromX(1);
        scaleTransition.setToX(-1);
        scaleTransition.play();
        scaleTransition.setOnFinished(event -> {imageView.setScaleX(1);imageView.setImage(card.getValue());});
    }

    public void backClicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("BattleSettings.fxml"));
        Stage stage = (Stage) back.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    @Override
    public void createImages(ArrayList<Card> cards) {
        for(int i =1; i<=gameMode.getSize();i++) {
            Image value = new Image("Images/Cards/"+ i + ".png");
            Card image2 = new Card(value,theme,i);
            cards.add(image2);
        }
    }

    public void nextClicked(){
    }

    public void goldfish() {
        Random random = new Random();
        random1 = random.nextInt(imageViews2.size());

        while(foundCards.contains(imageViews2.get(random1)))
            random1 = random.nextInt(imageViews2.size());

        botImageView = imageViews2.get(random1);
        botCard = cards2.get(random1);

        flipAnimation(botImageView,botCard);
    }

    private void compare(ImageView imageView,Card card){
        if(botCard.getId() == card.getId()){
            foundCards.add(botImageView);
            new Timeline(new KeyFrame(Duration.seconds(0.6), event -> {
                botImageView.setDisable(true);
                imageView.setDisable(true);
                botImageView.setOpacity(0.6);
                imageView.setOpacity(0.6);
            })).play();
        }
        else {
            new Timeline(new KeyFrame(Duration.seconds(1.5), event -> {
                botImageView.setImage(botCard.getBackground());
                imageView.setImage(card.getBackground());
            })).play();
        }
    }
}
