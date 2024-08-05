package com.apd.traveldiary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;

public class ApplicationController {

    @FXML private ListView<String> entriesList;
    @FXML private Label usernameLabel;
    @FXML private Button logoutButton;

//    sample entries for the list
    private final ObservableList<String> entries = FXCollections.observableArrayList("Entry 1", "Entry 2", "Entry 3", "Entry 4", "Entry 5");

    @FXML
    public void initialize() {
        entriesList.setItems(entries);
    }
//    @FXML private ListView<String> companiesList;
    



//    private final ObservableList<String> skillsList = FXCollections.observableArrayList();
//    private final List<Applicant> applicants = new ArrayList<>();
//    private final List<JobRole> jobRoles = new ArrayList<>();

//    @FXML
//    public void initialize() {
//        jobRoles.add(new JobRole("Software Developer", "Develop and maintain software applications."));
//        jobRoles.add(new JobRole("Data Analyst", "Analyze and interpret complex data sets."));
//        jobRoles.add(new JobRole("Project Manager", "Oversee and coordinate project activities."));
//
//        companiesList.getItems().addAll("Google", "Microsoft", "Apple", "Amazon", "Facebook");
//
//        for (JobRole jobRole : jobRoles) {
//            jobRolesList.getItems().add(jobRole.getTitle());
//            jobRoleComboBox.getItems().add(jobRole.getTitle());
//        }

//        skillsListView.setItems(skillsList);
//        skillsListView.setOnMouseClicked(mouseEvent -> {
//            if (mouseEvent.getClickCount() == 2) {
//                String selectedSkill = skillsListView.getSelectionModel().getSelectedItem();
//                editSkill(selectedSkill);
//            }
//        });
//    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUsername(String username) {
        usernameLabel.setText(username);
    }

//    @FXML
//    private void handleAddSkill() {
//        String newSkill = showSkillDialog(null);
//        if (newSkill != null) {
//            skillsList.add(newSkill);
//        }
//    }

//    private void editSkill(String skill) {
//        String editedSkill = showSkillDialog(skill);
//        if (editedSkill != null) {
//            int index = skillsList.indexOf(skill);
//            skillsList.set(index, editedSkill);
//        }
//    }

//    private String showSkillDialog(String skill) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("skill-dialog.fxml"));
//            AnchorPane page = loader.load();
//
//            Stage dialogStage = new Stage();
//            dialogStage.setTitle(skill == null ? "Add Skill" : "Edit Skill");
//            dialogStage.initModality(Modality.WINDOW_MODAL);
//            dialogStage.setScene(new Scene(page));
//
//            // Get the current stage
//            Stage currentStage = (Stage) nameField.getScene().getWindow();
//
//            dialogStage.initOwner(currentStage);
//
////            SkillDialogController controller = loader.getController();
////            controller.setDialogStage(dialogStage);
////            if (skill != null) {
////                controller.skillTextField.setText(skill);
////            }
//
//            dialogStage.showAndWait();
//            // Calculate the center position for the dialog stage
//            double centerXPosition = currentStage.getX() + currentStage.getWidth() / 2d - dialogStage.getWidth() / 2d;
//            double centerYPosition = currentStage.getY() + currentStage.getHeight() / 2d - dialogStage.getHeight() / 2d;
//
//            // Set the dialog position
//            dialogStage.setX(centerXPosition);
//            dialogStage.setY(centerYPosition);
//
////            return controller.isOkClicked() ? controller.getSkill() : null;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

//    @FXML
//    private void handleAddCompany() {
//        String company = companiesList.getSelectionModel().getSelectedItem();
//        if (company != null) {
//            if (interestedCompaniesList.getItems().contains(company)) {
//                showAlert("Error", "Company already added!");
//                return;
//            }
//            interestedCompaniesList.getItems().add(company);
//        }
//    }
//
//    @FXML
//    private void handleRemoveCompany() {
//        String company = interestedCompaniesList.getSelectionModel().getSelectedItem();
//        if (company != null) {
//            interestedCompaniesList.getItems().remove(company);
//        }
//    }

