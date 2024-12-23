package com.example.test10000;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



public class Product extends Category {
    protected String ID;
    private double price;
    private String name;
    private int counter;
    private int quantity;

    public Product() {
        this.counter = 0;
    }

    public Product(String type, String id, double price, String name, int quantity) {
        super(type);
        this.ID = id;
        this.price = price;
        this.name = name;
        this.quantity = quantity;
    }

    public void set_ID(String ID) {
        this.ID = ID;
    }

    public void set_name(String name) {
        this.name = name;
    }

    public void set_price(double price) {
        this.price = price;
    }

    public String get_ID() {
        return this.ID;
    }

    public String get_name() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public int getCounter() {
        return this.counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void incrementCounter() {
        ++this.counter;
    }

    public void decrementCounter() {
        --this.counter;
    }

    public String toString() {
        return "ID: " + this.ID + ", Name: " + this.name + ", Price(per one): " + this.price + ", Quantity: " + this.counter;
    }

    public void set_Quantity(int updatedQuantity) {
    }

    public int reduceQuantity() {
        if (this.quantity > 0) {
            --this.quantity;
        }

        return this.quantity;
    }

    public int getQuantity() {
        return this.quantity;
    }
}
