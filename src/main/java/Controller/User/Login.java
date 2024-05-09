package Controller.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entities.User.User;
import Services.UserService.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

public class Login {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField email;

    @FXML
    private Label lblErrors;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;

    @FXML
    void Registre(ActionEvent event) {
        try {
            // Get the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/registre.fxml"));
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
    void forgotPassword(ActionEvent event) {
        try {
            // Get the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForgetPassword.fxml"));
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
    void handleButtonAction(MouseEvent event) {

    }

    @FXML
    void login(ActionEvent event) {  String userEmail = email.getText();

        String pass = password.getText();
        if (!isValidEmail(userEmail)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter a valid email address.");
            return;
        }
        UserService x = new UserService();

        // Fetch user data from the database
        User user = x.getUserByEmail(userEmail);


        // Check if the user exists and the password matches
        if (user != null && BCrypt.checkpw(pass, user.getMdp()) ) {
            SessionManager.setCurrentUser(user);
            String userRole = x.getUserRoleByEmail(userEmail);

            if (userRole != null && (userRole.equals("ADMIN"))) {
                switchScene("/index-user.fxml", event);
                showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome Admin!");
            } else {
                switchScene("/dashi.fxml", event);
                showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome " + user.getPrenom() + "!");
            }


        } else {
            // Show error message for invalid email or password
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid email or password!");
        }

    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

    @FXML
    void initialize() {

    }

}
