package Controller.Reclamation;


import Entities.Reclamation.Reclammation;
import Services.Reclamation.ReclamtionResponseService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class CardReclamtionUser {
    @FXML
    private Label titre;
    @FXML
    private Label desciption;
    @FXML
    private Label date;
    @FXML
    private Label statut;
    @FXML
    private Label response;
    private Reclammation r;
    public void setData(Reclammation r){
        titre.setText(r.getTitre());
        desciption.setText(r.getDescription());
        date.setText(r.getDate_creation().toString());
        statut.setText(r.getStatut());
        if(r.getReponse()!=null){
            response.setText(r.getReponse().getContenu());
        }
        this.r=r;
    }
    public void modifier(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxll=new FXMLLoader();
        fxll.setLocation(getClass().getResource("/Reclamation/EditReclamtion.fxml"));
        Parent roott=fxll.load();
        EditReclamtion c=fxll.getController();
        c.SetData(this.r);
        Stage stage = new Stage();
        stage.setScene(new Scene(roott));
        stage.show();
    }

    public void supprimer(ActionEvent actionEvent) throws SQLException, IOException {
        ReclamtionResponseService reclamtionResponseService =new ReclamtionResponseService();
        int t=reclamtionResponseService.supprimerReclamtion(this.r.getId());
        if(t==1){
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/Reclamation/RecalamtionsFront.fxml"));
            Parent root=loader.load();
            statut.getScene().setRoot(root);
        }
    }
}
