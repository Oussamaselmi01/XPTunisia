package Controller.User;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import Entities.User.User;
import Services.UserService.UserService;
import Services.UserService.IService;
import javafx.stage.Stage;

public class UpdateUserController {
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
    private TextField numTelTextField;

    @FXML
    private TextField roleTextField;

    private IService<User> userService = new UserService();

    private IndexUserController indexUserController = new IndexUserController();

    private User selectedUser;

    public void setSelectedUser(User user) {
        this.selectedUser = user;
        if (selectedUser != null) {
            cinTextField.setText(selectedUser.getCin());
            nomTextField.setText(selectedUser.getNom());
            prenomTextField.setText(selectedUser.getPrenom());
            mdpTextField.setText(selectedUser.getMdp());
            emailTextField.setText(selectedUser.getEmail());
            adresseTextField.setText(selectedUser.getAdresse());
            numTelTextField.setText(String.valueOf(selectedUser.getNumtel()));
            roleTextField.setText(selectedUser.getRole());
        }else{System.out.println("user is null");}
    }

    public void setIndexUserController(IndexUserController controller) {
        this.indexUserController = controller;
    }

//    @FXML
//    private void initialize() {
//        setIndexUserController(indexUserController);
//
//
//
//    }

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

        if (numTelTextField.getText() == null || numTelTextField.getText().isEmpty()) {
            errorMessage += "Numéro de téléphone field is empty!\n";
        } else {
            // Check if numtel is numeric
            try {
                Integer.parseInt(numTelTextField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Numéro de téléphone must be a valid number!\n";
            }
        }

        if (roleTextField.getText() == null || roleTextField.getText().isEmpty()) {
            errorMessage += "Role field is empty!\n";
        }
        else{
            String role = roleTextField.getText().trim(); // Trim to remove leading and trailing spaces
            if (!(role.equals("admin") || role.equals("user"))) {
                errorMessage += "Role must be 'admin' or 'user'!\n";
            }
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlertDialog(Alert.AlertType.ERROR, "Error", "Failed to update user", errorMessage);
            return false;
        }
    }

    @FXML
    private void handleUpdate() {
        if (isInputValid()) {
            // Get data from fields
            String cin = cinTextField.getText();
            String nom = nomTextField.getText();
            String prenom = prenomTextField.getText();
            String mdp = mdpTextField.getText();
            String email = emailTextField.getText();
            String adresse = adresseTextField.getText();
            int numTel = Integer.parseInt(numTelTextField.getText());
            String role = roleTextField.getText();

            // Update selected user with new data
            selectedUser.setCin(cin);
            selectedUser.setNom(nom);
            selectedUser.setPrenom(prenom);
            selectedUser.setMdp(mdp);
            selectedUser.setEmail(email);
            selectedUser.setAdresse(adresse);
            selectedUser.setNumtel(numTel);
            selectedUser.setRole(role);

            // Update user in database
            userService.update(selectedUser);

            // Close the update window and refresh the user list
            Stage stage = (Stage) cinTextField.getScene().getWindow();
            if (indexUserController != null) {
                indexUserController.refreshTableView();
            }
            stage.close();
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    private void showAlertDialog(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
