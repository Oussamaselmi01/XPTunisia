package Controller.Activite;

import Entities.Activite.Categorie;
import Services.ActiviteService.CategorieService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.List;

public class UpdateCategorie {
    @FXML
    private TextField NomId;

    @FXML
    private TextArea descriptionId;
    @FXML
    private Text errorLabel;


    int myId;
    private Stage stage;
    private Scene scene;
    private Parent root;
    CategorieService categorieService = new CategorieService();

    @FXML
    void Back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Activite/AfficherCategorie.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void back(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Activite/AfficherCategorie.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    @FXML
    void ModifierCategorie(ActionEvent event) {
        if (isValidInput()){
        Categorie updatedCategorie = new Categorie();

        updatedCategorie.setNom(NomId.getText());
        updatedCategorie.setDescription(descriptionId.getText());

        updatedCategorie.setId(myId);
        System.out.println(updatedCategorie);
        categorieService.updateA(updatedCategorie);
        }


    }
    private boolean isValidInput() {
        if (NomId.getText().isEmpty()) {
            errorLabel.setText("Please enter a name.");
            return false;
        }
        if (descriptionId.getText().isEmpty()) {
            errorLabel.setText("Please enter a description.");
            return false;
        }
        errorLabel.setText("");

        return true;
    }


    public void initData(int id){
        myId = id;
        Categorie categorie = new Categorie();
        categorie = categorieService.getOne(id);
        NomId.setText(categorie.getNom());
        descriptionId.setText(categorie.getDescription());
}

    public void initialize(){
        List<Categorie> categorie = categorieService.getAll();
        ObservableList<Categorie> observableList = FXCollections.observableList(categorie);



    }
}
