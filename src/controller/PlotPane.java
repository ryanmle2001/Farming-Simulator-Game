package controller;

import cropvariety.Corn;
import cropvariety.Crop;
import cropvariety.Pumpkin;
import cropvariety.Tomato;
import inventory.InventoryBackEnd;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;
import java.util.Random;


public class PlotPane extends VBox {
    private IndivPlot currPlot;
    private static String[] waterImages = {"littleWater", "normalWater", "lotWater"};

    private HBox waterStatus = new HBox();
    private ImageView waterImage = new ImageView(new Image(InitialConfig.class.getClassLoader().
            getResourceAsStream(
            "images/" + "bucket" + ".png")));
    private Label waterQuotient = new Label("");
    private static final int IMAGE_SIZE = 20;
    public PlotPane() {
        currPlot = new IndivPlot(new PlotHandler());

        waterQuotient.setMaxHeight(IMAGE_SIZE);
        waterQuotient.getStyleClass().add("waterLabel");
        this.getStylesheets().add("/css/indiv_plot.css");


        waterStatus.getChildren().addAll(waterImage, waterQuotient);
        waterStatus.setAlignment(Pos.CENTER);
        waterStatus.setSpacing(50);

        this.getChildren().addAll(currPlot, waterStatus);
        this.setSpacing(10);
        this.setPadding(new Insets(0, 0, 10, 0));
        this.setWidth(currPlot.getWidth());
        this.setMaxHeight(Double.MAX_VALUE);
        this.setMinHeight(IMAGE_SIZE);
        this.setStyle("-fx-background-image: url('/images/plot.png'); -fx-background-color: red;");
        updatePaneView();

    }

    public void updatePaneView() {
        if (!currPlot.getPlanted() || currPlot.getIsDead()) {
            waterQuotient.setText("N/A");
            waterImage.setImage(new Image(InitialConfig.class.getClassLoader().getResourceAsStream(
                    "images/" + "bucket" + ".png")));
            waterImage.getStyleClass().add("statusImage");
            waterImage.setFitHeight(IMAGE_SIZE);
            waterImage.setFitWidth(IMAGE_SIZE);
            return;
        }
        int currWater = currPlot.getCurrWaterLevel();
        if (currWater < Crop.getMinWater()) {
            updateImageView(0);
        } else if (currWater == Crop.getMaxWater()) {
            updateImageView(2);
        } else {
            updateImageView(1);
        }
        String labelText = currWater + "/" + Crop.getMaxWater();
        waterQuotient.setText(labelText);
    }


    public void updateImageView(int index) {
        waterImage.setImage(
                new Image(InitialConfig.class.getClassLoader().getResourceAsStream(
                        "images/" + waterImages[index] + ".png")));
        waterImage.setFitHeight(IMAGE_SIZE);
        waterImage.setFitWidth(IMAGE_SIZE);
    }





    public void sampleSeeds(String cropName, int stage) {
        currPlot.newCrop(cropName);
        currPlot.stage += stage;
        currPlot.isDead = false;
        currPlot.planted = true;
        updatePaneView();
        currPlot.updateView();
    }
    public void sampleSmallCrop(String cropName, int stage) {
        currPlot.newCrop(cropName);
        currPlot.stage += stage;
        currPlot.planted = true;
        currPlot.isDead = false;
        updatePaneView();
        currPlot.updateView();
    }
    public void sampleBigCrop(String cropName, int stage) {
        currPlot.newCrop(cropName);
        currPlot.stage += stage;

        currPlot.planted = true;
        currPlot.isDead = false;
        updatePaneView();
        currPlot.updateView();
    }

    public void grow() {
        currPlot.grow();
    }

    public void changeWater(int amount) {
        if (!currPlot.planted || currPlot.isDead) {
            return;
        }
        currPlot.changeWaterLevel(amount);
    }
    public void locust() {
        if (!currPlot.planted || currPlot.isDead) {
            return;
        }
        currPlot.locust();
    }
    public IndivPlot getCurrPlot() {
        return currPlot;
    }
    
    public class IndivPlot extends Button {
        private boolean isSelected = false;
        private Crop plantedCrop;
        private int stage = -1;
        private int currWaterLevel;
        private boolean planted = false;
        private boolean isDead = false;
        private static final int IMAGE_SIZE = 100;
        private ImageView standard = new ImageView(new Image("/images/hoe.png"));

        /**
         * Initializer for IndivPlot.
         * sets the size of each button to 120 by 120.
         * the background is an image of soil.
         * @param plotHandler handler when the indivPlot is clicked
         */
        public IndivPlot(PlotPane.PlotHandler plotHandler) {
            super();
            standard.setFitHeight(IMAGE_SIZE);
            standard.setFitWidth(IMAGE_SIZE);
            this.setGraphic(standard);

            this.setMaxHeight(Double.MAX_VALUE);
            this.setMaxWidth(Double.MAX_VALUE);
            this.setMinHeight(120);

            this.setOnAction(plotHandler);
            this.setStyle("-fx-background-image: url('/images/plot.png')");
            this.getStylesheets().add("/css/indiv_plot.css");
        }

