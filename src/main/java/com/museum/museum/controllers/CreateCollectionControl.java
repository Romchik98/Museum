package com.museum.museum.controllers;

import com.museum.museum.Start;
import com.museum.museum.databaseUtilities.DatabaseControllers;
import com.museum.museum.ds.Collection;
import com.museum.museum.ds.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class CreateCollectionControl {

    @FXML
    public TextField collectionName;
    @FXML
    public TextField collectionDescription;

    private User loggedInUser;

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public void createCollection(ActionEvent actionEvent) throws IOException, SQLException {
        boolean doesExist = false;
        for(Collection collection : DatabaseControllers.getAllCollections()) {
            if (collection.getName().equals(this.collectionName.getText())) {
                LoginControl.alertMessage("Collection already exists");
                doesExist = true;
                break;
            } else if (this.collectionName.getText().length() < 1) {
                LoginControl.alertMessage("You must enter Collection name");
                doesExist = true;
                break;
            }
        }
        if(doesExist == false) {
            DatabaseControllers.createCollection(new Collection(this.collectionName.getText(), this.collectionDescription.getText()), this.loggedInUser);
        }
        this.goBack();
    }

    public void goBack() throws IOException, SQLException {
        if(loggedInUser.getUserType().equals("Admin")) {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            MainWindowControl mainCollectionsWindow = fxmlLoader.getController();
            mainCollectionsWindow.setLoggedInUser(this.loggedInUser);
            Stage stage = (Stage) this.collectionName.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-user-window.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            MainWindowControl mainCollectionsWindow = fxmlLoader.getController();
            mainCollectionsWindow.setLoggedInUser(this.loggedInUser);
            Stage stage = (Stage) this.collectionName.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    public String newCollection(String name, String description, User loggedInUser) throws IOException, SQLException {
        boolean doesExist = false;
        for(Collection collection : DatabaseControllers.getAllCollections()) {
            if (collection.getName().equals(name)) {
                doesExist = true;
                return ("Course already exists");
            }
        }
        if(isValidInput(name) == false) {
            return ("Please fill name field");
        }
        if(isValidInput(description) == false) {
            return ("Please fill description field");
        }
        if(doesExist == false)
        {
            try {
                DatabaseControllers.createCollection(new Collection(name, description), loggedInUser);
                return ("Collection created");
            } catch (Exception e) {
                System.out.println(e);
                return ("Error creating Collection" + e);
            }
        }
        return ("Failed");
    }

    private boolean isValidInput(String input) {
        if (input.length() == 0)
            return false;
        return true;
    }

}