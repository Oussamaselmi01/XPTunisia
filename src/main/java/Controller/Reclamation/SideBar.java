package Controller.Reclamation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public class SideBar {
    @FXML
    private Button btnrecla;
    public void gotreclamtions(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Reclamation/ReclamtionAdmin.fxml"));
        Parent root=loader.load();
        btnrecla.getScene().setRoot(root);
    }
}
