package Controller.Reclamation;


import Entities.Reclamation.Reclammation;
import Services.Reclamation.ReclamtionResponseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EditReclamtion {
    @FXML
    private TextArea description;
    @FXML
    private ComboBox combobox;
    @FXML
    private Label cctitre;
    @FXML
    private Label ccdescript;
    @FXML
    private Label ccaj;
    @FXML
    private HBox hbox;

    private String titre;
    private int idreclamtion;
    // List of prohibited words
    private List<String> prohibitedWords = Arrays.asList("merde", "putain", "connard", "salaud", "enculé", "merde", "putain", "bordel", "maudit", "sacré nom de dieu");

    public void SetData(Reclammation r){
        this.idreclamtion=r.getId();
        ObservableList<String> list= FXCollections.observableArrayList("Service","Produit","Guide");
        combobox.setItems(list);
        description.setText(r.getDescription());
        if(r.getTitre().equals("Service")){
            combobox.getSelectionModel().selectFirst();
            titre="Service";
        } else if (r.getTitre().equals("Produit")) {
            combobox.getSelectionModel().select(1);
            titre="Produit";
        }else {
            combobox.getSelectionModel().select(2);
            titre="Guide";
        }
        FXMLLoader fxl=new FXMLLoader();
        fxl.setLocation(getClass().getResource("/Reclamation/HeaderFront.fxml"));
        Parent root= null;
        try {
            root = fxl.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        hbox.getChildren().add(root);
    }
    public void Modifier(ActionEvent actionEvent) throws SQLException, IOException {
        ReclamtionResponseService reclamtionResponseService=new ReclamtionResponseService();
        int t=0;
        if (description.getText().isEmpty()) {
            t = 1;
            this.ccdescript.setText("Vous devez saisir la description");
        } else {
            // Check for prohibited words
            for (String word : prohibitedWords) {
                if (description.getText().toLowerCase().contains(word)) {
                    t = 1;
                    this.ccdescript.setText("La description contient des mots inappropriés.");
                    break;
                }
            }
            if (t != 1) {
                this.ccdescript.setText("");
            }
        }

        if (titre== null){
            t = 1;
            this.cctitre.setText("Vous devez saisir le titre");
        } else {
            this.cctitre.setText("");
        }
        if(t==0){
            Reclammation r=new Reclammation();
            r.setId(this.idreclamtion);
            r.setStatut("En attente");
            r.setDate_creation(new Date());
            r.setDescription(description.getText());
            r.setTitre(titre);
            t=reclamtionResponseService.ModifierReclamtion(r);
            if(t==0){
                this.ccaj.setText("Erreur lors de modification s'il vous plait essayer une autre fois");
            }else {
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/Reclamation/RecalamtionsFront.fxml"));
                Parent root=loader.load();
                description.getScene().setRoot(root);
            }
        }
    }

    public void Select(ActionEvent actionEvent) {
        titre=combobox.getSelectionModel().getSelectedItem().toString();
    }
}
