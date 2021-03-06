package com.example.alquilervehiculos.DTO;

public class RecyclerClientDTO {
    private String id;
    private String name;
    private String middleName;
    private String surname;
    private String personalId;

    public RecyclerClientDTO() {
    }

    public RecyclerClientDTO(String id, String name, String middleName, String surname, String personalId) {
        this.id = id;
        this.name = name;
        this.middleName = middleName;
        this.surname = surname;
        this.personalId = personalId;
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

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }
}
