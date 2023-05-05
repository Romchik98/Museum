package com.museum.museum.controllers;

import com.museum.museum.databaseUtilities.DatabaseControllers;
import com.museum.museum.ds.Collection;
import com.museum.museum.ds.Exhibit;
import com.museum.museum.ds.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MainWindowControl{

    //coursesTab
    @FXML
    public ListView<String> collectionsList;
    @FXML
    public ListView exhibitsList;
    @FXML
    public TreeView<String> foldersTree;
    @FXML
    public Button createCourseButton;
    @FXML
    public Button editCourseButton;
    @FXML
    public Button createFolderButton;
    @FXML
    public Button editFolderButton;
    @FXML
    public Button createFileButton;
    @FXML
    public Button editFileButton;
    @FXML
    public TabPane mainTab;
    @FXML
    public Tab courseTab;

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
    private ArrayList<User> accessedUsers;

    private User loggedInUser;
    private Collection selectedCollection;
    private Exhibit selectedExhibit;



    /*public void setLoggedInUser(User user) throws SQLException {
        this.loggedInUser = user;
        this.setCollectionsList(loggedInUser.getId());
        this.setAllUsersList();
    }*/

    public void switchTab() {
        this.mainTab.getSelectionModel().select(courseTab);
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
            for (Collection course : this.collections) {
                if(course.getName().equals(collectionName))
                    selectedCollection = course;
            }
            this.setExhibitsList(selectedCollection.getId());
        }
    }

    /*public void createCourse() throws SQLException, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("create-course.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        CreateCourseControl createCourseControl = fxmlLoader.getController();
        createCourseControl.setLoggedInUser(loggedInUser);
        Stage stage = (Stage) this.createCourseButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void deleteCourse() throws SQLException, IOException {
        if(selectedCourse != null) {
            DbQuerys.deleteCourse(selectedCourse.getId());
            setCollectionsList(loggedInUser.getId());

            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window.fxml")); //sketchy
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            MainWindowControl mainCoursesWindow = fxmlLoader.getController();
            mainCoursesWindow.setLoggedInUser(this.loggedInUser);
            Stage stage = (Stage) this.collectionsList.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else
            LoginControl.alertMessage("Please select course");
    }

    public void editCourse() throws SQLException, IOException {
        if(selectedCourse != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("edit-course.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            EditCourseControl editCourseControl = fxmlLoader.getController();
            editCourseControl.setLoggedInUser(loggedInUser);
            editCourseControl.setSelectedCourse(selectedCourse);

            Stage stage = (Stage) this.editCourseButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else
            LoginControl.alertMessage("Please select course");
    }*/


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

    /*public void createFile() throws SQLException, IOException {
        if (selectedFolder != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("create-file.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            CreateFileControl createFileControl = fxmlLoader.getController();
            createFileControl.setLoggedInUser(loggedInUser);
            createFileControl.setSelectedFolderId(selectedFolder.getId());

            Stage stage = (Stage) this.createFileButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else
            LoginControl.alertMessage("Please select folder");
    }

    public void editFile() throws SQLException, IOException {
        if (selectedFolder != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("edit-file.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            EditFileControl editFileControl = fxmlLoader.getController();
            editFileControl.setLoggedInUser(loggedInUser);
            editFileControl.setSelectedFileId(selectedFile.getId());

            Stage stage = (Stage) this.createFileButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else
            LoginControl.alertMessage("Please select file");
    }

    public void deleteFile() throws SQLException, IOException {
        if(selectedFile != null) {
            DbQuerys.deleteFile(selectedFile.getId());

            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window.fxml")); //sketchy
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            MainWindowControl mainCoursesWindow = fxmlLoader.getController();
            mainCoursesWindow.setLoggedInUser(this.loggedInUser);
            Stage stage = (Stage) this.collectionsList.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else
            LoginControl.alertMessage("Please select file");
    }*/

////////////////////////////////////////////////////////////////////////////////////////////////////
    //User//
////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setLoggedInUser(User user) throws SQLException {
        this.loggedInUser = user;
        this.setCollectionsList();
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
}

