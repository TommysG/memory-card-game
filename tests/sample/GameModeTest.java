package sample;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameModeTest {

    @Test
    void getRival1() {
        GameMode gameMode = new GameMode();
        gameMode.setRival1("test");
        assertEquals("test",gameMode.getRival1());
    }

    @Test
    void getRival2() {
        GameMode gameMode = new GameMode();
        gameMode.setRival2("test");
        assertEquals("test",gameMode.getRival2());
    }

    @Test
    void getRival3() {
        GameMode gameMode= new GameMode();
        gameMode.setRival3("test");
        assertEquals("test",gameMode.getRival3());
    }

    @Test
    void getRivalsNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(1000);
        GameMode gameMode = new GameMode();
        gameMode.setRivalsNumber(randomNumber);
        assertEquals(randomNumber,gameMode.getRivalsNumber());
    }

    @Test
    void getColumns() {
        GameMode gameMode = new GameMode();
        Random random = new Random();
        int mode = random.nextInt(3)+1;
        boolean isBattle = random.nextBoolean();
        gameMode.setMode(mode);

        if(isBattle)
            gameMode.setGlobalMode("Battle");
        else
            gameMode.setGlobalMode("");

        gameMode.CreateMode();
        if(gameMode.getGlobalMode().equals("Battle")){
            assertEquals(4,gameMode.getColumns());
        }
        else if(gameMode.getMode() == 1){
            assertEquals(6,gameMode.getColumns());
        }
        else if(gameMode.getMode() == 2){
            assertEquals(8,gameMode.getColumns());
        }
        else if(gameMode.getMode() == 3){
            assertEquals(6,gameMode.getColumns());
        }
        else
            fail("Mode out of bounds");
    }

    @Test
    void getSize() {
        GameMode gameMode = new GameMode();
        Random random = new Random();
        int mode = random.nextInt(3)+1;
        gameMode.setMode(mode);
        boolean isBattle = random.nextBoolean();
        gameMode.setSize(24);

        if(isBattle)
            gameMode.setGlobalMode("Battle");
        else
            gameMode.setGlobalMode("");

        gameMode.CreateMode();
        if(gameMode.getGlobalMode().equals("Battle")){
            assertEquals(24,gameMode.getSize());
        }
        else if(gameMode.getMode() == 1){
            assertEquals(24,gameMode.getSize());
        }
        else if(gameMode.getMode() == 2){
            assertEquals(48,gameMode.getSize());
        }
        else if(gameMode.getMode() == 3){
            assertEquals(36,gameMode.getSize());
        }
        else
            fail("Mode out of bounds");
    }

    @Test
    void getMode() {
        Random random = new Random();
        int randomNumber = random.nextInt(1000);
        GameMode gameMode = new GameMode();
        gameMode.setMode(randomNumber);
        assertEquals(randomNumber,gameMode.getMode());
    }

    @Test
    void getSelectCards() {
        GameMode gameMode = new GameMode();
        Random random = new Random();
        int mode = random.nextInt(3)+1;
        gameMode.setMode(mode);
        boolean isBattle = random.nextBoolean();

        if(isBattle)
            gameMode.setGlobalMode("Battle");
        else
            gameMode.setGlobalMode("");

        gameMode.CreateMode();
        if(gameMode.getGlobalMode().equals("Battle")){
            assertEquals(1,gameMode.getSelectCards());
        }
        else if(gameMode.getMode() == 1){
            assertEquals(2,gameMode.getSelectCards());
        }
        else if(gameMode.getMode() == 2){
            assertEquals(2,gameMode.getSelectCards());
        }
        else if(gameMode.getMode() == 3){
            assertEquals(3,gameMode.getSelectCards());
        }
        else
            fail("Mode out of bounds");
    }

    @Test
    void getGlobalMode() {
        GameMode gameMode = new GameMode();
        gameMode.setGlobalMode("test");
        assertEquals("test",gameMode.getGlobalMode());
    }

    @Test
    void getRows() {
        GameMode gameMode = new GameMode();
        Random random = new Random();
        int mode = random.nextInt(3)+1;
        boolean isBattle = random.nextBoolean();
        gameMode.setMode(mode);

        if(isBattle)
            gameMode.setGlobalMode("Battle");
        else
            gameMode.setGlobalMode("");

        gameMode.CreateMode();
        if(gameMode.getGlobalMode().equals("Battle")){
            assertEquals(6,gameMode.getRows());
        }
        else if(gameMode.getMode() == 1){
            assertEquals(4,gameMode.getRows());
        }
        else if(gameMode.getMode() == 2){
            assertEquals(6,gameMode.getRows());
        }
        else if(gameMode.getMode() == 3){
            assertEquals(6,gameMode.getRows());
        }
        else
            fail("Mode out of bounds");
    }
}