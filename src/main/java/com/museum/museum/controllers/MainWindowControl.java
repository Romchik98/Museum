package com.museum.museum.controllers;

import com.museum.museum.Start;
import com.museum.museum.databaseUtilities.DatabaseControllers;
import com.museum.museum.ds.Collection;
import com.museum.museum.ds.Exhibit;
import com.museum.museum.ds.Museum;
import com.museum.museum.ds.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class MainWindowControl{

    //Collections tab
    @FXML
    public ListView<String> collectionsList;
    @FXML
    public ListView exhibitsList;
    @FXML
    public ListView exhibitDataList;
    @FXML
    public ListView<String> museumsList;
    @FXML
    public ListView forwardExhibitsList;
    @FXML
    public Button createCollectionButton;
    @FXML
    public Button editCollectionButton;
    @FXML
    public Button createExhibitButton;
    @FXML
    public Button editExhibitButton;
    @FXML
    public Button forwardExhibitButton;
    @FXML
    public Button createMuseumButton;
    @FXML
    public Button editMuseumButton;
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

    //images
    @FXML
    public ImageView exhibitImage;


    private Connection connection;
    private Statement statement;

    private ArrayList<Collection> collections;
    private ArrayList<Exhibit> exhibits;
    private ArrayList<Exhibit> forwardExhibits;
    private ArrayList<Museum> museums;
    @FXML
    private ArrayList<Exhibit> exhibitData;
    private ArrayList<User> users;

    private User selectedAdminUser;
    private User loggedInUser;
    private Collection selectedCollection;
    private Exhibit selectedExhibit;
    private Museum selectedMuseum;


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

            /*FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window.fxml")); //sketchy
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            MainWindowControl mainCollectionsWindow = fxmlLoader.getController();
            mainCollectionsWindow.setLoggedInUser(this.loggedInUser);
            Stage stage = (Stage) this.collectionsList.getScene().getWindow();
            stage.setScene(scene);
            stage.show();*/
        }
        else
            LoginControl.alertMessage("Pasirinkite kolekciją");
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
            LoginControl.alertMessage("Pasirinkite kolekciją");
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

    private ArrayList<Exhibit> getExhibitData(int exhibitIdLike) throws SQLException {
        ArrayList<Exhibit> exhibitData = DatabaseControllers.getExhibitData(exhibitIdLike);
        this.exhibitData = exhibitData;
        return exhibitData;
    }
    public void setExhibitData(int exhibitIdLike) throws SQLException{
        this.exhibitDataList.getItems().clear();
        for (Exhibit exhibit : this.getExhibitData(exhibitIdLike)) {
            this.exhibitDataList.getItems().add(exhibit.getName());
            this.exhibitDataList.getItems().add(exhibit.getDescription());
            this.exhibitDataList.getItems().add(exhibit.getDateOfCreation());
            this.exhibitDataList.getItems().add(exhibit.getDateOfDiscovery());
            this.exhibitDataList.getItems().add(exhibit.getType());
            this.exhibitDataList.getItems().add(exhibit.getStatus());
            this.exhibitDataList.getItems().add(exhibit.getCondition());
            this.exhibitDataList.getItems().add(exhibit.getQuantity());
            this.exhibitDataList.getItems().add(exhibit.getMaterials());
            this.exhibitDataList.getItems().add(exhibit.getDimensions());
            this.exhibitDataList.getItems().add(exhibit.getLicence());
            this.exhibitDataList.getItems().add(exhibit.getLink());
            this.exhibitDataList.getItems().add(exhibit.getCurrentPlace());
            displayImage(exhibit.getName());
        }
    }

    public void selectExhibit(MouseEvent mouseEvent) throws SQLException{
        if (this.exhibitsList.getSelectionModel().getSelectedItem() != null) {
            String exhibitName = this.exhibitsList.getSelectionModel().getSelectedItem().toString();
            for (Exhibit exhibit : this.exhibits) {
                if(exhibit.getName().equals(exhibitName))
                    selectedExhibit = exhibit;
            }
            this.setExhibitData(selectedExhibit.getId());
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

            ObservableList<String> list = FXCollections.observableArrayList("atrestauruotas","restauruojasi","planuojama restauracija","restauracijos nereikia");
            createExhibitControl.exhibitStatus.setItems(list);

            Stage stage = (Stage) this.createExhibitButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            createExhibitControl.exhibitDescription.setDisable(true);
            createExhibitControl.exhibitDateOfCreation.setDisable(true);
            createExhibitControl.exhibitPlaceOfCreation.setDisable(true);
            createExhibitControl.exhibitCondition.setDisable(true);
            createExhibitControl.exhibitName.setDisable(true);
            createExhibitControl.exhibitDimensions.setDisable(true);
            createExhibitControl.exhibitLicence.setDisable(true);
            createExhibitControl.exhibitMaterials.setDisable(true);
            createExhibitControl.exhibitPlaceOfDiscovery.setDisable(true);
            createExhibitControl.exhibitQuantity.setDisable(true);
            createExhibitControl.exhibitDateOfDiscovery.setDisable(true);
            createExhibitControl.exhibitCurrentPlace.setDisable(true);
            createExhibitControl.exhibitLink.setDisable(true);
            createExhibitControl.exhibitStatus.setDisable(true);
        }
        else
            LoginControl.alertMessage("Pasirinkite kolekciją");
    }

    public void deleteExhibit() throws SQLException, IOException {
        if(selectedExhibit != null) {
            DatabaseControllers.deleteExhibit(selectedExhibit.getId());

            /*FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window.fxml")); //sketchy
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            MainWindowControl mainCollectionsWindow = fxmlLoader.getController();
            mainCollectionsWindow.setLoggedInUser(this.loggedInUser);
            Stage stage = (Stage) this.collectionsList.getScene().getWindow();
            stage.setScene(scene);
            stage.show();*/
        }
        else
            LoginControl.alertMessage("Pasirinkite eksponatą");
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
            LoginControl.alertMessage("Pasirinkite eksponatą");
    }


