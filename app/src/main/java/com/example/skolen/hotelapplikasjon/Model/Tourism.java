package com.example.skolen.hotelapplikasjon.Model;

/**
 * Created by Skolen on 15.05.2017.
 */

public class Tourism {

    private String name;
    private String description;
    private String address;

    public Tourism() {

    }

    public Tourism(String name, String description, String address) {
        this.name = name;
        this.description = description;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
