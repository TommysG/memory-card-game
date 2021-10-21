package sample;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <h1>Η κλάση του GameMode</h1>
 */
public class GameMode {

    private int mode;
    private int size;
    private int columns,rows;
    private int selectCards;
    private String globalMode,rival1,rival2,rival3;
    private Properties properties = new Properties();
    private int width,rivalsNumber;
    private double imWidth = 90,imHeight = 130;

    /**
     * Ο κατασκευαστής της κλάσης.
     */
    public GameMode(){
        rival1 = "None";
        rival2 = "None";
        rival3 = "None";
        rivalsNumber = 0;
    }

    /**
     * Η δημιουργία του παιχνιδιού ανάλογα τον τύπο(κανονικό,διπλό,τριπλό).
     */
    public void CreateMode(){

        switch(mode){
            case 1:
                size = 24;
                columns = 6;
                rows = 4;
                selectCards = 2;
                break;
            case 2:
                size = 48;
                columns = 8;
                rows = 6;
                selectCards = 2;
                break;
            case 3:
                size = 36;
                columns = 6;
                rows = 6;
                selectCards = 3;
                break;
        }

        if(globalMode.equals("Battle")){
            size = 24;
            rows = 6;
            columns = 4;
            selectCards =1;
        }
    }

    /**
     * Θέτει το μέγεθος του ταμπλο
     * @param size {@code int}
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Φτιάχνει την ανάλυση από τις κάρτες.
     * @throws IOException Εάν αποτύχει να φορτώσει το αρχείο.
     */
    public void gameResolution() throws IOException {

        File f = new File("config.properties");
        if(f.exists()) {
            InputStream input = new FileInputStream("config.properties");
            properties.load(input);

            width = Integer.parseInt(properties.getProperty("width"));
            if(width == 800){

                switch(mode){
                    case 1:
                        imWidth = 90;
                        imHeight = 130;
                        break;
                    case 2:
                        imWidth = 64;
                        imHeight = 90;
                        break;
                    case 3:
                        imWidth = 64;
                        imHeight = 90;
                        break;
                }

                if(globalMode.equals("Battle")){
                    imWidth = 64;
                    imHeight = 90;
                }
            }
            else if(width == 1280){
                imWidth = 80;
                imHeight = 114;
            }
            else if(width == 999){
                imWidth = 86;
                imHeight = 123;
            }
            else if(width == 600){

                switch(mode){
                    case 1:
                        imWidth = 64;
                        imHeight = 90;
                        break;
                    case 2:
                        imWidth = 43;
                        imHeight = 62;
                        break;
                    case 3:
                        imWidth = 43;
                        imHeight = 62;
                        break;
                }

                if(globalMode.equals("Battle")){
                    imWidth = 43;
                    imHeight = 62;
                }
            }

        }

    }

    /**
     * Επιστρέφει τον πρώτο αντίπαλο
     * @return rival1
     */
    public String getRival1() {
        return rival1;
    }

    /**
     * Θέτει τον πρώτο αντίπαλο
     * @param rival1 {@code String}
     */
    public void setRival1(String rival1) {
        this.rival1 = rival1;
    }

    /**
     * Επιστρέφει τον δεύτερο αντίπαλο
     * @return rival2
     */
    public String getRival2() {
        return rival2;
    }

    /**
     * Θέτει τον δεύτερο αντίπαλο
     * @param rival2 {@code String}
     */
    public void setRival2(String rival2) {
        this.rival2 = rival2;
    }

    /**
     * Επιστρέφει τον τρίτο αντίπαλο
     * @return rival3
     */
    public String getRival3() {
        return rival3;
    }

    /**
     * Θέτει τον τρίτο αντίπαλο
     * @param rival3 {@code String}
     */
    public void setRival3(String rival3) {
        this.rival3 = rival3;
    }

    /**
     * Επιστρέφει τον αριθμό των παιχτών
     * @return rivalsNumber
     */
    public int getRivalsNumber() {
        return rivalsNumber;
    }

    /**
     * Θέτει τον αριθμό των παιχτών
     * @param rivalsNumber {@code int}
     */
    public void setRivalsNumber(int rivalsNumber) {
        this.rivalsNumber = rivalsNumber;
    }

    /**
     * Επιστρέφει τον αριθμό των στηλών
     * @return columns
     */
    public int getColumns() { return columns; }

    /**
     * Επιστρέφει τον μέγεθος του ταμπλό
     * @return size
     */
    public int getSize() {
        return size;
    }

    /**
     * Επιστρέφει τον τύπο του παιχνιδιού
     * @return mode
     */
    public int getMode() {
        return mode;
    }

    /**
     * Θέτει τον τύπο του παιχνιδιού
     * @param mode {@code int}
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    /**
     * Επιστρέφει την τιμή των καρτών που μπορεί ο παιχτή να επιλέξει
     * @return selectCards
     */
    public int getSelectCards() {
        return selectCards;
    }

    /**
     * Επιστρέφει το γενικό τύπου του παιχνιδιού
     * @return globalMode
     */
    public String getGlobalMode() {
        return globalMode;
    }

    /**
     * Θέτει το γενικό τύπο του παιχνιδιού
     * @param globalMode {@code String}
     */
    public void setGlobalMode(String globalMode) {
        this.globalMode = globalMode;
    }

    /**
     * Επιστρέφει το ύψος της κάθε κάρτας
     * @return imHeight
     */
    public double getImHeight() {
        return imHeight;
    }

    /**
     * Επιστρέφει το πλάτος της κάθε κάρτας
     * @return imWidth
     */
    public double getImWidth() {
        return imWidth;
    }

    /**
     * Επιστρέφει τον αριθμό των σειρών
     * @return rows
     */
    public int getRows() {
        return rows;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
