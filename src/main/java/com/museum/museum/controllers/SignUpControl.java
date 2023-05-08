package com.museum.museum.controllers;

import com.museum.museum.Start;
import com.museum.museum.databaseUtilities.DatabaseControllers;
import com.museum.museum.ds.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SignUpControl {
    @FXML
    public TextField loginName;
    @FXML
    public PasswordField password;
    @FXML
    public TextField name;
    @FXML
    public TextField surname;

    private Connection connection;
    private PreparedStatement preparedStatement;

    public void startSignUp(ActionEvent actionEvent) throws IOException {
        if (this.isValidInput(this.getLoginName()) && this.isValidInput(this.getPassword()) && this.isValidInput(this.getName()) && this.isValidInput(this.getUserSurname())) {
            DatabaseControllers.createUser(new User(this.getLoginName(), this.getPassword(), this.getName(), this.getUserSurname()));
            this.returnToPrevious();
        }
    }

    public String signUp(String login, String password, String userName, String userSurname) throws IOException {
        try {
            if(DatabaseControllers.doesUserNameExist(login) == true)
                return("User already exists");
            if (    this.isValidInput(login) &&
                    this.isValidInput(password) &&
                    this.isValidInput(userName) &&
                    this.isValidInput(userSurname)) {
                DatabaseControllers.createUser(new User(login, password, userName, userSurname));
                return("User created");
            }
        }  catch (Exception e) {
            return("User not created" + e);
        }
        return ("Failed");
    }

    private void returnToPrevious() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("login-window.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) loginName.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void returnToLoginPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("login-window.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) loginName.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public String getLoginName() {
        String loginName = this.loginName.getText();
        if (loginName.length() > 0) {
            return loginName;
        } else {
            LoginControl.alertMessage("You must enter login name");
        };
        return "";
    }

    public String getPassword() {
        String password = this.password.getText();
        if (password.length() > 0)
            return password;
        LoginControl.alertMessage("You must enter password");
        return "";
    }

    public String getName() {
        String name = this.name.getText();
        if (name.length() > 0)
            return name;
        LoginControl.alertMessage("You must enter name");
        return "";
    }

    public String getUserSurname() {
        String surname = this.surname.getText();
        if (surname.length() > 0)
            return surname;
        LoginControl.alertMessage("You must enter last name");
        return "";
    }

    private boolean isValidInput(String input) {
        if (input.length() == 0)
            return false;
        return true;
    }
}
