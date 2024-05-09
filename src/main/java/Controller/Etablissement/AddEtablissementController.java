package Controller.Etablissement;

import Controller.User.SessionManager;
import Entities.Etablissement.Etablissement;
import Entities.User.User;
import Services.EtablissementService.AlertingService;
import Services.EtablissementService.EtablissementService;
import Services.EtablissementService.IService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEtablissementController {
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private TextField xTextField;
    @FXML
    private TextField yTextField;
    @FXML
    private TextField nomTextField;
    @FXML
    private TextField adresseTextField;

    private IService<Etablissement> etablissementService = new EtablissementService();
    private IndexEtablissementController indexEtablissementController = new IndexEtablissementController();

    public void setIndexEtablissementController(IndexEtablissementController controller) {
        this.indexEtablissementController = controller;
    }

    @FXML
    private void initialize() {
        setIndexEtablissementController(indexEtablissementController);
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (xTextField.getText() == null || xTextField.getText().isEmpty()) {
            errorMessage += "X field is empty!\n";
        } else {
            try {
                Double.parseDouble(xTextField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "X must be a valid number!\n";
            }
        }

        if (yTextField.getText() == null || yTextField.getText().isEmpty()) {
            errorMessage += "Y field is empty!\n";
        } else {
            try {
                Double.parseDouble(yTextField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Y must be a valid number!\n";
            }
        }

        if (nomTextField.getText() == null || nomTextField.getText().isEmpty()) {
            errorMessage += "Nom field is empty!\n";
            if(nomTextField.getText().length()>35){
                errorMessage+="Nom est trop long (ne doit pas depasser 35)! \n";
            }
        }

        if (typeComboBox.getValue() == null) {
            errorMessage += "Type field is not selected!\n";
        }

        if (adresseTextField.getText() == null || adresseTextField.getText().isEmpty()) {
            errorMessage += "Adresse field is empty!\n";
            if(adresseTextField.getText().length()<15){
                errorMessage+="Adresse est trop courte (doit depasser 15) ! \n";
            }
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            AlertingService.showAlertDialog(Alert.AlertType.ERROR, "Error", "Validation Error", errorMessage);
            return false;
        }
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            User currentUser = SessionManager.getCurrentUser();
            double x = Double.parseDouble(xTextField.getText());
            double y = Double.parseDouble(yTextField.getText());
            String nom = nomTextField.getText();
            String type = typeComboBox.getValue();
            String adresse = adresseTextField.getText();

            Etablissement newEtablissement = new Etablissement(x, y, nom, type, adresse);
            newEtablissement.setUser_id(currentUser.getId());
            etablissementService.insert(newEtablissement);

            Stage stage = (Stage) nomTextField.getScene().getWindow();
            if (indexEtablissementController != null) {
                indexEtablissementController.refreshTableView();
            }
            stage.close();
        }
    }


}
