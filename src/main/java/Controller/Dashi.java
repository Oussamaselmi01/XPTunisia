package Controller;

import Controller.User.SessionManager;
import Entities.User.User;
import Entities.User.VerificationRequest;
import Services.UserService.UserService;
import Services.UserService.VerificationRequestService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Dashi {
    @FXML
    private TextField Adresse;

    @FXML
    private TextField cin;

    @FXML
    private TextField email;

    @FXML
    private Button logoutButton;

    @FXML
    private TextField nom;



    @FXML
    private TextField prenom;



    @FXML
    private TextField telephone;

    @FXML
    private Button updateProfileButton;


    @FXML
    private Label welcomeLabel;

    @FXML
    private ImageView verifiedImg;

    @FXML
    private VBox pnItems = null;


    @FXML
    private Button btnOverview;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnMenus;

    @FXML
    private Button btnPackages;


    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlMenus;
    @FXML
    private Pane pnlPackage;

    @FXML
    private Pane pnlOffre;


    @FXML
    private Label UserName;

    @FXML
    void initialize() {
        User currentUser = SessionManager.getCurrentUser();

        if (currentUser != null) {
            fillUserData(currentUser);
        }
        boolean isConfirmed = checkConfirmationStatus();

        // Set the visibility of the image based on the confirmation status
        verifiedImg.setVisible(isConfirmed);





    }



    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnCustomers) {
            clearAndLoadFXML("/Reclamation/RecalamtionsFront.fxml", pnlCustomer);
        }
        if (actionEvent.getSource() == btnMenus) {
            clearAndLoadFXML("/Activite/AfficherActiviteFront.fxml", pnlMenus);
        }
        if (actionEvent.getSource() == btnOverview) {
            try {
                // Get the current stage
                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                // Load the new FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashi.fxml"));
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
        if (actionEvent.getSource() == btnOrders) {
            clearAndLoadFXML("/Produit/ListeProduit.fxml", pnlOrders);
        }
        if (actionEvent.getSource() == btnPackages) {
            clearAndLoadFXML("/etablissement/index-etablissement.fxml", pnlPackage);
        }
        if (actionEvent.getSource() == btnSettings) {
            clearAndLoadFXML("/offre/OffreFront.fxml", pnlOffre);
        }
    }

    private void loadFXML(String fxmlFilePath, Pane targetPanel) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            Parent root = loader.load();

            // Clear the content of the target panel
            targetPanel.getChildren().clear();

            // Set the loaded content as the only child of the target panel
            targetPanel.getChildren().setAll(root);
            targetPanel.toFront();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void clearAndLoadFXML(String fxmlFilePath, Pane targetPanel) {
        // Clear all panels except the target panel
        clearAllPanelsExcept(targetPanel);

        // Load new content into the target panel
        loadFXML(fxmlFilePath, targetPanel);
    }

    private void clearAllPanelsExcept(Pane targetPanel) {
        // Iterate through all panels and clear them except the target panel
        List<Pane> allPanels = Arrays.asList(pnlCustomer, pnlMenus, pnlOverview, pnlOrders, pnlPackage,pnlOffre);
        for (Pane panel : allPanels) {
            if (panel != targetPanel) {
                panel.getChildren().clear();
            }
        }
    }



    private boolean checkConfirmationStatus() {
        User currentUser = SessionManager.getCurrentUser();


        // If no user is logged in, return false
        if (currentUser == null) {
            return false;
        }

        // Check if there is a verification request for the current user
        VerificationRequestService verificationRequestService = new VerificationRequestService();
        UserService userService = new UserService();
        User user = userService.getUserByEmail(currentUser.getEmail());

        if (user != null) {
            VerificationRequest verificationRequest = verificationRequestService.getByUserId(user.getId());

            // If there is a verification request and it is confirmed, return true
            if (verificationRequest != null && verificationRequest.isConfirmed()) {
                return true;
            }
        }

        // If no verification request found or it's not confirmed, return false
        return false;
    }

    private void fillUserData(User user) {
        cin.setText(user.getCin());
        nom.setText(user.getNom());
        prenom.setText(user.getPrenom());
        email.setText(user.getEmail());
        Adresse.setText(user.getAdresse());
        telephone.setText(String.valueOf(user.getNumtel()));
        welcomeLabel.setText("Welcome, " + user.getPrenom() + "!");
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

    @FXML
    void update(ActionEvent event) {
        User currentUser = SessionManager.getCurrentUser();
        UserService userService = new UserService();
        // Get updated user information from text fields
        String updatedCin = cin.getText();
        String updatedNom = nom.getText();
        String updatedPrenom = prenom.getText();
        String updatedEmail = email.getText();
        String updatedAdresse = Adresse.getText();
        String updatedTelephone = telephone.getText();
        // Check if any field is empty
        if (updatedCin.isEmpty() || updatedNom.isEmpty() || updatedPrenom.isEmpty()  || updatedEmail.isEmpty() || updatedAdresse.isEmpty() || updatedTelephone.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required");
            return;
        }

        // Check if CIN is numeric with 8 characters
        if (!isNumeric(updatedCin) || updatedCin.length() != 8) {
            showAlert(Alert.AlertType.ERROR, "Error", "CIN must be numeric with 8 characters");
            return;
        }

        // Check if email is valid
        if (!isValidEmail(updatedEmail)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter a valid email address.");
            return;
        }

        // Check if the updated email already exists
        // Check if the updated email already exists
        if (!updatedEmail.equalsIgnoreCase(currentUser.getEmail()) && userService.emailExists(updatedEmail)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Email already exists");
            return;
        }

        // Check if phone number is numeric with 8 characters
        if (!isNumeric(updatedTelephone) || updatedTelephone.length() != 8) {
            showAlert(Alert.AlertType.ERROR, "Error", "Phone number must be numeric with 8 characters");
            return;
        }

        // Update user object

        currentUser.setCin(updatedCin);
        currentUser.setNom(updatedNom);
        currentUser.setPrenom(updatedPrenom);
        currentUser.setEmail(updatedEmail);
        currentUser.setAdresse(updatedAdresse);
        currentUser.setNumtel(Integer.parseInt(updatedTelephone));

        // Update user information in the database

        userService.update(currentUser);


        showAlert(Alert.AlertType.INFORMATION, "Profile Updated", "Your profile has been updated successfully!");

    }

    @FXML
    void handleUpdateProfileButtonAction() {

    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void Verifier(ActionEvent event) {
        // Check if the current user has already sent a verification request
        VerificationRequestService verificationRequestService = new VerificationRequestService();
        User currentUser = SessionManager.getCurrentUser();
        VerificationRequest existingRequest = verificationRequestService.getByUserId(currentUser.getId());

        // If an existing request is found, display an error message and return
        if (existingRequest != null) {
            showAlert(Alert.AlertType.ERROR, "Error", "You have already sent a verification request.");
            return;
        }

        // Create the alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to send a verification request?");

        // Set the button types (Yes/No)
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        // Show the alert and wait for the user's response
        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);

        // If the user clicked "Yes", send the verification request
        if (result == ButtonType.YES) {
            // Create a new VerificationRequest for the current user
            VerificationRequest request = new VerificationRequest(currentUser, false);

            // Insert the verification request into the database
            verificationRequestService.insert(request);

            // Show a confirmation message to the user
            showAlert(Alert.AlertType.INFORMATION, "Verification Request Sent", "Your verification request has been sent successfully!");
        }
    }
}
