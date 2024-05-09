package Controller.User;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Entities.User.User;
import Services.UserService.UserService;
import Services.UserService.IService;

public class AddUserController {
    @FXML
    private TextField cinTextField;

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField prenomTextField;

    @FXML
    private TextField mdpTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField adresseTextField;

    @FXML
    private TextField numtelTextField;

    @FXML
    private TextField roleTextField;

    private IService<User> userService = new UserService();

    private IndexUserController indexUserController = new IndexUserController();

    public void setIndexUserController(IndexUserController controller) {
        this.indexUserController = controller;
    }

    @FXML
    private void initialize() {
        setIndexUserController(indexUserController);
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (cinTextField.getText() == null || cinTextField.getText().isEmpty()) {
            errorMessage += "CIN field is empty!\n";
        }

        if (nomTextField.getText() == null || nomTextField.getText().isEmpty()) {
            errorMessage += "Nom field is empty!\n";
        }

        if (prenomTextField.getText() == null || prenomTextField.getText().isEmpty()) {
            errorMessage += "Prenom field is empty!\n";
        }

        if (mdpTextField.getText() == null || mdpTextField.getText().isEmpty()) {
            errorMessage += "Mot de passe field is empty!\n";
        }

        if (emailTextField.getText() == null || emailTextField.getText().isEmpty()) {
            errorMessage += "Email field is empty!\n";
        } else {
            // Check if email is in valid email format
            String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
            if (!emailTextField.getText().matches(emailPattern)) {
                errorMessage += "Invalid email format!\n";
            }
        }

        if (adresseTextField.getText() == null || adresseTextField.getText().isEmpty()) {
            errorMessage += "Adresse field is empty!\n";
        }

        if (roleTextField.getText() == null || roleTextField.getText().isEmpty()) {
            errorMessage += "Role field is empty!\n";
        }
        else{
            String role = roleTextField.getText().toUpperCase();
            if (!role.equals("ADMIN") && !role.equals("USER") ){
                errorMessage += "Role must be 'admin' or 'user' !\n";
            }
        }

        if (numtelTextField.getText() == null || numtelTextField.getText().isEmpty()) {
            errorMessage += "Numéro de téléphone field is empty!\n";
        } else {
            // Check if numtel is numeric
            try {
                Integer.parseInt(numtelTextField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Numéro de téléphone must be a valid number!\n";
            }
        }

        if (roleTextField.getText() == null || roleTextField.getText().isEmpty()) {
            errorMessage += "Role field is empty!\n";
        }


        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlertDialog(Alert.AlertType.ERROR, "Error", "Validation Error", errorMessage);
            return false;
        }
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            // Get data from fields
            String cin = cinTextField.getText();
            String nom = nomTextField.getText();
            String prenom = prenomTextField.getText();
            String mdp = mdpTextField.getText();
            String email = emailTextField.getText();
            String adresse = adresseTextField.getText();
            int numtel = Integer.parseInt(numtelTextField.getText());
            String role = roleTextField.getText();

            // Create new User object
            User newUser = new User(cin, nom, prenom, mdp, email, adresse, numtel, role);

            // Insert new User into database
            userService.insert(newUser);

            Stage stage = (Stage) cinTextField.getScene().getWindow();
            if (indexUserController != null) {
                indexUserController.refreshTableView();
            }
            stage.close();
        }
    }

    private void showAlertDialog(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
