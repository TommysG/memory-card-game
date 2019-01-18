package sample;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.*;

/**
 * Η κλάση του Multiplayer
 */
public class Multiplayer extends Game {

    private int clicks,random1,random2,random3,wins1,wins2,wins3;
    @FXML
    private GridPane grid,gridTable;
    @FXML
    private Button back,next;
    @FXML
    private Label turn,nextTurn,player1,player2,player3,player4,winLabel;

    private String t,nt,p1,p2,p3,p4;
    private String pl1,pl2,pl3,pl4,you;
    private String playerTurn1,playerTurn2,playerTurn3,playerTurn4,youWin,botWin;

    private ImageView clickedImageView;

    private int[] scoreBots = new int[3];

    private Properties properties = new Properties();
    private Properties properties2 = new Properties();
    private InputStream input = null;
    private OutputStream output = null;

    private MediaPlayer mediaPlayer;

    /**
     * Ο κατασκευαστής της κλάσης
     */
    public Multiplayer(){
        clicks = 0;
        clickedImageView = null;
        Media buttonSound = new Media(new File("src/Sounds/buttonSound.wav").toURI().toString());
        mediaPlayer = new MediaPlayer(buttonSound);
    }

    /**
     * Φορτώνει τις τιμές απο το αρχείο
     * @throws IOException εάν αποτύχει να φορτώσει το αρχείο
     */
    public void initialize()throws IOException{
        File f =new File("score.properties");
        File f2 =new File("config.properties");

        if(f2.exists()) {
            input = new FileInputStream("config.properties");
            properties.load(input);

            String lang = properties.getProperty("flag");
            loadLang(lang);

        }

        if(f.exists()){
            InputStream input2 = new FileInputStream("score.properties");
            properties2.load(input2);
            wins1 = Integer.parseInt(properties2.getProperty("MultiplayerWins1"));
            wins2 = Integer.parseInt(properties2.getProperty("MultiplayerWins2"));
            wins3 = Integer.parseInt(properties2.getProperty("MultiplayerWins3"));
        }
    }

    /**
     * Θέτει το GameMode και το θέμα του παιχνιδιού.
     * @param gameMode {@code GameMode}
     * @param theme {@code Image} φόντο της κάρτας
     * @throws IOException -
     */
    @Override
    public void setMode(GameMode gameMode, Image theme) throws IOException {
        super.setMode(gameMode, theme);
    }

    /**
     * Διορθώνει την γλώσσα στα Label των παιχτών.
     * @throws IOException -
     */
    public void fixLang() throws IOException{
        playersLang();
    }

    /**
     * Δημιουργεί τα ImageView , τις εικόνες και τις τοποθετεί στο GridPane
     */
    public void multiplayerStart(){
        createImageViews(grid,imageViews);
        createImages(cards);
        shuffleCards(imageViews);
        setImages(imageViews,cards);

        player();
        turn.setText(t + playerTurn1 + you);
        nextTurn.setText(nt + playerTurn2 + "(" + p2 + ")");
    }

    /**
     * O Event Handler για τα ImageView
     */
    @Override
    public void player() {
         super.player();
     }

