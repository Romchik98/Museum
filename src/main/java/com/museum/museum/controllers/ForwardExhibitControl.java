package com.museum.museum.controllers;

import com.museum.museum.Start;
import com.museum.museum.databaseUtilities.DatabaseControllers;
import com.museum.museum.ds.Exhibit;
import com.museum.museum.ds.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class ForwardExhibitControl {

    @FXML
    public ComboBox comboMuseum;
    private User loggedInUser;
    private Exhibit selectedExhibit;

    public void setLoggedInUser(User user) throws SQLException {
        this.loggedInUser = user;
    }

    public void setSelectedExhibit(Exhibit exhibit) {
        this.selectedExhibit = exhibit;
    }

    public void forwardExhibit() throws SQLException, IOException {
        DatabaseControllers.forwardExhibit(new Exhibit(this.comboMuseum.getSelectionModel().getSelectedItem().toString()), selectedExhibit.getId());
        this.goBack();
    }

    public void goBack() throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        MainWindowControl mainCollectionsWindow = fxmlLoader.getController();
        mainCollectionsWindow.setLoggedInUser(this.loggedInUser);
        Stage stage = (Stage) this.comboMuseum.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
