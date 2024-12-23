package com.example.test10000;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.util.List;

public class CustomerPage extends Application {

    private ListView<String> customerListView;

    @Override
    public void start(Stage primaryStage) {
        // Initialize components
        customerListView = new ListView<>();

        // Create layout
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #1E1E1E; -fx-font-family: Arial, sans-serif;");

        // Fetch the customers from the Database
        List<Customer> customers = Database.getCustomerList();

        // Display customer details
        for (Customer customer : customers) {
            String customerDetails = "Username: " + customer.getUsername() + "\n" +
                    "Address: " + customer.getAddress() + "\n" +
                    "Gender: " + customer.getGender() + "\n" +
                    "Date of Birth: " + customer.getDateOfBirth();
            customerListView.getItems().add(customerDetails);
        }

        // Style ListView
        customerListView.setStyle("-fx-font-size: 14px; -fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 5;");
        customerListView.setPrefHeight(300); // Limit the height of the ListView

        // Create Return to Admin Dashboard button
        Button returnButton = new Button("Return to Admin Dashboard");
        returnButton.setOnAction(e -> {
            AdminDashboard adminDashboard = new AdminDashboard(); // Assuming AdminDashboard class exists
            try {
                adminDashboard.start(primaryStage); // Navigate back to the admin dashboard
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        styleButton(returnButton);

        // Layout arrangement
        layout.getChildren().addAll(
                new Label("Customer List:") {{
                    setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");  // White text for title
                }},
                customerListView,
                returnButton  // Add the return button at the bottom
        );

        // Set up the scene and stage
        Scene scene = new Scene(layout, 400, 500);
        primaryStage.setTitle("iShop - Customer Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper method to style the buttons for a consistent look
    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #00CED1; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");
        button.setMinWidth(200);
        button.setMaxWidth(Double.MAX_VALUE);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
