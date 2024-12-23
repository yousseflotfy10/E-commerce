package com.example.test10000;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class OrderPageScene extends Application {

    private ListView<String> orderList;
    private Label totalLabel;

    @Override
    public void start(Stage primaryStage) {
        // Initialize components
        orderList = new ListView<>();
        totalLabel = new Label("Total Price: $0.0");

        // Create layout
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #1E1E1E;");

        // Fetch the orders from the Database
        double totalPrice = 0.0;
        for (Cart order : Database.getOrderList()) {
            // Display order details including username, address, and total price of the order
            String orderDetails = "Order for " + order.getCustomer().getUsername() + "\n" +
                    "Address: " + order.getCustomer().getAddress() + "\n" +
                    "Total Price: $" + order.getTotalPrice();
            orderList.getItems().add(orderDetails);
            totalPrice += order.getTotalPrice(); // Add to the total price
        }

        // Update the total label
        totalLabel.setText("Total Price: $" + totalPrice);
        totalLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #00CED1;");

        // Create Return to Admin Dashboard button
        Button returnToDashboardButton = new Button("Return to Admin Dashboard");
        returnToDashboardButton.setOnAction(e -> {
            AdminDashboard adminDashboard = new AdminDashboard();
            try {
                adminDashboard.start(primaryStage); // Navigate back to admin dashboard
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        styleButton(returnToDashboardButton);

        // Style ListView
        orderList.setStyle("-fx-font-size: 14px; -fx-background-color: #2B2B2B; -fx-text-fill: white; "
                + "-fx-border-color: #00CED1; -fx-border-radius: 5px; -fx-padding: 5px;");
        orderList.setPrefHeight(300); // Limit the height of the ListView

        // Layout arrangement
        layout.getChildren().addAll(
                new Label("iShop - Orders") {{
                    setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-padding: 0 0 10 0;");
                }},
                orderList,
                totalLabel,
                returnToDashboardButton  // Add the return button at the bottom
        );

        // Set up the scene and stage
        Scene scene = new Scene(layout, 450, 550);
        primaryStage.setTitle("iShop - Orders");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper method to style the buttons for a consistent look
    private void styleButton(Button button) {
        String buttonStyle = "-fx-background-color: #00CED1; -fx-text-fill: white; -fx-font-size: 14px; "
                + "-fx-padding: 10 20; -fx-border-radius: 5px; -fx-background-radius: 5px;";
        button.setStyle(buttonStyle);
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #00B3B3; -fx-text-fill: white; -fx-font-size: 14px; "
                + "-fx-padding: 10 20; -fx-border-radius: 5px; -fx-background-radius: 5px;"));
        button.setOnMouseExited(e -> button.setStyle(buttonStyle));
        button.setMinWidth(200);
        button.setMaxWidth(Double.MAX_VALUE);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
