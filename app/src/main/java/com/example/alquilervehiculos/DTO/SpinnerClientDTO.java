package com.example.alquilervehiculos.DTO;

import android.support.annotation.NonNull;

public class SpinnerClientDTO {
    private String id;
    private String name;
    private String middleName;
    private String surname;

    public SpinnerClientDTO() {
    }

    public SpinnerClientDTO(String name, String middleName, String surname) {
        this.name = name;
        this.middleName = middleName;
        this.surname = surname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @NonNull
    @Override
    public String toString() {
        return (surname + ", " + name + " " + middleName);
    }
}
