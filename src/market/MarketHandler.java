package market;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
public class MarketHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
        Stage marketStage = new Stage();
        marketStage.setTitle("Market");
        marketStage.setScene(MarketUI.createMarketScreen(marketStage));
        marketStage.show();
    }
}
