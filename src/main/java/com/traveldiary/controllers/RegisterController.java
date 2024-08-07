package com.traveldiary.controllers;

import com.traveldiary.models.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    private final UserManager userManager = UserManager.getInstance();

    @FXML
    private void handleRegister(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Registration Error", "Passwords do not match");
            return;
        }

        if (userManager.registerUser(username, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "User registered successfully");
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.close();
        } else {
            showAlert(Alert.AlertType.ERROR, "Registration Error", "Username already exists");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
