package inventory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class InventoryHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
        Stage inventoryStage = new Stage();
        inventoryStage.setTitle("Inventory");
        inventoryStage.setScene(InventoryUI.createInventoryScreen(700, 1000, inventoryStage));
        inventoryStage.show();
    }
}
