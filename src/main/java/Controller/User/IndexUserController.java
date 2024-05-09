package Controller.User;

import Entities.User.User;
import Services.UserService.IService;
import Services.UserService.UserService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class IndexUserController {

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> cinColumn;

    @FXML
    private TableColumn<User, String> addresseColumn;


    @FXML
    private TableColumn<User, String> nomColumn;

    @FXML
    private TableColumn<User, String> prenomColumn;

    @FXML
    private TableColumn<User, String> addresseColumnColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private Label labelCIN;

    @FXML
    private Label labelNom;

    @FXML
    private Label labelPrenom;
    @FXML
    private Button actid;
    private Stage stage;
    private Scene scene;
    private Parent root;





    @FXML
    private Label labelEmail;

    private ObservableList<User> userService = FXCollections.observableArrayList();
    private IService<User> userIService = new UserService();

    @FXML
    private void initialize() {
        cinColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCin()));
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        prenomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        addresseColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse()));



        try {
            userService.addAll(userIService.readAll());
            userTable.setItems(userService);
        } catch (Exception e) {
            e.printStackTrace();
            showAlertDialog(Alert.AlertType.ERROR, "Error", "Failed to load users", e.getMessage());
        }
    }


    @FXML
    public void refreshTableView() {
        try {
            userService.clear();
            userService.addAll(userIService.readAll());
        } catch (Exception e) {
            e.printStackTrace();
            showAlertDialog(Alert.AlertType.ERROR, "Database Error", "Failed to fetch users", e.getMessage());
        }
    }

    @FXML
    private void createUser(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/add-user.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Add User");
            stage.setScene(scene);

            AddUserController controller = loader.getController();
            controller.setIndexUserController(this);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteUser(ActionEvent event) {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                userIService.delete(selectedUser.getId());
                refreshTableView();
            } catch (Exception e) {
                e.printStackTrace();
                showAlertDialog(Alert.AlertType.ERROR, "Database Error", "Failed to delete user", e.getMessage());
            }
        } else {
            showAlertDialog(Alert.AlertType.WARNING, "No Selection", "No User Selected!", "Please select a user in the table!");
        }
    }

    @FXML
    private void updateUser(ActionEvent event) {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/update-user.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);

                Stage stage = new Stage();
                stage.setTitle("Update User");
                stage.setScene(scene);

                UpdateUserController controller = loader.getController();
                controller.setIndexUserController(this);
                controller.setSelectedUser(selectedUser);

                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showAlertDialog(Alert.AlertType.WARNING, "No Selection", "No User Selected!", "Please select a user in the table!");
        }
    }

    private void showAlertDialog(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void Verification(ActionEvent event) {
        try {
            // Get the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VerificationPage.fxml"));
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

    @FXML
    void logout(ActionEvent event) {
        // Perform logout actions here
        SessionManager.setCurrentUser(null); // Clear the current user session
        switchScene("/login.fxml", event);

    }
    private void switchScene(String fxmlFile, ActionEvent event) {
        try {
            System.out.println("fxml:"+ fxmlFile);

            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GoToRec(ActionEvent event) {
        try {
            // Get the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reclamation/ReclamtionAdmin.fxml"));
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


    @FXML
    void naviguer1(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Activite/AfficherActivite.fxml"));
            actid.getScene().setRoot(root);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    @FXML
    void naviguer2(ActionEvent event) throws IOException {
        root =FXMLLoader.load(getClass().getResource("/Activite/AfficherCategorie.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void naviguer3(ActionEvent event) throws IOException {
        root =FXMLLoader.load(getClass().getResource("/offre/index-offre.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}

