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

public class MainUserWindowControl {

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
    //private ArrayList<Folder> folders;
    private ArrayList<Exhibit> exhibits;
    private ArrayList<User> users;
    private ArrayList<User> accessedUsers;

    private User loggedInUser;
    private Collection selectedCollection;
    //private Folder selectedFolder;
    private Exhibit selectedExhibit;

    public void switchTab() {
        this.mainTab.getSelectionModel().select(courseTab);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Collections//
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //FOLDERS//
////////////////////////////////////////////////////////////////////////////////////////////////////
    /*private ArrayList<Folder> getFolders(int courseIdlike) throws SQLException {
        ArrayList<Folder> folders = DatabaseControllers.getFolders(courseIdlike);
        this.folders = folders;
        return folders;
    }*/

    /*public void setFoldersTree() throws  SQLException {
        TreeItem<String> rootItem = new TreeItem<>(selectedCourse.getName());
        this.foldersTree.setRoot(rootItem);
        getFolders(selectedCourse.getId());

        for (Folder folder : getFolders(selectedCourse.getId())) {
            if(folder.getParentId() == 0)
            {
                TreeItem<String> item = new TreeItem<>(folder.getName());
                rootItem.getChildren().add(item);
                isThereChildren(folder.getId(), item);
            }

        }
    }*/
    /*private void isThereChildren(int folderId, TreeItem<String> item) {
        for (Folder folder : this.folders) {
            if(folder.getParentId() == folderId) {
                TreeItem<String> childItem = new TreeItem<>(folder.getName());
                item.getChildren().add(childItem);

                isThereChildren(folder.getId(), childItem);
            }
        }
    }

    public void selectFolder(MouseEvent mouseEvent) throws SQLException{
        if (this.foldersTree.getSelectionModel().getSelectedItem() != null && this.foldersTree.getSelectionModel().getSelectedItem().getValue().toString() != selectedCourse.getName().toString()) {
            String folderName = this.foldersTree.getSelectionModel().getSelectedItem().getValue().toString();
            for (Folder folder : this.folders) {
                if(folder.getName().equals(folderName))
                    selectedFolder = folder;
            }
            setFilesList(selectedFolder.getId());
        }
    }*/

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Files//
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

