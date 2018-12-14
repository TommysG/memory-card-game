package sample;


import javafx.scene.image.Image;

public class Card {
    private Image value;
    private Image background;
    private int id;

    public Card(Image value, Image background, int id){
        this.value = value;
        this.background = background;
        this.id = id;
    }
    public Image getValue() {
        return value;
    }

    public Image getBackground() {
        return background;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
