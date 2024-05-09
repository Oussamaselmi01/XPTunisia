package Controller.Produit;

import Services.Produit.ProduitService;
import Entities.Produit.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AjouterProduitController {


    @FXML
    private Button ButtonAddProduct;

    @FXML
    private Button ButtonHome;

    @FXML
    private Button ButtonListSales;

    @FXML
    private Button ButtonProductList;

    @FXML
    private Button boutonajp;

    @FXML
    private TextField desctf;

    @FXML
    private TextField imagetf;

    @FXML
    private TextField nomtf;

    @FXML
    private TextField prixtf;

    private final ProduitService ps = new ProduitService();


    @FXML
    void Home(ActionEvent event) {

    }


    @FXML
    public void ajouterp(ActionEvent actionEvent) throws IOException {
        String nomt = nomtf.getText();
        String descri = desctf.getText();
        String prixStr = prixtf.getText();
        String imaget = imagetf.getText();

        // Vérifier si le prix est numérique
        if (!prixStr.matches("^\\d*\\.?\\d+$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText("Saisie invalide pour le prix !");
            alert.showAndWait();
            return;
        }

        double prix = Double.parseDouble(prixStr);

        if (nomt.isEmpty() || imaget.isEmpty()) {
            // Afficher un message d'erreur si les champs obligatoires ne sont pas remplis
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText("Information manquante !");
            alert.showAndWait();
            return;
        }

        ps.add(new Produit(nomt, descri, imaget, prix));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Produit/ListeProduit.fxml"));
        Parent root = loader.load();
    }


    @FXML
    void listP(ActionEvent event) {

    }

    @FXML
    void listV(ActionEvent event) {

    }

}
