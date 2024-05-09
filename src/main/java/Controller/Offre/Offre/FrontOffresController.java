package Controller.Offre.Offre;

import Entities.Offre.Offre;
import Services.OffreService.OffreService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import com.google.gson.JsonObject;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.methods.CloseableHttpResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import com.google.gson.Gson;


import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class FrontOffresController implements Initializable {

    @FXML
    private VBox vBox;
    @FXML
    private ScrollPane scrollPane;

    private OffreService offreService = new OffreService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherOffres();
    }

    private void handleReservation(Offre offre) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Confirmation de réservation");
        dialog.setHeaderText("Vérifier votre email pour confirmer la réservation:");
        dialog.setContentText("Email:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String email = result.get();
            offreService.decrementerNbPlaces(offre);
            sendConfirmationEmail(email, offre.getNom(), offre.getDescription());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation de réservation");
            alert.setHeaderText(null);
            alert.setContentText("Félicitations ! Vous avez réservé cette offre.");
            alert.showAndWait();
            refresh();
        }
    }



    private String generateRichText(String inputText) throws IOException {
        String apiKey = "sk-proj-aBiWA6jAa0K0XSHzjR5kT3BlbkFJgchvARC0baVcduATc2At";
        String apiUrl = "https://api.openai.com/v1/completions";

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("model", "gpt-3.5-turbo");
        jsonObject.addProperty("prompt", inputText + "\n" + "enrichi cette paragraphe pour que  soit claire et bien formulé :");
        String requestBody = new Gson().toJson(jsonObject);
        //String requestBody= inputText + "\n" +"enrichi cette paragraphe pour que  soit claire et bien formulé :";
        //Gson gson = new Gson();
        //String promptJson = gson.toJson(S1);

        // Construire la requête JSON
        //String requestBody = "{\"model\":\"text-davinci\", \"prompt\":" + promptJson + ", \"max_tokens\":100}";


        // Créer un client HTTP
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(apiUrl);

        // Ajouter l'en-tête d'authentification
        request.addHeader("Authorization", "Bearer " + apiKey);
        request.addHeader("Content-Type", "application/json");

        // Ajouter le corps de la requête
        request.setEntity(new StringEntity(requestBody));

        // Exécuter la requête
        CloseableHttpResponse response = httpClient.execute(request);

        // Lire la réponse
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder responseBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            responseBody.append(line);
        }

        // Fermer les ressources
        reader.close();
        httpClient.close();

        // Récupérer le texte généré de la réponse
        return responseBody.toString();
    }


    private void sendConfirmationEmail(String recipientEmail, String OffreNom, String OffreDescription ) {
        // Définir les propriétés SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        // Obtenir la session
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication("tradaziz7@gmail.com", "simn zyto nznn hqor");
            }
        });

        try {
            // Créer un message MIME
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("tradaziz7@gmail.com"));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Confirmation de réservation");

            String emailContent = "Félicitations ! Vous avez réservé l'offre : " + OffreNom + "\n" +
                    "<p>Description de l'offre : " + OffreDescription + "</p>\n" ;

            //String enrichedText = generateRichText(emailContent);

            // Construire le contenu HTML
            String htmlContent = "<html> <!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "  <head>\n" +
                    "    <meta charset=\"UTF-8\" />\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\" />\n" +
                    "    <title>Confirmation de réservation</title>\n" +
                    "\n" +
                    "    <link\n" +
                    "      href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap\"\n" +
                    "      rel=\"stylesheet\"\n" +
                    "    />\n" +
                    "  </head>\n" +
                    "  <body\n" +
                    "    style=\"\n" +
                    "      margin: 0;\n" +
                    "      font-family: 'Poppins', sans-serif;\n" +
                    "      background: #ffffff;\n" +
                    "      font-size: 14px;\n" +
                    "    \"\n" +
                    "  >\n" +
                    "    <div\n" +
                    "      style=\"\n" +
                    "        max-width: 680px;\n" +
                    "        margin: 0 auto;\n" +
                    "        padding: 45px 30px 60px;\n" +
                    "        background: #000080;\n" +
                    "        background-image: url(https://dynamic-media-cdn.tripadvisor.com/media/photo-o/28/1f/76/f4/caption.jpg?w=1200&h=-1&s=1);\n" +
                    "        background-repeat: no-repeat;\n" +
                    "        background-size: 800px 452px;\n" +
                    "        background-position: top center;\n" +
                    "        font-size: 14px;\n" +
                    "        color: #434343;\n" +
                    "      \"\n" +
                    "    >\n" +
                    "      <header>\n" +
                    "        <table style=\"width: 100%;\">\n" +
                    "          <tbody>\n" +
                    "            <tr style=\"height: 0;\">\n" +
                    "              <td>\n" +
                    "                <img\n" +
                    "                  alt=\"\"\n" +
                    "                  src=\"https://dynamic-media-cdn.tripadvisor.com/media/photo-o/28/1f/76/f4/caption.jpg?w=1200&h=-1&s=1\"\n" +
                    "                  height=\"30px\"\n" +
                    "                />\n" +
                    "              </td>\n" +
                    "              <td style=\"text-align: right;\">\n" +
                    "                <span\n" +
                    "                  style=\"font-size: 16px; line-height: 30px; color: #ffffff;\"\n" +
                    "                  > Xperience Tunisia </span\n" +
                    "                >\n" +
                    "              </td>\n" +
                    "            </tr>\n" +
                    "          </tbody>\n" +
                    "        </table>\n" +
                    "      </header>\n" +
                    "\n" +
                    "      <main>\n" +
                    "        <div\n" +
                    "          style=\"\n" +
                    "            margin: 0;\n" +
                    "            margin-top: 70px;\n" +
                    "            padding: 92px 30px 115px;\n" +
                    "            background: #ffffff;\n" +
                    "            border-radius: 30px;\n" +
                    "            text-align: center;\n" +
                    "          \"\n" +
                    "        >\n" +
                    "          <div style=\"width: 100%; max-width: 489px; margin: 0 auto;\">\n" +
                    "            <h1\n" +
                    "              style=\"\n" +
                    "                margin: 0;\n" +
                    "                font-size: 24px;\n" +
                    "                font-weight: 500;\n" +
                    "                color: #1f1f1f;\n" +
                    "              \"\n" +
                    "            >\n" + emailContent +
                    "            </h1>\n" +
                    "          </div>\n" +
                    "        </div>\n" +
                    "\n" +
                    "        <p\n" +
                    "          style=\"\n" +
                    "            max-width: 400px;\n" +
                    "            margin: 0 auto;\n" +
                    "            margin-top: 90px;\n" +
                    "            text-align: center;\n" +
                    "            font-weight: 500;\n" +
                    "            color: #8c8c8c;\n" +
                    "          \"\n" +
                    "        >\n" +
                    "          Need help? Ask at\n" +
                    "          <a\n" +
                    "            href=\"mailto:experiencetunisiaa@gmail.com\"\n" +
                    "            style=\"color: #499fb6; text-decoration: none;\"\n" +
                    "            >experiencetunisiaa@gmail.com</a\n" +
                    "          >\n" +
                    "          or visit our\n" +
                    "          <a\n" +
                    "            href=\"\"\n" +
                    "            target=\"_blank\"\n" +
                    "            style=\"color: #499fb6; text-decoration: none;\"\n" +
                    "            >Local place</a\n" +
                    "          >\n" +
                    "        </p>\n" +
                    "      </main>\n" +
                    "\n" +
                    "      <footer\n" +
                    "        style=\"\n" +
                    "          width: 100%;\n" +
                    "          max-width: 490px;\n" +
                    "          margin: 20px auto 0;\n" +
                    "          text-align: center;\n" +
                    "          border-top: 1px solid #e6ebf1;\n" +
                    "        \"\n" +
                    "      >\n" +
                    "        <p\n" +
                    "          style=\"\n" +
                    "            margin: 0;\n" +
                    "            margin-top: 40px;\n" +
                    "            font-size: 16px;\n" +
                    "            font-weight: 600;\n" +
                    "            color: #434343;\n" +
                    "          \"\n" +
                    "        >\n" +
                    "         Xperience Tunisia\n" +
                    "        </p>\n" +
                    "        <p style=\"margin: 0; margin-top: 8px; color: #434343;\">\n" +
                    "          Address 4000, City: Tunis, State: North.\n" +
                    "        </p>\n" +
                    "        <div style=\"margin: 0; margin-top: 16px;\">\n" +
                    "          <a href=\"\" target=\"_blank\" style=\"display: inline-block;\">\n" +
                    "            \n" +
                    "          </a>\n" +
                    "          <a\n" +
                    "            href=\"\"\n" +
                    "            target=\"_blank\"\n" +
                    "            style=\"display: inline-block; margin-left: 8px;\"\n" +
                    "          >\n" +
                    "           </a>\n" +
                    "          <a\n" +
                    "            href=\"\"\n" +
                    "            target=\"_blank\"\n" +
                    "            style=\"display: inline-block; margin-left: 8px;\"\n" +
                    "          >\n" +
                    "            \n" +
                    "          </a>\n" +
                    "          <a\n" +
                    "            href=\"\"\n" +
                    "            target=\"_blank\"\n" +
                    "            style=\"display: inline-block; margin-left: 8px;\"\n" +
                    "          >\n" +
                    "            </a>\n" +
                    "        </div>\n" +
                    "        <p style=\"margin: 0; margin-top: 16px; color: #434343;\">\n" +
                    "          Copyright © 2024 Company. All rights reserved.\n" +
                    "        </p>\n" +
                    "      </footer>\n" +
                    "    </div>\n" +
                    "  </body>\n" +
                    "</html>\n </html>";

            // Définir le contenu HTML du message
            message.setContent(htmlContent, "text/html; charset=utf-8");

            // Envoyer le message
            Transport.send(message);
            System.out.println("Email envoyé avec succès !");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
        }
    }




    public void refresh() {
        ArrayList<Offre> offres = offreService.readAll();
        vBox.getChildren().clear();
        for (Offre offre : offres) {
            Label nomLabel = new Label("Nom de l'offre: " + offre.getNom());
            Label descriptionLabel = new Label("Description de l'offre: " + offre.getDescription());
            Label dateFinLabel = new Label("Date de fin de l'offre: " + offre.getDate_fin());
            Label nbPlacesLabel = new Label("Nombre de places restantes pour cette offre : " + offre.getNb_place());
            ImageView imageView = new ImageView(new Image("C:\\Users\\Oussama\\Desktop\\Integ user done\\XpUser\\src\\main\\resources\\offre\\1.png"));
            imageView.setFitWidth(100);

            imageView.setPreserveRatio(true);

            VBox offreBox = new VBox(imageView, nomLabel, descriptionLabel, dateFinLabel, nbPlacesLabel);
            offreBox.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 5px; -fx-spacing: 5px;");
            Button reserverButton = new Button("Réserver");
            reserverButton.setOnAction(event -> handleReservation(offre));

            offreBox.getChildren().add(reserverButton);

            vBox.getChildren().add(offreBox);
        }
        if (scrollPane != null) {
            scrollPane.layout();
        } else {
            System.err.println("Le scrollPane est null. Impossible d'appeler la méthode layout().");
        }

    }

    private void afficherOffres() {
        ArrayList<Offre> offres = offreService.readAll();
        for (Offre offre : offres) {
            Label nomLabel = new Label("Nom de l'offre: " + offre.getNom());
            Label descriptionLabel = new Label("Description de l'offre: " + offre.getDescription());
            Label dateFinLabel = new Label("Date de fin de l'offre: " + offre.getDate_fin());
            Label nbPlacesLabel = new Label("Nombre de places restantes pour cette offre : " + offre.getNb_place());
            ImageView imageView = new ImageView(new Image("C:\\Users\\Oussama\\Desktop\\Integ user done\\XpUser\\src\\main\\resources\\offre\\1.png"));
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);

            VBox offreBox = new VBox(imageView, nomLabel, descriptionLabel, dateFinLabel, nbPlacesLabel);
            offreBox.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 5px; -fx-spacing: 5px;");
            Button reserverButton = new Button("Réserver");
            reserverButton.setOnAction(event -> handleReservation(offre));

            offreBox.getChildren().add(reserverButton);

            vBox.getChildren().add(offreBox);
        }
    }
}
