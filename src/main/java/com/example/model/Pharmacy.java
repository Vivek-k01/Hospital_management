package com.example.model;

import jakarta.persistence.*;



@Entity
@Table(name = "pharmacy")
public class Pharmacy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String medicine_name;
    private String category;
    private int stock;
    private double price;
    private String expiry_date;
    private String supplier;

    public Pharmacy() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMedicine_name() { return medicine_name; }
    public void setMedicine_name(String medicine_name) { this.medicine_name = medicine_name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getExpiry_date() { return expiry_date; }
    public void setExpiry_date(String expiry_date) { this.expiry_date = expiry_date; }
    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }
}
    

