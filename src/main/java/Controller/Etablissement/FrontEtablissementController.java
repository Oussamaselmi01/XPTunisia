package Controller.Etablissement;

import Entities.Etablissement.Etablissement;
import Services.EtablissementService.EtablissementService;
import Services.EtablissementService.IService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class FrontEtablissementController {


    @FXML
    private VBox vBox;

    @FXML
    private void initialize() {
        // Initialize columns
        showEtablissementDetails();
    }

    private void showEtablissementDetails() {
        EtablissementService etablissementService = new EtablissementService();

        ArrayList<Etablissement> Etablissements = etablissementService.readAll();
        for (Etablissement etablissement : Etablissements) {
            Label nomLabel = new Label("Nom de l'etablissement: " + etablissement.getNom());
            Label typeLabel = new Label("Type de l'etablissement: " + etablissement.getType());
            Label adresseLabel = new Label("Addresse de l'etablissement: " + etablissement.getAdresse());


            VBox etablissementBox = new VBox(nomLabel, typeLabel, adresseLabel);
            etablissementBox.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 5px; -fx-spacing: 5px;");


            vBox.getChildren().add(etablissementBox);
        }
    }
}


