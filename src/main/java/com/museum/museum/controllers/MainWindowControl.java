package com.museum.museum.controllers;

import com.museum.museum.Start;
import com.museum.museum.databaseUtilities.DatabaseControllers;
import com.museum.museum.ds.Collection;
import com.museum.museum.ds.Exhibit;
import com.museum.museum.ds.Museum;
import com.museum.museum.ds.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class MainWindowControl{

    //Collections tab
    @FXML
    public ListView<String> collectionsList;
    @FXML
    public ListView<String> exhibitsList;
    @FXML
    public ComboBox<String> typeFilter;
    @FXML
    public ListView<String> exhibitDataList;
    @FXML
    public ListView<String> museumsList;
    @FXML
    public ListView<String> forwardExhibitsList;
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
    public TabPane mainTab;
    @FXML
    public Tab collectionTab;

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

    private ArrayList<Collection> collections;
    private ArrayList<Exhibit> exhibits;
    private ArrayList<Exhibit> forwardExhibits;
    private ArrayList<Museum> museums;
    @FXML
    private ArrayList<Exhibit> exhibitData;

    private User loggedInUser;
    private Collection selectedCollection;
    private Exhibit selectedExhibit;
    private Museum selectedMuseum;

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
        Collections.sort(collections, Comparator.comparing(Collection ::getName));
        this.collections = collections;
        return collections;
    }

    public void selectCollection() throws SQLException{
        if (this.collectionsList.getSelectionModel().getSelectedItem() != null) {
            String collectionName = this.collectionsList.getSelectionModel().getSelectedItem();
            for (Collection collection : this.collections) {
                if(collection.getName().equals(collectionName))
                    selectedCollection = collection;
            }
            this.setExhibitsList(selectedCollection.getId());
        }
    }

    public void createCollection() throws IOException {
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
            editCollectionControl.loadCollectionData();

            Stage stage = /*new Stage()*/(Stage) this.editCollectionButton.getScene().getWindow();
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
        String type = getFilterType();
        ArrayList<Exhibit> exhibits = DatabaseControllers.getExhibits(collectionIdLike, type);
        Collections.sort(exhibits, Comparator.comparing(Exhibit ::getName));
        this.exhibits = exhibits;
        return exhibits;
    }

    public String getFilterType() {
        String type = typeFilter.getValue();
        System.out.println(type);
        return type;
    }
    public void loadChoiceBox() {
        ObservableList<String> list = FXCollections.observableArrayList("Visi", "Skaitmeniniai", "Fiziniai");
        typeFilter.setItems(list);
    }
    public void selectFilter() throws SQLException{
        selectCollection();
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

    public void selectExhibit() throws SQLException{
        if (this.exhibitsList.getSelectionModel().getSelectedItem() != null) {
            String exhibitName = this.exhibitsList.getSelectionModel().getSelectedItem().toString();
            for (Exhibit exhibit : this.exhibits) {
                if(exhibit.getName().equals(exhibitName))
                    selectedExhibit = exhibit;
            }
            this.setExhibitData(selectedExhibit.getId());
        }
    }

    public void createExhibit() throws IOException {
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

    public void deleteExhibit() throws SQLException {
        if(selectedExhibit != null) {
            DatabaseControllers.deleteExhibit(selectedExhibit.getId());
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
            editExhibitControl.setSelectedExhibit(selectedExhibit);
            editExhibitControl.loadExhibitData();

            ObservableList<String> list = FXCollections.observableArrayList("atrestauruotas","restauruojasi","planuojama restauracija","restauracijos nereikia");
            editExhibitControl.exhibitStatus.setItems(list);

            Stage stage = (Stage) this.createExhibitButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

            editExhibitControl.exhibitDescription.setDisable(true);
            editExhibitControl.exhibitDateOfCreation.setDisable(true);
            editExhibitControl.exhibitPlaceOfCreation.setDisable(true);
            editExhibitControl.exhibitCondition.setDisable(true);
            editExhibitControl.exhibitName.setDisable(true);
            editExhibitControl.exhibitDimensions.setDisable(true);
            editExhibitControl.exhibitLicence.setDisable(true);
            editExhibitControl.exhibitMaterials.setDisable(true);
            editExhibitControl.exhibitPlaceOfDiscovery.setDisable(true);
            editExhibitControl.exhibitQuantity.setDisable(true);
            editExhibitControl.exhibitDateOfDiscovery.setDisable(true);
            editExhibitControl.exhibitCurrentPlace.setDisable(true);
            editExhibitControl.exhibitLink.setDisable(true);
            editExhibitControl.exhibitStatus.setDisable(true);
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
        this.setMuseumsList();
        this.loadChoiceBox();
    }

    public void updateUser() throws Exception {
        if(!Objects.equals(loginName.getText(), "") &&
                !Objects.equals(password.getText(), "") &&
                !Objects.equals(name.getText(), "") &&
                !Objects.equals(surname.getText(), "")) {
            LoginControl loginControl = new LoginControl();
            DatabaseControllers.editUser(loginName.getText(), loginControl.encrypt(password.getText()), name.getText(), surname.getText(), loggedInUser.getId());
        }
        else {
            LoginControl.alertMessage("Įveskite visus laukelius");
        }
    }

    public void logOut() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Taip");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Ne");
        alert.setTitle("Atsijungimas");
        alert.setHeaderText("");
        alert.setContentText("Ar tikrai norite atsijungti?");

        if(alert.showAndWait().get() == ButtonType.OK) {
            FXMLLoader fxmlLoader = new FXMLLoader((Start.class.getResource("login-window.fxml")));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) loginName.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    public void loadUserdata() {
        loginName.setText(loggedInUser.getName());
        //password.setText(loggedInUser.getPassword());
        name.setText(loggedInUser.getName());
        surname.setText(loggedInUser.getUserSurname());
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
        museums.sort(Comparator.comparing(Museum::getName));
        this.museums = museums;
        return museums;
    }

    public void selectMuseum() throws SQLException{
        if (this.museumsList.getSelectionModel().getSelectedItem() != null) {
            String museumName = this.museumsList.getSelectionModel().getSelectedItem();
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

    public void selectForwardExhibit() throws SQLException{
        if (this.forwardExhibitsList.getSelectionModel().getSelectedItem() != null) {
            String exhibitName = this.forwardExhibitsList.getSelectionModel().getSelectedItem().toString();
            for (Exhibit exhibit : this.forwardExhibits) {
                if(exhibit.getName().equals(exhibitName))
                    selectedExhibit = exhibit;
            }
            this.setExhibitData(selectedExhibit.getId());
        }
    }

    public void forwardExhibit() throws SQLException, IOException {
        if (selectedExhibit != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("forward-exhibit.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            ForwardExhibitControl forwardExhibitControl = fxmlLoader.getController();
            forwardExhibitControl.setLoggedInUser(loggedInUser);
            forwardExhibitControl.setSelectedExhibit(selectedExhibit);
            forwardExhibitControl.comboMuseum.setItems(DatabaseControllers.loadComboBox());

            Stage stage = (Stage) this.forwardExhibitButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else
            LoginControl.alertMessage("Pasirinkite eksponatą");
    }

    public void createMuseum() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("create-museum.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        CreateMuseumControl createMuseumControl = fxmlLoader.getController();
        createMuseumControl.setLoggedInUser(loggedInUser);
        Stage stage = (Stage) this.createMuseumButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void deleteMuseum() throws SQLException {
        if(selectedMuseum != null) {
            DatabaseControllers.deleteMuseum(selectedMuseum.getId());
            setMuseumsList();
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
            editMuseumControl.loadMuseumData();

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

