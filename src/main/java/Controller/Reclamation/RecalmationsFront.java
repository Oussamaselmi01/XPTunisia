package Controller.Reclamation;

import Controller.User.SessionManager;
import Entities.Reclamation.Reclammation;
import Entities.User.User;
import Services.Reclamation.ReclamtionResponseService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class RecalmationsFront implements Initializable {
    @FXML
    private VBox vbox;
    @FXML
    private HBox hbox;
    private int idUser;

    public void gotoajout(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxll=new FXMLLoader();
        fxll.setLocation(getClass().getResource("/Reclamation/AddReclamtion.fxml"));
        Parent roott=fxll.load();
        AddReclamtion c=fxll.getController();
        c.SetData(this.idUser);

        Stage stage = new Stage();
        stage.setScene(new Scene(roott));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User currentUser = SessionManager.getCurrentUser();

        FXMLLoader fxl=new FXMLLoader();
        fxl.setLocation(getClass().getResource("/Reclamation/HeaderFront.fxml"));
        Parent root= null;
        try {
            root = fxl.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        hbox.getChildren().add(root);
        ReclamtionResponseService reclamtionResponseService =new ReclamtionResponseService();
        try {
            List<Reclammation>reclammations=reclamtionResponseService.getAllUserReclamations(currentUser.getId());
            for(int i=0;i<reclammations.size();i++){
                FXMLLoader fxll=new FXMLLoader();
                fxll.setLocation(getClass().getResource("/Reclamation/CardReclamtionUser.fxml"));
                Parent roott=fxll.load();
                CardReclamtionUser c=fxll.getController();
                c.setData(reclammations.get(i));
                vbox.getChildren().add(roott);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
