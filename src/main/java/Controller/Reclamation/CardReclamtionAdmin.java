package Controller.Reclamation;


import Entities.Reclamation.Reclammation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;

public class CardReclamtionAdmin {
    @FXML
    private Label nom;
    @FXML
    private Label prenom;
    @FXML
    private Label titre;
    @FXML
    private Label description;
    @FXML
    private Label statut;
    @FXML
    private Label date;
    @FXML
    private Label reponse;
    private Reclammation r;
    public void SetData(Reclammation r){
        if(r.getReponse()!=null){
            reponse.setText(r.getReponse().getContenu());
        }
        nom.setText(r.getUtulisateur().getNom());
        prenom.setText(r.getUtulisateur().getPrenom());
        titre.setText(r.getTitre());
        description.setText(r.getDescription());
        statut.setText(r.getStatut());
        date.setText(r.getDate_creation().toString());
        this.r=r;
    }
    public void repondre(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxll=new FXMLLoader();
        fxll.setLocation(getClass().getResource("/Reclamation/Repondre.fxml"));
        Parent roott=fxll.load();
        Repondre c=fxll.getController();
        c.setId(this.r.getId());
        statut.getScene().setRoot(roott);
    }
}
