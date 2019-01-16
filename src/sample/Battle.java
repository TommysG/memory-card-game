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
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.Random;
import javafx.event.*;

public class Battle extends Multiplayer {

    @FXML
    private Button back,nextButton;
    @FXML
    private GridPane gridTable1,gridTable2;
    private Image theme;

    private ArrayList<ImageView> imageViews2,foundCards2;
    private ArrayList<Card> cards2;

    private int clicks,random1;
    private ImageView playerImageview,botImageView,botImageViewE;
    private Card playerCard,botCard,botCardE;

    private boolean flag,boolRan;
    private GameMode gameMode;

    private int botScore,playerScore,wins;
    private Properties properties = new Properties();
    private InputStream input = null;
    private OutputStream output = null;

    @FXML
    private Label player1,player2,turn,nextTurn;


    @Override
    public void initialize() throws IOException {
        nextButton.setDisable(true);
        File f =new File("score.properties");

        if(f.exists()){
            input = new FileInputStream("score.properties");
            properties.load(input);
            wins = Integer.parseInt(properties.getProperty("BattleWins"));
        }
    }

    public Battle(){
        imageViews2 = new ArrayList<>();
        foundCards = new ArrayList<>();
        foundCards2 =new ArrayList<>();
        cards2 = new ArrayList<>();
        gameMode = new GameMode();
        flag = false;
        clicks = 0;
        boolRan = false;
    }

    @Override
    public void setMode(GameMode gameMode, Image theme) throws IOException {
        super.setMode(gameMode, theme);
        this.theme = theme;
        this.gameMode = gameMode;

        gameMode.gameResolution();

        createImageViews(gridTable1,imageViews);
        createImages(cards);
        // shuffleCards();
        setImages(imageViews,cards);
        player();

        createImageViews(gridTable2,imageViews2);
        createImages(cards2);
        // shuffleCards();
        setImages(imageViews2,cards2);

        turn.setText("Turn: Player1(You)");
        nextTurn.setText("Next Turn:"+gameMode.getRival1());
    }

    @Override
    public void clickEvent(ImageView imageView, Card card) {
        if(foundCards.size() == gameMode.getSize()*2) {
            findWinner();
            nextButton.setDisable(true);
            return;
        }
        clicks++;

        flipAnimation(imageView, card);
        playerImageview = imageView;
        playerCard = card;

        if(clicks == 1){
            nextButton.setDisable(false);
        }

        nextButton.setOnAction(event -> {
            if (clicks==0 ) {
                turn.setText("Turn: Player1(You)");
                nextTurn.setText("Next Turn: "+gameMode.getRival1());
                enableAll();
            }
            else if (clicks==1 ) {
                turn.setText("Turn: "+gameMode.getRival1());
                nextTurn.setText("Next Turn: Player1(You)");
                if(gameMode.getRival1().equals("Goldfish")){
                    this.goldfish();
                    compareGoldfish(playerImageview,playerCard);
                    clicks++;
                }
                else if(gameMode.getRival1().equals("Elephant")){
                    this.elephant(playerCard);
                    if(!flag){
                        this.goldfish();
                        compareGoldfish(playerImageview,playerCard);
                    }
                    else{
                        compareElephant(playerImageview,playerCard);
                    }
                    clicks++;
                }
                else if(gameMode.getRival1().equals("Kangaroo")){
                    this.kangaroo(playerCard);
                    if(!flag){
                        this.goldfish();
                        compareGoldfish(playerImageview,playerCard);
                    }
                    else{
                        compareKangaroo(playerImageview,playerCard);
                    }
                    clicks++;
                }
            }
            else if (clicks==2) {
                if(gameMode.getRival1().equals("Goldfish")){
                    this.goldfish();
                    clicks++;
                }
                else if(gameMode.getRival1().equals("Elephant")){
                    this.elephant(playerCard);
                    if(!flag){
                        this.goldfish();
                    }
                    clicks++;
                }
                else if(gameMode.getRival1().equals("Kangaroo")){
                    this.kangaroo(playerCard);
                    if(!flag){
                        this.goldfish();
                    }
                    clicks++;
                }
            }
            else if(clicks == 3) {
                turn.setText("Turn: Player1(You)");
                nextTurn.setText("Next Turn: "+gameMode.getRival1());
                enableAll();
            }
            else{
                if(gameMode.getRival1().equals("Goldfish")){
                    compareGoldfish(playerImageview, playerCard);
                    clicks = 0;
                }
                else if(gameMode.getRival1().equals("Elephant")){
                    if(!flag){
                        compareGoldfish(playerImageview,playerCard);
                    }
                    else{
                        compareElephant(playerImageview,playerCard);
                    }
                    clicks = 0;
                }
                else if(gameMode.getRival1().equals("Kangaroo")){
                    if(!flag){
                        compareGoldfish(playerImageview,playerCard);
                    }
                    else{
                        compareKangaroo(playerImageview,playerCard);
                    }
                    clicks = 0;
                }
            }
        });


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
        nextButton.setDisable(true);
        if(foundCards.size() == gameMode.getSize()*2) {
            findWinner();
            nextButton.setDisable(true);
            return;
        }
        Random random = new Random();
        random1 = random.nextInt(imageViews2.size());
        boolRan = random.nextBoolean();

        while(foundCards.contains(imageViews2.get(random1)))
            random1 = random.nextInt(imageViews2.size());

        botImageView = imageViews2.get(random1);
        botCard = cards2.get(random1);

        if(boolRan){
            if(!seenImageViewsKangaroo.contains(botImageView)){
                seenImageViewsKangaroo.add(botImageView);
                seenCardsKangaroo.add(botCard);
            }
        }

        if(!seenImageViewsElephant.contains(botImageView)){
            seenImageViewsElephant.add(botImageView);
            seenCardsElephant.add(botCard);
        }

        flipAnimation(botImageView,botCard);
        nextButton.setDisable(false);
    }

