package controller;

import javafx.application.Application;

import javafx.scene.Scene;

import javafx.stage.Stage;


public class Controller extends Application {
    private static Stage mainStage;
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 700;
    private static String gameTitle = "KindKoalas Farm game";
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = WelcomePage.createWelcomePage(new ShowInitialConfig(), WIDTH, HEIGHT);
        mainStage = stage;
        stage.setScene(scene);
        stage.setTitle(gameTitle);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch();

    }
    public static int getWidth() {
        return WIDTH;
    }
    public static int getHeight() {
        return HEIGHT;
    }
    public static Stage getMainStage() {
        return mainStage;
    }
}





