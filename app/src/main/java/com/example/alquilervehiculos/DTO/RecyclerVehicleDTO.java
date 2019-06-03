package com.example.alquilervehiculos.DTO;

public class RecyclerVehicleDTO {
    private String id;
    private String enrollment;
    private String brand;
    private String model;

    public RecyclerVehicleDTO() {
    }

    public RecyclerVehicleDTO(String id, String enrollment, String brand, String model) {
        this.id = id;
        this.enrollment = enrollment;
        this.brand = brand;
        this.model = model;
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
}
