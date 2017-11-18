package com.aaronshaver;

public class Leads {

    private String _id;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String entryDate;

    public String get_id() {
        return _id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getEntryDate() {
        return entryDate;
    }

    @Override
    public String toString() {
        return String.format("{\"_id\": \"%s\", \"email\": \"%s\", \"firstName\": \"%s\", \"lastName\": \"%s\", \"address\": \"%s\", \"entryDate\": \"%s\"}",
                _id, email, firstName, lastName, address, entryDate);
    }
}