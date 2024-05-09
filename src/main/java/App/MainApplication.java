package App;

import Controller.Offre.Offre.IndexOffreController;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    private static Stage stage; // Déclarer la scène de manière statique
    private static HostServices hostServices;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage; // Affecter la scène principale
        hostServices = getHostServices(); // Obtenez une référence à HostServices

        navigateToOffres(); // Débuter avec la vue Offres
        //navigateToTypes();
    }

    public static void navigateToOffres() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/offre/index-offre.fxml"));
        Parent root = fxmlLoader.load();

        IndexOffreController controller = fxmlLoader.getController();
        controller.setHostServices(hostServices);

        stage.setScene(new Scene(root));
        stage.setTitle("Offres");
        stage.show();
    }

    public static void navigateToTypes() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/type/index-type.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
        stage.setScene(scene);
        stage.setTitle("Types");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