        public void locust() {
            Random rand = new Random();
            if (rand.nextInt(3) == 0) {
                isDead = true;
            }
            updateView();
        }


        /**
         * Helper method for planting seeds
         * This method uses Inventory's seedList attribute.
         * If we have seeds in seedList, we will plant something
         * (user may choose not to by exiting out.)
         */
        private void plantSeed() {
            Map<String, Integer> seedList = InventoryBackEnd.getSeedList();
            Stage seedSelectStage = new Stage();

            //If the seed list is empty, we show warning.
            //else we show options to plant.
            if (seedList.isEmpty()) {
                //Create a scene with warning that says you have no seeds.
                VBox pane1 = new VBox();
                pane1.setAlignment(Pos.CENTER);
                pane1.setSpacing(10);
                pane1.setPadding(new Insets(30, 30, 30, 30));
                Label warningLabel = new Label("You have no seeds");
                warningLabel.setAlignment(Pos.CENTER);
                pane1.getChildren().add(warningLabel);
                Button closeButton = new Button("return");
                pane1.getChildren().add(closeButton);
                Scene scene = new Scene(pane1, 400, 100);
                closeButton.setOnAction(e -> seedSelectStage.close());
                seedSelectStage.setScene(scene);
                seedSelectStage.setTitle("Warning!!");
            } else {
                seedSelectStage.setScene(seedSelectionScene(seedList, seedSelectStage));
                seedSelectStage.setTitle("Select Seed to Plant");
            }
            seedSelectStage.show();
        }

