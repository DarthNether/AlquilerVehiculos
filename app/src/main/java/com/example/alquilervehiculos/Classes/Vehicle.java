package com.example.alquilervehiculos.Classes;

public class Vehicle {
    private int id;
    private String enrollment;
    private String brand;
    private String model;
    private double price_day;
    private String createdAt;

    public Vehicle() {
    }

    public Vehicle(int id, String enrollment, String brand, String model, double price_day, String createdAt) {
        this.id = id;
        this.enrollment = enrollment;
        this.brand = brand;
        this.model = model;
        this.price_day = price_day;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice_day() {
        return price_day;
    }

    public void setPrice_day(double price_day) {
        this.price_day = price_day;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
