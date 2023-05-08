package com.museum.museum.controllers;

import com.museum.museum.databaseUtilities.DatabaseControllers;
import com.museum.museum.ds.User;
//import org.junit.Test;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class CreateCollectionControlTest {
    @Test
    public void CreateCollection_NormalConditions_Success() throws SQLException, IOException {
        DatabaseControllers.deleteCollections("xxx");
        User user = new User(999, "unit", "unit", "unit");
        CreateCollectionControl collection = new CreateCollectionControl();
        String message = collection.newCollection("xxx", "xxx", user);

        assertEquals("Collection created", message);
    }

    @Test
    public void CreateCollection_NoName_Fail() throws SQLException, IOException {
        DatabaseControllers.deleteCollections("xxx");
        User user = new User(1, "unit", "unit", "unit");
        CreateCollectionControl collection = new CreateCollectionControl();
        String message = collection.newCollection("", "xxx", user);

        assertEquals("Please fill name field", message);
    }

    @Test
    public void CreateCollection_NoDescr_Fail() throws SQLException, IOException {
        DatabaseControllers.deleteCollections("xxx");
        User user = new User(1, "unit", "unit", "unit");
        CreateCollectionControl collection = new CreateCollectionControl();
        String message = collection.newCollection("xxx", "", user);

        assertEquals("Please fill description field", message);
    }

    @Test
    public void CreateCollection_CollectionExist_Fail() throws SQLException, IOException {
        DatabaseControllers.deleteCollections("xxx");
        User user = new User(1, "unit", "unit", "unit");
        CreateCollectionControl collection = new CreateCollectionControl();
        collection.newCollection("xxx", "xxx", user);
        String message = collection.newCollection("xxx", "xxx", user);

        assertEquals("Course already exists", message);
    }
}