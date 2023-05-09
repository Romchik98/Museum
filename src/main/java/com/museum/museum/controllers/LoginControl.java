package com.museum.museum.controllers;

import com.museum.museum.ds.User;
import com.museum.museum.databaseUtilities.DatabaseControllers;
import com.museum.museum.Start;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Objects;


public class LoginControl {

    @FXML
    public TextField loginName;
    @FXML
    public PasswordField password;

    private Connection connection;
    private Statement statement;

    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 16;

    private final String secretKey = "F3B9E8C7D6A5A4B3C2D1E0F1A2B3C4D5";

    public static void alertMessage(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(s);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    public void ValidateLogin(ActionEvent actionEvent) throws Exception {
        User user = DatabaseControllers.validateLogin(loginName.getText(), password.getText());

        if (user.getUserType().equals("User") && user != null)
        {
            alertMessage("Prisijungta kaip vartotojas");
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-user-window.fxml"));
            Parent root = fxmlLoader.load();
            MainUserWindowControl mainUserWindowControl = fxmlLoader.getController();
            mainUserWindowControl.setLoggedInUser(user);
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.loginName.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if (user.getUserType().equals("Admin") && user != null)
        {
            alertMessage("Prisijungta kaip administratorius");
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window.fxml"));
            Parent root = fxmlLoader.load();
            MainWindowControl mainWindowControl = fxmlLoader.getController();
            mainWindowControl.setLoggedInUser(user);
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.loginName.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }
        else
            alertMessage("Neteisingi prisijungimo duomenys");

    }

    public void startSignUp(ActionEvent actionEvent) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader((Start.class.getResource("sign-up-form.fxml")));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) loginName.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /*public static boolean validatePassword(String password, String storedHashedPassword) {
        boolean isValid = false;

        try {
            byte[] hashedBytes = Base64.getDecoder().decode(storedHashedPassword);

            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), hashedBytes, 10000, 256);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte[] enteredHashedBytes = keyFactory.generateSecret(spec).getEncoded();

            isValid = Base64.getEncoder().encodeToString(enteredHashedBytes).equals(storedHashedPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return isValid;
    }*/

        /*public LoginControl() {
            String encryptionKey = System.getenv("KRISTIS_APP_ENCRYPTION_KEY");
            secretKey = Objects.requireNonNullElse(encryptionKey, "m#5j$H0@lN^cQw8K");
        }*/

        public String encrypt(String password) throws Exception {
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

}
