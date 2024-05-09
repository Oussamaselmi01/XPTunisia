package Controller.Reclamation;


import Entities.Reclamation.Reclammation;
import Services.Reclamation.ReclamtionResponseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ReclamtionAdmin implements Initializable {
    @FXML
    private VBox vbox;
    @FXML
    private VBox cards;
    @FXML
    private PieChart pie;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxl=new FXMLLoader();
        fxl.setLocation(getClass().getResource("/Reclamation/SideBar.fxml"));
        Parent root= null;
        try {
            root = fxl.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        vbox.getChildren().add(root);
        ReclamtionResponseService reclamtionResponseService =new ReclamtionResponseService();
        try {
            List<Reclammation> reclammations=reclamtionResponseService.getAllReclamations();
            Map<String, Integer> countByType = new HashMap<>();
            int totalReclamations = reclammations.size();
            if(totalReclamations>0){
                for (Reclammation reclammation : reclammations) {
                    String type = reclammation.getTitre();
                    countByType.put(type, countByType.getOrDefault(type, 0) + 1);
                }
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
                for (Map.Entry<String, Integer> entry : countByType.entrySet()) {
                    String type = entry.getKey();
                    int count = entry.getValue();
                    double percentage = (double) count / totalReclamations * 100;
                    PieChart.Data data = new PieChart.Data(type, percentage);
                    pieChartData.add(data);
                }
                pie.setData(pieChartData);
            }
            for(int i=0;i<reclammations.size();i++){
                FXMLLoader fxll=new FXMLLoader();
                fxll.setLocation(getClass().getResource("/Reclamation/CardReclamtionAdmin.fxml"));
                Parent roott=fxll.load();
                CardReclamtionAdmin c=fxll.getController();
                c.SetData(reclammations.get(i));
                cards.getChildren().add(roott);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void back(ActionEvent event) {
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

