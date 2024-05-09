package Controller.Activite;

import Entities.Activite.Categorie;
import Services.ActiviteService.ActiviteService;
import Services.ActiviteService.CategorieService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Entities.Activite.Activite;


import java.io.IOException;
import java.util.Date;
import java.util.List;

public class AfficherActivite {

    @FXML
    private TableColumn<Activite, String> descriptioncol;

    @FXML
    private TableColumn<Activite, String> nomcol;

    @FXML
    private TableColumn<Activite, Date> date_debutCol;

    @FXML
    private TableColumn<Activite, Date> date_finCol;

    @FXML
    private TableView<Activite> tableview;

    @FXML
    private TableColumn<Activite, Integer> categoriecol;

    @FXML
    private TextField acttxt;



    private Stage stage;
    private Scene scene;
    private Parent root;
    int myIndex;
    int id;
    int[] ids;
    String[] names;
    Activite activite;
    Categorie categorie;



    private final ActiviteService as = new ActiviteService();
    private final CategorieService categorieService = new CategorieService();
    @FXML
    void initialize() {

        List<Activite> activites = as.getAll();


        ObservableList<Activite> observableList = FXCollections.observableList(activites);
        tableview.setItems(observableList);

        ids = new int[activites.size()];
         for(int i = 0; i < activites.size(); i++){
             ids[i] = activites.get(i).getId();
         }
        nomcol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        descriptioncol.setCellValueFactory(new PropertyValueFactory<>("description"));
        date_debutCol.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        date_finCol.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        categoriecol.setCellValueFactory(new PropertyValueFactory<>("id_categorie'"));


        FilteredList<Activite> filteredData = new FilteredList<>(observableList, c -> true);
        acttxt.textProperty().addListener((observable ,olValue,newValue)->{
            filteredData.setPredicate(c -> {
                if (newValue.isEmpty() || newValue.isBlank()||newValue==null) {
                    return true;
                }
                String searchKeyword= newValue.toLowerCase();
                if (c.getNom().toLowerCase().indexOf(searchKeyword) >-1) {
                    return true;
                }else
                    return false;
            });
        });
        SortedList<Activite> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableview.comparatorProperty());
        tableview.setItems(sortedData);



        tableview.setRowFactory( tv -> {
            TableRow<Activite> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty()))
                {
                    myIndex =  tableview.getSelectionModel().getSelectedIndex();

                    id = Integer.parseInt(String.valueOf(tableview.getItems().get(myIndex).getId()));
                }
            });
            System.out.println();

            return myRow;
        });
    }

    @FXML
    void AjouterActivite(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Activite/AddActivite.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void naviguer1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Activite/AfficherActivite.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void naviguer2(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Activite/AfficherCategorie.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void naviguer3(ActionEvent event) throws IOException {
        root =FXMLLoader.load(getClass().getResource("/Activite/AfficherActiviteFront.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void ModifierActivite(ActionEvent event) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Activite/Updateactivite.fxml"));
        Parent root = loader.load();
        Updateactivite controller = loader.getController();
        myIndex = tableview.getSelectionModel().getSelectedIndex();
        id = ids[myIndex];
        System.out.println("id activite :"+ id);
        controller.initData(id);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void SupprimerActvite(ActionEvent event) {
        myIndex = tableview.getSelectionModel().getSelectedIndex();

        id = Integer.parseInt(String.valueOf(tableview.getItems().get(myIndex).getId()));

        as.deleteA(id);
    }


    public void goToDash(ActionEvent event) {
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
    void back(ActionEvent event) {
        try {
            // Get the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/index-user.fxml"));
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

}