        /**
         * This screen shows the options for seeds and one can select the seed type.
         * (similar to the initialConfig Screen)
         * @param seedList list of available seeds
         * @param stage the stage that the scene will be put on.
         * @return seed Selection Scene
         */
        private Scene seedSelectionScene(Map<String, Integer> seedList, Stage stage) {
            VBox seedPane = new VBox(5);
            seedPane.setAlignment(Pos.CENTER_LEFT);
            seedPane.getStyleClass().add("seedPane");
            seedPane.setPadding(new Insets(40, 40, 40, 40));

            Label selectSeedLabel = new Label("Select Seed: ");
            selectSeedLabel.getStyleClass().add("selectSeedLabel");

            seedPane.getChildren().add(selectSeedLabel);
            ToggleGroup seedSelectionGroup = new ToggleGroup();
            for (String opt : MetaData.getCropList()) {
                if (!seedList.containsKey(opt)) {
                    continue;
                }
                RadioButton button = new RadioButton(opt);
                button.setToggleGroup(seedSelectionGroup);
                ImageView cropImage = new ImageView(
                        new Image(InitialConfig.class.getClassLoader().getResourceAsStream("images/"
                                + opt + ".png")));
                cropImage.setFitHeight(30);
                cropImage.setFitWidth(30);
                button.setGraphic(cropImage);
                seedPane.getChildren().add(button);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if (button.isSelected()) {
                            isSelected = true;
                            newCrop(opt);
                        }
                    }
                });
            }
            Button closeButton = new Button("select");
            closeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (isSelected) {
                        seedSelectorButtonAction();
                        String plantName = plantedCrop.getName();
                        if (seedList.get(plantName) == 1) {
                            seedList.remove(plantName);
                        } else {
                            seedList.put(plantName, seedList.get(plantName) - 1);
                        }
                        InventoryBackEnd.setCurrentCapacity(InventoryBackEnd.getCurrentCapacity()
                                - 1);
                        stage.close();
                    } else {
                        VBox pane1 = new VBox();
                        pane1.setAlignment(Pos.CENTER);
                        pane1.setSpacing(10);
                        pane1.setPadding(new Insets(30, 30, 30, 30));
                        Label warningLabel = new Label("Please select a crop");
                        warningLabel.setAlignment(Pos.CENTER);
                        pane1.getChildren().add(warningLabel);
                        Button closeButton = new Button("Return");
                        closeButton.getStyleClass().add("closeButton");

                        pane1.getChildren().add(closeButton);
                        Scene scene = new Scene(pane1, 400, 100);
                        Stage stage = new Stage();

                        closeButton.setOnAction(e -> stage.close());
                        stage.setScene(scene);
                        stage.setTitle("Warning!!");
                        stage.show();
                    }
                }
            });
            seedPane.getChildren().add(closeButton);
            Scene scene = new Scene(seedPane, 400, 200);
            scene.getStylesheets().add("/css/indiv_plot.css");
            return scene;
        }

        /**
         * method called inside "select" button that enables a user to select a seed to plant
         * The stage&planted attributes are updated, crop image is updated by calling updateView().
         */
        private void seedSelectorButtonAction() {
            stage = 0;
            planted = true;
            currWaterLevel = 3;
            isDead = false;
            updateView();
        }

        /**
         * This method updates the plantedCrop with selected when we first plant something.
         * @param cropName the crop we are planting.
         */
        private void newCrop(String cropName) {
            if (cropName.equals("corn")) {
                plantedCrop = new Corn();
            } else if (cropName.equals("tomato")) {
                plantedCrop = new Tomato();
            } else if (cropName.equals("pumpkin")) {
                plantedCrop = new Pumpkin();
            }
        }
        /**
         * This method harvests the crop once we reach the max water level.
         * This method does the following :
         * 1. removes the graphic set.
         * 2. reset stage to -1
         * 3. set planted to false.
         * It uses inventory.InventoryBackEnd class's addHarvestedCrop method to add harvested crop
         * to cropList in Inventory.
         */
        private void harvest() {
            if (InventoryBackEnd.addHarvestedCrop(plantedCrop.getName())) {
                stage = -1;
                planted = false;
                plantedCrop = null;
                tempMessageScreen("harvest");
                isSelected = false;
                standard.setFitHeight(IMAGE_SIZE);
                standard.setFitWidth(IMAGE_SIZE);
                this.setGraphic(standard);

            } else {
                tempMessageScreen("harvest fail");
            }


        }

        /**
         * waters the plant.
         * increases current water level by 1.
         * If it exceeds the max water level, the plant dies.
         */
        private void water() {
            currWaterLevel++;
            if (currWaterLevel > plantedCrop.getMaxWater()) {
                isDead = true;
            }
            tempMessageScreen("water" + currWaterLevel);
            updateView();
        }
        /**
         * This method is called every time we harvest or water
         * @param message either harvest or water
         */
        private void tempMessageScreen(String message) {


        }

        /**
         * removes dead plant
         */
        private void removeDead() {
            stage = -1;
            planted = false;
            plantedCrop = null;
            isDead = false;



            standard.setFitHeight(IMAGE_SIZE);
            standard.setFitWidth(IMAGE_SIZE);
            this.setGraphic(standard);
            tempMessageScreen("dead plant removed");
            isSelected = false;
        }

        public void grow() {
            if (!planted) {
                return;
            } else if (currWaterLevel >= plantedCrop.getMinWater()) {
                if (stage != plantedCrop.getMaxStageNeeded()) {
                    stage++;
                }
                currWaterLevel--;
            } else {
                isDead = true;
            }
            currPlot.tempMessageScreen("grow");
            updateView();
            updatePaneView();
        }

        /**
         * Changes water level
         * @param change the amount changing : positive for increasing and negative for decreasing
         */
        public void changeWaterLevel(int change) {
            currWaterLevel += change;
            if (currWaterLevel < plantedCrop.getMinWater()
                    || currWaterLevel > plantedCrop.getMaxWater()) {
                isDead = true;
            }
            updateView();
        }

        /**
         * This class waters the crop in the plot and updates the button image.
         * The image is selected as following:
         * 1. stage == 0 (first planted and not watered yet --> imageIndex = 0 (usually just sprout)
         * 2. stage > 0 but stage < maxWaterNeeded (have been watered but not harvestable) -->
         *     imageIndex = 1 (growing)
         * 3. stage == maxWaterNeeded (harvestable) --> imageIndex=2;
         * (no need to save the image list in the metadata).
         * Change the extension to .jpeg if needed.
         */
        private void updateView() {
            ImageView updateImage;
            if (!isDead) {
                int imageIndex;
                if (stage == 0) {
                    imageIndex = 0;
                } else if (stage == plantedCrop.getMaxStageNeeded()) {
                    imageIndex = 2;
                } else {
                    imageIndex = 1;
                }
                String[] imageList = plantedCrop.getGrowthImage();
                updateImage = new ImageView(
                        new Image(InitialConfig.class.getClassLoader().getResourceAsStream(
                                "images/" + imageList[imageIndex] + ".png")));
            } else {
                updateImage =  new ImageView(
                        new Image(InitialConfig.class.getClassLoader().getResourceAsStream(
                                "images/" + plantedCrop.getDeadImage() + ".png")));
            }
            updateImage.setFitHeight(IMAGE_SIZE);
            updateImage.setFitWidth(IMAGE_SIZE);
            this.setGraphic(updateImage);

            //do we update the farm screen? -- NO
            updatePaneView();
        }
        /**
         * This method handles whenever a plot is clicked on.
         * It does one of the following:
         *  1. plant
         *  2. water
         *  3. Harvest
         *  The condition for Harvest is when we have watered a plant up to maxWaterNeeded
         *  maxWaterNeeded is a property of crop
         */
        public void helpPlotHandling() {
            if (planted) {
                if (isDead) {
                    removeDead();
                } else if (stage == plantedCrop.getMaxStageNeeded()) {
                    harvest();
                } else {
                    water();
                }
            } else {
                plantSeed();
            }
        }


        public int getCurrWaterLevel() {
            return currWaterLevel;
        }

        public boolean getPlanted() {
            return planted;
        }

        public boolean getIsDead() {
            return isDead;
        }

    }

    public class PlotHandler implements EventHandler {

        @Override
        public void handle(Event event) {
            currPlot.helpPlotHandling();
            updatePaneView();
        }
    }

}
