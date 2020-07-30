package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;

class ShowInitialConfig implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {
        Scene nextScene = InitialConfig.createInitialConfig(
                new ShowFarmScreen(), Controller.getWidth(), Controller.getHeight());
        Controller.getMainStage().setScene(nextScene);
    }
}