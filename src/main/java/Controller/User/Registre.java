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

public class Registre {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField Adresse;

    @FXML
    private TextField cin;

    @FXML
    private TextField email;

    @FXML
    private Label lblErrors;

    @FXML
    private Button loginButton;

    @FXML
    private TextField nom;

    @FXML
    private PasswordField password;

    @FXML
    private TextField prenom;

    @FXML
    private TextField telephone;

    @FXML
    void Login(ActionEvent event) {
        try {
            // Get the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
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
    void Registre(ActionEvent event) {
        String cinText = cin.getText();
        String nomText = nom.getText();
        String prenomText = prenom.getText();
        String passwordText = password.getText();
        String emailText = email.getText();
        String adresseText = Adresse.getText();
        String numtelText = telephone.getText();// Assuming telephone is an integer

        UserService userService = new UserService();

        // Check if any field is empty
        if (cinText.isEmpty() || nomText.isEmpty() || prenomText.isEmpty() || passwordText.isEmpty() || emailText.isEmpty() || adresseText.isEmpty() || numtelText.isEmpty()) {
            displayAlert("Error", "All fields are required");
            return;
        }

        // Check if CIN is numeric with 8 characters
        if (!isNumeric(cinText) || cinText.length() != 8) {
            displayAlert("Error", "CIN must be numeric with 8 characters");
            return;
        }
        if (!isValidEmail(emailText)) {
            displayAlert( "Invalid Email", "Please enter a valid email address.");
            return;
        }

        if (userService.emailExists(emailText)) {
            displayAlert("Error", "Email already exists");
            return;
        }



        // Check if phone number is numeric with 8 characters
        if (!isNumeric(numtelText) || String.valueOf(numtelText).length() != 8) {
            displayAlert("Error", "Phone number must be numeric with 8 characters");
            return;
        }

        // Assuming role is constant for registration, you might need a way to set it
        String role = "USER";
        String hashedPassword = hashPassword(passwordText);

        // Create a new user object
        User user = new User(cinText, nomText, prenomText, hashedPassword, emailText, adresseText, Integer.parseInt(numtelText), role);

        // Insert the user using UserService

        userService.insert(user);

        // Provide feedback to the user
        displayAlert("Success", "User registered successfully!");
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


    private void displayAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @FXML
    void handleButtonAction(MouseEvent event) {

    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
    @FXML
    void initialize() {

    }

}
