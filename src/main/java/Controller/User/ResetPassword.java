package Controller.User;

import java.net.URL;
import java.util.ResourceBundle;

import Services.UserService.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

public class ResetPassword {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField ConfirmePassword;

    @FXML
    private TextField Password;

    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }




    @FXML
    void resetPassword(ActionEvent event) {
        String newPassword = Password.getText();
        String confirmPassword = ConfirmePassword.getText();

        // Check if new password and confirm password match
        if (!newPassword.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Error", "New password and confirm password do not match.");
            return;
        }


        // Get the email from the previous scene (you need to pass it from the ForgotPassword controller)
        System.out.println("Email from ForgotPassword: " + email);
        // Update the password in the database
        UserService crudUtilisateurs = new UserService();
        crudUtilisateurs.resetPassword(email, hashPassword(newPassword));

        showAlert(Alert.AlertType.INFORMATION, "Success", "Password reset successfully.");
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

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void initialize() {

    }

}
