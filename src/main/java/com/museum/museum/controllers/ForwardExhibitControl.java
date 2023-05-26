package com.museum.museum.controllers;

import com.dlsc.formsfx.model.structure.DateField;
import com.dlsc.formsfx.model.structure.IntegerField;
import com.museum.museum.Start;
import com.museum.museum.databaseUtilities.DatabaseConnection;
import com.museum.museum.databaseUtilities.DatabaseControllers;
import com.museum.museum.ds.Collection;
import com.museum.museum.ds.Exhibit;
import com.museum.museum.ds.User;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForwardExhibitControl {

    @FXML
    public ComboBox comboMuseum;
    private User loggedInUser;
    private int selectedExhibitId;
    private int selectedMuseumId;
    private Exhibit selectedExhibit;

    public void setLoggedInUser(User user) throws SQLException {
        this.loggedInUser = user;
    }

    public void setSelectedExhibitId(int exhibitId) throws SQLException {
        this.selectedExhibitId = exhibitId;
    }

    public void setSelectedExhibit(Exhibit exhibit) throws SQLException {
        this.selectedExhibit = exhibit;
    }

    public void forwardExhibit(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        DatabaseControllers.forwardExhibit(new Exhibit(this.comboMuseum.getSelectionModel().getSelectedItem().toString()), selectedExhibit.getId());
        System.out.println(this.comboMuseum.getSelectionModel().getSelectedItem().toString());
        this.goBack();
    }

    public void loadComboBox() throws SQLException, IOException, ClassNotFoundException {
        //DatabaseControllers.forwardExhibit(new Exhibit(this.comboMuseum.getAccessibleText()));
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String DB_URL = "jdbc:mysql://localhost/museum";
            String USER = "root";
            String PASS = "admin";
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException | ClassNotFoundException t) {
            t.printStackTrace();
        }

        ResultSet rs = connection.createStatement().executeQuery("select * from museum");
        ObservableList data = FXCollections.observableArrayList();
        while (rs.next()) {
            data.add(new String(rs.getString(2)));
        }
        comboMuseum.setItems(data);
    }

    public void goBack() throws IOException, SQLException {
        if(loggedInUser.getUserType().equals("Admin")) {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            MainWindowControl mainCollectionsWindow = fxmlLoader.getController();
            mainCollectionsWindow.setLoggedInUser(this.loggedInUser);
            Stage stage = (Stage) this.comboMuseum.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-use-window.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            MainWindowControl mainCollectionsWindow = fxmlLoader.getController();
            mainCollectionsWindow.setLoggedInUser(this.loggedInUser);
            Stage stage = (Stage) this.comboMuseum.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }


    public void selectMuseumFromCombobox(ActionEvent event) {
    }
}
