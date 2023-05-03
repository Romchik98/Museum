package com.museum.museum.controllers;

import com.museum.museum.databaseUtilities.DatabaseControllers;
import com.museum.museum.ds.Collection;
import com.museum.museum.ds.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MainUserWindowControl {

    //coursesTab
    @FXML
    public ListView coursesList;
    @FXML
    public ListView filesList;
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
    /*private ArrayList<Folder> folders;
    private ArrayList<File> files;*/
    private ArrayList<User> users;
    private ArrayList<User> accessedUsers;

    private User loggedInUser;
    /*private Course selectedCourse;
    private Folder selectedFolder;
    private File selectedFile;*/

    public void switchTab() {
        this.mainTab.getSelectionModel().select(courseTab);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Courses//
////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setCoursesList(int userIdLike) throws SQLException{
        this.coursesList.getItems().clear();
        for (Collection collection : this.getCollections(userIdLike)) {
            this.coursesList.getItems().add(collection.getName());
        }
    }

    private ArrayList<Collection> getCollections(int userIdLike) throws SQLException {
        ArrayList<Collection> collections = DatabaseControllers.getCollections(userIdLike);
        this.collections = collections;
        return collections;
    }

    /*public void selectCourse(MouseEvent mouseEvent) throws SQLException{
        if (this.coursesList.getSelectionModel().getSelectedItem() != null) {
            String courseName = this.coursesList.getSelectionModel().getSelectedItem().toString();
            for (Course course : this.courses) {
                if(course.getName().equals(courseName))
                    selectedCourse = course;
            }
            this.setFoldersTree();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //FOLDERS//
////////////////////////////////////////////////////////////////////////////////////////////////////
    private ArrayList<Folder> getFolders(int courseIdlike) throws SQLException {
        ArrayList<Folder> folders = DatabaseControllers.getFolders(courseIdlike);
        this.folders = folders;
        return folders;
    }

    public void setFoldersTree() throws  SQLException {
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
    }
    private void isThereChildren(int folderId, TreeItem<String> item) {
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
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Files//
////////////////////////////////////////////////////////////////////////////////////////////////////
    private ArrayList<File> getFiles(int folderIdLike) throws SQLException {
        ArrayList<File> files = DatabaseControllers.getFiles(folderIdLike);
        this.files = files;
        return files;
    }

    public void setFilesList(int folderIdLike) throws SQLException{
        this.filesList.getItems().clear();
        for (File file : this.getFiles(folderIdLike)) {
            this.filesList.getItems().add(file.getName());
        }
    }

    public void selectFile(MouseEvent mouseEvent) throws SQLException{
        if (this.filesList.getSelectionModel().getSelectedItem() != null) {
            String fileName = this.filesList.getSelectionModel().getSelectedItem().toString();
            for (File file : this.files) {
                if(file.getName().equals(fileName))
                    selectedFile = file;
            }
        }
    }*/

////////////////////////////////////////////////////////////////////////////////////////////////////
    //User//
////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setLoggedInUser(User user) throws SQLException {
        this.loggedInUser = user;
        this.setCoursesList(loggedInUser.getId());
    }

    /*public void updateUser(ActionEvent actionEvent) {
        if(     loginName.getText() != "" &&
                password.getText() != "" &&
                name.getText() != "" &&
                surname.getText() != "") {

            DatabaseControllers.editUser(loginName.getText(), password.getText(), name.getText(), surname.getText(), loggedInUser.getId());
        }
        else
            LoginControl.alertMessage("Please fill all fields");
    }*/
}

