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

    @FXML
    private void handleClose() {
        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.close();
    }

    public void setJournalEntry(JournalEntry entry) {
        if (entry != null) {
            titleLabel.setText(entry.getTitle());
            dateLabel.setText(entry.getDate());
            contentLabel.setText(entry.getContent());

            if (entry.getImagePath() != null && !entry.getImagePath().isEmpty()) {
                Image image = new Image("file:" + entry.getImagePath());
                imageView.setImage(image);
            } else {
                imageView.setImage(null);
            }
        }
    }
}