//    @FXML
//    private void handleSubmitApplication() {
//        String name = nameField.getText();
//        String phone = phoneField.getText();
//        String selectedJobRole = jobRoleComboBox.getSelectionModel().getSelectedItem();
//
//        if (name.isEmpty() || phone.isEmpty() || selectedJobRole == null) {
//            showAlert("Error", "Name, Phone number, and Job Role are required!");
//            return;
//        }
//
//        Applicant applicant = new Applicant(name, phone, selectedJobRole);
//        applicant.getSkills().addAll(skillsList);
//        applicant.getInterestedCompanies().addAll(interestedCompaniesList.getItems());
//
//        applicants.add(applicant);
//        confirmApplication(applicant);
//    }

//    @FXML
//    private void handleViewDetails() {
//        String selectedJobRoleTitle = jobRolesList.getSelectionModel().getSelectedItem();
//        if (selectedJobRoleTitle != null) {
//            for (JobRole jobRole : jobRoles) {
//                if (jobRole.getTitle().equals(selectedJobRoleTitle)) {
//                    jobDetailsArea.setText(jobRole.getDescription());
//                    break;
//                }
//            }
//        }
//    }

    @FXML
    private void handleClear() {
        clearForm();
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    private void clearForm() {
        jobDetailsArea.clear();
        interestedCompaniesList.getItems().clear();
    }

    public void handleViewDetails(ActionEvent actionEvent) {
        String selectedEntry = entriesList.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Entry Details");
        alert.setHeaderText("Entry Details: " + selectedEntry);
        alert.showAndWait();
        
    }

    public void handleDelete(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Entry");
        alert.setHeaderText("Still in Development");
        alert.showAndWait();
    }

//    private void showAlert(String title, String message) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.initOwner(nameField.getScene().getWindow());
//
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }

//    private void confirmApplication(Applicant applicant) {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.initOwner(nameField.getScene().getWindow());
//        alert.setTitle("Application Details");
//        alert.setHeaderText("Applicant Information");
//        alert.setContentText("Applicant Name: " + applicant.getName() + "\n" +
//                "Applicant Phone: " + applicant.getPhoneNumber() + "\n" +
//                (!applicant.getSkills().isEmpty() ? "Applicant Skills: " + String.join(", ", applicant.getSkills()) + "\n" : "") +
//                "Applicant Job Role: " + applicant.getJobRole() + "\n" +
//                (!applicant.getInterestedCompanies().isEmpty() ? "Applicant Interested Companies: " + String.join(", ", applicant.getInterestedCompanies()) : ""));
//
//        alert.showAndWait();
//
//        if (alert.getResult().getButtonData().isCancelButton()) {
//            showAlert("Application Canceled", "");
//        } else {
//            showAlert("Success", "Application submitted successfully!");
//            logApplicant(applicant);
//            clearForm();
//            promptForAnotherApplication();
//        }
//    }

//    private void logApplicant(Applicant applicant) {
//        System.out.println("Applicant Name: " + applicant.getName());
//        System.out.println("Applicant Phone: " + applicant.getPhoneNumber());
//        if (!applicant.getSkills().isEmpty()) {
//            System.out.println("Applicant Skills: " + String.join(", ", applicant.getSkills()));
//        }
//        System.out.println("Applicant Job Role: " + applicant.getJobRole());
//        if (!applicant.getInterestedCompanies().isEmpty()) {
//            System.out.println("Applicant Interested Companies: " + String.join(", ", applicant.getInterestedCompanies()));
//        }
//    }

//    private void promptForAnotherApplication() {
//        Alert alertContinue = new Alert(Alert.AlertType.CONFIRMATION);
//        alertContinue.initOwner(nameField.getScene().getWindow());
//        alertContinue.setTitle("Submit Another Application?");
//        alertContinue.setHeaderText("Do you want to submit another application?");
//        ButtonType buttonTypeOne = new ButtonType("Yes");
//        ButtonType buttonTypeTwo = new ButtonType("No");
//        alertContinue.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
//        alertContinue.showAndWait();
//        if (alertContinue.getResult() == buttonTypeTwo) {
//            showAlert("Goodbye", "Thank you for using the Job Application Portal!");
//            System.exit(0);
//        } else {
//            clearForm();
//        }
//    }
}