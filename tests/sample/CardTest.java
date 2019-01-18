package sample;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    //Cannot Compare Images
    @Test
    void getValue() {
    }

    //Cannot Compare Images
    @Test
    void getBackground() {
    }

    @Test
    void getId() {
        Random random = new Random();
        int randomId = random.nextInt(1000);
        Card card = new Card(null,null,randomId);
        assertEquals(randomId,card.getId());
    }
}