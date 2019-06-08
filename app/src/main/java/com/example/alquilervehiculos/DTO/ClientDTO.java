package com.example.alquilervehiculos.DTO;

public class ClientDTO {
    private String name;
    private String middleName;
    private String surname;
    private String personalId;
    private String phoneNumber;
    private String email;

    public ClientDTO() {
    }

    public ClientDTO(String name, String middleName, String surname, String personalId, String phoneNumber, String email) {
        this.name = name;
        this.middleName = middleName;
        this.surname = surname;
        this.personalId = personalId;
        this.phoneNumber = phoneNumber;
        this.email = email;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
