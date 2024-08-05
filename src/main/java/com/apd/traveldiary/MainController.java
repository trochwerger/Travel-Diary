package com.apd.traveldiary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
public class MainController {

    public VBox mainSection;
    @FXML
    private Button startApplicationButton;
    @FXML private Label usernameLabel;
    @FXML private VBox loggedInSection;
    @FXML private HBox loginSection;

        @FXML
        public void handleStartApplication(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("application-view.fxml"));
                Parent root = loader.load();
                ApplicationController controller = loader.getController();
                controller.setUsername(usernameLabel.getText().replace("Welcome, ", ""));
                Stage stage = (Stage) startApplicationButton.getScene().getWindow();
                stage.setResizable(true);
                stage.setWidth(800);
                stage.setHeight(600);
                stage.centerOnScreen();
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    @FXML
    private void handleRegister() {
        showLoginRegisterDialog("Register");
    }

    @FXML
    private void handleLogin() {
        showLoginRegisterDialog("Login");
    }

    @FXML
    private void handleLogout() {
        loggedInSection.setVisible(false);
        loggedInSection.setManaged(false);
        loginSection.setVisible(true);
        loginSection.setManaged(true);
    }

    @FXML
    private void handleExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Goodbye");
            alert.setHeaderText("Thank you for using Travel Diary");
            alert.showAndWait();
            System.exit(0);
        } else {
            alert.close();
        }
    }

    public void updateHomeView(String username) {
        loginSection.setVisible(false);
        loginSection.setManaged(false);
        usernameLabel.setText("Welcome, " + username);
        loggedInSection.setVisible(true);
        loggedInSection.setManaged(true);
    }

    private void showLoginRegisterDialog(String action) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-register-dialog.fxml"));
            Scene scene = new Scene(loader.load(), 400, 300);
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(scene);
            dialogStage.setTitle(action);

            // Get the current stage
            Stage currentStage = (Stage) startApplicationButton.getScene().getWindow();

            // Calculate the center position for the dialog stage
            double centerXPosition = currentStage.getX() + currentStage.getWidth() / 2d - 200; // 200 is half the width of the dialog
            double centerYPosition = currentStage.getY() + currentStage.getHeight() / 2d - 150; // 150 is half the height of the dialog

            // Set the dialog position
            dialogStage.setX(centerXPosition);
            dialogStage.setY(centerYPosition);

//             Show the dialog and wait
                LoginRegisterController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setPortalController(this);
                controller.initData(action);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
