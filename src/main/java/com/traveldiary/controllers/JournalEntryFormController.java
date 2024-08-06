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
import java.io.IOException;

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

    private JournalEntryManager journalEntryManager = JournalEntryManager.getInstance();
    private JournalEntry currentEntry;
    private String currentUser;

    @FXML
    private void initialize() {
        currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentEntry != null) {
            setJournalEntry(currentEntry); // Populate fields if editing
        }
    }

    @FXML
    private void handleSave() {
        // Ensure all fields are properly initialized
        if (titleField == null || dateField == null || contentArea == null || imagePathField == null || saveButton == null) {
            System.err.println("One or more UI elements are not initialized.");
            return;
        }

        // Retrieve data from fields
        String title = titleField.getText();
        String date = dateField.getText();
        String content = contentArea.getText();
        String imagePath = imagePathField.getText();

        // Validate fields
        if (title.isEmpty() || date.isEmpty() || content.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill in all required fields.");
            return;
        }

        // Create or update the journal entry
        if (currentEntry == null) {
            // Create a new JournalEntry if none exists
            currentEntry = new JournalEntry(title, date, content, imagePath, currentUser);
        } else {
            // Update the existing JournalEntry
            currentEntry.setTitle(title);
            currentEntry.setDate(date);
            currentEntry.setContent(content);
            currentEntry.setImagePath(imagePath);
        }

        // Save the entry using JournalEntryManager
        boolean success = journalEntryManager.saveEntry(currentEntry);
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Journal entry saved successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Save Error", "An error occurred while saving the journal entry.");
        }

        // Close the form
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
            // Set image path in TextField
            imagePathField.setText(file.getAbsolutePath());

            // Load image into ImageView
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }

    @FXML
    private void handleDelete() {
        if (currentEntry == null) {
            showAlert(Alert.AlertType.ERROR, "Delete Error", "No entry selected to delete.");
            return;
        }

        // Confirm deletion
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this entry?");
        confirmation.setTitle("Confirm Deletion");
        confirmation.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                boolean success = journalEntryManager.deleteEntry(String.valueOf(currentEntry));
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Journal entry deleted successfully.");
                    Stage stage = (Stage) deleteButton.getScene().getWindow();
                    stage.close();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Delete Error", "An error occurred while deleting the journal entry.");
                }
            }
        });
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
