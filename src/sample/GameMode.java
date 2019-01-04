package sample;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GameMode {

    private int mode;
    private int size;
    private int columns,rows;
    private int selectCards;
    private String globalMode,player1,player2,player3,player4;
    private Properties properties = new Properties();
    private InputStream input = null;
    private int width;
    private double imWidth = 90,imHeight = 130;

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
    }

    public void gameResolution() throws IOException {

        File f = new File("config.properties");
        if(f.exists()) {
            input = new FileInputStream("config.properties");
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
            }

        }

    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getPlayer3() {
        return player3;
    }

    public void setPlayer3(String player3) {
        this.player3 = player3;
    }

    public String getPlayer4() {
        return player4;
    }

    public void setPlayer4(String player4) {
        this.player4 = player4;
    }

    public int getColumns() { return columns; }

    public int getSize() {
        return size;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getSelectCards() {
        return selectCards;
    }

    public String getGlobalMode() {
        return globalMode;
    }

    public void setGlobalMode(String globalMode) {
        this.globalMode = globalMode;
    }

    public double getImHeight() {
        return imHeight;
    }

    public double getImWidth() {
        return imWidth;
    }


    public int getRows() {
        return rows;
    }
}
