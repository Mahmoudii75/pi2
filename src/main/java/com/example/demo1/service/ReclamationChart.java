package com.example.demo1.service;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ReclamationChart extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/view/stat.fxml"));
        Parent root = loader.load();

        // Set the controller
        StatController controller = loader.getController();

        // Display the scene
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Stat Page");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
