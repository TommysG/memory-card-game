package sample;

public class Score {
    private int moves;
    private int foundCards;

    public int getFoundCards() {
        return foundCards;
    }

    public void setFoundCards(int foundCards) {
        this.foundCards = foundCards;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public void updateMoves(){
        moves++;
    }
}
