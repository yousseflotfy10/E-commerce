package com.example.test10000;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AdminDashboard extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the dashboard layout
        VBox layout = new VBox(20); // Increased gap for better spacing
        layout.setPadding(new Insets(40, 30, 30, 30)); // Adjusted padding for professional spacing

        // Set a dark theme background color
        layout.setStyle("-fx-background-color: #1E1E1E;");

        // Create a title label and style it for the dark theme
        Label lblTitle = new Label("iShop - Admin Dashboard");
        lblTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");

        // Create styled buttons using teal color with white text
        Button btnViewOrders = createStyledButton("View Orders");
        btnViewOrders.setOnAction(e -> {
            OrderPageScene ordersPage = new OrderPageScene();
            try {
                ordersPage.start(primaryStage); // Navigate to orders page
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button btnManageProducts = createStyledButton("Manage Products");
        btnManageProducts.setOnAction(e -> {
            ProductManagementScene productManagementScene = new ProductManagementScene();
            try {
                productManagementScene.start(primaryStage); // Navigate to product management page
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button btnViewCustomers = createStyledButton("View Customers");
        btnViewCustomers.setOnAction(e -> {
            CustomerPage customerPageScene = new CustomerPage();
            try {
                customerPageScene.start(primaryStage); // Navigate to customers page
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button btnLogout = createStyledButton("Logout");
        btnLogout.setOnAction(e -> {
            LoginScene loginScene = new LoginScene();
            try {
                loginScene.start(primaryStage); // Navigate back to login page
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button btnExit = createStyledButton("Exit");
        btnExit.setOnAction(e -> primaryStage.close()); // Close the application

        // Add all components to the layout
        layout.getChildren().addAll(lblTitle, btnViewOrders, btnManageProducts, btnViewCustomers, btnLogout, btnExit);
        layout.setAlignment(Pos.CENTER);

        // Set up scene and stage
        Scene scene = new Scene(layout, 450, 450); // Increased width and height for spacing
        primaryStage.setTitle("iShop Admin Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Helper method to create styled buttons for dark theme
     *
     * @param text The button text
     * @return A styled button
     */
    private Button createStyledButton(String text) {
        String buttonStyle = "-fx-background-color: #00CED1; -fx-text-fill: #FFFFFF; -fx-font-size: 16px; "
                + "-fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px;";
        Button button = new Button(text);
        button.setStyle(buttonStyle);
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #00B3B3; -fx-text-fill: #FFFFFF; -fx-font-size: 16px; "
                + "-fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px;"));
        button.setOnMouseExited(e -> button.setStyle(buttonStyle));
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
