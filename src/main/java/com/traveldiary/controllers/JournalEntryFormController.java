package com.traveldiary.controllers;

import com.traveldiary.models.JournalEntry;
import com.traveldiary.models.JournalEntryManager;
import com.traveldiary.models.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class JournalEntryFormController {

    @FXML
    private TextField titleField;
    @FXML
    private TextField dateField;
    @FXML
    private TextArea contentArea;
    @FXML
    private ImageView imageView;

    private String imagePath;
    private JournalEntry entry;

    private JournalEntryManager journalEntryManager = JournalEntryManager.getInstance();
    private String currentUser = SessionManager.getInstance().getCurrentUser();

    public void setJournalEntry(JournalEntry entry) {
        this.entry = entry;
        if (entry != null) {
            titleField.setText(entry.getTitle());
            dateField.setText(entry.getDate());
            contentArea.setText(entry.getContent());
            if (entry.getImagePath() != null && !entry.getImagePath().isEmpty()) {
                imageView.setImage(new Image(entry.getImagePath()));
                imagePath = entry.getImagePath();
            }
        }
    }

    @FXML
    private void handleAddImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                File imagesDir = new File("images");
                if (!imagesDir.exists()) {
                    imagesDir.mkdirs();
                }

                File destFile = new File(imagesDir, selectedFile.getName());
                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                imagePath = destFile.toURI().toString();
                imageView.setImage(new Image(imagePath));
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Image Error", "Could not add image: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleSave() {
        String title = titleField.getText();
        String date = dateField.getText();
        String content = contentArea.getText();
        if (title.isEmpty() || date.isEmpty() || content.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Title, Date, and Content cannot be empty.");
            return;
        }
        if (entry == null) {
            JournalEntry newEntry = new JournalEntry(title, date, content, imagePath);
            journalEntryManager.addJournalEntry(currentUser, newEntry);
        } else {
            entry.setTitle(title);
            entry.setDate(date);
            entry.setContent(content);
            entry.setImagePath(imagePath);
            journalEntryManager.updateJournalEntry(currentUser, entry, entry);
        }
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