    private void elephant(Card card){
        nextButton.setDisable(true);
        if(foundCards.size() == gameMode.getSize()*2) {
            findWinner();
            nextButton.setDisable(true);
            return;
        }
        flag = false;
        ImageView seenImageView1 = imageViews2.get(0);
        Card seenCard1 = cards2.get(0);

        for(int i = 0;i<seenCardsElephant.size();i++){
            if(seenCardsElephant.get(i).getId() == card.getId()){
                seenImageView1 = seenImageViewsElephant.get(i);
                seenCard1 = seenCardsElephant.get(i);
                flag = true;
                break;
            }
        }
        System.out.printf("SIZE IS: %d\n",seenCardsElephant.size());

        botImageView = seenImageView1;
        botCard = seenCard1;
        nextButton.setDisable(false);
    }

    private void kangaroo(Card card){
        nextButton.setDisable(true);
        if(foundCards.size() == gameMode.getSize()*2) {
            findWinner();
            nextButton.setDisable(true);
            return;
        }
        flag = false;
        ImageView seenImageView1 = imageViews2.get(0);
        Card seenCard1 = cards2.get(0);

        for(int i = 0;i<seenCardsKangaroo.size();i++){
            if(seenCardsKangaroo.get(i).getId() == card.getId()){
                seenImageView1 = seenImageViewsKangaroo.get(i);
                seenCard1 = seenCardsKangaroo.get(i);
                flag = true;
                break;
            }
        }
        System.out.printf("SIZE IS: %d\n",seenCardsElephant.size());

        botImageView = seenImageView1;
        botCard = seenCard1;
        nextButton.setDisable(false);
    }

    private void compareKangaroo(ImageView playerImageview,Card playerCard){
        nextButton.setDisable(true);
        if(flag){
            if(clicks == 1){
                botScore++;
                player2.setText("Player2: "+botScore);
            }
            else if(clicks == 4){
                playerScore++;
                player1.setText("Player1: "+playerScore);
            }

            foundCards.add(botImageView);
            foundCards.add(playerImageview);
            seenImageViewsKangaroo.remove(botImageView);
            seenCardsKangaroo.remove(botCard);
            seenImageViewsElephant.remove(botImageView);
            seenCardsElephant.remove(botCard);
            flipAnimation(botImageView,botCard);
            new Timeline(new KeyFrame(Duration.seconds(0.6),event -> {
                botImageView.setDisable(true);
                playerImageview.setDisable(true);
                botImageView.setOpacity(0.6);
                playerImageview.setOpacity(0.6);
                nextButton.setDisable(false);
            })).play();
        }
    }

    private void compareGoldfish(ImageView playerImageview,Card playerCard){
        nextButton.setDisable(true);
        if(botCard.getId() == playerCard.getId()){
            if(clicks == 1){
                botScore++;
                player2.setText("Player2: "+botScore);
            }
            else if(clicks == 4){
                playerScore++;
                player1.setText("Player1: "+playerScore);
            }
            foundCards.add(botImageView);
            foundCards.add(playerImageview);
            seenImageViewsElephant.remove(botImageView);
            seenCardsElephant.remove(botCard);
            seenCardsKangaroo.remove(botCard);
            seenImageViewsKangaroo.remove(botImageView);
            new Timeline(new KeyFrame(Duration.seconds(0.6), event -> {
                botImageView.setDisable(true);
                playerImageview.setDisable(true);
                botImageView.setOpacity(0.6);
                playerImageview.setOpacity(0.6);
                nextButton.setDisable(false);
            })).play();
        }
        else {
            new Timeline(new KeyFrame(Duration.seconds(1.5), event -> {
                botImageView.setImage(botCard.getBackground());
                playerImageview.setImage(playerCard.getBackground());
                nextButton.setDisable(false);
            })).play();
        }
    }

    private void compareElephant(ImageView playerImageview,Card playerCard){
        nextButton.setDisable(true);
        if(flag){
            if(clicks == 1){
                botScore++;
                player2.setText("Player2: "+botScore);
            }
            foundCards.add(botImageView);
            foundCards.add(playerImageview);
            seenImageViewsElephant.remove(botImageView);
            seenCardsElephant.remove(botCard);
            seenImageViewsKangaroo.remove(botImageView);
            seenCardsKangaroo.remove(botCard);
            flipAnimation(botImageView,botCard);
            new Timeline(new KeyFrame(Duration.seconds(0.6),event -> {
                botImageView.setDisable(true);
                playerImageview.setDisable(true);
                botImageView.setOpacity(0.6);
                playerImageview.setOpacity(0.6);
                nextButton.setDisable(false);
            })).play();
        }
    }

    private void findWinner(){
        if (playerScore>botScore){
            wins++;
            try {
                output = new FileOutputStream("score.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            properties.setProperty("BattleWins",Integer.toString(wins));
            try {
                properties.store(output,null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
