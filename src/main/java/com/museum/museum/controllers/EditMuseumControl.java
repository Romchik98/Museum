package com.museum.museum.controllers;

import com.museum.museum.Start;
import com.museum.museum.databaseUtilities.DatabaseControllers;
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

public class EditMuseumControl {

    @FXML
    public TextField museumName;

    private User loggedInUser;
    private Museum selectedMuseum;

    public void setLoggedInUser(User user) throws SQLException {
        this.loggedInUser = user;
    }

    public void setSelectedMuseum(Museum museum) {
        this.selectedMuseum = museum;
    }

    public void updateMuseum() throws IOException, SQLException {
        if (this.museumName.getText().length() < 1) {
            LoginControl.alertMessage("Įveskite muziejaus pavadinimą");
        } else {
            DatabaseControllers.editMuseum(new Museum(this.museumName.getText()), selectedMuseum.getId());
            this.goBack();
        }
    }

    public void goBack() throws IOException, SQLException {
        if(loggedInUser.getUserType().equals("Admin")) {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            MainWindowControl mainCollectionsWindow = fxmlLoader.getController();
            mainCollectionsWindow.setLoggedInUser(this.loggedInUser);
            Stage stage = (Stage) this.museumName.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-user-window.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            MainWindowControl mainCollectionsWindow = fxmlLoader.getController();
            mainCollectionsWindow.setLoggedInUser(this.loggedInUser);
            Stage stage = (Stage) this.museumName.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    public void loadMuseumData() {
        if (selectedMuseum != null) {
            museumName.setText(selectedMuseum.getName());
        }
    }
}
