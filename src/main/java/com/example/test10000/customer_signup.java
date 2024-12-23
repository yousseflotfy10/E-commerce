package com.example.test10000;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.swing.*;
import java.time.LocalDate;
import java.time.Period;

class CustomerSignup extends Application {

    @Override
    public void start(Stage primaryStage) {

        // iShop Title Label
        Label lblTitle = new Label("iShop");
        lblTitle.setFont(new Font("Arial", 30));
        lblTitle.setTextFill(Color.web("#00CED1")); // Updated to teal
        lblTitle.setStyle("-fx-font-weight: bold;");
        lblTitle.setAlignment(Pos.CENTER);

        // Initialize components
        Label lblUsername = new Label("Username:");
        lblUsername.setTextFill(Color.web("#FFFFFF"));
        Label lblPassword = new Label("Password:");
        lblPassword.setTextFill(Color.web("#FFFFFF"));
        Label lblConfirmPassword = new Label("Confirm Password:");
        lblConfirmPassword.setTextFill(Color.web("#FFFFFF"));
        Label lblDateOfBirth = new Label("Date of Birth:");
        lblDateOfBirth.setTextFill(Color.web("#FFFFFF"));
        Label lblAddress = new Label("Address:");
        lblAddress.setTextFill(Color.web("#FFFFFF"));
        Label lblGender = new Label("Gender:");
        lblGender.setTextFill(Color.web("#FFFFFF"));

        TextField txtUsername = new TextField();
        txtUsername.setStyle("-fx-background-color: #3C3F41; -fx-text-fill: #FFF; -fx-prompt-text-fill: #BBB; -fx-border-radius: 5px; -fx-padding: 5px;");

        PasswordField txtPassword = new PasswordField();
        txtPassword.setStyle("-fx-background-color: #3C3F41; -fx-text-fill: #FFF; -fx-prompt-text-fill: #BBB; -fx-border-radius: 5px; -fx-padding: 5px;");

        PasswordField txtConfirmPassword = new PasswordField();
        txtConfirmPassword.setStyle("-fx-background-color: #3C3F41; -fx-text-fill: #FFF; -fx-prompt-text-fill: #BBB; -fx-border-radius: 5px; -fx-padding: 5px;");

        DatePicker dateOfBirthPicker = new DatePicker();
        dateOfBirthPicker.setStyle("-fx-background-color: #3C3F41; -fx-text-fill: #FFF; -fx-border-radius: 5px; -fx-padding: 5px;");

        TextField txtAddress = new TextField();
        txtAddress.setStyle("-fx-background-color: #3C3F41; -fx-text-fill: #FFF; -fx-prompt-text-fill: #BBB; -fx-border-radius: 5px; -fx-padding: 5px;");

        ComboBox<String> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("MALE", "FEMALE");
        genderComboBox.setStyle("-fx-background-color: #3C3F41; -fx-text-fill: #FFF; -fx-border-radius: 5px; -fx-padding: 5px;");

        // Label to display selected gender
        Label lblSelectedGender = new Label();
        lblSelectedGender.setTextFill(Color.web("#FFFFFF"));
        lblSelectedGender.setStyle("-fx-font-size: 14px;");

        // Listen for selection changes in the gender combo box
        genderComboBox.setOnAction(e -> {
            String selectedGender = genderComboBox.getValue();
            lblSelectedGender.setText("Selected Gender: " + selectedGender);
        });

        Button btnSignUp = new Button("Sign Up");
        styleButton(btnSignUp, "#00CED1", "#20B2AA"); // Updated to teal-based theme

        Button btnReturnToLogin = new Button("Return to Login");
        styleButton(btnReturnToLogin, "#00CED1", "#20B2AA"); // Updated to teal-based theme

        // Layout configuration
        GridPane grid = new GridPane();
        grid.setVgap(12);
        grid.setHgap(12);
        grid.setPadding(new Insets(20));
        grid.setStyle("-fx-background-color: #2B2B2B; -fx-border-radius: 10px; -fx-border-color: #444;");

        // Adding components to the grid
        grid.add(lblUsername, 0, 0);
        grid.add(txtUsername, 1, 0);
        grid.add(lblPassword, 0, 1);
        grid.add(txtPassword, 1, 1);
        grid.add(lblConfirmPassword, 0, 2);
        grid.add(txtConfirmPassword, 1, 2);
        grid.add(lblDateOfBirth, 0, 3);
        grid.add(dateOfBirthPicker, 1, 3);
        grid.add(lblAddress, 0, 4);
        grid.add(txtAddress, 1, 4);
        grid.add(lblGender, 0, 5);
        grid.add(genderComboBox, 1, 5);
        grid.add(lblSelectedGender, 1, 6); // Add the label to show selected gender
        grid.add(btnSignUp, 1, 7);
        grid.add(btnReturnToLogin, 1, 8);

        // Align the grid contents
        grid.setAlignment(Pos.CENTER);

        // Combine Title and Grid into a Vertical Box
        VBox layout = new VBox(20, lblTitle, grid);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #1E1E1E; -fx-padding: 20;");

        // Button action for Sign-Up
        btnSignUp.setOnAction(e -> {
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            String confirmPassword = txtConfirmPassword.getText();
            LocalDate dateOfBirth = dateOfBirthPicker.getValue();
            String address = txtAddress.getText();
            String genderString = genderComboBox.getValue();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || dateOfBirth == null || address.isEmpty() || genderString == null) {
                showError("All fields must be filled in.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                showError("Passwords do not match.");
                return;
            }

            // Password validation
            try {
                Person.validatePassword(password);
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
                return;
            }

            int age = Period.between(dateOfBirth, LocalDate.now()).getYears();
            if (age < 10) {
                showError("You must be at least 10 years old.");
                return;
            }

            Customer.Gender gender = Customer.Gender.valueOf(genderString.toUpperCase());
            Customer newCustomer = new Customer(username, dateOfBirth, password, address, gender);

            if (UserManager.registerCustomer(username, password, dateOfBirth, address, gender)) {
                redirectToCartPage(primaryStage, newCustomer);
            } else {
                showError("Registration failed. Please try again.");
            }
        });

        btnReturnToLogin.setOnAction(e -> redirectToLoginPage(primaryStage));

        // Set up the scene and show the window
        Scene scene = new Scene(layout, 500, 550);
        primaryStage.setTitle("Customer Sign-Up - iShop");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void redirectToCartPage(Stage primaryStage, Customer customer) {
        Cart_Gui shoppingCart = new Cart_Gui(customer);
        try {
            shoppingCart.start(primaryStage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void redirectToLoginPage(Stage primaryStage) {
        LoginScene loginPage = new LoginScene();
        try {
            loginPage.start(primaryStage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void showError(String message) {
        // Using JOptionPane to display the error message
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void styleButton(Button button, String normalColor, String hoverColor) {
        button.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px; -fx-background-color: " + normalColor + "; -fx-text-fill: white; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px; -fx-background-color: " + hoverColor + "; -fx-text-fill: white; -fx-border-radius: 5px; -fx-background-radius: 5px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px; -fx-background-color: " + normalColor + "; -fx-text-fill: white; -fx-border-radius: 5px; -fx-background-radius: 5px;"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}