package com.museum.museum.ds;

public class Museum {
    private int id;
    private String name;

    public Museum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Museum(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return super.toString();
    }
}
