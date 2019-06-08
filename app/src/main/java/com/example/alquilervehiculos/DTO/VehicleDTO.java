package com.example.alquilervehiculos.DTO;

public class VehicleDTO {
    private String id;
    private String enrollment;
    private String brand;
    private String model;
    private String price_day;
    private String rented;

    public VehicleDTO() {
    }

    public VehicleDTO(String id, String enrollment, String brand, String model, String price_day, String rented) {
        this.id = id;
        this.enrollment = enrollment;
        this.brand = brand;
        this.model = model;
        this.price_day = price_day;
        this.rented = rented;
    }

    public String getRented() {
        return rented;
    }

    public void setRented(String rented) {
        this.rented = rented;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getPrice_day() {
        return price_day;
    }

    public void setPrice_day(String price_day) {
        this.price_day = price_day;
    }
}
