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
import java.util.*;

/**
 * <h1>Η κλάση του μονού παιχνιδιού.</h1>
 */
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
    private Properties properties2 = new Properties();
    private InputStream input = null,input2 =null;
    private OutputStream output = null,output2 =null;
    private ResourceBundle bundle;
    private Locale locale;
    private String word1,word2;

    @FXML
    private GridPane grid;
    public Score score;
    @FXML
    private Label Moves,foundCardsLabel;

    public Boolean cardsMatch;

    /**
     * Ο κατασκευαστής της κλάσης.
     */
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

    /**
     * Φορτώνει τιμές απο τα αρχεία score και config.
     * @throws IOException εάν αποτύχει να φορτώσει τα αρχεία.
     */
    public void initialize() throws IOException{
        File f2 =new File("score.properties");
        File f1 =new File("config.properties");

        if(f1.exists()){
            input = new FileInputStream("config.properties");
            properties.load(input);

            String lang = properties.getProperty("flag");
            loadLang(lang);

            if(lang.equals("el")) {
                word1 = "Κινήσεις: ";
                word2 = "Βρέθηκαν: ";
            }
            else if(lang.equals("en")) {
                word1 = "Moves: ";
                word2 = "Found Pairs: ";
            }
        }

        if(f2.exists()){
            input2 = new FileInputStream("score.properties");
            properties2.load(input2);

            moves1 = Integer.parseInt(properties2.getProperty("SingleModeHighScore1"));
            moves2 = Integer.parseInt(properties2.getProperty("SingleModeHighScore2"));
            moves3 = Integer.parseInt(properties2.getProperty("SingleModeHighScore3"));
        }

    }

    /**
     * Καθορίχει το είδος του παιχνιδιού και το θέμα των καρτών ανάλογα με το τι έχει επιλέξει ο χρήστης στις ρυθμίσεις.
     * @param gameMode {@code GameMode}
     * @param theme {@code Image} φόντο της κάρτας
     * @throws IOException -
     */
    public void setMode(GameMode gameMode,Image theme) throws IOException{
        this.gameMode = gameMode;
        this.theme = theme;

        gameMode.CreateMode();
        gameMode.gameResolution();

    }

    /**
     * Δημιουργεί το ταμπλό και τις κάρτες και τις ανακατεύει.Έπειτα καλεί τον event handler των ImageView ώστε να ξεκινήσει ο παίχτης να παίζει.
     */
    public void gameStart(){
        createImageViews(grid,imageViews);
        createImages(cards);
        //shuffleCards();
        setImages(imageViews,cards);

        player();
    }

    /**
     * Είναι ο event handler των καρτών.
     */
    public void player(){
        for(int i = 0; i<imageViews.size();i++){
            final ImageView imageView = imageViews.get(i);
            final Card card = cards.get(i);
            imageViews.get(i).setOnMouseClicked(event -> clickEvent(imageView,card));
        }
    }

    /**
     * Έαν πατηθεί κάποιο ImageView τότε καλείται αυτή η μέθοδος. Γυρνάει με την βοήθεια ενός ScaleTransition να γυρίσει την κάρτα που ο χρήστης έχει επιλέξει.Κρατάει την κάρτα που πάτησε πρώτη
     * δεύτερη ή και τρίτη ανάλογα το GameMode και τις συγκρίνει ανάλογα,ενώ ταυτόχρονα αποθηκεύει σε ArrayList κάποιες κάρτες και ImageView.
     * @param imageView {@code ImageView} το ImageView που φαίνεται στην οθόνη
     * @param card {@code Card} κάρτα
     */
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
                    Moves.setText(word1+score.getMoves());
                disableAll();
                if (id1 == id2) {
                    score.updateFoundCards();
                    if(gameMode.getGlobalMode().equals("SingleMode"))
                        foundCardsLabel.setText(word2+score.getFoundCards());
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
                            properties2.setProperty("SingleModeHighScore1", Integer.toString(score.getMoves()));
                        }
                    }
                    else if(gameMode.getMode() == 2){
                        if(score.getMoves()<moves2){
                            properties2.setProperty("SingleModeHighScore2", Integer.toString(score.getMoves()));
                        }
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
                }
                clicks = 0;
            }
        }
        if (gameMode.getMode()==3) {
            if (clicks == 3) {
                score.updateMoves();
                if(gameMode.getGlobalMode().equals("SingleMode"))
                    Moves.setText(word1 + score.getMoves());
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
                        foundCardsLabel.setText(word2+score.getFoundCards());
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
                        properties2.setProperty("SingleModeHighScore3",Integer.toString(score.getMoves()));
                        try {
                            properties2.store(output,null);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                clicks=0;
            }
        }
    }

    /**
     * Διαγράφει τις κάρτες από το GridPane όταν τελειώσει το παιχνίδι.
     * @param grid {@code GridPane}
     */
    public void eraseCards(GridPane grid){
        for(int i = 0;i<imageViews.size();i++){
            grid.getChildren().remove(imageViews.get(i));
        }
    }

    /**
     * Ενεργοποιεί όλες τις κάρτες εκτός απο αυτές που έχουν βρεθεί, ώστε ο χρήστης να μπορεί να τις πατήσει.
     */
    public void enableAll(){
        for(int i = 0;i<imageViews.size();i++){
            imageViews.get(i).setDisable(false);
        }

        for(int i = 0;i<foundCards.size();i++){
            foundCards.get(i).setDisable(true);
        }
    }

    /**
     * Απενεργοποιεί όλες τις κάρτες.
     */
    public void disableAll(){
        for(int i = 0;i<imageViews.size();i++){
            imageViews.get(i).setDisable(true);
        }
    }

    /**
     * Ο event handler του κουμπιού EXIT.
     * @throws IOException Εάν αποτύχει να φορτώσει το αρχείο FXML.
     */
    public void backClicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SingleModeSettings.fxml"));
        Stage stage = (Stage) back.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    /**
     * Παίρνει το GridPane που έχει δημιουργήθει στο αρχείο FXML, δημιουργεί σειρές και στήλες ανάλογα, αφήνοντας τα κατάλληλα κενά και ορίζοντας τις κατάλληλες διαστάσεις.Στην συνέχεια
     * δημιουργεί τα αντικέιμενα ImageView και τα βάζει σε ArrayList.
     * @param grid {@code GridPane}
     * @param imageViews {@code ArrayList<ImageView>}
     */
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

    /**
     * Παίρνει τις εικόνες από το φάκελο Images,δημιουργεί τις κάρτες και τις αποθηκεύει στο ArrayList.
     * @param cards {@code ArrayList<Card>}
     */
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

    /**
     * Βάζει στα ImageViews τις εικόνες του πίσω μέρους των καρτών.
     * @param imageViews {@code ArrayList<ImageView>}
     * @param cards {@code ArrayList<Card>}
     */
    public void setImages(ArrayList<ImageView> imageViews,ArrayList<Card> cards){
        for(int i = 0;i<imageViews.size();i++){
            imageViews.get(i).setImage(cards.get(i).getBackground());
        }
    }

    /**
     * Ανακατεύει τα ImageViews που βρίσκονται στο ArrayList για να βγουν σε τυχαία σειρά.
     * @param imageViews {@code (ArrayList<ImageView>}
     */
    public void shuffleCards(ArrayList<ImageView> imageViews){
        Collections.shuffle(imageViews);
    }

    /**
     * Φορτώνει τη γλώσσα που εμφανίζεται στο μονό παιχνίδι.
     * @param lang {@code String}
     */
    private void loadLang(String lang) {
        locale = new Locale(lang);
        bundle = ResourceBundle.getBundle("sample.lang",locale);

        Moves.setText(bundle.getString("moves"));
        foundCardsLabel.setText(bundle.getString("foundPairs"));

    }

}
