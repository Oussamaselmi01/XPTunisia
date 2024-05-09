package Controller.Produit;

import Services.Produit.ProduitService;
import Entities.Produit.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

public class ModifierProduitController {

    private int idp;

    @FXML
    private TextField descrim;

    @FXML
    private TextField imagem;

    @FXML
    private Button modification;

    @FXML
    private TextField nomm;

    @FXML
    private TextField prixm;

    @FXML
    private TextField quantitem;
    ProduitService ps = new ProduitService();
    Date utilDate = new Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

    public void setProduitId(int id) {
        this.idp = id;
        Produit produit = ps.readById(id); // Supposons que vous avez une méthode getProduitById dans ProduitService pour récupérer le produit par son ID
        if (produit != null) {
            nomm.setText(produit.getNom());
            descrim.setText(produit.getDescription());
            prixm.setText(String.valueOf(produit.getPrix()));
            imagem.setText(produit.getImage());
            // Remplissez d'autres champs si nécessaire
        } else {
            // Gérer le cas où le produit n'est pas trouvé
        }
    }


    @FXML
    void modifier(ActionEvent event) throws IOException {
        String nom = nomm.getText();
        String descr = descrim.getText();

        if (!ps.isNumeric(prixm.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText("Saisie invalide pour le prix !");
            alert.showAndWait();
            return;
        }

        if (nom.isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information manquante!");
            alert.setHeaderText(null);
            alert.setContentText("Saisissez un nom !");
            alert.showAndWait();
            return;
        }
        double prix =Float.parseFloat(prixm.getText());
        Produit pp = new Produit();
        pp.setId(this.idp);
        pp.setNom(nom);
        pp.setPrix(prix);
        pp.setDescription(descr);
        // Demander une confirmation à l'utilisateur (vous pouvez personnaliser cela selon vos besoins)
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de modification");
        alert.setHeaderText("Modifier le produit");
        alert.setContentText("Êtes-vous sûr de vouloir modifier la réservation sélectionnée ?");

        Optional<ButtonType> result = alert.showAndWait();
        // Si l'utilisateur confirme la suppression, procéder
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Mettre à jour la séance dans la base de données
            ps.update(pp);

        }
    }
}