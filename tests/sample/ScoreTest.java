package sample;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    @Test
    void getFoundCards() {
        Score score = new Score();
        Random random = new Random();
        int randomNumber = random.nextInt(1000);
        score.setFoundCards(randomNumber);
        assertEquals(randomNumber,score.getFoundCards());
    }

    @Test
    void getMoves() {
        Score score = new Score();
        Random random = new Random();
        int randomNumber = random.nextInt(1000);
        score.setMoves(randomNumber);
        assertEquals(randomNumber,score.getMoves());
    }

    @Test
    void updateMoves() {
        Score score = new Score();
        Random random = new Random();
        int randomNumber = random.nextInt(1000);
        score.setMoves(randomNumber);
        score.updateMoves();
        assertEquals(randomNumber+1,score.getMoves());
    }

    @Test
    void updateFoundCards() {
        Score score = new Score();
        Random random = new Random();
        int randomNumber = random.nextInt(1000);
        score.setFoundCards(randomNumber);
        score.updateFoundCards();
        assertEquals(randomNumber+1,score.getFoundCards());
    }
}