////////////////////////////////////////////////////////////////////////////////////////////////////
    //User//
////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setLoggedInUser(User user) throws SQLException {
        this.loggedInUser = user;
        this.setCollectionsList();
        //this.setAllUsersList();
        this.setMuseumsList();
    }

    public void updateUser(ActionEvent actionEvent) throws Exception {
        if(     loginName.getText() != "" &&
                password.getText() != "" &&
                name.getText() != "" &&
                surname.getText() != "") {
            LoginControl loginControl = new LoginControl();
            DatabaseControllers.editUser(loginName.getText(), loginControl.encrypt(password.getText()), name.getText(), surname.getText(), loggedInUser.getId());
        }
        else {
            LoginControl.alertMessage("Įveskite visus laukelius");
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Museums//
////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setMuseumsList() throws SQLException{
        this.museumsList.getItems().clear();
        for (Museum museum : this.getMuseums()) {
            this.museumsList.getItems().add(museum.getName());
        }
    }

    private ArrayList<Museum> getMuseums() throws SQLException {
        ArrayList<Museum> museums = DatabaseControllers.getAllMuseums();
        this.museums = museums;
        return museums;
    }

    public void selectMuseum(MouseEvent mouseEvent) throws SQLException{
        if (this.museumsList.getSelectionModel().getSelectedItem() != null) {
            String museumName = this.museumsList.getSelectionModel().getSelectedItem().toString();
            for (Museum museum : this.museums) {
                if(museum.getName().equals(museumName))
                    selectedMuseum = museum;
            }
            this.setForwardExhibitsList(selectedMuseum.getId());
        }
    }

    public void setForwardExhibitsList(int museumIdLike) throws SQLException{
        this.forwardExhibitsList.getItems().clear();
        for (Exhibit exhibit : this.getForwardExhibits(museumIdLike)) {
            this.forwardExhibitsList.getItems().add(exhibit.getName());
        }
    }

    private ArrayList<Exhibit> getForwardExhibits(int museumIdLike) throws SQLException {
        ArrayList<Exhibit> forwardExhibits = DatabaseControllers.getForwardExhibits(museumIdLike);
        this.forwardExhibits = forwardExhibits;
        return forwardExhibits;
    }

    public void selectForwardExhibit(MouseEvent mouseEvent) throws SQLException{
        if (this.forwardExhibitsList.getSelectionModel().getSelectedItem() != null) {
            String exhibitName = this.forwardExhibitsList.getSelectionModel().getSelectedItem().toString();
            for (Exhibit exhibit : this.forwardExhibits) {
                if(exhibit.getName().equals(exhibitName))
                    selectedExhibit = exhibit;
            }
            this.setExhibitData(selectedExhibit.getId());
        }
    }

    public void forwardExhibit() throws SQLException, IOException, ClassNotFoundException {
        if (selectedExhibit != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("forward-exhibit.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            ForwardExhibitControl forwardExhibitControl = fxmlLoader.getController();
            forwardExhibitControl.setLoggedInUser(loggedInUser);
            forwardExhibitControl.setSelectedExhibitId(selectedExhibit.getId());
            forwardExhibitControl.loadComboBox();

            Stage stage = (Stage) this.forwardExhibitButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else
            LoginControl.alertMessage("Pasirinkite eksponatą");
    }

    public void createMuseum() throws SQLException, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("create-museum.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        CreateMuseumControl createMuseumControl = fxmlLoader.getController();
        createMuseumControl.setLoggedInUser(loggedInUser);
        Stage stage = (Stage) this.createMuseumButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void deleteMuseum() throws SQLException, IOException {
        if(selectedMuseum != null) {
            DatabaseControllers.deleteMuseum(selectedMuseum.getId());
            setMuseumsList();

            /*FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window.fxml")); //sketchy
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            MainWindowControl mainCollectionsWindow = fxmlLoader.getController();
            mainCollectionsWindow.setLoggedInUser(this.loggedInUser);
            Stage stage = (Stage) this.museumsList.getScene().getWindow();
            stage.setScene(scene);
            stage.show();*/
        }
        else
            LoginControl.alertMessage("Pasirinkite muziejų");
    }

    public void editMuseum() throws SQLException, IOException {
        if(selectedMuseum != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("edit-museum.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            EditMuseumControl editMuseumControl = fxmlLoader.getController();
            editMuseumControl.setLoggedInUser(loggedInUser);
            editMuseumControl.setSelectedMuseum(selectedMuseum);

            Stage stage = (Stage) this.createMuseumButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else
            LoginControl.alertMessage("Pasirinkite kolekciją");
    }

    //////////////////////////////////////////////////////////
    //IMAGE
    //////////////////////////////////////////////////////////
    public void displayImage(String exhibitName) {
        Image myImage = new Image("E:\\uni\\Museum\\src\\main\\resources\\images\\" + exhibitName + ".jpg");
        exhibitImage.setImage(myImage);
    }

}

