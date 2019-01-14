package sample;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class Multiplayer extends Game {

    private int clicks,random1,random2,random3;
    @FXML
    private GridPane grid,gridTable;
    @FXML
    private Button back;
    private Timeline multiInitialize;
    @FXML
    private Label turn,nextTurn,player1,player2,player3,player4;

    private ImageView clickedImageView;

    private final Object PAUSE_KEY = new Object();

    public Multiplayer(){
        multiInitialize = new Timeline();
        clicks = 0;
        clickedImageView = null;
    }

    @Override
    public void setMode(GameMode gameMode, Image theme) throws IOException {
        super.setMode(gameMode, theme);

    }

    public void multiplayerStart(){
        createImageViews(grid,imageViews);
        createImages(cards);
        // shuffleCards();
        setImages(imageViews,cards);

        player();
        turn.setText("Turn: Player1(You)");
        nextTurn.setText("Next Turn: Player 2"+"("+gameMode.getRival1()+")");
    }

    @Override
    public void player() {
         super.player();

     }

    public void disablePlayer(){
        for(int i = 0; i<imageViews.size();i++){
            imageViews.get(i).setOnMouseClicked(null);
        }
    }

    @Override
    public void clickEvent(ImageView imageView, Card card) {
        super.clickEvent(imageView,card);
        clickedImageView = imageView;
        clicks++;

        if(gameMode.getMode()!= 3) {
            if (clicks == 2 && cardsMatch) {
                clicks = 0;
                // enableAll();
            }
            if (clicks == 2 && foundCards.size() == gameMode.getSize())
                return;

            if (gameMode.getRivalsNumber() == 1 && clicks == 4) {
                clicks = 0;
            }
            if (gameMode.getRivalsNumber() == 2 && clicks == 6) {
                clicks = 0;
            }
            if (gameMode.getRivalsNumber() == 3 && clicks == 8) {
                clicks = 0;
            }

            if (clicks == 0) {
                Timeline playAgain = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> enableAll()));
                playAgain.play();
            }
        }
        else{
            System.out.println(clicks);
            if (clicks == 3 && cardsMatch) {
                clicks = 0;
                // enableAll();
            }
            if (clicks == 3 && foundCards.size() == gameMode.getSize())
                return;

            if (gameMode.getRivalsNumber() == 1 && clicks == 6) {
                clicks = 0;
            }
            if (gameMode.getRivalsNumber() == 2 && clicks == 9) {
                clicks = 0;
            }
            if (gameMode.getRivalsNumber() == 3 && clicks == 12) {
                clicks = 0;
            }


            if (clicks == 0) {
                Timeline playAgain = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> enableAll()));
                playAgain.play();
            }
        }

    }

    public void goldfish(){
        if(foundCards.size() == gameMode.getSize())
            return;
        disableAll();
        Random random = new Random();
        random1 = random.nextInt(imageViews.size());
        random2 = random.nextInt(imageViews.size());

        while ((foundCards.contains(imageViews.get(random1)) || foundCards.contains(imageViews.get(random2)) || random1 == random2) && foundCards.size() != gameMode.getSize()) {
            random1 = random.nextInt(imageViews.size());
            random2 = random.nextInt(imageViews.size());
        }


        final ImageView imageView1 = imageViews.get(random1);
        final ImageView imageView2 = imageViews.get(random2);
        final Card card1 = cards.get(random1);
        final Card card2 = cards.get(random2);

        if(!seenImageViewsElephant.contains(imageView1)){
            seenImageViewsElephant.add(imageView1);
            seenCardsElephant.add(card1);
        }

        if(!seenImageViewsElephant.contains(imageView2)){
            seenImageViewsElephant.add(imageView2);
            seenCardsElephant.add(card2);
        }

        if(!seenImageViewsKangaroo.contains(imageView1)){
            seenImageViewsKangaroo.add(imageView1);
            seenCardsKangaroo.add(card1);
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
                seenImageViewsElephant.remove(imageView1);
                seenImageViewsElephant.remove(imageView2);
                seenCardsElephant.remove(card1);
                seenCardsElephant.remove(card2);
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.6), event -> {
                    imageView1.setDisable(true);
                    imageView2.setDisable(true);
                    imageView1.setOpacity(0.6);
                    imageView2.setOpacity(0.6);
                }));
                timeline.play();
                timeline.setOnFinished(event -> {
                    new Timeline(new KeyFrame(Duration.seconds(3), event1 -> goldfish())).play();
                });
            } else {
                new Timeline(new KeyFrame(Duration.seconds(1.5), event -> {
                    imageView1.setImage(card1.getBackground());
                    imageView2.setImage(card2.getBackground());
                    // imageView1.setDisable(false);
                    // imageView2.setDisable(false);
                })).play();
            }

    }

    public void goldfish3(){
        if(foundCards.size() == gameMode.getSize())
            return;
        disableAll();
        Random random = new Random();
        random1 = random.nextInt(imageViews.size());
        random2 = random.nextInt(imageViews.size());
        random3 = random.nextInt(imageViews.size());


        while (((foundCards.contains(imageViews.get(random1)) || foundCards.contains(imageViews.get(random2)) || foundCards.contains(imageViews.get(random3)) || random1 == random2 || random2 == random3 || random1 == random3)) && foundCards.size() != gameMode.getSize()) {
                random1 = random.nextInt(imageViews.size());
                random2 = random.nextInt(imageViews.size());
                random3 = random.nextInt(imageViews.size());
        }

        final ImageView imageView1 = imageViews.get(random1);
        final ImageView imageView2 = imageViews.get(random2);
        final ImageView imageView3 = imageViews.get(random3);
        final Card card1 = cards.get(random1);
        final Card card2 = cards.get(random2);
        final Card card3 = cards.get(random3);

        if(!seenImageViewsElephant.contains(imageView1)){
            seenImageViewsElephant.add(imageView1);
            seenCardsElephant.add(card1);
        }

        if(!seenImageViewsElephant.contains(imageView2)){
            seenImageViewsElephant.add(imageView2);
            seenCardsElephant.add(card2);
        }

        if(!seenImageViewsElephant.contains(imageView3)){
            seenImageViewsElephant.add(imageView3);
            seenCardsElephant.add(card3);
        }

        if(!seenImageViewsKangaroo.contains(imageView1)){
            seenImageViewsKangaroo.add(imageView1);
            seenCardsKangaroo.add(card1);
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

        ScaleTransition scale3 = new ScaleTransition(Duration.seconds(0.5),imageView3);
        scale3.setFromX(1);
        scale3.setToX(-1);
        scale3.play();
        scale3.setOnFinished(event -> {imageView3.setScaleX(1); imageView3.setImage(card3.getValue());});

        if (cards.get(random1).getId() == cards.get(random2).getId() && cards.get(random2).getId() == cards.get(random3).getId()) {
                foundCards.add(imageView1);
                foundCards.add(imageView2);
                foundCards.add(imageView3);
                seenImageViewsElephant.remove(imageView1);
                seenImageViewsElephant.remove(imageView2);
                seenImageViewsElephant.remove(imageView3);
                seenCardsElephant.remove(card1);
                seenCardsElephant.remove(card2);
                seenCardsElephant.remove(card3);
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.6), event -> {
                    imageView1.setDisable(true);
                    imageView2.setDisable(true);
                    imageView3.setDisable(true);
                    imageView1.setOpacity(0.6);
                    imageView2.setOpacity(0.6);
                    imageView3.setOpacity(0.6);
                }));
                timeline.play();
                timeline.setOnFinished(event -> {
                    new Timeline(new KeyFrame(Duration.seconds(3), event1 -> goldfish3())).play();
                });
            } else {
                new Timeline(new KeyFrame(Duration.seconds(1.5), event -> {
                    imageView1.setImage(card1.getBackground());
                    imageView2.setImage(card2.getBackground());
                    imageView3.setImage(card3.getBackground());
                    // imageView1.setDisable(false);
                    // imageView2.setDisable(false);
                })).play();
            }
    }

    public void kangaroo(){
        if(foundCards.size() == gameMode.getSize())
            return;

        disableAll();
        boolean flag = false;
        ImageView seenImageView1 = imageViews.get(0);
        ImageView seenImageView2 = imageViews.get(0);
        Card seenCard1 = cards.get(0);
        Card seenCard2 = cards.get(0);

        for(int i =0; i<seenCardsKangaroo.size();i++){
            for(int j = 0; j< seenCardsKangaroo.size();j++){
                if(j!=i && seenCardsKangaroo.get(i).getId() == seenCardsKangaroo.get(j).getId()){
                    seenImageView1 = seenImageViewsKangaroo.get(i);
                    seenImageView2 = seenImageViewsKangaroo.get(j);
                    seenCard1 = seenCardsKangaroo.get(i);
                    seenCard2 = seenCardsKangaroo.get(j);
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
            seenImageViewsElephant.remove(i1);
            seenImageViewsElephant.remove(i2);
            seenCardsElephant.remove(c1);
            seenCardsElephant.remove(c2);
            seenImageViewsKangaroo.remove(i1);
            seenImageViewsKangaroo.remove(i2);
            seenCardsKangaroo.remove(c1);
            seenCardsKangaroo.remove(c2);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.6),event -> {
                findAnimation(i1, i2, c1, c2);
                foundCards.add(i1);
                foundCards.add(i2);
            }));
            timeline.play();
            timeline.setOnFinished(event -> {
                new Timeline(new KeyFrame(Duration.seconds(2.5),event2 -> {
                    kangaroo();
                })).play();
            });
        }
        else{
            new Timeline(new KeyFrame(Duration.seconds(2.5),event -> goldfish())).play();
        }
    }

    public void kangaroo3(){
        if(foundCards.size() == gameMode.getSize())
            return;

        disableAll();
        boolean flag = false;
        ImageView seenImageView1 = imageViews.get(0);
        ImageView seenImageView2 = imageViews.get(0);
        ImageView seenImageView3 = imageViews.get(0);
        Card seenCard1 = cards.get(0);
        Card seenCard2 = cards.get(0);
        Card seenCard3 = cards.get(0);

        for(int i =0; i<seenCardsKangaroo.size();i++){
            for(int j = i+1; j< seenCardsKangaroo.size();j++){
                for(int z = j+1;z<seenCardsKangaroo.size();z++) {
                    if (seenCardsKangaroo.get(i).getId() == seenCardsKangaroo.get(j).getId() && seenCardsKangaroo.get(i).getId() == seenCardsKangaroo.get(z).getId() ) {
                        seenImageView1 = seenImageViewsKangaroo.get(i);
                        seenImageView2 = seenImageViewsKangaroo.get(j);
                        seenImageView3 = seenImageViewsKangaroo.get(z);
                        seenCard1 = seenCardsKangaroo.get(i);
                        seenCard2 = seenCardsKangaroo.get(j);
                        seenCard3 = seenCardsKangaroo.get(z);
                        flag = true;
                        break;
                    }
                }
            }
        }

        final ImageView i1 = seenImageView1;
        final ImageView i2 = seenImageView2;
        final ImageView i3 = seenImageView3;
        final Card c1 = seenCard1;
        final Card c2 = seenCard2;
        final Card c3 = seenCard3;

        if(flag){
            seenImageViewsElephant.remove(i1);
            seenImageViewsElephant.remove(i2);
            seenImageViewsElephant.remove(i3);
            seenCardsElephant.remove(c1);
            seenCardsElephant.remove(c2);
            seenCardsElephant.remove(c3);
            seenImageViewsKangaroo.remove(i1);
            seenImageViewsKangaroo.remove(i2);
            seenImageViewsKangaroo.remove(i3);
            seenCardsKangaroo.remove(c1);
            seenCardsKangaroo.remove(c2);
            seenCardsKangaroo.remove(c3);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.6),event -> {
                findAnimation3(i1, i2,i3, c1, c2,c3);
                foundCards.add(i1);
                foundCards.add(i2);
                foundCards.add(i3);
            }));
            timeline.play();
            timeline.setOnFinished(event -> {
                new Timeline(new KeyFrame(Duration.seconds(2.5),event2 -> {
                    kangaroo3();
                })).play();
            });
        }
        else{
            new Timeline(new KeyFrame(Duration.seconds(2.5),event -> goldfish3())).play();
        }
    }

    public void elephant(){
        if(foundCards.size() == gameMode.getSize())
            return;

        disableAll();
        boolean flag = false;
        ImageView seenImageView1 = imageViews.get(0);
        ImageView seenImageView2 = imageViews.get(0);
        Card seenCard1 = cards.get(0);
        Card seenCard2 = cards.get(0);

        for(int i =0; i<seenCardsElephant.size();i++){
            for(int j = 0; j< seenCardsElephant.size();j++){
                if(j!=i && seenCardsElephant.get(i).getId() == seenCardsElephant.get(j).getId()){
                    seenImageView1 = seenImageViewsElephant.get(i);
                    seenImageView2 = seenImageViewsElephant.get(j);
                    seenCard1 = seenCardsElephant.get(i);
                    seenCard2 = seenCardsElephant.get(j);
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
            seenImageViewsElephant.remove(i1);
            seenImageViewsElephant.remove(i2);
            seenCardsElephant.remove(c1);
            seenCardsElephant.remove(c2);
            seenImageViewsKangaroo.remove(i1);
            seenCardsKangaroo.remove(c1);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.6),event -> {
                findAnimation(i1, i2, c1, c2);
                foundCards.add(i1);
                foundCards.add(i2);
            }));
            timeline.play();
            timeline.setOnFinished(event -> {
                new Timeline(new KeyFrame(Duration.seconds(2.5),event2 -> {
                    elephant();
                })).play();
            });
        }
        else{
            new Timeline(new KeyFrame(Duration.seconds(2.5),event -> goldfish())).play();
        }
    }

    public void elephant3(){
        if(foundCards.size() == gameMode.getSize())
            return;

        disableAll();
        boolean flag = false;
        ImageView seenImageView1 = imageViews.get(0);
        ImageView seenImageView2 = imageViews.get(0);
        ImageView seenImageView3 = imageViews.get(0);
        Card seenCard1 = cards.get(0);
        Card seenCard2 = cards.get(0);
        Card seenCard3 = cards.get(0);
        for(int i =0; i<seenCardsElephant.size();i++){
            for(int j = i+1; j< seenCardsElephant.size();j++){
                for(int z = j+1;z<seenCardsElephant.size();z++) {
                    if (seenCardsElephant.get(i).getId() == seenCardsElephant.get(j).getId() && seenCardsElephant.get(i).getId() == seenCardsElephant.get(z).getId() ) {
                        seenImageView1 = seenImageViewsElephant.get(i);
                        seenImageView2 = seenImageViewsElephant.get(j);
                        seenImageView3 = seenImageViewsElephant.get(z);
                        seenCard1 = seenCardsElephant.get(i);
                        seenCard2 = seenCardsElephant.get(j);
                        seenCard3 = seenCardsElephant.get(z);
                        flag = true;
                        break;
                    }
                }
            }
        }

        final ImageView i1 = seenImageView1;
        final ImageView i2 = seenImageView2;
        final ImageView i3 = seenImageView3;
        final Card c1 = seenCard1;
        final Card c2 = seenCard2;
        final Card c3 = seenCard3;

        if(flag){
            seenImageViewsElephant.remove(i1);
            seenImageViewsElephant.remove(i2);
            seenImageViewsElephant.remove(i3);
            seenCardsElephant.remove(c1);
            seenCardsElephant.remove(c2);
            seenCardsElephant.remove(c3);
            seenImageViewsKangaroo.remove(i1);
            seenCardsKangaroo.remove(c1);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.6),event -> {
                findAnimation3(i1, i2,i3, c1, c2,c3);
                foundCards.add(i1);
                foundCards.add(i2);
                foundCards.add(i3);
            }));
            timeline.play();
            timeline.setOnFinished(event -> {
                new Timeline(new KeyFrame(Duration.seconds(3),event2 -> {
                    elephant3();
                })).play();
            });
        }
        else{
            new Timeline(new KeyFrame(Duration.seconds(3),event -> goldfish3())).play();
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

    public void findAnimation3(ImageView imageView1,ImageView imageView2,ImageView imageView3,Card card1,Card card2,Card card3){
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

        ScaleTransition scaleTransition3 = new ScaleTransition(Duration.seconds(0.5),imageView3);
        scaleTransition3.setFromX(1);
        scaleTransition3.setToX(-1);
        scaleTransition3.play();
        scaleTransition3.setOnFinished(event -> {
            imageView3.setScaleX(1);
            imageView3.setImage(card3.getValue());
            imageView3.setDisable(true);
            imageView3.setOpacity(0.6);
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

    public void enable(ImageView imageView){
        super.enableAll();
        imageView.setDisable(true);
    }

    private void pause() {
        Platform.enterNestedEventLoop(PAUSE_KEY);
    }

    private void resume() {
        Platform.exitNestedEventLoop(PAUSE_KEY, null);
    }

    @Override
    public void backClicked() throws IOException {
        clicks = 0;
        multiInitialize.stop();
        Parent root = FXMLLoader.load(getClass().getResource("MultiplayerSettings.fxml"));
        Stage stage = (Stage) back.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    private void multiplayerInitialize1_2(){
        if(foundCards.size() == gameMode.getSize()) {
            System.out.println("STOPPED");
            multiInitialize.stop();
            return;
        }

        if(clicks == 0){
            turn.setText("Turn: Player1(You)");
            nextTurn.setText("Next Turn: Player 2"+"("+gameMode.getRival1()+")");
            enableAll();
            player();
        }
        else if(clicks == 1)
            enable(clickedImageView);
        else if(clicks == 2){
            turn.setText("Turn: Player2"+"("+gameMode.getRival1()+")");
            nextTurn.setText("Next Turn: Player3"+"("+gameMode.getRival2()+")");
            if(gameMode.getRival1().equals("Goldfish")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(1.5),event1 -> {
                    goldfish();
                }));
                bot.play();
                clicks = clicks +2;
                if(gameMode.getRivalsNumber() == 1){
                    nextTurn.setText("Next Turn: Player1(You)");
                    clicks = 0;
                }
            }
            else if(gameMode.getRival1().equals("Elephant")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(2),event1 -> {elephant();}));
                bot.play();
                clicks = clicks +2;
                if(gameMode.getRivalsNumber() == 1){
                    nextTurn.setText("Next Turn: Player1(You)");
                    clicks = 0;
                }
            }
            else if(gameMode.getRival1().equals("Kangaroo")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(2),event1 -> {kangaroo();}));
                bot.play();
                clicks = clicks +2;
                if(gameMode.getRivalsNumber() == 1){
                    nextTurn.setText("Next Turn: Player1(You)");
                    clicks = 0;
                }
            }
        }
        else if(clicks == 4){
            turn.setText("Turn: Player3"+"("+gameMode.getRival2()+")");
            nextTurn.setText("Next Turn: Player4"+"("+gameMode.getRival3()+")");
            if(gameMode.getRival2().equals("Goldfish")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(2),event1 -> {goldfish();}));
                bot.play();
                clicks = clicks +2;
                if(gameMode.getRivalsNumber() == 2){
                    nextTurn.setText("Next Turn: Player1(You)");
                    clicks = 0;
                }
            }
            else if(gameMode.getRival2().equals("Elephant")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(2),event1 -> {elephant();}));
                bot.play();
                clicks = clicks +2;
                if(gameMode.getRivalsNumber() == 2){
                    nextTurn.setText("Next Turn: Player1(You)");
                    clicks = 0;
                }
            }
            else if(gameMode.getRival2().equals("Kangaroo")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(2),event1 -> {kangaroo();}));
                bot.play();
                clicks = clicks +2;
                if(gameMode.getRivalsNumber() == 2){
                    nextTurn.setText("Next Turn: Player1(You)");
                    clicks = 0;
                }
            }
        }
        else if(clicks == 6){
            turn.setText("Turn: Player4"+"("+gameMode.getRival3()+")");
            nextTurn.setText("Next Turn: Player1(You)");
            if(gameMode.getRival3().equals("Goldfish")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(2),event1 -> goldfish()));
                bot.play();
                clicks = clicks +2;
                if(gameMode.getRivalsNumber() == 3){
                    clicks = 0;
                }
            }
            else if(gameMode.getRival3().equals("Elephant")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(2),event1 -> elephant()));
                bot.play();
                clicks = clicks +2;
                if(gameMode.getRivalsNumber() == 3){
                    clicks = 0;
                }
            }
            else if(gameMode.getRival3().equals("Kangaroo")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(2),event1 -> kangaroo()));
                bot.play();
                clicks = clicks +2;
                if(gameMode.getRivalsNumber() == 3){
                    clicks = 0;
                }
            }
        }
    }

    private void multiplayerInitialize3(){
        if(foundCards.size() == gameMode.getSize()) {
            System.out.println("STOPPED");
            multiInitialize.stop();
            return;
        }

        if(clicks == 0){
            turn.setText("Turn: Player1(You)");
            nextTurn.setText("Next Turn: Player 2"+"("+gameMode.getRival1()+")");
            enableAll();
            player();
        }
        else if(clicks == 1)
            enable(clickedImageView);
        else if(clicks == 3){
            turn.setText("Turn: Player2"+"("+gameMode.getRival1()+")");
            nextTurn.setText("Next Turn: Player3"+"("+gameMode.getRival2()+")");
            if(gameMode.getRival1().equals("Goldfish")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(1.5),event1 -> {
                    goldfish3();
                }));
                bot.play();
                clicks = clicks +3;
                if(gameMode.getRivalsNumber() == 1){
                    nextTurn.setText("Next Turn: Player1(You)");
                    clicks = 0;
                }
            }
            else if(gameMode.getRival2().equals("Elephant")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(2),event1 -> {elephant3();}));
                bot.play();
                clicks = clicks +3;
                if(gameMode.getRivalsNumber() == 1){
                    nextTurn.setText("Next Turn: Player1(You)");
                    clicks = 0;
                }
            }
            else if(gameMode.getRival1().equals("Kangaroo")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(2),event1 -> {kangaroo3();}));
                bot.play();
                clicks = clicks +3;
                if(gameMode.getRivalsNumber() == 1){
                    nextTurn.setText("Next Turn: Player1(You)");
                    clicks = 0;
                }
            }
        }
        else if(clicks == 6){
            turn.setText("Turn: Player3"+"("+gameMode.getRival2()+")");
            nextTurn.setText("Next Turn: Player4"+"("+gameMode.getRival3()+")");
            if(gameMode.getRival2().equals("Goldfish")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(2),event1 -> {goldfish3();}));
                bot.play();
                clicks = clicks +3;
                if(gameMode.getRivalsNumber() == 2){
                    nextTurn.setText("Next Turn: Player1(You)");
                    clicks = 0;
                }
            }
            else if(gameMode.getRival2().equals("Elephant")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(2),event1 -> {elephant3();}));
                bot.play();
                clicks = clicks +3;
                if(gameMode.getRivalsNumber() == 2){
                    nextTurn.setText("Next Turn: Player1(You)");
                    clicks = 0;
                }
            }
            else if(gameMode.getRival2().equals("Kangaroo")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(2),event1 -> {kangaroo3();}));
                bot.play();
                clicks = clicks +3;
                if(gameMode.getRivalsNumber() == 2){
                    nextTurn.setText("Next Turn: Player1(You)");
                    clicks = 0;
                }
            }
        }
        else if(clicks == 9){
            turn.setText("Turn: Player4"+"("+gameMode.getRival3()+")");
            nextTurn.setText("Next Turn: Player1(You)");
            if(gameMode.getRival3().equals("Goldfish")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(2),event1 -> goldfish3()));
                bot.play();
                clicks = clicks +3;
                if(gameMode.getRivalsNumber() == 3){
                    clicks = 0;
                }
            }
            else if(gameMode.getRival3().equals("Elephant")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(2),event1 -> elephant3()));
                bot.play();
                clicks = clicks +3;
                if(gameMode.getRivalsNumber() == 3){
                    clicks = 0;
                }
            }
            else if(gameMode.getRival3().equals("Kangaroo")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(2),event1 -> kangaroo3()));
                bot.play();
                clicks = clicks +3;
                if(gameMode.getRivalsNumber() == 3){
                    clicks = 0;
                }
            }
        }
    }

    public void nextClicked(){

        if(gameMode.getMode()!=3)
            multiplayerInitialize1_2();
        else
            multiplayerInitialize3();


    }


}
