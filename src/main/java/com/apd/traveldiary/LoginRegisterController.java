package com.apd.traveldiary;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LoginRegisterController {

    private static final Map<String, String> userDatabase = new HashMap<>();

    static {
        userDatabase.put("admin", "admin");
    }

    private String action;
    private Stage dialogStage;
    private MainController portalController;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button actionButton;

    @FXML
    private Label statusLabel;

    public void setPortalController(MainController portalController) {
        this.portalController = portalController;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void initData(String action) {
        this.action = action;
        actionButton.setText(action);
    }

    @FXML
    private void handleAction() {
        if ("Register".equals(action)) {
            handleRegister();
        } else if ("Login".equals(action)) {
            handleLogin();
        }
    }

    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Username and password cannot be empty.");
        } else if (userDatabase.containsKey(username)) {
            statusLabel.setText("Username already exists.");
        } else if (password.length() < 8) {
            statusLabel.setText("Password must be at least 8 characters long.");
        } else if (!password.matches(".*\\d.*")) {
            statusLabel.setText("Password must contain at least one digit.");
        } else if (!password.matches(".*[a-z].*") || !password.matches(".*[A-Z].*")) {
            statusLabel.setText("Password must contain at least one uppercase and one lowercase letter.");
        } else {
            userDatabase.put(username, password);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(dialogStage);
            alert.setTitle("Registration Successful");
            alert.setHeaderText(null);
            alert.setContentText("User registered successfully. Would you like to log in as " + username + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                loginUser(username, password);
            }
            closeDialog();
        }
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        loginUser(username, password);
    }

    private void loginUser(String username, String password) {
        if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(dialogStage);
            alert.setTitle("Login Successful");
            alert.setHeaderText(null);
            alert.setContentText("Login successful.");
            alert.showAndWait();
            portalController.updateHomeView(username);
            closeDialog();
        } else {
            statusLabel.setText("Invalid username or password.");
        }
    }

    private void closeDialog() {
        dialogStage.close();
    }
}