    /**
     * Καθορίζει τις κινήσεις του χρήστη και γυρίζει ανάλογα τις κάρτες που αυτός θα επιλέξει.
     * @param imageView {@code ImageView} το ImageView που φαίνεται στην οθόνη
     * @param card {@code Card} κάρτα
     */
    @Override
    public void clickEvent(ImageView imageView, Card card) {
        super.clickEvent(imageView,card);
        if(foundCards.size() == gameMode.getSize()) {
            findWinner();
            return;
        }
        player1.setText(pl1+score.getFoundCards());
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

    /**
     * Το μποτάκι GoldFish που σηκώνει δυο τυχαίες κάρτες.
     */
    public void goldfish(){
        if(foundCards.size() == gameMode.getSize()) {
            findWinner();
            return;
        }

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
                botUpdateScore1_2();
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

    /**
     * Το μποτάκι Goldfish που σηκώνεις τρείς τυχαίες κάρτες.
     */
    public void goldfish3(){
        if(foundCards.size() == gameMode.getSize()) {
            findWinner();
            return;
        }

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
                botUpdateScore3();
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

    /**
     * Το μποτάκι Kangaroo που σηκώνει δυο τυχαίες κάρτες.
     */
    public void kangaroo(){
        if(foundCards.size() == gameMode.getSize()) {
            findWinner();
            return;
        }

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
            if(gameMode.getRival1().equals("Kangaroo") && (clicks == 4 || clicks == 0)){
                scoreBots[0]++;
                player2.setText(pl2+scoreBots[0]);
            }
            else if(gameMode.getRival2().equals("Kangaroo") && (clicks == 6 || gameMode.getRivalsNumber() == 2)){
                scoreBots[1]++;
                player3.setText(pl3+scoreBots[1]);
            }
            else if(gameMode.getRival3().equals("Kangaroo") && clicks == 0){
                scoreBots[2]++;
                player4.setText(pl4+scoreBots[2]);
            }
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
            new Timeline(new KeyFrame(Duration.seconds(1),event -> goldfish())).play();
        }
    }

    /**
     * Το μποτάκι Kangaroo που σηκώνει τρείς τυχαίες κάρτες.
     */
    public void kangaroo3(){
        if(foundCards.size() == gameMode.getSize()) {
            findWinner();
            return;
        }

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
            if(gameMode.getRival1().equals("Kangaroo") && (clicks == 6 ||gameMode.getRivalsNumber()==1)){
                scoreBots[0]++;
                player2.setText(pl2+scoreBots[0]);
            }
            else if(gameMode.getRival2().equals("Kangaroo") && (clicks == 9 ||gameMode.getRivalsNumber()==2)){
                scoreBots[1]++;
                player3.setText(pl3+scoreBots[1]);
            }
            else if(gameMode.getRival3().equals("Kangaroo") && clicks == 0){
                scoreBots[2]++;
                player4.setText(pl4+scoreBots[2]);
            }
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
            new Timeline(new KeyFrame(Duration.seconds(1),event -> goldfish3())).play();
        }
    }

    /**
     * Το μποτάκι Elephant που σηκώνει δυο τυχαίες κάρτες.
     */
    public void elephant(){
        if(foundCards.size() == gameMode.getSize()) {
            findWinner();
            return;
        }

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
            if(gameMode.getRival1().equals("Elephant") && (clicks == 4 || gameMode.getRivalsNumber() == 1)){
                scoreBots[0]++;
                player2.setText(pl2+scoreBots[0]);
            }
            else if(gameMode.getRival2().equals("Elephant") && (clicks == 6 || gameMode.getRivalsNumber() == 2)){
                scoreBots[1]++;
                player3.setText(pl3+scoreBots[1]);
            }
            else if(gameMode.getRival3().equals("Elephant") && clicks == 0){
                scoreBots[2]++;
                player4.setText(pl4+scoreBots[2]);
            }
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
            new Timeline(new KeyFrame(Duration.seconds(1),event -> goldfish())).play();
        }
    }

