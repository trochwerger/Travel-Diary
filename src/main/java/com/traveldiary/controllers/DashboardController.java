package com.traveldiary.controllers;

import com.traveldiary.models.JournalEntry;
import com.traveldiary.models.JournalEntryManager;
import com.traveldiary.models.SessionManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private ListView<JournalEntry> journalListView;

    private JournalEntryManager journalEntryManager = JournalEntryManager.getInstance();
    private String currentUser;

    @FXML
    private void initialize() {
        currentUser = SessionManager.getInstance().getCurrentUser();
        loadJournalEntries();
    }

    private void loadJournalEntries() {
        journalListView.setItems(FXCollections.observableArrayList(journalEntryManager.getJournalEntries(currentUser)));
    }

    @FXML
    private void handleAdd() {
        openJournalEntryForm(null);
    }

    @FXML
    private void handleUpdate() {
        JournalEntry selectedEntry = journalListView.getSelectionModel().getSelectedItem();
        if (selectedEntry == null) {
            showAlert(Alert.AlertType.ERROR, "Selection Error", "No journal entry selected.");
            return;
        }
        openJournalEntryForm(selectedEntry);
    }

    @FXML
    private void handleDelete() {
        JournalEntry selectedEntry = journalListView.getSelectionModel().getSelectedItem();
        if (selectedEntry == null) {
            showAlert(Alert.AlertType.ERROR, "Selection Error", "No journal entry selected.");
            return;
        }
        journalEntryManager.deleteJournalEntry(currentUser, selectedEntry);
        journalListView.getItems().remove(selectedEntry);
    }

    @FXML
    private void handleView() {
        JournalEntry selectedEntry = journalListView.getSelectionModel().getSelectedItem();
        if (selectedEntry == null) {
            showAlert(Alert.AlertType.ERROR, "Selection Error", "No journal entry selected.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/traveldiary/views/ViewJournalEntry.fxml"));
            Parent root = loader.load();
            ViewJournalEntryController controller = loader.getController();
            controller.setJournalEntry(selectedEntry);
            Stage stage = new Stage();
            stage.setTitle("View Journal Entry");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        MainController mainController = new MainController();
        mainController.setPrimaryStage((Stage) journalListView.getScene().getWindow());
        mainController.loadWelcome();
    }

    @FXML
    private void handleExit() {
        Stage stage = (Stage) journalListView.getScene().getWindow();
        stage.close();
    }

    private void openJournalEntryForm(JournalEntry entry) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/traveldiary/views/JournalEntryForm.fxml"));
            Parent root = loader.load();
            JournalEntryFormController controller = loader.getController();
            controller.setJournalEntry(entry);
            Stage stage = new Stage();
            stage.setTitle(entry == null ? "Add Journal Entry" : "Update Journal Entry");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            // Refresh the list view
            loadJournalEntries();
        } catch (Exception e) {
            e.printStackTrace();
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
