package com.example.test10000;


import java.time.LocalDate;

public class Admin extends Person {
    private String role;
    private int workinghours;


    public Admin(String username, String pass, LocalDate dateOfBirth, String role, int workinghours) {
        super(pass, dateOfBirth, username);
        this.role = role;
        this.workinghours = workinghours;
    }


    public String getRole() {
        return role;
    }


    public void setRole(String role) {
        this.role = role;
    }


    public int getWorkinghours() {
        return workinghours;
    }

    public String getpassword() {
        return pass;
    }


    public void setWorkinghours(int workinghours) {
        this.workinghours = workinghours;
    }

    @Override
    public String toString() {
        return super.toString() + ", Role: " + role + ", Working Hours: " + workinghours;
    }




}