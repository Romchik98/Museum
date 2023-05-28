package com.museum.museum.controllers;

import com.museum.museum.Start;
import com.museum.museum.databaseUtilities.DatabaseControllers;
import com.museum.museum.ds.Collection;
import com.museum.museum.ds.Museum;
import com.museum.museum.ds.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class CreateMuseumControl {

    @FXML
    public TextField museumName;

    private User loggedInUser;

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public void createMuseum() throws IOException, SQLException {
        boolean doesExist = false;
        for (Collection collection : DatabaseControllers.getAllCollections()) {
            if (collection.getName().equals(this.museumName.getText())) {
                LoginControl.alertMessage("Muziejus jau egzistuoja");
                doesExist = true;
                break;
            } else if (this.museumName.getText().length() < 1) {
                LoginControl.alertMessage("Įveskite muziejaus pavadinimą");
                doesExist = true;
                break;
            }
        }
        if (!doesExist) {
            DatabaseControllers.createMuseum(new Museum(this.museumName.getText()));
        }
        this.goBack();
    }

    public void goBack() throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        MainWindowControl mainCollectionsWindow = fxmlLoader.getController();
        mainCollectionsWindow.setLoggedInUser(this.loggedInUser);
        Stage stage = (Stage) this.museumName.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}