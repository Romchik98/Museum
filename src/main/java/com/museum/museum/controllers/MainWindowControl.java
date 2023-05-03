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

public class MainWindowControl{

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
    private User selectedAllUser;
    private User selectedAccessUser;



    public void setLoggedInUser(User user) throws SQLException {
        this.loggedInUser = user;
        this.setCoursesList(loggedInUser.getId());
        this.setAllUsersList();
    }

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
            this.setAccesedUsersList();
        }
    }

    public void createCourse() throws SQLException, IOException {
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
            setCoursesList(loggedInUser.getId());

            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window.fxml")); //sketchy
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            MainWindowControl mainCoursesWindow = fxmlLoader.getController();
            mainCoursesWindow.setLoggedInUser(this.loggedInUser);
            Stage stage = (Stage) this.coursesList.getScene().getWindow();
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
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //FOLDERS//
////////////////////////////////////////////////////////////////////////////////////////////////////
    private ArrayList<Folder> getFolders(int courseIdlike) throws SQLException {
        ArrayList<Folder> folders = DbQuerys.getFolders(courseIdlike);
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

    public void createFolder() throws SQLException, IOException{
        if(selectedCourse != null) {

            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("create-folder.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            CreateFolderControl createFolderControl = fxmlLoader.getController();
            createFolderControl.setLoggedInUser(loggedInUser);
            createFolderControl.setSelectedCourse(selectedCourse);
            if (selectedFolder != null)
                createFolderControl.setSelectedFolderId(selectedFolder.getId());
            else
                createFolderControl.setSelectedFolderId(0);

            Stage stage = (Stage) this.createFolderButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else
            LoginControl.alertMessage("Please select course");

    }

    public void editFolder() throws SQLException, IOException {
        if(selectedFolder != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("edit-folder.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            EditFolderControl editFolderControl = fxmlLoader.getController();
            editFolderControl.setLoggedInUser(loggedInUser);
            editFolderControl.setSelectedFolderId(selectedFolder.getId());

            Stage stage = (Stage) this.editFolderButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else
            LoginControl.alertMessage("Please select folder");
    }

    public void deleteFolder() throws SQLException, IOException {
        if(selectedFolder != null) {
            isThereChildrenToRemove(this.selectedFolder.getId());
            DbQuerys.deleteFolder(selectedFolder.getId());
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            MainWindowControl mainCoursesWindow = fxmlLoader.getController();
            mainCoursesWindow.setLoggedInUser(this.loggedInUser);
            Stage stage = (Stage) this.coursesList.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else
            LoginControl.alertMessage("Please select folder");
    }
    private void isThereChildrenToRemove(int folderId) throws SQLException {
        for (Folder folder : this.folders) {
            if(folder.getParentId() == folderId) {
                DbQuerys.deleteFolder(folder.getId());
                isThereChildrenToRemove(folder.getId());
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Files//
////////////////////////////////////////////////////////////////////////////////////////////////////
    private ArrayList<File> getFiles(int folderIdLike) throws SQLException {
        ArrayList<File> files = DbQuerys.getFiles(folderIdLike);
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
    }

    public void createFile() throws SQLException, IOException {
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
            Stage stage = (Stage) this.coursesList.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else
            LoginControl.alertMessage("Please select file");
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    //Course accessibilities//
////////////////////////////////////////////////////////////////////////////////////////////////////

    public void addUserAccess(ActionEvent actionEvent) throws SQLException{//try reik!!!!
        if(selectedCourse != null && selectedAllUser != null) {
            DbQuerys.addUserAccess(selectedCourse.getId(), selectedAllUser.getId());
            setAccesedUsersList();
        }
        else
            LoginControl.alertMessage("Please select course and user");
    }


    public void removeUserAccess(ActionEvent actionEvent) throws SQLException {//try reik!!!!
        if(selectedCourse != null && selectedAccessUser != null) {
            DbQuerys.removeUserAccess(selectedCourse.getId(), selectedAccessUser.getId() );
            setAccesedUsersList();
        }
        else
            LoginControl.alertMessage("Please select course and user");
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    //User//
////////////////////////////////////////////////////////////////////////////////////////////////////

    public void selectAllUser(MouseEvent mouseEvent) throws SQLException{
        if (this.userList.getSelectionModel().getSelectedItem() != null) {//
            String login = this.userList.getSelectionModel().getSelectedItem().toString();
            for (User user : this.users) {
                if(user.getLogin().equals(login))
                    selectedAllUser = user;
            }
        }
    }

    public void selectAccessUser(MouseEvent mouseEvent) throws SQLException{
        if (this.userAccessList.getSelectionModel().getSelectedItem() != null) {
            String login = this.userAccessList.getSelectionModel().getSelectedItem().toString();

            for (User user : this.getUsers()) {
                for (User accessedUser : this.getAccessedUsers())
                    if (accessedUser.getId() == user.getId())
                        if(user.getLogin().equals(login))
                            selectedAccessUser = user;
            }
        }
    }*/


    public void setAllUsersList() throws SQLException {
        this.userList.getItems().clear();
        for (User user : this.getUsers())
            this.userList.getItems().add(user.getLogin());
    }

    private ArrayList<User> getUsers() throws SQLException {
        ArrayList<User> users = DatabaseControllers.getUsers();
        this.users = users;
        return users;
    }

    /*public void setAccesedUsersList() throws SQLException {
        this.userAccessList.getItems().clear();
        for (User user : this.getUsers()) {
            for (User accessedUser : this.getAccessedUsers())
                if (accessedUser.getId() == user.getId())
                    this.userAccessList.getItems().add(user.getLogin());
        }
    }

    private ArrayList<User> getAccessedUsers() throws SQLException {
        ArrayList<User> users = DbQuerys.getAccessedUsers(selectedCourse.getId());
        this.accessedUsers = users;
        return users;
    }

    public void updateUser(ActionEvent actionEvent) {
        if(     loginName.getText() != "" &&
                password.getText() != "" &&
                name.getText() != "" &&
                surname.getText() != "") {

            DbQuerys.editUser(loginName.getText(), password.getText(), name.getText(), surname.getText(), loggedInUser.getId());
        }
        else
            LoginControl.alertMessage("Please fill all fields");
    }*/
}

