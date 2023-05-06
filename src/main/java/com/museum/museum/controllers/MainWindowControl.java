package com.museum.museum.controllers;

import com.museum.museum.Start;
import com.museum.museum.databaseUtilities.DatabaseControllers;
import com.museum.museum.ds.Collection;
import com.museum.museum.ds.Exhibit;
import com.museum.museum.ds.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MainWindowControl{

    //Collections tab
    @FXML
    public ListView<String> collectionsList;
    @FXML
    public ListView exhibitsList;
    @FXML
    public Button createCollectionButton;
    @FXML
    public Button editCollectionButton;
    @FXML
    public Button createExhibitButton;
    @FXML
    public Button editExhibitButton;
    @FXML
    public TabPane mainTab;
    @FXML
    public Tab collectionTab;

    //accessibility tab
    @FXML
    public ListView userList;
    @FXML
    public ListView userAccessList;

    //settingsTab
    @FXML
    public TextField loginName;
    @FXML
    public PasswordField password;
    @FXML
    public TextField name;
    @FXML
    public TextField surname;


    private Connection connection;
    private Statement statement;

    private ArrayList<Collection> collections;
    private ArrayList<Exhibit> exhibits;
    private ArrayList<User> users;

    private User selectedAdminUser;
    private User loggedInUser;
    private Collection selectedCollection;
    private Exhibit selectedExhibit;


    public void switchTab() {
        this.mainTab.getSelectionModel().select(collectionTab);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Collection//
////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setCollectionsList() throws SQLException{
        this.collectionsList.getItems().clear();
        for (Collection collection : this.getCollections()) {
            this.collectionsList.getItems().add(collection.getName());
        }
    }

    private ArrayList<Collection> getCollections() throws SQLException {
        ArrayList<Collection> collections = DatabaseControllers.getAllCollections();
        this.collections = collections;
        return collections;
    }

    public void selectCollection(MouseEvent mouseEvent) throws SQLException{
        if (this.collectionsList.getSelectionModel().getSelectedItem() != null) {
            String collectionName = this.collectionsList.getSelectionModel().getSelectedItem().toString();
            for (Collection collection : this.collections) {
                if(collection.getName().equals(collectionName))
                    selectedCollection = collection;
            }
            this.setExhibitsList(selectedCollection.getId());
        }
    }

    public void createCollection() throws SQLException, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("create-collection.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        CreateCollectionControl createCollectionControl = fxmlLoader.getController();
        createCollectionControl.setLoggedInUser(loggedInUser);
        Stage stage = (Stage) this.createCollectionButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void deleteCollection() throws SQLException, IOException {
        if(selectedCollection != null) {
            DatabaseControllers.deleteCollection(selectedCollection.getId());
            setCollectionsList();

            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window.fxml")); //sketchy
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            MainWindowControl mainCollectionsWindow = fxmlLoader.getController();
            mainCollectionsWindow.setLoggedInUser(this.loggedInUser);
            Stage stage = (Stage) this.collectionsList.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else
            LoginControl.alertMessage("Please select collection");
    }

    public void editCollection() throws SQLException, IOException {
        if(selectedCollection != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("edit-collection.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            EditCollectionControl editCollectionControl = fxmlLoader.getController();
            editCollectionControl.setLoggedInUser(loggedInUser);
            editCollectionControl.setSelectedCollection(selectedCollection);

            Stage stage = (Stage) this.editCollectionButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else
            LoginControl.alertMessage("Please select collection");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Exhibits//
////////////////////////////////////////////////////////////////////////////////////////////////////
    private ArrayList<Exhibit> getExhibits(int collectionIdLike) throws SQLException {
        ArrayList<Exhibit> exhibits = DatabaseControllers.getExhibits(collectionIdLike);
        this.exhibits = exhibits;
        return exhibits;
    }

    public void setExhibitsList(int collectionIdLike) throws SQLException{
        this.exhibitsList.getItems().clear();
        for (Exhibit exhibit : this.getExhibits(collectionIdLike)) {
            this.exhibitsList.getItems().add(exhibit.getName());
        }
    }

    public void selectExhibit(MouseEvent mouseEvent) throws SQLException{
        if (this.exhibitsList.getSelectionModel().getSelectedItem() != null) {
            String exhibitName = this.exhibitsList.getSelectionModel().getSelectedItem().toString();
            for (Exhibit exhibit : this.exhibits) {
                if(exhibit.getName().equals(exhibitName))
                    selectedExhibit = exhibit;
            }
        }
    }

    public void createExhibit() throws SQLException, IOException {
        if (selectedCollection != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("create-exhibit.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            CreateExhibitControl createExhibitControl = fxmlLoader.getController();
            createExhibitControl.setLoggedInUser(loggedInUser);
            createExhibitControl.setSelectedCollectionId(selectedCollection.getId());

            Stage stage = (Stage) this.createExhibitButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else
            LoginControl.alertMessage("Please select collection");
    }

    public void deleteExhibit() throws SQLException, IOException {
        if(selectedExhibit != null) {
            DatabaseControllers.deleteExhibit(selectedExhibit.getId());

            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window.fxml")); //sketchy
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            MainWindowControl mainCollectionsWindow = fxmlLoader.getController();
            mainCollectionsWindow.setLoggedInUser(this.loggedInUser);
            Stage stage = (Stage) this.collectionsList.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else
            LoginControl.alertMessage("Please select exhibit");
    }

    public void editExhibit() throws SQLException, IOException {
        if (selectedCollection != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("edit-exhibit.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            EditExhibitControl editExhibitControl = fxmlLoader.getController();
            editExhibitControl.setLoggedInUser(loggedInUser);
            editExhibitControl.setSelectedExhibitId(selectedExhibit.getId());

            Stage stage = (Stage) this.createExhibitButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else
            LoginControl.alertMessage("Please select exhibit");
    }


////////////////////////////////////////////////////////////////////////////////////////////////////
    //User//
////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setLoggedInUser(User user) throws SQLException {
        this.loggedInUser = user;
        this.setCollectionsList();
        //this.setAllUsersList();
    }

    public void updateUser(ActionEvent actionEvent) throws IOException {
        if(     loginName.getText() != "" &&
                password.getText() != "" &&
                name.getText() != "" &&
                surname.getText() != "") {

            DatabaseControllers.editUser(loginName.getText(), password.getText(), name.getText(), surname.getText(), loggedInUser.getId());
        }
        else {
            LoginControl.alertMessage("Please fill all fields");
        }
    }

    public void setAllUsersList() throws SQLException {
        this.userList.getItems().clear();
        for (User user : this.getUsers())
            this.userList.getItems().add(user.getLogin());
    }

    private ArrayList<User> getUsers() throws SQLException {
        ArrayList<User> users = DatabaseControllers.getAllUsers();
        this.users = users;
        return users;
    }

    public void selectAdminUser(MouseEvent mouseEvent) throws SQLException {
        /*if (this.userList.getSelectionModel().getSelectedItem() != null) {
            String userName = this.userList.getSelectionModel().getSelectedItem().toString();
            for (User user : this.users) {
                if(user.getName().equals(userName))
                    selectedAdminUser = user;
            }
        }*/
    }

    public void resetUser(ActionEvent actionEvent) {
    }

    public void deleteUser(ActionEvent actionEvent) {
    }
}

