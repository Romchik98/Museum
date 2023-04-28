package controllers;

import com.museum.museum.MuseumManagementApp;
import databaseUtilities.DatabaseControllers;
import ds.User;
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
        if (this.isValidInput(this.getLoginName()) && this.isValidInput(this.getPassword()) && this.isValidInput(this.getName()) && this.isValidInput(this.getSurname())) {
            DatabaseControllers.createUser(new User(this.getLoginName(), this.getPassword(), this.getName(), this.getSurname()));
            this.returnToPrevious();
        }
    }

    private void returnToPrevious() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MuseumManagementApp.class.getResource("login-window.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) loginName.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public String getLoginName() {
        String loginName = this.loginName.getText();
        if (loginName.length() > 0)
            return loginName;
        LoginControl.alertMessage("You must enter login name");
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

    public String getSurname() {
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
