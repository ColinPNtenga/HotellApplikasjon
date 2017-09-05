package com.example.skolen.hotelapplikasjon.Model;

/**
 * Created by Skolen on 05.05.2017.
 */

public class Food {

    private String name;
    private String cost;

    public Food() {

    }

    public Food(String name, String cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

}
