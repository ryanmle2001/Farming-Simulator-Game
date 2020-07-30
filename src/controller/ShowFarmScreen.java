package controller;

import inventory.InventoryBackEnd;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

class ShowFarmScreen implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
        if (InitialConfig.getInputName().isEmpty()
                || InitialConfig.getInputName().trim().isEmpty()) {
            VBox pane1 = new VBox();
            pane1.setAlignment(Pos.CENTER);
            pane1.setSpacing(10);
            pane1.setPadding(new Insets(30, 30, 30, 30));
            Label warningLabel = new Label("Please enter your name");
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
        } else {
            UserInfo.setDifficulty(InitialConfig.getDifficultyIndex());
            UserInfo.setCurrentMoney(
                    MetaData.getMoneyList()[UserInfo.getDifficulty()]);
            Scene nextScene = MainFarmScreen.createFarmScreen(
                    Controller.getHeight(), Controller.getWidth());
            Controller.getMainStage().setScene(nextScene);
            InventoryBackEnd.addSeed(MetaData.getCropList()[InitialConfig.getCropSelectIndex()]);
        }
    }
}