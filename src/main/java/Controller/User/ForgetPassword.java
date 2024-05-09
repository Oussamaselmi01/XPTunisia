package Controller.User;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;


import com.sendgrid.*;
import Services.UserService.UserService;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ForgetPassword {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField emailField;

    @FXML
    private TextField verificationCodeField;


    private void switchScene(String fxmlFile, String email, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Pass the email to the ResetPassword controller
            ResetPassword resetPasswordController = loader.getController();
            resetPasswordController.setEmail(email);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchScene2(String fxmlFile, ActionEvent event) {
        try {
            System.out.println("fxml:"+ fxmlFile);

            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goback(ActionEvent event) {
        switchScene2("/login.fxml", event);
    }

    @FXML
    void resetPassword(ActionEvent event) {
        String email = emailField.getText();
        String verificationCode = verificationCodeField.getText();

        // Check if the verification code is correct
        UserService y = new UserService();
        if (y.isVerificationCodeCorrect(email, verificationCode)) {
            // Redirect the user to the ResetPassword interface and pass the email
            switchScene("/resetPassword.fxml", email, event);

            System.out.println("Verification code is correct. Redirecting to reset password interface.");
        } else {
            // Display an error message if the verification code is incorrect
            showAlert(Alert.AlertType.ERROR, "Error", "Incorrect verification code. Please try again.");
        }
    }

    @FXML
    void sendVerificationCode(ActionEvent event) {
        String email = emailField.getText();

        // Check if the email is empty
        if (email.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter your email address.");
            return;
        }

        // Check if the email is valid
        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid email address.");
            return;
        }

        // Check if the email exists in the database
        UserService crudUtilisateurs = new UserService();
        if (!crudUtilisateurs.emailExists(email)) {
            showAlert(Alert.AlertType.ERROR, "Error", "The entered email does not exist.");
            return;
        }
        // Send the verification code via email using SendGrid API
        sendVerificationCodeViaSendGrid(email);
    }

    private void sendVerificationCodeViaSendGrid(String email) {
        Email from = new Email("experiencetunisiaa@gmail.com");
        String subject = "Verification Code";
        Email to = new Email(email);
        String verificationCode = generateVerificationCode(); // You need to implement this method
        UserService x = new UserService();
        x.updateVerificationCode(email,verificationCode);
        String bodyContent =


                "Your verification code is: " + verificationCode;
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
                "                  > Xperience Tunisia - Reset your password</span\n" +
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
                "                  Hey dear user\n" +
                "            </p>\n" +
                "            <p\n" +
                "              style=\"\n" +
                "                margin: 0;\n" +
                "                margin-top: 17px;\n" +
                "                font-weight: 500;\n" +
                "                letter-spacing: 0.56px;\n" +
                "              \"\n" +
                "            >\n" +
                "              Thank you for choosing Our company. Use the following OTP\n" +
                "              to complete the procedure to change your password." +
                "              \n" +
                "              <span style=\"font-weight: 600; color: #1f1f1f;\"></span>.\n" +
                "              Do not share this code with others. \n" +
                "             .\n" +
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
                "              \n" +  verificationCode +
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
                "</html>\n </html>"; // Replace this with your HTML content

        // Set up the mail content with both HTML and plain text
        Content content = new Content("text/html", htmlContent);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Verification code sent to your email.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to send verification code. Please try again later.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to send verification code. Please try again later.");
        }
    }
    @FXML
    void initialize() {

    }

    private String generateVerificationCode() {
        // Define the length of the verification code
        int length = 6;

        // Define the characters allowed in the verification code
        String characters = "0123456789";

        // Create a StringBuilder to store the verification code
        StringBuilder verificationCode = new StringBuilder();

        // Create a random object
        Random random = new Random();

        // Generate the verification code
        for (int i = 0; i < length; i++) {
            // Generate a random index within the range of the characters string
            int index = random.nextInt(characters.length());

            // Append the character at the random index to the verification code
            verificationCode.append(characters.charAt(index));
        }

        // Convert the StringBuilder to a String and return the verification code
        return verificationCode.toString();
    }

    private boolean isValidEmail(String email) {
        // Your email validation logic here
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
