package inventory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class InventoryBackEnd {
    /**
     * inventory.InventoryBackEnd
     * seedList Map : seed type, number of seeds
     * cropList Map : crop type, number of harvested crops
     */
    private static Map<String, Integer> seedList = new HashMap<>();
    private static Map<String, Integer> cropList = new HashMap<>();
    private static int currentCapacity = 0;
    private static int maxCapacity = 20;
    private static int inventoryEntries = 6;

    /**
     * Whenever a crop is harvested, the harvest list (i.e. croplist) is updated
     * @param crop the name of the crop harvested.
     * @return boolean to check conditions before updating inventory
     */
    public static boolean addHarvestedCrop(String crop) {
        if (currentCapacity < maxCapacity) {
            if (cropList.containsKey(crop)) {
                cropList.put(crop, (cropList.get(crop)) + 1);
            } else {
                cropList.put(crop, 1);
            }
            currentCapacity++;
            return true;
        } else {
            VBox pane1 = new VBox();
            pane1.setAlignment(Pos.CENTER);
            pane1.setSpacing(10);
            pane1.setPadding(new Insets(30, 30, 30, 30));
            Label warningLabel = new Label("Not enough capacity in inventory");
            warningLabel.setAlignment(Pos.CENTER);
            pane1.getChildren().add(warningLabel);
            Button closeButton = new Button("return");
            pane1.getChildren().add(closeButton);
            Scene scene = new Scene(pane1, 400, 100);
            Stage stage = new Stage();

            closeButton.setOnAction(e -> stage.close());
            stage.setScene(scene);
            stage.setTitle("Warning!!");
            stage.show();
            return false;
        }
    }
    public static boolean addSeed(String seed) {
        if (currentCapacity < maxCapacity) {
            if (seedList.containsKey(seed)) {
                seedList.put(seed, (seedList.get(seed)) + 1);
            } else {
                seedList.put(seed, 1);
            }
            currentCapacity++;
            return true;
        } else {
            VBox pane1 = new VBox();
            pane1.setAlignment(Pos.CENTER);
            pane1.setSpacing(10);
            pane1.setPadding(new Insets(30, 30, 30, 30));
            Label warningLabel = new Label("Not enough capacity in inventory");
            warningLabel.setAlignment(Pos.CENTER);
            pane1.getChildren().add(warningLabel);
            Button closeButton = new Button("return");
            pane1.getChildren().add(closeButton);
            Scene scene = new Scene(pane1, 400, 100);
            Stage stage = new Stage();

            closeButton.setOnAction(e -> stage.close());
            stage.setScene(scene);
            stage.setTitle("Warning!!");
            stage.show();
            return false;
        }
    }
    public static void removeSeed(String seed) {
        if (currentCapacity > 0) {
            if (seedList.containsKey(seed)) {
                if (seedList.get(seed) == 1) {
                    seedList.remove(seed);
                } else {
                    seedList.put(seed, (seedList.get(seed)) - 1);
                }
            }
            currentCapacity--;
        }
    }
    public static void removeCrop(String crop) {
        if (currentCapacity > 0) {
            if (cropList.containsKey(crop)) {
                if (cropList.get(crop) == 1) {
                    cropList.remove(crop);
                } else {
                    cropList.put(crop, (cropList.get(crop)) - 1);
                }
            }
            currentCapacity--;
        }
    }

    public static int getCurrentCapacity() {
        return currentCapacity;
    }

    public static int getMaxCapacity() {
        return maxCapacity;
    }

    public static Map<String, Integer> getSeedList() {
        return seedList;
    }

    public static void setSeedList(Map<String, Integer> seedList) {
        InventoryBackEnd.seedList = seedList;
    }

    public static Map<String, Integer> getCropList() {
        return cropList;
    }

    public static void setCropList(Map<String, Integer> cropList) {
        InventoryBackEnd.cropList = cropList;
    }

    public static int getInventoryEntries() {
        return inventoryEntries;
    }

    public static void setCurrentCapacity(int currentCapacity) {
        InventoryBackEnd.currentCapacity = currentCapacity;
    }


}
