package com.museum.museum.controllers;

import com.museum.museum.ds.User;
import com.museum.museum.databaseUtilities.DatabaseControllers;
import com.museum.museum.Start;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class LoginControl {

    @FXML
    public TextField loginName;
    @FXML
    public PasswordField password;
    @FXML
    public Button infoButton;
    private static final String ALGORITHM = "AES";

    public static void alertMessage(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(s);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    public void ValidateLogin() throws Exception {
        User user = DatabaseControllers.validateLogin(loginName.getText(), password.getText());

        if (user.getUserType().equals("User"))
        {
            alertMessage("Prisijungta kaip vartotojas");
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-user-window.fxml"));
            Parent root = fxmlLoader.load();
            MainUserWindowControl mainUserWindowControl = fxmlLoader.getController();
            mainUserWindowControl.setLoggedInUser(user);
            mainUserWindowControl.loadUserdata();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.loginName.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if (user.getUserType().equals("Admin"))
        {
            alertMessage("Prisijungta kaip administratorius");
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window.fxml"));
            Parent root = fxmlLoader.load();
            MainWindowControl mainWindowControl = fxmlLoader.getController();
            mainWindowControl.setLoggedInUser(user);
            mainWindowControl.loadUserdata();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.loginName.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }
        else
            alertMessage("Neteisingi prisijungimo duomenys");

    }

    public void startSignUp() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader((Start.class.getResource("sign-up-form.fxml")));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) loginName.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

        public String encrypt(String password) throws Exception {
            String secretKey = "F3B9E8C7D6A5A4B3C2D1E0F1A2B3C4D5";
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(password.getBytes());
            return new String(Base64.getEncoder().encode(encrypted));
        }

        /*public String decrypt(String encryptedPassword) throws Exception {
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword.getBytes()));
            return new String(decrypted);
        }*/

    public void openInfo() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader((Start.class.getResource("information-window.fxml")));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

}
