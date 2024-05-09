package Controller.Activite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class Home {
    @FXML
    private Button actid;

    @FXML
    private Button catid;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    private ResourceBundle bundle;
    private Locale locale;

    private Stage stage;
    private Scene scene;
    private Parent root;




        @FXML
        void naviguer1(ActionEvent event) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/Activite/AfficherActivite.fxml"));
                actid.getScene().setRoot(root);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }



        }

        @FXML
        void naviguer2(ActionEvent event) throws IOException {
            root =FXMLLoader.load(getClass().getResource("/Activite/AfficherCategorie.fxml"));
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


    public void initialize(URL url , ResourceBundle rb){

    }

    private void loadLang(String lang) {
        locale = new Locale(lang);
        Locale.setDefault(locale);



        bundle = ResourceBundle.getBundle("Activite.lang.lang", locale);


        label1.setText(bundle.getString("label1"));
        label2.setText(bundle.getString("label2"));
    }
    @FXML
    void EN(ActionEvent event) {
        loadLang("en");
    }

    @FXML
    void FR(ActionEvent event) {
        loadLang("fr");
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

