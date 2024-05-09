package Controller.Reclamation;

import Entities.Reclamation.Reclammation;
import Entities.Reclamation.Reponse;
import Services.Reclamation.ReclamtionResponseService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

public class Repondre {
    @FXML
    private Label ccok;
    @FXML
    private Label cccontenu;
    @FXML
    private TextArea contenu;
    @FXML
    private VBox vbox;
    private int id;
    private String userEmail="Montassar.Mtar@esprit.tn"; // Assuming you have the user's email address

    public void setId(int id){
        this.id=id;
        FXMLLoader fxl=new FXMLLoader();
        fxl.setLocation(getClass().getResource("/Reclamation/SideBar.fxml"));
        Parent root= null;
        try {
            root = fxl.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        vbox.getChildren().add(root);
    }

    public void ok(ActionEvent actionEvent) throws SQLException, IOException {
        ReclamtionResponseService reclamtionResponseService = new ReclamtionResponseService();
        int t = 0;

        if (contenu.getText().isEmpty()) {
            t = 1;
            this.cccontenu.setText("Vous devez saisir la Reponse");
        } else {
            this.cccontenu.setText("");
        }

        if (t == 0) {
            // Create a new response object
            Reponse r = new Reponse();
            r.setContenu(contenu.getText());
            r.setDate_creation(new Date());

            // Add the response to the database
            t = reclamtionResponseService.addResponse(r, this.id);

            if (t == 0) {
                this.ccok.setText("Erreur lors de l'ajout, veuillez réessayer.");
            } else {
                // Retrieve more details about the complaint
                Reclammation complaint = reclamtionResponseService.getComplaintDetails(this.id);

                // Construct the email content with complaint details
                String emailContent =
                        "Your verification code is: " ;
                String htmlContent = "<html> <!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "  <head>\n" +
                        "    <meta charset=\"UTF-8\" />\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                        "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\" />\n" +
                        "    <title>Static Template</title>\n" +
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
                        "                  > Xperience Tunisia - Reponse a votre reclammation </span\n" +
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
                        "            >\n" +
                        "              Your OTP\n" +
                        "            </h1>\n" +
                        "            <p\n" +
                        "              style=\"\n" +
                        "                margin: 0;\n" +
                        "                margin-top: 17px;\n" +
                        "                font-size: 16px;\n" +
                        "                font-weight: 500;\n" +
                        "              \"\n" +
                        "            >\n" +
                        "                  Hey cher utilisateur\n" +
                        "            </p>\n" +
                        "            <p\n" +
                        "              style=\"\n" +
                        "                margin: 0;\n" +
                        "                margin-top: 17px;\n" +
                        "                font-weight: 500;\n" +
                        "                letter-spacing: 0.56px;\n" +
                        "              \"\n" +
                        "            >\n" +
                        "              Votre reclamation concernant \"" + complaint.getTitre() + "\" a ete traitee avec succes.\n" +
                        "              Nous avons pris en compte les details suivants :\n" +
                        "              <br/>\n" +
                        "              - Description : " + complaint.getDescription() + "\n" +
                        "              <br/>\n" +
                        "              - Date de creation : " + complaint.getDate_creation() + "\n" +
                        "              <br/>\n" +
                        "              - Reponse de la reclamation : " + complaint.getReponse().getContenu() + "\n" +
                        "              <br/>\n" +
                        "              \n" +
                        "              <span style=\"font-weight: 600; color: #1f1f1f;\"></span>.\n" +
                        "              \n" +
                        "             \n" +
                        "            </p>\n" +
                        "            <p\n" +
                        "              style=\"\n" +
                        "                margin: 0;\n" +
                        "                margin-top: 60px;\n" +
                        "                font-size: 40px;\n" +
                        "                font-weight: 600;\n" +
                        "                letter-spacing: 25px;\n" +
                        "                color: #ba3d4f;\n" +
                        "              \"\n" +
                        "            >\n" +
                        "              \n" +
                        "            </p>\n" +
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

                // Extract the content of the response and include it in the email
                //emailContent += "Reponse de la réclamation :" + complaint.getReponse().getContenu() + "\n";

                // Send email to user with detailed response

                sendEmailToUser(userEmail, "Réponse à votre réclamation", htmlContent);

                // Redirect to another scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reclamation/ReclamtionAdmin.fxml"));
                Parent root = loader.load();
                contenu.getScene().setRoot(root);
            }
        }
    }


    private void sendEmailToUser(String userEmail, String subject, String content) {
        String host = "smtp.gmail.com"; // Change to your email provider's SMTP server
        String from = "montassarmtar@gmail.com"; // Change to your email address
        String password = "qntm chzh npax yppu"; // Change to your email password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
            message.setSubject(subject);
            //message.setText(content);
            //message.setContent(content, "text/html");
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(content, "text/html");

            // Create the multipart message and add the HTML part
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);

            // Set the content of the message
            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Email sent to " + userEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
