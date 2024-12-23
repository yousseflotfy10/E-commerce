package com.example.test10000;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public abstract class Person {
    protected LocalDate dateOfBirth;
    protected String pass;
    protected String username;
    Scanner scanner = new Scanner(System.in);
    //constructor
    public Person(String pass, LocalDate dateOfBirth, String username) {
        this.pass = pass;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
    }

    // Method for password validation and confirmation
    public void setPassSign() {
        String firstPass;
        String reenter;

        while (true) {
            try {

                System.out.print("Enter your password: ");
                firstPass = scanner.nextLine();

                validatePassword(firstPass);
                // Confirm password
                while (true) {
                    System.out.print("Please confirm password: ");
                    reenter = scanner.nextLine();

                    if (firstPass.equals(reenter)) {

                        pass = firstPass;
                        System.out.println("Password set successfully.");
                        return;
                    } else {
                        System.out.println("Passwords do not match. Please try again.");
                    }
                }
            } catch (IllegalArgumentException e) {

                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Password validation method
    public static void validatePassword(String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter.");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("Password must contain at least one lowercase letter.");
        }
        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Password must contain at least one number.");
        }
        if (!password.matches(".*[@#$%!].*")) {
            throw new IllegalArgumentException("Password must contain at least one special character (@, #, $, %, or !).");
        }
    }

    // Method for username validation and assignment
    public void setUsername() {
        while (true) {
            try {
                System.out.print("Enter your username: ");
                String inputUsername = scanner.nextLine();
                validateUsername(inputUsername);
                username = inputUsername;
                System.out.println("Username accepted.");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Username validation method
    public static void validateUsername(String username) {
        if (username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (username.length() < 5 || username.length() > 15) {
            throw new IllegalArgumentException("Username must be between 5 and 15 characters.");
        }
        if (Character.isDigit(username.charAt(0))) {
            throw new IllegalArgumentException("Username cannot start with a number.");
        }
        if (!username.matches("[a-zA-Z0-9_]+")) {
            throw new IllegalArgumentException("Username can only contain letters, numbers, and underscores.");
        }
        if (username.startsWith("_") || username.endsWith("_")) {
            throw new IllegalArgumentException("Username cannot start or end with an underscore.");
        }
        if (username.matches("\\d+")) {
            throw new IllegalArgumentException("Username cannot consist entirely of numbers.");
        }
    }


    // Method to get  username
    public String getUsername() {
        if (username == null) {
            return "No username set.";
        }
        return username;
    }


    @Override
    public String toString() {
        if (username == null || dateOfBirth == null) {
            return "No user data available.";
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return "Username: " + username +
                "\nDate of Birth: " + dateFormatter.format(dateOfBirth);
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getpassword() {
        return pass;
    }

    //interfaces
    public interface Payment {
        void addbalance(double amount);
        void subbalance(double amount);
        double getbalance();
    }


}
