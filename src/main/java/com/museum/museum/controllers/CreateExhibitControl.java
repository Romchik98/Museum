package com.museum.museum.controllers;

import com.museum.museum.Start;
import com.museum.museum.databaseUtilities.DatabaseControllers;
import com.museum.museum.ds.Exhibit;
import com.museum.museum.ds.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateExhibitControl {

    @FXML
    public TextField exhibitName;
    @FXML
    public TextField exhibitDescription;
    @FXML
    public TextField exhibitDateOfCreation;
    @FXML
    public TextField exhibitDateOfDiscovery;
    @FXML
    public TextField exhibitQuantity;
    @FXML
    public TextField exhibitCondition;
    @FXML
    public TextField exhibitPlaceOfCreation;
    @FXML
    public TextField exhibitPlaceOfDiscovery;
    @FXML
    public TextField exhibitDimensions;
    @FXML
    public TextField exhibitMaterials;
    @FXML
    public TextField exhibitType;
    @FXML
    public ComboBox exhibitStatus;
    @FXML
    public TextField exhibitLicence;
    @FXML
    public TextField exhibitCurrentPlace;
    @FXML
    public TextField exhibitLink;
    @FXML
    private RadioButton rButton1, rButton2;

    private User loggedInUser;
    private int selectedCollectionId;

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public void setSelectedCollectionId(int collectionId) {
        this.selectedCollectionId = collectionId;
    }

    public void chooseExhibitType(ActionEvent event) {
        if(rButton1.isSelected()) {
            exhibitDescription.setDisable(false);
            exhibitDateOfCreation.setDisable(false);
            exhibitPlaceOfCreation.setDisable(false);
            exhibitCondition.setDisable(false);
            exhibitName.setDisable(false);
            exhibitDimensions.setDisable(false);
            exhibitLicence.setDisable(false);
            exhibitMaterials.setDisable(false);
            exhibitStatus.setDisable(false);
            exhibitPlaceOfDiscovery.setDisable(false);
            exhibitQuantity.setDisable(false);
            exhibitDateOfDiscovery.setDisable(false);
            exhibitCurrentPlace.setDisable(false);
            exhibitLink.setDisable(true);
        } else if (rButton2.isSelected()) {
            exhibitDescription.setDisable(false);
            exhibitDateOfCreation.setDisable(false);
            exhibitPlaceOfCreation.setDisable(true);
            exhibitCondition.setDisable(true);
            exhibitName.setDisable(false);
            exhibitDimensions.setDisable(true);
            exhibitLicence.setDisable(false);
            exhibitMaterials.setDisable(true);
            exhibitStatus.setDisable(true);
            exhibitPlaceOfDiscovery.setDisable(true);
            exhibitQuantity.setDisable(true);
            exhibitDateOfDiscovery.setDisable(true);
            exhibitCurrentPlace.setDisable(true);
            exhibitLink.setDisable(false);
        }
    }

    public void createExhibit(ActionEvent actionEvent) throws SQLException, IOException   {
        //exhibitDescription.setDisable(true);
        for(Exhibit exhibit : DatabaseControllers.getExhibits(selectedCollectionId)) {
            //exhibitDescription.setDisable(true);
            if (exhibit.getName().equals(this.exhibitName.getText())) {
                LoginControl.alertMessage("Eksponatas jau egzistuoja");
                break;
            } else if (this.exhibitName.getText().length() < 1) {
                LoginControl.alertMessage("Įveskite eksponato pavadinimą");
                break;
            } else {
                DatabaseControllers.createExhibit(new Exhibit(this.exhibitName.getText(), selectedCollectionId, this.exhibitDescription.getText(), this.exhibitDateOfCreation.getText(), this.exhibitDateOfDiscovery.getText(),
                        this.exhibitQuantity.getText(), this.exhibitCondition.getText(), this.exhibitPlaceOfCreation.getText(), this.exhibitPlaceOfDiscovery.getText(), this.exhibitDimensions.getText(),
                        this.exhibitMaterials.getText(), this.exhibitType.getText(), this.exhibitStatus.getSelectionModel().getSelectedItem().toString(), this.exhibitLicence.getText(), this.exhibitLink.getText(),  this.exhibitCurrentPlace.getText()));
            }
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
            Stage stage = (Stage) this.exhibitName.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-use-window.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            MainWindowControl mainCollectionsWindow = fxmlLoader.getController();
            mainCollectionsWindow.setLoggedInUser(this.loggedInUser);
            Stage stage = (Stage) this.exhibitName.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    public String newExhibit(int selectedCollectionId, String name) throws SQLException {
        boolean doesExist = false;
        for(Exhibit exhibit : DatabaseControllers.getAllExhibits()) {
            if (exhibit.getName().equals(name)) {
                doesExist = true;
                return("Exhibit already exists");
            }
        }
        if(isValidInput(name) == false) {
            return ("Please fill name field");
        }
        if(doesExist == false)
        {
            try {
                DatabaseControllers.createExhibit(new Exhibit(name, selectedCollectionId));
                return("Exhibit created");
            } catch (Exception e) {
                System.out.println(e);
                return("Error creating exhibit" + e);
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