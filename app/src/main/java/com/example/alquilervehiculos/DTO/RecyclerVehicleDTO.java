package com.example.alquilervehiculos.DTO;

public class RecyclerVehicleDTO {
    private String enrollment;
    private String brand;
    private String model;

    public RecyclerVehicleDTO() {
    }

    public RecyclerVehicleDTO(String enrollment, String brand, String model) {
        this.enrollment = enrollment;
        this.brand = brand;
        this.model = model;
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
