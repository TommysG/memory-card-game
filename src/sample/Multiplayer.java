package sample;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class Multiplayer extends Game {

    private int clicks = 0,random1,random2;
    @FXML
    private GridPane grid;
    @FXML
    private Button back;


    private double secs;

    public Multiplayer(){
    }

    @Override
    public void setMode(GameMode gameMode, Image theme) throws IOException {
        super.setMode(gameMode, theme);

        createImageViews(grid);
        createImages();
        setImages();

        player();
    }

     @Override
     public void player() {
         super.player();
     }

    @Override
    public void clickEvent(ImageView imageView, Card card) {
        super.clickEvent(imageView,card);
        clicks++;

        Timeline player1 = new Timeline(new KeyFrame(Duration.seconds(1.5),event -> {

            if(clicks == 2){
                if(gameMode.getPlayer1().equals("Goldfish")){
                    goldfish();
                    clicks = clicks +2;
                    if(gameMode.getPlayersNumber() == 1) {
                        enableAll();
                        clicks = 0;
                    }
                }
                else if(gameMode.getPlayer1().equals("Human")){
                        enableAll();
                    if(gameMode.getPlayersNumber() == 1) {
                        clicks = 0;
                    }
                }
                else if(gameMode.getPlayer1().equals("Elephant")){
                    elephant();
                    clicks = clicks +2;
                    if(gameMode.getPlayersNumber() == 1) {
                        enableAll();
                        clicks = 0;
                    }
                }
            }
        }));
        player1.play();

        Timeline player2 = new Timeline(new KeyFrame(Duration.seconds(3),event -> {
            if(clicks == 4){
                if(gameMode.getPlayer2().equals("Goldfish")){
                    goldfish();
                    clicks = clicks +2;
                    if(gameMode.getPlayersNumber() == 2) {
                        enableAll();
                        clicks = 0;
                    }
                }
                else if(gameMode.getPlayer2().equals("Human")){
                    enableAll();
                    if(gameMode.getPlayersNumber() == 2) {
                        clicks = 0;
                    }
                }
                else if(gameMode.getPlayer2().equals("Elephant")){
                    elephant();
                    clicks = clicks +2;
                    if(gameMode.getPlayersNumber() == 2) {
                        enableAll();
                        clicks = 0;
                    }
                }
            }
        }));
        player2.play();

        Timeline player3 = new Timeline(new KeyFrame(Duration.seconds(4.5),event -> {
            if(clicks == 6){
                if(gameMode.getPlayer3().equals("Goldfish")){
                    goldfish();
                    clicks = clicks +2;
                    if(gameMode.getPlayersNumber() == 3) {
                        enableAll();
                        clicks = 0;
                    }
                }
                else if(gameMode.getPlayer3().equals("Human")){
                    enableAll();
                    if(gameMode.getPlayersNumber() == 3) {
                        clicks = 0;
                    }
                }
                else if(gameMode.getPlayer3().equals("Elephant")){
                    elephant();
                    clicks = clicks +2;
                    if(gameMode.getPlayersNumber() == 3) {
                        enableAll();
                        clicks = 0;
                    }
                }
            }
        }));
        player3.play();

    }

    public void goldfish(){
        disableAll();
        Random random = new Random();
        random1 = random.nextInt(imageViews.size());
        random2 = random.nextInt(imageViews.size());

        while((foundCards.contains(imageViews.get(random1)) || foundCards.contains(imageViews.get(random2)) || random1==random2) && foundCards.size()!=gameMode.getSize()){
            random1 = random.nextInt(imageViews.size());
            random2 = random.nextInt(imageViews.size());
        }

        final ImageView imageView1 = imageViews.get(random1);
        final ImageView imageView2 = imageViews.get(random2);
        final Card card1 = cards.get(random1);
        final Card card2 = cards.get(random2);

        if(!seenImageViews.contains(imageView1)){
            seenImageViews.add(imageView1);
            seenCards.add(card1);
        }

        if(!seenImageViews.contains(imageView2)){
            seenImageViews.add(imageView2);
            seenCards.add(card2);
        }

        ScaleTransition scale1 = new ScaleTransition(Duration.seconds(0.5),imageView1);
        scale1.setFromX(1);
        scale1.setToX(-1);
        scale1.play();
        scale1.setOnFinished(event -> {imageView1.setScaleX(1); imageView1.setImage(card1.getValue());});

        ScaleTransition scale2 = new ScaleTransition(Duration.seconds(0.5),imageView2);
        scale2.setFromX(1);
        scale2.setToX(-1);
        scale2.play();
        scale2.setOnFinished(event -> {imageView2.setScaleX(1); imageView2.setImage(card2.getValue());});

        if (cards.get(random1).getId() == cards.get(random2).getId()) {
            foundCards.add(imageView1);
            foundCards.add(imageView2);
            seenImageViews.remove(imageView1);
            seenImageViews.remove(imageView2);
            seenCards.remove(card1);
            seenCards.remove(card2);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.6)));
            timeline.play();
            timeline.setOnFinished(event -> {
                imageView1.setDisable(true);
                imageView2.setDisable(true);
                imageView1.setOpacity(0.6);
                imageView2.setOpacity(0.6);
            });
        } else {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5)));
            timeline.play();
            timeline.setOnFinished(event -> {
                imageView1.setImage(card1.getBackground());
                imageView2.setImage(card2.getBackground());
                imageView1.setDisable(false);
                imageView2.setDisable(false);
            });

        }

      //  Timeline beat2 = new Timeline(new KeyFrame(Duration.seconds(1.2),event -> {enableAll();}));
      //  beat2.play();
    }

    public void elephant(){
        disableAll();
        boolean flag = false;
        ImageView seenImageView1 = imageViews.get(0);
        ImageView seenImageView2 = imageViews.get(0);
        Card seenCard1 = cards.get(0);
        Card seenCard2 = cards.get(0);

        for(int i =0; i<seenCards.size();i++){
            for(int j = 0; j< seenCards.size();j++){
                if(j!=i && seenCards.get(i).getId() == seenCards.get(j).getId()){
                    seenImageView1 = seenImageViews.get(i);
                    seenImageView2 = seenImageViews.get(j);
                    seenCard1 = seenCards.get(i);
                    seenCard2 = seenCards.get(j);
                    flag = true;
                    break;
                }
            }
        }

        final ImageView i1 = seenImageView1;
        final ImageView i2 = seenImageView2;
        final Card c1 = seenCard1;
        final Card c2 = seenCard2;

        if(flag){
            Timeline beat = new Timeline(new KeyFrame(Duration.seconds(1)));
            beat.play();
            beat.setOnFinished(event -> {
                findAnimation(i1, i2, c1, c2);
                foundCards.add(i1);
                foundCards.add(i2);
            });
           // Timeline beat2 = new Timeline(new KeyFrame(Duration.seconds(2.5),event -> enableAll()));
          //  beat2.play();
            seenImageViews.remove(i1);
            seenImageViews.remove(i2);
            seenCards.remove(c1);
            seenCards.remove(c2);
        }
        else{
            goldfish();
        }
    }

    public void findAnimation(ImageView imageView1,ImageView imageView2,Card card1,Card card2){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5),imageView1);
        scaleTransition.setFromX(1);
        scaleTransition.setToX(-1);
        scaleTransition.play();
        scaleTransition.setOnFinished(event -> {
            imageView1.setScaleX(1);
            imageView1.setImage(card1.getValue());
            imageView1.setDisable(true);
            imageView1.setOpacity(0.6);
        });

        ScaleTransition scaleTransition2 = new ScaleTransition(Duration.seconds(0.5),imageView2);
        scaleTransition2.setFromX(1);
        scaleTransition2.setToX(-1);
        scaleTransition2.play();
        scaleTransition2.setOnFinished(event -> {
            imageView2.setScaleX(1);
            imageView2.setImage(card2.getValue());
            imageView2.setDisable(true);
            imageView2.setOpacity(0.6);
        });
    }

    @Override
    public void disableAll() {
        super.disableAll();
    }

    @Override
    public void enableAll() {
        super.enableAll();
    }

    @Override
    public void backClicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MultiplayerSettings.fxml"));
        Stage stage = (Stage) back.getScene().getWindow();
        stage.getScene().setRoot(root);
    }


}
