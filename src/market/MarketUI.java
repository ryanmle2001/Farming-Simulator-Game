package market;

import cropvariety.Corn;
import cropvariety.Pumpkin;
import cropvariety.Tomato;
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
import cropvariety.Crop;
import seedvariety.CornSeed;
import seedvariety.PumpkinSeed;
import seedvariety.Seed;
import seedvariety.TomatoSeed;
import inventory.*;
import controller.*;

/**
 * This is for market screen. It doesn't hold any data. Just shows the UI.
 * Create screen with appropriate buttons.
 */
public class MarketUI {
    private static Label moneyLabel = new Label(" $ " + UserInfo.getCurrentMoney() + " ");
    private static Seed seedy;
    private static ImageView seedImage;
    private static final int IMAGE_SIZE = 40;
    private static GridPane gridPane;
    private static MarketHandler marketHandler = new MarketHandler();

    public static Scene createMarketScreen(Stage stage) {
        // gridPane
        gridPane = new GridPane();

        // moneyLabel
        gridPane.add(moneyLabel, 0, 0);
        moneyLabel.getStyleClass().add("moneyLabel");
        gridPane.setFillWidth(moneyLabel, true);
        moneyLabel.setAlignment(Pos.CENTER_LEFT);

        // market label
        Label marketLabel = new Label("Market");
        gridPane.add(marketLabel, 1, 0);
        marketLabel.getStyleClass().add("marketLabel");
        gridPane.setFillWidth(marketLabel, true);
        marketLabel.setMaxWidth(Double.MAX_VALUE);
        marketLabel.setAlignment(Pos.CENTER);

        // return Button
        Button returnButton = new Button("Return");
        gridPane.add(returnButton, MetaData.getCropList().length - 1, 0);
        returnButton.getStyleClass().add("returnButton");
        gridPane.setFillWidth(returnButton, true);
        returnButton.setMaxWidth(Double.MAX_VALUE);
        returnButton.setAlignment(Pos.CENTER);

        returnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });

        ColumnConstraints[] columns = new ColumnConstraints[MetaData.getCropList().length];
        for (int i = 0; i < MetaData.getCropList().length; i++) {
            columns[i] = new ColumnConstraints();
            columns[i].setPercentWidth(100 / MetaData.getCropList().length);
            gridPane.getColumnConstraints().add(columns[i]);
        }
        // Creating 5 Rows
        RowConstraints[] rows = new RowConstraints[6];
        for (int i = 0; i < 6; i++) {
            rows[i] = new RowConstraints();
            gridPane.getRowConstraints().add(rows[i]);
        }
        rows[0].setPercentHeight(10);
        rows[1].setPercentHeight(5);
        rows[2].setPercentHeight(30);
        rows[3].setPercentHeight(5);
        rows[4].setPercentHeight(25);
        rows[5].setPercentHeight(25);

        //Seed label
        Label seedLabel = new Label("Seeds");
        gridPane.add(seedLabel, 0, 1);
        gridPane.setFillWidth(seedLabel, true);
        seedLabel.setMaxWidth(Double.MAX_VALUE);
        seedLabel.setAlignment(Pos.CENTER_LEFT);
        seedLabel.getStyleClass().add("seedLabel");

        // inventoryLabel
        Label inventoryLabel = new Label("Inventory");
        gridPane.add(inventoryLabel, 0, 3);
        gridPane.setFillWidth(inventoryLabel, true);
        inventoryLabel.setMaxWidth(Double.MAX_VALUE);
        inventoryLabel.setAlignment(Pos.CENTER_LEFT);
        inventoryLabel.getStyleClass().add("inventoryLabel");

        //Adding VBox for every Seed to buy: Includes Label, Image, Button
        VBox[] seedOptions = new VBox[MetaData.getCropList().length];
        Label[] seedNames = new Label[MetaData.getCropList().length];
        Button[] buyBtns = new Button[MetaData.getCropList().length];
        for (int i = 0; i < MetaData.getCropList().length; i++) {
            seedOptions[i] = new VBox(10);
            seedNames[i] = new Label(
                    MetaData.getCropList()[i].substring(0, 1).toUpperCase()
                            + MetaData.getCropList()[i].substring(1));
            if (MetaData.getCropList()[i].equals("corn")) {
                seedy = new CornSeed();
            } else if (MetaData.getCropList()[i].equals("tomato")) {
                seedy = new TomatoSeed();
            } else if (MetaData.getCropList()[i].equals("pumpkin")) {
                seedy = new PumpkinSeed();
            }
            seedImage = new ImageView(
                    new Image(MarketUI.class.getClassLoader().getResourceAsStream("images/seed"
                            + MetaData.getCropList()[i] + ".png")));
            seedImage.setFitHeight(IMAGE_SIZE);
            seedImage.setFitWidth(IMAGE_SIZE);
            String seedName = MetaData.getCropList()[i];
            int price =  MarketBackEnd.calculateBuyPrice(seedy);

            // buy button
            buyBtns[i] =  new Button("Buy for $" + price);
            buyBtns[i].getStyleClass().add("buyButton");

            buyBtns[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    int addSuccess = MarketBackEnd.buySeed(seedName, price);
                    if (addSuccess == 0) {
                        stage.setScene(MarketUI.createMarketScreen(stage));
                    } else if (addSuccess == -1) {
                        VBox pane1 = new VBox();
                        pane1.setAlignment(Pos.CENTER);
                        pane1.setSpacing(10);
                        pane1.setPadding(new Insets(30, 30, 30, 30));
                        Label warningLabel = new Label("Not enough money");
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
                    }

                }
            });
            seedOptions[i].getChildren().addAll(seedNames[i], seedImage);
            seedOptions[i].setAlignment(Pos.CENTER);

            HBox marketBox = new HBox(5);
            marketBox.getChildren().addAll(seedOptions[i], buyBtns[i]);
            marketBox.getStyleClass().add("gridCell");
            marketBox.setAlignment(Pos.CENTER);
            gridPane.add(marketBox, i, 2);
        }
        // adding seedSquare to grid
        for (int i = 0; i < MetaData.getCropList().length; i++) {
            gridPane.add(createSeedSquareMarket(MetaData.getCropList()[i]), i, 4);
        }

        // adding cropSquare to grid
        for (int i = 0; i < MetaData.getCropList().length; i++) {
            gridPane.add(createCropSquareMarket(MetaData.getCropList()[i]), i,  5);
        }

        //Setting the vertical and horizontal gaps between the columns
        gridPane.setVgap(25);
        gridPane.setHgap(15);
        gridPane.setPadding(new Insets(0, 0, 30, 15));
        gridPane.getStylesheets().add("/css/market_ui.css");
        Scene scene = new Scene(gridPane, 900, 450);
        return scene;
    }
    public static HBox createSeedSquareMarket(String seed) {
        VBox itemBox = new VBox(5);
        HBox inventoryBox = new HBox(5);
        // seed image
        ImageView seedImage = new ImageView(new Image("/images/seed"
                + seed.substring(0, 1).toUpperCase() + seed.substring(1) + ".png"));
        seedImage.setFitHeight(IMAGE_SIZE);
        seedImage.setFitWidth(IMAGE_SIZE);

        // Count label
        int count;
        if (InventoryBackEnd.getSeedList().get(seed) == null) {
            count = 0;
        } else {
            count = InventoryBackEnd.getSeedList().get(seed);
        }
        Label countLabel = new Label("x" + count);
        countLabel.getStyleClass().add("countLabel");

        // Crop label
        Label nameLabel = new Label(seed.substring(0, 1).toUpperCase() + seed.substring(1));
        nameLabel.getStyleClass().add("nameLabel");

        Seed seedy1 = new CornSeed();
        if (seed.equals("corn")) {
            seedy1 = new CornSeed();
        } else if (seed.equals("tomato")) {
            seedy1 = new TomatoSeed();
        } else if (seed.equals("pumpkin")) {
            seedy1 = new PumpkinSeed();
        }
        int price = MarketBackEnd.calculateBuyPrice(seedy1);
        Button sellButton = new Button("Sell for $" + price);
        sellButton.getStyleClass().add("sellButton");
        sellButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MarketBackEnd.sellSeed(seed, price);
                Integer count2 = InventoryBackEnd.getSeedList().get(seed);
                count2 = count2 != null ? count2 : 0;
                countLabel.setText("x" + count2);
            }
        });

        // HBox per grid entry
        itemBox.getChildren().addAll(nameLabel, seedImage, countLabel);
        itemBox.setAlignment(Pos.CENTER);

        inventoryBox.getChildren().addAll(itemBox, sellButton);
        inventoryBox.setAlignment(Pos.CENTER);
        inventoryBox.getStyleClass().add("gridCell");

        return inventoryBox;
    }
    public static HBox createCropSquareMarket(String crop) {
        VBox itemBox = new VBox(5);
        HBox inventoryBox = new HBox(5);
        // HBox inventoryBox = new
        // Crop image
        ImageView cropImage = new ImageView(new Image("/images/big"
                + crop.substring(0, 1).toUpperCase() + crop.substring(1) + ".png"));
        cropImage.setFitHeight(IMAGE_SIZE);
        cropImage.setFitWidth(IMAGE_SIZE);
        // Count label
        int count;
        if (InventoryBackEnd.getCropList().get(crop) == null) {
            count = 0;
        } else {
            count = InventoryBackEnd.getCropList().get(crop);
        }
        Label inventoryCountLabel = new Label("x" + count);
        inventoryCountLabel.getStyleClass().add("inventoryCountLabel");
        // Crop label
        Label inventoryItemLabel = new Label(crop.substring(0, 1).toUpperCase()
                + crop.substring(1));
        inventoryItemLabel.getStyleClass().add("inventoryItemLabel");

        Crop crop1 = new Corn();
        if (crop.equals("corn")) {
            crop1 = new Corn();
        } else if (crop1.equals("tomato")) {
            crop1 = new Tomato();
        } else if (crop.equals("pumpkin")) {
            crop1 = new Pumpkin();
        }
        int price = MarketBackEnd.calculateSellPrice(crop1);
        Button sellButtonCrop = new Button("Sell for $" + price);
        sellButtonCrop.getStyleClass().add("sellButton");
        sellButtonCrop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MarketBackEnd.sellCrop(crop, price);
                Integer count2 = InventoryBackEnd.getCropList().get(crop);
                count2 = count2 != null ? count2 : 0;
                inventoryCountLabel.setText("x" + count2);
            }
        });

        // HBox per grid entry
        itemBox.getChildren().addAll(inventoryItemLabel, cropImage, inventoryCountLabel);
        itemBox.setAlignment(Pos.CENTER);

        inventoryBox.getChildren().addAll(itemBox, sellButtonCrop);
        inventoryBox.setAlignment(Pos.CENTER);
        inventoryBox.getStyleClass().add("gridCell");
        return inventoryBox;
    }


    public static void setMoneyLabel(int amt) {
        moneyLabel.setText("$" + amt);
    }
}




















