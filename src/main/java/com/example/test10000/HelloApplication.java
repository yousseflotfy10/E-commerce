package com.example.test10000;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private Cart cart;
    private ListView<String> productList;
    private Label totalLabel;

    @Override
    public void start(Stage primaryStage) {
        // Initialize components
        Database.initialize();
        Customer customer = Database.getCustomerList().get(0); // Use the first predefined customer
        cart = new Cart(customer);

        productList = new ListView<>();
        totalLabel = new Label("Total Price: $0.0");

        // Create layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        // Create product list dropdown displaying ID and name
        ComboBox<String> productDropdown = new ComboBox<>();
        for (Product product : Database.getProductList()) {
            // Display product ID and Name in the dropdown
            productDropdown.getItems().add(product.get_ID() + " - " + product.get_name() + " - $" + product.getPrice());
        }

        // Add to cart button
        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setOnAction(e -> {
            String selected = productDropdown.getValue();
            if (selected != null) {
                // Extract product ID from the selected string
                String productID = selected.split(" - ")[0];  // Extracts the ID part
                Product product = Database.getProductByID(productID);
                if (product != null) {
                    cart.addProduct(product);
                    updateProductList();
                }
            }
        });

        // Remove from cart button
        Button removeFromCartButton = new Button("Remove from Cart");
        removeFromCartButton.setOnAction(e -> {
            String selected = productList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                // Extract product ID from the selected list item
                String productID = selected.split(", ")[0].split(": ")[1];  // Extracts the ID part
                Product product = Database.getProductByID(productID);
                if (product != null) {
                    cart.removeProduct(product);
                    updateProductList();
                }
            }
        });

        // Payment buttons
        Button cashButton = new Button("Pay with Cash");
        cashButton.setOnAction(e -> cart.cash());

        Button visaButton = new Button("Pay with Visa");
        visaButton.setOnAction(e -> cart.visa());

        Button installmentButton = new Button("Pay with Installment");
        installmentButton.setOnAction(e -> cart.installment());

        // Layout arrangement
        HBox paymentButtons = new HBox(10, cashButton, visaButton, installmentButton);
        layout.getChildren().addAll(
                new Label("Select a Product:"),
                productDropdown,
                addToCartButton,
                new Label("Cart Items:"),
                productList,
                removeFromCartButton,
                totalLabel,
                new Label("Payment Methods:"),
                paymentButtons
        );

        // Set up the scene and stage
        Scene scene = new Scene(layout, 400, 500);
        primaryStage.setTitle("Shopping Cart Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateProductList() {
        productList.getItems().clear();
        for (Product product : cart.getProducts()) {
            productList.getItems().add(
                    "ID: " + product.get_ID() + ", Name: " + product.get_name() + ", Quantity: " + product.getCounter()
            );
        }
        totalLabel.setText("Total Price: $" + cart.getTotalPrice());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
