package com.museum.museum.ds;

public class User {
    private int id;
    private String login;
    private String password;
    private String userType;
    private String userName;
    private String userSurname;

    public User(int id, String login, String password, String userType, String userName, String userSurname) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.userType = userType;
        this.userName = userName;
        this.userSurname = userSurname;
    }

    public User(String login, String password, String userName, String userSurname) {
        this.login = login;
        this.password = password;
        this.userName = userName;
        this.userSurname = userSurname;
    }

    public User(int id, String userName, String userSurname, String userType) {
        this.id = id;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userType = userType;
    }


    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() { return userType; }

    public void setUserType(String userType) { this.userType = userType; }

    public String getName() { return userName; }

    public void setName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", userType='" + userType + '\'' +
                ", userName='" + userName + '\'' +
                ", userSurname='" + userSurname + '\'' +
                '}';
    }
}