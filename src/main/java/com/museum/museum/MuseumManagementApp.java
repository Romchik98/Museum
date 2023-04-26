package com.museum.museum;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/*public class MuseumManagementApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MuseumManagementApp.class.getResource("login-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}*/

public class MuseumManagementApp extends Application {
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        // Load the main FXML file
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
//        Parent root = loader.load();
//
//        // Create the main controller
//        MuseumManager museumManager = loader.getController();
//
//        // Set up the primary stage
//        primaryStage.setTitle("Museum Management App");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();
//
//        // Initialize the museum manager
//        museumManager.initialize();
//    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MuseumManagementApp.class.getResource("login-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Museum collection management system");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}