package sample;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

public class Game {

    @FXML
    private Button back;
    public GameMode gameMode;
    @FXML
    private AnchorPane Game;
    public ArrayList<ImageView> imageViews,foundCards,seenImageViews;
    public ArrayList<Card> cards,seenCards;

    private Image theme;
    private int clicks = 0;
    public int id1,id2,id3;

    public ImageView imageView1,imageView2,imageView3;
    public Card card1,card2,card3;

    private Properties properties = new Properties();
    private InputStream input = null;

    @FXML
    private Label winLabel;

    @FXML
    private GridPane grid;

    public Boolean played;


    public Game(){
        gameMode = new GameMode();
        imageViews = new ArrayList<>();
        cards = new ArrayList<>();
        foundCards = new ArrayList<>();
        seenImageViews = new ArrayList<>();
        seenCards = new ArrayList<>();
        played = false;
    }

    public void initialize() {
        winLabel.setVisible(false);
    }

    public void setMode(GameMode gameMode,Image theme) throws IOException{
        this.gameMode = gameMode;
        this.theme = theme;

        gameMode.CreateMode();
        gameMode.gameResolution();

    }

    public void gameStart(){
        createImageViews(grid);
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
        played = false;
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
            if(!seenImageViews.contains(imageView1)) {
                seenImageViews.add(imageView1);
                seenCards.add(card1);
            }
        }
        if(clicks == 2) {
            id2 = card.getId();
            imageView2 = imageView;
            card2 = card;
            if(!seenImageViews.contains(imageView2)) {
                seenImageViews.add(imageView2);
                seenCards.add(card2);
            }
            if (gameMode.getMode() != 3) {
                disableAll();
                if (id1 == id2) {
                    foundCards.add(imageView1);
                    foundCards.add(imageView2);
                    seenImageViews.remove(imageView1);
                    seenImageViews.remove(imageView2);
                    seenCards.remove(card1);
                    seenCards.remove(card2);
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
                        imageView1.setDisable(false);
                        imageView2.setDisable(false);
                        if(gameMode.getGlobalMode().equals("SingleMode"))
                            enableAll();
                    }));
                    timeline.play();
                }
                if (foundCards.size() == gameMode.getSize() && gameMode.getGlobalMode().equals("SingleMode")) {
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
        if (gameMode.getMode()==3) {
            if (clicks == 3) {
                id3 = card.getId();
                imageView3 = imageView;
                card3 = card;

                disableAll();
                if (id1 == id2 && id2 == id3) {
                    foundCards.add(imageView1);
                    foundCards.add(imageView2);
                    foundCards.add(imageView3);
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.6),event -> {
                        imageView1.setDisable(true);
                        imageView2.setDisable(true);
                        imageView3.setDisable(true);
                        imageView1.setOpacity(0.6);
                        imageView2.setOpacity(0.6);
                        imageView3.setOpacity(0.6);
                        enableAll();
                    }));
                    timeline.play();
                }
                else{
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.2),event -> {
                        imageView1.setImage(card1.getBackground());
                        imageView2.setImage(card2.getBackground());
                        imageView3.setImage(card3.getBackground());
                        imageView1.setDisable(false);
                        imageView2.setDisable(false);
                        imageView3.setDisable(false);
                        enableAll();
                    }));
                    timeline.play();
                }
                if (foundCards.size() == gameMode.getSize()) {
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.2)));
                    timeline.play();
                    timeline.setOnFinished(event -> {
                        eraseCards();
                        winLabel.setVisible(true);
                    });
                }
                clicks=0;
            }
        }
        played = true;
    }

    public void eraseCards(){
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


    public void createImageViews(GridPane grid){

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
