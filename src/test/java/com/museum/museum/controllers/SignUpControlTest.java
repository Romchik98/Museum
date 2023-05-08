package com.museum.museum.controllers;

import com.museum.museum.databaseUtilities.DatabaseControllers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SignUpControlTest {

    @Test
    public void signUp_NormalConditions_Success() throws SQLException, IOException {
        DatabaseControllers.deleteLogin("xxx");
        SignUpControl signup = new SignUpControl();
        String message = signup.signUp("xxx", "x", "x", "x");

        assertEquals("User created", message);
    }

    @Test
    public void signUp_UserExist_Fail() throws SQLException, IOException {
        DatabaseControllers.deleteLogin("xxx");
        SignUpControl signup = new SignUpControl();
        signup.signUp("xxx", "x", "x", "x");
        String message = signup.signUp("xxx", "x", "x", "x");

        assertEquals("User already exists", message);
    }

    @Test
    public void signUp_EmptyLogin_Fail() throws SQLException, IOException {
        DatabaseControllers.deleteLogin("xxx");
        SignUpControl signup = new SignUpControl();
        String message = signup.signUp("", "x", "x", "x");

        assertEquals("Failed", message);
    }

    @Test
    public void signUp_EmptyPsw_Fail() throws SQLException, IOException {
        DatabaseControllers.deleteLogin("xxx");
        SignUpControl signup = new SignUpControl();
        String message = signup.signUp("x", "", "x", "x");

        assertEquals("Failed", message);
    }

    @Test
    public void signUp_EmptyName_Fail() throws SQLException, IOException {
        DatabaseControllers.deleteLogin("xxx");
        SignUpControl signup = new SignUpControl();
        String message = signup.signUp("x", "x", "", "x");

        assertEquals("Failed", message);
    }

    @Test
    public void signUp_EmptySur_Fail() throws SQLException, IOException {
        DatabaseControllers.deleteLogin("xxx");
        SignUpControl signup = new SignUpControl();
        String message = signup.signUp("x", "x", "x", "");

        assertEquals("Failed", message);
    }

}