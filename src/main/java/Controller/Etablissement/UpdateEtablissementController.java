package Controller.Etablissement;

import Entities.Etablissement.Etablissement;
import Services.EtablissementService.EtablissementService;
import Services.EtablissementService.IService;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateEtablissementController {
    @FXML
    private TextField idTextField;
    @FXML
    private TextField xTextField;
    @FXML
    private TextField yTextField;
    @FXML
    private TextField nomTextField;
    @FXML
    private TextField adresseTextField;
    @FXML
    private ComboBox<String> typeComboBox;

    private IService<Etablissement> etablissementService = new EtablissementService();

    private IndexEtablissementController indexEtablissementController;

    private Etablissement selectedEtablissement;

    public void setSelectedEtablissement(Etablissement etablissement) {
        this.selectedEtablissement = etablissement;
        if (selectedEtablissement != null) {
            idTextField.setText(String.valueOf(selectedEtablissement.getId()));
            xTextField.setText(String.valueOf(selectedEtablissement.getX()));
            yTextField.setText(String.valueOf(selectedEtablissement.getY()));
            nomTextField.setText(selectedEtablissement.getNom());
            adresseTextField.setText(selectedEtablissement.getAdresse());
            typeComboBox.setValue(selectedEtablissement.getType());
        }
    }

    public void setIndexEtablissementController(IndexEtablissementController controller) {
        this.indexEtablissementController = controller;
    }

    @FXML
    private void handleUpdate() {
        if (isInputValid()) {
            selectedEtablissement.setX(Double.parseDouble(xTextField.getText()));
            selectedEtablissement.setY(Double.parseDouble(yTextField.getText()));
            selectedEtablissement.setNom(nomTextField.getText());
            selectedEtablissement.setAdresse(adresseTextField.getText());
            selectedEtablissement.setType(typeComboBox.getValue());

            etablissementService.update(selectedEtablissement);

            Stage stage = (Stage) nomTextField.getScene().getWindow();
            stage.close();
            indexEtablissementController.refreshTableView();
        }
    }

    private boolean isInputValid() {
        // Validation logic here
        return true; // Simplified for example
    }
}
