package com.example.test10000;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ProductManagementScene extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Set up the layout for Product Management Scene
        GridPane grid = new GridPane();
        grid.setVgap(15);
        grid.setHgap(15);
        grid.setPadding(new Insets(30));

        // Set the background color of the grid to dark
        grid.setStyle("-fx-background-color: #1E1E1E;");

        // Labels and Inputs
        Label lblTitle = new Label("iShop - Product Management");
        lblTitle.setFont(new Font("Arial", 24));
        lblTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #00CED1;");  // Teal color for title

        Label lblProductId = new Label("Product ID:");
        Label lblProductName = new Label("Product Name:");
        Label lblProductType = new Label("Product Type:");
        Label lblProductPrice = new Label("Price:");
        Label lblProductQuantity = new Label("Quantity:");

        // Set text color to white for labels
        lblProductId.setStyle("-fx-text-fill: #FFFFFF;");
        lblProductName.setStyle("-fx-text-fill: #FFFFFF;");
        lblProductType.setStyle("-fx-text-fill: #FFFFFF;");
        lblProductPrice.setStyle("-fx-text-fill: #FFFFFF;");
        lblProductQuantity.setStyle("-fx-text-fill: #FFFFFF;");

        TextField txtProductId = new TextField();
        TextField txtProductName = new TextField();
        TextField txtProductType = new TextField();
        TextField txtProductPrice = new TextField();
        TextField txtProductQuantity = new TextField();

        // Style text fields with dark background and light text
        String textFieldStyle = "-fx-background-color: #3C3F41; -fx-text-fill: #FFFFFF;";
        txtProductId.setStyle(textFieldStyle);
        txtProductName.setStyle(textFieldStyle);
        txtProductType.setStyle(textFieldStyle);
        txtProductPrice.setStyle(textFieldStyle);
        txtProductQuantity.setStyle(textFieldStyle);

        // Buttons
        Button btnAddProduct = new Button("Add Product");
        Button btnUpdateProduct = new Button("Update Product");
        Button btnRemoveProduct = new Button("Remove Product");
        Button btnViewProduct = new Button("View Product");
        Button btnShowAllProducts = new Button("Show All Products");
        Button btnReturnToAdmin = new Button("Return to Admin Dashboard");

        // Apply consistent button style
        styleButton(btnAddProduct);
        styleButton(btnUpdateProduct);
        styleButton(btnRemoveProduct);
        styleButton(btnViewProduct);
        styleButton(btnShowAllProducts);
        styleButton(btnReturnToAdmin);

        // Add elements to the grid
        grid.add(lblTitle, 0, 0, 2, 1);
        grid.add(lblProductId, 0, 1);
        grid.add(txtProductId, 1, 1);
        grid.add(lblProductName, 0, 2);
        grid.add(txtProductName, 1, 2);
        grid.add(lblProductType, 0, 3);
        grid.add(txtProductType, 1, 3);
        grid.add(lblProductPrice, 0, 4);
        grid.add(txtProductPrice, 1, 4);
        grid.add(lblProductQuantity, 0, 5);
        grid.add(txtProductQuantity, 1, 5);

        grid.add(btnAddProduct, 0, 6);
        grid.add(btnUpdateProduct, 1, 6);
        grid.add(btnRemoveProduct, 0, 7);
        grid.add(btnViewProduct, 1, 7);
        grid.add(btnShowAllProducts, 0, 8, 2, 1);
        grid.add(btnReturnToAdmin, 0, 9, 2, 1);

        // Add Product Action
        btnAddProduct.setOnAction(e -> {
            String id = txtProductId.getText();
            String name = txtProductName.getText();
            String type = txtProductType.getText();
            double price;
            int quantity;

            try {
                price = Double.parseDouble(txtProductPrice.getText());
                quantity = Integer.parseInt(txtProductQuantity.getText());

                if (Database.getProductByID(id) != null) {
                    showAlert("Duplicate Product ID", "There is already a product with the ID: " + id);
                } else {
                    Product product = new Product(type, id, price, name, quantity);
                    Database.addProduct(product);
                    clearFields(txtProductId, txtProductName, txtProductType, txtProductPrice, txtProductQuantity);
                }
            } catch (NumberFormatException ex) {
                showAlert("Input Error", "Invalid price or quantity input.");
            }
        });

        // Update Product Action
        btnUpdateProduct.setOnAction(e -> {
            String id = txtProductId.getText();
            String name = txtProductName.getText();
            String type = txtProductType.getText();
            double price;
            int quantity;

            try {
                price = Double.parseDouble(txtProductPrice.getText());
                quantity = Integer.parseInt(txtProductQuantity.getText());

                if (Database.getProductByID(id) != null) {
                    Database.updateProduct(id, type, price, name, quantity);
                    clearFields(txtProductId, txtProductName, txtProductType, txtProductPrice, txtProductQuantity);
                } else {
                    showAlert("Product Not Found", "No product found with the ID: " + id);
                }
            } catch (NumberFormatException ex) {
                showAlert("Input Error", "Invalid price or quantity input.");
            }
        });

        // Remove Product Action
        btnRemoveProduct.setOnAction(e -> {
            String id = txtProductId.getText();
            boolean success = Database.removeProductByID(id);
            if (success) {
                clearFields(txtProductId, txtProductName, txtProductType, txtProductPrice, txtProductQuantity);
            } else {
                showAlert("Product Not Found", "No product found with the ID: " + id);
            }
        });

        // View Product Action
        btnViewProduct.setOnAction(e -> {
            String id = txtProductId.getText();
            Product product = Database.getProductByID(id);
            if (product != null) {
                txtProductName.setText(product.get_name());
                txtProductType.setText(product.get_type());
                txtProductPrice.setText(String.valueOf(product.getPrice()));
                txtProductQuantity.setText(String.valueOf(product.getQuantity()));
            } else {
                showAlert("Product Not Found", "No product found with the ID: " + id);
            }
        });

        // Show All Products Action
        btnShowAllProducts.setOnAction(e -> {
            ArrayList<Product> productList = Database.getProductList();

            Stage allProductsStage = new Stage();
            allProductsStage.setTitle("All Products");

            TableView<Product> productTable = new TableView<>();

            TableColumn<Product, String> idColumn = new TableColumn<>("Product ID");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("_ID"));

            TableColumn<Product, String> nameColumn = new TableColumn<>("Product Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("_name"));

            TableColumn<Product, String> typeColumn = new TableColumn<>("Product Type");
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("_type"));

            TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Quantity");
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

            productTable.getColumns().addAll(idColumn, nameColumn, typeColumn, priceColumn, quantityColumn);
            productTable.getItems().addAll(productList);

            VBox vbox = new VBox(10, new Label("All Products:"), productTable);
            vbox.setPadding(new Insets(20));

            Scene scene = new Scene(vbox, 600, 400);
            allProductsStage.setScene(scene);
            allProductsStage.show();
        });

        // Return to Admin Dashboard
        btnReturnToAdmin.setOnAction(e -> {
            AdminDashboard adminDashboard = new AdminDashboard();
            try {
                adminDashboard.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Set up the scene and show the stage
        Scene scene = new Scene(grid, 550, 600);
        primaryStage.setTitle("iShop - Product Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #00CED1; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-border-radius: 5px; -fx-cursor: hand;");
        button.setMinWidth(220);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
