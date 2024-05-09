package Controller.Etablissement;

import Entities.Etablissement.Commentaire;
import Entities.Etablissement.Etablissement;
import Services.EtablissementService.AlertingService;
import Services.EtablissementService.CommentaireService;
import Services.EtablissementService.EtablissementService;
import Services.EtablissementService.IService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class IndexEtablissementController {
    @FXML
    private TableView<Commentaire> commentaireTable;

    @FXML
    private TableColumn<Commentaire, Number> commentIdColumn;
    @FXML
    private TableColumn<Commentaire, String> commentContentColumn;
    @FXML
    private TableColumn<Commentaire, String> commentNoteColumn;

    @FXML
    private TableColumn<Commentaire, Number> commentEtabIdColumn;

    public Label labelImage;
    public Label labelType;
    public Label labelAdresse;
    public Label labelNom;
    public Label labelx;
    public Label labely;
    @FXML
    private TableView<Etablissement> etablissementTable;

    @FXML
    private TableColumn<Etablissement, Number> idColumn;
    @FXML
    private TableColumn<Etablissement, Number> userIdColumn;
    @FXML
    private TableColumn<Etablissement, Number> xColumn;
    @FXML
    private TableColumn<Etablissement, Number> yColumn;
    @FXML
    private TableColumn<Etablissement, String> nomColumn;
    @FXML
    private TableColumn<Etablissement, String> typeColumn;
    @FXML
    private TableColumn<Etablissement, String> adresseColumn;
    @FXML
    private TableColumn<Etablissement, String> imageColumn;
    private ObservableList<Commentaire> commentaireService = FXCollections.observableArrayList();
    private CommentaireService commentaireIService = new CommentaireService();

    private ObservableList<Etablissement> etablissementService = FXCollections.observableArrayList();
    private IService<Etablissement> etablissementIService = new EtablissementService();

    @FXML
    private void initialize() {
        labelNom.setText("");
        labelType.setText("");
        labelAdresse.setText("");
        labelImage.setText("");
        labely.setText("");
        labelx.setText("");

        commentIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
        commentContentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMessage()));
        commentNoteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNote()));
        commentEtabIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEtablissement_id()));

        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
        userIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUser_id()));
        xColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getX()));
        yColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getY()));
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        adresseColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse()));
        imageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImage()));

        try {
            etablissementService.addAll(etablissementIService.readAll());
            etablissementTable.setItems(etablissementService);
        } catch (Exception e) {
            e.printStackTrace();
        }

        etablissementTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showEtablissementDetails(newValue));
    }

    private void loadComments(int etablissementId) {
        commentaireService.clear();
        try {
            commentaireService.addAll(commentaireIService.readByEtablissement(etablissementId));
            commentaireTable.setItems(commentaireService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showEtablissementDetails(Etablissement etablissement) {
        if (etablissement != null) {
            labelNom.setText(etablissement.getNom());
            labelType.setText(etablissement.getType());
            labelAdresse.setText(etablissement.getAdresse());
            labelImage.setText(etablissement.getImage());
            labely.setText(String.valueOf(etablissement.getY()));
            labelx.setText(String.valueOf(etablissement.getX()));

            loadComments(etablissement.getId());

        } else {
            labelNom.setText("");
            labelType.setText("");
            labelAdresse.setText("");
            labelImage.setText("");
            labely.setText("");
            labelx.setText("");

            commentaireService.clear();

        }
    }

    @FXML
    public void refreshTableView() {
        try {
            etablissementService.clear();
            etablissementService.addAll(etablissementIService.readAll());
        } catch (Exception e) {
            e.printStackTrace();
            AlertingService.showAlertDialog(Alert.AlertType.ERROR, "Database Error", "Failed to fetch establishments", e.getMessage());
        }
    }


    @FXML
    private void createEtablissement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/etablissement/add-etablissement.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Add Etablissement");
            stage.setScene(scene);

            AddEtablissementController controller = loader.getController();
            controller.setIndexEtablissementController(this);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateEtablissement(ActionEvent event) {
        Etablissement selectedEtablissement = etablissementTable.getSelectionModel().getSelectedItem();
        if (selectedEtablissement != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/etablissement/update-etablissement.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Update Etablissement");
                stage.setScene(scene);

                UpdateEtablissementController controller = loader.getController();
                controller.setIndexEtablissementController(this);
                controller.setSelectedEtablissement(selectedEtablissement);

                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            AlertingService.showAlertDialog(Alert.AlertType.WARNING, "No Selection", "No Etablissement Selected!", "Please select an etablissement in the table!");
        }
    }

    @FXML
    private void handleDeleteEtablissement(ActionEvent event) {
        Etablissement selectedEtablissement = etablissementTable.getSelectionModel().getSelectedItem();
        if (selectedEtablissement != null) {
            try {
                etablissementIService.delete(selectedEtablissement.getId());
                refreshTableView(); // Rafraîchir la table après suppression
                AlertingService.showAlertDialog(Alert.AlertType.INFORMATION, "Delete Success", "Etablissement Deleted Successfully", "The etablissement has been deleted.");
            } catch (Exception e) {
                e.printStackTrace();
                AlertingService.showAlertDialog(Alert.AlertType.ERROR, "Database Error", "Failed to delete etablissement", e.getMessage());
            }
        } else {
            AlertingService.showAlertDialog(Alert.AlertType.WARNING, "No Selection", "No Etablissement Selected!", "Please select an etablissement in the table.");
        }
    }

    @FXML
    private void openAddCommentForm() {
        Etablissement selectedEtablissement = etablissementTable.getSelectionModel().getSelectedItem();
        if (selectedEtablissement != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/etablissement/add-commentaire.fxml"));
                Parent root = loader.load();

                AddCommentaireController controller = loader.getController();
                controller.initializeEtablissement(selectedEtablissement); // Pass the selected establishment to the controller

                Stage stage = new Stage();
                stage.setTitle("Commenter pour '" + selectedEtablissement.getNom() + "'");
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            AlertingService.showAlertDialog(Alert.AlertType.WARNING, "No Selection", "No Etablissement Selected!", "Please select an etablissement in the table.");
        }
    }

    public void refreshCommentTableView() {
        Etablissement selectedEtablissement = etablissementTable.getSelectionModel().getSelectedItem();
        if (selectedEtablissement != null) {
            loadComments(selectedEtablissement.getId());
        }
    }

    @FXML
    private void handleDeleteComment() {
        Commentaire selectedCommentaire = commentaireTable.getSelectionModel().getSelectedItem();
        if (selectedCommentaire != null) {
            commentaireIService.delete(selectedCommentaire.getId());
            refreshCommentTableView();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Comment Selected");
            alert.setContentText("Please select a comment in the table.");
            alert.showAndWait();
        }
    }




}
