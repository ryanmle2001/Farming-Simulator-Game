package inventory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class InventoryUI {
    // private GridPane gridPane = new GridPane();
    //Vbox contains 4 rows, top has the seeds label and return button
    //there is an hbox with the seed squares and then an hbox with the crop squares
    /**
     * Creates a square to hold crops in inventory based on crop type
     * Each Square should have image of crop and count in bottom right corner
     * @param crop crop type
     * @return Inventory Square with crop image
     */
    public static VBox createCropSquare(String crop) {
        VBox inventoryBox = new VBox(10);
        // Crop image
        ImageView cropImage = new ImageView(
                new Image("/images/big" + crop.substring(0, 1).toUpperCase()
                        + crop.substring(1) + ".png"));
        cropImage.setFitHeight(100);
        cropImage.setFitWidth(100);

        // Count label
        int count = InventoryBackEnd.getCropList().get(crop);
        Label countLabel = new Label("x" + count);
        countLabel.getStyleClass().add("countLabel");
        // Crop label
        Label nameLabel = new Label(crop.substring(0, 1).toUpperCase() + crop.substring(1));
        nameLabel.getStyleClass().add("nameLabel");

        // VBox per grid entry
        inventoryBox.getChildren().addAll(nameLabel, cropImage, countLabel);
        inventoryBox.setAlignment(Pos.CENTER);
        inventoryBox.getStyleClass().add("gridCell");
        return inventoryBox;
    }
    /**
     * Creates a square to hold seeds in inventory based on seed type
     * Every square should have image of seed and count in bottom right corner
     * @param seed seed type
     * @return Inventory Square with seed image and count
     */
    public static VBox createSeedSquare(String seed) {
        VBox inventoryBox = new VBox(10);
        // seed image
        ImageView seedImage = new ImageView(
                new Image("/images/seed" + seed.substring(0, 1).toUpperCase()
                        + seed.substring(1) + ".png"));
        seedImage.setFitHeight(100);
        seedImage.setFitWidth(100);

        // Count label
        int count = InventoryBackEnd.getSeedList().get(seed);
        Label countLabel = new Label("x" + count);
        countLabel.getStyleClass().add("countLabel");

        // Crop label
        Label nameLabel = new Label(seed.substring(0, 1).toUpperCase() + seed.substring(1));
        nameLabel.getStyleClass().add("nameLabel");

        // VBox per grid entry
        inventoryBox.getChildren().addAll(nameLabel, seedImage, countLabel);
        inventoryBox.setAlignment(Pos.CENTER);
        inventoryBox.getStyleClass().add("gridCell");

        return inventoryBox;
    }

    /**
     * creates inventory scene
     * @param height screen height
     * @param width screen width
     * @param currentStage current stage
     * @return inventory screen
     */
    public static Scene createInventoryScreen(int height, int width, Stage currentStage) {
        GridPane gridPane = new GridPane();
        //gridPane.setGridLinesVisible(true);

        // Creating columns
        ColumnConstraints[] columns = new ColumnConstraints[InventoryBackEnd.getInventoryEntries()];
        for (int i = 0; i < InventoryBackEnd.getInventoryEntries(); i++) {
            columns[i] = new ColumnConstraints();
            columns[i].setPercentWidth((100 / (InventoryBackEnd.getInventoryEntries())));
            gridPane.getColumnConstraints().add(columns[i]);
        }
        // Creating rows
        RowConstraints[] rows = new RowConstraints[4];
        for (int i = 0; i < 4; i++) {
            rows[i] = new RowConstraints();
            gridPane.getRowConstraints().add(rows[i]);
        }
        rows[0].setPercentHeight(10);
        rows[1].setPercentHeight(40);
        rows[2].setPercentHeight(10);
        rows[3].setPercentHeight(40);

        // adding each seedCell to the grid
        int seedCounter = 0;
        for (String seed: InventoryBackEnd.getSeedList().keySet()) {
            gridPane.add(createSeedSquare(seed), seedCounter, 1);
            seedCounter++;
        }
        for (int i = seedCounter; i < InventoryBackEnd.getInventoryEntries(); i++) {
            VBox emptyGridCell = new VBox(10);
            emptyGridCell.getStyleClass().add("gridCell");
            gridPane.add(emptyGridCell, i, 1);
            // seedBox.getChildren().add(createSeedSquare(seed));
        }
        // adding each harvestedPlant cell to the grid

        int cropCounter = 0;
        for (String crop: InventoryBackEnd.getCropList().keySet()) {
            gridPane.add(createCropSquare(crop), cropCounter, 3);
            cropCounter++;
            // seedBox.getChildren().add(createSeedSquare(seed));

        }
        for (int i = cropCounter; i < InventoryBackEnd.getInventoryEntries(); i++) {
            VBox emptyGridCell = new VBox(10);
            emptyGridCell.getStyleClass().add("gridCell");
            gridPane.add(emptyGridCell, i, 3);
            // seedBox.getChildren().add(createSeedSquare(seed));
        }

        // Seeds subtitle
        Label seedLabel = new Label("Seeds");
        gridPane.add(seedLabel, 0, 0);
        gridPane.setFillWidth(seedLabel, true);
        seedLabel.setMaxWidth(Double.MAX_VALUE);
        seedLabel.setAlignment(Pos.CENTER);
        seedLabel.getStyleClass().add("seedLabel");

        // Crops subtitle
        Label cropLabel = new Label("Crops");
        gridPane.add(cropLabel, 0, 2);
        gridPane.setFillWidth(cropLabel, true);
        cropLabel.setMaxWidth(Double.MAX_VALUE);
        cropLabel.setAlignment(Pos.CENTER);
        cropLabel.getStyleClass().add("cropLabel");

        // Return button
        Button returnButton = new Button("Return");
        returnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                currentStage.close();
            }
        });
        gridPane.add(returnButton, InventoryBackEnd.getInventoryEntries() - 1, 0);
        gridPane.setFillWidth(returnButton, true);
        returnButton.setMaxWidth(Double.MAX_VALUE);
        returnButton.setAlignment(Pos.CENTER);
        returnButton.getStyleClass().add("returnButton");

        // gridPane
        //gridPane.setVgap(10);
        gridPane.setHgap(5);
        gridPane.setPadding(new Insets(0, 0, 20, 20));
        Scene scene = new Scene(gridPane, 900, 450);
        scene.getStylesheets().add("/css/inventory_ui.css");
        return scene;
    }

}
