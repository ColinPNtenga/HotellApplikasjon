package com.example.skolen.hotelapplikasjon.Firebase.Entities;

/**
 * Created by Skolen on 05.05.2017.
 */

public class Customer {

    private String name;
    private int mobile;
    private String email;
    private int roomNumber;
    private String fromDate;
    private String toDate;

    public Customer() {

    }

    public Customer(String name, int mobile, String email, int roomNumber, String fromDate, String toDate) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.roomNumber = roomNumber;
        this.fromDate = fromDate;
        this.toDate = toDate;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
