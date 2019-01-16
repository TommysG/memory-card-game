package sample;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

public class  Game {

    @FXML
    private Button back;
    public GameMode gameMode;
    @FXML
    private AnchorPane Game;
    public ArrayList<ImageView> imageViews,foundCards,seenImageViewsElephant,seenImageViewsKangaroo;
    public ArrayList<Card> cards,seenCardsElephant,seenCardsKangaroo;

    private Image theme;
    private int clicks = 0,moves1,moves2,moves3;
    public int id1,id2,id3;

    public ImageView imageView1,imageView2,imageView3;
    public Card card1,card2,card3;

    private Properties properties = new Properties();
    private InputStream input = null;
    private OutputStream output = null;

    @FXML
    private GridPane grid;
    public Score score;
    @FXML
    private Label Moves,foundCardsLabel;

    public Boolean cardsMatch;


    public Game(){
        gameMode = new GameMode();
        imageViews = new ArrayList<>();
        cards = new ArrayList<>();
        foundCards = new ArrayList<>();
        seenImageViewsElephant = new ArrayList<>();
        seenCardsElephant = new ArrayList<>();
        seenImageViewsKangaroo = new ArrayList<>();
        seenCardsKangaroo = new ArrayList<>();
        score= new Score();
        cardsMatch = false;
    }

    public void initialize() throws IOException{
        File f2 =new File("score.properties");

        if(f2.exists()){
            input = new FileInputStream("score.properties");
            properties.load(input);

            moves1 = Integer.parseInt(properties.getProperty("SingleModeHighScore1"));
            moves2 = Integer.parseInt(properties.getProperty("SingleModeHighScore2"));
            moves3 = Integer.parseInt(properties.getProperty("SingleModeHighScore3"));
        }

    }

    public void setMode(GameMode gameMode,Image theme) throws IOException{
        this.gameMode = gameMode;
        this.theme = theme;

        gameMode.CreateMode();
        gameMode.gameResolution();

    }

