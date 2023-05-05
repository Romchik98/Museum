package com.museum.museum.controllers;

import com.dlsc.formsfx.model.structure.DateField;
import com.dlsc.formsfx.model.structure.IntegerField;
import com.museum.museum.Start;
import com.museum.museum.databaseUtilities.DatabaseControllers;
import com.museum.museum.ds.Exhibit;
import com.museum.museum.ds.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class CreateExhibitControl {

    @FXML
    public TextField exhibitName;
    @FXML
    public TextField exhibitDescription;
    @FXML
    public DatePicker exhibitDateOfCreation;
    @FXML
    public DatePicker exhibitDateOfDiscovery;
    @FXML
    public IntegerField exhibitQuantity;
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
    public TextField exhibitObject;
    @FXML
    public TextField exhibitLicence;

    private User loggedInUser;
    private int selectedCollectionId;

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public void setSelectedCollectionId(int collectionId) {
        this.selectedCollectionId = collectionId;
    }

    public void createExhibit(ActionEvent actionEvent) throws SQLException, IOException   {
        for(Exhibit exhibit : DatabaseControllers.getExhibits(selectedCollectionId)) {
            if (exhibit.getName().equals(this.exhibitName.getText())) {
                LoginControl.alertMessage("Exhibit already exists");
                break;
            } else if (this.exhibitName.getText().length() < 1) {
                LoginControl.alertMessage("You must enter Exhibit name");
                break;
            } else {
                DatabaseControllers.createExhibit(new Exhibit(this.exhibitName.getText(), selectedCollectionId, this.exhibitDescription.getText(), this.exhibitDateOfCreation.getValue(), this.exhibitDateOfDiscovery.getValue(),
                        this.exhibitQuantity.getValue(), this.exhibitCondition.getText(), this.exhibitPlaceOfCreation.getText(), this.exhibitPlaceOfDiscovery.getText(), this.exhibitDimensions.getText(),
                        this.exhibitMaterials.getText(), this.exhibitType.getText(), this.exhibitObject.getText(), this.exhibitLicence.getText()));
                this.goBack();
            }
        }
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
}