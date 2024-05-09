package Controller.Offre.Type;


import Entities.Offre.TypeOffre;
import Services.OffreService.TypeService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class AddTypeController {

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private Button ajouterButton;

    private TypeService typeService = new TypeService();

    private IndexTypeController indexTypeController;

    public void setIndexTypeController(IndexTypeController indexTypeController) {
        this.indexTypeController = indexTypeController;
    }

    @FXML
    private void handleAjouter() {

        if (isInputValid()) {

            TypeOffre nouveauType = new TypeOffre(
                    nomTextField.getText(),
                    descriptionTextField.getText(),
                    LocalDate.now()
            );

            typeService.insert(nouveauType);


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText("Type ajouté");
            alert.setContentText("Le type a été ajouté avec succès !");
            alert.showAndWait();


            if (indexTypeController != null) {
                indexTypeController.refresh();
            }


            nomTextField.getScene().getWindow().hide();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";


        if (nomTextField.getText() == null || nomTextField.getText().isEmpty()) {
            errorMessage += "Nom de Type invalide !\n";
        }else if (nomTextField.getText().length() < 4) {
            errorMessage += "Le nom de Type doit contenir au moins 4 caractères !\n";
        } else if (nomTextField.getText().length() > 25) {
            errorMessage += "Le nom de Type ne peut pas dépasser 25 caractères !\n";
        }else {

            if (typeService.isNomTypeExist(nomTextField.getText())) {
                errorMessage += "Nom de Type déjà existe !\n";
            }
        }


        if (descriptionTextField.getText() == null || descriptionTextField.getText().isEmpty()) {
            errorMessage += "Description invalide !\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Champs invalides");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

}
