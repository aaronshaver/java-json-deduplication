package com.aaronshaver;

public class Leads {
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    private String _id;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    private String firstName;
    private String lastName;
    private String address;
    private String entryDate;

    public Leads(String _id, String email, String firstName, String lastName, String address, String entryDate) {
        _id = _id;
        email = email;
        firstName = firstName;
        lastName = lastName;
        address = address;
        entryDate = entryDate;
    }

}