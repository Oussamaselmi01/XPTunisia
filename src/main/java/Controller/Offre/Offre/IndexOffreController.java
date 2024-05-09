package Controller.Offre.Offre;


import App.Home;
import javafx.beans.Observable;
import App.MainApplication;

import Entities.Offre.Offre;
import Entities.Offre.TypeOffre;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.scene.web.WebView;
import javafx.application.HostServices;
import javafx.application.Platform;
import Services.OffreService.OffreService;
import Services.OffreService.TypeService;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.stage.Stage;

public class IndexOffreController {
    private final OffreService offreService = new OffreService();
    private HostServices hostServices;

    @FXML
    private TableView<Offre> tableView;
    @FXML
    private TableColumn<Offre, String> nomColumn;
    @FXML
    private TableColumn<Offre, String> descriptionColumn;
    @FXML
    private TableColumn<Offre, String> conditionColumn;
    @FXML
    private TableColumn<Offre, String> dateDebutColumn;
    @FXML
    private TableColumn<Offre, String> dateFinColumn;
    @FXML
    private TableColumn<Offre, String> nbPlacesColumn;
    @FXML
    private TableColumn<Offre, String> typeColumn;
    @FXML
    private TableColumn<Offre, Void> updateColumn;
    @FXML
    private TableColumn<Offre, Void> deleteColumn;
    @FXML
    private TableColumn<Offre, Void> shareColumn;

    @FXML
    private TextField filterField;

    @FXML
    private Button button;
    @FXML
    private WebView webView;



    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }


    @FXML
    private void initialize() {

        nomColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getNom()));
        descriptionColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getDescription()));
        conditionColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getCondition_utilisation()));
        dateDebutColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getDate_debut().toString()));
        dateFinColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getDate_fin().toString()));
        typeColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> String.valueOf(cellData.getValue().getType_id())));
        nbPlacesColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getNb_place()).asString());

        updateColumn.setCellFactory(param -> new TableCell<>() {
            final Button cellButton = new Button("Modifier");

            {
                cellButton.setOnAction(event -> {
                    Offre offre = getTableView().getItems().get(getIndex());
                    handleUpdateOffre(offre);
                });
                cellButton.setStyle("-fx-background-color: #1aff00; -fx-text-fill: white;");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(cellButton);
                    setText(null);
                }
            }
        });

        deleteColumn.setCellFactory(param -> new TableCell<>() {
            final Button cellButton = new Button("Supprimer");

            {
                cellButton.setOnAction(event -> {
                    Offre offre = getTableView().getItems().get(getIndex());
                    handleDeleteOffre(offre);
                });
                cellButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white;");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(cellButton);
                    setText(null);
                }
            }
        });

        refresh();
    }

    @FXML
    void navigateToOffres(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        Home.navigateToOffres();
    }

    @FXML
    void navigateToTypes(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        Home.navigateToTypes();
    }

    public void refresh() {
        ArrayList<Offre> offres = offreService.readAll();
        tableView.getItems().clear();
        tableView.getItems().addAll(offres);
    }

    @FXML
    void GoToDash(ActionEvent event) {
        try {
            // Get the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashi.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Close the current stage
            currentStage.close();
            // Show the new stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void handleUpdateOffre(Offre offre) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/offre/update-offre.fxml"));
            Parent root = loader.load();
            UpdateOffreController controller = loader.getController();
            controller.setIndexOffreController(this);
            controller.setOffre(offre);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteOffre(Offre offre) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Suppression de l'offre");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cette offre ?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                offreService.delete(offre.getId());
                refresh();
            }
        });
    }

    @FXML
    private void handleAddOffre() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/offre/add-offre.fxml"));
            Parent root = loader.load();
            AddOffreController controller = loader.getController();
            controller.setIndexOffreController(this);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void shareCurrentOffre(ActionEvent event) {
        Offre selectedOffre = tableView.getSelectionModel().getSelectedItem();
        if (selectedOffre != null) {
            shareOffreOnSocialMedia(selectedOffre);
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de partage");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une offre à partager.");
            alert.showAndWait();
        }
    }
    @FXML
    private void handleSearch() {
        String searchText = filterField.getText().toLowerCase();


        ArrayList<Offre> allOffres = offreService.readAll();


        ObservableList<Offre> allOffresObservable = FXCollections.observableArrayList(allOffres);


        ObservableList<Offre> filteredOffres = FXCollections.observableArrayList();


        for (Offre offre : allOffresObservable) {

            if (offre.getNom().toLowerCase().contains(searchText) || offre.getDescription().toLowerCase().contains(searchText)) {

                filteredOffres.add(offre);
            }
        }


        tableView.setItems(filteredOffres);
    }



    private void shareOffreOnSocialMedia(Offre offre) {

        String offreNom = offre.getNom();
        String offreDescription = offre.getDescription();
        String offreDateFin = offre.getDate_fin().toString();


        String shareText = "Découvrez cette offre : " + offreNom + ", " + offreDescription +
                ". La date fin de cette offre est " + offreDateFin;



        String facebookURL = "https://www.facebook.com/sharer/sharer.php?u=&quote=" + shareText;
        //
        openURL(facebookURL);

        //String twitterURL = "https://twitter.com/intent/tweet?text=" + shareText;

        //openURL(twitterURL);

    }


    private void openURL(String url) {
        Platform.runLater(() -> {
            if (hostServices != null) {
                hostServices.showDocument(url);
            } else {
                System.out.println("HostServices non défini.");
            }
        });


    }
}
