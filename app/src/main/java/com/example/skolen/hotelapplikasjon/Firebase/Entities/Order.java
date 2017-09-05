package com.example.skolen.hotelapplikasjon.Firebase.Entities;

/**
 * Created by Skolen on 26.05.2017.
 */

public class Order {

    private String foodName;
    private String customerName;
    private String customerRoom;
    private int quantity;

    public Order() {

    }

    public Order(String foodName, String customerName, String customerRoom, int quantity) {
        this.foodName = foodName;
        this.customerName = customerName;
        this.customerRoom = customerRoom;
        this.quantity = quantity;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerRoom() {
        return customerRoom;
    }

    public void setCustomerRoom(String customerRoom) {
        this.customerRoom = customerRoom;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