    /**
     * Το μποτάκι Elephant που σηκώνει τρείς τυχαίες κάρτες.
     */
    public void elephant3(){
        if(foundCards.size() == gameMode.getSize()) {
            findWinner();
            return;
        }

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
            if(gameMode.getRival1().equals("Elephant") && (clicks == 6 ||gameMode.getRivalsNumber()==1)){
                scoreBots[0]++;
                player2.setText(pl2+scoreBots[0]);
            }
            else if(gameMode.getRival2().equals("Elephant") && (clicks == 9 ||gameMode.getRivalsNumber()==2)){
                scoreBots[1]++;
                player3.setText(pl3+scoreBots[1]);
            }
            else if(gameMode.getRival3().equals("Elephant") && clicks == 0){
                scoreBots[2]++;
                player4.setText(pl4+scoreBots[2]);
            }
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
                new Timeline(new KeyFrame(Duration.seconds(2.5),event2 -> {
                    elephant3();
                })).play();
            });
        }
        else{
            new Timeline(new KeyFrame(Duration.seconds(1),event -> goldfish3())).play();
        }
    }

    /**
     * Ένα ScaleTransition για το animation, όταν δύο κάρτες βρίσκονται
     */
    private void findAnimation(ImageView imageView1,ImageView imageView2,Card card1,Card card2){
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

    /**
     * Ένα ScaleTransition για το animation, όταν τρείς κάρτες βρίσκονται
     */
    private void findAnimation3(ImageView imageView1,ImageView imageView2,ImageView imageView3,Card card1,Card card2,Card card3){
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

    /**
     * Απενεργοποιεί τις κάρτες οταν το κλίκ του παίχτη είναι ένα
     * @param imageView {@code ImageView}
     */
    private void enable(ImageView imageView){
        super.enableAll();
        imageView.setDisable(true);
    }

    /**
     * O Event handler που πηγαίνει στην προηγούμενη σκηνή
     * @throws IOException εάν αποτύχει να φορτώσει το FXML
     */
    @Override
    public void backClicked() throws IOException {
        mediaPlayer.seek(Duration.ZERO);
        mediaPlayer.setVolume(0.3f);
        mediaPlayer.play();
        clicks = 0;
        Parent root = FXMLLoader.load(getClass().getResource("MultiplayerSettings.fxml"));
        Stage stage = (Stage) back.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    /**
     * Καθορίζει την σειρά των μποτ και ανάλογα αυτή εκτελεί το μποτ που έχει την σειρά, όταν το παιχνίδι παίζεται με δύο κάρτες
     */
    private void multiplayerInitialize1_2(){
        if(foundCards.size() == gameMode.getSize()) {
            System.out.println("STOPPED");

            return;
        }
        if(clicks == 0){
            turn.setText(t + playerTurn1 + you);
            nextTurn.setText(nt + playerTurn2 + "(" + p2 + ")");
            enableAll();
            player();
        }
        else if(clicks == 1)
            enable(clickedImageView);
        else if(clicks == 2){
            turn.setText(t + playerTurn2 + "(" + p2 + ")");
            nextTurn.setText(nt + playerTurn3 + "(" + p3 + ")");
            if(gameMode.getRival1().equals("Goldfish")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(1.5),event1 -> {
                    goldfish();
                }));
                bot.play();
                clicks = clicks +2;
                if(gameMode.getRivalsNumber() == 1){
                    nextTurn.setText(nt + playerTurn1 + you);
                    clicks = 0;
                }
            }
            else if(gameMode.getRival1().equals("Elephant")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(1.5),event1 -> {
                    elephant();
                }));
                bot.play();
                clicks = clicks +2;
                if(gameMode.getRivalsNumber() == 1){
                    nextTurn.setText(nt + playerTurn1 + you);
                    clicks = 0;
                }
            }
            else if(gameMode.getRival1().equals("Kangaroo")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(1.5),event1 -> {kangaroo();}));
                bot.play();
                clicks = clicks +2;
                if(gameMode.getRivalsNumber() == 1){
                    nextTurn.setText(nt + playerTurn1 + you);
                    clicks = 0;
                }
            }
        }
        else if(clicks == 4){
            turn.setText(t + playerTurn3 + "(" + p3 + ")");
            nextTurn.setText(nt + playerTurn4 + "(" + p4 + ")");
            if(gameMode.getRival2().equals("Goldfish")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(1.5),event1 -> {goldfish();}));
                bot.play();
                clicks = clicks +2;
                if(gameMode.getRivalsNumber() == 2){
                    nextTurn.setText(nt + playerTurn1 + you);
                    clicks = 0;
                }
            }
            else if(gameMode.getRival2().equals("Elephant")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(1.5),event1 -> {elephant();}));
                bot.play();
                clicks = clicks +2;
                if(gameMode.getRivalsNumber() == 2){
                    nextTurn.setText(nt + playerTurn1 + you);
                    clicks = 0;
                }
            }
            else if(gameMode.getRival2().equals("Kangaroo")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(1.5),event1 -> {kangaroo();}));
                bot.play();
                clicks = clicks +2;
                if(gameMode.getRivalsNumber() == 2){
                    nextTurn.setText(nt + playerTurn1 + you);
                    clicks = 0;
                }
            }
        }
        else if(clicks == 6){
            turn.setText(t + playerTurn4 + "(" + p4 + ")");
            nextTurn.setText(nt + playerTurn1 + you);
            if(gameMode.getRival3().equals("Goldfish")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(1.5),event1 -> goldfish()));
                bot.play();
                clicks = clicks +2;
                if(gameMode.getRivalsNumber() == 3){
                    clicks = 0;
                }
            }
            else if(gameMode.getRival3().equals("Elephant")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(1.5),event1 -> elephant()));
                bot.play();
                clicks = clicks +2;
                if(gameMode.getRivalsNumber() == 3){
                    clicks = 0;
                }
            }
            else if(gameMode.getRival3().equals("Kangaroo")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(1.5),event1 -> kangaroo()));
                bot.play();
                clicks = clicks +2;
                if(gameMode.getRivalsNumber() == 3){
                    clicks = 0;
                }
            }
        }
    }

    /**
     * Καθορίζει την σειρά των μποτ και ανάλογα αυτή εκτελεί το μποτ που έχει την σειρά, όταν το παιχνίδι παίζεται με τρείς κάρτες
     */
    private void multiplayerInitialize3(){
        if(foundCards.size() == gameMode.getSize()) {
            System.out.println("STOPPED");
            return;
        }

        if(clicks == 0){
            turn.setText(t + playerTurn1 + you);
            nextTurn.setText(nt + playerTurn2 + "(" + p2 + ")");
            enableAll();
            player();
        }
        else if(clicks == 1)
            enable(clickedImageView);
        else if(clicks == 3){
            turn.setText(t + playerTurn2 + "(" + p2 + ")");
            nextTurn.setText(nt + playerTurn3 + "(" + p3 + ")");
            if(gameMode.getRival1().equals("Goldfish")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(1.5),event1 -> {
                    goldfish3();
                }));
                bot.play();
                clicks = clicks +3;
                if(gameMode.getRivalsNumber() == 1){
                    nextTurn.setText(nt + playerTurn1 + you);
                    clicks = 0;
                }
            }
            else if(gameMode.getRival1().equals("Elephant")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(1.5),event1 -> {elephant3();}));
                bot.play();
                clicks = clicks +3;
                if(gameMode.getRivalsNumber() == 1){
                    nextTurn.setText(nt + playerTurn1 + you);
                    clicks = 0;
                }
            }
            else if(gameMode.getRival1().equals("Kangaroo")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(1.5),event1 -> {kangaroo3();}));
                bot.play();
                clicks = clicks +3;
                if(gameMode.getRivalsNumber() == 1){
                    nextTurn.setText(nt + playerTurn1 + you);
                    clicks = 0;
                }
            }
        }
        else if(clicks == 6){
            turn.setText(t + playerTurn3 + "(" + p3 + ")");
            nextTurn.setText(nt + playerTurn4 + "(" + p4 + ")");
            if(gameMode.getRival2().equals("Goldfish")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(1.5),event1 -> {goldfish3();}));
                bot.play();
                clicks = clicks +3;
                if(gameMode.getRivalsNumber() == 2){
                    nextTurn.setText(nt + playerTurn1 + you);
                    clicks = 0;
                }
            }
            else if(gameMode.getRival2().equals("Elephant")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(1.5),event1 -> {elephant3();}));
                bot.play();
                clicks = clicks +3;
                if(gameMode.getRivalsNumber() == 2){
                    nextTurn.setText(nt + playerTurn1 + you);
                    clicks = 0;
                }
            }
            else if(gameMode.getRival2().equals("Kangaroo")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(1.5),event1 -> {kangaroo3();}));
                bot.play();
                clicks = clicks +3;
                if(gameMode.getRivalsNumber() == 2){
                    nextTurn.setText(nt + playerTurn1 + you);
                    clicks = 0;
                }
            }
        }
        else if(clicks == 9){
            turn.setText(t + playerTurn4 + "(" + p4 + ")");
            nextTurn.setText(nt + playerTurn1 + you);
            if(gameMode.getRival3().equals("Goldfish")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(1.5),event1 -> goldfish3()));
                bot.play();
                clicks = clicks +3;
                if(gameMode.getRivalsNumber() == 3){
                    clicks = 0;
                }
            }
            else if(gameMode.getRival3().equals("Elephant")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(1.5),event1 -> elephant3()));
                bot.play();
                clicks = clicks +3;
                if(gameMode.getRivalsNumber() == 3){
                    clicks = 0;
                }
            }
            else if(gameMode.getRival3().equals("Kangaroo")){
                Timeline bot = new Timeline(new KeyFrame(Duration.seconds(1.5),event1 -> kangaroo3()));
                bot.play();
                clicks = clicks +3;
                if(gameMode.getRivalsNumber() == 3){
                    clicks = 0;
                }
            }
        }
    }

    /**
     * Αυξάνει το σκόρ όταν παίζουν τα μποτάκια με δύο κάρτες, αυξάνοντας όμως και το σκόρ αυτών οταν δεν θυμούνται την κάρτα και παίζουν σαν Goldfish
     */
    private void botUpdateScore1_2(){
        if(gameMode.getRival1().equals("Goldfish") && (clicks == 4 || gameMode.getRivalsNumber() == 1)){
            scoreBots[0]++;
            player2.setText(pl2+scoreBots[0]);
        }
        else if(gameMode.getRival2().equals("Goldfish") && (clicks == 6 || gameMode.getRivalsNumber() ==2)){
            scoreBots[1]++;
            player3.setText(pl3+scoreBots[1]);
        }
        else if(gameMode.getRival3().equals("Goldfish") && clicks == 0){
            scoreBots[2]++;
            player4.setText(pl4+scoreBots[2]);
        }
        else if(gameMode.getRival1().equals("Kangaroo") && (clicks == 4 || gameMode.getRivalsNumber() == 1)){
            scoreBots[0]++;
            player2.setText(pl2+scoreBots[0]);
        }
        else if(gameMode.getRival2().equals("Kangaroo") && (clicks == 6 || gameMode.getRivalsNumber() ==2)){
            scoreBots[1]++;
            player3.setText(pl3+scoreBots[1]);
        }
        else if(gameMode.getRival3().equals("Kangaroo") && clicks == 0){
            scoreBots[2]++;
            player4.setText(pl4+scoreBots[2]);
        }
        else if(gameMode.getRival1().equals("Elephant") && (clicks == 4 || gameMode.getRivalsNumber() == 1)){
            scoreBots[0]++;
            player2.setText(pl2+scoreBots[0]);
        }
        else if(gameMode.getRival2().equals("Elephant") && (clicks == 6 || gameMode.getRivalsNumber() ==2)){
            scoreBots[1]++;
            player3.setText(pl3+scoreBots[1]);
        }
        else if(gameMode.getRival3().equals("Elephant") && clicks == 0){
            scoreBots[2]++;
            player4.setText(pl4+scoreBots[2]);
        }
    }
    /**
     * Αυξάνει το σκόρ όταν παίζουν τα μποτάκια με τρείς κάρτες, αυξάνοντας όμως και το σκόρ αυτών οταν δεν θυμούνται την κάρτα και παίζουν σαν Goldfish
     */
    private void botUpdateScore3(){
        if(gameMode.getRival1().equals("Goldfish") && (clicks == 6 || gameMode.getRivalsNumber()==1)){
            scoreBots[0]++;
            player2.setText(pl2+scoreBots[0]);
        }
        else if(gameMode.getRival2().equals("Goldfish") && (clicks == 9 ||gameMode.getRivalsNumber()==2)){
            scoreBots[1]++;
            player3.setText(pl3+scoreBots[1]);
        }
        else if(gameMode.getRival3().equals("Goldfish") && clicks == 0){
            scoreBots[2]++;
            player4.setText(pl4+scoreBots[2]);
        }
        else if(gameMode.getRival1().equals("Kangaroo") && (clicks == 6 || gameMode.getRivalsNumber()==1)){
            scoreBots[0]++;
            player2.setText(pl2+scoreBots[0]);
        }
        else if(gameMode.getRival2().equals("Kangaroo") && (clicks == 9||gameMode.getRivalsNumber()==2)){
            scoreBots[1]++;
            player3.setText(pl3+scoreBots[1]);
        }
        else if(gameMode.getRival3().equals("Kangaroo") && clicks == 0){
            scoreBots[2]++;
            player4.setText(pl4+scoreBots[2]);
        }
        else if(gameMode.getRival1().equals("Elephant") && (clicks == 6 || gameMode.getRivalsNumber()==1)){
            scoreBots[0]++;
            player2.setText(pl2+scoreBots[0]);
        }
        else if(gameMode.getRival2().equals("Elephant") && (clicks == 9 || gameMode.getRivalsNumber()==2)){
            scoreBots[1]++;
            player3.setText(pl3+scoreBots[1]);
        }
        else if(gameMode.getRival3().equals("Elephant") && clicks == 0){
            scoreBots[2]++;
            player4.setText(pl4+scoreBots[2]);
        }
    }

    /**
     * Ο Event Handler που ελέγχει ποιανού σειρά είναι και εκτελεί τις κατάλληλες μεθόδους.
     */
    @FXML
    private void nextClicked(){
        if(gameMode.getMode()!=3)
            multiplayerInitialize1_2();
        else
            multiplayerInitialize3();
    }

    /**
     * Ελέγχει ποιος έχει νικήσει και αντίστοιχα εμφανίζει στην οθόνη
     */
    private void findWinner(){
        int count = 0;
        boolean playerWon = false;

        for(int i = 0;i<scoreBots.length;i++){
            if(score.getFoundCards()> scoreBots[i]){
                count++;
            }
        }
        if(count == 3){
            playerWon = true;
            if(gameMode.getMode() == 1){
                wins1++;
                properties2.setProperty("MultiplayerWins1",Integer.toString(wins1));
            }
            else if(gameMode.getMode() == 2){
                wins2++;
                properties2.setProperty("MultiplayerWins2",Integer.toString(wins2));
            }
            else if(gameMode.getMode() == 3){
                wins3++;
                properties2.setProperty("MultiplayerWins3",Integer.toString(wins3));
            }
            try {
                output = new FileOutputStream("score.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                properties2.store(output,null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            winLabel.setText(youWin);
        }
        else{
            int max = scoreBots[0],pos = 0;
            for(int i = 0;i<scoreBots.length;i++){
                if(max<scoreBots[i]){
                    pos = i;
                    max = scoreBots[i];
                }
            }
            if(pos == 0){
                winLabel.setText(playerTurn2 +" " +botWin);
            }
            else if(pos ==1){
                winLabel.setText(playerTurn3 +" "+ botWin);
            }
            else if(pos == 2){
                winLabel.setText(playerTurn4 +" "+ botWin);
            }
        }

    }

    /**
     * Φορτώνει την γλώσσα του FXML.
     * @param lang {@code String}
     */
    private void loadLang(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("sample.lang", locale);

        next.setText(bundle.getString("next"));

        turn.setText(bundle.getString("turn"));
        t = (bundle.getString("turn"));
        nextTurn.setText(bundle.getString("nextTurn"));
        nt = bundle.getString("nextTurn");
        player1.setText(bundle.getString("player1") + "0");
        player2.setText(bundle.getString("player2")+ "0");
        player3.setText(bundle.getString("player3")+ "0");
        player4.setText(bundle.getString("player4")+ "0");


        pl1 = bundle.getString("player1");
        pl2 = bundle.getString("player2");
        pl3 = bundle.getString("player3");
        pl4 = bundle.getString("player4");

        playerTurn1 = bundle.getString("player1T");
        playerTurn2 = bundle.getString("player2T");
        playerTurn3 = bundle.getString("player3T");
        playerTurn4 = bundle.getString("player4T");

        you = bundle.getString("you");
        youWin = bundle.getString("win");
        botWin = bundle.getString("botWin");

    }

    /**
     * Διορθώνει την γλώσσα των παιχτών στα Labels
     * @throws IOException εάν αποτύχει να φορτώσει το αρχείο.
     */
    private void playersLang() throws IOException{
        File f2 =new File("config.properties");

        if(f2.exists()) {
            input = new FileInputStream("config.properties");
            properties.load(input);

            String lang = properties.getProperty("flag");
            loadLang(lang);

            if (lang.equals("el")) {
                if(gameMode.getRival1().equals("Goldfish")){
                    p2 = "Χρυσόψαρο";
                }
                else if(gameMode.getRival1().equals("Kangaroo")){
                    p2 = "Καγκουρό";
                }
                else if(gameMode.getRival1().equals("Elephant")){
                    p2 = "Ελέφαντας";
                }
                if(gameMode.getRival2().equals("Goldfish")){
                    p3 = "Χρυσόψαρο";
                }
                else if(gameMode.getRival2().equals("Kangaroo")){
                    p3 = "Καγκουρό";
                }
                else if(gameMode.getRival2().equals("Elephant")){
                    p3 = "Ελέφαντας";
                }
                if(gameMode.getRival3().equals("Goldfish")){
                    p4 = "Χρυσόψαρο";
                }
                else if(gameMode.getRival3().equals("Kangaroo")){
                    p4 = "Καγκουρό";
                }
                else if(gameMode.getRival3().equals("Elephant")){
                    p4 = "Ελέφαντας";
                }
            } else if (lang.equals("en")) {
                if(gameMode.getRival1().equals("Goldfish")){
                    p2 = "Goldfish";
                }
                else if(gameMode.getRival1().equals("Kangaroo")){
                    p2 = "Kangaroo";
                }
                else if(gameMode.getRival1().equals("Elephant")){
                    p2 = "Elephant";
                }
                if(gameMode.getRival2().equals("Goldfish")){
                    p3 = "Goldfish";
                }
                else if(gameMode.getRival2().equals("Kangaroo")){
                    p3 = "Kangaroo";
                }
                else if(gameMode.getRival2().equals("Elephant")){
                    p3 = "Elephant";
                }
                if(gameMode.getRival3().equals("Goldfish")){
                    p4 = "Goldfish";
                }
                else if(gameMode.getRival3().equals("Kangaroo")){
                    p4 = "Kangaroo";
                }
                else if(gameMode.getRival3().equals("Elephant")){
                    p4 = "Elephant";
                }
            }
        }


    }


}
