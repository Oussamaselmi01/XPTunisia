package Controller.Activite;

import Entities.Activite.Categorie;
import Services.ActiviteService.ActiviteService;
import Services.ActiviteService.CategorieService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Entities.Activite.Activite;


import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Updateactivite {

    @FXML
    private ChoiceBox<String> CategoyId;

    @FXML
    private TextField NomId;

    @FXML
    private DatePicker dateDebutId;

    @FXML
    private DatePicker dateFinId;

    @FXML
    private TextArea descriptionId;
    @FXML
    private Text errorLabel;

    String name_of_category;
    int[] ids;
    String[] names;
    int id;
    int id_category;
    int myId;

    ActiviteService activiteService = new ActiviteService();
    CategorieService categorieService = new CategorieService();



    @FXML
    void ModifierActivite(ActionEvent event) {
        if (isValidInput()){
        Activite updatedActivite = new Activite();

        updatedActivite.setNom(NomId.getText());
        updatedActivite.setDescription(descriptionId.getText());
        updatedActivite.setId_categorie(id_category);
        updatedActivite.setId(myId);

        LocalDate selectedDatedebut = dateDebutId.getValue();
        LocalDate selectedDatefin = dateFinId.getValue();

        java.sql.Date sqlDateDebut = java.sql.Date.valueOf(selectedDatedebut);
        java.sql.Date sqlDateFin = java.sql.Date.valueOf(selectedDatefin);

        updatedActivite.setDate_debut(sqlDateDebut);
        updatedActivite.setDate_fin(sqlDateFin);
        System.out.println(updatedActivite);
        activiteService.updateA(updatedActivite);
        }



    }
    private boolean isValidInput() {
        if (NomId.getText().isEmpty()) {
            errorLabel.setText("Please enter a name.");
            return false;
        }
        if (descriptionId.getText().isEmpty()) {
            errorLabel.setText("Please enter a description.");
            return false;
        }
        if (dateDebutId.getValue() == null) {
            errorLabel.setText("Please enter a datedebut.");
            return false;
        }
        if (dateFinId.getValue() == null) {
            errorLabel.setText("Please enter a datefin.");
            return false;
        }
        errorLabel.setText("");

        return true;
    }


    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void Back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Activite/AfficherActivite.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void back(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Activite/AfficherActivite.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void initData(int id){
        myId = id;
        Activite activite = new Activite();
        activite = activiteService.getOne(id);
        NomId.setText(activite.getNom());
        descriptionId.setText(activite.getDescription());

        List<Categorie> categories = categorieService.getAll();

        for(int i = 0; i < categories.size(); i++){
            ids[i] = categories.get(i).getId();
            names[i] = categories.get(i).getNom();
            CategoyId.getItems().add(names[i]);

        }

        CategoyId.setValue(names[0]);
        CategoyId.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) ->{
            int selectedIndex = CategoyId.getItems().indexOf(newValue);
            id_category= ids[selectedIndex];
            System.out.println(id_category);

        });

        Date dateDebutFromDatabase = (Date) activite.getDate_debut();
        Date dateFinFromDatabase = (Date) activite.getDate_fin();

        // convert java.sql.Date to java.util.Date
        Date utilDateDebut = new Date(dateDebutFromDatabase.getTime());
        Date utilDateFin = new Date(dateFinFromDatabase.getTime());
        // convert java.util.Date to LocalDate

        LocalDate localDateDebut = utilDateDebut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDateFin = utilDateFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        dateDebutId.setValue(localDateDebut);
        dateFinId.setValue(localDateFin);

    }

    public void initialize(){

        List<Categorie> categories = categorieService.getAll();

        ids = new int[categories.size()];
        names = new String[categories.size()];

        for(int i = 0; i < categories.size(); i++){
            ids[i] = categories.get(i).getId();
            names[i] = categories.get(i).getNom();
            CategoyId.getItems().add(names[i]);

        }

        CategoyId.setValue(names[0]);
        CategoyId.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) ->{
            int selectedIndex = CategoyId.getItems().indexOf(newValue);
            id= ids[selectedIndex];

        });
    }
}
