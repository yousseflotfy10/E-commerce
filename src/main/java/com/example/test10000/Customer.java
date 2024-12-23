package com.example.test10000;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer extends Person implements Person.Payment {

    private double balance = 10000;
    private String address;
    private  static Scanner scanner = new Scanner(System.in);
    private ArrayList<String> interests;

    public Customer(String customerpass1, LocalDate of, String customer1, String address, Gender gender) {
        super(customerpass1,of,customer1);
        this.address=address;
        this.gender=gender;
    }

    public String getAddress() {
        return address;
    }

    public Gender getGender() {
        return gender;
    }


    public enum Gender {
        MALE, FEMALE
    }

    public void Customer(){}


    private Gender gender;

    // Public method gender
    public void setGenderFromInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter gender (MALE/FEMALE): ");

        while (true) {
            try {
                String input = scanner.nextLine().trim().toUpperCase();
                setGender(input);
                System.out.println("Gender set successfully.");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input! Gender not set: " + e.getMessage());
            }
        }
    }




    // Method that validates
    public void setGender(String input) throws IllegalArgumentException {
        if (!input.equals("MALE") && !input.equals("FEMALE")) {
            throw new IllegalArgumentException("Input must be MALE or FEMALE.");
        }
        this.gender = Gender.valueOf(input);
    }

    public double getbalance()
    {
        return balance;
    }





    public void setInterest() {
        String[] predefinedInterests = { "Tech", "Fashion", "Sports", "Music", "Gaming" };
        interests = new ArrayList<>();

        System.out.println("Please choose your interests from the following list:");
        for (int i = 0; i < predefinedInterests.length; i++) {
            System.out.println((i + 1) + ". " + predefinedInterests[i]);
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter the number of your interest (or 0 to finish): ");
            int choice = scanner.nextInt();

            if (choice == 0) {
                break;
            }

            if (choice >= 1 && choice <= predefinedInterests.length) {
                String selectedInterest = predefinedInterests[choice - 1];
                if (!interests.contains(selectedInterest)) {
                    interests.add(selectedInterest);
                    System.out.println("Interest added: " + selectedInterest);
                } else {
                    System.out.println("Interest already added. Please select a different one.");
                }
            } else {
                System.out.println("Invalid choice! Please select a valid number.");
            }
        }
    }




    @Override



    public void subbalance (double amount){
        if (amount>balance){
            System.out.println("there are not enough money in your balance ");
        }
        else{
            {
                balance-=amount;
                System.out.println("Amount subtracted. New balance: " + balance);
            }

        }



    }


    public void addbalance(double visaamount){
        this.balance += visaamount;
        System.out.println("Amount added. New balance: " + balance);
    }
    public void setBalance(double newBalance) {
        if (newBalance < 0) {
            System.out.println("Balance cannot be negative.");
        } else {
            this.balance = newBalance;
            System.out.println("Balance updated to: " + balance);
        }
    }


    @Override

    public String toString() {

        return super.toString()+ " ";
    }
    public String getpassword()
    {
        return pass;
    }


}
