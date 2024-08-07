package com.traveldiary.controllers;

import com.traveldiary.models.JournalEntry;
import com.traveldiary.models.JournalEntryManager;
import com.traveldiary.models.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DashboardController {

    @FXML
    private ListView<JournalEntry> journalEntryListView;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button viewButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button exitButton;

    private JournalEntryManager journalEntryManager = JournalEntryManager.getInstance();
    private String currentUser;

    @FXML
    private void initialize() {
        currentUser = SessionManager.getInstance().getCurrentUser();
        loadJournalEntries();

        // Customize ListView to display only name and date
        journalEntryListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(JournalEntry entry, boolean empty) {
                super.updateItem(entry, empty);
                if (empty || entry == null) {
                    setText(null);
                } else {
                    setText(entry.getTitle() + " - " + entry.getDate().toString());
                }
            }
        });
    }

    private void loadJournalEntries() {
        List<JournalEntry> entries = journalEntryManager.getJournalEntries(currentUser);
        journalEntryListView.getItems().setAll(entries);
    }

    @FXML
    private void handleAddButtonAction() {
        showJournalEntryForm(null);
    }

    @FXML
    private void handleUpdateButtonAction() {
        JournalEntry selectedEntry = journalEntryListView.getSelectionModel().getSelectedItem();
        if (selectedEntry != null) {
            showJournalEntryForm(selectedEntry);
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an entry to update.");
        }
    }

    @FXML
    private void handleDeleteButtonAction() {
        JournalEntry selectedEntry = journalEntryListView.getSelectionModel().getSelectedItem();
        if (selectedEntry != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirm Deletion");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Are you sure you want to delete this entry?");
            if (confirmation.showAndWait().orElse(null) == ButtonType.OK) {
                journalEntryManager.deleteEntry(selectedEntry.getTitle());  // Use the entry object itself
                loadJournalEntries();  // Reload entries after deletion
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an entry to delete.");
        }
    }

    @FXML
    private void handleViewButtonAction() {
        JournalEntry selectedEntry = journalEntryListView.getSelectionModel().getSelectedItem();
        if (selectedEntry != null) {
            showViewJournalEntry(selectedEntry);
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an entry to view.");
        }
    }

    @FXML
    private void handleLogoutButtonAction() {
        SessionManager.getInstance().clearSession();
        // Transition to login screen or main application screen
        try {
            Stage stage = (Stage) addButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/traveldiary/views/LoginView.fxml"));
            Parent root = loader.load();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the login screen.");
        }
    }

    @FXML
    private void handleExitButtonAction() {
        // Confirm exit
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Exit");
        confirmation.setHeaderText(null);
        confirmation.setContentText("Are you sure you want to exit the application?");
        if (confirmation.showAndWait().orElse(null) == ButtonType.OK) {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            if (stage != null) {
                stage.close();
            } else {
                System.out.println("Stage is null");
            }
        }
    }

    private void showJournalEntryForm(JournalEntry entry) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/traveldiary/views/JournalEntryForm.fxml"));
            Parent root = loader.load();

            JournalEntryFormController controller = loader.getController();
            controller.setJournalEntry(entry);

            Stage stage = new Stage();
            stage.setTitle(entry == null ? "Add New Entry" : "Edit Entry");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            loadJournalEntries();  // Reload entries after form is closed

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the entry form.");
        }
    }

    private void showViewJournalEntry(JournalEntry entry) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/traveldiary/views/ViewJournalEntry.fxml"));
            Parent root = loader.load();

            ViewJournalEntryController controller = loader.getController();
            controller.setJournalEntry(entry);

            Stage stage = new Stage();
            stage.setTitle("View Journal Entry");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the view entry screen.");
        }
    }

    @FXML
    private void handleListViewMouseClick(MouseEvent event) {
        if (event.getClickCount() == 2) {  // Double-click to view an entry
            JournalEntry selectedEntry = journalEntryListView.getSelectionModel().getSelectedItem();
            if (selectedEntry != null) {
                showViewJournalEntry(selectedEntry);
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
