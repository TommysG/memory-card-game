package sample;

public class GameMode {

    private int mode;
    private int size;
    private int rows;
    private int selectCards;

    public void CreateMode(){

        switch(mode){
            case 1:
                size = 24;
                rows = 6;
                selectCards = 2;
                break;
            case 2:
                size = 48;
                rows = 8;
                selectCards = 2;
                break;
            case 3:
                size = 36;
                rows = 6;
                selectCards = 3;
                break;
        }
    }

    public int getRows() { return rows; }

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

}
