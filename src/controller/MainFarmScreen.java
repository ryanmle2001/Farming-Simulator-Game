package controller;

import inventory.InventoryHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import market.MarketHandler;

import java.util.Random;


public class MainFarmScreen {
    private static HBox top = new HBox();
    private static VBox center = new VBox();
    private static Label money = new Label(" $ " + UserInfo.getCurrentMoney() + " ");
    private static int date = 1;
    private static String status = "Good Conditions ";
    private static Label forecast = new Label(" Encountered " + status);
    private static Label day = new Label(" Day " + date + " ");
    private static Button nextDayBtn = new Button("Next Day");
    private static PlotPane[] plotList = new PlotPane[MetaData.getPlotNum()];

    private static HBox bottom = new HBox(100);
    private static Button inventoryB = new Button("Inventory");
    private static Button machineB = new Button("Upgrade Machines");
    private static Button marketB = new Button("Market");



    public static Scene createFarmScreen(int height, int width) {
        BorderPane border = new BorderPane();


        GridPane centerGrid = new GridPane();

        // centerPane
        // Creating columns
        ColumnConstraints[] columns = new ColumnConstraints[MetaData.getPlotNum() / 3];
        for (int i = 0; i < MetaData.getPlotNum() / 3; i++) {
            columns[i] = new ColumnConstraints();
            columns[i].setPercentWidth(100 / (MetaData.getPlotNum() / 3));
            centerGrid.getColumnConstraints().add(columns[i]);
        }
        // Creating rows
        RowConstraints[] rows = new RowConstraints[3];
        for (int i = 0; i < 3; i++) {
            rows[i] = new RowConstraints();
            centerGrid.getRowConstraints().add(rows[i]);
        }
        rows[0].setPercentHeight(100 / 3);
        rows[1].setPercentHeight(100 / 3);
        rows[2].setPercentHeight(100 / 3);

        int colCounter = 0;
        int rowCounter = 0;
        int plotCounter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < MetaData.getPlotNum() / 3; j++) {
                plotList[plotCounter] = new PlotPane();
                centerGrid.add(plotList[plotCounter], colCounter, rowCounter);
                centerGrid.setFillWidth(plotList[plotCounter], true);
                centerGrid.setMaxWidth(Double.MAX_VALUE);
                centerGrid.setAlignment(Pos.CENTER);
                plotCounter++;
                colCounter++;
            }
            colCounter = 0;
            rowCounter++;
        }

        // center grid styling
        centerGrid.setHgap(20);
        centerGrid.setVgap(10);
        centerGrid.setPadding(new Insets(10, 10, 20, 10));
        centerGrid.getStyleClass().add("centerGrid");

        center.getChildren().addAll(forecast, centerGrid);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(10, 0, 0, 0));
        center.setStyle("-fx-background-color: forestgreen");
        border.setCenter(center);

        //bottomPane
        inventoryB.setOnAction(new InventoryHandler());
        marketB.setOnAction(new MarketHandler());
        //bottom.getChildren().addAll(inventoryB, marketB, machineB);
        bottom.getChildren().addAll(inventoryB, marketB);
        bottom.setStyle("-fx-background-color: white");
        bottom.setAlignment(Pos.CENTER);
        bottom.setSpacing(210);
        border.setBottom(bottom);

        // topPane
        top.setAlignment(Pos.CENTER);
        top.setSpacing(100);
        nextDayBtn.setOnAction(event -> {
            for (PlotPane p: plotList) {
                p.grow();
            }
            date += 1;
            day.setText(" Day " + date + " ");
            randomEvent();
            forecast.setText(" Encountered " + status);
        });
        top.getChildren().addAll(money, day, nextDayBtn);
        top.getStyleClass().add("top");
        top.setStyle("-fx-background-color: white");
        border.setTop(top);


        Scene scene = new Scene(border, width, height);
        scene.getStylesheets().add("/css/main_farm_screen.css");

        return scene;
    }

    /**
     * This static method generates a random seed/immature/mature crop assortment.
     * Potential reuse after demo for a "random farm" game mode in the controller.InitialConfig
     */
    public static void generateRandomFarm() {
        int plotIndex = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < MetaData.getPlotNum() / 3; j++) {
                Random rand = new Random();
                int randomInt = rand.nextInt(MetaData.getCropList().length + 1);
                switch (randomInt) {
                case 0:
                    int randomCornState = rand.nextInt(3);
                    switch (randomCornState) {
                    case 0:
                        plotList[plotIndex].sampleSeeds("corn", 1);
                        break;
                    case 1:
                        plotList[plotIndex].sampleSmallCrop("corn", 2);
                        break;
                    case 2:
                        plotList[plotIndex].sampleBigCrop("corn", 4);
                        break;
                    default:
                        break;
                    }
                    break;
                case 1:
                    int randomTomatoState = rand.nextInt(3);
                    switch (randomTomatoState) {
                    case 0:
                        plotList[plotIndex].sampleSeeds("tomato", 1);
                        break;
                    case 1:
                        plotList[plotIndex].sampleSmallCrop("tomato", 2);
                        break;
                    case 2:
                        plotList[plotIndex].sampleBigCrop("tomato", 4);
                        break;
                    default:
                        break;
                    }
                    break;
                case 2:
                    int randomPumpkinState = rand.nextInt(3);
                    switch (randomPumpkinState) {
                    case 0:
                        plotList[plotIndex].sampleSeeds("pumpkin", 1);
                        break;
                    case 1:
                        plotList[plotIndex].sampleSmallCrop("pumpkin", 2);
                        break;
                    case 2:
                        plotList[plotIndex].sampleBigCrop("pumpkin", 9);
                        break;
                    default:
                        break;
                    }
                    break;
                default:
                    break;
                }
                plotIndex++;
            }
        }

    }
    /**
     * The following static methods are different events the player will encounter.
     * These include a droughtEvent, a rainEvent, and a locustEvent
     */

    public static void droughtEvent() {
        for (PlotPane plot: plotList) {
            plot.changeWater(-1);
        }
    }

    public static void rainEvent() {
        for (PlotPane plot: plotList) {
            plot.changeWater(2);
        }
    }

    public static void locustEvent() {
        for (PlotPane plot: plotList) {
            plot.locust();
        }
    }

    public static void setMoney(int amt) {
        money.setText("$" + amt + " ");

    }

    /**
     * Call this method inside lambda.
     * Call changeWater (for drought or rain) or locust on each plotList
     */
    public static void randomEvent() {
        Random rand = new Random();
        int eventChance = rand.nextInt(15 / (InitialConfig.getDifficultyIndex() + 1));
        if (eventChance > 2) {
            status = "Good Conditions ";
        } else {
            int event = rand.nextInt(10);
            if (event == 0 || event == 1 || event == 2 || event == 3) {
                droughtEvent();
                status = "Drought ";
            } else if (event == 4 || event == 5 || event == 6 || event == 7) {
                rainEvent();
                status = "Rain ";
            } else if (event == 8 || event == 9) {
                locustEvent();
                status = "Locusts ";
            }
        }
    }
}
