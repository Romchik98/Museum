package com.museum.museum.controllers;

import com.museum.museum.Start;
import com.museum.museum.databaseUtilities.DatabaseControllers;
import com.museum.museum.ds.Collection;
import com.museum.museum.ds.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class EditCollectionControl {

    @FXML
    public TextField collectionName;
    @FXML
    public TextField collectionDescription;

    private User loggedInUser;
    private Collection selectedCollection;

    public void setLoggedInUser(User user) throws SQLException {
        this.loggedInUser = user;
    }

    public void setSelectedCollection(Collection collection) {
        this.selectedCollection = collection;
    }

    public void updateCollection() throws IOException, SQLException {
        if (this.collectionName.getText().length() < 1) {
            LoginControl.alertMessage("Įveskite kolekcijos pavadinimą");
        } else {
            DatabaseControllers.editCollection(new Collection(this.collectionName.getText(), this.collectionDescription.getText()), selectedCollection.getId());
            this.goBack();
        }
    }

    public void goBack() throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        MainWindowControl mainCollectionsWindow = fxmlLoader.getController();
        mainCollectionsWindow.setLoggedInUser(this.loggedInUser);
        Stage stage = (Stage) this.collectionName.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void loadCollectionData() {
        if (selectedCollection != null) {
            collectionName.setText(selectedCollection.getName());
            collectionDescription.setText(selectedCollection.getDescription());
        }
    }
}
