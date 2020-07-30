package seedvariety;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This is a seed abstract class. Holds the base price of seed and the name.
 */

public abstract class Seed {
    protected String name;
    protected int basePrice;

    public Seed(String name, int basePrice) {
        this.name = name;
        this.basePrice = basePrice;
    }

    public void showSeed(int height, int width) {
        String seed = this.getName();
        ImageView seedView = new ImageView(
                new Image("/images/" + seed + ".png"));
        seedView.setFitHeight(height);
        seedView.setFitWidth(width);
    }

    public String getName() {
        return name;
    }

    public int getBasePrice() {
        return basePrice;
    }

}
