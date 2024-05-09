package Controller.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entities.User.VerificationRequest;
import Services.UserService.VerificationRequestService;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VerificationPage {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Back;

    @FXML
    private TableColumn<VerificationRequest, String> Confirmation;

    @FXML
    private Button DeleteVerification;

    @FXML
    private TableColumn<VerificationRequest, String> User;

    @FXML
    private TableView<VerificationRequest> panierTableView;

    @FXML
    private Text title;

    private final VerificationRequestService verificationRequestService = new VerificationRequestService();
    private final ObservableList<VerificationRequest> verificationRequests = FXCollections.observableArrayList();


    @FXML
    void initialize() {
        // Set cell value factories for the table columns
        User.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getNom() + ' ' + cellData.getValue().getUser().getPrenom()));
        Confirmation.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isConfirmed() ? "Confirmed" : "Not Confirmed"));
        // Load verification requests from the database
        loadVerificationRequests();
    }


    @FXML
    void Confirme(ActionEvent event) {
        // Get the selected verification request
        VerificationRequest selectedRequest = panierTableView.getSelectionModel().getSelectedItem();
        if (selectedRequest == null) {
            // If no request is selected, show an error message
            // You can customize this message or handle it differently
            System.out.println("No verification request selected.");
            return;
        }
        // Check if the request is already confirmed
        if (selectedRequest.isConfirmed()) {
            // If the request is already confirmed, show an alert to the admin
            showAlert(Alert.AlertType.ERROR, "Error", "The selected request is already confirmed.");
            return;
        }

        // Confirm the selected request
        selectedRequest.setConfirmed(true);
        verificationRequestService.update(selectedRequest);

        // Reload the verification requests in the table view
        loadVerificationRequests();
    }

    private void loadVerificationRequests() {
        // Clear existing data
        verificationRequests.clear();

        // Load verification requests from the service
        verificationRequests.addAll(verificationRequestService.getAll());

        // Set the items in the table view
        panierTableView.setItems(verificationRequests);
    }

    @FXML
    void DeleteVerification(ActionEvent event) {
        VerificationRequest selectedRequest = panierTableView.getSelectionModel().getSelectedItem();
        if (selectedRequest == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a verification request to delete.");
            return;
        }

        VerificationRequestService verificationService = new VerificationRequestService();
        verificationService.delete(selectedRequest.getId());
        // Refresh the table view after deletion
        loadVerificationRequests();
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void back(ActionEvent event) {
        try {
            // Get the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/index-user.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Close the current stage
            currentStage.close();

            // Show the new stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    }


