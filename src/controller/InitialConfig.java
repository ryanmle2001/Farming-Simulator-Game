package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


import java.util.ArrayList;
import java.util.List;

public class InitialConfig {
    private static String namePrompt = "Enter name here. Ex) Bob";
    private static final int IMAGE_SIZE = 30;
    private static List<FlowPane> myPanes = new ArrayList();
    private static TextField nameEnter = new TextField();
    private static int difficultyIndex = 0;
    private static int cropSelectIndex = 0;
    private static BorderPane main = new BorderPane();
    private static HBox topPane = new HBox();
    private static VBox top = new VBox();

    /**
     * should we need have a list of IndivPlot as an attribute?
     * to use when we impelment update?
     * @param buttonHandler Handles button
     * @param h height
     * @param w width
     * @return returns Initial Config Scene
     */

    public static Scene createInitialConfig(EventHandler<ActionEvent> buttonHandler, int h, int w) {

        VBox vBoxFrame = new VBox(8);

        //Add welcome text
        FlowPane welcomeText = new FlowPane();
        Label welcome = new Label("Welcome to Kind Koalas' Farm");
        welcome.getStyleClass().add("welcome");
        welcomeText.getChildren().add(welcome);
        welcomeText.setAlignment(Pos.CENTER);
        welcomeText.getStyleClass().add("welcomeText");

        //get name
        FlowPane nameField = new FlowPane();
        nameField.setAlignment(Pos.CENTER_LEFT);
        Label playerName = new Label("Player Name:");
        nameField.getChildren().add(playerName);
        playerName.setFont(Font.font("Cambria", 20));
        nameEnter.setPromptText(namePrompt);
        nameEnter.setFont(Font.font("Cambria", 20));
        nameField.getChildren().add(nameEnter);
        myPanes.add(nameField);


        //get difficulty
        FlowPane difficultyPane = new FlowPane();
        difficultyPane.getChildren().add(new Label("Difficulty : "));
        ToggleGroup difficultyRadioButtons = new ToggleGroup();

        for (String opt: MetaData.getDifficulty()) {
            RadioButton button = new RadioButton(opt);
            button.setToggleGroup(difficultyRadioButtons);
            difficultyPane.getChildren().add(button);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (button.isSelected()) {
                        difficultyIndex = MetaData.getDifficultyIndex(opt);
                    }
                }
            });


        }
        //set the first one to be selected by default.
        difficultyRadioButtons.getToggles().get(0).setSelected(true);
        myPanes.add(difficultyPane);


        //set starting seed
        FlowPane seedPane = new FlowPane();
        seedPane.getChildren().add(new Label("Select Seed : "));
        ToggleGroup seedSelectionGroup = new ToggleGroup();

        for (String opt: MetaData.getCropList()) {
            RadioButton button = new RadioButton(opt);
            button.setToggleGroup(seedSelectionGroup);
            ImageView cropImage = new ImageView(
                    new Image(InitialConfig.class.getClassLoader().
                            getResourceAsStream("images/" + opt + ".png")));
            cropImage.setFitHeight(IMAGE_SIZE);
            cropImage.setFitWidth(IMAGE_SIZE);
            button.setGraphic(cropImage);
            seedPane.getChildren().add(button);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (button.isSelected()) {
                        cropSelectIndex = MetaData.getCropIndex(opt);
                    }
                }
            });


        }
        //set the first one to be selected by default.
        seedSelectionGroup.getToggles().get(0).setSelected(true);
        myPanes.add(seedPane);


        //add button
        FlowPane buttonPane = new FlowPane();
        Button startFarmingButton = new Button("Start Farming!");
        startFarmingButton.getStyleClass().add("startButton");
        startFarmingButton.setOnAction(buttonHandler);
        buttonPane.getChildren().add(startFarmingButton);
        startFarmingButton.setFont(Font.font("Cambria", 60));
        myPanes.add(buttonPane);

        //add everything to the vbox
        for (FlowPane pane : myPanes) {
            pane.setHgap(20);
            pane.setVgap(20);
            pane.setAlignment(Pos.CENTER);
            vBoxFrame.getChildren().add(pane);
        }

        vBoxFrame.setPadding(new Insets(20, 20, 20, 20));
        vBoxFrame.setSpacing(30);
        vBoxFrame.setAlignment(Pos.CENTER);
        vBoxFrame.setStyle("");
        vBoxFrame.getStyleClass().add("center");

        //topPane
        for (String opt: MetaData.getTopViewImages()) {
            ImageView topImage = new ImageView(
                    new Image(InitialConfig.class.getClassLoader().
                            getResourceAsStream("images/" + opt + ".png")));
            topImage.setFitHeight(100);
            topImage.setFitWidth(100);
            topPane.getChildren().add(topImage);
        }
        topPane.getStyleClass().add("topPane");
        topPane.setAlignment(Pos.CENTER);
        topPane.setSpacing(40);

        //top
        top.getChildren().addAll(topPane, welcomeText);
        top.getStyleClass().add("top");
        top.setSpacing(40);

        //main
        main.setTop(top);
        main.setCenter(vBoxFrame);

        //get scene
        Scene scene = new Scene(main, h, w);
        scene.getStylesheets().add("/css/initial_config.css");

        return scene;
    }


    public static int getDifficultyIndex() {
        return difficultyIndex;
    }
    public static String getInputName() {
        return nameEnter.getText();
    }

    public static String getNamePrompt() {
        return namePrompt;
    }
    public static int getCropSelectIndex() {
        return cropSelectIndex;
    }
}

