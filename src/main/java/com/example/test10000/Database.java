package com.example.test10000;

import java.time.LocalDate;
import java.util.ArrayList;

public class Database {
    private static ArrayList<Customer> customerList = new ArrayList<>(); // List of Customers
    private static ArrayList<Admin> adminList = new ArrayList<>(); // List of Admins
    private static ArrayList<Product> productList = new ArrayList<>(); // List of Products
    private static ArrayList<Cart> orderList = new ArrayList<>(); // List of Orders (Cart objects)

    public static void initialize() {
        // Predefined Customers
        customerList.add(new Customer("123", LocalDate.of(1990, 1, 15), "eyad", "123 Main St", Customer.Gender.MALE));
        customerList.add(new Customer("pass456", LocalDate.of(1995, 6, 10), "jane_doe", "456 Elm St", Customer.Gender.FEMALE));

        // Predefined Admins
        adminList.add(new Admin("admin", "admin123", LocalDate.of(1985, 3, 25), "Manager", 40));
        adminList.add(new Admin("superadmin", "supersecure", LocalDate.of(1980, 8, 15), "Director", 50));

        productList.add(new Product("Electronics", "P001", 400, "Smartphone", 10));
        productList.add(new Product("Home Appliances", "P002", 300, "Microwave Oven", 10));
        productList.add(new Product("Gaming", "P003", 50, "Video Game", 10));
        productList.add(new Product("Books", "P004", 10, "Java Programming Book", 10));
        productList.add(new Product("Books", "P005", 20, "Python for Beginners", 10));
        productList.add(new Product("Clothing", "P006", 30, "Men's T-shirt", 10));
        productList.add(new Product("Clothing", "P007", 40, "Women's Jacket", 10));
        productList.add(new Product("Furniture", "P008", 200, "Dining Table", 10));
        productList.add(new Product("Furniture", "P009", 100, "Office Chair", 10));

        // Predefined Orders
        Cart order1 = new Cart(customerList.get(0));
        order1.addProduct(productList.get(0)); // Electronics - Smartphone
        order1.addProduct(productList.get(1)); // Home Appliances - Microwave Oven
        addOrder(order1);

        Cart order2 = new Cart(customerList.get(1));
        order2.addProduct(productList.get(2)); // Gaming - Video Game
        addOrder(order2);
    }


    // Get methods for lists
    public static ArrayList<Customer> getCustomerList() {
        return customerList;
    }

    public static ArrayList<Admin> getAdminList() {
        return adminList;
    }

    public static ArrayList<Product> getProductList() {
        return productList;
    }

    public static ArrayList<Cart> getOrderList() {
        return orderList;
    }

    // Add methods for adding objects to the lists
    public static void addCustomer(Customer customer) {
        customerList.add(customer);
    }

    public static void addAdmin(Admin admin) {
        adminList.add(admin);
    }

    public static void addProduct(Product product) {
        productList.add(product);
    }

    // Search for a product by ID
    public static Product getProductByID(String productID) {
        return productList.stream()
                .filter(product -> product.get_ID().equals(productID))
                .findFirst()
                .orElse(null);
    }

    // Remove a product by ID
    public static boolean removeProductByID(String productID) {
        Product product = getProductByID(productID);
        if (product != null) {
            productList.remove(product);
            return true;
        }
        return false;
    }

    // Update a product's details
    public static void updateProduct(String productID, String newType, double newPrice, String newName, int updatedQuantity) {
        Product product = getProductByID(productID);
        if (product != null) {
            product.setType(newType);
            product.set_price(newPrice);
            product.set_name(newName);
            product.set_Quantity(updatedQuantity);
            System.out.println("Product updated successfully: " + product);
        } else {
            System.out.println("Product with ID " + productID + " not found.");
        }
    }
    public static void updateProductQuantity(String productID, int newQuantity) {
        Product product = getProductByID(productID);
        if (product != null) {
            product.set_Quantity(newQuantity);
            System.out.println("Product updated: " + product.get_name() + ", New Quantity: " + newQuantity);
        } else {
            System.out.println("Error: Product with ID " + productID + " not found.");
        }
    }

    // Add a new Order (Cart)
    public static void addOrder(Cart order) {
        orderList.add(order);
        //System.out.println("Order for customer " + order.getCustomer().getUsername() + " has been successfully added.");
    }

    // Display all orders
    public static void displayOrders() {
        orderList.forEach(System.out::println);
    }
}
