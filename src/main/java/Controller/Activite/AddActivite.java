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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import Entities.Activite.Activite;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class AddActivite {

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
    private Text imagetextname;
    @FXML
    private Text errorLabel;



    private final ActiviteService as = new ActiviteService();
    private final CategorieService cs = new CategorieService();

    int[] ids;
    String[] names;
    int id;

    String imageAction;
    File selectedFile;
    String destDirectoryPath = "C:/Users/HP/IdeaProjects/test/src/main/resources/img/";

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void AjouterActivite(ActionEvent event) {

        try{if (isValidInput()) {
            LocalDate selectedDate_debut = dateDebutId.getValue();
            LocalDate selectedDate_fin = dateFinId.getValue();

            Date sqlDate_debut = Date.valueOf(selectedDate_debut);
            Date sqlDate_fin = Date.valueOf(selectedDate_fin);

            System.out.println(id);
            as.addA(new Activite(NomId.getText(),descriptionId.getText(),sqlDate_debut,sqlDate_fin,id,imageAction));
            // copy the image to the storage directory
            Path sourcePath = selectedFile.toPath();
            Path destPath = new File(destDirectoryPath, selectedFile.getName()).toPath();
            Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
        }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
    private boolean isValidInput() {
        LocalDate currentDate = LocalDate.now();

        if (NomId.getText().isEmpty()) {
            errorLabel.setText("Please enter a name.");
            return false;
        }
        if (descriptionId.getText().isEmpty()) {
            errorLabel.setText("Please enter a description.");
            return false;
        }
        if (dateDebutId.getValue() == null || dateDebutId.getValue().isBefore(currentDate)) {
            errorLabel.setText("Please enter a valid start date.");
            return false;
        }
        if (dateFinId.getValue() == null || dateFinId.getValue().isBefore(dateDebutId.getValue())) {
            errorLabel.setText("Please enter a valid end date.");
            return false;
        }
        errorLabel.setText("");

        return true;
    }

    public void initialize(){
        imagetextname.setText("");


        List<Categorie> categories = cs.getAll();

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
    @FXML
    void Back(ActionEvent event) throws IOException {
        root =FXMLLoader.load(getClass().getResource("/Activite/Home.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    void back(MouseEvent event) throws IOException {
        root =FXMLLoader.load(getClass().getResource("/Activite/AfficherActivite.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    void choisirimage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", ".jpeg", ".gif");
        fileChooser.getExtensionFilters().add(extFilter);

        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {

            String fileName = selectedFile.getName();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

            imageAction = fileName ;
            imagetextname.setText(imageAction);

            System.out.println("Selected Image: " + fileName);
            System.out.println("File Extension: " + fileExtension);
        }

    }



    /*@FXML
    void Ajouter(ActionEvent event) {

        as.addA(new Activite(nomTF.getAccessibleText(), descriptionTF.getAccessibleText()));
    }*/


}
