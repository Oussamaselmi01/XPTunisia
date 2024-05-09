package Controller.Produit;

import Services.Produit.CommandeService;
import Entities.Produit.Commande;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class ListeCommandeBackController {



    @FXML
    private Button ButtonAccueil;

    @FXML
    private Button ButtonAjouterP;

    @FXML
    private Button ButtonListeP;

    @FXML
    private Button ButtonListeV;

    @FXML
    private Button ButtonModifierP;

    @FXML
    private Button ButtonModifierV;

    @FXML
    private TableView<?> TableCommande;

    @FXML
    private TableColumn<?, ?> DP;

    @FXML
    private TableColumn<?, ?> DateP;

    @FXML
    private TableColumn<?, ?> IP;

    @FXML
    private TableColumn<?, ?> NP;

    @FXML
    private TableColumn<?, ?> PP;

    @FXML
    private TableColumn<?, ?> RP;

    @FXML
    private TableColumn<?, ?> SP;

    @FXML
    private TableColumn<Commande, Void> SuppressionP;

    @FXML
    private TableColumn<Commande, Void> ModificationP;

    CommandeService ps = new CommandeService();
    List<Commande> PList;

    public void initialize() throws IOException {
        // Initialiser le ComboBox avec des données
        ObservableList<String> options = FXCollections.observableArrayList(
                "confirmée",
                "annulée"
        );
        ShowCommande();
        configureSuppressionColumn();
        configureModificationColumn();
        //showNotification();
    }

    public void ShowCommande() throws IOException {

        PList = ps.readall();
        List<Commande> filtredPList = new ArrayList<>();
        NP.setCellValueFactory(new PropertyValueFactory<>("mode_paiement"));
        PP.setCellValueFactory(new PropertyValueFactory<>("adresse_livraison"));
        DateP.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        SP.setCellValueFactory(new PropertyValueFactory<>("client_name"));
        DP.setCellValueFactory(new PropertyValueFactory<>("total"));
        //IP.setCellValueFactory(new PropertyValueFactory<>("date-livraison"));
        if (TableCommande != null && TableCommande instanceof TableView) {
            // Cast ticket_tableview to TableView<Ticket> and set its items
            ((TableView<Commande>) TableCommande).setItems(FXCollections.observableArrayList(PList));
        }
    }


    private void configureModificationColumn() {
        ModificationP.setCellFactory(param -> new TableCell<>() {
            private final Button boutonModifier = new Button("Modifier");

            {
                boutonModifier.setOnAction(event -> {
                    Commande commande = getTableView().getItems().get(getIndex());
                    System.out.println("Modifier : " + commande.getClient_name());

                    // Créer une nouvelle fenêtre (Stage)
                    Stage nouvelleFenetre = new Stage();

                    // Créer un FXMLLoader pour charger la nouvelle scène depuis le fichier FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Produit/ModifierCommande.fxml"));

                    try {
                        Parent root = loader.load();
                        // Obtenir le contrôleur de la nouvelle scène
                        ModifierCommandeController modifierCommandeController = loader.getController();

                        // Transmettre l'id de la vente au contrôleur de la nouvelle scène
                        modifierCommandeController.setCommandeId(commande.getId());

                        Scene nouvelleScene = new Scene(root);
                        nouvelleFenetre.setScene(nouvelleScene);
                        nouvelleFenetre.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Rafraîchir la TableView si nécessaire
                    TableCommande.refresh();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : boutonModifier);
            }
        });
    }


    private void configureSuppressionColumn() {
        SuppressionP.setCellFactory(param -> new TableCell<>() {
            private final Button boutonSupprimer = new Button("Supprimer");
            {
                boutonSupprimer.setOnAction(event -> {
                    Commande commande = getTableView().getItems().get(getIndex());
                    // Ici, vous pouvez appeler votre méthode de suppression
                    // en utilisant les données du produit.
                    ps.delete(commande);
                    System.out.println("Supprimer : " + commande.getId());
                    TableCommande.refresh();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Produit/ListeCommandeBack.fxml"));
                        Parent root = loader.load();
                        // Obtenir le contrôleur de la nouvelle interface
                        ListeCommandeBackController listeCommandeBackController = loader.getController();

                        // Créer une nouvelle scène
                        Scene scene = new Scene(root);

                        // Configurer la nouvelle scène dans une nouvelle fenêtre
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);

                        // Afficher la nouvelle fenêtre
                        stage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : boutonSupprimer);
            }
        });
    }



    private String getFileNameFromPath(String filePath) {
        // Split the path based on the '/' character
        String[] pathParts = filePath.split("/");

        // Get the last part of the path (file name)
        return pathParts[pathParts.length - 1];
    }

    /*
    private void showNotification() {
        try {
            Image image = new Image(getClass().getResource("/notif.png").toExternalForm());

            Notifications notifications = Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("Donation added successfully");
            notifications.title("Success Message");
            notifications.hideAfter(Duration.seconds(4));
            notifications.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @FXML
    public void listP(ActionEvent actionEvent)  throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Produit/ListeProduitBack.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Produit");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    @FXML
    public void listV(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Produit/ListeCommandeBack.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Vente");

        // Afficher la nouvelle fenêtre
        stage.show();
    }
    @FXML
    public void Home(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Produit/DashboardBack.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    @FXML
    public void ajP(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Produit/AjouterProduit.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    @FXML
    public void showStatP(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Produit/StatBuvette.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard");

        // Afficher la nouvelle fenêtre
        stage.show();
    }
}