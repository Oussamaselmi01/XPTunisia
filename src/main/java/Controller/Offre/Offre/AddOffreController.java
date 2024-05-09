
package Controller.Offre.Offre;


import Entities.Offre.Offre;
import Entities.Offre.TypeOffre;
import Services.OffreService.IService;
import Services.OffreService.OffreService;
import Services.OffreService.TypeService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddOffreController {

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField conditionTextField;

    @FXML
    private DatePicker dateDebutPicker;

    @FXML
    private DatePicker dateFinPicker;

    @FXML
    private TextField nbPlacesTextField;

    @FXML
    private ComboBox<String> typeComboBox;

    private IService<Offre> offreService = new OffreService();
    private TypeService typeService = new TypeService();

    private IndexOffreController indexOffreController;

    public void setIndexOffreController(IndexOffreController indexOffreController) {
        this.indexOffreController = indexOffreController;
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nomTextField.getText() == null || nomTextField.getText().isEmpty()) {
            errorMessage += "Nom de l'offre est vide!\n";
        }

        if (dateDebutPicker.getValue() == null || dateFinPicker.getValue() == null) {
            errorMessage += "Veuillez sélectionner une date de début et une date de fin!\n";
        } else if (dateDebutPicker.getValue().isAfter(dateFinPicker.getValue())) {
            errorMessage += "La date de début ne peut pas être après la date de fin!\n";
        }
        if (nbPlacesTextField.getText() == null || nbPlacesTextField.getText().isEmpty()) {
            errorMessage += "Veuillez spécifier le nombre de places disponibles!\n";
        } else {
            try {
                int nbPlaces = Integer.parseInt(nbPlacesTextField.getText());
                if (nbPlaces < 0) {
                    errorMessage += "Le nombre de places disponibles ne peut pas être négatif!\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "Le nombre de places disponibles doit être un entier valide!\n";
            }
        }


        if (typeComboBox.getValue() == null || typeComboBox.getValue().isEmpty()) {
            errorMessage += "Veuillez sélectionner un type d'offre!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlertDialog(Alert.AlertType.ERROR, "Erreur", "Champs invalides", errorMessage);
            return false;
        }
    }


    @FXML
    private void initialize() {
        ArrayList<TypeOffre> typesOffre = typeService.readAll();
        for (TypeOffre type : typesOffre) {
            typeComboBox.getItems().add(type.getNom());
        }
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            String nom = nomTextField.getText();
            String description = descriptionTextField.getText();
            String condition = conditionTextField.getText();
            LocalDate dateDebut = dateDebutPicker.getValue();
            LocalDate dateFin = dateFinPicker.getValue();
            String typeName = typeComboBox.getValue();
            int nbPlaces = Integer.parseInt(nbPlacesTextField.getText());




            int typeId = typeService.getTypeIdByName(typeName).getId();


            Offre nouvelleOffre = new Offre(nom, description, condition, dateDebut, dateFin, typeId , nbPlaces);

            offreService.insert(nouvelleOffre);


            if (indexOffreController != null) {
                indexOffreController.refresh();
            }

            nomTextField.getScene().getWindow().hide();
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