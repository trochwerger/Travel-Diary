package com.traveldiary.controllers;

import com.traveldiary.models.JournalEntry;
import com.traveldiary.models.JournalEntryManager;
import com.traveldiary.models.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;

public class JournalEntryFormController {

    @FXML
    private TextField titleField;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField dateField;

    @FXML
    private TextArea contentArea;

    @FXML
    private TextField imagePathField;

    @FXML
    private Button saveButton;

    @FXML
    private Button deleteButton;

    private final JournalEntryManager journalEntryManager = JournalEntryManager.getInstance();
    private JournalEntry currentEntry;
    private String currentUser;

    @FXML
    private void initialize() {
        currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentEntry != null) {
            setJournalEntry(currentEntry);
        }
    }

    @FXML
    private void handleSave() {
        if (titleField == null || dateField == null || contentArea == null || imagePathField == null || saveButton == null) {
            System.err.println("One or more UI elements are not initialized.");
            return;
        }

        String title = titleField.getText();
        String date = dateField.getText();
        String content = contentArea.getText();
        String imagePath = imagePathField.getText();

        if (title.isEmpty() || date.isEmpty() || content.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill in all required fields.");
            return;
        }

        if (currentEntry == null) {
            currentEntry = new JournalEntry(title, date, content, imagePath, currentUser);
        } else {
            currentEntry.setTitle(title);
            currentEntry.setDate(date);
            currentEntry.setContent(content);
            currentEntry.setImagePath(imagePath);
        }

        boolean success = journalEntryManager.saveEntry(currentEntry);
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Journal entry saved successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Save Error", "An error occurred while saving the journal entry.");
        }

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleAddImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            imagePathField.setText(file.getAbsolutePath());
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }

    public void setJournalEntry(JournalEntry entry) {
        this.currentEntry = entry;
        if (entry != null) {
            titleField.setText(entry.getTitle());
            dateField.setText(entry.getDate());
            contentArea.setText(entry.getContent());
            imagePathField.setText(entry.getImagePath());
            if (entry.getImagePath() != null && !entry.getImagePath().isEmpty()) {
                Image image = new Image(new File(entry.getImagePath()).toURI().toString());
                imageView.setImage(image);
            } else {
                imageView.setImage(null);
            }
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
