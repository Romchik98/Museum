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

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginControl {

    @FXML
    public TextField loginName;
    @FXML
    public PasswordField password;

    private Connection connection;
    private Statement statement;

    public static void alertMessage(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(s);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    public void ValidateLogin(ActionEvent actionEvent) throws SQLException, IOException{
        User user = DatabaseControllers.validateLogin(loginName.getText(), password.getText());

        if (user.getUserType().equals("User") && user != null)
        {
            alertMessage("Logged in as User");
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
            alertMessage("Logged in as Admin");
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
            alertMessage("Bad credentials");

    }

    public void startSignUp(ActionEvent actionEvent) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader((Start.class.getResource("sign-up-form.fxml")));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) loginName.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
