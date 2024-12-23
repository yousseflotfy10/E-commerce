package com.example.test10000;

import javafx.application.Application;
import javafx.stage.Stage;

public class UserApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        LoginScene loginScene = new LoginScene();
        loginScene.start(primaryStage);
    }

    public static void main(String[] args) {
        Database.initialize();
        launch(args);
    }
}
