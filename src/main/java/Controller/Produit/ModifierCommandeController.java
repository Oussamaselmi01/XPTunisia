package Controller.Produit;

import Services.Produit.CommandeService;
import Entities.Produit.Commande;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;


public class ModifierCommandeController {
    @FXML
    private DatePicker ddl;

    @FXML
    private TextField adl;

    @FXML
    private TextField mdp;

    @FXML
    private Button modification;

    @FXML
    private TextField nc;

    @FXML
    private TextField numtel;

    @FXML
    private TextField total;

    CommandeService ps = new CommandeService();
    private int idc; // Déclarez la variable id

    public void setCommandeId(int id) {
        this.idc = id;
        Commande commande = ps.readById(id);
        if (commande != null) {
            adl.setText(commande.getAdresse_livraison());
            mdp.setText(commande.getMode_paiement());
            nc.setText(commande.getClient_name());
            numtel.setText(commande.getPhone_number());
            total.setText(String.valueOf(commande.getTotal()));
            // Assurez-vous que la date de livraison n'est pas nulle avant de l'assigner au DatePicker
            if (commande.getDate_livraison() != null) {
                ddl.setValue(commande.getDate_livraison().toLocalDate());
            }
        } else {
            // Gérer le cas où la commande n'est pas trouvée
        }
    }

    @FXML
    void modifier(ActionEvent event) {
        // Vérifiez si les champs sont vides ou non, traitez les erreurs si nécessaire
        if (adl.getText().isEmpty() || mdp.getText().isEmpty() || nc.getText().isEmpty() || numtel.getText().isEmpty() || total.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.showAndWait();
            return;
        }

        // Demander une confirmation à l'utilisateur
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de modification");
        confirmationAlert.setHeaderText("Modifier la commande");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir modifier la commande sélectionnée ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Mettre à jour la commande dans la base de données
            Commande commande = new Commande();
            commande.setId(this.idc);
            commande.setAdresse_livraison(adl.getText());
            commande.setMode_paiement(mdp.getText());
            commande.setClient_name(nc.getText());
            commande.setPhone_number(numtel.getText());
            commande.setTotal(Double.parseDouble(total.getText()));

            // Assigner la date de livraison à partir du DatePicker
            LocalDate localDate = ddl.getValue();
            if (localDate != null) {
                Date sqlDate = Date.valueOf(localDate);
                commande.setDate_livraison(sqlDate);
            }

            // Passez la commande à la méthode update() de votre service
            ps.update(commande);
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialisez le DatePicker ici si nécessaire
        // Par exemple, pour définir une date par défaut :
        ddl.setValue(LocalDate.now()); // Cela initialise le DatePicker avec la date actuelle
    }
}
