package com.museum.museum.ds;

public class Exhibit {
    private int id;
    private int collectionId;
    private String name;
    private String description;
    private String dateOfCreation;
    private String dateOfDiscovery;
    private String quantity;
    private String condition;
    private String placeOfCreation;
    private String placeOfDiscovery;
    private String currentPlace;
    private String dimensions;
    private String materials;
    private String type;
    private String status;
    private String licence;
    private String link;
    private int museumId;

    /*public Exhibit(String exhibitNameText, int selectedCollectionId, String exhibitDescriptionText, String exhibitDateOfCreationValue, String value, int collectionId, String exhibitConditionText, String exhibitPlaceOfCreationText, String exhibitPlaceOfDiscoveryText, String exhibitDimensionsText, String exhibitMaterialsText, String exhibitTypeText, String text, String name) {
        this.collectionId = collectionId;
        this.name = name;
    }*/

    public Exhibit(String name, int selectedCollectionId) {
        this.collectionId = collectionId;
        this.name = name;
    }

    /*public Exhibit(String name, int selectedMuseumId) {
        this.museumId = museumId;
        this.name = name;
    }*/

    public Exhibit(String currentPlace) {
        this.currentPlace = currentPlace;
    }

    public Exhibit(String name, int collectionId, String description, String dateOfCreation, String dateOfDiscovery, String quantity, String condition, String placeOfCreation, String placeOfDiscovery, String dimensions, String materials, String type, String status, String licence, String link, String currentPlace) {
        this.collectionId = collectionId;
        this.name = name;
        this.description = description;
        this.dateOfCreation = dateOfCreation;
        this.dateOfDiscovery = dateOfDiscovery;
        this.quantity = quantity;
        this.condition = condition;
        this.placeOfCreation = placeOfCreation;
        this.placeOfDiscovery = placeOfDiscovery;
        this.dimensions = dimensions;
        this.materials = materials;
        this.type = type;
        this.status = status;
        this.licence = licence;
        this.link = link;
        this.currentPlace = currentPlace;
    }

    public Exhibit(String name, int collectionId, String description, String dateOfCreation, String dateOfDiscovery, String quantity, String condition, String placeOfCreation, String placeOfDiscovery, String dimensions, String materials, String status, String licence, String link, String currentPlace) {
        this.collectionId = collectionId;
        this.name = name;
        this.description = description;
        this.dateOfCreation = dateOfCreation;
        this.dateOfDiscovery = dateOfDiscovery;
        this.quantity = quantity;
        this.condition = condition;
        this.placeOfCreation = placeOfCreation;
        this.placeOfDiscovery = placeOfDiscovery;
        this.dimensions = dimensions;
        this.materials = materials;
        this.status = status;
        this.licence = licence;
        this.link = link;
        this.currentPlace = currentPlace;
    }

    public Exhibit(int id, String name, String description, String dateOfCreation, String dateOfDiscovery, String quantity, String condition, int collectionId, String placeOfCreation, String placeOfDiscovery, String dimensions, String materials, String type, String status, String licence, String link, String currentPlace) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateOfCreation = dateOfCreation;
        this.dateOfDiscovery = dateOfDiscovery;
        this.quantity = quantity;
        this.condition = condition;
        this.collectionId = collectionId;
        this.placeOfCreation = placeOfCreation;
        this.placeOfDiscovery = placeOfDiscovery;
        this.dimensions = dimensions;
        this.materials = materials;
        this.type = type;
        this.status = status;
        this.licence = licence;
        this.link = link;
        this.currentPlace = currentPlace;
    }

    public Exhibit(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getDateOfDiscovery() {
        return dateOfDiscovery;
    }

    public void setDateOfDiscovery(String dateOfDiscovery) {
        this.dateOfDiscovery = dateOfDiscovery;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public String getPlaceOfCreation() {
        return placeOfCreation;
    }

    public void setPlaceOfCreation(String placeOfCreation) {
        this.placeOfCreation = placeOfCreation;
    }

    public String getPlaceOfDiscovery() {
        return placeOfDiscovery;
    }

    public void setPlaceOfDiscovery(String placeOfDiscovery) {
        this.placeOfDiscovery = placeOfDiscovery;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public Exhibit(int id, String name, String description, String dateOfCreation, String dateOfDiscovery,
                   String quantity, String condition, int collectionId, String placeOfCreation, String placeOfDiscovery,
                   String dimensions, String materials, String type, String status, String licence) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateOfCreation = dateOfCreation;
        this.dateOfDiscovery = dateOfDiscovery;
        this.quantity = quantity;
        this.condition = condition;
        this.collectionId = collectionId;
        this.placeOfCreation = placeOfCreation;
        this.placeOfDiscovery = placeOfDiscovery;
        this.dimensions = dimensions;
        this.materials = materials;
        this.type = type;
        this.status = status;
        this.licence = licence;
    }

    @Override
    public String toString() {
        return "Exhibit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dateOfCreation=" + dateOfCreation +
                ", dateOfDiscovery=" + dateOfDiscovery +
                ", quantity=" + quantity +
                ", condition='" + condition + '\'' +
                ", collectionId=" + collectionId +
                ", placeOfCreation='" + placeOfCreation + '\'' +
                ", placeOfDiscovery='" + placeOfDiscovery + '\'' +
                ", dimensions='" + dimensions + '\'' +
                ", materials='" + materials + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", licence='" + licence + '\'' +
                '}';
    }

    public String getCurrentPlace() {
        return currentPlace;
    }

    public void setCurrentPlace(String currentPlace) {
        this.currentPlace = currentPlace;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getMuseumId() {
        return museumId;
    }

    public void setMuseumId(int museumId) {
        this.museumId = museumId;
    }
}