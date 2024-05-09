package Controller.Offre.Type;

import App.Home;
import Entities.Offre.TypeOffre;
import Services.OffreService.TypeService;

import Controller.Offre.Offre.UpdateOffreController;


import App.MainApplication;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class IndexTypeController {
    private final TypeService typeService = new TypeService();

    @FXML
    private TableView<TypeOffre> tableView;

    @FXML
    private TableColumn<TypeOffre, String> nomColumn;
    @FXML
    private TableColumn<TypeOffre, String> descriptionColumn;
    @FXML
    private TableColumn<TypeOffre, String> dateCreationColumn;
    @FXML
    private TableColumn<TypeOffre, Void> updateColumn;
    @FXML
    private TableColumn<TypeOffre, Void> deleteColumn;

    @FXML
    private TextField filterField;

    @FXML
    private Button button;








    @FXML
    private void initialize() {

        nomColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getNom()));
        descriptionColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getDescription()));
        dateCreationColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getDate_creation().toString()));


        updateColumn.setCellFactory(param -> new TableCell<>() {
            final Button cellButton = new Button("Modifier");

            {
                cellButton.setOnAction(event -> {
                    TypeOffre type = getTableView().getItems().get(getIndex());
                    handleUpdateType(type);
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
                    TypeOffre type = getTableView().getItems().get(getIndex());
                    handleDeleteType(type);
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

    public void refresh() {

        ArrayList<TypeOffre> types = typeService.readAll();


        tableView.getItems().clear();


        tableView.getItems().addAll(types);

    }

    @FXML
    void navigateToTypes(ActionEvent event) throws IOException {
        Home.navigateToTypes();
    }

    @FXML
    void navigateToOffres(ActionEvent event) throws IOException {
       Home.navigateToOffres();
    }

    @FXML
    private void handleAjouterType() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/type/add-type.fxml"));
            Parent root = loader.load();
            AddTypeController controller = loader.getController();
            controller.setIndexTypeController(this);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateType(TypeOffre type) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/type/update-type.fxml"));
            Parent root = loader.load();
            UpdateTypeController controller = loader.getController();
            controller.setIndexTypeController(this);
            controller.setType(type);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    private void handleSearch() {
        String searchText = filterField.getText().toLowerCase();


        ArrayList<TypeOffre> allTypes = typeService.readAll();


        ObservableList<TypeOffre> allTypesObservable = FXCollections.observableArrayList(allTypes);


        ObservableList<TypeOffre> filteredTypes = FXCollections.observableArrayList();


        for (TypeOffre type : allTypesObservable) {

            if (type.getNom().toLowerCase().contains(searchText) || type.getDescription().toLowerCase().contains(searchText)) {

                filteredTypes.add(type);
            }
        }


        tableView.setItems(filteredTypes);
    }


    @FXML
    private void handleDeleteType(TypeOffre type) {
        // Implement delete type functionality here
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Suppression de le type");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cette type ?");


        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {

                typeService.delete(type.getId());

                refresh();
            }
        });
    }
}
