package com.example.test10000;

import javax.swing.*;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Cart extends Product {
    private double totalprice;
    private ArrayList<Product> cart = new ArrayList();
    private ArrayList<Product> product = new ArrayList();
    private Scanner scanner;
    private Customer customer;

    public Cart() {
        this.scanner = new Scanner(System.in);
        this.totalprice = 0.0;
    }

    public Cart(Customer customer) {
        this.scanner = new Scanner(System.in);
        this.customer = customer;
        this.product = new ArrayList();
    }

    public void addProduct(Product p) {
        for (int i = 0; i < this.cart.size(); ++i) {
            if (((Product) this.cart.get(i)).get_ID().equals(p.get_ID())) {
                if (p.getCounter() < p.getQuantity()) { // Check if stock allows addition
                    ((Product) this.cart.get(i)).incrementCounter();
                   p.set_Quantity(p.getQuantity()-p.reduceQuantity());
                } else {
                    // Display error if adding exceeds available stock
                    JOptionPane.showMessageDialog(null, "Error: Not enough stock available!", "Stock Error", JOptionPane.ERROR_MESSAGE);
                }
                return;
            }
        }

        if (p.getCounter() == 0) {
            p.incrementCounter();
        }

        if (p.getCounter() <= p.getQuantity()) {  // Check stock before adding
            this.cart.add(p);
            p.set_Quantity(p.getQuantity()-p.reduceQuantity());
        } else {
            // Show error if the stock is exceeded
            JOptionPane.showMessageDialog(null, "Error: Not enough stock available!", "Stock Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removeProduct(Product p) {
        for (int i = 0; i < this.cart.size(); ++i) {
            if (((Product) this.cart.get(i)).get_ID().equals(p.get_ID())) {
                ((Product) this.cart.get(i)).decrementCounter();
                if (((Product) this.cart.get(i)).getCounter() == 0) {
                    this.cart.remove(i);
                }
                return;
            }
        }
    }

    public void displayProducts() {
        if (this.cart.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Products in your cart:");
            for (int i = 0; i < this.cart.size(); ++i) {
                Product product = (Product) this.cart.get(i);
                PrintStream var10000 = System.out;
                String var10001 = product.get_ID();
                var10000.println("ID: " + var10001 + ", Name: " + product.get_name() + ", Price(per one): " + product.getPrice() + ", Quantity: " + product.getCounter());
            }
            System.out.println("Total Price: $" + this.getTotalPrice());
        }
    }

    public double getTotalPrice() {
        this.totalprice = 0.0;
        for (int i = 0; i < this.cart.size(); ++i) {
            Product product = (Product) this.cart.get(i);
            this.totalprice += product.getPrice() * (double) product.getCounter();
        }
        return this.totalprice;
    }

    public void paymentMethod() {
        System.out.println("Select Payment Method:");
        System.out.println("1. Cash");
        System.out.println("2. Visa");
        System.out.println("3. Installment");

        while (true) {
            try {
                int choice = this.scanner.nextInt();
                if (choice == 1) {
                    this.cash();
                } else if (choice == 2) {
                    this.visa();
                } else {
                    if (choice != 3) {
                        System.out.println("Invalid input. Please enter a valid number (1, 2, or 3).");
                        continue;
                    }
                    this.installment();
                }
                return;
            } catch (InputMismatchException var2) {
                System.out.println("Invalid input. Please enter a valid number (1, 2, or 3).");
                this.scanner.nextLine();
            }
        }
    }

    public void cash() {
        System.out.println("Total Price: $" + this.getTotalPrice());
        System.out.println("Thanks for purchasing!");
        Database.addOrder(this);
    }

    public void visa() {
        try {
            this.scanner.nextLine();
            System.out.println("Enter Card Number: ");
            String cardNumber = this.scanner.nextLine();
            System.out.println("Enter Cardholder Name: ");
            String cardName = this.scanner.nextLine();
            System.out.println("Enter Card Expiration Month (1-12): ");
            int cardExpireMonth = this.scanner.nextInt();
            System.out.println("Enter Card Expiration Year (YYYY): ");
            int cardExpireYear = this.scanner.nextInt();

            try {
                System.out.print("Enter CVV: ");
                int cvv = this.scanner.nextInt();
                if (cvv < 100 || cvv > 999) {
                    System.out.println("Invalid CVV. It should be a 3-digit number.");
                    return;
                }
            } catch (InputMismatchException var7) {
                System.out.println("Invalid input. Please enter a 3-digit number for the CVV.");
                this.scanner.nextLine();
            }

            System.out.println("Card Number: " + cardNumber);
            System.out.println("Cardholder Name: " + cardName);
            System.out.println("Card Expiration Date: " + cardExpireMonth + "/" + cardExpireYear);
            double remainingAmount = this.getTotalPrice();
            if (this.customer.getbalance() >= remainingAmount) {
                this.customer.subbalance(remainingAmount);
                System.out.println("Payment of $" + remainingAmount + " was successful using Visa.");
                System.out.println("Remaining Balance: $" + this.customer.getbalance());
                Database.addOrder(this);
            } else {
                System.out.println("Insufficient balance. Payment cannot be processed.");
            }
        } catch (Exception var8) {
            System.out.println("Invalid input. Please try again.");
            this.scanner.nextLine();
        }
    }

    public void installment() {
        try {
            System.out.println("Enter number of months for installment payment (6 or 12): ");
            int months = this.scanner.nextInt();
            double interestRate = months == 6 ? 4.42 : (months == 12 ? 4.75 : 0.0);
            if (interestRate == 0.0) {
                System.out.println("Invalid number of months. Choose 6 or 12.");
                return;
            }
            double interestAmount = this.getTotalPrice() * interestRate / 100.0;
            double totalAmountWithInterest = this.getTotalPrice() + interestAmount;
            double monthlyInstallment = totalAmountWithInterest / months;
            System.out.println("Total amount with interest: $" + totalAmountWithInterest);
            System.out.println("Your monthly installment for " + months + " months will be: $" + monthlyInstallment);
            if (this.customer.getbalance() >= totalAmountWithInterest) {
                this.customer.subbalance(totalAmountWithInterest);
                System.out.println("Payment of $" + totalAmountWithInterest + " was successful.");
                System.out.println("Remaining Balance: $" + this.customer.getbalance());
                Database.addOrder(this);
            } else {
                System.out.println("Insufficient balance. Payment cannot be processed.");
            }
        } catch (Exception var10) {
            System.out.println("Invalid input. Please try again.");
            this.scanner.nextLine();
        }
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public String toString() {
        String result = "Order for Customer: " + this.customer.getUsername() + "\nProducts in Cart:\n";
        for (int i = 0; i < this.cart.size(); ++i) {
            Product product = (Product) this.cart.get(i);
            result = result + product.toString() + "\n";
        }
        result = result + "Total Price: $" + this.getTotalPrice();
        return result;
    }

    public ArrayList<Product> getProducts() {
        return this.cart;
    }

}