package sample;

/**
 * <h1>Η κλάση του σκορ.</h1>
 */
public class Score {
    private int moves;
    private int foundCards;

    /**
     * Επιστρέφει των αριθμό των καρτών που έχουν βρεθεί.
     * @return foundCards
     */
    public int getFoundCards() {
        return foundCards;
    }

    /**
     * Θέτει τον αρχικό αριθμό των καρτών που έχουν βρεθεί.
     * @param foundCards {@code int} κάρτες που έχουν βρεθεί
     */
    public void setFoundCards(int foundCards) {
        this.foundCards = foundCards;
    }

    /**
     * Επιστρέφει τον αριθμό των κινήσεων του παίχτη.
     * @return moves
     */
    public int getMoves() {
        return moves;
    }
    /**
     * Θέτει τον αρχικό αριθμό των κινήσεων του παίχτη.
     * @param moves {@code int} κινήσεις
     */
    public void setMoves(int moves) {
        this.moves = moves;
    }

    /**
     * Αυξάνει τον αριθμό των κινήσεων του παίχτη.
     */
    public void updateMoves(){
        moves++;
    }
    /**
     * Αυξάνει τον αριθμό των καρτών που έχουν βρεθεί.
     */
    public void updateFoundCards(){
        foundCards++;
    }
}
