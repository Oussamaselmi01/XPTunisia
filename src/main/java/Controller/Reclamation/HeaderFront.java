package Controller.Reclamation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HeaderFront {
    @FXML
    private Button btnrec;
    public void gotoreclamtions(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reclamation/RecalamtionsFront.fxml"));
        Parent root = loader.load();

        // Create a new stage for the new scene
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
