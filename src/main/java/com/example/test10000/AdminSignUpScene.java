package com.example.test10000;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javax.swing.*;
import java.time.LocalDate;

public class AdminSignUpScene extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Set up the scene and layout for Admin Sign-Up
        GridPane grid = new GridPane();
        grid.setVgap(12);
        grid.setHgap(12);
        grid.setPadding(new Insets(20));
        grid.setStyle("-fx-background-color: #1E1E1E; -fx-border-radius: 10px; -fx-border-color: #444;");

        // Title label for the page
        Label lblTitle = new Label("iShop");
        lblTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24)); // Set font size and weight
        lblTitle.setStyle("-fx-text-fill: #00CED1;"); // Teal color for the title

        // Initialize components
        Label lblUsername = new Label("Username:");
        Label lblPassword = new Label("Password:");
        Label lblConfirmPassword = new Label("Confirm Password:");
        Label lblRole = new Label("Role:");

        TextField txtUsername = new TextField();
        PasswordField txtPassword = new PasswordField();
        PasswordField txtConfirmPassword = new PasswordField();
        ComboBox<String> cmbRole = new ComboBox<>();
        cmbRole.getItems().addAll("Admin", "supervisior", "Manager","Moderator");
        cmbRole.setValue("Admin"); // Default selection

        Button btnSignUpAdmin = new Button("Sign Up as Admin");
        Button btnReturnToLogin = new Button("Return to Login");

        // Make labels white
        lblUsername.setStyle("-fx-text-fill: #FFFFFF;");
        lblPassword.setStyle("-fx-text-fill: #FFFFFF;");
        lblConfirmPassword.setStyle("-fx-text-fill: #FFFFFF;");
        lblRole.setStyle("-fx-text-fill: #FFFFFF;");

        // Adding components to the grid layout
        grid.add(lblTitle, 0, 0, 2, 1); // Add title label spanning two columns
        grid.add(lblUsername, 0, 1);
        grid.add(txtUsername, 1, 1);
        grid.add(lblPassword, 0, 2);
        grid.add(txtPassword, 1, 2);
        grid.add(lblConfirmPassword, 0, 3);
        grid.add(txtConfirmPassword, 1, 3);
        grid.add(lblRole, 0, 4);
        grid.add(cmbRole, 1, 4);
        grid.add(btnSignUpAdmin, 0, 5, 2, 1);
        grid.add(btnReturnToLogin, 0, 6, 2, 1);

        // Center the title label
        GridPane.setHalignment(lblTitle, javafx.geometry.HPos.CENTER);

        // Style the components
        txtUsername.setStyle("-fx-background-color: #3C3F41; -fx-text-fill: #FFF; -fx-placeholder-text-fill: #BBB; -fx-border-radius: 5px; -fx-padding: 5px;");
        txtPassword.setStyle("-fx-background-color: #3C3F41; -fx-text-fill: #FFF; -fx-placeholder-text-fill: #BBB; -fx-border-radius: 5px; -fx-padding: 5px;");
        txtConfirmPassword.setStyle("-fx-background-color: #3C3F41; -fx-text-fill: #FFF; -fx-placeholder-text-fill: #BBB; -fx-border-radius: 5px; -fx-padding: 5px;");
        cmbRole.setStyle("-fx-background-color: #3C3F41; -fx-text-fill: #FFF; -fx-border-radius: 5px; -fx-padding: 5px;");

        btnSignUpAdmin.setStyle("-fx-background-color: #00CED1; -fx-text-fill: white; -fx-border-radius: 5px; -fx-padding: 10px 20px;");
        btnSignUpAdmin.setOnMouseEntered(e -> btnSignUpAdmin.setStyle("-fx-background-color: #20B2AA; -fx-text-fill: white; -fx-border-radius: 5px; -fx-padding: 10px 20px;"));
        btnSignUpAdmin.setOnMouseExited(e -> btnSignUpAdmin.setStyle("-fx-background-color: #00CED1; -fx-text-fill: white; -fx-border-radius: 5px; -fx-padding: 10px 20px;"));

        btnReturnToLogin.setStyle("-fx-background-color: #00CED1; -fx-text-fill: white; -fx-border-radius: 5px; -fx-padding: 10px 20px;");
        btnReturnToLogin.setOnMouseEntered(e -> btnReturnToLogin.setStyle("-fx-background-color: #20B2AA; -fx-text-fill: white; -fx-border-radius: 5px; -fx-padding: 10px 20px;"));
        btnReturnToLogin.setOnMouseExited(e -> btnReturnToLogin.setStyle("-fx-background-color: #00CED1; -fx-text-fill: white; -fx-border-radius: 5px; -fx-padding: 10px 20px;"));

        // Align the grid contents
        grid.setAlignment(Pos.CENTER);

        // Button action for Sign-Up
        btnSignUpAdmin.setOnAction(e -> {
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            String confirmPassword = txtConfirmPassword.getText();
            String role = cmbRole.getValue();

            // Validate that both passwords are equal
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

            if (role == null || role.isEmpty()) {
                showError("Role cannot be empty.");
                return;
            }

            if (UserManager.registerAdmin(username, password, LocalDate.now(), role, 40)) {
                System.out.println("Admin registered successfully.");
                AdminDashboard adminDashboard = new AdminDashboard();
                try {
                    adminDashboard.start(primaryStage); // Navigate to Admin Dashboard
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                showError("Admin username already exists.");
            }
        });

        // Button action for Return to Login
        btnReturnToLogin.setOnAction(e -> {
            // Redirect back to the login page
            redirectToLoginPage(primaryStage);
        });

        // Set up the scene and show the window
        Scene scene = new Scene(grid, 400, 350); // Adjusted height to fit the title
        primaryStage.setTitle("iShop"); // Set the title explicitly to "iShop"
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Redirect to the LoginPage
    private void redirectToLoginPage(Stage primaryStage) {
        LoginScene loginPage = new LoginScene();  // Create an instance of the LoginPage
        try {
            loginPage.start(primaryStage);  // Navigate to the LoginPage
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Show error message using JOptionPane
    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
