package com.traveldiary.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainController {

    private Stage primaryStage;
    private VBox rootLayout;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setRootLayout(VBox rootLayout) {
        this.rootLayout = rootLayout;
    }

    public void showLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/traveldiary/views/LoginView.fxml"));
            Parent loginView = loader.load();
            LoginController loginController = loader.getController();
            loginController.setMainController(this);
            rootLayout.getChildren().setAll(loginView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showRegister(ActionEvent event) {
        try {
            Parent registerView = FXMLLoader.load(getClass().getResource("/com/traveldiary/views/RegisterView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Register");
            stage.setScene(new Scene(registerView));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadDashboard() {
        try {
            Parent dashboardView = FXMLLoader.load(getClass().getResource("/com/traveldiary/views/DashboardView.fxml"));
            primaryStage.setTitle("Dashboard");
            primaryStage.setScene(new Scene(dashboardView));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadWelcome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/traveldiary/views/WelcomeView.fxml"));
            Parent welcomeView = loader.load();
            MainController mainController = loader.getController();
            mainController.setPrimaryStage(primaryStage);
            mainController.setRootLayout((VBox) welcomeView);
            primaryStage.setTitle("Welcome to Travel Diary");
            primaryStage.setScene(new Scene(welcomeView));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
