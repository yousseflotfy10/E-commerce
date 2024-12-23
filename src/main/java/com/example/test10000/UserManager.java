package com.example.test10000;



import java.time.LocalDate;

public class UserManager {
    public static boolean validateLogin(String username, String password, boolean isAdmin) {

        if (isAdmin) {
            for (int i = 0; i < Database.getAdminList().size(); i++) {
                Admin admin = Database.getAdminList().get(i);
                if (admin.getUsername().equals(username) && admin.getpassword().equals(password)) {
                    return true;
                }
            }

        } else {
            for (int i = 0; i < Database.getCustomerList().size(); i++) {
                Customer customer = Database.getCustomerList().get(i);
                if (customer.getUsername().equals(username) && customer.getpassword().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Register Admin
    public static boolean registerAdmin(String username, String password, LocalDate dateOfBirth, String role, int workingHours) {
        for (int i = 0; i < Database.getAdminList().size(); i++) {
            Admin admin = Database.getAdminList().get(i);
            if (admin.getUsername().equals(username)) {
                System.out.println("Admin username already exists.");
                return false;
            }
        }

        int attempts = 3;
        while (attempts > 0) {
            try {

                Person.validateUsername(username);
                Person.validatePassword(password);


                Admin newAdmin = new Admin(username, password, dateOfBirth, role, workingHours);
                Database.addAdmin(newAdmin);
                return true;

            } catch (Exception e) {
                attempts--;
                System.out.println("Invalid input: " + e.getMessage());
            }
        }

        return false;
    }


    public static boolean registerCustomer(String username, String password, LocalDate dateOfBirth, String address, Customer.Gender gender) {
        for (int i = 0; i < Database.getCustomerList().size(); i++) {
            Customer customer = Database.getCustomerList().get(i);
            if (customer.getUsername().equals(username)) {
                System.out.println("Customer username already exists.");
                return false;
            }
        }

        int attempts = 3;
        while (attempts > 0) {
            try {

                Person.validateUsername(username);
                Person.validatePassword(password);


                Customer newCustomer = new Customer(password, dateOfBirth, username, address, gender);
                Database.addCustomer(newCustomer);
                return true;

            } catch (Exception e) {
                attempts--;
                System.out.println("Invalid input: " + e.getMessage());
            }
        }

        return false;
    }

}
