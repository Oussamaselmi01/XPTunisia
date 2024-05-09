package Controller.Activite;


import Services.ActiviteService.ActiviteService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Entities.Activite.Activite;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


public class AfficherActiviteFront {
    @FXML
    private ImageView activitieimage;
    @FXML
    private VBox actionsContainer;
    String imagepath ;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Text label;
    private ResourceBundle bundle;
    private Locale locale;
    @FXML
    private Text labellistactivites;

    ActiviteService as = new ActiviteService();



    @FXML
    void initialize() {
        List<Activite> activites = as.getAll();
        Locale currentLocale = LanguageSettings.getCurrentLocale();

        System.out.println("currentLocale "+currentLocale);
        for (Activite activite :activites) {
            Pane actionPane = createActionPane(activite);
            actionsContainer.getChildren().add(actionPane);
            imagepath = "C:/Users/Oussama/IdeaProjects/XpUser/src/main/images/"+activite.getImage();
            setImage(imagepath);
        }
        if(currentLocale.getLanguage()=="en"){

            try {
                labellistactivites.setText(Translator.translate(labellistactivites.getText(),"fr","en"));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if(currentLocale.getLanguage()=="fr"){
            try {
                labellistactivites.setText(Translator.translate(labellistactivites.getText(),"en","fr"));


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void setImage(String imagePath) {
        try {
            Image image = new Image(new File(imagePath).toURI().toString());
            activitieimage.setImage(image);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());

        }
    }



    private Pane createActionPane(Activite activite) {
        Locale currentLocale = LanguageSettings.getCurrentLocale();

        Pane pane = new Pane();
        pane.setPrefWidth(740.0);
        pane.setPrefHeight(164.0);
        pane.setStyle("-fx-background-color: #ffff;");
        ImageView imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);
        imageView.setLayoutX(10.0);
        imageView.setLayoutY(2.0);

        String imagePath = "C:/Users/Oussama/IdeaProjects/XpUser/src/main/images/" + activite.getImage();
        Image image = new Image(new File(imagePath).toURI().toString(), true);
        imageView.setImage(image);


        String partOfDescription = activite.getDescription().length() > 70 ? activite.getDescription().substring(0, 60) : activite.getDescription();
        String partOfNom = activite.getNom().length() > 70 ? activite.getNom().substring(0, 60) : activite.getNom();

        if(currentLocale.getLanguage()=="en"){

            try {
                partOfDescription = Translator.translate(partOfDescription,"fr","en");
                partOfNom = Translator.translate(partOfNom,"fr","en");


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if(currentLocale.getLanguage()=="fr"){
            try {
                partOfDescription = Translator.translate(partOfDescription,"en","fr");
                partOfNom = Translator.translate(partOfNom,"en","fr");

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        /*Locale local = Locale.getDefault();
        if (local.getLanguage().equals("fr")){

        }*/
        Text title = new Text(partOfNom+"");
        title.setLayoutX(220.0);
        title.setLayoutY(50.0);
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Text description = new Text(partOfDescription + "...");
        description.setLayoutX(220.0);
        description.setLayoutY(108.0);
        description.setStyle("-fx-font-size: 13px;");


        pane.getChildren().addAll(imageView,description,title);




        return pane;
    }

    @FXML
    void EN(ActionEvent event) throws IOException{
        LanguageSettings.setCurrentLocale(new Locale("en"));

        Parent root = FXMLLoader.load(getClass().getResource("/Activite/AfficherActiviteFront.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void FR(ActionEvent event) throws IOException{
        LanguageSettings.setCurrentLocale(new Locale("fr_Fr"));

        Parent root = FXMLLoader.load(getClass().getResource("/Activite/AfficherActiviteFront.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }





    @FXML
    void back(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Activite/Home.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

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
    }

