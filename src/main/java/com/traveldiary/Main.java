package com.traveldiary;

import com.traveldiary.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/traveldiary/views/WelcomeView.fxml"));
        VBox root = loader.load();
        MainController mainController = loader.getController();
        mainController.setPrimaryStage(primaryStage);
        mainController.setRootLayout(root);
        primaryStage.setTitle("Welcome to Travel Diary");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