    public void gameStart(){
        createImageViews(grid,imageViews);
        createImages(cards);
        //shuffleCards();
        setImages(imageViews,cards);

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
        cardsMatch = false;
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
            if(!seenImageViewsElephant.contains(imageView1)) {
                seenImageViewsElephant.add(imageView1);
                seenCardsElephant.add(card1);
            }
            if(!seenImageViewsKangaroo.contains(imageView1)){
                seenImageViewsKangaroo.add(imageView1);
                seenCardsKangaroo.add(card1);
            }
        }
        if(clicks == 2) {
            id2 = card.getId();
            imageView2 = imageView;
            card2 = card;
            if(!seenImageViewsElephant.contains(imageView2)) {
                seenImageViewsElephant.add(imageView2);
                seenCardsElephant.add(card2);
            }
            if (gameMode.getMode() != 3) {
                score.updateMoves();
                if(gameMode.getGlobalMode().equals("SingleMode"))
                    Moves.setText("Moves: "+score.getMoves());
                disableAll();
                if (id1 == id2) {
                    score.updateFoundCards();
                    if(gameMode.getGlobalMode().equals("SingleMode"))
                        foundCardsLabel.setText("Found Pairs:"+score.getFoundCards());
                    cardsMatch = true;
                    foundCards.add(imageView1);
                    foundCards.add(imageView2);
                    seenImageViewsElephant.remove(imageView1);
                    seenImageViewsElephant.remove(imageView2);
                    seenImageViewsKangaroo.remove(imageView1);
                    seenCardsElephant.remove(card1);
                    seenCardsElephant.remove(card2);
                    seenCardsKangaroo.remove(card1);
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.6),event -> {
                        imageView1.setDisable(true);
                        imageView2.setDisable(true);
                        imageView1.setOpacity(0.6);
                        imageView2.setOpacity(0.6);
                        if(gameMode.getGlobalMode().equals("SingleMode"))
                            enableAll();
                    }));
                    timeline.play();
                } else {
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.2),event -> {
                        imageView1.setImage(card1.getBackground());
                        imageView2.setImage(card2.getBackground());
                        if(gameMode.getGlobalMode().equals("SingleMode")) {
                            imageView1.setDisable(false);
                            imageView2.setDisable(false);
                            enableAll();
                        }
                    }));
                    timeline.play();
                }
                if (foundCards.size() == gameMode.getSize() && gameMode.getGlobalMode().equals("SingleMode")) {
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.2),event -> eraseCards(grid)));
                    timeline.play();
                    if(gameMode.getMode() == 1){
                        if(score.getMoves()<moves1){
                            properties.setProperty("SingleModeHighScore1", Integer.toString(score.getMoves()));
                        }
                    }
                    else if(gameMode.getMode() == 2){
                        if(score.getMoves()<moves2){
                            properties.setProperty("SingleModeHighScore2", Integer.toString(score.getMoves()));
                        }
                    }
                    try {
                        output = new FileOutputStream("score.properties");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        properties.store(output,null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                clicks = 0;
            }
        }
        if (gameMode.getMode()==3) {
            if (clicks == 3) {
                score.updateMoves();
                if(gameMode.getGlobalMode().equals("SingleMode"))
                    Moves.setText("Moves: " + score.getMoves());
                id3 = card.getId();
                imageView3 = imageView;
                card3 = card;

                if(!seenImageViewsElephant.contains(imageView3)) {
                    seenImageViewsElephant.add(imageView3);
                    seenCardsElephant.add(card3);
                }

                disableAll();
                if (id1 == id2 && id2 == id3) {
                    score.updateFoundCards();
                    if(gameMode.getGlobalMode().equals("SingleMode"))
                        foundCardsLabel.setText("Found Pairs:"+score.getFoundCards());
                    cardsMatch = true;
                    foundCards.add(imageView1);
                    foundCards.add(imageView2);
                    foundCards.add(imageView3);
                    seenImageViewsElephant.remove(imageView1);
                    seenImageViewsElephant.remove(imageView2);
                    seenImageViewsElephant.remove(imageView3);
                    seenImageViewsKangaroo.remove(imageView1);
                    seenCardsElephant.remove(card1);
                    seenCardsElephant.remove(card2);
                    seenCardsElephant.remove(card3);
                    seenCardsKangaroo.remove(card1);
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.6),event -> {
                        imageView1.setDisable(true);
                        imageView2.setDisable(true);
                        imageView3.setDisable(true);
                        imageView1.setOpacity(0.6);
                        imageView2.setOpacity(0.6);
                        imageView3.setOpacity(0.6);
                        if(gameMode.getGlobalMode().equals("SingleMode"))
                            enableAll();
                    }));
                    timeline.play();
                }
                else{
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.2),event -> {
                        imageView1.setImage(card1.getBackground());
                        imageView2.setImage(card2.getBackground());
                        imageView3.setImage(card3.getBackground());
                        if(gameMode.getGlobalMode().equals("SingleMode")) {
                            imageView1.setDisable(false);
                            imageView2.setDisable(false);
                            imageView3.setDisable(false);
                            enableAll();
                        }
                    }));
                    timeline.play();
                }
                if (foundCards.size() == gameMode.getSize() && gameMode.getGlobalMode().equals("SingleMode")) {
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.2),event -> eraseCards(grid)));
                    timeline.play();
                    if(score.getMoves()<moves3){
                        try {
                            output = new FileOutputStream("score.properties");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        properties.setProperty("SingleModeHighScore3",Integer.toString(score.getMoves()));
                        try {
                            properties.store(output,null);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                clicks=0;
            }
        }
    }

    public void eraseCards(GridPane grid){
        for(int i = 0;i<imageViews.size();i++){
            grid.getChildren().remove(imageViews.get(i));
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
        Parent root = FXMLLoader.load(getClass().getResource("SingleModeSettings.fxml"));
        Stage stage = (Stage) back.getScene().getWindow();
        stage.getScene().setRoot(root);
    }


    public void createImageViews(GridPane grid,ArrayList<ImageView> imageViews){

        grid.setHgap(10);
        grid.setVgap(10);
        for(int i = 0; i<gameMode.getRows();i++){
            RowConstraints row = new RowConstraints(gameMode.getImHeight());
            row.setMinHeight(Double.MIN_VALUE);
            //  row.setMaxHeight(Double.MAX_VALUE);
            row.setVgrow(Priority.ALWAYS);
            grid.getRowConstraints().add(row);
        }
        for(int i = 0; i<gameMode.getColumns();i++){
            ColumnConstraints column = new ColumnConstraints(gameMode.getImWidth());
            column.setMinWidth(Double.MIN_VALUE);
            //  column.setMaxWidth(Double.MAX_VALUE);
            column.setHgrow(Priority.ALWAYS);
            grid.getColumnConstraints().add(column);
        }

        for(int i = 0; i<gameMode.getRows();i++){
            for(int j = 0; j<gameMode.getColumns();j++) {
                ImageView imageView = new ImageView();
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(gameMode.getImWidth());
                imageView.setFitHeight(gameMode.getImHeight());
                grid.add(imageView,j,i);
                imageViews.add(imageView);
            }
        }

    }

    public void createImages(ArrayList<Card> cards) {
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

    public void setImages(ArrayList<ImageView> imageViews,ArrayList<Card> cards){
        for(int i = 0;i<imageViews.size();i++){
            imageViews.get(i).setImage(cards.get(i).getBackground());
        }
    }

    public void shuffleCards(ArrayList<ImageView> imageViews){
        Collections.shuffle(imageViews);
    }

}
