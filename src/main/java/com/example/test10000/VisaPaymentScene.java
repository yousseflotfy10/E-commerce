package com.example.test10000;



import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class VisaPaymentScene {
    private Cart cart;
    private Customer customer;
    private Label statusLabel;
    private TextField cardNumberField;
    private TextField cardHolderField;
    private TextField expiryMonthField;
    private TextField expiryYearField;
    private TextField cvvField;

    public VisaPaymentScene(Cart cart, Customer customer) {
        this.cart = cart;
        this.customer = customer;
    }

    public Scene createVisaPaymentScene(Stage stage) {
        // Labels and input fields with custom styling
        Label cardNumberLabel = new Label("Card Number (16 digits):");
        cardNumberLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        cardNumberField = new TextField();
        cardNumberField.setStyle("-fx-pref-width: 300px; -fx-font-size: 14px; -fx-padding: 10px;");

        Label cardHolderLabel = new Label("Cardholder Name:");
        cardHolderLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        cardHolderField = new TextField();
        cardHolderField.setStyle("-fx-pref-width: 300px; -fx-font-size: 14px; -fx-padding: 10px;");

        Label expiryMonthLabel = new Label("Expiration Month (1-12):");
        expiryMonthLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        expiryMonthField = new TextField();
        expiryMonthField.setStyle("-fx-pref-width: 150px; -fx-font-size: 14px; -fx-padding: 10px;");

        Label expiryYearLabel = new Label("Expiration Year (YYYY):");
        expiryYearLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        expiryYearField = new TextField();
        expiryYearField.setStyle("-fx-pref-width: 150px; -fx-font-size: 14px; -fx-padding: 10px;");

        Label cvvLabel = new Label("CVV (3 digits):");
        cvvLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        cvvField = new TextField();
        cvvField.setStyle("-fx-pref-width: 150px; -fx-font-size: 14px; -fx-padding: 10px;");

        statusLabel = new Label();
        statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");

        // Pay Button with hover effect
        Button payButton = new Button("Pay with Visa");
        styleButton(payButton, "#4CAF50", "#45a049");

        // Return Button with hover effect
        Button returnToLoginButton = new Button("Return to Login");
        styleButton(returnToLoginButton, "#2196F3", "#1976D2");

        // Layout with better spacing and alignment
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: #dcdcdc; -fx-border-width: 2px;");

        layout.getChildren().addAll(
                cardNumberLabel, cardNumberField,
                cardHolderLabel, cardHolderField,
                expiryMonthLabel, expiryMonthField,
                expiryYearLabel, expiryYearField,
                cvvLabel, cvvField,
                payButton, statusLabel, returnToLoginButton
        );

        Scene scene = new Scene(layout, 400, 400);
        stage.setScene(scene);
        return scene;
    }

    // Helper method to style the buttons with hover effects
    private void styleButton(Button button, String normalColor, String hoverColor) {
        button.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px; -fx-background-color: " + normalColor + "; -fx-text-fill: white; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px; -fx-background-color: " + hoverColor + "; -fx-text-fill: white; -fx-border-radius: 5px; -fx-background-radius: 5px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px; -fx-background-color: " + normalColor + "; -fx-text-fill: white; -fx-border-radius: 5px; -fx-background-radius: 5px;"));
    }

    private void processVisaPayment() {
        String cardNumber = cardNumberField.getText();
        if (!cardNumber.matches("\\d{16}")) {
            statusLabel.setText("Invalid Card Number.");
            return;
        }

        String cardHolderName = cardHolderField.getText();
        if (cardHolderName.trim().isEmpty()) {
            statusLabel.setText("Cardholder Name cannot be empty.");
            return;
        }

        int expiryMonth = Integer.parseInt(expiryMonthField.getText());
        if (expiryMonth < 1 || expiryMonth > 12) {
            statusLabel.setText("Invalid Expiration Month.");
            return;
        }

        int expiryYear = Integer.parseInt(expiryYearField.getText());
        if (expiryYear < 2024) {
            statusLabel.setText("Invalid Expiration Year.");
            return;
        }

        String cvv = cvvField.getText();
        if (!cvv.matches("\\d{3}")) {
            statusLabel.setText("Invalid CVV.");
            return;
        }

        double remainingAmount = cart.getTotalPrice();
        if (customer.getbalance() >= remainingAmount) {
            customer.subbalance(remainingAmount);
            statusLabel.setText("Payment successful. Shipped to: " + customer.getAddress());
            Database.addOrder(cart);
        } else {
            statusLabel.setText("Insufficient balance.");
        }
    }
}
