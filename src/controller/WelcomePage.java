package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Welcome page in JavaFX.
 * @author Ryan le
 * @version 1.0
 */
public class WelcomePage {
    private static Button beginButton = new Button("Begin your Farm!");
    private  static BorderPane main = new BorderPane();
    private   static VBox top = new VBox();
    private  static VBox center = new VBox();
    private  static HBox topPane = new HBox();
    private  static HBox titlePane = new HBox();
    private  static HBox producerPane = new HBox();
    private  static Label title = new Label("Farm Fever");
    private  static Label producer = new Label("KindKoalas 2020");
    private  static final int IMAGE_SIZE_A = 100;
    private  static final int IMAGE_SIZE_B = 60;

    public static Scene createWelcomePage(EventHandler<ActionEvent> buttonHandler, int h, int w) {
        // topPane
        for (String opt: MetaData.getTopViewImages()) {

            ImageView topImage = new ImageView(
                    new Image(WelcomePage.class.getClassLoader().
                            getResourceAsStream("images/" + opt + ".png")));
            topImage.setFitHeight(IMAGE_SIZE_A);
            topImage.setFitWidth(IMAGE_SIZE_A);
            topPane.getChildren().add(topImage);
        }
        topPane.getStyleClass().add("topPane");
        topPane.setAlignment(Pos.CENTER);
        topPane.setSpacing(40);

        // title styling
        title.setStyle("");
        title.getStyleClass().add("title");

        // titlePane
        for (String opt: MetaData.getTitleImages()) {
            ImageView cropImage = new ImageView(

                    new Image(WelcomePage.class.getClassLoader().
                            getResourceAsStream("images/" + opt + ".png")));
            cropImage.setFitHeight(IMAGE_SIZE_A);
            cropImage.setFitWidth(IMAGE_SIZE_A);
            titlePane.getChildren().add(cropImage);
        }
        titlePane.getChildren().add((MetaData.getTitleImages().length / 2), title);
        titlePane.getStyleClass().add("titlePane");
        titlePane.setAlignment(Pos.CENTER);

        // top
        top.getChildren().addAll(topPane, titlePane);
        top.setStyle("");
        top.getStyleClass().add("top");
        top.setSpacing(30);
        top.setAlignment(Pos.CENTER);

        // producer styling
        producer.setStyle("");
        producer.getStyleClass().add("producer");

        // producerPane
        for (String opt: MetaData.getProducerImages()) {
            ImageView producerImage = new ImageView(

                    new Image(WelcomePage.class.getClassLoader().
                            getResourceAsStream("images/" + opt + ".png")));
            producerImage.setFitHeight(IMAGE_SIZE_B);
            producerImage.setFitWidth(IMAGE_SIZE_B);
            producerPane.getChildren().add(producerImage);
        }
        producerPane.getChildren().add((MetaData.getTitleImages().length / 2), producer);
        producerPane.getStyleClass().add("producerPane");
        producerPane.setAlignment(Pos.CENTER);

        // button styling + functionality
        beginButton.setStyle("-fx-cursor: pointer;");
        beginButton.getStyleClass().add("beginButton");
        beginButton.setOnAction(buttonHandler);

        // center
        center.getChildren().addAll(producerPane, beginButton);
        center.setStyle("");
        center.getStyleClass().add("center");
        center.setSpacing(30);
        center.setAlignment(Pos.CENTER);

        //main
        main.setTop(top);
        main.setCenter(center);

        Scene scene = new Scene(main, h, w);
        scene.getStylesheets().add("/css/welcome_page.css");
        return scene;
    }
}