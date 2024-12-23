package com.example.test10000;

import java.util.ArrayList;

public abstract class Category {
    private String type;
    private static ArrayList<Category> categories = new ArrayList<>();


    public Category() {
        categories.add(this); // Add  created category to the list
    }

    public Category(String type) {
        this.type = type;
        categories.add(this);
    }


    @Override
    public String toString() {
        return "Name: " + type;
    }


    public void setType(String type) {
        this.type = type;
    }

    public String get_type() {
        return type;
    }
}
