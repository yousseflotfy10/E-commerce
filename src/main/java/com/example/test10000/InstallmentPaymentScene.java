package com.example.test10000;



import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class InstallmentPaymentScene {
    private Cart cart;
    private Customer customer;

    public InstallmentPaymentScene(Cart cart, Customer customer) {
        this.cart = cart;
        this.customer = customer;
    }

    public Scene createInstallmentPaymentScene(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label instructionLabel = new Label("Enter the number of months for installment payment (6 or 12):");
        TextField monthsField = new TextField();
        monthsField.setPromptText("6 or 12");

        Label resultLabel = new Label();

        Button calculateButton = new Button("Calculate Installment");
        calculateButton.setOnAction(e -> {
            try {
                int months = Integer.parseInt(monthsField.getText());
                double interestRate = (months == 6) ? 4.42 : (months == 12) ? 4.75 : 0;

                if (interestRate == 0) {
                    resultLabel.setText("Invalid number of months.");
                    return;
                }

                double totalPrice = cart.getTotalPrice();
                double interestAmount = (totalPrice * interestRate) / 100;
                double totalAmountWithInterest = totalPrice + interestAmount;
                double monthlyInstallment = totalAmountWithInterest / months;

                if (customer.getbalance() >= totalAmountWithInterest) {
                    customer.subbalance(totalAmountWithInterest);
                    resultLabel.setText(String.format("Payment Successful!\nTotal: $%.2f\nMonthly: $%.2f", totalAmountWithInterest, monthlyInstallment));
                    Database.addOrder(cart);
                } else {
                    resultLabel.setText("Insufficient balance.");
                }
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid input.");
            }
        });

        Button returnToLoginButton = new Button("Return to Login");
        returnToLoginButton.setOnAction(e -> {
            LoginScene loginScene = new LoginScene();
            try {
                loginScene.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        layout.getChildren().addAll(instructionLabel, monthsField, calculateButton, resultLabel);
        return new Scene(layout, 400, 300);
    }
}
