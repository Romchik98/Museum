package com.museum.museum.controllers;

import com.museum.museum.databaseUtilities.DatabaseControllers;
import com.museum.museum.ds.User;
import org.junit.Assert;
//import org.junit.Test;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateExhibitControlTest {

    @Test
    public void CreateExhibit_NormalConditions_Success() throws SQLException, IOException {
        DatabaseControllers.deleteExhibits("xxx");
        User user = new User(999, "unit", "unit", "unit");
        CreateCollectionControl collection = new CreateCollectionControl();
        collection.newCollection("xxx", "xxx", user);

        CreateExhibitControl exhibit = new CreateExhibitControl();
        String message = exhibit.newExhibit(DatabaseControllers.getCollectionId("xxx"), "xxx");

        Assert.assertEquals("Exhibit created", message);
    }

   @Test
    public void CreateExhibit_ExhibitExist_Fail() throws SQLException, IOException {
        DatabaseControllers.deleteCollections("xxx");
        User user = new User(999, "unit", "unit", "unit");
        CreateCollectionControl collection = new CreateCollectionControl();
        collection.newCollection("xxx", "xxx", user);

        CreateExhibitControl exhibit = new CreateExhibitControl();
        exhibit.newExhibit(DatabaseControllers.getCollectionId("xxx"), "xxx");
        String message = exhibit.newExhibit(DatabaseControllers.getCollectionId("xxx"), "xxx");

        Assert.assertEquals("Exhibit already exists", message);
    }

     @Test
    public void CreateExhibit_NoName_Fail() throws SQLException, IOException {
        DatabaseControllers.deleteCollections("xxx");
        User user = new User(999, "unit", "unit", "unit");
        CreateCollectionControl collection = new CreateCollectionControl();
        collection.newCollection("xxx", "xxx", user);

        CreateExhibitControl exhibit = new CreateExhibitControl();
        String message = exhibit.newExhibit(DatabaseControllers.getCollectionId("xxx"), "");

        Assert.assertEquals("Please fill name field", message);
    }

}