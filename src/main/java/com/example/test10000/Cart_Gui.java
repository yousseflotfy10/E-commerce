package com.example.test10000;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Cart_Gui extends Application {

    private Cart cart;
    private ListView<String> productList;
    private Label totalLabel;
    private Customer customer;

    public Cart_Gui(Customer customer) {  // Constructor with customer parameter
        this.customer = customer;
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialize components
        cart = new Cart(customer);

        productList = new ListView<>();
        totalLabel = new Label("Total Price: $0.0");
        totalLabel.setStyle("-fx-text-fill: #FFFFFF;");

        // Create layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        // Set background color to dark
        layout.setStyle("-fx-background-color: #1E1E1E;");  // Dark background

        // Add shop name label with teal color
        Label shopNameLabel = new Label("iShop");
        shopNameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #00CED1;");  // Teal color for shop name

        // Create search bar with dark background and light text
        TextField searchField = new TextField();
        searchField.setPromptText("Search for products...");
        searchField.setStyle("-fx-background-color: #3C3F41; -fx-text-fill: #FFF; -fx-prompt-text-fill: #FFFFF;");  // Dark background and light text
        searchField.setOnKeyReleased(e -> filterProducts(searchField.getText()));  // Trigger search on key release

        // Define the common button style
        String buttonStyle = "-fx-background-color: #00CED1; -fx-text-fill: #FFFFFF;";

        // Create buttons for product types with the same color
        Button allProductsButton = new Button("All Products");
        allProductsButton.setStyle(buttonStyle);
        allProductsButton.setOnAction(e -> displayAllProducts());

        Button electronicsButton = new Button("Electronics");
        electronicsButton.setStyle(buttonStyle);
        electronicsButton.setOnAction(e -> filterByCategory("Electronics"));

        Button clothingButton = new Button("Clothing");
        clothingButton.setStyle(buttonStyle);
        clothingButton.setOnAction(e -> filterByCategory("Clothing"));

        Button accessoriesButton = new Button("Accessories");
        accessoriesButton.setStyle(buttonStyle);
        accessoriesButton.setOnAction(e -> filterByCategory("Accessories"));

        // Add to cart button
        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setStyle(buttonStyle);
        addToCartButton.setOnAction(e -> {
            String selected = productList.getSelectionModel().getSelectedItem();
            if (selected != null && !selected.isEmpty()) {
                // Extract product ID from the selected list item
                String productID = selected.split(", ")[0].split(": ")[1];  // Extracts the ID part
                Product product = Database.getProductByID(productID);
                if (product != null) {
                    cart.addProduct(product);
                    updateProductList();
                }
            }
        });

        Button removeFromCartButton = new Button("Remove from Cart");
        removeFromCartButton.setStyle(buttonStyle);
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

        Button cashButton = new Button("Pay with Cash");
        cashButton.setStyle(buttonStyle);
        cashButton.setOnAction(e -> {
            double totalPrice = cart.getTotalPrice();
            if (totalPrice > 0) {
                Database.addOrder(cart);
                showPaymentSummary(primaryStage, totalPrice);
            } else {
                System.out.println("Your cart is empty. Please add products before proceeding.");
            }
        });

        Button visaButton = new Button("Pay with Visa");
        visaButton.setStyle(buttonStyle);
        visaButton.setOnAction(e -> {
            VisaPaymentScene visaScene = new VisaPaymentScene(cart, customer);
            primaryStage.setScene(visaScene.createVisaPaymentScene(primaryStage));
        });

        Button installmentButton = new Button("Pay with Installment");
        installmentButton.setStyle(buttonStyle);
        installmentButton.setOnAction(e -> {
            InstallmentPaymentScene installmentScene = new InstallmentPaymentScene(cart, customer);
            primaryStage.setScene(installmentScene.createInstallmentPaymentScene(primaryStage));
        });

        // Create Return to Login button
        Button returnToLoginButton = new Button("Return to Login");
        returnToLoginButton.setStyle(buttonStyle);
        returnToLoginButton.setOnAction(e -> {
            LoginScene loginScene = new LoginScene();
            try {
                loginScene.start(primaryStage); // Navigate back to login page
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Layout arrangement
        HBox categoryButtons = new HBox(10, allProductsButton, electronicsButton, clothingButton, accessoriesButton);
        HBox paymentButtons = new HBox(10, cashButton, visaButton, installmentButton);
        // Create label with white text for "Search for Products:"
        Label searchLabel = new Label("Search for Products:");
        searchLabel.setStyle("-fx-text-fill: #FFFFFF;");

// Create label with white text for "Cart Items:"
        Label cartItemsLabel = new Label("Cart Items:");
        cartItemsLabel.setStyle("-fx-text-fill: #FFFFFF;");

// Create label with white text for "Payment Methods:"
        Label paymentMethodsLabel = new Label("Payment Methods:");
        paymentMethodsLabel.setStyle("-fx-text-fill: #FFFFFF;");

        layout.getChildren().addAll(
                shopNameLabel,  // Display shop name at the top
                searchLabel,  // White label for search instruction
                searchField,
                categoryButtons,  // Add category filter buttons
                cartItemsLabel,  // White label for cart items heading
                productList,
                totalLabel,
                addToCartButton,
                removeFromCartButton,
                paymentMethodsLabel,  // White label for payment methods heading
                paymentButtons,
                returnToLoginButton
        );

        // Set up the scene and stage
        Scene scene = new Scene(layout, 400, 600);  // Increased height for better layout
        primaryStage.setTitle("Shopping Cart Application");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initially display all products
        displayAllProducts();
    }

    private void displayAllProducts() {
        ArrayList<Product> products = new ArrayList<>(Database.getProductList());  // Create an ArrayList from the Database products

        // Clear any previously displayed items
        productList.getItems().clear();

        // Add all products to the list view
        for (Product product : products) {
            productList.getItems().add(
                    "ID: " + product.get_ID() + ", Name: " + product.get_name() + ", Price: $" + product.getPrice()
            );
        }
    }

    private void filterByCategory(String category) {
        ArrayList<Product> filteredProducts = new ArrayList<>(Database.getProductList());  // Create an ArrayList from the Database products

        filteredProducts = (ArrayList<Product>) filteredProducts.stream()
                .filter(product -> product.get_type().equalsIgnoreCase(category))
                .collect(Collectors.toList());

        // Update product list display
        productList.getItems().clear();
        for (Product product : filteredProducts) {
            productList.getItems().add(
                    "ID: " + product.get_ID() + ", Name: " + product.get_name() + ", Price: $" + product.getPrice()
            );
        }
    }

    private void filterProducts(String query) {
        // Filter products based on the search query using ArrayList
        ArrayList<Product> filteredProducts = new ArrayList<>(Database.getProductList());  // Create an ArrayList from the Database products
        filteredProducts = (ArrayList<Product>) filteredProducts.stream()
                .filter(product -> product.get_name().toLowerCase().contains(query.toLowerCase()) ||
                        product.get_ID().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());

        // Update product list display
        productList.getItems().clear();
        for (Product product : filteredProducts) {
            productList.getItems().add(
                    "ID: " + product.get_ID() + ", Name: " + product.get_name() + ", Price: $" + product.getPrice()
            );
        }
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

    private void showPaymentSummary(Stage primaryStage, double totalPrice) {
        for(Product product : Database.getProductList())
        {
            product.setCounter(0);
        }
        // Create a new layout for the payment summary
        VBox summaryLayout = new VBox(10);
        summaryLayout.setPadding(new Insets(10));

        // Display customer details and total price
        Label lblSummary = new Label("Payment Summary");
        Label lblName = new Label("Customer Name: " + customer.getUsername());
        Label lblAddress = new Label("Customer Address: " + customer.getAddress());
        Label lblTotalPrice = new Label("Total Price: $" + totalPrice);

        // Create the Return to Login button for the summary page
        Button returnToLoginButton = new Button("Return to Login");
        returnToLoginButton.setStyle("-fx-background-color: #00CED1; -fx-text-fill: #FFFFFF;");
        returnToLoginButton.setOnAction(e -> {
            LoginScene loginScene = new LoginScene();
            try {
                loginScene.start(primaryStage); // Navigate back to login page
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Add all labels and buttons to the layout
        summaryLayout.getChildren().addAll(
                lblSummary, lblName, lblAddress, lblTotalPrice, returnToLoginButton
        );

        // Set the summary scene
        Scene summaryScene = new Scene(summaryLayout, 300, 250);
        primaryStage.setScene(summaryScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
