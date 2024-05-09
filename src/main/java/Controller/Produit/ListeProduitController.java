package Controller.Produit;

import Services.Produit.ProduitService;
import Entities.Produit.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.List;
//import org.example.Service.PanierService;
//import org.example.Service.ProduitService;


public class ListeProduitController {

    @FXML
    private Button ajoutpanier;

    @FXML
    private TextField barrerecherche;

    @FXML
    private Label compte;

    @FXML
    private ImageView imageaffp;

    @FXML
    private Button moinsqte;

    @FXML
    private Label nompAf;

    @FXML
    private Button plusqte;

    @FXML
    private Label prixpAf;

    @FXML
    private Label qteproduit;

    @FXML
    private AnchorPane rechpan;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Button search;

    @FXML
    private ImageView showpanier;


    private File selectedFile;

    private Produit selectedProduit;

    private ProduitService ps = new ProduitService();
    List<Produit> produits = ps.readall();
    //Produit produit = new Produit();

   // private Panier panier;
    //private PanierService pas = new PanierService();

    private int va =0;

    public void initialize(){

        setProduitGridPaneList();
    }
    @FXML
    void getqteproduit(ActionEvent event) {

        plusqte.setOnAction(this::plusqte);

        StackPane root = new StackPane();
        root.getChildren().add(plusqte);
        root.getChildren().addAll(plusqte, qteproduit);
        Scene scene = new Scene(root, 300, 250);
    }

    @FXML
    void moinsqte(ActionEvent event) {
        if (va > 1) {
            va--;
            System.out.println("Quantité : " + va);
            qteproduit.setText(String.valueOf(va));
        }
    }

    @FXML
    void plusqte(ActionEvent event) {
        va++;
        System.out.println("Quantité : " + va);
        qteproduit.setText(String.valueOf(va));
    }

    private void setProduitGridPaneList( ) {
        VBox mainVBox = new VBox(60);  // Espace vertical entre chaque ligne
        mainVBox.setPadding(new Insets(100));  // Marge autour du VBox principal

        HBox currentHBox = new HBox(100);  // Espace horizontal entre chaque activité dans une ligne
        for (Produit produit : produits) {
            VBox vbox = createProduitVBox(produit);
            currentHBox.getChildren().add(vbox);

            // Créer une nouvelle ligne après chaque 3 activités
            if (currentHBox.getChildren().size() == 3) {
                mainVBox.getChildren().add(currentHBox);
                currentHBox = new HBox(100);
            }
        }

        // Ajouter la dernière ligne si elle n'est pas complète
        if (!currentHBox.getChildren().isEmpty()) {
            mainVBox.getChildren().add(currentHBox);
        }

        // Ajouter le VBox principal à votre conteneur parent (ScrollPane)
        scroll.setContent(mainVBox);
    }


    private VBox createProduitVBox(Produit produit) {
        VBox vbox = new VBox();

        Label nomLabel = new Label(produit.getNom());
        Label prixLabel = new Label(Double.toString(produit.getPrix()) + "dt");
        ImageView imageView = new ImageView();

        try {
            Image image = new Image(new File(produit.getImage()).toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(200);  // largeur
            imageView.setFitHeight(200); // hauteur
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Création du bouton affichageButton en dehors du gestionnaire d'événements
        Button affichageButton = new Button("Voir");
        affichageButton.setOnAction(event -> {
            selectedProduit = produit;
            System.out.println("Produit sélectionné : " + selectedProduit);

            nompAf.setText("" + selectedProduit.getNom());
            prixpAf.setText(selectedProduit.getPrix() + "   dt");
            Image image = new Image(new File(selectedProduit.getImage()).toURI().toString());
            imageaffp.setImage(image);
        });

        affichageButton.getStyleClass().add("round-buttonMenu1");

        // Ajout des éléments au VBox
        vbox.getChildren().addAll(imageView, nomLabel, prixLabel, affichageButton);
        vbox.setAlignment(Pos.CENTER);

        // Retourner le VBox
        return vbox;
    }

}





