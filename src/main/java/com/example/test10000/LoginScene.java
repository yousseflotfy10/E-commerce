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

public class LoginScene extends Application {

    @Override
    public void start(Stage primaryStage) {


        // iShop Title Label
        Label lblTitle = new Label("iShop");
        lblTitle.setFont(new Font("Arial", 30));
        lblTitle.setTextFill(Color.web("#00CED1")); // Teal color for the title
        lblTitle.setStyle("-fx-font-weight: bold;");
        lblTitle.setAlignment(Pos.CENTER);

        // Labels and input fields
        TextField txtUsername = new TextField();
        txtUsername.setPromptText("Enter your username");
        txtUsername.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px; -fx-padding: 10; -fx-background-color: #3C3F41; -fx-text-fill: #FFF; -fx-prompt-text-fill: #BBB;");

        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Enter your password");
        txtPassword.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px; -fx-padding: 10; -fx-background-color: #3C3F41; -fx-text-fill: #FFF; -fx-prompt-text-fill: #BBB;");

        // Buttons with hover effects
        Button btnLogin = new Button("Login");
        styleButton(btnLogin, "#00CED1", "#20B2AA"); // Updated to teal-based theme

        Button btnSignUpAdmin = new Button("Sign Up as Admin");
        styleButton(btnSignUpAdmin, "#00CED1", "#20B2AA"); // Updated to teal-based theme

        Button btnSignUpCustomer = new Button("Sign Up as Customer");
        styleButton(btnSignUpCustomer, "#00CED1", "#20B2AA"); // Updated to teal-based theme

        // Layout configuration
        VBox loginBox = new VBox(20, lblTitle, txtUsername, txtPassword, btnLogin, btnSignUpAdmin, btnSignUpCustomer);
        loginBox.setPadding(new Insets(20));
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setStyle("-fx-background-color: #1E1E1E; -fx-border-color: #444; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        // Root container
        StackPane root = new StackPane(loginBox);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #1E1E1E;"); // Dark background color

        // Set up the scene and stage
        Scene scene = new Scene(root, 400, 450);
        primaryStage.setTitle("iShop - Login");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Button actions
        btnLogin.setOnAction(e -> {
            String username = txtUsername.getText();
            String password = txtPassword.getText();

            boolean validAdmin = UserManager.validateLogin(username, password, true);
            boolean validCustomer = UserManager.validateLogin(username, password, false);

            if (validAdmin) {
                AdminDashboard adminDashboard = new AdminDashboard();
                try {
                    adminDashboard.start(primaryStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else if (validCustomer) {
                Customer currentCustomer = Database.getCustomerList().stream()
                        .filter(c -> c.getUsername().equals(username))
                        .findFirst()
                        .orElse(null);
                if (currentCustomer != null) {
                    Cart_Gui shoppingCart = new Cart_Gui(currentCustomer);
                    try {
                        shoppingCart.start(primaryStage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText("Invalid Credentials");
                alert.setContentText("Please check your username and password and try again.");
                alert.showAndWait();
            }
        });

        btnSignUpAdmin.setOnAction(e -> {
            AdminSignUpScene adminSignUpScene = new AdminSignUpScene();
            try {
                adminSignUpScene.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnSignUpCustomer.setOnAction(e -> {
            CustomerSignup customerSignUpScene = new CustomerSignup();
            try {
                customerSignUpScene.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
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
