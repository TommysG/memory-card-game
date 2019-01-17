package sample;


import javafx.scene.image.Image;

/**
 * <h1>Η κλάση της κάρτας.</h1>
 */
public class Card {
    private Image value;
    private Image background;
    private int id;

    /**
     * Ο κατασκευαστής της κλάσης,στον οποίο δίνονται οι αρχικές τιμές των μεταβλητών.
     * @param value {@code Image}
     * @param background {@code Image}
     * @param id {@code int}
     */
    public Card(Image value, Image background, int id){
        this.value = value;
        this.background = background;
        this.id = id;
    }

    /**
     * Επιστρέφει την τιμή της κάρτας.
     * @return value
     */
    public Image getValue() {
        return value;
    }

    /**
     * Επιστρέφει το φόντο της κάρτας.
     * @return background
     */
    public Image getBackground() {
        return background;
    }

    /**
     * Επιστρέφει τον αριθμό της κάρτας.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Θέτει τον αριθμό της καρτας.
     * @param id {@code int}
     */
    public void setId(int id) {
        this.id = id;
    }
}
