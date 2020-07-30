package cropvariety;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This is an abstract class for crop. Defines the "frame" for crops.
 * look notion.
 * don't have to be static.
 */
public abstract class Crop {
    protected String name;
    protected int basePrice;
    protected String[] growthImage = new String[3];
    protected static final int MAX_WATER = 5;
    protected static final int MIN_WATER = 2;
    protected int maxStageNeeded;
    protected static String deadImage = "deadPlant";

    public Crop(String name, int basePrice, String[] growthImage, int maxStageNeeded) {
        this.name = name;
        this.basePrice = basePrice;
        this.growthImage = growthImage;
        this.maxStageNeeded = maxStageNeeded;

    }

    public String getName() {
        return name;
    }

    public int getBasePrice() {
        return basePrice;
    }
    public String[] getGrowthImage() {
        return growthImage;
    }

    public int getMaxStageNeeded() {
        return maxStageNeeded;
    }

    public void showSprout(int height, int width) {
        String sprout = this.getGrowthImage()[0];
        ImageView sproutView = new ImageView(
                new Image("/images/" + sprout + ".png"));
        sproutView.setFitHeight(height);
        sproutView.setFitWidth(width);
    }

    public void showImmaturePlant(int height, int width) {
        String immaturePlant = this.getGrowthImage()[1];
        ImageView immaturePlantView = new ImageView(
                new Image("/images/" + immaturePlant + ".png"));
        immaturePlantView.setFitHeight(height);
        immaturePlantView.setFitWidth(width);
    }

    public void showMaturePlant(int height, int width) {
        String maturePlant = this.getGrowthImage()[2];
        ImageView maturePlantView = new ImageView(
                new Image("/images/" + maturePlant + ".png"));
        maturePlantView.setFitHeight(height);
        maturePlantView.setFitWidth(width);

    }

    public static int getMaxWater() {
        return MAX_WATER;
    }

    public static int getMinWater() {
        return MIN_WATER;
    }

    public static String getDeadImage() {
        return deadImage;
    }
}
