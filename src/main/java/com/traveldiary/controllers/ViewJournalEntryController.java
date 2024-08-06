package com.traveldiary.controllers;

import com.traveldiary.models.JournalEntry;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ViewJournalEntryController {

    @FXML
    private Label titleLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label contentLabel;
    @FXML
    private ImageView imageView;

    public void setJournalEntry(JournalEntry entry) {
        titleLabel.setText(entry.getTitle());
        dateLabel.setText(entry.getDate());
        contentLabel.setText(entry.getContent());
        if (entry.getImagePath() != null && !entry.getImagePath().isEmpty()) {
            imageView.setImage(new Image(entry.getImagePath()));
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.close();
    }
}
