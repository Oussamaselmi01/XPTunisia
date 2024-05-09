package Controller.Etablissement;

import Entities.Etablissement.Commentaire;
import Entities.Etablissement.Etablissement;
import Services.EtablissementService.CommentaireService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AddCommentaireController {

    @FXML
    private Label labelTitle;
    @FXML
    private TextArea commentTextArea;
    @FXML
    private ComboBox<Integer> noteComboBox;
    private int selectedRating = 0;
    @FXML
    private HBox starBox;

    private Etablissement etablissement;
    private IndexEtablissementController indexEtablissementController; // Reference to the index controller

    public void setIndexEtablissementController(IndexEtablissementController controller) {
        this.indexEtablissementController = controller;
    }

    public void initializeEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
        labelTitle.setText("Commenter pour '" + etablissement.getNom() + "'");

        // Ajouter les étoiles noires
        for (int i = 0; i < 5; i++) {
            Button starButton = new Button("\u2605"); // Étoile unicode
            starButton.setStyle("-fx-text-fill: black;");
            int rating = i + 1;
            starButton.setOnAction(event -> selectRating(rating));
            starBox.getChildren().add(starButton);
        }
    }

    private void selectRating(int rating) {
        selectedRating = rating;
        // Mettre en jaune les étoiles sélectionnées
        for (int i = 0; i < starBox.getChildren().size(); i++) {
            Button starButton = (Button) starBox.getChildren().get(i);
            if (i < rating) {
                starButton.setStyle("-fx-text-fill: yellow;");
            } else {
                starButton.setStyle("-fx-text-fill: black;");
            }
        }
    }


    @FXML
    private void handleSubmitComment() {
        if (isInputValid()) {
            CommentaireService service = new CommentaireService();
            Commentaire commentaire = new Commentaire(
                    etablissement.getId(),
                    commentTextArea.getText(),
                    String.valueOf(selectedRating)
            );
            service.insert(commentaire);
            if (indexEtablissementController != null) {
                indexEtablissementController.refreshCommentTableView(); // Cette ligne rafraîchit la table des commentaires.
            }
            closeWindow();
        }
    }


    private boolean isInputValid() {
        String errorMessage = "";

        if (commentTextArea.getText() == null || commentTextArea.getText().isEmpty()) {
            errorMessage += "Le champ de commentaire ne peut pas être vide !\n";
        }

        if (selectedRating == 0) {
            errorMessage += "Vous devez sélectionner une note !\n";
        }

        if (!errorMessage.isEmpty()) {
            showAlertDialog(Alert.AlertType.ERROR, "Erreur de Validation", errorMessage);
            return false;
        }
        return true;
    }

    private void showAlertDialog(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) labelTitle.getScene().getWindow();
        stage.close();
    }
}
